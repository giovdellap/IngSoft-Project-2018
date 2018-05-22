package it.polimi.ingsw.client.MultiPackage;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Connection.SocketClient;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.MultiPackage.ModelMP;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.IOException;


public class Multiplayer {

    private int round;
    private int turn;
    private int players;
    private String[] playersNames;
    private int[] playersTokens;

    private SocketClient cSocket;

    public MinorLogger loggerMP;

    private ModelMP modelSession;

    public Multiplayer() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException {

        //INITIALIZATION 1: PHASE 1

        loggerMP = new MinorLogger();
        loggerMP.minorLog("MultiPlayer logger operative");

        modelSession = new ModelMP();
        loggerMP.minorLog("modelSession started and initialized");

        cSocket = new SocketClient();
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();

        //INITIALIZATION 1: PHASE 2

        players = cSocket.getNumPlayers();
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();

        String[] playerNames = cSocket.getPlayers();
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();

        //INITIALIZATION 2

        modelSession.setMyPrivObj(cSocket.getPrivObj());
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();

        int[] schemesID = cSocket.getSchemes();
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();

        modelSession.setPubObjs(cSocket.getPublicObjs());
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();

        modelSession.setMyScheme(cSocket.selectAndSendScheme(schemesID));
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();

        playersTokens = new int[players];
        modelSession.setPlayerSchemesVect(players);
        for(int i=0;i<players;i++)
        {

            int[] temp = cSocket.getOppSchemes();
            loggerMP.stackLog(cSocket.socketLogger.updateFather());
            cSocket.socketLogger.reinitialize();
            modelSession.setPlayerSchemes(temp[0]-1, temp[1], temp[2]);
            playersTokens[temp[0]-1] = temp[3];
            loggerMP.minorLog("player "+Integer.toString(temp[0])+" scheme initialized in modelMP");

        }

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
