package it.polimi.ingsw.client.Connection;


import it.polimi.ingsw.client.MultiPackage.ConnectionClient;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.*;
import java.net.Socket;

public class SocketClient implements ConnectionClient {

    private final static int PORT = 7777;
    private int finalPort;
    public MinorLogger socketLogger;

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;


    private String msgIN;
    private String msgOUT;
    private String tempCmd;
    private String tempArg;
    private boolean isConfirmed;
    private boolean isConnected;

    private int playerID=0;
    private int numPlayers=0;

    public SocketClient() throws GenericInvalidArgumentException, IOException {

        socketLogger = new MinorLogger();
        socketLogger.minorLog("SocketClient logger operative");
        isConnected=false;

        connect();

        socket = new Socket("localhost", finalPort);
        this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        isConfirmed = false;
        socketLogger.minorLog("inSocket/outSocket/inKeyboard/outVideo initialized");
        isConnected=true;

        msgIN = inSocket.readLine();
        simpleDecode(msgIN);
        playerID = Integer.parseInt(tempArg);
        socketLogger.minorLog("ID = "+tempArg);
        outVideo.println("ID = "+tempArg);
        outVideo.flush();

        usernameInsertion();
        if(playerID==1)
            numPlayersInsertion();

        msgIN = inSocket.readLine();
        simpleDecode(msgIN);
        if(tempCmd.equals("wait")&&tempArg.equals("players"))
        {
            socketLogger.minorLog("waiting for players");

            outVideo.println();
            outVideo.flush();

            outVideo.println("waiting for players..");
            outVideo.flush();
        }

        socketLogger.minorLog("Initialization 1: phase 1 completed");

    }

    //INITIALIZATION 1: PHASE 1

    private void connect() throws IOException, GenericInvalidArgumentException {
        socket = new Socket("localhost", PORT);
        this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        socketLogger.minorLog("Connected on port 7777");

        msgIN = inSocket.readLine();
        simpleDecode(msgIN);
        if(tempCmd.equals("switch"))
            finalPort = Integer.parseInt(tempArg);

        socket.close();
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
            if(tempStr.charAt(index)=='$')
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

    public void usernameInsertion() throws IOException, GenericInvalidArgumentException {
        isConfirmed = false;
        msgIN=inSocket.readLine();
        simpleDecode(msgIN);
        while(!isConfirmed) {
            if (tempCmd.equals("insert")&&tempArg.equals("username"))
            {
                outVideo.println();
                outVideo.flush();
                outVideo.println("INSERT USERNAME");
                outVideo.flush();
                socketLogger.minorLog("username requested from server");
                msgOUT = inKeyboard.readLine();
                socketLogger.minorLog("username "+msgOUT+" inserted from user");
                outSocket.println("#username#$"+msgOUT+"$");
                outSocket.flush();
                msgIN=inSocket.readLine();
                while(msgIN==null)
                    msgIN=inSocket.readLine();
                simpleDecode(msgIN);
                if(tempCmd.equals("confirm")&&tempArg.equals("username")) {
                    isConfirmed = true;

                    outVideo.println();
                    outVideo.flush();

                    outVideo.println("username "+msgOUT+" accepted");
                    outVideo.flush();
                    socketLogger.minorLog("Username "+msgOUT+" accepted");
                }
            }
        }
    }

    public void numPlayersInsertion() throws IOException, GenericInvalidArgumentException {
        isConfirmed=false;
        msgIN=inSocket.readLine();
        simpleDecode(msgIN);
        while(!isConfirmed)
        {
            if(tempCmd.equals("insert")&&tempArg.equals("numplayers"))
            {
                outVideo.println();
                outVideo.flush();

                outVideo.println("INSERT PLAYERS NUMBER");
                outVideo.flush();
                socketLogger.minorLog("numPlayers requested from server");
                msgOUT = inKeyboard.readLine();
                socketLogger.minorLog(msgOUT+" inserted from user");
                outSocket.println("#numplayers#$"+msgOUT+"$");
                outSocket.flush();
                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
                if(tempCmd.equals("confirm")&&tempArg.equals("numplayers"))
                {
                    isConfirmed=true;

                    outVideo.println();
                    outVideo.flush();

                    outVideo.println("number accepted");
                    outVideo.flush();
                    socketLogger.minorLog("numPlayers "+msgOUT+" accepted");
                }
            }
        }

    }

    //INITIALIZATION 1: PHASE 2

    public int getNumPlayers() throws IOException, GenericInvalidArgumentException {
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("fail"))
        {
            outVideo.println("PLAYER "+tempArg+" DISCONNECTED");
            outVideo.flush();
            msgIN = inSocket.readLine();
            simpleDecode(msgIN);
        }

        if(tempCmd.equals("numplayers")) {
            numPlayers = Integer.parseInt(tempArg);
            socketLogger.minorLog("numPlayers = "+tempCmd);
            outVideo.println();
            outVideo.flush();
            outVideo.println(tempArg+" PLAYERS");
            outVideo.flush();
        }
        return numPlayers;
    }

