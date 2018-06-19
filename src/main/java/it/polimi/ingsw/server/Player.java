package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.server.Connection.ConnectionManager;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

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


    public Player(Socket socket) throws IOException, GenericInvalidArgumentException {
        connectionManager = new ConnectionManager(socket);
        connectionManager.addObserver(this);
    }
    public void getUsername(String[] names) throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {
        name = connectionManager.getUsername(names);
    }
    public void getUsername() throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {
        name = connectionManager.getUsername();
    }

    //EVENT HANDLING
    public void handleEvent(Event event) throws IOException, InvalidIntArgumentException {
        //send-get events
        connectionManager.handleEvent(event);
    }
    public void getEvent() throws IOException, InvalidIntArgumentException {
        //gets events
        connectionManager.getEvent();
    }
    public void sendEvent(Event event)
    {
        connectionManager.sendEvent(event);
    }



    //GET METHODS
    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }

    public boolean getIPlayedFirstMove() {
        return iPlayedFirstMove;
    }

    public void setIPlayedFirstMove(boolean iPlayedFirstMove) {
        this.iPlayedFirstMove = iPlayedFirstMove;
    }

    //ACTIVATION
    public void activate()
    {
        active=true;
    }

    public void deactivate()
    {
        active=false;
    }

    public boolean isActive() {
        return active;
    }


    //TOKENS
    public int getTokens() { return tokens; }

    public void setTokens(int n)
    {
        tokens=n;
    }

    public void usedTokens(int num)
    {
        //player used num tokens
        tokens=tokens-num;
    }

    public boolean canUse(int toDecrease)
    {
        //can player use those tokens?
        if(tokens-toDecrease>=0)
            return true;
        else return false;
    }

    //TOKENS
    public void useEight() { eightUsed=true; }

    public boolean checkEight() { return eightUsed; }

    public void roundReset() {
        //resets eightUsed when round changes
        eightUsed=false;
    }

    public boolean isDisconnected() {
        return isDisconnected;
    }

    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }


}
