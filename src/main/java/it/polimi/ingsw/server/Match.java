package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.IOException;
import java.util.ArrayList;

public class Match
{
    //INSTANCE FROM OTHERS CLASSES
    ConnectionManager server;
    private Model modelInstance;

    private ArrayList<String> playerNames;

    private int numPlayers=0;
    private int round;
    private int playerTurn;
    private int[] connectedPlayers;

    //General Model Components

    private int[] playerTokens;
    private int[] scores;

    public MinorLogger matchLog;

    //COSTRUCTOR

    public Match() throws IOException, InvalidIntArgumentException, InvalidinSocketException, GenericInvalidArgumentException
    {
        //INITIALIZATION 0
        matchLog = new MinorLogger();
        matchLog.minorLog("Match Logger operative");
        matchLog.minorLog("start match constructor");

        //INITIALIZATION 1
        initialization1();

        //INITIALIZATION 2
        initialization2();
    }

    private void initialization1() throws GenericInvalidArgumentException, IOException
    {
        matchLog.minorLog("START INITIALIZATION 1");

        server = new ConnectionManager();                           //SERVER
        matchLog.stackLog(server.sServerLog.updateFather());
        server.sServerLog.reinitialize();

        playerNames = server.lobbyCreation();                       //PLAYERNAMES

        numPlayers = playerNames.size();

        updatePlayersInit();

        matchLog.stackLog(server.sServerLog.updateFather());
        server.sServerLog.reinitialize();

        modelInstance = new Model(numPlayers);                      //MODELINSTANCE

        matchLog.minorLog("END INITIALIZATION 1");
    }


    public void initialization2() throws InvalidIntArgumentException, IOException, InvalidinSocketException, GenericInvalidArgumentException
    {
        //INITIALIZATION 2: PHASE 1
        matchLog.minorLog("START INITIALIZATION 2 PHASE 1");

        //private objectives
        initPrObjs();
        updatePlayersInit();

        //schemes
        initSchemes();
        updatePlayersInit();

        //public objectives
        initPubObjs();
        updatePlayersInit();

        //reception schemes
        receiveAndcheckScheme();
        updatePlayersInit();

        //INITIALIZATION 2: PHASE 2
        matchLog.minorLog("Initialization 2: Phase 2 started");

        SchemeCard[] temp = new SchemeCard[numPlayers];

        for(int i=0;i<numPlayers;i++)
            temp[i] = modelInstance.getSchemebyIndex(i);


        server.sendSchemestoEveryone(temp);

        matchLog.stackLog(server.sServerLog.updateFather());
        server.sServerLog.reinitialize();

        updatePlayersInit();

        matchLog.minorLog("End initialization 2");
    }

    private void receiveAndcheckScheme() throws InvalidinSocketException, GenericInvalidArgumentException, IOException, InvalidIntArgumentException
    {
        boolean schemeCheck;

        for (int i = 0; i < numPlayers; i++)
        {
            schemeCheck=false;
            while (!schemeCheck)
            {
                int[] temp = new int[2];
                temp = server.getSelectedScheme(i);
                matchLog.stackLog(server.sServerLog.updateFather());
                server.sServerLog.reinitialize();

                if (temp[0] == modelInstance.getTempSchemes(i).getID() || temp[0] == modelInstance.getTempSchemes(i + numPlayers).getID())
                    schemeCheck=true;

                modelInstance.setSelectedScheme(i, temp[0], temp[1]);
                matchLog.minorLog("Player " + Integer.toString(i + 1) + " Schemeid = " + Integer.toString(modelInstance.getSchemebyIndex(i).getID()));
                matchLog.minorLog("Player " + Integer.toString(i + 1) + " FB = " + Integer.toString(modelInstance.getSchemebyIndex(i).getfb()));

            }
        }
    }

    private void initPrObjs() throws InvalidIntArgumentException, GenericInvalidArgumentException, IOException
    {
        modelInstance.setPrivateObjectives();
        for(int i=0;i<numPlayers;i++)
        {
            server.sendPrivObj(i, modelInstance.getPrivateObjective(i).getColor());
            matchLog.stackLog(server.sServerLog.updateFather());
            server.sServerLog.reinitialize();
        }
        matchLog.minorLog("PRIVATE OBJECTIVES OK");
    }

    private void initSchemes() throws InvalidIntArgumentException, IOException, GenericInvalidArgumentException
    {
        modelInstance.setSchemesDeck();
        for(int i=0;i<numPlayers;i++)
        {
            server.sendSchemes(i, modelInstance.getTempSchemes(i).getID(), modelInstance.getTempSchemes(i+numPlayers).getID());
            matchLog.stackLog(server.sServerLog.updateFather());
            server.sServerLog.reinitialize();
        }
        matchLog.minorLog("SCHEMES SENT OK");
    }

    private void initPubObjs() throws InvalidIntArgumentException, IOException, GenericInvalidArgumentException
    {
        modelInstance.setPubObjs();
        for(int i=0;i<playerNames.size();i++)
        {
            server.sendPubObjs(i, modelInstance.getPubObj(0).getId(), modelInstance.getPubObj(1).getId(), modelInstance.getPubObj(2).getId());
        }
        matchLog.stackLog(server.sServerLog.updateFather());
        server.sServerLog.reinitialize();
        matchLog.minorLog("PUBLIC OBJECTIVES OK");
    }

    //DISCONNECTION MANAGEMENT
    public void updatePlayersInit()
    {
        ArrayList<String> temp = server.notifyFatherInit();
        for(String matchobject : playerNames)
        {
            boolean flag = false;
            for (String argObj : temp)
                if(matchobject.equals(argObj))
                    flag = true;

            if(!flag)
                playerNames.remove(matchobject);
        }
    }

    public void CalculateScores()
    {
        // calcolo finale dei punteggi
    }

    public SchemeCard getPlayerScheme(int player)
    {
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
}