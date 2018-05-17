package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.*;
import java.net.Socket;

public class ServerPlayer extends Thread
{

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private int id;
    private String username="";
    private String tempCmd="";
    private String tempArg="";
    private String msg;
    private int numPlayers=1;
    private boolean firstPlayerHasDone;
    private String[] tempNames;
    private String[] playerNames;

    public MinorLogger sPlayerLog;

    public ServerPlayer(Socket s, int n) throws IOException {

        sPlayerLog = new MinorLogger();
        sPlayerLog.minorLog("ServerPlayer Logger operative");


        sPlayerLog.minorLog("ServerPlayer check");
        socket = s;
        id=n;
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        sPlayerLog.minorLog("check serverplayer constructor end");
        numPlayers=1;


    }

    public ServerPlayer(Socket s, int n, String[] tempArr)
    {
        try {

            sPlayerLog = new MinorLogger();
            sPlayerLog.minorLog("ServerPlayer Logger operative");

            sPlayerLog.minorLog("ServerPlayer 2 check");
            this.socket = s;
            this.id=n;
            this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
            tempNames = new String[tempArr.length];
            tempNames = tempArr;

        } catch(Exception ex)
        {
            sPlayerLog.minorLog("Exception ex");
        }
    }

    //INITIALIZATION 1

    public void initializeFirst() {
        try {
            outSocket.println("#player#$1$");
            outSocket.flush();

            while(username=="")
            {
                outSocket.println("#insert#$username$");
                outSocket.flush();
                msg = inSocket.readLine();
                simpleDecode(msg);
                if (tempCmd.equals("username"))
                {
                    username = tempArg;
                    outSocket.println("#confirm#$username$");
                    outSocket.flush();
                }
            }

            while(numPlayers==1)
            {
                outSocket.println("#insert#$numplayers$");
                outSocket.flush();
                msg = inSocket.readLine();
                simpleDecode(msg);
                if(tempCmd.equals("numplayers"))
                {
                    int n = Integer.parseInt(tempArg);
                    if(n>1&&n<5)
                        numPlayers=n;
                }

            }
            outSocket.println("#confirm#$numplayers$");
            outSocket.flush();
            outSocket.println("#wait#$players$");
        } catch(Exception e) {
            try {
                sPlayerLog.minorLog("Exception e");
                socket.close();
                sPlayerLog.minorLog("Socket closed");
            } catch(Exception ex)
            {}
        }
    }

    private void simpleDecode(String tempStr)
    {
        //parts the message into command and argument
        tempCmd="";
        tempArg="";
        int index=0;
        if(tempStr.charAt(index)=='#')
        {
            index++;
            while (tempStr.charAt(index) != '#')
            {
                tempCmd+=Character.toString(tempStr.charAt(index));
                index++;
            }
            index++;
            if(tempStr.charAt(index)=='$');
            {
                index++;
                while(tempStr.charAt(index)!='$')
                {
                    tempArg+=Character.toString(tempStr.charAt(index));
                    index++;
                }
            }
        }
    }

    public void initializeN()
    {
        try {
            outSocket.println("#player#$"+Integer.toString(id+1)+"$");
            outSocket.flush();

            while(username=="")
            {
                outSocket.println("#insert#$username$");
                outSocket.flush();
                msg = inSocket.readLine();
                simpleDecode(msg);
                if (tempCmd.equals("username"))
                {
                    if(checkExistingName()) {
                        username = tempArg;
                    }
                }
            }
            outSocket.println("#wait#$players$");


        } catch(Exception e) {
            try {
                System.out.println("Exception e");
                socket.close();
                System.out.println("Socket closed");
            } catch(Exception ex)
            {}
        }
    }

    public int getNumPlayers()
    {
        return numPlayers;
    }

    public boolean hasFPDone()
    {
        return firstPlayerHasDone;
    }

    public String getUsername()
    {
        return username;
    }

    public boolean checkExistingName()
    {
        boolean flag=true;
        //checks the username is not already in use
        for(int i=0;i<tempNames.length;i++)
            if(tempNames[i].equals(tempArg))
                flag = false;

        return flag;
    }

    public void sendPlayersUsernames(String[] temp)
    {
        numPlayers=temp.length;
        playerNames=new String[numPlayers];
        playerNames=temp;
        for(int i=0;i<numPlayers;i++)
        {
            outSocket.println("#player#$" + Integer.toString(i + 1) + "$");
            outSocket.flush();
            outSocket.println("#username#$"+playerNames[i]+"$");
            outSocket.flush();

        }
    }

    //INITIALIZATION 2

    public void sendPrivateObj(int id)
    {
        outSocket.println("#privateObj#$id$");
        outSocket.flush();
        outSocket.println("#id#$"+Integer.toString(id)+"$");
        outSocket.flush();

    }

    public void sendScheme(int id)
    {
        outSocket.println("#scheme#$"+Integer.toString(id)+"$");
        outSocket.flush();
    }

    public void sendPubObjs(int id1, int id2, int id3)
    {
        outSocket.println("#pubobj#$"+Integer.toString(id1)+"$");
        outSocket.flush();
        outSocket.println("#pubobj#$"+Integer.toString(id2)+"$");
        outSocket.flush();
        outSocket.println("#pubobj#$"+Integer.toString(id3)+"$");
        outSocket.flush();
    }

    public int[] receiveScheme() throws IOException, InvalidinSocketException
    {
        outSocket.println("#insert#$scheme$");
        outSocket.flush();
        int[] temp = new int[2];
        msg=inSocket.readLine();
        simpleDecode(msg);

        if(Integer.parseInt(tempArg)<1||Integer.parseInt(tempArg)>12)
            throw new InvalidinSocketException();

        temp[0]=Integer.parseInt(tempArg);
        msg=inSocket.readLine();
        simpleDecode(msg);

        if(Integer.parseInt(tempArg)!=1&&Integer.parseInt(tempArg)!=2)
            throw new InvalidinSocketException();

        temp[1]=Integer.parseInt(tempArg);
        outSocket.println("#wait#$players$");
        outSocket.flush();
        return temp;
    }

    public void sendSchemeVect(SchemeCard[] vect) throws InvalidIntArgumentException {
        for(int i=0;i<numPlayers;i++)
        {
            outSocket.println("#player#$"+Integer.toString(i+1)+"$");
            outSocket.flush();
            outSocket.println("#scheme#$"+Integer.toString(vect[i].getID())+"$");
            outSocket.flush();
            outSocket.println("#fb#$"+Integer.toString(vect[i].getfb())+"$");
            outSocket.flush();
            outSocket.println("#favtokens#$"+Integer.toString(vect[i].getDiff(vect[i].getfb())));
        }
    }

}

