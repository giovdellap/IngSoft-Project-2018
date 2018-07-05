package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.*;
import it.polimi.ingsw.commons.Events.Disconnection.DisconnectionEvent;
import it.polimi.ingsw.commons.Events.Disconnection.ForfaitEvent;
import it.polimi.ingsw.commons.Events.Disconnection.ReconnectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.server.Connection.GeneralServer;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.*;

import java.io.IOException;
import java.net.Socket;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static it.polimi.ingsw.server.PlayerThread.State.*;

public class Match implements Observer
{
    //Managers
    private GeneralServer generalServer;
    private Model modelInstance;
    private TurnManager turnManager;
    private ToolCardUsageRecord toolRecord;
    private CheckingMethods checkingMethods;
    private ExecutorService executor;
    private TurnActionHandler turnActionHandler;

    //datas
    private ArrayList<PlayerThread> players;
    private ArrayList<Event> currentEvent;
    private boolean endInitialization=false;
    private boolean timeExpired;

    private ArrayList<Integer> disconnectedPlayersInitializationPhase;

    private SimpleLogger logger;

    /**
     * Match Constructor
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws FullDataStructureException
     */

    public Match() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException, InterruptedException
    {
        LobbyCreator lobbyCreator;
        disconnectedPlayersInitializationPhase = new ArrayList<Integer>();

        generalServer = new GeneralServer();
        logger = new SimpleLogger(3, true);

        //lobby creation
        lobbyCreator = new LobbyCreator(generalServer);
        players = lobbyCreator.createThatLobby();

        for(PlayerThread player: players)
            player.addObserver(this);

        logger.log("lobby created successfully");

        for(int i=0;i<players.size();i++)
            players.get(i).setId(i);

        modelInstance = new Model(players.size());

        currentEvent= new ArrayList<Event>();

        for (int i=0; i<players.size();i++)
            currentEvent.add(new PassEvent());

        executor = Executors.newFixedThreadPool(players.size());

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
    public void initialization2() throws InvalidIntArgumentException, IOException, GenericInvalidArgumentException, FullDataStructureException, InterruptedException
    {
        //INITIALIZATION 2: PHASE 1
        logger.log("START INITIALIZATION 2 PHASE 1");

        //private objectives
        modelInstance.setPrivateObjectives();

        //schemes
        modelInstance.setSchemesDeck();

        //public objectives
        modelInstance.setPubObjs();
        logger.printNBlankLines(1);
        int[] pubObjs = new int[3];
        pubObjs[0] = modelInstance.getPubObj(0).getId();
        logger.log("Public objective 0: " + Integer.toString(modelInstance.getPubObj(0).getId()));
        pubObjs[1] = modelInstance.getPubObj(1).getId();
        logger.log("Public objective 1: " + Integer.toString(modelInstance.getPubObj(0).getId()));
        pubObjs[2] = modelInstance.getPubObj(2).getId();
        logger.log("Public objective 2: " + Integer.toString(modelInstance.getPubObj(0).getId()));

        //tools
        toolRecord = new ToolCardUsageRecord();

        for (PlayerThread player : players)
        {
            ModelInitializationEvent modelEvent = new ModelInitializationEvent();
            int i = player.getId();

            logger.printNBlankLines(1);
            logger.log("PLAYER " + Integer.toString(i)+" model init. event");

            modelEvent.setPrivateObjective(modelInstance.getPrivateObjective(i).getColor());

            logger.log("Private Objective: " + Integer.toString(modelInstance.getPrivateObjective(i).getColor()));

            modelEvent.setSchemes(modelInstance.getTempSchemes(i).getID(), modelInstance.getTempSchemes(i + players.size()).getID());

            logger.log("Schemes " + Integer.toString(modelInstance.getTempSchemes(i).getID()) + ", " + Integer.toString(modelInstance.getTempSchemes(i + players.size()).getID()));

            modelEvent.setPublicObjectives(pubObjs);
            modelEvent.setToolIds(toolRecord.getSelectedId());

            player.setEvent(modelEvent);
            player.setState(SEND);

        }

        logger.log("Sending modelEvents...");

        for(PlayerThread player : players)
            executor.execute(player);

        executor.shutdown();
        executor.awaitTermination(5, TimeUnit.SECONDS);

        logger.log("ModelEvents sent");
        executor=Executors.newFixedThreadPool(players.size());

        logger.log("Sending schemes");
        for(PlayerThread player : players)
        {
            player.setState(SCHEMESELECTION);
            player.setTempSchemes(modelInstance.getTempSchemes(player.getId()).getID(), modelInstance.getTempSchemes(player.getId()+players.size()).getID());
            executor.execute(player);
        }
        executor.shutdown();
        executor.awaitTermination(30, TimeUnit.SECONDS);

        modelInstance.initializationEnd();
        endInitialization=true;

        modelInstance.firstRoundCleaner(disconnectedPlayersInitializationPhase);
        for(Integer i : disconnectedPlayersInitializationPhase)
        {
            currentEvent.remove(0);
            players.remove(i);
        }

        for(int i=0;i<players.size();i++) {
            logger.log("old player id: "+players.get(i).getId());
            players.get(i).setId(i);
            logger.log("new player id: "+players.get(i).getId());
        }

        //PHASE 2

        //event construction
        Initialization2Event initEvent = new Initialization2Event();

        for(int i=0;i<players.size();i++)
            logger.log("Player id "+Integer.toString(i)+" scheme id: "+Integer.toString(modelInstance.getSchemebyIndex(i).getID()));

        for(int i=0;i<players.size();i++)
        {
            logger.log("fb "+Integer.toString(modelInstance.getSchemebyIndex(i).getfb()));
            players.get(i).setTokens(modelInstance.getSchemebyIndex(players.get(i).getId()).getDiff(modelInstance.getSchemebyIndex(players.get(i).getId()).getfb()));
            initEvent.addEventPlayer(i, players.get(i).getName(), modelInstance.getSchemebyIndex(i).getID(), modelInstance.getSchemebyIndex(i).getfb(), players.get(i).getTokens());
            logger.log("final model fb: "+modelInstance.getSchemebyIndex(i).getfb());
        }
        executor=Executors.newFixedThreadPool(players.size());

        logger.log("Sending initialization2Events to players...");

        for(PlayerThread player: players)
        {
            player.setState(SEND);
            player.setEvent(initEvent);
            executor.execute(player);
            logger.log("Player"+Integer.toString(player.getId())+" sending...");
        }

        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);
        logger.log("All events sent");

        startMatch();
    }

