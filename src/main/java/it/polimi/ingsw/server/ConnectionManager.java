package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;


import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;


public class ConnectionManager implements ConnectionServer {

    public static int PORT = 7777;
    private int counter = 0;

    private ArrayList<SocketPlayer> players;
    private Boolean[] isConnected;
    private String[] playersNames;
    private boolean matchEnd = false;

    private ServerSocket serverSocket;
    private Socket generalSocket;
    private SocketPlayer test;

    public MinorLogger sServerLog;


    public ConnectionManager() throws GenericInvalidArgumentException, IOException {

        sServerLog = new MinorLogger();
        sServerLog.minorLog("ConnectionManager Logger operative");

        sServerLog.minorLog("start socketserver constructor");
        players = new ArrayList<SocketPlayer>();
        sServerLog.minorLog("end socketserver connection");

        serverSocket = new ServerSocket(PORT);

    }

    //ACCEPTATION

    public ArrayList<String> lobbyCreation() throws IOException, GenericInvalidArgumentException {

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        int maxPlayers=4;

        acceptation();

        //timer
        while (elapsedTime < 120*1000 && players.size()<=maxPlayers)
        {
            acceptation();
            if(elapsedTime>=60*1000&&elapsedTime<90*1000)
                maxPlayers=3;
            if(elapsedTime>=90*1000)
                maxPlayers=2;
            elapsedTime = (new Date()).getTime() - startTime;
            System.out.println(elapsedTime);
            System.out.println(maxPlayers);
        }

        ArrayList<String> temp = new ArrayList<String>();
        for(SocketPlayer pl : players)
            temp.add(pl.getUsername());

        return temp;
    }

    private void acceptation() throws IOException, GenericInvalidArgumentException {
        System.out.println("sono in acceptation");
        generalSocket = serverSocket.accept();
        players.add(new SocketPlayer(generalSocket));
        boolean accFlag=false;
        while(!accFlag) {
            String username = players.get(players.size()-1).insertUsername();       //--------------------
            System.out.println(username);
            System.out.println("check username: "+checkUsername(username));
            if(checkUsername(username))
            {
                if(players.get(players.size()-1).connectionCheck()) {
                    System.out.println("check");
                    players.get(players.size() - 1).confirmUsername();
                    if(players.get(players.size()-1).connectionCheck())
                        accFlag = true;
                    else
                        initializationDiscManager(players.size()-1);
                }
                else
                    initializationDiscManager(players.size()-1);
            }
        }
    }

    private boolean checkUsername(String s)
    {
        //true if ok, false if name already in use
        boolean tempFlag=true;
        for(int i=0;i<players.size()-1;i++)
        {
            if(players.get(i).getUsername().equals(s))
                tempFlag=false;
        }
        System.out.println("tempFlag: "+tempFlag);
        return tempFlag;
    }

    //INITIALIZATION 2

    public void sendPrivObj(int player, int id) throws GenericInvalidArgumentException, IOException {

        players.get(player).sendPrivateObj(id);
        if(!players.get(player).connectionCheck())
            initializationDiscManager(player);

    }

    public void sendSchemes(int player, int id1, int id2) throws GenericInvalidArgumentException, IOException {
        if(players.get(player).connectionCheck()) {
            players.get(player).sendScheme(id1);
            players.get(player).sendScheme(id2);
            sServerLog.stackLog(players.get(player).sPlayerLog.updateFather());
            players.get(player).sPlayerLog.reinitialize();
            sServerLog.minorLog("schemes id " + Integer.toString(id1) + ", id " + Integer.toString(id2) + " sent to player " + Integer.toString(player + 1));
        }
        else
            initializationDiscManager(player);


    }

    public void sendPubObjs(int player, int id1, int id2, int id3) throws GenericInvalidArgumentException, IOException {

        if(players.get(player).connectionCheck()) {
            players.get(player).sendPubObjs(id1, id2, id3);
            sServerLog.stackLog(players.get(player).sPlayerLog.updateFather());
            players.get(player).sPlayerLog.reinitialize();
            sServerLog.minorLog("public objectives id " + Integer.toString(id1) + ", " + Integer.toString(id2) + ", " + Integer.toString(id3) + " sent to player " + Integer.toString(player + 1));
        }
        else
            initializationDiscManager(player);
    }



    public int[] getSelectedScheme(int player) throws IOException, InvalidinSocketException, GenericInvalidArgumentException {
        System.out.println(Boolean.toString(players.get(player).connectionCheck()));
        if(players.get(player).connectionCheck()) {
            int[] temp = players.get(player).receiveScheme();
            sServerLog.stackLog(players.get(player).sPlayerLog.updateFather());
            players.get(player).sPlayerLog.reinitialize();
            return temp;
        }
        else
        {
            initializationDiscManager(player);
            int[] temp = new int[2];
            temp[0]=0;
            temp[1]=0;
            return temp;
        }

    }

    public void sendSchemestoEveryone(SchemeCard[] tempVect) throws InvalidIntArgumentException, GenericInvalidArgumentException, IOException {

        playersNames=new String[players.size()];

        for (int i=0;i<players.size();i++)
        {
            playersNames[i]=players.get(i).getUsername();

        }

        for(SocketPlayer temp : players)
        {
            temp.setPlayersNames(playersNames);
            temp.init1Phase2NumPl(players.size());
        }

        for(SocketPlayer temp : players)

         temp.setPlayersNames(playersNames);


        for(SocketPlayer temp : players)
        {
            temp.init1Phase2NumPl(players.size());
            if(!temp.connectionCheck())
                initializationDiscManager(players.indexOf(temp));
            for(int i=0;i<players.size();i++)
            {
                temp.init1Phase2Player(i, players.get(i).getUsername());
                if(!temp.connectionCheck())
                    initializationDiscManager(players.indexOf(temp));
            }
        }

        for(int i=0;i<players.size();i++)
        {
            if(players.get(i).connectionCheck()) {
                players.get(i).sendSchemeVect(tempVect);
                sServerLog.stackLog(players.get(i).sPlayerLog.updateFather());
                players.get(i).sPlayerLog.reinitialize();
                sServerLog.minorLog("All schemes sent to player " + Integer.toString(i + 1));
            }
            else
                initializationDiscManager(i);
        }

    }

    public void notifyConnectionStatus() throws GenericInvalidArgumentException, IOException {
        for(int i=0; i<players.size();i++)
        {
            if(!players.get(i).connectionCheck())
            {
                for(int n=0;n<players.size();n++)
                {
                    if(players.get(n).connectionCheck())
                        players.get(n).notifyDisconnectedPlayer(i+1);
                }
            }
        }

    }

    public void sendDraftPool(DraftPool draft) {

    }

    public SchemeCard checkMove() {
        return null;

    }

    public int checkTool() {
        return 0;

    }

    public DraftPool checkDraft() {
        return null;

    }

    public void sendCorrectMove(boolean ok) {

    }

    public void sendCorrectToolUsed(boolean ok) {

    }

    public void sendNewToolTokens(int toolId, int tokens) {

    }

    public void schemeSelected(int id, int fb, int player) {

    }

    //DISCONNECTION MANAGEMENT


    private void initializationDiscManager(int index) throws GenericInvalidArgumentException, IOException {
        players.remove(index);
        //for(SocketPlayer temp : players)
            //temp.notifyDisconnectedPlayer(index);
    }


    public ArrayList<String> notifyFatherInit()
    {
        ArrayList<String> temp = new ArrayList<String>();
        for(SocketPlayer pl : players)
            temp.add(pl.getUsername());

        return temp;
    }

}
