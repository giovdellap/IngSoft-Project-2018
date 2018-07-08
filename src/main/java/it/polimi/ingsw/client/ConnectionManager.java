package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Connection.SocketClient;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.SimpleLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class ConnectionManager extends Observable implements Runnable, Observer
{

    private String ip;
    private SocketClient socketClient;
    private String username;
    private Event toSend;
    private Event toReceive;
    private State state;
    private boolean stopSleeping;
    private SimpleLogger logger;

    private ArrayList<String> settings;

    /**
     * ConnectionManager Constructor
     * @throws IOException
     */
    public ConnectionManager(ArrayList<String> settings) throws IOException
    {
        this.settings=settings;
        this.ip=settings.get(3);
        establishConnection();
        logger = new SimpleLogger(1, Boolean.parseBoolean(settings.get(0)));
    }

    /**
     * run() method for sending, receiving and handling events
     * needs a state and eventually the event to send
     */
    public void run() {
        logger.debugLog("connection manager started: "+state);
        if(state==State.SEND)
        {
            try
            {
                logger.debugLog("SENDING...");
                socketClient.sendEvent(toSend);
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            }
        }
        if(state==State.RECEIVE)
        {
            logger.debugLog("RECEIVING...");
            stopSleeping=false;
            while(!stopSleeping)
            {
                try
                {
                    if(socketClient.ready())
                    {
                        logger.debugLog("Socket client receiving...");
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(socketClient);
                        ((ExecutorService) executor).shutdown();
                        ((ExecutorService) executor).awaitTermination(5, TimeUnit.SECONDS);
                        stopSleeping=true;

                    }
                    else {
                        Thread.sleep(1000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logger.debugLog("RECEIVE state terminated");
        }
        if(state==State.SENDANDRECEIVE)
        {
            logger.debugLog("Start SENDANDRECEIVE state");
            try
            {
                logger.debugLog("Sending...");
                socketClient.sendEvent(toSend);
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            }

            logger.debugLog("Data sent, start receiving");

            try {
                socketClient.getEvent();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (GenericInvalidArgumentException e) {
                e.printStackTrace();
            }
            logger.debugLog("Receiving phase ended");


        }
        if(state==State.RECEIVEANDSEND)
        {
            logger.debugLog("Start RECEIVEANDSEND state");
            stopSleeping=false;
            while(!stopSleeping)
            {
                logger.debugLog("Start receiving...");
                try
                {
                    if(socketClient.ready())
                    {
                        logger.debugLog("Socket client Receiving...");
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(socketClient);
                        ((ExecutorService) executor).shutdown();
                        ((ExecutorService) executor).awaitTermination(8, TimeUnit.SECONDS);
                        stopSleeping=true;

                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            logger.debugLog("Event received, start sending...");

            try {
                logger.debugLog("Sending...");
                socketClient.sendEvent(toReceive);
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            }
        }
        if(state==State.SLEEP) {
            logger.debugLog("Start SLEEP state");
            stopSleeping=false;
            while(!stopSleeping)
            {
                try {
                    if(socketClient.ready())
                    {
                        logger.debugLog("Socket client receiving...");
                        Executor executor = Executors.newSingleThreadExecutor();
                        executor.execute(socketClient);
                        ((ExecutorService) executor).shutdown();
                        ((ExecutorService) executor).awaitTermination(5, TimeUnit.SECONDS);
                        stopSleeping=true;
                    }
                    else {
                        Thread.sleep(2000);
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            logger.debugLog("SLEEP ended");
        }
        logger.log("connection manager ended: "+state);

    }


    /**
     * establishes a connection and adds observer
     * @throws IOException
     */
    public void establishConnection() throws IOException
    {
        socketClient = new SocketClient(settings);
        socketClient.addObserver(this);
    }

    /**
     * sets connectionmanager state
     * @param state
     */
    public void setState(State state)
    {
        this.state=state;
    }

    /**
     * sets event to send
     * @param toSend
     */
    public void setEvent(Event toSend)
    {
        this.toSend=toSend;
    }

    public enum State
    {
        SEND,
        RECEIVE,
        SENDANDRECEIVE,
        RECEIVEANDSEND,
        SLEEP,
    }


    public void update(Observable o, Object arg)
    {
        if(o==socketClient)
        {
            toReceive = (Event) arg;
            logger.debugLog("Event received: "+((Event)arg).getType());
            setChanged();
            notifyObservers(arg);
            logger.debugLog("update ended");

        }
    }

    public String toString()
    {
        return "connection";
    }

    public void stopSleep()
    {
        logger.debugLog("DRIIIIIIN");
        stopSleeping=true;
    }
}
