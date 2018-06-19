package it.polimi.ingsw.commons;

import it.polimi.ingsw.server.Connection.SocketServer;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ConnectionThread implements Runnable {

    private ServerSocket serverSocket;
    private Socket socket;

    public void run() {

        try {
            serverSocket = new ServerSocket(7777);
            socket = serverSocket.accept();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Socket getSocket()
    {
        return socket;
    }
}