    public String[] getPlayers() throws IOException, GenericInvalidArgumentException {
        String[] temp = new String[numPlayers];
        int i=0;
        while(i<numPlayers)
        {
            msgIN=inSocket.readLine();
            simpleDecode(msgIN);

            if(tempCmd.equals("fail"))
            {
                outVideo.println("PLAYER "+tempArg+" DISCONNECTED");
                outVideo.flush();
                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
            }

            if(tempCmd.equals("player")&&tempArg.equals(Integer.toString(i+1)))
            {
                msgIN = inSocket.readLine();
                while(msgIN==null)
                    msgIN = inSocket.readLine();
                simpleDecode(msgIN);

                if(tempCmd.equals("username"))
                {
                    temp[i]=tempArg;
                    outVideo.println("PLAYER "+Integer.toString(i+1)+": "+tempArg);
                    outVideo.flush();
                    socketLogger.minorLog("player "+Integer.toString(i+1)+": "+tempArg);
                }
            }
            i++;
        }
        return temp;
    }

    //INITIALIZATION 2

    public int getPrivObj() throws IOException, GenericInvalidArgumentException {
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("fail"))
        {
            outVideo.println("PLAYER "+tempArg+" DISCONNECTED");
            outVideo.flush();
            msgIN = inSocket.readLine();
            simpleDecode(msgIN);
        }

