package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


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

    public SocketServer() {
        System.out.println("start socketserver constructor");
        players = new ArrayList<ServerPlayer>();
        System.out.println("end socketserver connection");
    }


    //INITIALIZATION 1


    public int initializeFPS() throws IOException
    {
        System.out.println("start intialization 1 player 1");
        serverSocket = new ServerSocket(PORT);
        Socket generalSocket = serverSocket.accept();

        System.out.println("Client " + Integer.toString(counter+1) + " connected");
        players.add(new ServerPlayer(generalSocket, counter));

        players.get(0).initializeFirst();
        System.out.println("Client: "+Integer.toString(counter+1)+" username: "+players.get(0).getUsername());
        numPlayers = players.get(0).getNumPlayers();
        System.out.println("numPlayers: "+Integer.toString(numPlayers));

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
        System.out.println("Client " + Integer.toString(counter+1) + " connected");
        players.add(new ServerPlayer(generalSocket, counter, temp));
        players.get(counter).initializeN();
        playersNames[counter] = players.get(counter).getUsername();
        System.out.println("Client: "+Integer.toString(counter+1)+" username: "+players.get(counter).getUsername());

        counter++;
    }

    public void initialization1Phase2() {
        System.out.println("Start Initialization 1 phase 2");
        playersNames = new String[numPlayers];
        for (int i = 0; i < numPlayers; i++)
            playersNames[i] = players.get(i).getUsername();
        for (int i = 0; i < numPlayers; i++) {
            players.get(i).sendPlayersUsernames(playersNames);
        }
        System.out.println("End initialization 1 phase 2");
    }

    //INITIALIZATION 2

    public void sendPrivObj(int player, int id) {
        players.get(player).sendPrivateObj(id);
        System.out.println("Private objective id "+Integer.toString(id)+" sent to player "+Integer.toString(player+1));
    }

    public void sendSchemes(int player, int id1, int id2) {
        players.get(player).sendScheme(id1);
        players.get(player).sendScheme(id2);
        System.out.println("schemes id "+Integer.toString(id1)+", id "+Integer.toString(id2)+" sent to player "+Integer.toString(player+1));
    }

    public void sendPubObjs(int id1, int id2, int id3)
    {
        for(int i=0;i<numPlayers;i++) {
            players.get(i).sendPubObjs(id1, id2, id3);
            System.out.println("public objectives id " + Integer.toString(id1) + ", " + Integer.toString(id2) + ", " + Integer.toString(id3) + " sent to player " + Integer.toString(i+1));
        }
    }

    public int[] getSelectedScheme(int player) throws IOException, InvalidinSocketException {
        return players.get(player).receiveScheme();
    }

    public void sendSchemestoEveryOne(SchemeCard[] tempVect) throws InvalidIntArgumentException {
        for(int i=0;i<numPlayers;i++)
        {
            players.get(i).sendSchemeVect(tempVect);
            System.out.println("All schemes sent to player "+Integer.toString(i+1));
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
