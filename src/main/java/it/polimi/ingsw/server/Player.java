package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Events.Disconnection.DisconnectionEvent;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.server.Connection.ConnectionManager;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.io.IOException;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;

public class Player extends Observable implements Observer
{
    //DATA
    private int id;// player id
    private String name;// player username
    private boolean active;//playing player flag
    private int tokens;//player tokens counter
    private boolean eightUsed;//player ToolCard 8 usage flag
    private boolean isDisconnected=false;
    private boolean iPlayedFirstMove = false;

    //MANAGERS
    private ConnectionManager connectionManager;

    /**
     * Player Constructor
     * @param socket socket to insert into connectionManager
     * @throws IOException
     * @throws GenericInvalidArgumentException
     */
    public Player(Socket socket) throws IOException, GenericInvalidArgumentException {
        connectionManager = new ConnectionManager(socket);
        connectionManager.addObserver(this);
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
     * @throws IOException
     * @throws InvalidIntArgumentException
     */
    public void getEvent() throws IOException, InvalidIntArgumentException {
        connectionManager.getEvent();
    }

    /**
     *
     * @param event event to send to connectionManager
     * @throws InvalidIntArgumentException
     */
    public void sendEvent(Event event) throws InvalidIntArgumentException, IOException {
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
     * activates a player setting active to true
     */
    public void activate()
    {
        active=true;
    }

    /**
     * deactivates a player setting active to false
     */
    public void deactivate()
    {
        active=false;
    }

    /**
     *
     * @return if the player is active or not
     */
    public boolean isActive() {
        return active;
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
     *
     * @param num number of tokens used, decreased to total
     */
    public void usedTokens(int num)
    {
        //player used num tokens
        tokens=tokens-num;
    }

    /**
     * sets boolean eightUsed to true, when you use ToolCard 8
     */
    public void useEight() { eightUsed=true; }

    /**
     *
     * @return eightUsed
     */
    public boolean checkEight() { return eightUsed; }

    /**
     * sets boolean eightUsed to false, when the round ends
     */
    public void roundReset() {
        eightUsed=false;
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
    public void update(Observable o, Object arg) {
        if(((Event)arg).getType().equals("DisconnectionEvent")) {
            isDisconnected = true;
            ((DisconnectionEvent)arg).setId(id);
        }
        setChanged();
        notifyObservers(arg);
    }

    /**
     * @param id
     * sets id to param
     */
    public void setId(int id)
    {
        this.id = id;
    }

    public void changeSocket(Socket socket) throws IOException {
        connectionManager.changeSocket(socket);
        isDisconnected=false;
    }


}