        if(tempCmd.equals("privobj")) {
            socketLogger.minorLog("Received private objective ID: "+tempArg);
            outVideo.println();
            outVideo.flush();
            outVideo.println("PRIVATE OBJECTIVE : "+tempArg);
            outVideo.flush();
            return Integer.parseInt(tempArg);
        }
        else {
            return 0;

        }
    }

    public int[] getSchemes() throws IOException, GenericInvalidArgumentException {
        int temp[] = new int[2];

        outVideo.println();
        outVideo.flush();

        msgIN=inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("fail"))
        {
            outVideo.println("PLAYER "+tempArg+" DISCONNECTED");
            outVideo.flush();
            msgIN = inSocket.readLine();
            simpleDecode(msgIN);
        }

        if(tempCmd.equals("scheme")) {
            temp[0] = Integer.parseInt(tempArg);
            outVideo.println("EXTRACTED SCHEME ID: "+tempArg);
            outVideo.flush();
            socketLogger.minorLog("Scheme "+tempArg+" received");
        }

        msgIN=inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("scheme"))
        {
            temp[1] = Integer.parseInt(tempArg);
            outVideo.println("EXTRACTED SCHEME ID: "+tempArg);
            outVideo.flush();
            socketLogger.minorLog("Scheme "+tempArg+" received");
        }

        return temp;

    }

    public int[] getPublicObjs() throws IOException, GenericInvalidArgumentException {

        int[] temp = new int[3];

        outVideo.println();
        outVideo.flush();

        for(int i=0; i<3; i++)
        {
            msgIN = inSocket.readLine();
            simpleDecode(msgIN);

            if(tempCmd.equals("fail"))
            {
                outVideo.println("PLAYER "+tempArg+" DISCONNECTED");
                outVideo.flush();
                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
            }

            if(tempCmd.equals("pubobj"))
            {
                socketLogger.minorLog("Public Objective "+Integer.toString(i+1)+" ID: "+tempArg);
                outVideo.println("PUBLIC OBJECTIVE "+Integer.toString(i+1)+" ID: "+tempArg);
                outVideo.flush();

                temp[i] = Integer.parseInt(tempArg);
            }

        }

        msgIN = inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("wait")&&tempArg.equals("players"))
        {
            outVideo.println();
            outVideo.flush();

            outVideo.println("WAITING FOR PLAYERS..");
            outVideo.flush();


            socketLogger.minorLog("waiting for players");
        }
        return temp;
    }

    public int[] selectAndSendScheme(int[] arg) throws IOException, GenericInvalidArgumentException {
        boolean flag=false;
        int[] temp = new int[2];

        outVideo.println();
        outVideo.flush();

        while(!flag)
        {
            msgIN=inSocket.readLine();
            simpleDecode(msgIN);

            if(tempCmd.equals("fail"))
            {
                outVideo.println("PLAYER "+tempArg+" DISCONNECTED");
                outVideo.flush();
                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
            }

            if(tempCmd.equals("insert")&&tempArg.equals("scheme")) {
                outVideo.println("SELECT A SCHEME");
                outVideo.flush();
                msgOUT = inKeyboard.readLine();
            }
            if(Integer.parseInt(msgOUT)==arg[0]||Integer.parseInt(msgOUT)==arg[1])
                flag=true;
            else
            {
                outVideo.println("SCHEME ID NOT VALID");
                outVideo.flush();
            }
        }
        socketLogger.minorLog("Scheme "+msgOUT+" selected");
        temp[0] = Integer.parseInt(msgOUT);
        outSocket.println("#scheme#$"+msgOUT+"$");
        outSocket.flush();

        outVideo.println();
        outVideo.flush();

        flag=false;
        while(!flag)
        {
            outVideo.println("SELECT FRONT OR BACK");
            outVideo.flush();

            outVideo.println("INSERT F FOR FRONT, B FOR BACK");
            outVideo.flush();

            msgOUT = inKeyboard.readLine();
            if(msgOUT.equals("F")||msgOUT.equals("B"))
                flag=true;
            else
            {
                outVideo.println("INSERTION NOT VALID");
                outVideo.flush();
            }
        }

        System.out.println(msgOUT);

        if(msgOUT.equals("F")) {

            socketLogger.minorLog("front selected");
            outSocket.println("#fb#$1$");
            outSocket.flush();
            temp[1]=1;
        }
        else {
            System.out.println("sono in else");
            socketLogger.minorLog("back selected");
            outSocket.println("#fb#$2$");
            outSocket.flush();
            temp[1]=2;
        }

        msgIN = inSocket.readLine();
        simpleDecode(msgIN);


        if(tempCmd.equals("wait")&&tempArg.equals("players"))
        {
            outVideo.println();
            outVideo.flush();

            outVideo.println("WAITING FOR PLAYERS..");
            outVideo.flush();


            socketLogger.minorLog("waiting for players");
        }
        return temp;

    }

    public int[] getOppSchemes() throws IOException, GenericInvalidArgumentException {
        int[] temp = new int[4];

        outVideo.println();
        outVideo.flush();

        msgIN = inSocket.readLine();
        simpleDecode(msgIN);
        System.out.println();

        if(tempCmd.equals("fail"))
        {
            outVideo.println("PLAYER "+tempArg+" DISCONNECTED");
            outVideo.flush();
            msgIN = inSocket.readLine();
            simpleDecode(msgIN);
        }

        if(tempCmd.equals("player"))
        {
            temp[0]=Integer.parseInt(tempArg);
            String disconnectedID=tempArg;


            msgIN = inSocket.readLine();
            simpleDecode(msgIN);
            if(tempCmd.equals("scheme"))
            {

                temp[1] = Integer.parseInt(tempArg);

                if(temp[0]==0)
                {
                    temp[1]=0;
                    temp[2]=0;
                    outVideo.println("Player "+disconnectedID+" disconnected");
                    outVideo.flush();
                    socketLogger.minorLog("Player "+disconnectedID+" disconnected");
                }

                socketLogger.minorLog("Player "+Integer.toString(temp[0])+" selected scheme "+tempArg);

                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
                if(tempCmd.equals("fb"))
                {

                    temp[2] = Integer.parseInt(tempArg);
                    socketLogger.minorLog("with fb "+tempArg);

                    msgIN = inSocket.readLine();
                    System.out.println(msgIN);
                    simpleDecode(msgIN);
                    if(tempCmd.equals("favtokens"))
                    {
                        temp[3] = Integer.parseInt(tempArg);
                        socketLogger.minorLog("favour tokens: "+tempArg);
                        outVideo.println("Player "+Integer.toString(temp[0])+" selected scheme "+Integer.toString(temp[1])+" fb: "+Integer.toString(temp[2])+" and got "+Integer.toString(temp[3])+" favourtokens");
                        outVideo.flush();
                    }
                }
            }
        }
        return temp;
    }

    public boolean connectionCheck()
    {
        return isConnected;
    }

    public DraftPoolMP getDraftPool()
    {

        return null;
    }

    public void sendScheme(int[] arg)
    {

    }

    public SchemeCardMP receiveOppScheme()
    {
        return null;
    }

    public int receiveOppTokens()
    {

        return 0;
    }

    public int oppUsesaTool()
    {

        return 0;
    }

    public int changeTurn()
    {

        return 0;
    }

    public int getIdScoreBoard()
    {
        return 0;

    }

    public int getScores()
    {
        return 0;

    }

    public void toolCardUsed(int id)
    {

    }

    public boolean checkedToolCardUsed()
    {
        return true;

    }

    public boolean checkedMove()
    {
        return true;

    }

    public void sendDraft(DraftPoolMP draft)
    {

    }

    public int[] receiveNewToolTokens()
    {

        return null;
    }

    public void selectScheme(int id, int fb)
    {

    }
}