    public void setScheme(PlayerThread player) throws InvalidIntArgumentException
    {
        int id = ((SchemeSelectionEvent)currentEvent.get(player.getId())).getId();
        int fb = ((SchemeSelectionEvent)currentEvent.get(player.getId())).getFb();
        modelInstance.setPlayerSchemeFirstTime(player.getId(), id, fb);
    }

    /**
     * initializes the model for the beginning of the match and starts the match
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws IOException
     * @throws FullDataStructureException
     */

    private void startMatch() throws InvalidIntArgumentException, GenericInvalidArgumentException, IOException, FullDataStructureException, InterruptedException
    {
        //initializing turn manager
        turnManager = new TurnManager(players);
        //Initializing draftPool
        modelInstance.setFinalNumPlayers(players.size());
        modelInstance.draftPoolInitialization();
        //Initializing roundTrack
        modelInstance.roundTrackInitialization();
        //initializing checkingMethods
        checkingMethods = new CheckingMethods();
        //initializing TurnActionHandler
        turnActionHandler = new TurnActionHandler();

        //START MATCH
        turnManager.start();

        while(!turnManager.theEnd())
        {
            turn();
            turnManager.endTurn();
            int disconnected =0;
            for(PlayerThread pl: players)
                if(pl.isDisconnected())
                    disconnected++;
            if(players.size()-disconnected==1)
                for(PlayerThread pl : players)
                    if(!pl.isDisconnected())
                        pl.sendEvent(new ForfaitEvent());

            if(turnManager.isNextRound())
                modelInstance.roundEnd();
        }

        ScoreCalculator scoreCalculator = new ScoreCalculator(modelInstance, players);
        ScoreEvent event =scoreCalculator.calculateScore();
        for(PlayerThread pl:players)
            if(!pl.isDisconnected())
                pl.sendEvent(event);

    }

