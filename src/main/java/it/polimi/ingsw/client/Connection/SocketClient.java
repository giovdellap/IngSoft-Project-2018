package it.polimi.ingsw.client.Connection;


import it.polimi.ingsw.client.JSONSettings.SettingsReader;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.commons.Socket.EventHandling.EventDecoder;
import it.polimi.ingsw.commons.Socket.EventHandling.EventEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketDecoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

public class SocketClient extends Observable implements Runnable {



    private int port;

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private SimpleLogger logger;

    private SocketProtocolTransformer transformer;
    private EventEncoder eventEncoder;
    private EventDecoder eventDecoder;

    private String msgIN;
    private ArrayList<String> msg;
    private Event currentEvent;

    private String serverIP;

    private ArrayList<String> settings;

    /**
     * SocketClient Constructor
     * @param settings
     * @throws IOException
     */
    public SocketClient(ArrayList<String> settings) throws IOException {

        this.settings=settings;
        logger = new SimpleLogger(1, Boolean.parseBoolean(settings.get(0)));

        serverIP = settings.get(3);
        port = Integer.parseInt(settings.get(4));
        transformer = new SocketProtocolTransformer();
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
        socket = new Socket(serverIP, port);
        this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
        this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
        logger.debugLog("Connected on port "+Integer.toString(port));
        logger.debugLog("inSocket/outSocket initialized");
    }

    /**
     * sends an event
     * @param event event to send
     * @throws InvalidIntArgumentException
     */
    public void sendEvent(Event event) throws InvalidIntArgumentException {
        logger.debugLog(event.getType());
        sendEncoded(eventEncoder.encodeEvent(event));
    }

    /**
     * gets an event
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public void getEvent() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException {
        msg = new ArrayList<String>();
        receiveEvent();
        currentEvent = eventDecoder.decodeEvent(msg);
        if(currentEvent.getType().equals("TurnEvent")) {
            outSocket.println("PING");
            sendEvent(currentEvent);
        }
        setChanged();
        notifyObservers(currentEvent);
        System.out.println("connection manager notified");

    }

    /**
     * receives a message in socket
     * @throws IOException
     */
    private void simpleReceive() throws IOException {
        msgIN = inSocket.readLine();

        while (msgIN==null) {
            logger.debugLog("bound: " + Boolean.toString(socket.isBound()));
            logger.debugLog("closed: " + Boolean.toString(socket.isClosed()));
            logger.debugLog("connected: " + Boolean.toString(socket.isConnected()));
            logger.debugLog("input: " + Boolean.toString(socket.isInputShutdown()));
            logger.debugLog("output: " + Boolean.toString(socket.isOutputShutdown()));
            msgIN=inSocket.readLine();
        }
        logger.debugLog("msgIN : "+msgIN);

    }

    /**
     * receives an event
     * @throws IOException
     */
    private void receiveEvent() throws IOException
    {
        logger.debugLog("RECEIVE NEW EVENT");

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


    public void run() {
        try {
            getEvent();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (InvalidIntArgumentException e) {
            e.printStackTrace();
        } catch (GenericInvalidArgumentException e) {
            e.printStackTrace();
        }
    }

    public boolean ready() throws IOException {
        return inSocket.ready();
    }
}