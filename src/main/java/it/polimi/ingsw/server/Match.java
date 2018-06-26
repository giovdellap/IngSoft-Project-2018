package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.Disconnection.DisconnectionEvent;
import it.polimi.ingsw.commons.Events.Disconnection.ForfaitEvent;
import it.polimi.ingsw.commons.Events.Disconnection.ReconnectionEvent;
import it.polimi.ingsw.commons.Events.Disconnection.ReconnectionPlayer;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.PassEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.Events.TurnEvent;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.server.Connection.GeneralServer;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;
import it.polimi.ingsw.server.ToolCards.*;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

public class Match implements Observer
{
    //Managers
    private GeneralServer generalServer;
    private Model modelInstance;
    private TurnManager turnManager;
    private ToolCardUsageRecord toolRecord;
    private CheckingMethods checkingMethods;
    private LobbyCreator lobbyCreator;

    //datas
    private ArrayList<Player> players;
    private Event currentEvent;
    private boolean endInitialization=false;


    private int oldSize;
    private ArrayList<Integer> disconnectedPlayersInitializationPhase;

    private SimpleLogger logger;

    /**
     * Match Constructor
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws InvalidinSocketException
     * @throws GenericInvalidArgumentException
     * @throws FullDataStructureException
     */

    public Match() throws IOException, InvalidIntArgumentException, InvalidinSocketException, GenericInvalidArgumentException, FullDataStructureException, it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {

        logger = new SimpleLogger(3, true);

        //lobby creation
        lobbyCreator = new LobbyCreator();
        players = lobbyCreator.createThatLobby();
        for(Player player: players)
            player.addObserver(this);
        logger.log("lobby created successfully");

        for(int i=0;i<players.size();i++)
            players.get(i).setId(i);

        modelInstance = new Model(players.size());
        oldSize = players.size();
        disconnectedPlayersInitializationPhase = new ArrayList<Integer>();


        //INITIALIZATION 2
        initialization2();
    }

    //INITIALIZATION METHODS

    /**
     * Second initialization, sets schemes, objectives and toolcards
     * @throws InvalidIntArgumentException
     * @throws IOException
     * @throws GenericInvalidArgumentException
     * @throws FullDataStructureException
     */
    public void initialization2() throws InvalidIntArgumentException, IOException, GenericInvalidArgumentException, FullDataStructureException {
        //INITIALIZATION 2: PHASE 1
        logger.log("START INITIALIZATION 2 PHASE 1");

        //event initialization
        ModelInitializationEvent[] modelEvents = new ModelInitializationEvent[players.size()];
        for(int i=0;i<players.size();i++)
        {
            modelEvents[i] = new ModelInitializationEvent();
        }
        //private objectives
        modelInstance.setPrivateObjectives();

        //schemes
        modelInstance.setSchemesDeck();

        //public objectives
        modelInstance.setPubObjs();
        logger.printNBlankLines(1);
        int[] pubObjs = new int[3];
        pubObjs[0] = modelInstance.getPubObj(0).getId();
        logger.log("Public objective 0: "+Integer.toString(modelInstance.getPubObj(0).getId()));
        pubObjs[1] = modelInstance.getPubObj(1).getId();
        logger.log("Public objective 1: "+Integer.toString(modelInstance.getPubObj(0).getId()));
        pubObjs[2] = modelInstance.getPubObj(2).getId();
        logger.log("Public objective 2: "+Integer.toString(modelInstance.getPubObj(0).getId()));

        //tools
        toolRecord = new ToolCardUsageRecord();
        for(int i=0;i<players.size();i++)
        {
            logger.printNBlankLines(1);
            logger.log("PLAYER "+Integer.toString(i));
            modelEvents[i].setPrivateObjective(modelInstance.getPrivateObjective(i).getColor());
            logger.log("Private Objective: "+Integer.toString(modelInstance.getPrivateObjective(i).getColor()));
            modelEvents[i].setSchemes(modelInstance.getTempSchemes(i).getID(), modelInstance.getTempSchemes(i+players.size()).getID());
            logger.log("Schemes "+Integer.toString(modelInstance.getTempSchemes(i).getID())+", "+Integer.toString(modelInstance.getTempSchemes(i+players.size()).getID()));
            modelEvents[i].setPublicObjectives(pubObjs);
            modelEvents[i].setToolIds(toolRecord.getSelectedId());
            players.get(i).sendEvent(modelEvents[i]);
        }
        for(Player pl : players)
        {
            logger.debugLog("PLAYER :"+Integer.toString(pl.getId()));
            boolean flag=false;
            while(!flag) {
                pl.getEvent();
                if (!currentEvent.getType().equals("DisconnectionEvent"))
                {
                    for(Integer n : disconnectedPlayersInitializationPhase)
                        if(pl.getId()>n)
                            modelInstance.setPlayerScheme(n, null);
                    if (((SchemeSelectionEvent) currentEvent).getId() == modelInstance.getTempSchemes(pl.getId()).getID()) {
                        SchemeCard scheme = modelInstance.getTempSchemes(pl.getId());
                        scheme.setfb(((SchemeSelectionEvent) currentEvent).getFb());
                        modelInstance.setPlayerScheme(pl.getId(), scheme);
                        currentEvent.validate();
                        flag = true;
                        pl.setTokens(modelInstance.getSchemebyIndex(pl.getId()).getDiff(modelInstance.getSchemebyIndex(pl.getId()).getfb()));
                    }
                    if (((SchemeSelectionEvent) currentEvent).getId() == modelInstance.getTempSchemes(pl.getId() + oldSize).getID()) {
                        SchemeCard scheme = modelInstance.getTempSchemes(pl.getId() + oldSize);
                        scheme.setfb(((SchemeSelectionEvent) currentEvent).getFb());
                        modelInstance.setPlayerScheme(pl.getId(), scheme);
                        currentEvent.validate();
                        flag = true;
                        pl.setTokens(modelInstance.getSchemebyIndex(pl.getId()).getDiff(modelInstance.getSchemebyIndex(pl.getId()).getfb()));
                    }
                    pl.sendEvent(currentEvent);
                }
                else
                    flag=true;
            }
        }

        endInitialization=true;
        if(players.size()<oldSize)
            modelInstance.firstRoundCleaner(disconnectedPlayersInitializationPhase);
        for(int i : disconnectedPlayersInitializationPhase)
            players.remove(i);
        for(int i=0;i<players.size();i++)
            players.get(i).setId(i);

        //PHASE 2

        //event construction
        Initialization2Event initEvent = new Initialization2Event();
        for(int i=0;i<players.size();i++)
        {
            initEvent.addEventPlayer(i, players.get(i).getName(), modelInstance.getSchemebyIndex(i).getID(), modelInstance.getSchemebyIndex(i).getfb(), players.get(i).getTokens());
            System.out.println(modelInstance.getSchemebyIndex(i).getfb());
        }
        for(int i=0;i<players.size();i++) {
            if(!players.get(i).isDisconnected())
                players.get(i).sendEvent(initEvent);
            else
                waitForReconnection();
        }
        startMatch();
    }

