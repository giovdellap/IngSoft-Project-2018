package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Demos.Move;
import it.polimi.ingsw.server.Demos.Tool3;
import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.*;
import java.net.Socket;

public class SocketPlayer extends Thread
{

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;

    private int id;
    private String username="";
    private String tempCmd="";
    private String tempArg="";
    private String msg;
    private int numPlayers;

    private String[] tempNames;
    private String[] playerNames;
    private boolean connected = true;

    public MinorLogger sPlayerLog;

    public SocketPlayer(Socket s) throws IOException, GenericInvalidArgumentException {

        sPlayerLog = new MinorLogger();
        sPlayerLog.minorLog("SocketPlayer Logger operative");


        sPlayerLog.minorLog("SocketPlayer check");
        socket = s;
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        sPlayerLog.minorLog("check serverplayer constructor end");

    }


    //INITIALIZATION 1

    public String insertUsername() throws GenericInvalidArgumentException, IOException {
        try {
            outSocket.println(simpleEncode("insert", "username"));
            outSocket.flush();

            simpleDecode(inSocket.readLine());
            if (tempCmd.equals("demo"))
            {
                if (tempArg.equals("1"))
                {
                    Move demo=new Move(socket);

                }
                if (tempArg.equals("5"))
                {
                    Tool3 demo=new Tool3(socket);
                }

            }

            System.out.println("tempArg: "+tempArg);
            return tempArg;
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
            return null;
        }
    }

    public void confirmUsername() throws IOException, GenericInvalidArgumentException {

        try {
            username = tempArg;
            outSocket.println(simpleEncode("confirm", "username"));
            outSocket.flush();

            outSocket.println(simpleEncode("wait", "players"));
            outSocket.flush();
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }


    public String getUsername()
    {
        return username;
    }


    public void init1Phase2NumPl(int num) throws GenericInvalidArgumentException, IOException {
        numPlayers = num;
        try
        {
            outSocket.println(simpleEncode("numplayers", Integer.toString(numPlayers)));
            outSocket.flush();
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public void init1Phase2Player(int index, String name) throws GenericInvalidArgumentException, IOException {
        try
        {
            outSocket.println(simpleEncode("player", Integer.toString(index)));
            outSocket.flush();

            outSocket.println(simpleEncode("username", name));
            outSocket.flush();
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }


    //INITIALIZATION 2

    public void sendPrivateObj(int id) throws GenericInvalidArgumentException, IOException {
       try
       {
           outSocket.println(simpleEncode("privobj", Integer.toString(id)));
           outSocket.flush();
       } catch (Exception e)
       {
           socket.close();
           disconnectionManager();
       }
    }

    public void sendScheme(int id) throws GenericInvalidArgumentException, IOException {
        try {
            outSocket.println(simpleEncode("scheme", Integer.toString(id)));
            outSocket.flush();
        } catch(Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public void sendPubObjs(int id1, int id2, int id3) throws GenericInvalidArgumentException, IOException {
       try
       {
           outSocket.println(simpleEncode("pubobj", Integer.toString(id1)));
           outSocket.flush();

           outSocket.println(simpleEncode("pubobj", Integer.toString(id2)));
           outSocket.flush();

           outSocket.println(simpleEncode("pubobj", Integer.toString(id3)));
           outSocket.flush();
       } catch (Exception e)
       {
           socket.close();
           disconnectionManager();
       }
    }

    public void setPlayersNames(String[]usernames){
        playerNames=usernames;

    }

    public int[] receiveScheme() throws IOException, InvalidinSocketException, GenericInvalidArgumentException {

        int[] temp = new int[2];
        try {
            outSocket.println("#insert#$scheme$");
            outSocket.flush();
        } catch (Exception e) {
            try {
                sPlayerLog.minorLog("Exception e");
                socket.close();
                sPlayerLog.minorLog("Socket closed");
                disconnectionManager();
            } catch (Exception ex) {
            }
        }

        try {
            msg = inSocket.readLine();
            simpleDecode(msg);
        } catch (Exception e)
        {
            try {
                sPlayerLog.minorLog("Exception e");
                socket.close();
                sPlayerLog.minorLog("Socket closed");
                disconnectionManager();
            } catch (Exception ex) {
            }
        }

        if(!connectionCheck())
        {
            temp[0]=0;
            temp[1]=0;
            disconnectionManager();
            return temp;
        }
        if(Integer.parseInt(tempArg)<1||Integer.parseInt(tempArg)>12)
            throw new InvalidinSocketException();

        temp[0]=Integer.parseInt(tempArg);

        msg=inSocket.readLine();
        simpleDecode(msg);


        if((!tempCmd.equals("fb"))||(Integer.parseInt(tempArg)!=1&&Integer.parseInt(tempArg)!=2)) {

            throw new InvalidinSocketException();
        }

        temp[1]=Integer.parseInt(tempArg);
        try {
            outSocket.println("#wait#$players$");
            outSocket.flush();
        } catch (Exception e) {
            try {
                sPlayerLog.minorLog("Exception e");
                socket.close();
                sPlayerLog.minorLog("Socket closed");
                disconnectionManager();
            } catch (Exception ex) {
            }
        }

        if(socket.isClosed())
            disconnectionManager();

        return temp;
    }

    public void sendSchemeVect(SchemeCard[] vect) throws InvalidIntArgumentException, GenericInvalidArgumentException {


        for(int i=0;i<numPlayers;i++)
        {
            try {

                outSocket.println(simpleEncode("player", Integer.toString(i)));
                outSocket.flush();

                outSocket.println(simpleEncode("username", playerNames[i]));
                outSocket.flush();

                outSocket.println("#scheme#$" + Integer.toString(vect[i].getID()) + "$");
                outSocket.flush();

                outSocket.println("#fb#$" + Integer.toString(vect[i].getfb()) + "$");
                outSocket.flush();

                outSocket.println("#favtokens#$" + Integer.toString(vect[i].getDiff(vect[i].getfb()))+"$");

            } catch (Exception e) {
                try {
                    sPlayerLog.minorLog("Exception e");
                    socket.close();
                    sPlayerLog.minorLog("Socket closed");
                    disconnectionManager();
                } catch (Exception ex) {
                }
            }
        }

        if(socket.isClosed())
            disconnectionManager();
    }



    //CONNECTION LOST MANAGEMENT
    private void disconnectionManager() throws GenericInvalidArgumentException {
        connected = false;
        sPlayerLog.minorLog("CLIENT "+Integer.toString(id+1)+" DISCONNECTED");
    }

    public boolean connectionCheck()
    {
        //returns true if connected
        return connected;
    }

    public void notifyDisconnectedPlayer(int playerID) throws GenericInvalidArgumentException, IOException {
        try {
            outSocket.println("#fail#$" + Integer.toString(playerID) + "$");
            outSocket.flush();
            sPlayerLog.minorLog("Disconnection player " + Integer.toString(playerID) + " notified to player " + Integer.toString(id));

        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    //ENCODE/SERVER


    public String simpleEncode(String cmd, String arg)
    {
        String temp = "#"+cmd+"#$"+arg+"$";
        return temp;
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
}

