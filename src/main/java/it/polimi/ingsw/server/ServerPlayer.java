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
    private boolean firstPlayerHasDone;
    private String[] usernameVector;

    public ServerPlayer(Socket s, int n)
    {
        try {
            System.out.println("check del ServerPlayer");
            socket = s;
            id=n;
            inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
            System.out.println("check fine costruttore serverplayer");
            numPlayers=1;


        } catch(Exception ex)
        {
            System.out.println("Exception ex");
        }
    }

    public ServerPlayer(Socket s, int n, String[] usernames)
    {
        try {
            System.out.println("ServerPlayer 2 check");
            this.socket = s;
            this.id=n;
            this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
            usernameVector=new String[usernames.length];
            usernameVector=usernames;

        } catch(Exception ex)
        {
            System.out.println("Exception ex");
        }
    }



    public void initializeFirst() {
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
                }
            }

            while(numPlayers==1)
            {
                outSocket.println("<insert>$numplayers$");
                outSocket.flush();
                msg = inSocket.readLine();
                simpleDecode(msg);
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
                    if(checkExistingName()) {
                        System.out.println("check secondo if");
                        username = tempArg;
                        System.out.println("check username = tempArg");
                        outSocket.println("<confirm>$username$");
                        outSocket.flush();
                    }
                        System.out.println("check 10");
                }
                outSocket.println("<wait>$players$");


            }

        } catch(Exception e) {
            try {
                System.out.println("Exception e");
                socket.close();
                System.out.println("Socket closed");
            } catch(Exception ex)
            {}
        }
    }


    public void setPrivateObj(int id)
    {
        outSocket.println("<privateObj>$id$");
        outSocket.flush();
        outSocket.println("<id>$"+Integer.toString(id)+"$");
        outSocket.flush();


    }


    public int getNumPlayers()
    {
        return numPlayers;
    }

    public boolean hasFPDone()
    {
        return firstPlayerHasDone;
    }

    public String getUsername()
    {
        return username;
    }

    public boolean checkExistingName()
    {
        System.out.println("Check existing name 1");
        //checks the username is not already in use
        for(int i=0;i<numPlayers;i++)
        {
            System.out.println("for"+Integer.toString(i));
            if (usernameVector[i]==tempArg)
                return false;
        }
        return true;
    }





}

