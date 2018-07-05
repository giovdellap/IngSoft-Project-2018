package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.*;
import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.Events.TurnEvent;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SimpleLogger;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static it.polimi.ingsw.client.ConnectionManager.State.*;
import static java.util.concurrent.TimeUnit.SECONDS;


public class Match extends Observable implements Observer {

    //connections
    private String ip;

    //managers
    private GraphicsManager graphicsManager;
    private ConnectionManager connectionManager;
    private ModelManagerMP modelManagerMP;
    private ToolRecord toolRecord;
    private MatchManager matchManager;
    private ExecutorService executor;

    //general
    private boolean firstFlag;
    private String myUsername;
    private boolean endTurn=false;
    private boolean myTurn;
    private Event currentEvent;
    private Event toCheck;
    private SimpleLogger logger;

    /**
     * Starts the CLI
     * @throws Exception
     */
    public void start() throws Exception {

        //starting managers
        graphicsManager = new GraphicsManager();
        graphicsManager.addObserver(this);

        connectionManager = new ConnectionManager(ip);
        connectionManager.addObserver(this);

        addObserver(connectionManager);
        firstFlag=false;

        logger = new SimpleLogger(3, true);

        while(!firstFlag)
        {
            graphicsManager.askUsername();
            executor=Executors.newSingleThreadExecutor();
            connectionManager.setEvent(toCheck);
            connectionManager.setState(ConnectionManager.State.SENDANDRECEIVE);
            executor.execute(connectionManager);
            executor.shutdown();
            executor.awaitTermination(15, SECONDS);

            firstFlag=currentEvent.isValidated();
            if(firstFlag)
                myUsername=((UsernameEvent)currentEvent).getUserName();

        }

        graphicsManager.waitForPlayers();
        modelManagerMP = new ModelManagerMP();

        //model initialization
        connectionManager.setState(RECEIVE);
        executor=Executors.newSingleThreadExecutor();
        executor.execute(connectionManager);
        executor.shutdown();
        executor.awaitTermination(1, TimeUnit.MINUTES);

        modelManagerMP.setMyPrivObj(((ModelInitializationEvent)currentEvent).getPrivateObjective());
        modelManagerMP.setPubObjs(((ModelInitializationEvent)currentEvent).getPublicObjectives());
        modelManagerMP.setTempSchemes(((ModelInitializationEvent)currentEvent).getSchemes()[0], ((ModelInitializationEvent)currentEvent).getSchemes()[1]);
        toolRecord = new ToolRecord(((ModelInitializationEvent)currentEvent).getToolIds());


        //SCHEME SELECTION
        PublicObjectiveMP[] tempPubObjs = new PublicObjectiveMP[3];
        for(int i=0;i<3;i++)
            tempPubObjs[i]=modelManagerMP.getPubObjs(i);

        firstFlag=false;
        while(!firstFlag)
        {
            graphicsManager.getSelectedScheme(modelManagerMP.getTempScheme(0), modelManagerMP.getTempScheme(1), myUsername, modelManagerMP.getMyPrObj(), tempPubObjs, toolRecord.getID());
            connectionManager.setState(ConnectionManager.State.SENDANDRECEIVE);
            connectionManager.setEvent(toCheck);
            executor=Executors.newSingleThreadExecutor();
            executor.execute(connectionManager);
            executor.shutdown();
            executor.awaitTermination(6, SECONDS);

            firstFlag = currentEvent.isValidated();
            if(firstFlag)
                modelManagerMP.setMyScheme(((SchemeSelectionEvent)currentEvent).getId(),((SchemeSelectionEvent)currentEvent).getFb());
        }
        graphicsManager.waitForPlayers2();

        connectionManager.setState(RECEIVE);
        executor=Executors.newSingleThreadExecutor();
        executor.execute(connectionManager);
        executor.shutdown();
        executor.awaitTermination(15, SECONDS);

        matchManager = new MatchManager((Initialization2Event)currentEvent, myUsername);

        game();
    }

