package it.polimi.ingsw.client.PackageMP.Connection;


import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.PlayerClient;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.FcknSimpleLogger;
import it.polimi.ingsw.commons.Socket.EventHandling.EventDecoder;
import it.polimi.ingsw.commons.Socket.EventHandling.EventEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketDecoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

public class SocketClient extends Observable {


    private final static int PORT = 7777;
    private int finalPort;

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private FcknSimpleLogger logger;

    private SocketProtocolTransformer transformer;
    private SocketDecoder decoder;
    private SocketEncoder encoder;
    private EventEncoder eventEncoder;
    private EventDecoder eventDecoder;

    private String msgIN;
    private String msgOUT;
    private ArrayList<String> msg;
    private Event currentEvent;
    private boolean isConfirmed;
    private boolean isConnected;

    private int playerID=0;
    private int numPlayers=0;
    private String serverIP;

    private int[] notMyTurnMove;


    public SocketClient(String ip) throws GenericInvalidArgumentException, IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {

        logger = new FcknSimpleLogger(1, true);
        isConnected=false;
        serverIP = ip;
        notMyTurnMove = new int[4];
        transformer = new SocketProtocolTransformer();
        decoder = new SocketDecoder();
        encoder = new SocketEncoder();
        eventEncoder = new EventEncoder();
        eventDecoder = new EventDecoder();
        msg = new ArrayList<String>();
        connect();
    }

    private void connect() throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        socket = new Socket(serverIP, PORT);
        this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        logger.debugLog("Connected on port 7777");
        logger.debugLog("inSocket/outSocket initialized");
        isConnected=true;
    }

    public void sendEvent(Event event)
    {
        logger.debugLog(event.getType());
        sendEncoded(eventEncoder.encodeEvent(event));
    }

    public void getEvent() throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, IOException {
        msg = new ArrayList<String>();
        receiveEvent();
        currentEvent = eventDecoder.decodeEvent(msg);

        setChanged();
        notifyObservers(currentEvent);

    }


    //RECEPTION
    private void receiveMessage() throws IOException {
        transformer.simpleDecode(inSocket.readLine());
    }
    private void simpleReceive() throws IOException {
        msgIN = inSocket.readLine();

        logger.debugLog("msgIN : "+msgIN);

    }
    private void receiveEvent() throws IOException
    {
        simpleReceive();
        transformer.simpleDecode(msgIN);
        while (!(transformer.getCmd().equals("end")&&transformer.getArg().equals("event")))
        {
            msg.add(msgIN);

            simpleReceive();
            transformer.simpleDecode(msgIN);
        }

    }

    //SENDING
    private void sendMessage(String cmd, String arg)
    {
        outSocket.println(transformer.simpleEncode(cmd, arg));
        outSocket.flush();
    }
    private void sendEncoded(ArrayList<String> arg)
    {
        //DEBUG
        logger.debugLog(" ");
        logger.debugLog("Send");
        logger.debugLog(" ");

        for(int i=0;i<arg.size();i++)
        {
            //DEBUG
            logger.debugLog(arg.get(i));

            outSocket.println(arg.get(i));
            outSocket.flush();
        }
    }


    //PLAYER'S DISCONNECTION MANAGEMENT


    public boolean connectionCheck()
    {
        return isConnected;
    }

}