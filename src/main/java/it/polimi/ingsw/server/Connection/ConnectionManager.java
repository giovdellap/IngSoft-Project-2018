package it.polimi.ingsw.server.Connection;

import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;


import java.io.*;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;


public class ConnectionManager extends Observable implements Observer {

    private SocketServer socketServer;
    private SimpleLogger logger;
    private String userName="";
    private Event currentEvent;

    /**
     * ConnectionManager Constructor
     * @param s socket to assign to connection
     * @throws IOException
     */
    public ConnectionManager(Socket s) throws IOException
    {
        logger = new SimpleLogger(0, false);
        socketServer = new SocketServer(s);
        socketServer.addObserver(this);
        logger.log("Connection Manager initialized");

    }

    /**
     * gets player usernames from event
     * @param names array of username strings to check
     * @return a username string
     * @throws IOException
     * @throws InvalidIntArgumentException
     */
    public String getUsername(String[] names) throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean accFlag=false;
        while(!accFlag) {
            userName="";
            socketServer.insertUsername();
            userName = ((UsernameEvent)currentEvent).getUserName();
            logger.log("username received: "+userName);
            if(checkUsername(userName, names))
            {
                currentEvent.validate();
                socketServer.sendEvent(currentEvent);
                logger.log("username "+userName+" confirm sent to the player");
                accFlag = true;
            }
            else
                socketServer.sendEvent(currentEvent);

        }
        return userName;
    }

    /**
     * gets player username from event
     * @return a username string
     * @throws IOException
     * @throws InvalidIntArgumentException
     */
    public String getUsername() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException {
        socketServer.insertUsername();
        userName = ((UsernameEvent)currentEvent).getUserName();
        currentEvent.validate();
        socketServer.sendEvent(currentEvent);
        logger.log("username "+userName+" confirm sent to Player");
        return userName;
    }

    /**
     * checks the uniqueness of a username
     * @param s username to check
     * @param vector usernames from which to check
     * @return true if it's not used, false if name is already in use
     */
    private boolean checkUsername(String s, String[] vector)
    {
        boolean tempFlag=true;
        for(int i=0;i<vector.length;i++)
        {
            if(vector[i].equals(s))
                tempFlag=false;
        }
        return tempFlag;
    }

    /**
     * gets an event from the socket server
     */
    public void getEvent() {
        socketServer.getEvent();
    }

    /**
     * sends an event to socket server
     * @param event event to send to socket server
     */
    public void sendEvent(Event event) throws IOException {

        socketServer.sendEvent(event);
    }

    /**
     * updates on an event
     * @param o observable
     * @param arg object
     */
    public void update(Observable o, Object arg) {
        currentEvent=(Event)arg;
        if(countObservers()>0)
        {
            setChanged();
            notifyObservers(arg);
        }
    }

    public void changeSocket(Socket socket) throws IOException {
        socketServer.changeSocket(socket);
    }

}