    /**
     * manages the turn
     * @throws InvalidIntArgumentException
     * @throws FullDataStructureException
     * @throws GenericInvalidArgumentException
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     */

    public void game() throws FullDataStructureException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException, InterruptedException
    {
        logger.debugLog("game start");
        connectionManager.setState(RECEIVE);
        executor=Executors.newSingleThreadExecutor();
        executor.execute(connectionManager);
        executor.shutdown();
        executor.awaitTermination(20, SECONDS);

        while (!currentEvent.getType().equals("ScoreEvent"))
        {
            logger.debugLog("turn start");
            updateGraphicsManager();
            endTurn=false;

            updateGraphicsManager();
            matchManager.setRound(((TurnEvent) currentEvent).getRound());
            if (!((TurnEvent) currentEvent).getNoDisconnected())
                matchManager.setDisconnectedPlayers(((TurnEvent) currentEvent).getDisconnected());

            matchManager.setActivePlayer(((TurnEvent) currentEvent).getActive());
            modelManagerMP.setDraft(((TurnEvent) currentEvent).getDraft());
            if (((TurnEvent) currentEvent).isNextRound())
                modelManagerMP.addRound(((TurnEvent) currentEvent).getLastRound());
            toolRecord.setTokens(((TurnEvent) currentEvent).getToolsUpdate());

            updateGraphicsManager();
            currentEvent.validate();


            if (((TurnEvent) currentEvent).itsMyTurn())
            {
                myTurn=true;
                itsMyTurn();
            }
            else {
                myTurn=false;
                notMyTurn();
            }
        }
        gameEnded();
    }

    /**
     * manages my turn
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     * @throws FullDataStructureException
     */
    public void itsMyTurn() throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, FullDataStructureException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException, InterruptedException
    {
        while(!endTurn)
        {
            connectionManager.stopSleep();
            logger.debugLog("itsMyTurn while 1");
            updateGraphicsManager();
            executor=Executors.newFixedThreadPool(2);

            connectionManager.setState(ConnectionManager.State.SLEEP);
            graphicsManager.setState(GraphicsManager.State.ASKMAIN);
            if(currentEvent.getType().equals("MoveEvent"))
            {
                if(currentEvent.isValidated())
                {
                    graphicsManager.setSecondaryState(GraphicsManager.SecondaryState.MOVEACCEPTED);
                    applyMove();
                    updateGraphicsManager();
                }
                else
                    graphicsManager.setSecondaryState(GraphicsManager.SecondaryState.MOVEREFUSED);
            }
            if(currentEvent instanceof ToolCardEvent)
            {
                if(currentEvent.isValidated())
                {
                    graphicsManager.setSecondaryState(GraphicsManager.SecondaryState.TOOLACCEPTED);
                    applyTool();
                    updateGraphicsManager();
                }
                else
                    graphicsManager.setSecondaryState(GraphicsManager.SecondaryState.TOOLREFUSED);
            }
            if(!currentEvent.getType().equals("MoveEvent")||!(currentEvent instanceof ToolCardEvent))
                graphicsManager.setSecondaryState(GraphicsManager.SecondaryState.VIRGIN);

            executor.execute(connectionManager);
            executor.execute(graphicsManager);

            executor.shutdown();
            executor.awaitTermination(70, SECONDS);

            logger.debugLog("itsMyTurn while 2");

        }
    }

    /**
     * manages other player's turn
     * @throws InterruptedException
     */
    public void notMyTurn() throws InterruptedException
    {
        graphicsManager.setState(GraphicsManager.State.SHOWTURN);
        updateGraphicsManager();
        executor=Executors.newFixedThreadPool(1);
        executor.execute(graphicsManager);
        executor.shutdown();
        executor.awaitTermination(5, SECONDS);

        while(!endTurn)
        {
            connectionManager.setState(RECEIVE);
            executor=Executors.newFixedThreadPool(1);
            executor.execute(connectionManager);
            executor.shutdown();
            executor.awaitTermination(45, SECONDS);
        }
    }

