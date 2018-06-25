package it.polimi.ingsw.client.PackageMP.Connection;


import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.SimpleLogger;
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
    private SimpleLogger logger;

    private SocketProtocolTransformer transformer;
    private SocketDecoder decoder;
    private SocketEncoder encoder;
    private EventEncoder eventEncoder;
    private EventDecoder eventDecoder;

    private String msgIN;
    private String msgOUT;
    private ArrayList<String> msg;
    private Event currentEvent;
    private boolean isConnected;

    private String serverIP;

    private int[] notMyTurnMove;

    /**
     * SocketClient Constructor
     * @param ip
     * @throws IOException
     */
    public SocketClient(String ip) throws IOException {

        logger = new SimpleLogger(1, true);
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

    /**
     * connects to socket
     * @throws IOException
     */
    private void connect() throws IOException {
        socket = new Socket(serverIP, PORT);
        this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        logger.debugLog("Connected on port 7777");
        logger.debugLog("inSocket/outSocket initialized");
        isConnected=true;
    }

    /**
     * sends an event
     * @param event event to send
     * @throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException
     */
    public void sendEvent(Event event) throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException {
        logger.debugLog(event.getType());
        sendEncoded(eventEncoder.encodeEvent(event));
    }

    /**
     * gets an event
     * @throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException
     * @throws IOException
     */
    public void getEvent() throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, InvalidIntArgumentException, GenericInvalidArgumentException {
        msg = new ArrayList<String>();
        receiveEvent();
        currentEvent = eventDecoder.decodeEvent(msg);

        setChanged();
        notifyObservers(currentEvent);

    }

    /**
     * receives a message in socket
     * @throws IOException
     */
    private void simpleReceive() throws IOException {
        msgIN = inSocket.readLine();

        if(msgIN==null) {
            logger.debugLog("bound: " + Boolean.toString(socket.isBound()));
            logger.debugLog("closed: " + Boolean.toString(socket.isClosed()));
            logger.debugLog("connected: " + Boolean.toString(socket.isConnected()));
            logger.debugLog("input: " + Boolean.toString(socket.isInputShutdown()));
            logger.debugLog("output: " + Boolean.toString(socket.isOutputShutdown()));

        }
        logger.debugLog("msgIN : "+msgIN);

    }

    /**
     * receives an event
     * @throws IOException
     */
    private void receiveEvent() throws IOException
    {
        logger.debugLog("NEW EVENT");

        simpleReceive();
        transformer.simpleDecode(msgIN);
        while (!(transformer.getCmd().equals("end")&&transformer.getArg().equals("event")))
        {
            msg.add(msgIN);

            simpleReceive();
            transformer.simpleDecode(msgIN);
        }

    }

    /**
     * sends an encoded ArrayList of strings
     * @param arg ArrayList of encoded strings
     */
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


}