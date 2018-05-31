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

    public MinorLogger socketLogger;

    private final static int PORT = 7777;
    private int finalPort;

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;

    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    private SocketDecoder decoder;

    private String msgIN;
    private String msgOUT;
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
        decoder = new SocketDecoder();
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


    public boolean usernameConfirm(String tempusername) throws IOException, GenericInvalidArgumentException {
        receiveMessage();
        if (decoder.getCmd().equals("insert") && decoder.getArg().equals("username")) {
            sendMessage("username", tempusername);
            socketLogger.minorLog("username "+tempusername+" sent");

            receiveMessage();
            if (decoder.getCmd().equals("confirm") && decoder.getArg().equals("username"))
            {
                socketLogger.minorLog("username confirmed");
                return true;
            }
            else
            {
                socketLogger.minorLog("username "+tempusername+" refused");
                return false;
            }
        } else
            return false;
    }

    //INITIALIZATION 2

    public int getPrivObj() throws IOException, GenericInvalidArgumentException {
       receiveMessage();

        if(decoder.getCmd().equals("privobj")) {
            socketLogger.minorLog("Received private objective ID: "+decoder.getArg());
            return Integer.parseInt(decoder.getArg());
        }
        else
            return getPrivObj();
    }


    public int[] getSchemes() throws IOException, GenericInvalidArgumentException {
        int temp[] = new int[2];

        receiveMessage();
        temp[0] = Integer.parseInt(decoder.getArg());
        socketLogger.minorLog("Scheme "+decoder.getArg()+" received");

        receiveMessage();
        temp[1] = Integer.parseInt(decoder.getArg());
        socketLogger.minorLog("Scheme "+decoder.getArg()+" received");

        return temp;
    }
    public int[] getPubObjs() throws IOException, GenericInvalidArgumentException {
            int[] temp = new int[3];
            for(int i=0; i<3; i++)
            {
               receiveMessage();
               socketLogger.minorLog("Public Objective "+Integer.toString(i+1)+" ID: "+decoder.getArg());
               temp[i] = Integer.parseInt(decoder.getArg());
            }

            receiveMessage();
            if(decoder.getCmd().equals("wait")&&decoder.getArg().equals("players"))
            {
                socketLogger.minorLog("waiting for players");
            }
            return temp;
        }
    /*
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

    */
    //RECEPTION
    private void receiveMessage() throws IOException {
        decoder.simpleDecode(inSocket.readLine());
    }

    //SENDING
    private void sendMessage(String cmd, String arg)
    {
        outSocket.println(decoder.simpleEncode(cmd, arg));
        outSocket.flush();
    }


    //PLAYER'S DISCONNECTION MANAGEMENT


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

}