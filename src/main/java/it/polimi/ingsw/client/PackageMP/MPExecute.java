package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MajorLogger;
import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.RoundDiceMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.Events.TurnEvent;
import it.polimi.ingsw.commons.FcknSimpleLogger;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;

public class MPExecute extends Application implements Observer {

    MajorLogger mpLogger;

    //connections
    private String ip;

    //settings
    private int mode; // 1 = SP, 2 = MP, 3 = DEMO
    private int connection; // 1 = SOCKET, 2 = RMIClient
    private int graphics; // 1 = GUI, 2 = CLI

    //managers
    private GraphicsManager graphicsManager;
    private ConnectionManager connectionManager;
    private ModelManagerMP modelManagerMP;
    private ToolRecord toolRecord;
    private MatchManager matchManager;

    //general
    private boolean firstFlag;
    private String myUsername;
    private boolean endMatch=false;
    private Event currentEvent;
    private Event toCheck;
    private FcknSimpleLogger logger;

    @Override
    public void start(Stage stage) throws Exception {
        //starting managers
        graphicsManager = new GraphicsManager(graphics);
        graphicsManager.addObserver(this);
        connectionManager = new ConnectionManager(ip, connection);
        connectionManager.addObserver(this);

        //asking user for username and sending it to server while isn't correct
        firstFlag=false;

        logger = new FcknSimpleLogger(0, false);

        while(!firstFlag)
        {
            graphicsManager.askUsername();
            connectionManager.sendEvent(toCheck);
            connectionManager.getEvent();
            firstFlag=currentEvent.isValidated();
            if(firstFlag)
                myUsername=((UsernameEvent)currentEvent).getUserName();

        }

        graphicsManager.waitForPlayers();
        modelManagerMP = new ModelManagerMP();
        mpLogger.majorLog("ModelManager started");

        //model initialization
        connectionManager.getEvent();
        modelManagerMP.setMyPrivObj(((ModelInitializationEvent)currentEvent).getPrivateObjective());
        modelManagerMP.setPubObjs(((ModelInitializationEvent)currentEvent).getPublicObjectives());
        modelManagerMP.setTempSchemes(((ModelInitializationEvent)currentEvent).getSchemes()[0], ((ModelInitializationEvent)currentEvent).getSchemes()[1]);
        toolRecord = new ToolRecord(((ModelInitializationEvent)currentEvent).getToolIds());


        //SCHEME SELECTION
        PublicObjectiveMP[] tempPubObjs = new PublicObjectiveMP[3];
        for(int i=0;i<3;i++)
            tempPubObjs[i]=modelManagerMP.getPubObjs(i);

        firstFlag=false;
        while(!firstFlag) {
            graphicsManager.getSelectedScheme(modelManagerMP.getTempScheme(0), modelManagerMP.getTempScheme(1), myUsername, modelManagerMP.getMyPrObj(), tempPubObjs, toolRecord.getID());
            //sending scheme and get confirmation
            connectionManager.sendEvent(toCheck);
            connectionManager.getEvent();

            firstFlag = currentEvent.isValidated();
            if(firstFlag)
                modelManagerMP.setMyScheme(((SchemeSelectionEvent)currentEvent).getId(),((SchemeSelectionEvent)currentEvent).getFb());

        }
        graphicsManager.waitForPlayers2();
        connectionManager.getEvent();

        System.out.println("type:"+currentEvent.getType());
        System.out.println("validate:"+Boolean.toString(currentEvent.isValidated()));
        System.out.println("username:"+ myUsername);

        matchManager = new MatchManager((Initialization2Event)currentEvent, myUsername);

        while(!endMatch)
            turn();
    }

