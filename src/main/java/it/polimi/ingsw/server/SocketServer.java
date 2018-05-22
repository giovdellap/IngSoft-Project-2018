package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MajorLogger;
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
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class SocketServer implements ConnectionServer {

    public static int PORT = 7777;
    private int counter = 0;
    private boolean firstPlayerHasDone = false;
    private ArrayList<ServerPlayer> players;
    private int numPlayers = 1;
    private String[] playersNames;
    private boolean matchEnd = false;
    private ServerSocket serverSocket;
    private Socket generalSocket;
    private ServerPlayer test;

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;

    public MinorLogger sServerLog;


    public SocketServer() throws GenericInvalidArgumentException {

        sServerLog = new MinorLogger();
        sServerLog.minorLog("SocketServer Logger operative");

        sServerLog.minorLog("start socketserver constructor");
        players = new ArrayList<ServerPlayer>();
        sServerLog.minorLog("end socketserver connection");


    }

    //ACCEPTATION
    public void acceptAndSwitch() throws IOException, GenericInvalidArgumentException {
        serverSocket = new ServerSocket(PORT);
        Socket generalSocket = serverSocket.accept();
        sServerLog.minorLog("Client "+Integer.toString(counter+1)+" accepted on port "+Integer.toString(PORT));

        inSocket = new BufferedReader(new InputStreamReader(generalSocket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(generalSocket.getOutputStream())), true);

        outSocket.println("#switch#$"+Integer.toString(PORT+counter+1)+"$");
        outSocket.flush();
        sServerLog.minorLog("Port "+Integer.toString(PORT+counter+1)+" sent to Client "+Integer.toString(counter+1)+" to switch");

        generalSocket.close();
        serverSocket.close();
        sServerLog.minorLog("Socket on port 7777 closed");
    }

    //INITIALIZATION 1


    public int initializeFPS() throws IOException, GenericInvalidArgumentException {
        sServerLog.minorLog("start intialization 1 player 1");

        acceptAndSwitch();

        serverSocket = new ServerSocket(PORT+counter+1);
        Socket generalSocket = serverSocket.accept();

        sServerLog.minorLog("Client " + Integer.toString(counter+1) + " connected");
        players.add(new ServerPlayer(generalSocket, counter));
        sServerLog.stackLog(players.get(0).sPlayerLog.updateFather());
        players.get(0).sPlayerLog.reinitialize();

        players.get(0).initializeFirst();
        sServerLog.stackLog(players.get(0).sPlayerLog.updateFather());
        players.get(0).sPlayerLog.reinitialize();
        sServerLog.minorLog("Client: "+Integer.toString(counter+1)+" username: "+players.get(0).getUsername());
        numPlayers = players.get(0).getNumPlayers();
        sServerLog.minorLog("numPlayers: "+Integer.toString(numPlayers));

        playersNames = new String[numPlayers];
        playersNames[0] = players.get(0).getUsername();
        for (int i = 1; i < numPlayers; i++)
            playersNames[i] = "";
        counter++;
        return numPlayers;
    }

    public void initializeNPS() throws IOException, GenericInvalidArgumentException {
        String[] temp = new String[counter];
        for (int i = 0; i < counter; i++) {
            temp[i] = playersNames[i];
        }

        acceptAndSwitch();

        serverSocket = new ServerSocket(PORT+counter+1);
        generalSocket = serverSocket.accept();
        sServerLog.minorLog("Client " + Integer.toString(counter+1) + " connected");
        players.add(new ServerPlayer(generalSocket, counter, temp));
        sServerLog.stackLog(players.get(counter).sPlayerLog.updateFather());
        players.get(counter).sPlayerLog.reinitialize();
        players.get(counter).initializeN();
        sServerLog.stackLog(players.get(counter).sPlayerLog.updateFather());
        players.get(counter).sPlayerLog.reinitialize();
        playersNames[counter] = players.get(counter).getUsername();
        sServerLog.minorLog("Client: "+Integer.toString(counter+1)+" username: "+players.get(counter).getUsername());

        counter++;
    }

    public String[] initialization1Phase2() throws GenericInvalidArgumentException {
        sServerLog.minorLog("Start Initialization 1 phase 2");
        playersNames = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            playersNames[i] = players.get(i).getUsername();
            sServerLog.stackLog(players.get(i).sPlayerLog.updateFather());
            players.get(i).sPlayerLog.reinitialize();
        }

        for (int i = 0; i < numPlayers; i++) {
            players.get(i).sendNumPlayers(numPlayers);
            players.get(i).sendPlayersUsernames(playersNames);
            sServerLog.stackLog(players.get(i).sPlayerLog.updateFather());
            players.get(i).sPlayerLog.reinitialize();
        }

        sServerLog.minorLog("End initialization 1 phase 2");
        return playersNames;
    }

    //INITIALIZATION 2

    public void sendPrivObj(int player, int id) throws GenericInvalidArgumentException {
        if(players.get(player).connectionCheck()) {
            players.get(player).sendPrivateObj(id);
            sServerLog.stackLog(players.get(player).sPlayerLog.updateFather());
            players.get(player).sPlayerLog.reinitialize();
            sServerLog.minorLog("Private objective id " + Integer.toString(id) + " sent to player " + Integer.toString(player + 1));
        }
    }

    public void sendSchemes(int player, int id1, int id2) throws GenericInvalidArgumentException {
        if(players.get(player).connectionCheck()) {
            players.get(player).sendScheme(id1);
            players.get(player).sendScheme(id2);
            sServerLog.stackLog(players.get(player).sPlayerLog.updateFather());
            players.get(player).sPlayerLog.reinitialize();
            sServerLog.minorLog("schemes id " + Integer.toString(id1) + ", id " + Integer.toString(id2) + " sent to player " + Integer.toString(player + 1));
        }
    }

    public void sendPubObjs(int id1, int id2, int id3) throws GenericInvalidArgumentException {
        for(int i=0;i<numPlayers;i++)
        {
            if(players.get(i).connectionCheck()) {
                players.get(i).sendPubObjs(id1, id2, id3);
                sServerLog.stackLog(players.get(i).sPlayerLog.updateFather());
                players.get(i).sPlayerLog.reinitialize();
                sServerLog.minorLog("public objectives id " + Integer.toString(id1) + ", " + Integer.toString(id2) + ", " + Integer.toString(id3) + " sent to player " + Integer.toString(i + 1));
            }
        }

    }

    public int[] getSelectedScheme(int player) throws IOException, InvalidinSocketException, GenericInvalidArgumentException {
        if(players.get(player).connectionCheck()) {
            int[] temp = players.get(player).receiveScheme();
            sServerLog.stackLog(players.get(player).sPlayerLog.updateFather());
            players.get(player).sPlayerLog.reinitialize();
            return temp;
        }
        else
        {
            int[] temp = new int[2];
            temp[0]=0;
            temp[1]=0;
            return temp;
        }

    }

    public void sendSchemestoEveryone(SchemeCard[] tempVect) throws InvalidIntArgumentException, GenericInvalidArgumentException {
        for(int i=0;i<numPlayers;i++)
        {
            if(players.get(i).connectionCheck()) {
                players.get(i).sendSchemeVect(tempVect);
                sServerLog.stackLog(players.get(i).sPlayerLog.updateFather());
                players.get(i).sPlayerLog.reinitialize();
                sServerLog.minorLog("All schemes sent to player " + Integer.toString(i + 1));
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

}
