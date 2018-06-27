package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.Connection.RMIClient;
import it.polimi.ingsw.client.PackageMP.Connection.SocketClient;
import it.polimi.ingsw.commons.Events.Event;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class ConnectionManager extends Observable implements Observer
{

    private String ip;
    private int connection;
    private SocketClient socketClient;
    private RMIClient rmiClient;
    private String username;

    /**
     * ConnectionManager Constructor
     * @param ip ip to set
     * @param connection type of connection
     * @throws IOException
     * @throws it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException
     * @throws it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException
     */
    public ConnectionManager(String ip, int connection) throws IOException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException {

        this.ip=ip;
        this.connection=connection;
        establishConnection();
    }

    /**
     * establishes a connection and adds observer
     * @throws IOException
     * @throws it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException
     * @throws it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException
     */
    public void establishConnection() throws IOException {
        if(connection==1) {
            socketClient = new SocketClient(ip);
            socketClient.addObserver(this);
        }
    }

    /**
     * sends event to socketClient
     * @param event
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     */
    public void sendEvent(Event event) throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException {
        socketClient.sendEvent(event);
    }

    /**
     * gets event from socketClient
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     * @throws IOException
     */
    public void getEvent() throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, IOException, GenericInvalidArgumentException, InvalidIntArgumentException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException {
        socketClient.getEvent();
    }






    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
