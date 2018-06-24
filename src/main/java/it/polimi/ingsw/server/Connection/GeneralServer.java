package it.polimi.ingsw.server.Connection;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;

public class GeneralServer
{
    ServerSocket serverSocket;
    Socket generalSocket;

    /**
     * GeneralServer Constructor
     * @throws IOException
     */
    public GeneralServer() throws IOException {
        generalSocket = new Socket();

        serverSocket = new ServerSocket(7777);
        serverSocket.setSoTimeout(25*1000);
    }

    /**
     * accepts a connection
     * @return a connected socket
     * @throws IOException
     */
    public Socket accept() throws IOException {
        try {
            generalSocket=new Socket();
            System.out.println("Waiting for clients..\n");
            generalSocket = serverSocket.accept();
        }
        catch (SocketTimeoutException e){
            generalSocket=null;
            System.out.println("Connection timed out\n");
            return generalSocket;
        }

        return generalSocket;
    }


}
