package it.polimi.ingsw.server.Connection;

import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class GeneralServer
{
    private ServerSocket serverSocket;
    private Socket generalSocket;
    private String username="";

    /**
     * GeneralServer Constructor
     * @throws IOException
     */
    public GeneralServer(int sec) throws IOException {
        generalSocket = new Socket();

        serverSocket = new ServerSocket(7777);
        serverSocket.setSoTimeout(sec*1000);
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

    public Socket reconnection(ArrayList<String> usernames) throws IOException {
        try {
            generalSocket=new Socket();
            System.out.println("Waiting for clients..\n");
            generalSocket = serverSocket.accept();
        }
        catch (SocketTimeoutException e){
            generalSocket.close();
            System.out.println("Connection timed out\n");
            return generalSocket;
        } catch (IOException e) {
            e.printStackTrace();
        }

        BufferedReader inSocket = new BufferedReader(new InputStreamReader(generalSocket.getInputStream()));
        PrintWriter outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(generalSocket.getOutputStream())), true);
        SocketProtocolTransformer transformer = new SocketProtocolTransformer();
        String msgIN;
        msgIN = inSocket.readLine();
        transformer.simpleDecode(msgIN);
        if(transformer.getArg().equals("UsernameEvent"))
        {
            msgIN = inSocket.readLine();
            transformer.simpleDecode(msgIN);
            username = transformer.getArg();
            msgIN=inSocket.readLine();
        }
        for(int i=0;i<usernames.size();i++)
            if(usernames.get(i).equals(username))
                return generalSocket;

        inSocket.close();
        outSocket.close();
        return null;
    }

    public String getUsername() {
        return username;
    }

    public void close() throws IOException {
        serverSocket.close();
    }
}