    public void gameEnded() throws InterruptedException
    {
        graphicsManager.setReceivedEvent(currentEvent);
        graphicsManager.setState(GraphicsManager.State.SHOWSCORE);
        executor=Executors.newFixedThreadPool(1);
        executor.execute(graphicsManager);
        executor.shutdown();
        executor.awaitTermination(5, SECONDS);
    }

    /**
     * applies the move event in the model
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */

    public void applyMove() throws InvalidIntArgumentException, GenericInvalidArgumentException
    {
        DraftPoolMP tempDraft = modelManagerMP.getDraft();
        Die tempDie = tempDraft.returnDie(((MoveEvent)currentEvent).getIndex());
        tempDraft.pickUpDie(((MoveEvent)currentEvent).getIndex());
        SchemeCard tempScheme = matchManager.getPlayer(((MoveEvent) currentEvent).getId()).getPlayerScheme();
        tempScheme.setDie(tempDie, ((MoveEvent)currentEvent).getX(), ((MoveEvent)currentEvent).getY());
        matchManager.setPlayerScheme(((MoveEvent) currentEvent).getId(), tempScheme);
        logger.debugLog("ApplyMove scheme: "+Integer.toString(matchManager.getGraphicsUpdate()[((MoveEvent) currentEvent).getId()].getPlayerScheme().getDie(((MoveEvent) currentEvent).getX(), ((MoveEvent) currentEvent).getY()).getColor()));
    }

    /**
     * applies the tool event in the model
     * @throws InvalidIntArgumentException
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     */
    public void applyTool() throws InvalidIntArgumentException, GenericInvalidArgumentException
    {
        int minus = toolRecord.getTokensDecrease(((ToolCardEvent)currentEvent).getId());
        int tokens = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getTokens()-minus;
        matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).setTokens(tokens);


