package it.polimi.ingsw.server.Connection;

import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.FcknSimpleLogger;
import it.polimi.ingsw.commons.Socket.EventHandling.EventDecoder;
import it.polimi.ingsw.commons.Socket.EventHandling.EventEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;
import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.RoundTrack;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

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

    public FcknSimpleLogger logger;

    //COSTRUCTOR

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
        logger = new FcknSimpleLogger(1, true);
    }

    //USERNAME INSERTION

    public void insertUsername() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException {
            currentEvent = eventDecoder.decodeEvent(listenForEvent());
            setChanged();
            notifyObservers(currentEvent);
    }

    //EVENT HANDLING
    public void sendEvent(Event event)
    {
        sendArrayList(eventEncoder.encodeEvent(event));
    }
    public void getEvent() throws IOException, InvalidIntArgumentException {
        currentEvent = eventDecoder.decodeEvent(listenForEvent());
        setChanged();
        notifyObservers(currentEvent);
    }


    //TURN METHODS



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




    public void setPlayersNames(String[]usernames)
    {
        playerNames=usernames;
    }




    //CONNECTION LOST MANAGEMENT
    private void disconnectionManager() throws GenericInvalidArgumentException
    {
        connected = false;
    }

    public boolean connectionCheck()
    {
        //returns true if connected
        return connected;
    }



    //ENCODE/DECODE


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

    //RECEIVE/SEND
    private void receiveMessage() throws IOException
    {
        transformer.simpleDecode(inSocket.readLine());
    }

    private void sendMessage(String cmd, String arg)
    {
        outSocket.println(transformer.simpleEncode(cmd, arg));
        outSocket.flush();;
    }

    private void sendReadyMessage(String s)
    {
        outSocket.println(s);
        outSocket.flush();
    }

    private ArrayList<String> listenForEvent() throws IOException
    {
        ArrayList<String> temp = new ArrayList<String>();
        String msgIN = inSocket.readLine();

        transformer.simpleDecode(msgIN);
        while(!(transformer.getCmd().equals("end")&&transformer.getArg().equals("event")))
        {
            temp.add(msgIN);
            msgIN=inSocket.readLine();

            //DEBUG
            logger.debugLog(msgIN);

            transformer.simpleDecode(msgIN);
        }
        return temp;
    }
    private void sendArrayList(ArrayList<String> toSend)
    {
        for(String go: toSend)
        {
            sendReadyMessage(go);
            logger.debugLog(go);
        }
    }
}

