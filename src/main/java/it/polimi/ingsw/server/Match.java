package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MajorLogger;
import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.IOException;

public class Match
{
    SocketServer server;

    private int numPlayers=0;
    private String[] playerNames;
    private int round;
    private int playerTurn;
    private Model modelInstance;

    //General Model Components

    private int[] playerTokens;
    private int[] scores;

    public MinorLogger matchLog;

    public Match() throws IOException, InvalidIntArgumentException, InvalidinSocketException {

        matchLog = new MinorLogger();
        matchLog.minorLog("Match Logger operative");
        matchLog.minorLog("start match constructor");

        //INITIALIZATION 1
        matchLog.minorLog("START INITIALIZATION 1");
        server = new SocketServer();
        matchLog.stackLog(server.sServerLog.updateFather());

        numPlayers=server.initializeFPS();
        for(int i=1;i<numPlayers;i++) {
            server.initializeNPS();
            matchLog.stackLog(server.sServerLog.updateFather());
        }

        playerNames = server.initialization1Phase2();
        modelInstance = new Model(numPlayers);
        matchLog.minorLog("END INITIALIZATION 1");


        //INITIALIZATION 2
        initialization2();

    }

    public void CalculateScores()
    {
        // calcolo finale dei punteggi
    }

    public SchemeCard getPlayerScheme(int player) {
        // ritorna lo schema del giocatore in questione
        return null;
    }

    public int getPlayerFb(int player)
    {
        //
        return 0;
    }

    public DraftPool getDraft()
    {
        // ritorna la draftpool
        return null;
    }

    public void setPlayerScheme(int player, SchemeCard scheme)
    {
        // associa lo schema al giocatore
    }

    public void setDraft(DraftPool draft)
    {
        // imposta la draftpool passata come parametro come nuova draftpool
    }

    public void initialization2() throws InvalidIntArgumentException, IOException, InvalidinSocketException {

        matchLog.minorLog("START INITIALIZATION 2");
        //private objectives
        modelInstance.setPrivateObjectives();
        for(int i=0;i<numPlayers;i++) {
            server.sendPrivObj(i, modelInstance.getPrivateObjective(i).getColor());
            matchLog.stackLog(server.sServerLog.updateFather());
        }
            matchLog.minorLog("PRIVATE OBJECTIVES OK");

        //schemes
        modelInstance.setSchemesDeck();
        for(int i=0;i<numPlayers;i++) {
            server.sendSchemes(i, modelInstance.getTempSchemes(i).getID(), modelInstance.getTempSchemes(i+numPlayers).getID());
            matchLog.stackLog(server.sServerLog.updateFather());
        }
        matchLog.minorLog("SCHEMES SENT OK");

        //public objectives
        modelInstance.setPubObjs();
        server.sendPubObjs(modelInstance.getPubObj(0).getId(), modelInstance.getPubObj(1).getId(), modelInstance.getPubObj(2).getId());
        matchLog.stackLog(server.sServerLog.updateFather());
        matchLog.minorLog("PUBLIC OBJECTIVES OK");

        //reception schemes
        for(int i=0;i<numPlayers;i++)
        {
            int[] temp = new int[2];
            temp=server.getSelectedScheme(i);
            matchLog.stackLog(server.sServerLog.updateFather());
            modelInstance.setSelectedScheme(i,temp[0], temp[1]);
            matchLog.minorLog("Player "+Integer.toString(i+1)+" Schemeid = "+Integer.toString(modelInstance.getSchemebyIndex(i).getID()));
            matchLog.minorLog("Player "+Integer.toString(i+1)+" FB = "+Integer.toString(modelInstance.getSchemebyIndex(i).getfb()));
        }
        matchLog.minorLog("SCHEMES RECEPTION OK");
        matchLog.minorLog("initialization 2: phase 1 ended");


        //INITIALIZATION 2: PHASE 2
        matchLog.minorLog("initialization 2: phase 2 started");
        SchemeCard[] temp = new SchemeCard[numPlayers];
        for(int i=0;i<numPlayers;i++)
            temp[i] = modelInstance.getSchemebyIndex(i);

        server.sendSchemestoEveryone(temp);
        matchLog.stackLog(server.sServerLog.updateFather());

        matchLog.minorLog("End initialization 2");


    }
}