        switch (((ToolCardEvent)currentEvent).getId())
        {
            case (1):
            {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = tempDraft.returnDie(((ToolCardOneEvent)currentEvent).getIndex());
                tempDraft.pickUpDie(((ToolCardOneEvent)currentEvent).getIndex());
                int value = tempDie.getValue();
                if(((ToolCardOneEvent)currentEvent).getAction()=='+')
                    tempDie.setValue(value+1);
                else
                    tempDie.setValue(value-1);
                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardOneEvent)currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.setDie(tempDie, ((ToolCardOneEvent)currentEvent).getX(), ((ToolCardOneEvent)currentEvent).getY());
                matchManager.setPlayerScheme(((ToolCardOneEvent) currentEvent).getPlayer(), tempScheme);
                modelManagerMP.setDraft(tempDraft);
                break;
            }
            case (2):
            {
                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.shiftDie(((ToolCardTwoThreeEvent)currentEvent).getX0(),((ToolCardTwoThreeEvent) currentEvent).getY0() , ((ToolCardTwoThreeEvent) currentEvent).getX1(), ((ToolCardTwoThreeEvent) currentEvent).getY1() );
                matchManager.setPlayerScheme(((ToolCardTwoThreeEvent) currentEvent).getPlayer(),tempScheme);
                break;

            }
            case (3):
            {
                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.shiftDie(((ToolCardTwoThreeEvent)currentEvent).getX0(),((ToolCardTwoThreeEvent) currentEvent).getY0() , ((ToolCardTwoThreeEvent) currentEvent).getX1(), ((ToolCardTwoThreeEvent) currentEvent).getY1());
                matchManager.setPlayerScheme(((ToolCardTwoThreeEvent) currentEvent).getPlayer(),tempScheme);
                break;
            }
            case (4):
            {
                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.shiftDie(((ToolCardFourEvent)currentEvent).getX01(),((ToolCardFourEvent) currentEvent).getY01(),((ToolCardFourEvent) currentEvent).getX11(),((ToolCardFourEvent) currentEvent).getY11());
                tempScheme.shiftDie(((ToolCardFourEvent) currentEvent).getX02(),((ToolCardFourEvent) currentEvent).getY02(),((ToolCardFourEvent) currentEvent).getX22(),((ToolCardFourEvent) currentEvent).getY22());
                matchManager.setPlayerScheme(((ToolCardFourEvent) currentEvent).getPlayer(),tempScheme);
                break;
            }
            case (5):
            {

                //pick up die from Draftpool
                ToolCardFiveEvent event = (ToolCardFiveEvent)currentEvent;
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = new Die(tempDraft.returnDie(event.getIndex()).getColor());
                tempDie.setValue(tempDraft.returnDie(event.getIndex()).getValue());
                tempDraft.pickUpDie(event.getIndex());

                //replacing the roundtrack die with that die
                RoundDiceMP tempRD = modelManagerMP.getTrack().returnNTurnRoundDice(event.getTurn());
                Die tempRDDie = modelManagerMP.getTrack().returnNTurnRoundDice(event.getTurn()).getDie(event.getPos());
                tempRD.deleteDie(event.getPos());
                tempRD.addPos(event.getPos(), tempDie);

                //setting the die in the schemecard
                SchemeCard tempScheme = matchManager.getPlayer(event.getPlayer()).getPlayerScheme();
                tempScheme.setDie(tempRDDie, event.getX(), event.getY());

                //updating everything
                modelManagerMP.setDraft(tempDraft);
                modelManagerMP.setTrack(tempRD.getDiceVector(), event.getTurn());
                matchManager.setPlayerScheme(event.getPlayer(), tempScheme);
                break;

            }
            case (6):
            {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = new Die(tempDraft.returnDie(((ToolCardSixEvent)currentEvent).getIndex()).getColor());
                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getPlayerScheme();
                tempDie.setValue(((ToolCardSixEvent)currentEvent).getNewValue());
                if(((ToolCardSixEvent) currentEvent).isApplyTwo()) {
                    tempDraft.pickUpDie(((ToolCardSixEvent) currentEvent).getIndex());
                    tempScheme.setDie(tempDie,((ToolCardSixEvent) currentEvent).getX(),((ToolCardSixEvent) currentEvent).getY());
                    matchManager.setPlayerScheme(((ToolCardSixEvent) currentEvent).getPlayer(),tempScheme);
                    modelManagerMP.setDraft(tempDraft);
                }
                else {
                    tempDraft.replaceDie(((ToolCardSixEvent) currentEvent).getIndex(),tempDie);
                    modelManagerMP.setDraft(tempDraft);
                }

                break;

            }

            case (7): {
                modelManagerMP.setDraft(((ToolCardSevenEvent)currentEvent).getDice());
                break;
            }

