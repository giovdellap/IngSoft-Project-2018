package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MajorLogger;
import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


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

    public MinorLogger sServerLog;

    public SocketServer() {

        sServerLog = new MinorLogger();
        sServerLog.minorLog("SocketServer Logger operative");

        sServerLog.minorLog("start socketserver constructor");
        players = new ArrayList<ServerPlayer>();
        sServerLog.minorLog("end socketserver connection");

    }


    //INITIALIZATION 1


    public int initializeFPS() throws IOException
    {
        sServerLog.minorLog("start intialization 1 player 1");
        serverSocket = new ServerSocket(PORT);
        Socket generalSocket = serverSocket.accept();

        sServerLog.minorLog("Client " + Integer.toString(counter+1) + " connected");
        players.add(new ServerPlayer(generalSocket, counter));
        sServerLog.stackLog(players.get(0).sPlayerLog.updateFather());

        players.get(0).initializeFirst();
        sServerLog.stackLog(players.get(0).sPlayerLog.updateFather());
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

    public void initializeNPS() throws IOException {
        String[] temp = new String[counter];
        for (int i = 0; i < counter; i++) {
            temp[i] = playersNames[i];
        }
        serverSocket = new ServerSocket(PORT + counter);
        generalSocket = serverSocket.accept();
        sServerLog.minorLog("Client " + Integer.toString(counter+1) + " connected");
        players.add(new ServerPlayer(generalSocket, counter, temp));
        sServerLog.stackLog(players.get(counter).sPlayerLog.updateFather());
        players.get(counter).initializeN();
        sServerLog.stackLog(players.get(counter).sPlayerLog.updateFather());
        playersNames[counter] = players.get(counter).getUsername();
        sServerLog.minorLog("Client: "+Integer.toString(counter+1)+" username: "+players.get(counter).getUsername());

        counter++;
    }

    public String[] initialization1Phase2() {
        sServerLog.minorLog("Start Initialization 1 phase 2");
        playersNames = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++) {
            playersNames[i] = players.get(i).getUsername();
            sServerLog.stackLog(players.get(i).sPlayerLog.updateFather());
        }

        for (int i = 0; i < numPlayers; i++) {
        players.get(i).sendPlayersUsernames(playersNames);
        sServerLog.stackLog(players.get(i).sPlayerLog.updateFather());
        }

        sServerLog.minorLog("End initialization 1 phase 2");
        return playersNames;
    }

    //INITIALIZATION 2

    public void sendPrivObj(int player, int id) {
        players.get(player).sendPrivateObj(id);
        sServerLog.stackLog(players.get(player).sPlayerLog.updateFather());
        sServerLog.minorLog("Private objective id "+Integer.toString(id)+" sent to player "+Integer.toString(player+1));
    }

    public void sendSchemes(int player, int id1, int id2) {
        players.get(player).sendScheme(id1);
        players.get(player).sendScheme(id2);
        sServerLog.stackLog(players.get(player).sPlayerLog.updateFather());
        sServerLog.minorLog("schemes id "+Integer.toString(id1)+", id "+Integer.toString(id2)+" sent to player "+Integer.toString(player+1));
    }

    public void sendPubObjs(int id1, int id2, int id3)
    {
        for(int i=0;i<numPlayers;i++) {
            players.get(i).sendPubObjs(id1, id2, id3);
            sServerLog.stackLog(players.get(i).sPlayerLog.updateFather());
            sServerLog.minorLog("public objectives id " + Integer.toString(id1) + ", " + Integer.toString(id2) + ", " + Integer.toString(id3) + " sent to player " + Integer.toString(i+1));
        }
    }

    public int[] getSelectedScheme(int player) throws IOException, InvalidinSocketException {
        int[] temp = players.get(player).receiveScheme();
        sServerLog.stackLog(players.get(player).sPlayerLog.updateFather());
        return temp;
    }

    public void sendSchemestoEveryone(SchemeCard[] tempVect) throws InvalidIntArgumentException {
        for(int i=0;i<numPlayers;i++)
        {
            players.get(i).sendSchemeVect(tempVect);
            sServerLog.stackLog(players.get(i).sPlayerLog.updateFather());
            sServerLog.minorLog("All schemes sent to player "+Integer.toString(i+1));
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
