package it.polimi.ingsw.client.Connection;


import it.polimi.ingsw.client.ConnectionClient;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

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

    private int playerID=0;
    private int numPlayers=0;

    public SocketClient() throws GenericInvalidArgumentException, IOException {

        socketLogger = new MinorLogger();
        socketLogger.minorLog("SocketClient logger operative");

        if(!firstClient())
            if(!nClient(1))
                if(!nClient(2))
                    if(!nClient(3)) {
                        socketLogger.minorLog("CONNECTION FAILED");
                        outVideo.println("CONNECTION FAILED");
                    }

        this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        isConfirmed = false;
        socketLogger.minorLog("inSocket/outSocket/inKeyboard/outVideo initialized");

        finalPort = socket.getPort();
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);
        playerID = Integer.parseInt(tempArg);
        socketLogger.minorLog("ID = "+tempArg);
        outVideo.println("ID = "+tempArg);
        outVideo.flush();

        usernameInsertion();
        if(playerID==1)
        numPlayersInsertion();


        msgIN=inSocket.readLine();
        simpleDecode(msgIN);
        if(tempCmd=="wait"&&tempArg=="players")
        {
            outVideo.println("WAITING FOR OTHER PLAYERS");
            socketLogger.minorLog("Initialization 1: phase 1 completed");
        }
    }

    //INITIALIZATION 1: PHASE 1

    private boolean firstClient() throws IOException {
        try
        {
            socket = new Socket("localhost", PORT);
        } catch(Exception e)
        {
            System.out.println("port "+Integer.toString(PORT)+" not working");
            socket.close();
            return false;
        }
        return true;
    }

    private boolean nClient(int n) throws IOException {
        try
        {
            socket = new Socket("localhost", PORT+n);
        } catch(Exception e)
        {
            System.out.println("port "+Integer.toString(PORT+n)+" not working");
            socket.close();
            return false;
        }
        return true;
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

    public void usernameInsertion() throws IOException, GenericInvalidArgumentException {
        isConfirmed = false;
        System.out.println("check 1");
        msgIN=inSocket.readLine();
        simpleDecode(msgIN);
        System.out.println(msgIN);
        while(!isConfirmed) {
            if (tempCmd.equals("insert")&&tempArg.equals("username"))
            {
                System.out.println("check 2");
                outVideo.println("INSERT USERNAME");
                outVideo.flush();
                socketLogger.minorLog("username requested from server");
                msgOUT = inKeyboard.readLine();
                socketLogger.minorLog("username "+msgOUT+" inserted from user");
                outSocket.println("#username#$"+msgOUT+"$");
                outSocket.flush();
                msgIN=inSocket.readLine();
                simpleDecode(msgIN);
                if(tempCmd.equals("confirm")&&tempArg.equals("username")) {
                    isConfirmed = true;
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
                outVideo.println("INSERT PLAYERS' NUMBER");
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
                    outVideo.println("number accepted");
                    outVideo.flush();
                    socketLogger.minorLog("numPlayers "+msgOUT+" accepted");
                }
            }
        }
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);
        if(tempCmd.equals("wait")&&tempArg.equals("players"))
        {
            socketLogger.minorLog("waiting for players");
            outVideo.println("waiting for players");
            outVideo.flush();
        }
    }

    //INITIALIZATION 1: PHASE 2

    public int getNumPlayers() throws IOException, GenericInvalidArgumentException {
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);
        if(tempCmd.equals("numplayers")) {
            numPlayers = Integer.parseInt(tempArg);
            socketLogger.minorLog("numPlayers = "+tempCmd);
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
            msgIN = inSocket.readLine();
            simpleDecode(msgIN);
            if(tempCmd.equals("player")&&tempArg==Integer.toString(i+1))
            {
                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
                if(tempCmd.equals("username"))
                {
                    temp[i]=tempArg;
                    outVideo.println("PLAYER "+Integer.toString(i+1)+": "+tempArg);
                    socketLogger.minorLog("player "+Integer.toString(i+1)+": "+tempArg);
                }
            }
        }
        return temp;
    }


    public void toolCardUsed(int id)
    {

    }

    public void getPrivObj()
    {

    }

    public int[] getSchemes()
    {
        return null;

    }

    public int getScoreMarkers()
    {

        return 0;
    }

    public int[] getPublicObjs()
    {

        return null;
    }

    public SchemeCardMP getOppSchemes()
    {

        return null;
    }

    public DraftPoolMP getDraftPool()
    {

        return null;
    }

    public void sendScheme(SchemeCardMP sc)
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