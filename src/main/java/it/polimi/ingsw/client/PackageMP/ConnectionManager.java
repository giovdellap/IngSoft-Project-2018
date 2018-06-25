package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.Connection.RMIClient;
import it.polimi.ingsw.client.PackageMP.Connection.SocketClient;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.RoundTrackMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;

import java.io.IOException;
import java.util.ArrayList;
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
     * @throws it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException
     * @throws it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException
     */
    public ConnectionManager(String ip, int connection) throws IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {

        this.ip=ip;
        this.connection=connection;
        establishConnection();
    }

    /**
     * establishes a connection and adds observer
     * @throws IOException
     * @throws it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException
     * @throws it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException
     */
    public void establishConnection() throws IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {
        if(connection==1) {
            socketClient = new SocketClient(ip);
            socketClient.addObserver(this);
        }
    }

    /**
     * sends event to socketClient
     * @param event
     * @throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException
     */
    public void sendEvent(Event event) throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException {
        socketClient.sendEvent(event);
    }

    /**
     * gets event from socketClient
     * @throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException
     * @throws IOException
     */
    public void getEvent() throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, IOException, GenericInvalidArgumentException, InvalidIntArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {
        socketClient.getEvent();
    }






    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
