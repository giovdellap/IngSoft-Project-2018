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


    public ConnectionManager(String ip, int connection) throws IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {

        this.ip=ip;
        this.connection=connection;
        enstablishConnection();
    }

    public void enstablishConnection() throws IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {
        if(connection==1) {
            socketClient = new SocketClient(ip);
            socketClient.addObserver(this);
        }
    }

    //EVENT HANDLING
    public void sendEvent(Event event) throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException {
        socketClient.sendEvent(event);
    }
    public void getEvent() throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, IOException {
        socketClient.getEvent();
    }






    public void update(Observable o, Object arg) {
        setChanged();
        notifyObservers(arg);
    }
}