            case (8): {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = tempDraft.returnDie(((ToolCardEightNineTenEvent)currentEvent).getIndex());
                tempDraft.pickUpDie(((ToolCardEightNineTenEvent)currentEvent).getIndex());
                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardEightNineTenEvent) currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.setDie(tempDie, ((ToolCardEightNineTenEvent)currentEvent).getX(), ((ToolCardEightNineTenEvent)currentEvent).getY());
                matchManager.setPlayerScheme(((ToolCardEightNineTenEvent) currentEvent).getPlayer(), tempScheme);
                break;
            }

            case (9): {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = tempDraft.returnDie(((ToolCardEightNineTenEvent)currentEvent).getIndex());
                tempDraft.pickUpDie(((ToolCardEightNineTenEvent)currentEvent).getIndex());
                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardEightNineTenEvent) currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.setDie(tempDie, ((ToolCardEightNineTenEvent)currentEvent).getX(), ((ToolCardEightNineTenEvent)currentEvent).getY());
                matchManager.setPlayerScheme(((ToolCardEightNineTenEvent) currentEvent).getPlayer(), tempScheme);
                break;
            }

            case (10): {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = tempDraft.returnDie(((ToolCardEightNineTenEvent)currentEvent).getIndex());
                boolean changed = false;

                if(tempDie.getValue()==1 && !changed)
                {
                    tempDie.setValue(6);
                    changed=true;
                }

                if(tempDie.getValue()==2 && !changed)
                {
                    tempDie.setValue(5);
                    changed=true;
                }

                if(tempDie.getValue()==3 && !changed)
                {
                    tempDie.setValue(4);
                    changed=true;
                }

                if(tempDie.getValue()==4 && !changed)
                {
                    tempDie.setValue(3);
                    changed=true;
                }

                if(tempDie.getValue()==5 && !changed)
                {
                    tempDie.setValue(2);
                    changed=true;
                }

                if(tempDie.getValue()==6 && !changed)
                {
                    tempDie.setValue(1);
                    changed=true;
                }

                tempDraft.pickUpDie(((ToolCardEightNineTenEvent)currentEvent).getIndex());
                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardEightNineTenEvent) currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.setDie(tempDie, ((ToolCardEightNineTenEvent)currentEvent).getX(), ((ToolCardEightNineTenEvent)currentEvent).getY());
                matchManager.setPlayerScheme(((ToolCardEightNineTenEvent) currentEvent).getPlayer(), tempScheme);
                break;
            }

            case (11): {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardElevenEvent) currentEvent).getPlayer()).getPlayerScheme();
                Die tempDie = new Die(((ToolCardElevenEvent)currentEvent).getNewColor());
                tempDie.setValue(((ToolCardElevenEvent) currentEvent).getNewValue());

                if(((ToolCardElevenEvent)currentEvent).isApplyOne())
                    tempDraft.replaceDie(((ToolCardElevenEvent) currentEvent).getIndex(), tempDie);

                else {
                    tempScheme.setDie(tempDie, ((ToolCardElevenEvent) currentEvent).getX(),((ToolCardElevenEvent) currentEvent).getY());
                    tempDraft.pickUpDie(((ToolCardElevenEvent) currentEvent).getIndex());
                }

                modelManagerMP.setDraft(tempDraft);
                if(((ToolCardElevenEvent) currentEvent).isApplyTwo())
                    matchManager.setPlayerScheme(((ToolCardElevenEvent) currentEvent).getPlayer(), tempScheme);
                break;
            }

            case (12): {

                SchemeCard tempScheme = matchManager.getPlayer(((ToolCardTwelveEvent)currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.shiftDie(((ToolCardTwelveEvent) currentEvent).getX01(), ((ToolCardTwelveEvent) currentEvent).getY01(),((ToolCardTwelveEvent) currentEvent).getX11() ,((ToolCardTwelveEvent) currentEvent).getY11());

                if(!((ToolCardTwelveEvent) currentEvent).isOnlyOne())
                {
                    tempScheme.shiftDie(((ToolCardTwelveEvent) currentEvent).getX02(),((ToolCardTwelveEvent) currentEvent).getY02() , ((ToolCardTwelveEvent) currentEvent).getX22(),((ToolCardTwelveEvent) currentEvent).getY22() );
                }

                matchManager.setPlayerScheme(((ToolCardTwelveEvent) currentEvent).getPlayer(), tempScheme);
                break;
            }

        }
    }



    /**
     * Match Constructor
     * @param ip ip address to set
     */

    public Match(String ip)
    {
        //constructor
        //receives ip and settings from launcher
        this.ip=ip;

    }

    /**
     * updates the GraphicsManager, passing by parameters all the updated elements of the current model
     */

    private void updateGraphicsManager()
    {
        int me=0;
        for(int i=0;i<matchManager.getGraphicsUpdate().length;i++)
            if(matchManager.getGraphicsUpdate()[i].itsMe())
                me=i;
        graphicsManager.gmUpdate(matchManager.getGraphicsUpdate(), modelManagerMP.getDraft(), modelManagerMP.getTrack(), toolRecord.getTokens(), matchManager.getActivePlayer(), me, matchManager.getRound(), matchManager.getDisconnectedPlayers());
    }

    /**
     * updates listeners
     * @param o observable
     * @param arg object
     */

    public void update(Observable o, Object arg)
    {
        logger.debugLog("update - myTurn: "+Boolean.toString(myTurn));

        if(o==graphicsManager) {
            toCheck = (Event) arg;
            logger.debugLog("Update from graphicsManager");
        }

        if(o==connectionManager) {
            currentEvent = (Event) arg;
            logger.debugLog("Update from connectionManager");
        }

        if(((Event)arg).getType().equals("TurnEvent")) {

            connectionManager.stopSleep();
            if(toCheck.getType().equals("PassEvent"))
                graphicsManager.stopView();
            executor.shutdown();
            endTurn=true;

            logger.log("TurnEvent update : "+executor.toString());
        }

        if(myTurn&&o==graphicsManager&&(!((Event)arg).getType().equals("PassEvent")))
            myTurnAction();

        if(myTurn&&o==graphicsManager&&((Event)arg).getType().equals("PassEvent"))
            pass();

        if(myTurn&&o==connectionManager&&((Event)arg).getType().equals("ToolCardSixEvent"))
            tool6Part2();
        if(myTurn&&o==connectionManager&&((Event)arg).getType().equals("ToolCardElevenEvent"))
            tool11Part2();
        if(!myTurn&&((Event)arg).getType().equals("MoveEvent"))
        {
            try {
                showMove();
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (GenericInvalidArgumentException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(!myTurn&&(arg instanceof ToolCardEvent))
        {
            try {
                showTool();
            } catch (GenericInvalidArgumentException e) {
                e.printStackTrace();
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


    }

    public void myTurnAction()
    {
        connectionManager.stopSleep();

        logger.debugLog("myTurnAction");

        executor=Executors.newFixedThreadPool(1);
        connectionManager.setState(ConnectionManager.State.SENDANDRECEIVE);
        connectionManager.setEvent(toCheck);
        executor.execute(connectionManager);
    }

    public void pass()
    {
        connectionManager.stopSleep();
        logger.debugLog("PASS!!!!!!");

        executor=Executors.newFixedThreadPool(1);
        connectionManager.setEvent(toCheck);
        connectionManager.setState(SENDANDRECEIVE);
        executor.execute(connectionManager);
        executor.shutdown();
        endTurn=true;
    }

    public void tool6Part2()
    {
        executor.shutdownNow();
        graphicsManager.setState(GraphicsManager.State.ASKTOOL6);
        graphicsManager.setReceivedEvent(currentEvent);
        connectionManager.setState(ConnectionManager.State.SLEEP);
        executor=Executors.newFixedThreadPool(2);
        executor.execute(graphicsManager);
        executor.execute(connectionManager);
    }

    public void tool11Part2()
    {
        executor.shutdownNow();
        graphicsManager.setState(GraphicsManager.State.ASKTOOL11);
        graphicsManager.setReceivedEvent(currentEvent);
        connectionManager.setState(ConnectionManager.State.SLEEP);
        executor=Executors.newFixedThreadPool(2);
        executor.execute(graphicsManager);
        executor.execute(connectionManager);
    }

    public void showMove() throws InvalidIntArgumentException, GenericInvalidArgumentException, InterruptedException
    {
        graphicsManager.setState(GraphicsManager.State.SHOWMOVE);
        graphicsManager.setReceivedEvent(currentEvent);
        applyMove();
        updateGraphicsManager();
        executor=Executors.newFixedThreadPool(1);
        executor.execute(graphicsManager);
        executor.shutdown();
        executor.awaitTermination(10, SECONDS);
    }

    public void showTool() throws GenericInvalidArgumentException, InvalidIntArgumentException, InterruptedException
    {
        graphicsManager.setState(GraphicsManager.State.SHOWTOOL);
        graphicsManager.setReceivedEvent(currentEvent);
        applyTool();
        updateGraphicsManager();
        executor=Executors.newFixedThreadPool(1);
        executor.execute(graphicsManager);
        executor.shutdown();
        executor.awaitTermination(5, SECONDS);
    }


}
