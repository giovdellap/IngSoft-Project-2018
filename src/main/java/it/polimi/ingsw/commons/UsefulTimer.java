package it.polimi.ingsw.commons;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class UsefulTimer implements Observer {

    private TimerThread timer;
    private ConnectionThread connection;
    private PrintWriter outSocket;


    private Socket socket;

    private ExecutorService executorService;



    public void tryConnection(String name) throws IOException {
        timer = new TimerThread();
        connection = new ConnectionThread();
        timer.addObserver(this);

        ExecutorService executorService = Executors.newCachedThreadPool();

        executorService.execute(timer);
        executorService.execute(connection);



    }

    public void acceptUsers()
    {

    }

    public Socket getLastUser()
    {
        return null;
    }

    public void update(Observable o, Object arg) {
        socket = connection.getSocket();
        executorService.shutdown();
        System.out.println("update");
    }
}
