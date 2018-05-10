package it.polimi.ingsw.server;

import java.io.*;
import java.net.Socket;
import java.lang.StringBuilder.*;

public class ServerPlayer extends Thread
{

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private int id;
    private String username="";
    private String tempCmd="";
    private String tempArg="";
    private String msg;
    private int numPlayers=1;

    public ServerPlayer(Socket s, int n)
    {
        try {
            System.out.println("check del ServerPlayer");
            this.socket = s;
            this.id=n;
            this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
            this.start();

        } catch(Exception ex)
        {
            System.out.println("Exception ex");
        }
    }

    public void run()
    {
        if(id==1)
            initializeFirst();
        else
            initializeN();
    }

    private void initializeFirst() {
        try {
            outSocket.println("<player>$1$");
            outSocket.flush();

            while(username=="")
            {
                outSocket.println("<insert>$username$");
                outSocket.flush();
                msg = inSocket.readLine();
                simpleDecode(msg);
                System.out.println("check 13");
                if (tempCmd.equals("username"))
                {
                    System.out.println("check 11");

                    username = tempArg;
                    System.out.println("check 10");
                    outSocket.println("<confirm>$username$");
                    outSocket.flush();
                    //TO INSERT: add username to playerlist
                }
            }

            while(numPlayers==1)
            {
                outSocket.println("<insert>$numplayers$");
                System.out.println("Check a");
                outSocket.flush();
                msg = inSocket.readLine();
                System.out.println("Check b");
                simpleDecode(msg);
                System.out.println("Check c");
                if(tempCmd.equals("numplayers"))
                {
                    System.out.println("Check d");
                    int n = Integer.parseInt(tempArg);
                    System.out.println("Check e");
                    if(n>1&&n<5)
                        numPlayers=n;
                }
            }


            outSocket.println("<wait>$players$");


        } catch(Exception e) {
            try {
                System.out.println("Exception e");
                socket.close();
                System.out.println("Socket closed");
            } catch(Exception ex)
            {}
        }
    }

    private void simpleDecode(String tempStr)
    {
        //parts the message into command and argument
        tempCmd="";
        tempArg="";
        int index=0;
        if(tempStr.charAt(index)=='<')
        {
            index++;
            while (tempStr.charAt(index) != '>')
            {
                tempCmd+=Character.toString(tempStr.charAt(index));
                System.out.println(tempCmd);
                index++;
            }
            System.out.println(tempCmd);
            index++;
            if(tempStr.charAt(index)=='$');
            {
                index++;
                while(tempStr.charAt(index)!='$')
                {
                    tempArg+=Character.toString(tempStr.charAt(index));
                    index++;
                    System.out.println(tempArg);
                }
            }
            System.out.println(tempArg);

        }
    }

    public void initializeN()
    {

    }

    public int getNumPlayers()
    {
        return numPlayers;
    }
}
