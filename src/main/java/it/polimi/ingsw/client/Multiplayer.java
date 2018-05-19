package it.polimi.ingsw.client;

import it.polimi.ingsw.client.Connection.SocketClient;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.ModelComponentsMP.ToolsDeckMP;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.IOException;


public class Multiplayer {

    private int round;
    private int turn;
    private int players;
    private String[] playersNames;

    SocketClient cSocket;

    public MinorLogger loggerMP;

    public Multiplayer() throws GenericInvalidArgumentException, IOException {

        loggerMP = new MinorLogger();
        loggerMP.minorLog("MultiPlayer logger operative");

        cSocket = new SocketClient();
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();

        players = cSocket.getNumPlayers();
        String[] playerNames = cSocket.getPlayers();
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();


    }



    public void play() {

        // metodo per giocare
    }


    public void myTurn() {

        // gestisce il mio turno
    }


    public void oppTurn() {

        // gestisce il turno dell'avversario
    }


    public void endOfTheGame() {

        // gestisce la fine del gioco
    }


    public void takeTokens(int n) {

        // ottiene i token
    }

}
