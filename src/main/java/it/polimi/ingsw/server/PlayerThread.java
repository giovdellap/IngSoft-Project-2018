package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Events.Disconnection.DisconnectionEvent;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.TurnEvent;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.server.Connection.ConnectionManager;
import it.polimi.ingsw.server.Connection.GeneralServer;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

import static it.polimi.ingsw.server.PlayerThread.State.*;

public class PlayerThread extends Observable implements Observer, Runnable
{
    //DATA
    private int id;// player id
    private String name;// player username
    private int tokens;//player tokens counter
    private boolean isDisconnected=false;
    private boolean iPlayedFirstMove = false;

    //SCHEME SELECTION REP
    private int[] tempSchemes;
    private boolean schemeSelected;
    private int selected;

    //MANAGERS
    private ConnectionManager connectionManager;
    private GeneralServer server;

    private State state;
    private Event currentEventSend;
    private Event currentEventReceive;
    private Boolean stop;

    private SimpleLogger logger;

    /**
     * Player Constructor
     * @param socket socket to insert into connectionManager
     * @throws IOException
     */
    public PlayerThread(Socket socket) throws IOException {
        connectionManager = new ConnectionManager(socket);
        connectionManager.addObserver(this);
        logger= new SimpleLogger(0,false );
    }


    public void run()
    {
        System.out.println("Player "+Integer.toString(id)+" "+state);

        stop=false;

        if(!isDisconnected) {
            if (state == SEND) {
                try {
                    logger.log("Player "+Integer.toString(id)+" sending event "+currentEventSend.getType());
                    if (currentEventSend.getType().equals("TurnEvent"))
                        logger.log("TurnEvent send active: "+Integer.toString(((TurnEvent)currentEventSend).getActive()));
                    connectionManager.sendEvent(currentEventSend);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (state == RECEIVE) {
                try {
                    while (!stop && !connectionManager.isReady())
                        Thread.sleep(100);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if(!stop)
                    connectionManager.getEvent();
                logger.log("Player "+Integer.toString(id)+" receiving event "+currentEventSend.getType());
                if (currentEventSend.getType().equals("TurnEvent"))
                    logger.log("TurnEvent receive active: "+Integer.toString(((TurnEvent)currentEventSend).getActive()));

            }

            if (state == HANDLE) {
                try {
                    logger.log("Player "+Integer.toString(id)+" sending event "+currentEventSend.getType());
                    if (currentEventSend.getType().equals("TurnEvent"))
                        logger.log("TurnEvent send active: "+Integer.toString(((TurnEvent)currentEventSend).getActive()));
                    connectionManager.sendEvent(currentEventSend);

                    try {
                        while (!stop && !connectionManager.isReady())
                            Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if(!stop)
                        connectionManager.getEvent();
                    logger.log("Player "+Integer.toString(id)+" receiving event "+currentEventSend.getType());
                    if (currentEventSend.getType().equals("TurnEvent"))
                        logger.log("TurnEvent receive active: "+Integer.toString(((TurnEvent)currentEventSend).getActive()));

                } catch (IOException e) {
                    e.printStackTrace();

                }
            }

            if (state == SCHEMESELECTION) {
                try {
                    connectionManager.getEvent();
                    if (selected == tempSchemes[0] || selected == tempSchemes[1])
                        schemeSelected = true;
                    while (!schemeSelected) {
                        connectionManager.getEvent();
                        if (selected == tempSchemes[0] || selected == tempSchemes[1])
                            schemeSelected = true;
                    }
                    currentEventSend = currentEventReceive;
                    currentEventSend.validate();
                    connectionManager.sendEvent(currentEventSend);
                    if(!isDisconnected) {
                        setChanged();
                        notifyObservers(currentEventReceive);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        else {
            try {
                logger.log("player "+Integer.toString(id)+" trying reconnection..");
                tryReconnection();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        System.out.println("Fine run thread");

    }

    public void stops()
    {
        stop=true;
    }


    public void setState(State state)
    {
        this.state = state;
    }

    public void setEvent(Event event)
    {
        currentEventSend = event;
        System.out.println(event.getType());
    }

    public Event getLastEventSent()
    {
        return currentEventSend;
    }

    public Event getLastEventReceive()
    {
        return currentEventReceive;
    }

    public void setTempSchemes(int id1, int id2) {
        tempSchemes = new int[2];
        tempSchemes[0] = id1;
        tempSchemes[1] = id2;
    }

    /**
     *
     * @param names gets string vector of all usernames from connectionManager
     * @throws IOException
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public void getUsername(String[] names) throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {
        name = connectionManager.getUsername(names);
    }

    /**
     * gets username from connectionManager
     * @throws IOException
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public void getUsername() throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {
        name = connectionManager.getUsername();
    }

    /**
     * gets event from connectionManager
     */
    public void getEvent() {
        connectionManager.getEvent();
    }

    /**
     *
     * @param event event to send to connectionManager
     */
    public void sendEvent(Event event) throws IOException {
        connectionManager.sendEvent(event);

    }


    /**
     *
     * @return player's name
     */
    public String getName()
    {
        return name;
    }

    /**
     *
     * @return player's id
     */
    public int getId()
    {
        return id;
    }

    /**
     *
     * @return iPlayedFirst
     */
    public boolean getIPlayedFirstMove() {
        return iPlayedFirstMove;
    }

    /**
     *
     * @param iPlayedFirstMove sets a boolean if the player played his first move
     */
    public void setIPlayedFirstMove(boolean iPlayedFirstMove) {
        this.iPlayedFirstMove = iPlayedFirstMove;
    }


    /**
     *
     * @return number of tokens
     */
    public int getTokens() { return tokens; }

    /**
     *
     * @param n number of tokens to set
     */
    public void setTokens(int n)
    {
        tokens=n;
    }


    /**
     * @return boolean if the player is disconnected or not
     */
    public boolean isDisconnected() {
        return isDisconnected;
    }

    /**
     * @param o Observable
     * @param arg Object
     */
    public void update(Observable o, Object arg)
    {
        currentEventReceive = (Event) arg;

        if(((Event)arg).getType().equals("SchemeSelectionEvent"))
            selected=((SchemeSelectionEvent)arg).getId();
        else {

            if (((Event) arg).getType().equals("DisconnectionEvent")) {
                System.out.println("\nDISCONNECTED");
                System.out.println("Disconnected thread: " + Integer.toString(id));
                isDisconnected = true;
                ((DisconnectionEvent) arg).setId(id);
            }

            System.out.println("arg pl thread" + ((Event) arg).getType());
            setChanged();
            notifyObservers(arg);
        }
    }

    /**
     * @param id
     * sets id to param
     */
    public void setId(int id)
    {
        this.id = id;
    }

    //DISCONNECTION MANAGEMENT
    public void setGeneralServer(GeneralServer server)
    {
        this.server=server;
    }

    public void tryReconnection() throws IOException {
        server.accept(4);
    }

    public void changeSocket(Socket socket) throws IOException
    {
        connectionManager.changeSocket(socket);
        isDisconnected=false;
    }

    public void resetDisconnected()
    {
        isDisconnected=false;
    }


    public enum State {
        SEND,
        RECEIVE,
        HANDLE,
        SCHEMESELECTION,
    }

    public String getState()
    {
        System.out.println();
        return state.toString();
    }
}