    /**
     * manages the use and validation of a turn event
     * @throws GenericInvalidArgumentException
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws FullDataStructureException
     */
    private void turn() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException, FullDataStructureException, InterruptedException
    {

        if(!players.get(turnManager.getActivePlayer()).isDisconnected())
        {
            sendTurnEvent();

            //manages turn progress
            boolean toolUsed = false;
            boolean moveUsed = false;
            boolean endTurn = false;
            long longDate = System.currentTimeMillis();
            timeExpired=false;

            while (!endTurn&&(System.currentTimeMillis()-longDate<(180*1000)))
            {
                boolean sendToAll=false;
                logger.log("turn loop started");

                executor=Executors.newFixedThreadPool(1);
                players.get(turnManager.getActivePlayer()).setState(RECEIVE);
                executor.execute(players.get(turnManager.getActivePlayer()));
                executor.shutdown();
                boolean timeExpired=!executor.awaitTermination(longDate+180*1000-System.currentTimeMillis(), TimeUnit.MILLISECONDS);
                logger.log("turn time expired: "+ Boolean.toString(timeExpired));
                logger.log("receive phase ended");


                Event actualEvent = currentEvent.get(turnManager.getActivePlayer());
                actualEvent.resetValidation();
                logger.log("Event received: "+actualEvent.getType());

                //PassEvent
                if(actualEvent.getType().equals("PassEvent"))
                    endTurn = true;
                else {
                    boolean alreadyChecked = false;

                    if ((actualEvent.getType().equals("MoveEvent") && moveUsed) || (actualEvent.getType().equals("ToolEvent") && toolUsed)) {
                        actualEvent.resetValidation();
                        alreadyChecked = true;
                        logger.log("Player has already moved or used a toolcard");
                        sendToAll = false;
                    }

                    //MoveEvent
                    if (actualEvent.getType().equals("MoveEvent") && !alreadyChecked) {
                        alreadyChecked = true;
                        moveUsed = move();
                        if (moveUsed) {
                            logger.log("Move Accepted: " + Boolean.toString(moveUsed));
                            actualEvent.validate();
                            sendToAll = true;
                        }


                    }

                    //ToolCardEvent
                    if (actualEvent instanceof ToolCardEvent && !alreadyChecked) {
                        alreadyChecked = true;
                        int currEvent = ((ToolCardEvent) currentEvent.get(turnManager.getActivePlayer())).getId();

                        if ((moveUsed && (currEvent == 1 || currEvent == 5 || currEvent == 6 || currEvent == 7 || currEvent == 9 || currEvent == 10 || currEvent == 11 || currEvent == 8))) {
                            logger.log("After drafting toolcard unusable because player already moved");
                            actualEvent.resetValidation();
                            sendToAll = false;
                        } else {
                            toolUsed = tool();
                            if (toolUsed) {
                                sendToAll = true;
                                actualEvent = currentEvent.get(turnManager.getActivePlayer());
                                logger.log("toolcard accepted");
                                actualEvent.validate();
                            }
                            if ((toolUsed && (currEvent == 1 || currEvent == 5 || currEvent == 6 || currEvent == 7 || currEvent == 9 || currEvent == 10 || currEvent == 11 || currEvent == 8)))
                                moveUsed = true;
                        }
                    }

                    logger.log("actualEvent: " + actualEvent.getType());

                    if (!timeExpired)
                    {
                        if (sendToAll)
                        {
                            executor = Executors.newFixedThreadPool(players.size());
                            for (PlayerThread player : players)
                            {
                                player.setState(SEND);
                                player.setEvent(actualEvent);
                                System.out.println("Sending event " + actualEvent.getType() + " to player " + Integer.toString(player.getId()));
                                executor.execute(player);
                            }
                        }
                        else {
                            executor = Executors.newFixedThreadPool(1);
                            players.get(turnManager.getActivePlayer()).setEvent(actualEvent);
                            players.get(turnManager.getActivePlayer()).setState(SEND);
                            executor.execute(players.get(turnManager.getActivePlayer()));
                            logger.log("Event sent to player " + Integer.toString(turnManager.getActivePlayer()));
                        }
                        executor.shutdown();
                        executor.awaitTermination(8, TimeUnit.SECONDS);
                    }
                }
            }
        }
    }

    /**
     * sends turn event to client
     * @throws InvalidIntArgumentException
     */

