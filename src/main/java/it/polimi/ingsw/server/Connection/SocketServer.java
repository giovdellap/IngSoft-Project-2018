package it.polimi.ingsw.server.Connection;

import it.polimi.ingsw.commons.Events.Disconnection.DisconnectionEvent;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.commons.Socket.EventHandling.EventDecoder;
import it.polimi.ingsw.commons.Socket.EventHandling.EventEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;

public class SocketServer extends Observable
{

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private String ip;

    //TOOLS
    private SocketProtocolTransformer transformer;
    private SocketEncoder socketEncoder;
    private EventEncoder eventEncoder;
    private EventDecoder eventDecoder;

    private String tempCmd="";
    private String tempArg="";
    private int numPlayers;

    private ArrayList<String> msg;
    private String[] tempNames;
    private String[] playerNames;
    private boolean connected = true;

    public Event currentEvent;

    public SimpleLogger logger;

    /**
     * SocketServer Constructor
     * @param s socket to assign to connection
     * @throws IOException
     */
    public SocketServer(Socket s) throws IOException
    {

        socket = s;
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        transformer = new SocketProtocolTransformer();
        socketEncoder= new SocketEncoder();
        eventEncoder = new EventEncoder();
        eventDecoder = new EventDecoder();

        msg = new ArrayList<String>();
        logger = new SimpleLogger(1, true);
    }

    /**
     * decodes an insertUsername event
     * @throws GenericInvalidArgumentException
     * @throws IOException
     * @throws InvalidIntArgumentException
     */
    public void insertUsername() throws IOException, InvalidIntArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException, GenericInvalidArgumentException {
            currentEvent = eventDecoder.decodeEvent(listenForEvent());
            setChanged();
            notifyObservers(currentEvent);
    }

    /**
     * send an event
     * @param event event to send
     * @throws InvalidIntArgumentException
     */
    public void sendEvent(Event event) throws InvalidIntArgumentException, IOException {
        try {
            sendArrayList(eventEncoder.encodeEvent(event));
        }catch (Exception e)
        {
            socket.close();
            logger.log("Player disconnected");
            disconnectionManager();
        }
    }

    /**
     * gets an event
     * @throws IOException
     * @throws InvalidIntArgumentException
     */
    public void getEvent() throws IOException, InvalidIntArgumentException {
        try {
            currentEvent = eventDecoder.decodeEvent(listenForEvent());
            setChanged();
            notifyObservers(currentEvent);
        }catch(Exception e)
        {
            disconnectionManager();
        }
    }

    /**
     * sends a draft pool as an event
     * @param draft draft pool to send
     * @throws InvalidIntArgumentException
     * @throws IOException
     * @throws GenericInvalidArgumentException
     */
    public void sendDraft(DraftPool draft) throws InvalidIntArgumentException, IOException, GenericInvalidArgumentException
    {
        String[] temp = socketEncoder.draftEncoder(draft);


        for(int i=0;i<temp.length;i++)
        {
            try
            {
                sendReadyMessage(temp[i]);
            } catch (Exception e)
            {
                socket.close();
                disconnectionManager();
            }
        }

    }


    /**
     * sends a scheme card as an event
     * @param scheme scheme card to send
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws IOException
     */
    public void sendScheme(SchemeCard scheme) throws InvalidIntArgumentException, GenericInvalidArgumentException, IOException
    {
        String[] temp = socketEncoder.schemeCardEncoder(scheme);
        try
        {
            for(int i=0;i<temp.length;i++)
                sendReadyMessage(temp[i]);
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    /**
     * gets tool's id
     * @return integer representing the tool's id
     * @throws IOException
     * @throws GenericInvalidArgumentException
     */

    public int getToolId() throws IOException, GenericInvalidArgumentException
    {
        int temp;
        try
        {
            receiveMessage();
        }catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
        temp=Integer.parseInt(transformer.getArg());
        return temp;
    }

    /**
     * manages the disconnection
     */

    private void disconnectionManager()
    {
        connected = false;
        DisconnectionEvent event = new DisconnectionEvent();
        setChanged();
        notifyObservers(event);
    }


    /**
     * encodes a message
     * @param cmd message command
     * @param arg message argument
     * @return string of the decoded message
     */

    public String simpleEncode(String cmd, String arg)
    {
        String temp = "#"+cmd+"#$"+arg+"$";
        return temp;
    }


    /**
     * receives a message
     * @throws IOException
     */
    private void receiveMessage() throws IOException
    {
        transformer.simpleDecode(inSocket.readLine());
    }

    /**
     * sends a message with parameter string
     * @param s string to be sent
     */
    private void sendReadyMessage(String s)
    {

        outSocket.println(s);
        outSocket.flush();
    }

    /**
     * listens for an event, stays in reception
     * @return ArrayList of strings with the event received
     * @throws IOException
     */
    private ArrayList<String> listenForEvent() throws IOException
    {
        ArrayList<String> temp = new ArrayList<String>();

        logger.debugLog(" ");
        logger.debugLog("RECEIVE");
        logger.debugLog(" ");

        String msgIN = inSocket.readLine();
        logger.debugLog(msgIN);

        transformer.simpleDecode(msgIN);
        while(!(transformer.getCmd().equals("end")&&transformer.getArg().equals("event")))
        {
            temp.add(msgIN);
            msgIN=inSocket.readLine();

            logger.debugLog(msgIN);

            transformer.simpleDecode(msgIN);
        }
        return temp;
    }

    /**
     * sends an ArrayList of strings
     * @param toSend ArrayList of strings to send
     */
    private void sendArrayList(ArrayList<String> toSend)
    {
        logger.debugLog(" ");
        logger.debugLog("SEND");
        logger.debugLog(" ");

        for(String go: toSend)
        {
            sendReadyMessage(go);
            logger.debugLog(go);
        }
    }

    public void changeSocket(Socket socket) throws IOException {
        this.socket=socket;
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

    }
}

