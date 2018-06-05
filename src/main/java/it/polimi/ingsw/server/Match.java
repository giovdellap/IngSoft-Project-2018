package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;
import it.polimi.ingsw.server.ToolCards.ToolCardUsageRecord;

import java.io.IOException;
import java.util.ArrayList;

public class Match
{
    //Managers
    private ConnectionManager server;
    private Model modelInstance;
    private TurnManager turnManager;
    private ToolCardUsageRecord toolRecord;
    private CheckingMethods checkingMethods;

    //datas
    private ArrayList<String> playerNames;
    private int numPlayers=0;
    private int[] connectedPlayers;

    //score
    private String[] ranking;
    private int[][] rankingSpecs;

    public MinorLogger matchLog;

    //COSTRUCTOR

    public Match() throws IOException, InvalidIntArgumentException, InvalidinSocketException, GenericInvalidArgumentException, FullDataStructureException {
        //INITIALIZATION 0
        matchLog = new MinorLogger();
        matchLog.minorLog("Match Logger operative");
        matchLog.minorLog("start match constructor");

        //INITIALIZATION 1
        initialization1();

        //INITIALIZATION 2
        initialization2();
    }



    private void startMatch() throws InvalidIntArgumentException, GenericInvalidArgumentException, IOException, FullDataStructureException {

        //initializing turn manager
        turnManager = new TurnManager(setUpTurnManager());
        //Initializing draftPool
        modelInstance.setFinalNumPlayers(playerNames.size());
        modelInstance.draftPoolInitialization();
        //Initializing roundTrack
        modelInstance.roundTrackInitialization();
        //initializing checkingMethods
        checkingMethods = new CheckingMethods();

        //START MATCH
        turnManager.start();
        while(!turnManager.theEnd())
        {
            turn();
            turnManager.endTurn();
            if(turnManager.getActivePlayer()==0&&!turnManager.theEnd())
                modelInstance.roundEnd();
        }
        calculateScore();

    }

    private void turn() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException {
        //manages turn progress

        boolean toolUsed=false;
        boolean moveUsed=false;
        boolean endTurn=false;

        server.turnInitialization(turnManager.getRound(), turnManager.getActivePlayer(), modelInstance.getDraft(), modelInstance.getTrack(), toolRecord.getRecord());
        while(!endTurn)
        {
            int todo = server.getWhatToDo(turnManager.getActivePlayer());
            if(todo==0)
                endTurn=true;
            if(todo==1&&moveUsed==false)
                moveUsed=move();
            if(todo==2&&toolUsed==false)
                toolUsed=tool();
            if(toolUsed==true&&moveUsed==true)
                endTurn=true;
        }
        server.notifyEndTurn(turnManager.getActivePlayer());
    }

    private boolean move() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException {
        //int[0] = draft index, int[1] = scheme x, int[2] = scheme y

        //sends true response and gets modifies
        server.sendServerResponse(true, turnManager.getActivePlayer());
        int[] temp = server.getMove(turnManager.getActivePlayer());

        //checks modifies
        SchemeCard tempSC = modelInstance.getSchemebyIndex(turnManager.getActivePlayer());
        DraftPool tempDP = modelInstance.getDraft();
        Die tempDie = tempDP.returnDie(temp[0]);
        tempDP.pickUpDie(temp[0]);
        boolean flag = checkingMethods.checkMove(tempSC, tempDie, temp[1], temp[2]);

        //eventually applies modifies
        if(flag==true)
        {
            modelInstance.setDraft(tempDP);
            modelInstance.setPlayerScheme(turnManager.getActivePlayer(), tempSC);
            server.sendServerResponse(true, turnManager.getActivePlayer());
            server.notifyMove(turnManager.getActivePlayer(), tempDP, tempSC);
            return true;
        }
        else
            return false;
    }

    private boolean tool() throws GenericInvalidArgumentException, IOException {
        //gets tool id
        int id = server.getToolId(turnManager.getActivePlayer());
        int check = toolRecord.checkAndApplyUsage(turnManager.getTokens(), id);
        if(check==0)
        {
            server.sendServerResponse(false, turnManager.getActivePlayer());
            return false;
        }
        else
        {
            return true;
        }
    }


    public void calculateScore() throws InvalidIntArgumentException, GenericInvalidArgumentException {
        ranking = new String[numPlayers];
        rankingSpecs = new int[numPlayers][7];

        String[] temp = new String[numPlayers];
        int[][] tempSpecs = new int[numPlayers][7]; //columns: 0=priv, 1=pub1, 2=pub2, 3=pub3, 4=tokens, 5=minus, 6=tot

        for(int i=0;i<numPlayers;i++)
        {
            Player player = turnManager.getPlayer(i);
            temp[i] = player.getName();

            tempSpecs[i][0] = modelInstance.getPrivateObjective(i).calculateBonus(modelInstance.getSchemebyIndex(i));
            tempSpecs[i][1] = modelInstance.getPubObj(0).setBonus(modelInstance.getSchemebyIndex(i));
            tempSpecs[i][2] = modelInstance.getPubObj(1).setBonus(modelInstance.getSchemebyIndex(i));
            tempSpecs[i][3] = modelInstance.getPubObj(2).setBonus(modelInstance.getSchemebyIndex(i));
            tempSpecs[i][4] = player.getTokens();
            tempSpecs[i][5] = 0;
            for(int x=0;x<4;x++)
                for(int y=0;y<5;y++)
                    if(modelInstance.getSchemebyIndex(i).getDie(x, y).isDisabled())
                        tempSpecs[i][5]++;
            tempSpecs[i][6] = tempSpecs[i][0]+tempSpecs[i][1]+tempSpecs[i][2]+tempSpecs[i][3]+tempSpecs[i][4]-tempSpecs[i][5];
        }

        for(int i=0;i<numPlayers;i++) {
            int max=0;
            int index=0;
            for (int j=0;j<numPlayers;i++)
            if(temp[j]!=null&&tempSpecs[j][6]>max) {
                index = j;
                max = tempSpecs[j][6];
            }
            ranking[i] = temp[index];
            for(int n=0;n<7;n++)
                rankingSpecs[i][n] = tempSpecs[index][n];
        }
    }


    //INITIALIZATION METHODS

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


    public void initialization2() throws InvalidIntArgumentException, IOException, InvalidinSocketException, GenericInvalidArgumentException, FullDataStructureException {
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

        //tools
        initTools();
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

        startMatch();
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
                {
                    schemeCheck = true;
                }
                modelInstance.setSelectedScheme(i, temp[0], temp[1]);
                matchLog.minorLog("PlayerClient " + Integer.toString(i + 1) + " Schemeid = " + Integer.toString(modelInstance.getSchemebyIndex(i).getID()));
                matchLog.minorLog("PlayerClient " + Integer.toString(i + 1) + " FB = " + Integer.toString(modelInstance.getSchemebyIndex(i).getfb()));
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

    private void initTools() throws IOException, GenericInvalidArgumentException {
        toolRecord = new ToolCardUsageRecord();
        for(int i=0;i<playerNames.size();i++)
            server.sendTools(i, toolRecord.getSelectedId());
    }

    //TURN MANAGER CONSTRUCTOR
    private Player[] setUpTurnManager() throws InvalidIntArgumentException {
        //builds the TurnManager argument
        Player[] temp = new Player[playerNames.size()];
        for(int i=0;i<playerNames.size();i++)
        {
            temp[i] = new Player(i, playerNames.get(i), modelInstance.getSchemebyIndex(i).getDiff(modelInstance.getSchemebyIndex(i).getfb()));
        }
        return temp;
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






}