    public void sendTurnEvent() throws InvalidIntArgumentException, InterruptedException
    {
        TurnEvent activeEvent = new TurnEvent();
        TurnEvent notActiveEvent = new TurnEvent();
        activeEvent.setRound(turnManager.getRound());
        notActiveEvent.setRound(turnManager.getRound());

        if(getNoDisconnected())
        {
            activeEvent.noDisconnected();
            notActiveEvent.noDisconnected();
        }
        else
        {
            activeEvent.setDisconnected(getDisconnected());
            activeEvent.setDisconnected(getDisconnected());
        }
        activeEvent.setActive(turnManager.getActivePlayer());
        notActiveEvent.setActive(turnManager.getActivePlayer());

        activeEvent.setDraft(modelInstance.getDraft().getDraft());
        notActiveEvent.setDraft(modelInstance.getDraft().getDraft());

        activeEvent.setToolsUpdate(toolRecord.getRecord());
        notActiveEvent.setToolsUpdate(toolRecord.getRecord());

        if(turnManager.getTurnIndex()==0&&turnManager.getRound()!=0)
        {
            activeEvent.setNextRound(true);
            notActiveEvent.setNextRound(true);

            ArrayList<Die> temp = new ArrayList<Die>();
            for(int i=0;i<modelInstance.getTrack().returnNTurnRoundDice(modelInstance.getTrack().returnActualTurn()-1).returnDim();i++)
                temp.add(modelInstance.getTrack().returnNTurnRoundDice(modelInstance.getTrack().returnActualTurn() - 1).getDie(i));

            activeEvent.setLastRound(temp);
            notActiveEvent.setLastRound(temp);
        }
        else {
            activeEvent.setNextRound(false);
            notActiveEvent.setNextRound(false);
        }

        activeEvent.setMyTurn(true);

        executor=Executors.newFixedThreadPool(players.size());

        for(PlayerThread player : players)
        {
            if(player.getId()==turnManager.getActivePlayer())
            {
                logger.log("Setting up active player");
                player.setEvent(activeEvent);
                player.setState(HANDLE);
                logger.log("Player itsMyTurn: "+((TurnEvent)player.getLastEventSent()).itsMyTurn());
            }
            else
            {
                logger.debugLog("Setting up unactive player id: "+Integer.toString(player.getId()));
                currentEvent.set(player.getId(), notActiveEvent);
                player.setEvent(notActiveEvent);
                player.setState(HANDLE);
            }
            executor.execute(player);
        }
        executor.shutdown();
        executor.awaitTermination(20, TimeUnit.SECONDS);

        logger.log("Turn events sent");
    }

