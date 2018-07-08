package it.polimi.ingsw.server.Connection;

import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

public class GeneralServer
{
    private ServerSocket serverSocket;
    private Socket generalSocket;
    private String username="";
    private int port;

    /**
     * GeneralServer Constructor
     * @throws IOException
     */
    public GeneralServer(int port) throws IOException {
        this.port = port;
        serverSocket = new ServerSocket(port);
    }

    /**
     * accepts a connection
     * @return a connected socket
     * @throws IOException
     */
    public Socket accept(int seconds) throws IOException {
        serverSocket.setSoTimeout(seconds*1000);
        try {
            generalSocket=new Socket();
            System.out.println("Waiting for clients..\n");
            System.out.println(serverSocket.isClosed()+" accept");
            generalSocket = serverSocket.accept();
        }
        catch (SocketTimeoutException e){
            generalSocket=null;
            System.out.println("Connection timed out\n");
            return generalSocket;
        }

        return generalSocket;
    }

    /**
     * manages reconnection
     * @param usernames
     * @return
     * @throws IOException
     */
    public Socket reconnection(ArrayList<String> usernames) throws IOException {
        try {
            generalSocket=new Socket();
            System.out.println("Waiting for clients..(rec)\n");
            System.out.println(serverSocket.isClosed()+" reconn");
            System.out.println(serverSocket.toString());
            generalSocket = serverSocket.accept();
        }
        catch (SocketTimeoutException e){
            System.out.println("server chiuso(rec)");
            generalSocket.close();
            System.out.println("Connection timed out(rec)\n");
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
