package it.polimi.ingsw.server.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class GeneralServer
{
    ServerSocket serverSocket;
    Socket generalSocket;

    public GeneralServer() throws IOException {
        serverSocket = new ServerSocket(7777);
        generalSocket = new Socket();
    }

    public Socket accept() throws IOException {
        generalSocket = serverSocket.accept();
        return generalSocket;
    }
}