    /**
     * manages the use and validation of a move event
     * @return true if the move event can be validated, false if it can't
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    private boolean move() throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        //checks modifies
        SchemeCard tempSC = modelInstance.getSchemebyIndex(turnManager.getActivePlayer());
        DraftPool tempDP = modelInstance.getDraft();
        Die tempDie = tempDP.returnDie(((MoveEvent)currentEvent.get(turnManager.getActivePlayer())).getIndex());
        boolean flag;

        if(!players.get(turnManager.getActivePlayer()).getIPlayedFirstMove())
        {
            flag = checkingMethods.checkFirstMove(tempSC, tempDie, ((MoveEvent) currentEvent.get(turnManager.getActivePlayer())).getX(), ((MoveEvent) currentEvent.get(turnManager.getActivePlayer())).getY());
            if(flag)
                players.get(turnManager.getActivePlayer()).setIPlayedFirstMove(true);
        }
        else
            flag = checkingMethods.checkMove(tempSC, tempDie, ((MoveEvent)currentEvent.get(turnManager.getActivePlayer())).getX(), ((MoveEvent)currentEvent.get(turnManager.getActivePlayer())).getY());

        logger.log("move accepted: "+Boolean.toString(flag));

        //eventually applies modifies
        if(flag)
        {
            logger.log("applying move modifies...");
            tempDie = tempDP.returnDie(((MoveEvent)currentEvent.get(turnManager.getActivePlayer())).getIndex());
            tempDP.pickUpDie(((MoveEvent)currentEvent.get(turnManager.getActivePlayer())).getIndex());
            tempSC.setDie(tempDie, ((MoveEvent) currentEvent.get(turnManager.getActivePlayer())).getX(), ((MoveEvent) currentEvent.get(turnManager.getActivePlayer())).getY());
            modelInstance.setPlayerScheme(turnManager.getActivePlayer(), tempSC);
            modelInstance.setDraft(tempDP);
            currentEvent.get(turnManager.getActivePlayer()).validate();
            ((MoveEvent) currentEvent.get(turnManager.getActivePlayer())).setId(turnManager.getActivePlayer());
            return true;
        }
        else
        {
            logger.debugLog("move not accepted");
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
        int toolId = ((ToolCardEvent)currentEvent.get(turnManager.getActivePlayer())).getId();

        //checks player can use that tool
        //check id
        boolean flag = false;
        for(int i=0;i<3;i++)
            if(toolRecord.getSelectedId()[i]==toolId)
                flag=true;
        if(!flag)
            return false;

        turnActionHandler.setModel(modelInstance);
        turnActionHandler.setPlayer(players.get(turnManager.getActivePlayer()));
        turnActionHandler.setToolRecord(toolRecord);
        turnActionHandler.setTurnManager(turnManager);

        ToolCardEvent tempEvent = turnActionHandler.useTool((ToolCardEvent)currentEvent.get(turnManager.getActivePlayer()));
        currentEvent.set(turnManager.getActivePlayer(), tempEvent);
        modelInstance = turnActionHandler.getModel();
        players.set(turnManager.getActivePlayer(),turnActionHandler.getPlayer());
        toolRecord = turnActionHandler.getToolRecord();
        turnManager = turnActionHandler.getTurnManager();

        return tempEvent.isValidated();

    }


    //DISCONNECTION MANAGEMENT

    /**
     *
     */

    public void waitForReconnection() throws IOException, InvalidIntArgumentException
    {
        logger.log("Waiting for reconnection");
        ArrayList<String> geppetto = new ArrayList<String>();
        for(PlayerThread pl:players)
            if(pl.isDisconnected())
                geppetto.add(pl.getName());
        Socket thisSocket = generalServer.reconnection(geppetto);
        if(thisSocket!=null)
        {
            logger.log("Player "+generalServer.getUsername()+" reconnected");
            for(PlayerThread pl:players)
                if(pl.getName().equals(generalServer.getUsername()))
                {
                    pl.changeSocket(thisSocket);
                    pl.sendEvent(createReconnectionEvent(pl.getId()));
                }
        }

    }

    public void activePlayerDisconnected() throws IOException {
        for(PlayerThread pl : players)
            if(!pl.isDisconnected())
                pl.sendEvent(new PassEvent());
    }

    public ReconnectionEvent createReconnectionEvent(int playerId) throws InvalidIntArgumentException
    {
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
     * @return if there aren't disconnected players
     */

    public boolean getNoDisconnected()
    {
        boolean flag = true;
        for(PlayerThread pl:players)
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
        for(PlayerThread pl: players)
            if(pl.isDisconnected())
                temp.add(pl.getName());
        return temp;
    }

    /**
     *
     * @param o Observable
     * @param arg Object
     */

    public void update(Observable o, Object arg) {

        currentEvent.set(((PlayerThread)o).getId(), (Event)arg);
        System.out.println(((Event)arg).getType()+" player "+((PlayerThread) o).getId());

        try {
            if(arg instanceof DisconnectionEvent&&!timeExpired&&(((PlayerThread)o).getId()!=turnManager.previousActive())) {
                System.out.println("Disconnected: " + ((PlayerThread) o).getId());
                if (!endInitialization) {
                    disconnectedPlayersInitializationPhase.add(((PlayerThread) o).getId());
                }
                else
                    players.get(((PlayerThread) o).getId()).setGeneralServer(generalServer);
            }
            if(((Event) arg).getType().equals("DisconnectionEvent")&&timeExpired&&(((PlayerThread)o).getId()!=turnManager.previousActive()))
                ((PlayerThread)o).resetDisconnected();
        } catch (GenericInvalidArgumentException e) {
            e.printStackTrace();
        }
        if(((Event)arg).getType().equals("SchemeSelectionEvent")) {
            try {
                setScheme((PlayerThread)o);
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            }
        }
    }


}