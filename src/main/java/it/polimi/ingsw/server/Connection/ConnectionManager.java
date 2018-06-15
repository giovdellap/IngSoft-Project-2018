package it.polimi.ingsw.server.Connection;

import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.FcknSimpleLogger;
import it.polimi.ingsw.commons.Listeners.EventListener;
import it.polimi.ingsw.commons.Listeners.MagnificentListenersManager;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.RoundTrack;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeListenerProxy;
import java.io.*;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;


public class ConnectionManager extends Observable implements Observer {

    public static int PORT = 7777;
    private Socket socket;
    private SocketServer socketServer;

    private boolean matchEnd = false;

    private FcknSimpleLogger logger;
    private String userName="";
    private MagnificentListenersManager listenersManager;
    private Event currentEvent;


    public ConnectionManager(Socket s) throws GenericInvalidArgumentException, IOException
    {
        logger = new FcknSimpleLogger(0, false);
        socket=s;
        socketServer = new SocketServer(s);
        socketServer.addObserver(this);
        logger.log("Connection Manager initialized");
        listenersManager = new MagnificentListenersManager();

    }

    //USERNAME ACCEPTATION
    public String getUsername(String[] names) throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {
        //returns player's username

        boolean accFlag=false;
        while(!accFlag) {
            userName="";

            socketServer.insertUsername();
            userName = ((UsernameEvent)currentEvent).getUserName();
            logger.log("username received: "+userName);
            if(checkUsername(userName, names))
            {
                if(socketServer.connectionCheck())
                {
                    currentEvent.validate();
                    socketServer.sendEvent(currentEvent);
                    logger.log("username "+userName+" confirm sent to the player");
                    if(socketServer.connectionCheck())
                        accFlag = true;
                }
                else
                    initializationDiscManager(names.length-1);
            }
        }
        return userName;
    }

    public String getUsername() throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {
        socketServer.insertUsername();
        userName = ((UsernameEvent)currentEvent).getUserName();
        currentEvent.validate();
        socketServer.sendEvent(currentEvent);
        logger.log("username "+userName+" confirm sent to Player");
        return userName;
    }

    private boolean checkUsername(String s, String[] vector)
    {
        //true if ok, false if name already in use
        boolean tempFlag=true;
        for(int i=0;i<vector.length;i++)
        {
            if(vector[i].equals(s))
                tempFlag=false;
        }
        return tempFlag;
    }

    //EVENT HANDLING
    public void handleEvent(Event event) throws IOException, InvalidIntArgumentException {
        socketServer.sendEvent(event);
        socketServer.getEvent();
    }
    public void getEvent() throws IOException, InvalidIntArgumentException {
        socketServer.getEvent();
    }
    public void sendEvent(Event event)
    {
        socketServer.sendEvent(event);
    }



    //DISCONNECTION MANAGEMENT

    private void initializationDiscManager(int index) throws GenericInvalidArgumentException, IOException
    {

    }

    public void update(Observable o, Object arg) {
        currentEvent=(Event)arg;
        if(countObservers()>0)
        {
            setChanged();
            notifyObservers(arg);
        }
    }

}
