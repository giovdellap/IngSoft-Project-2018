package it.polimi.ingsw.server;

import java.io.*;
import java.net.Socket;
import java.lang.StringBuilder.*;
import java.util.ArrayList;

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
    private String[] tempNames;
    private String[] playerNames;

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

    public ServerPlayer(Socket s, int n, String[] tempArr)
    {
        try {
            System.out.println("ServerPlayer 2 check");
            this.socket = s;
            this.id=n;
            this.inSocket = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            this.outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream())), true);
            System.out.println("check pd1");
            tempNames = new String[tempArr.length];
            tempNames = tempArr;
            System.out.println("check pd2");

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
            outSocket.println("<player>$"+Integer.toString(id+1)+"$");
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
                    System.out.println("check first if");
                    if(checkExistingName()) {
                        System.out.println("check secondo if");
                        username = tempArg;
                        System.out.println("check username: "+username);
                    }
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


    public void sendPrivateObj(int id)
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
        boolean flag=true;
        System.out.println("Check existing name 1");
        //checks the username is not already in use
        for(int i=0;i<tempNames.length;i++)
        {
            System.out.println("Check exname 2 i: "+Integer.toString(i));
            System.out.println("tempNames["+Integer.toString(i)+"] = "+tempNames[i]);
            System.out.println("tempArg = "+tempArg);
            if(tempNames[i].equals(tempArg)) {
                flag = false;
                System.out.println("tempNames["+Integer.toString(i)+"] = "+tempNames[i]);
                System.out.println("tempArg = "+tempArg);
            }
        }
        System.out.println("check exname before flag "+Boolean.toString(flag));
        return flag;

    }

    public void sendPlayersUsernames(String[] temp)
    {
        numPlayers=temp.length;
        playerNames=new String[numPlayers];
        playerNames=temp;
        for(int i=0;i<numPlayers;i++)
        {
            if(id!=i+1) {
                outSocket.println("<player>$" + Integer.toString(i + 1) + "$");
                outSocket.flush();
                outSocket.println("<username>$"+playerNames[i]+"$");
                outSocket.flush();
            }
        }
    }

    public void sendScheme(int id)
    {
        outSocket.println("<scheme>$"+Integer.toString(id)+"$");
        outSocket.flush();
    }






}