    public void turn() throws IOException, InvalidIntArgumentException, FullDataStructureException, GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException {

        //turn initialization
        connectionManager.getEvent();
        matchManager.setRound(((TurnEvent)currentEvent).getRound());
        if(!((TurnEvent)currentEvent).getNoDisconnected())
            matchManager.setDisconnectedPlayers(((TurnEvent)currentEvent).getDisconnected());

        matchManager.setActivePlayer(((TurnEvent) currentEvent).getActive());
        modelManagerMP.setDraft(((TurnEvent) currentEvent).getDraft());
        if(((TurnEvent) currentEvent).isNextRound())
            modelManagerMP.setTrack(((TurnEvent) currentEvent).getLastRound(), ((TurnEvent) currentEvent).getRound());
        toolRecord.setTokens(((TurnEvent) currentEvent).getToolsUpdate());

        updateGraphicsManager();
        currentEvent.validate();
        connectionManager.sendEvent(currentEvent);
        if(((TurnEvent) currentEvent).itsMyTurn())
            itsMyTurn();
        else
            notMyTurn();

    }

    public void itsMyTurn() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, FullDataStructureException {
        boolean end = false;
        while(!end)
        {
            updateGraphicsManager();
            graphicsManager.myTurn();
            connectionManager.sendEvent(toCheck);
            if(!toCheck.getType().equals("PassEvent")) {
                if (currentEvent.isValidated()) {
                    connectionManager.getEvent();
                    logger.log("padre pio inculato?:" + Boolean.toString(currentEvent.isValidated()));
                    if (currentEvent.getType().equals("MoveEvent")) {
                        applyMove();
                        updateGraphicsManager();
                        graphicsManager.moveAccepted();
                    }
                    if (currentEvent instanceof ToolCardEvent) {
                        if (((ToolCardEvent) currentEvent).getId() == 6)
                            toolCard6Part2();
                        applyTool();
                        updateGraphicsManager();
                        graphicsManager.toolAccepted();
                    }
                } else {
                    if (currentEvent.getType().equals("MoveEvent"))
                        graphicsManager.moveRefused();
                }
            }
            else
                end=true;
        }
    }
    public void notMyTurn() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, FullDataStructureException {
        updateGraphicsManager();
        graphicsManager.notMyTurn();
        connectionManager.getEvent();
        while(!currentEvent.getType().equals("PassEvent"))
        {
            if (currentEvent.getType().equals("MoveEvent"))
            {
                applyMove();
                updateGraphicsManager();
                graphicsManager.showMove((MoveEvent)currentEvent);
            }
            if (currentEvent instanceof ToolCardEvent)
            {
                applyTool();
                updateGraphicsManager();
                graphicsManager.showTool((ToolCardEvent)currentEvent);
            }
            connectionManager.getEvent();
        }
    }

    //event application in model
    public void applyMove() throws InvalidIntArgumentException, GenericInvalidArgumentException {
        DraftPoolMP tempDraft = modelManagerMP.getDraft();
        Die tempDie = tempDraft.returnDie(((MoveEvent)currentEvent).getIndex());
        tempDraft.pickUpDie(((MoveEvent)currentEvent).getIndex());
        SchemeCardMP tempScheme = matchManager.getPlayer(((MoveEvent) currentEvent).getId()).getPlayerScheme();
        tempScheme.setDie(tempDie, ((MoveEvent)currentEvent).getX(), ((MoveEvent)currentEvent).getY());
        matchManager.setPlayerScheme(((MoveEvent) currentEvent).getId(), tempScheme);
    }

