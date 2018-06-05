package it.polimi.ingsw.client.PackageMP.Connection;


import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.PlayerClient;
import it.polimi.ingsw.commons.SocketDecoder;
import it.polimi.ingsw.commons.SocketEncoder;
import it.polimi.ingsw.commons.SocketProtocolTransformer;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SocketClient {

    public MinorLogger socketLogger;

    private final static int PORT = 7777;
    private int finalPort;

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;

    private SocketProtocolTransformer transformer;
    private SocketDecoder decoder;
    private SocketEncoder encoder;

    private String msgIN;
    private String msgOUT;
    private boolean isConfirmed;
    private boolean isConnected;

    private int playerID=0;
    private int numPlayers=0;
    private String serverIP;

    private int[] notMyTurnMove;


    public SocketClient(String ip) throws GenericInvalidArgumentException, IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {

        socketLogger = new MinorLogger();
        socketLogger.minorLog("SocketClient logger operative");
        isConnected=false;
        serverIP = ip;
        notMyTurnMove = new int[4];
        transformer = new SocketProtocolTransformer();
        decoder = new SocketDecoder();
        encoder = new SocketEncoder();
        connect();
    }

    //INITIALIZATION 1: PHASE 1

    private void connect() throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        socket = new Socket(serverIP, PORT);
        this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        socketLogger.minorLog("Connected on port 7777");
        socketLogger.minorLog("inSocket/outSocket initialized");
        isConnected=true;
    }


    public boolean usernameConfirm(String tempusername) throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        receiveMessage();
        if (transformer.getCmd().equals("insert") && transformer.getArg().equals("username")) {
            sendMessage("username", tempusername);
            socketLogger.minorLog("username "+tempusername+" sent");

            receiveMessage();
            if (transformer.getCmd().equals("confirm") && transformer.getArg().equals("username"))
            {
                socketLogger.minorLog("username confirmed");
                receiveMessage();
                return true;
            }
            else
            {
                socketLogger.minorLog("username "+tempusername+" refused");
                receiveMessage();
                return false;
            }
        } else
            return false;
    }

    //INITIALIZATION 2

    public int getPrivObj() throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
       receiveMessage();

        if(transformer.getCmd().equals("privobj")) {
            socketLogger.minorLog("Received private objective ID: "+ transformer.getArg());
            return Integer.parseInt(transformer.getArg());
        }
        else
            return 0;
    }


    public int[] getSchemes() throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        int temp[] = new int[2];


        receiveMessage();
        temp[0] = Integer.parseInt(transformer.getArg());
        socketLogger.minorLog("Scheme "+ transformer.getArg()+" received");

        receiveMessage();
        temp[1] = Integer.parseInt(transformer.getArg());
        socketLogger.minorLog("Scheme "+ transformer.getArg()+" received");

        return temp;
    }
    public int[] getPubObjs() throws IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
            int[] temp = new int[3];
            for(int i=0; i<3; i++)
            {
               receiveMessage();
               socketLogger.minorLog("Public Objective "+Integer.toString(i+1)+" ID: "+ transformer.getArg());
               temp[i] = Integer.parseInt(transformer.getArg());
            }

            return temp;
    }
    public int[] getTools() throws IOException {
        int[] temp = new int[3];
        receiveMessage();
        temp[0] = Integer.parseInt(transformer.getArg());
        receiveMessage();
        temp[1] = Integer.parseInt(transformer.getArg());
        receiveMessage();
        temp[2] = Integer.parseInt(transformer.getArg());
        return temp;
    }
    public void getSelectionCheck() throws IOException {
        //gets insert scheme command from the server
        receiveMessage();
    }

    public boolean getSchemeConfirmation(int schemeId, int fb) throws IOException {

        sendMessage("scheme", Integer.toString(schemeId));
        sendMessage("fb", Integer.toString(fb));

        receiveMessage();
        if(transformer.getCmd().equals("wait")&& transformer.getArg().equals("players"))
            return true;
        else
            return false;
    }

    //INITIALIZATION 2: PHASE 2
    public PlayerClient[] getPlayersStart(String myUsername) throws IOException, InvalidIntArgumentException {
        //receives players before the start of first round

        SchemesDeckMP deck = new SchemesDeckMP();
        receiveMessage();
        int numPlayers=Integer.parseInt(transformer.getArg());
        PlayerClient[] temp = new PlayerClient[numPlayers];
        for(int i=0;i<numPlayers;i++)
        {
            PlayerClient player;
            //setting id & username
            receiveMessage();
            receiveMessage();
            if(transformer.getArg().equals(myUsername))
                player = new PlayerClient(i, transformer.getArg(), true);
            else
                player  = new PlayerClient(i, transformer.getArg(), false);


            //setting schemecard
            receiveMessage();
            int id = Integer.parseInt(transformer.getArg());
            receiveMessage();
            SchemeCardMP tempSC = deck.extractSchemebyID(id);
            tempSC.setfb(Integer.parseInt(transformer.getArg()));
            player.setPlayerScheme(tempSC);

            //setting tokens
            receiveMessage();
            player.setTokens(Integer.parseInt(transformer.getArg()));

            temp[i]=player;
        }
        return temp;
    }

    //TURN METHODS

    //turn initialization
    public int newTurn() throws IOException {
        //receives round string
        receiveMessage();
        return Integer.parseInt(transformer.getArg());
    }
    public ArrayList<Integer> getDiscPlayers() throws IOException {
        receiveMessage();
        int num = Integer.parseInt(transformer.getArg());
        ArrayList<Integer> temp = new ArrayList<Integer>();

        for(int i=0;i<num;i++)
        {
            receiveMessage();
            temp.add(Integer.parseInt(transformer.getArg()));
        }
        return temp;
    }
    public int getActive() throws IOException {
        receiveMessage();
        return Integer.parseInt(transformer.getArg());
    }

    public boolean askForActive() throws IOException {
        receiveMessage();
        if(transformer.getArg().equals("todo"))
            return true;
        else
            return false;
    }
    public boolean toDo(int arg) throws IOException {
        sendMessage("todo", Integer.toString(arg));
        return serverCheck();
    }
    public boolean move(int[] arg) throws IOException {
        sendMessage("client", "move");
        sendEncoded(encoder.draftEncoder(arg[0]));
        sendEncoded(encoder.schemeCardEncoder(arg[1], arg[2]));
        return serverCheck();
    }

    //NOT MY TURN
    public int[] getAction() throws IOException {
        //int[0] = player id, int[1] = what he does
        int[] temp = new int[2];
        receiveMessage();
        temp[0] = Integer.parseInt(transformer.getArg());
        receiveMessage();
        temp[1] = Integer.parseInt(transformer.getArg());
        return temp;

    }


    //MODEL COMPONENTS DECODING
    public DraftPoolMP getDraft() throws IOException, InvalidIntArgumentException {

        ArrayList<String> tempAl = new ArrayList<String>();
        //receives the arraylist
        receiveMessage();
        if(transformer.getCmd().equals("model")&&transformer.getArg().equals("draft")) {
            simpleReceive();
            transformer.simpleDecode(msgIN);
            while(!(transformer.getCmd().equals("end")&&transformer.getArg().equals("draft")))
            {
                tempAl.add(msgIN);
                simpleReceive();
                transformer.simpleDecode(msgIN);
            }
        }
        return decoder.draftPoolDecoder(tempAl);
    }
    public RoundTrackMP getTrack() throws IOException, InvalidIntArgumentException, FullDataStructureException {
        ArrayList<String> tempAl = new ArrayList<String>();
        //receives the arraylist
        receiveMessage();
        if(transformer.getCmd().equals("model")&&transformer.getArg().equals("track")) {
            simpleReceive();
            transformer.simpleDecode(msgIN);
            while(!(transformer.getCmd().equals("end")&&transformer.getArg().equals("track")))
            {
                tempAl.add(msgIN);
                simpleReceive();
                transformer.simpleDecode(msgIN);
            }
        }
        return decoder.roundTrackDecoder(tempAl);
    }
    public int[] toolCardUpdate() throws IOException {

        String[] temp = new String[6];
        for(int i=0;i<3;i++)
        {
            simpleReceive();
            temp[i] = msgIN;
        }

        return decoder.toolTokensDecode(temp);
    }
    public PlayerClient getSchemeCard(PlayerClient previousPlayer) throws InvalidIntArgumentException, IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        SchemesDeckMP deck = new SchemesDeckMP();
        SchemeCardMP scheme = deck.extractSchemebyID(previousPlayer.getPlayerScheme().getID());
        scheme.setfb(previousPlayer.getPlayerScheme().getfb());

        receiveMessage();
        receiveMessage();
        while (!(transformer.getCmd().equals("end")&&transformer.getArg().equals("scheme")))
        {
            //x
            int x = Integer.parseInt(transformer.getArg());
            //y
            receiveMessage();
            int y = Integer.parseInt(transformer.getArg());
            //color
            DieMP tempDie = new DieMP(Integer.parseInt(transformer.getArg()));
            //value
            receiveMessage();
            tempDie.setValueTest(Integer.parseInt(transformer.getArg()));

            scheme.setDie(tempDie, x, y);
            if(previousPlayer.getPlayerScheme().getDie(x, y).isDisabled()) {
                notMyTurnMove[0] = tempDie.getColor();
                notMyTurnMove[1] = tempDie.getValue();
                notMyTurnMove[2] = x;
                notMyTurnMove[3] = y;
            }
            receiveMessage();
        }

        PlayerClient toReturn = previousPlayer;
        toReturn.setPlayerScheme(scheme);
        return toReturn;
    }

    public int[] getNotMyTurnMove()
    {
        return notMyTurnMove;
    }

    //SERVER CHECKS
    private boolean serverCheck() throws IOException {
        receiveMessage();
        return Boolean.getBoolean(transformer.getArg());
    }

    //RECEPTION
    private void receiveMessage() throws IOException {
        transformer.simpleDecode(inSocket.readLine());
        //System.out.println("received: "+transformer.getCmd()+" "+transformer.getArg());
    }
    private void simpleReceive() throws IOException {
        msgIN = inSocket.readLine();
    }

    //SENDING
    private void sendMessage(String cmd, String arg)
    {
        //System.out.println("sent "+cmd+" "+arg);
        outSocket.println(transformer.simpleEncode(cmd, arg));
        outSocket.flush();
    }
    private void sendEncoded(String[] arg)
    {
        for(int i=0;i<arg.length;i++)
        {
            outSocket.println(arg[i]);
            outSocket.flush();
        }
    }


    //PLAYER'S DISCONNECTION MANAGEMENT


    public boolean connectionCheck()
    {
        return isConnected;
    }



}