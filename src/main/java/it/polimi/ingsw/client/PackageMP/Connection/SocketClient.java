package it.polimi.ingsw.client.PackageMP.Connection;


import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemesDeckMP;
import it.polimi.ingsw.client.PackageMP.Player;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.*;
import java.net.Socket;

public class SocketClient {

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
    private String serverIP;

    public SocketClient(String ip) throws GenericInvalidArgumentException, IOException {

        socketLogger = new MinorLogger();
        socketLogger.minorLog("SocketClient logger operative");
        isConnected=false;
        serverIP = ip;

        connect();
    }

    //INITIALIZATION 1: PHASE 1

    private void connect() throws IOException, GenericInvalidArgumentException {
        socket = new Socket(serverIP, PORT);
        this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        socketLogger.minorLog("Connected on port 7777");
        socketLogger.minorLog("inSocket/outSocket initialized");
        isConnected=true;
    }

    public boolean getInsertUsername() throws IOException {
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);
        if(tempCmd.equals("insert")&&tempArg.equals("username"))
            return true;
        else
            return false;
    }

    public void sendUsername(String s) throws GenericInvalidArgumentException {
        outSocket.println(simpleEncode("username", s));
        outSocket.flush();
        socketLogger.minorLog("Username "+s+" sent");
    }

    public boolean usernameConfirm() throws IOException, GenericInvalidArgumentException {
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("confirm")&&tempArg.equals("username"))
        {
            socketLogger.minorLog("Username confirmed");
            return true;
        }
        if(tempCmd.equals("insert")&&tempArg.equals("username"))
        {
            socketLogger.minorLog("Username not valid");
            return false;
        }
        else
            return false;
    }


    //INITIALIZATION 1: PHASE 2



    //INITIALIZATION 2

    public int getPrivObj() throws IOException, GenericInvalidArgumentException {
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("privobj")) {
            socketLogger.minorLog("Received private objective ID: "+tempArg);
            return Integer.parseInt(tempArg);
        }
        else
            return getPrivObj();
    }

    public int[] getSchemes() throws IOException, GenericInvalidArgumentException {
        int temp[] = new int[2];

        msgIN=inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("scheme")) {
            temp[0] = Integer.parseInt(tempArg);
            socketLogger.minorLog("Scheme "+tempArg+" received");
        }
        else
            return getSchemes();

        msgIN=inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("scheme"))
        {
            temp[1] = Integer.parseInt(tempArg);
            socketLogger.minorLog("Scheme "+tempArg+" received");
        }
        else
            return getSchemes();

        return temp;

    }

    public int[] getPublicObjs() throws IOException, GenericInvalidArgumentException {

        int[] temp = new int[3];

        for(int i=0; i<3; i++)
        {
            msgIN = inSocket.readLine();
            simpleDecode(msgIN);

            if(tempCmd.equals("pubobj"))
            {
                socketLogger.minorLog("Public Objective "+Integer.toString(i+1)+" ID: "+tempArg);
                temp[i] = Integer.parseInt(tempArg);
            }

        }

        msgIN = inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("wait")&&tempArg.equals("players"))
        {
            socketLogger.minorLog("waiting for players");
        }
        return temp;
    }

    public boolean selectScheme(int id, int fb) throws IOException {
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("insert")&&tempArg.equals("scheme"))
        {
            outSocket.println(simpleEncode("scheme", Integer.toString(id)));
            outSocket.println();

            outSocket.println(simpleEncode("fb", Integer.toString(fb)));
            outSocket.flush();

            msgIN = inSocket.readLine();
            simpleDecode(msgIN);

            if(tempCmd.equals("wait")&&tempArg.equals("players"))
                return true;
            else
                return false;
        }
        else
            selectScheme(id, fb);
        return false;
    }

    //INITIALIZATION 2: PHASE 2

    public int getNumPlayers() throws IOException {
        msgIN = inSocket.readLine();
        simpleDecode(msgIN);

        if(tempCmd.equals("numplayers"))
        {
            numPlayers = Integer.parseInt(tempArg);
            return numPlayers;
        }
        else
            getNumPlayers();
        return 0;
    }

    public Player getPlayer() throws IOException, InvalidIntArgumentException {
        int id;

        msgIN = inSocket.readLine();
        simpleDecode(msgIN);
        if(tempCmd.equals("player")) {
            id = Integer.parseInt(tempArg);

            msgIN = inSocket.readLine();
            simpleDecode(msgIN);
            if(tempCmd.equals("username")) {
                Player temp = new Player(id, tempArg);

                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
                if (tempCmd.equals("scheme")) {
                    SchemesDeckMP tempSCDeck = new SchemesDeckMP();
                    SchemeCardMP tempSC = tempSCDeck.extractSchemebyID(id);

                    msgIN = inSocket.readLine();
                    simpleDecode(msgIN);

                    if(tempCmd.equals("fb")) {
                        tempSC.setfb(Integer.parseInt(tempArg));
                        temp.setPlayerScheme(tempSC);

                        msgIN = inSocket.readLine();
                        simpleDecode(msgIN);
                        if(tempCmd.equals("favtokens")) {
                            temp.setTokens(Integer.parseInt(tempArg));

                            msgIN = inSocket.readLine();
                            simpleDecode(msgIN);
                            if(tempCmd.equals("wait")&&tempArg.equals("players"))
                                return temp;
                        }
                    }
                }
            }
        }
        else
            getPlayer();
        return null;
    }



    //PLAYER'S DISCONNECTION MANAGEMENT


    public boolean connectionCheck()
    {
        return isConnected;
    }

    //SIMPLE ENCODE/DECODE

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

}