    public void applyTool() throws InvalidIntArgumentException, it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException {
        switch (((ToolCardEvent)currentEvent).getId())
        {
            case (1):
            {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = tempDraft.returnDie(((ToolCardOneEvent)currentEvent).getIndex());
                tempDraft.pickUpDie(((ToolCardOneEvent)currentEvent).getIndex());
                int value = tempDie.getValue();
                if(((ToolCardOneEvent)currentEvent).getAction()=='+')
                    tempDie.setValueTest(value+1);
                else
                    tempDie.setValueTest(value-1);
                SchemeCardMP tempScheme = matchManager.getPlayer(((ToolCardOneEvent)currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.setDie(tempDie, ((ToolCardOneEvent)currentEvent).getX(), ((ToolCardOneEvent)currentEvent).getY());
                matchManager.setPlayerScheme(((ToolCardOneEvent) currentEvent).getPlayer(), tempScheme);
                break;
            }
            case (2):
            {
                SchemeCardMP tempScheme = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getPlayerScheme();
                Die tempDie = tempScheme.getDie(((ToolCardTwoThreeEvent)currentEvent).getX0(), ((ToolCardTwoThreeEvent)currentEvent).getY0());
                tempScheme.disableDie(((ToolCardTwoThreeEvent)currentEvent).getX0(), ((ToolCardTwoThreeEvent)currentEvent).getY0());
                tempScheme.setDie(tempDie, ((ToolCardTwoThreeEvent)currentEvent).getX1(), ((ToolCardTwoThreeEvent)currentEvent).getY1());
                matchManager.setPlayerScheme(((ToolCardTwoThreeEvent) currentEvent).getPlayer(),tempScheme);
                break;
            }
            case (3):
            {
                SchemeCardMP tempScheme = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getPlayerScheme();
                Die tempDie = tempScheme.getDie(((ToolCardTwoThreeEvent)currentEvent).getX0(), ((ToolCardTwoThreeEvent)currentEvent).getY0());
                tempScheme.disableDie(((ToolCardTwoThreeEvent)currentEvent).getX0(), ((ToolCardTwoThreeEvent)currentEvent).getY0());
                tempScheme.setDie(tempDie, ((ToolCardTwoThreeEvent)currentEvent).getX1(), ((ToolCardTwoThreeEvent)currentEvent).getY1());
                matchManager.setPlayerScheme(((ToolCardTwoThreeEvent) currentEvent).getPlayer(),tempScheme);
                break;
            }
            case (4):
            {
                SchemeCardMP tempScheme = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getPlayerScheme();
                Die tempDie1 = tempScheme.getDie(((ToolCardFourEvent)currentEvent).getX01(), ((ToolCardFourEvent)currentEvent).getY01());
                Die tempDie2 = tempScheme.getDie(((ToolCardFourEvent)currentEvent).getX02(), ((ToolCardFourEvent)currentEvent).getY02());
                tempScheme.disableDie(((ToolCardFourEvent)currentEvent).getX01(), ((ToolCardFourEvent)currentEvent).getY01());
                tempScheme.disableDie(((ToolCardFourEvent)currentEvent).getX02(), ((ToolCardFourEvent)currentEvent).getY02());
                tempScheme.setDie(tempDie1, ((ToolCardFourEvent)currentEvent).getX11(), ((ToolCardFourEvent)currentEvent).getY11());
                tempScheme.setDie(tempDie2, ((ToolCardFourEvent)currentEvent).getX22(), ((ToolCardFourEvent)currentEvent).getY22());
                matchManager.setPlayerScheme(((ToolCardFourEvent) currentEvent).getPlayer(),tempScheme);
                break;
            }
            case (5):
            {
                RoundDiceMP tempRoundDice = modelManagerMP.getTrack().returnNTurnRoundDice(((ToolCardFiveEvent)currentEvent).getTurn());
                Die tempRoundDie = tempRoundDice.getDie(((ToolCardFiveEvent)currentEvent).getPos());
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDraftDie = tempDraft.replaceDie(((ToolCardFiveEvent)currentEvent).getIndex(), tempRoundDie);
                tempRoundDice.addDie(tempDraftDie);
                modelManagerMP.getTrack().setSpecificRoundDice(tempRoundDice, ((ToolCardFiveEvent)currentEvent).getTurn());
                modelManagerMP.setDraft(tempDraft);
                break;
            }
            case (6):
            {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = tempDraft.returnDie(((ToolCardSixEvent)currentEvent).getIndex());
                SchemeCardMP tempScheme = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getPlayerScheme();
                tempDie.setValueTest(((ToolCardSixEvent)currentEvent).getNewValue());
                if(((ToolCardSixEvent) currentEvent).isApplyOne()) {
                    tempDraft.pickUpDie(((ToolCardSixEvent) currentEvent).getIndex());
                    tempScheme.setDie(tempDie,((ToolCardSixEvent) currentEvent).getX(),((ToolCardSixEvent) currentEvent).getY());
                    matchManager.setPlayerScheme(((ToolCardTwoThreeEvent) currentEvent).getPlayer(),tempScheme);
                    modelManagerMP.setDraft(tempDraft);
                }

                if(((ToolCardSixEvent) currentEvent).isApplyTwo()) {
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
                break;
            }

            case(9): {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = tempDraft.returnDie(((ToolCardEightNineTenEvent)currentEvent).getIndex());
                SchemeCardMP tempScheme = matchManager.getPlayer(((ToolCardEvent)currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.setDie(tempDie,((ToolCardEightNineTenEvent) currentEvent).getX(),((ToolCardEightNineTenEvent) currentEvent).getY());
                tempDraft.pickUpDie(((ToolCardEightNineTenEvent) currentEvent).getIndex());
                modelManagerMP.setDraft(tempDraft);
                matchManager.setPlayerScheme(((ToolCardEightNineTenEvent) currentEvent).getPlayer(),tempScheme);
                break;

            }

            case (10): {
                DraftPoolMP tempDraft = modelManagerMP.getDraft();
                Die tempDie = tempDraft.returnDie(((ToolCardEightNineTenEvent)currentEvent).getIndex());
                SchemeCardMP tempScheme = matchManager.getPlayer(((ToolCardEvent)currentEvent).getPlayer()).getPlayerScheme();
                tempScheme.setDie(tempDie,((ToolCardEightNineTenEvent) currentEvent).getX(),((ToolCardEightNineTenEvent) currentEvent).getY());
                tempDraft.pickUpDie(((ToolCardEightNineTenEvent) currentEvent).getIndex());
                modelManagerMP.setDraft(tempDraft);
                matchManager.setPlayerScheme(((ToolCardEightNineTenEvent) currentEvent).getPlayer(),tempScheme);
                break;

            }

            case (11): {

                break;
            }

            case (12): {

                SchemeCardMP tempScheme = matchManager.getPlayer(((ToolCardEvent) currentEvent).getPlayer()).getPlayerScheme();
                Die tempDie1 = tempScheme.getDie(((ToolCardTwelveEvent)currentEvent).getX01(), ((ToolCardTwelveEvent)currentEvent).getY01());
                Die tempDie2 = tempScheme.getDie(((ToolCardTwelveEvent)currentEvent).getX02(), ((ToolCardTwelveEvent)currentEvent).getY02());
                tempScheme.disableDie(((ToolCardTwelveEvent)currentEvent).getX01(), ((ToolCardTwelveEvent)currentEvent).getY01());
                tempScheme.disableDie(((ToolCardTwelveEvent)currentEvent).getX02(), ((ToolCardTwelveEvent)currentEvent).getY02());
                tempScheme.setDie(tempDie1, ((ToolCardTwelveEvent)currentEvent).getX11(), ((ToolCardTwelveEvent)currentEvent).getY11());
                tempScheme.setDie(tempDie2, ((ToolCardTwelveEvent)currentEvent).getX22(), ((ToolCardTwelveEvent)currentEvent).getY22());
                matchManager.setPlayerScheme(((ToolCardTwelveEvent) currentEvent).getPlayer(),tempScheme);
                break;

            }


        }
    }

    public void toolCard6Part2() throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, IOException {
        graphicsManager.toolCard6Part2((ToolCardSixEvent)currentEvent);
        connectionManager.sendEvent(currentEvent);
        connectionManager.getEvent();
    }



    public MPExecute(String ip, int[] toSet) throws GenericInvalidArgumentException {
        //constructor
        //receives ip and settings from launcher
        mode=toSet[0];
        connection=toSet[1];
        graphics=toSet[2];
        this.ip=ip;

        mpLogger = new MajorLogger();
        mpLogger.majorLog("MPExecute Logger started");
    }

    private void updateGraphicsManager()
    {
        int me=0;
        for(int i=0;i<matchManager.getGraphicsUpdate().length;i++)
            if(matchManager.getGraphicsUpdate()[i].itsMe())
                me=i;
        graphicsManager.gmUpdate(matchManager.getGraphicsUpdate(), modelManagerMP.getDraft(), modelManagerMP.getTrack(), toolRecord.getTokens(), matchManager.getActivePlayer(), me, matchManager.getRound(), matchManager.getDisconnectedPlayers());
    }



    public void update(Observable o, Object arg) {

        if(o.getClass()==GraphicsManager.class)
        {
            toCheck=(Event)arg;
        }
        if(o.getClass()==ConnectionManager.class)
        {
            currentEvent=(Event)arg;
        }

    }
}