    /**
     * initializes the model for the beginning of the match and starts the match
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws IOException
     * @throws FullDataStructureException
     */

    private void startMatch() throws InvalidIntArgumentException, GenericInvalidArgumentException, IOException, FullDataStructureException
    {
        generalServer = new GeneralServer(20);

        //initializing turn manager
        turnManager = new TurnManager(players);
        //Initializing draftPool
        modelInstance.setFinalNumPlayers(players.size());
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
            int disconnected =0;
            for(Player pl: players)
                if(pl.isDisconnected())
                    disconnected++;
            if(players.size()-disconnected==1)
                for(Player pl : players)
                    if(!pl.isDisconnected())
                        pl.sendEvent(new ForfaitEvent());
            if(turnManager.isNextRound())
                modelInstance.roundEnd();
        }

        ScoreCalculator scoreCalculator = new ScoreCalculator(modelInstance, players);
        currentEvent=scoreCalculator.calculateScore();
        for(Player pl:players)
            if(!pl.isDisconnected())
                pl.sendEvent(currentEvent);

    }

    /**
     * manages the use and validation of a turn event
     * @throws GenericInvalidArgumentException
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws FullDataStructureException
     */
    private void turn() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException, FullDataStructureException
    {
        sendTurnEvent();

        if(!players.get(turnManager.getActivePlayer()).isDisconnected()) {
            //manages turn progress
            boolean toolUsed = false;
            boolean moveUsed = false;
            boolean endTurn = false;

            while (!endTurn) {
                players.get(turnManager.getActivePlayer()).getEvent();
                if(!players.get(turnManager.getActivePlayer()).isDisconnected())
                {
                    if ((currentEvent.getType().equals("MoveEvent") && moveUsed) || (currentEvent.getType().equals("ToolEvent") && toolUsed))
                        players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
                    if (currentEvent.getType().equals("PassEvent")) {
                        endTurn = true;
                        for (int i = 0; i < players.size(); i++)
                            if (i != turnManager.getActivePlayer())
                                players.get(i).sendEvent(currentEvent);
                    }
                    if (currentEvent.getType().equals("MoveEvent") && !moveUsed)
                        moveUsed = move();
                    logger.debugLog(currentEvent.getType());
                    if (currentEvent instanceof ToolCardEvent) {
                        if (toolUsed)
                            players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
                        else {
                            if ((moveUsed && (((ToolCardEvent) currentEvent).getId() == 1 || ((ToolCardEvent) currentEvent).getId() == 5 || ((ToolCardEvent) currentEvent).getId() == 6 || ((ToolCardEvent) currentEvent).getId() == 7 || ((ToolCardEvent) currentEvent).getId() == 9 || ((ToolCardEvent) currentEvent).getId() == 10 || ((ToolCardEvent) currentEvent).getId() == 11)) || (!moveUsed && ((ToolCardEvent) currentEvent).getId() == 8))
                                players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
                            else {
                                toolUsed = tool();
                                if (toolUsed && (((ToolCardEvent) currentEvent).getId() == 1 || ((ToolCardEvent) currentEvent).getId() == 5 || ((ToolCardEvent) currentEvent).getId() == 6 || ((ToolCardEvent) currentEvent).getId() == 9 || ((ToolCardEvent) currentEvent).getId() == 10 || ((ToolCardEvent) currentEvent).getId() == 11))
                                    moveUsed = true;

                            }
                        }
                    }
                    if(players.get(turnManager.getActivePlayer()).isDisconnected()) {
                        endTurn = true;
                        System.out.println("check 1");
                        activePlayerDisconnected();
                    }
                }
                else
                {
                    System.out.println("check 2");
                    activePlayerDisconnected();
                    endTurn=true;
                }
            }
        }
    }

    /**
     * sends turn event to client
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     * @throws IOException
     */

    public void sendTurnEvent() throws GenericInvalidArgumentException, InvalidIntArgumentException, IOException
    {
        TurnEvent event = new TurnEvent();
        event.setRound(turnManager.getRound());

        if(getNoDisconnected())
            event.noDisconnected();
        else
            event.setDisconnected(getDisconnected());

        event.setActive(turnManager.getActivePlayer());

        event.setDraft(modelInstance.getDraft().getDraft());

        event.setToolsUpdate(toolRecord.getRecord());

        if(turnManager.getTurnIndex()==0&&turnManager.getRound()!=0)
        {
            event.setNextRound(true);
            ArrayList<Die> temp = new ArrayList<Die>();
            for(int i=0;i<modelInstance.getTrack().returnNTurnRoundDice(modelInstance.getTrack().returnActualTurn()-1).returnDim();i++) {
                temp.add(modelInstance.getTrack().returnNTurnRoundDice(modelInstance.getTrack().returnActualTurn() - 1).getDie(i));
                logger.debugLog("Index: "+Integer.toString(i));
                logger.debugLog("Value: "+Integer.toString(modelInstance.getTrack().returnNTurnRoundDice(modelInstance.getTrack().returnActualTurn()-1).getDie(i).getValue()));
                logger.debugLog("Color: "+Integer.toString(modelInstance.getTrack().returnNTurnRoundDice(modelInstance.getTrack().returnActualTurn()-1).getDie(i).getColor()));

            }
            event.setLastRound(temp);
        }
        else
            event.setNextRound(false);
        for(int i=0;i<players.size();i++)
        {
            System.out.println("sono nel for i:"+Integer.toString(i));
            if(players.get(i).isDisconnected())
                waitForReconnection();
            else
            {
                System.out.println("sono nell'else");
                if(i==turnManager.getActivePlayer())
                {
                    logger.debugLog("active");
                    TurnEvent playerEvent = event;
                    playerEvent.setMyTurn(true);
                    players.get(i).sendEvent(playerEvent);
                    if(!players.get(i).isDisconnected())
                        players.get(i).getEvent();
                }
                else
                {
                    logger.debugLog("not active");
                    TurnEvent playerEvent = event;
                    playerEvent.setMyTurn(false);
                    players.get(i).sendEvent(playerEvent);
                    if(!players.get(i).isDisconnected())
                        players.get(i).getEvent();
                }
            }
        }
    }

    /**
     * manages the use and validation of a move event
     * @return true if the move event can be validated, false if it can't
     * @throws GenericInvalidArgumentException
     * @throws IOException
     * @throws InvalidIntArgumentException
     */
    private boolean move() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException
    {
        //checks modifies
        SchemeCard tempSC = modelInstance.getSchemebyIndex(turnManager.getActivePlayer());
        DraftPool tempDP = modelInstance.getDraft();
        Die tempDie = tempDP.returnDie(((MoveEvent)currentEvent).getIndex());
        boolean flag;
        if(!players.get(turnManager.getActivePlayer()).getIPlayedFirstMove()) {
            flag = checkingMethods.checkFirstMove(tempSC, tempDie, ((MoveEvent) currentEvent).getX(), ((MoveEvent) currentEvent).getY());
            if(flag)
                players.get(turnManager.getActivePlayer()).setIPlayedFirstMove(true);
        }
        else
            flag = checkingMethods.checkMove(tempSC, tempDie, ((MoveEvent)currentEvent).getX(), ((MoveEvent)currentEvent).getY());

        logger.debugLog("return checkingMethods "+Boolean.toString(flag));

        //eventually applies modifies
        if(flag)
        {
            logger.debugLog("move return true");
            tempDie = tempDP.returnDie(((MoveEvent)currentEvent).getIndex());
            tempDP.pickUpDie(((MoveEvent)currentEvent).getIndex());
            tempSC.setDie(tempDie, ((MoveEvent) currentEvent).getX(), ((MoveEvent) currentEvent).getY());
            modelInstance.setPlayerScheme(turnManager.getActivePlayer(), tempSC);
            modelInstance.setDraft(tempDP);
            currentEvent.validate();
            ((MoveEvent) currentEvent).setId(turnManager.getActivePlayer());
            for(int i=0;i<players.size();i++) {
                logger.debugLog("Move event send player "+Integer.toString(i));
                if(!players.get(i).isDisconnected())
                    players.get(i).sendEvent(currentEvent);

            }return true;
        }
        else
        {
            logger.debugLog("move return false");
            if(!players.get(turnManager.getActivePlayer()).isDisconnected())
                players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
            return false;
        }
    }


    /**
     * manages the use and validation of a tool event
     * @return true if the tool event can be validated, false if it can't
     * @throws GenericInvalidArgumentException
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws FullDataStructureException
     */
    private boolean tool() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException, FullDataStructureException
    {
        int toolId = ((ToolCardEvent)currentEvent).getId();

        //checks player can use that tool
        //check id
        boolean flag = false;
        for(int i=0;i<3;i++)
            if(toolRecord.getSelectedId()[i]==toolId)
                flag=true;
        logger.debugLog("flag: "+Boolean.toString(flag));
        if(!flag)
            return false;
        //check tokens
        int tokens = toolRecord.checkUsage(players.get(turnManager.getActivePlayer()).getTokens(), toolId);
        logger.debugLog("tokens: "+Integer.toString(tokens));
        if(tokens==0) {
            if(!players.get(turnManager.getActivePlayer()).isDisconnected())
                players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
            return false;
        }
        boolean applied = checkAndApplyToolCardModifies();
        logger.debugLog("check and apply: "+Boolean.toString(applied));
        if(applied)
        {
            toolRecord.applyUsage(toolId);
            players.get(turnManager.getActivePlayer()).usedTokens(tokens);
            currentEvent.validate();
            ((ToolCardEvent) currentEvent).setPlayer(turnManager.getActivePlayer());
            for(Player pl: players)
                if(!pl.isDisconnected())
                    pl.sendEvent(currentEvent);
            return true;
        }
        else {
            if(!players.get(turnManager.getActivePlayer()).isDisconnected())
                players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
            return false;
        }

    }

    /**
     *
     * @return true if the chosen tool card can be used, false if it can't
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     * @throws IOException
     * @throws FullDataStructureException
     */

    public boolean checkAndApplyToolCardModifies() throws GenericInvalidArgumentException, InvalidIntArgumentException, IOException, FullDataStructureException
    {
        ToolCard card;
        ToolCardEvent event;
        switch (((ToolCardEvent)currentEvent).getId())
        {
            case 1:
            {
                logger.debugLog("dentro checkandapply");
                card = toolRecord.getCard(1);
                event = (ToolCardOneEvent)currentEvent;
                int modify;
                if(((ToolCardOneEvent) event).getAction()=='+')
                    modify=1;
                else
                    modify=2;
                boolean check = ((ToolCardOne)card).checkToolCardOne(((ToolCardOneEvent) event).getIndex(), modify, modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), modelInstance.getDraft(), ((ToolCardOneEvent) event).getX(), ((ToolCardOneEvent) event).getY());
                Die dieTemp = modelInstance.getDraft().returnDie(((ToolCardOneEvent) event).getIndex());
                if(modify==1)
                    dieTemp.setValue(modelInstance.getDraft().returnDie(((ToolCardOneEvent) event).getIndex()).getValue()+1);
                else
                    dieTemp.setValue(modelInstance.getDraft().returnDie(((ToolCardOneEvent) event).getIndex()).getValue()-1);


                if(check)
                    check=afterDraftingCheck(dieTemp, ((ToolCardOneEvent) event).getX(), ((ToolCardOneEvent) event).getY());
                if(check)
                {
                    ((ToolCardOne) card).setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                    ((ToolCardOne) card).setDraft(modelInstance.getDraft());
                    ((ToolCardOne) card).applyModifies(((ToolCardOneEvent) event).getIndex(), modify, ((ToolCardOneEvent) event).getX(), ((ToolCardOneEvent) event).getY());
                    modelInstance.setDraft(((ToolCardOne) card).getDraft());
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardOne) card).getScheme());
                }
                return check;
            }
            case 2:
            {
                card = toolRecord.getCard(2);
                event = (ToolCardTwoThreeEvent)currentEvent;
                ((ToolCardTwo)card).setTempScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                boolean check = ((ToolCardTwo)card).checkToolCardTwo(((ToolCardTwoThreeEvent) event).getX0(), ((ToolCardTwoThreeEvent) event).getY0(), modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), ((ToolCardTwoThreeEvent) event).getX1(), ((ToolCardTwoThreeEvent) event).getY1());
                if(check)
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardTwo)card).applyModifies(((ToolCardTwoThreeEvent) event).getX0(), ((ToolCardTwoThreeEvent) event).getY0(), ((ToolCardTwoThreeEvent) event).getX1(), ((ToolCardTwoThreeEvent) event).getY1()));

                return check;
            }
            case 3:
            {
                card = toolRecord.getCard(3);
                event = (ToolCardTwoThreeEvent)currentEvent;
                ((ToolCardThree)card).setTempScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                boolean check = ((ToolCardThree)card).checkToolCardThree(((ToolCardTwoThreeEvent) event).getX0(), ((ToolCardTwoThreeEvent) event).getY0(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), ((ToolCardTwoThreeEvent) event).getX1(), ((ToolCardTwoThreeEvent) event).getY1());
                if(check)
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardThree)card).applyModifies(((ToolCardTwoThreeEvent) event).getX0(), ((ToolCardTwoThreeEvent) event).getY0(),((ToolCardTwoThreeEvent) event).getX1() ,((ToolCardTwoThreeEvent) event).getY1()));

                return check;
            }
            case 4:
            {
                card = toolRecord.getCard(4);
                event = (ToolCardFourEvent)currentEvent;
                ((ToolCardFour)card).setTempScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                boolean check = ((ToolCardFour) card).checkToolCardFour(((ToolCardFourEvent)event).getX01(),((ToolCardFourEvent) event).getY01(),((ToolCardFourEvent) event).getX02(),((ToolCardFourEvent) event).getY02(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()),((ToolCardFourEvent) event).getX11(),((ToolCardFourEvent) event).getY11(),((ToolCardFourEvent) event).getX22(),((ToolCardFourEvent) event).getY22());
                if(check)
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardFour) card).applyModifies(((ToolCardFourEvent) event).getX01(),((ToolCardFourEvent) event).getY01(),((ToolCardFourEvent) event).getX02(),((ToolCardFourEvent) event).getY02(),((ToolCardFourEvent) event).getX11(),((ToolCardFourEvent) event).getY11(),((ToolCardFourEvent) event).getX22(),((ToolCardFourEvent) event).getY22()));

                return check;
            }
            case 5:
            {
                card = toolRecord.getCard(5);
                event = (ToolCardFiveEvent)currentEvent;
                boolean check = ((ToolCardFive)card).checkToolCardFive(modelInstance.getDraft(),((ToolCardFiveEvent)event).getIndex(),modelInstance.getTrack(),((ToolCardFiveEvent)event).getTurn(),((ToolCardFiveEvent)event).getPos());
                if(check)
                    check=afterDraftingCheck(modelInstance.getDraft().returnDie(((ToolCardFiveEvent) event).getIndex()), ((ToolCardFiveEvent) event).getX(), ((ToolCardFiveEvent) event).getY());
                if(check)
                {
                    ((ToolCardFive)card).setRoundTrack(modelInstance.getTrack());
                    ((ToolCardFive)card).setDraft(modelInstance.getDraft());
                    ((ToolCardFive)card).setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                    ((ToolCardFive)card).applyModifies(((ToolCardFiveEvent)event).getIndex(),((ToolCardFiveEvent)event).getTurn(),((ToolCardFiveEvent)event).getPos(),((ToolCardFiveEvent) currentEvent).getX(),((ToolCardFiveEvent) currentEvent).getY());
                    modelInstance.setDraft(((ToolCardFive)card).getDraft());
                    modelInstance.setTrack(((ToolCardFive)card).getTrack());
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardFive) card).getScheme());
                }
                return check;
            }
            case 6:
            {
                card = toolRecord.getCard(6);
                event = (ToolCardSixEvent)currentEvent;
                modelInstance.getDraft().returnDie(((ToolCardSixEvent)event).getIndex()).throwDie();
                ((ToolCardSixEvent) event).setNewValue(modelInstance.getDraft().returnDie(((ToolCardSixEvent)event).getIndex()).getValue());
                event.validate();
                ((ToolCardSixEvent) event).setApplyOne(true);
                if(!players.get(turnManager.getActivePlayer()).isDisconnected()) {
                    players.get(turnManager.getActivePlayer()).sendEvent(event);
                    if(!players.get(turnManager.getActivePlayer()).isDisconnected()) {
                        players.get(turnManager.getActivePlayer()).getEvent();
                        event = (ToolCardSixEvent) currentEvent;
                        Boolean check = afterDraftingCheck(modelInstance.getDraft().returnDie(((ToolCardSixEvent) event).getIndex()), ((ToolCardSixEvent) event).getX(), ((ToolCardSixEvent) event).getY());
                        logger.log("Secondo check: " + Boolean.toString(check));
                        ((ToolCardSixEvent) event).setApplyTwo(check);
                        if (check) {
                            ((ToolCardSix) card).setDraft(modelInstance.getDraft());
                            ((ToolCardSix) card).setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                            ((ToolCardSix) card).ApplyModifiesToScheme(((ToolCardSixEvent) event).getIndex(), ((ToolCardSixEvent) event).getX(), ((ToolCardSixEvent) event).getY());
                            modelInstance.setDraft(((ToolCardSix) card).getDraft());
                            modelInstance.setPlayerScheme(turnManager.getActivePlayer(), ((ToolCardSix) card).getScheme());
                        } else {
                            ((ToolCardSix) card).setDraft(modelInstance.getDraft());
                            ((ToolCardSix) card).setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                            ((ToolCardSix) card).ApplyModifiesToDraft(((ToolCardSixEvent) event).getIndex());
                            modelInstance.setDraft(((ToolCardSix) card).getDraft());
                            modelInstance.setPlayerScheme(turnManager.getActivePlayer(), ((ToolCardSix) card).getScheme());
                        }

                        currentEvent = event;
                        return check;
                    }
                    else
                        return false;
                }
                else
                    return false;
            }
            case 7:
            {
                card = toolRecord.getCard(7);
                event = (ToolCardSevenEvent)currentEvent;
                if(turnManager.getTurnIndex()>=players.size())
                {
                    DraftPool tempDraft = (((ToolCardSeven) card).applyModifies(modelInstance.getDraft()));
                    modelInstance.setDraft(tempDraft);
                    ((ToolCardSevenEvent) event).setDice(modelInstance.getDraft().getDraft());
                    currentEvent = event;
                    return true;
                }

                return false;
            }
            case 8:
            {
                card = toolRecord.getCard(8);
                event = (ToolCardEightNineTenEvent)currentEvent;
                boolean check = (turnManager.getTurnIndex()<players.size());
                if(check)
                    check = checkingMethods.checkMove(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), modelInstance.getDraft().returnDie(((ToolCardEightNineTenEvent) currentEvent).getIndex()), ((ToolCardEightNineTenEvent) currentEvent).getX(), ((ToolCardEightNineTenEvent) currentEvent).getY());
                if(check)
                {
                    turnManager.usedEight(turnManager.getActivePlayer());
                    ToolCardEight toolCardEight = new ToolCardEight();
                    toolCardEight.setDraft(modelInstance.getDraft());
                    toolCardEight.setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                    toolCardEight.applyModifies(((ToolCardEightNineTenEvent) currentEvent).getIndex(), ((ToolCardEightNineTenEvent) currentEvent).getX(), ((ToolCardEightNineTenEvent) currentEvent).getY());
                    modelInstance.setDraft(toolCardEight.getDraft());
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(), toolCardEight.getScheme());
                }
                return check;

            }
            case 9:
            {
                card = toolRecord.getCard(9);
                event = (ToolCardEightNineTenEvent)currentEvent;
                boolean check = ((ToolCardNine)card).checkToolCardNine(modelInstance.getDraft(),((ToolCardEightNineTenEvent)event).getIndex(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()),((ToolCardEightNineTenEvent) event).getX(),((ToolCardEightNineTenEvent) event).getY());
                if(check)
                {
                    ((ToolCardNine) card).setDraft(modelInstance.getDraft());
                    ((ToolCardNine) card).setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                    ((ToolCardNine) card).applyModifies(((ToolCardEightNineTenEvent) event).getIndex(),((ToolCardEightNineTenEvent) event).getX(),((ToolCardEightNineTenEvent) event).getY());
                    modelInstance.setDraft(((ToolCardNine) card).getDraft());
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardNine) card).getScheme());
                }
                return check;
            }
            case 10:
            {
                card = toolRecord.getCard(10);
                event = (ToolCardEightNineTenEvent)currentEvent;
                boolean check = (afterDraftingCheck(((ToolCardTen)card).checkToolCardTen(modelInstance.getDraft(), ((ToolCardEightNineTenEvent) event).getIndex()), ((ToolCardEightNineTenEvent) event).getX(), ((ToolCardEightNineTenEvent) event).getY()));
                if(check)
                {
                    ((ToolCardTen) card).setDraft(modelInstance.getDraft());
                    ((ToolCardTen) card).setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                    ((ToolCardTen) card).applyModifies(((ToolCardEightNineTenEvent) event).getIndex(),((ToolCardEightNineTenEvent) event).getX(),((ToolCardEightNineTenEvent) event).getY());
                    modelInstance.setDraft(((ToolCardTen) card).getDraft());
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardTen) card).getScheme());
                }
                return check;
            }
            case 11:
            {
                card = toolRecord.getCard(11);
                event = (ToolCardElevenEvent)currentEvent;
                DraftPool tempDraft = modelInstance.getDraft();
                Die tempDie = tempDraft.toolCardElevenReplacement(((ToolCardElevenEvent) event).getIndex());
                ((ToolCardElevenEvent) event).setNewColor(tempDie.getColor());
                ((ToolCardElevenEvent) event).setFirstCheck(true);
                event.validate();
                if(!players.get(turnManager.getActivePlayer()).isDisconnected()) {
                    players.get(turnManager.getActivePlayer()).sendEvent(event);
                    if(!players.get(turnManager.getActivePlayer()).isDisconnected()) {
                        players.get(turnManager.getActivePlayer()).getEvent();
                        event = (ToolCardElevenEvent) currentEvent;
                        tempDie.setValue(((ToolCardElevenEvent) event).getNewValue());
                        logger.log(Integer.toString(tempDraft.returnDie(0).getColor()));
                        logger.log(card.getToolCardName());
                        ((ToolCardEleven) card).setDraft(tempDraft);
                        ((ToolCardEleven) card).setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));


                        boolean check = afterDraftingCheck(tempDie, ((ToolCardElevenEvent) event).getX(), ((ToolCardElevenEvent) event).getY());
                        if (check) {
                            ((ToolCardEleven) card).applyModifiesTwo(tempDie, ((ToolCardElevenEvent) event).getX(), ((ToolCardElevenEvent) event).getY(), ((ToolCardElevenEvent) event).getIndex());
                            ((ToolCardElevenEvent) event).setApplyTwo(true);
                            modelInstance.setDraft(((ToolCardEleven) card).getDraft());
                            modelInstance.setPlayerScheme(turnManager.getActivePlayer(), ((ToolCardEleven) card).getScheme());
                        } else {
                            ((ToolCardEleven) card).applyModifiesOne(tempDie, ((ToolCardElevenEvent) event).getIndex());
                            ((ToolCardElevenEvent) event).setApplyOne(true);
                            modelInstance.setDraft(((ToolCardEleven) card).getDraft());
                        }
                        return true;
                    }
                    else
                        return false;
                }
                else
                    return false;
            }
            case 12:
            {
                card = toolRecord.getCard(12);
                event = (ToolCardTwelveEvent)currentEvent;
                if(((ToolCardTwelveEvent) event).isOnlyOne())
                {
                    ((ToolCardTwelve)card).setTempScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                    if(((ToolCardTwelve)card).checkToolCardTwelve1Die(modelInstance.getTrack(), ((ToolCardTwelveEvent) event).getTurn(), ((ToolCardTwelveEvent) event).getPos(), modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), ((ToolCardTwelveEvent) event).getX01(), ((ToolCardTwelveEvent) event).getY01(), ((ToolCardTwelveEvent) event).getX11(), ((ToolCardTwelveEvent) event).getY11()))
                    {
                        modelInstance.setPlayerScheme(turnManager.getActivePlayer(), ((ToolCardTwelve) card).applyModifies(((ToolCardTwelveEvent) currentEvent).getX01(), ((ToolCardTwelveEvent) currentEvent).getY01(), ((ToolCardTwelveEvent) currentEvent).getX11(),((ToolCardTwelveEvent) currentEvent).getY11() ));
                        return true;
                    }
                }
                else
                {
                    ((ToolCardTwelve)card).setTempScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                    if(((ToolCardTwelve)card).checkToolCardTwelve2Dice(modelInstance.getTrack(), ((ToolCardTwelveEvent) event).getTurn(), ((ToolCardTwelveEvent) event).getPos(), ((ToolCardTwelveEvent) event).getX01(), ((ToolCardTwelveEvent) event).getY01(), ((ToolCardTwelveEvent) event).getX02(), ((ToolCardTwelveEvent) event).getY02(), modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), ((ToolCardTwelveEvent) event).getX11(), ((ToolCardTwelveEvent) event).getY11(), ((ToolCardTwelveEvent) event).getX22(), ((ToolCardTwelveEvent) event).getY22()));
                    {
                        modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardTwelve)card).applyModifies(((ToolCardTwelveEvent) event).getX01(), ((ToolCardTwelveEvent) event).getY01(), ((ToolCardTwelveEvent) event).getX02(), ((ToolCardTwelveEvent) event).getY02(),((ToolCardTwelveEvent) event).getX11(), ((ToolCardTwelveEvent) event).getY11(), ((ToolCardTwelveEvent) event).getX22(), ((ToolCardTwelveEvent) event).getY22()));
                        return true;
                    }
                }
                return false;


            }
        }
        return false;
    }

    /**
     * checks placement for the After Drafting tool cards
     * @param toPlace die to check placement
     * @param x row
     * @param y column
     * @return true if the die can be position there, false if it can't
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */

    private boolean afterDraftingCheck(Die toPlace, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {
        if(!players.get(turnManager.getActivePlayer()).getIPlayedFirstMove())
            return checkingMethods.checkFirstMove(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), toPlace, x, y);
        else
            return checkingMethods.checkMove(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), toPlace, x, y);

    }


    //DISCONNECTION MANAGEMENT

    /**
     *
     */

    public void waitForReconnection() throws IOException, InvalidIntArgumentException {
        logger.log("Waiting for reconnection");
        ArrayList<String> geppetto = new ArrayList<String>();
        for(Player pl:players)
            if(pl.isDisconnected())
                geppetto.add(pl.getName());
        Socket thisSocket = generalServer.reconnection(geppetto);
        if(thisSocket!=null)
        {
            logger.log("Player "+generalServer.getUsername()+" reconnected");
            for(Player pl:players)
                if(pl.getName().equals(generalServer.getUsername())) {
                    pl.changeSocket(thisSocket);
                    pl.sendEvent(createReconnectionEvent(pl.getId()));
                }
        }

    }

    public void activePlayerDisconnected() throws InvalidIntArgumentException, IOException {
        for(Player pl : players)
            if(!pl.isDisconnected())
                pl.sendEvent(new PassEvent());
    }

    public ReconnectionEvent createReconnectionEvent(int playerId) throws InvalidIntArgumentException {
        ReconnectionEvent event = new ReconnectionEvent();
        for(int i=0;i<players.size();i++)
            event.addPlayer(players.get(i).getName(), players.get(i).getTokens(), modelInstance.getSchemebyIndex(i));
        event.addPrivObj(modelInstance.getPrivateObjective(playerId).getColor());

        int[] temp = new int[3];
        temp[0] = modelInstance.getPubObj(0).getId();
        temp[1] = modelInstance.getPubObj(1).getId();
        temp[2] = modelInstance.getPubObj(2).getId();
        event.addPubObjs(temp);

        event.addToolsIds(toolRecord.getSelectedId());
        event.addToolsTokens(toolRecord.getRecord());

        event.addTrack(modelInstance.getTrack());

        return event;
    }




    /**
     *
     * @return if ther aren't disconnected players
     */

    public boolean getNoDisconnected()
    {
        boolean flag = true;
        for(Player pl:players)
            if(pl.isDisconnected())
                flag=false;
        return flag;
    }

    /**
     *
     * @return list of disconnected players
     */
    public ArrayList<String> getDisconnected()
    {
        ArrayList<String> temp = new ArrayList<String>();
        for(Player pl: players)
            if(pl.isDisconnected())
                temp.add(pl.getName());
        return null;
    }

    /**
     *
     * @param o Observable
     * @param arg Object
     */

    public void update(Observable o, Object arg) {
        if(((Event)arg).getType().equals("DisconnectionEvent"))
        {
            logger.log("Player "+Integer.toString(((DisconnectionEvent)arg).getId())+" disconnected");
            if(!endInitialization)
                disconnectedPlayersInitializationPhase.add(((DisconnectionEvent)arg).getId());
        }
        currentEvent = (Event)arg;
    }


}