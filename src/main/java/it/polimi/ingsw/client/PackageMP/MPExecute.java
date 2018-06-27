package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MajorLogger;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.Events.TurnEvent;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SimpleLogger;
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
    private SimpleLogger logger;

    /**
     * Starts the CLI
     * @param stage
     * @throws Exception
     */
    @Override
    public void start(Stage stage) throws Exception {
        //starting managers
        graphicsManager = new GraphicsManager(graphics);
        graphicsManager.addObserver(this);
        connectionManager = new ConnectionManager(ip, connection);
        connectionManager.addObserver(this);

        //asking user for username and sending it to server while isn't correct
        firstFlag=false;

        logger = new SimpleLogger(0, false);

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
        endMatch();
    }

    /**
     * manages the turn
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws FullDataStructureException
     * @throws GenericInvalidArgumentException
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     */

    public void turn() throws IOException, InvalidIntArgumentException, FullDataStructureException, GenericInvalidArgumentException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException {

        //turn initialization
        connectionManager.getEvent();
        if(!currentEvent.getType().equals("ScoreEvent")) {
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
            connectionManager.sendEvent(currentEvent);
            if (((TurnEvent) currentEvent).itsMyTurn())
                itsMyTurn();
            else
                notMyTurn();
        }
        else
            endMatch=true;
    }

    /**
     * manages my turn
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     * @throws FullDataStructureException
     */
    public void itsMyTurn() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, FullDataStructureException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException {
        boolean end = false;
        while(!end)
        {
            updateGraphicsManager();
            graphicsManager.myTurn();
            connectionManager.sendEvent(toCheck);
            if(!toCheck.getType().equals("PassEvent")) {
                connectionManager.getEvent();
                if (currentEvent.isValidated()) {
                    logger.log(currentEvent.getType());
                    if (currentEvent.getType().equals("MoveEvent")) {
                        applyMove();
                        updateGraphicsManager();
                        graphicsManager.moveAccepted();
                    }
                    else {

                        if (((ToolCardEvent) currentEvent).getId() == 6)
                            toolCard6Part2();
                        if(((ToolCardEvent) currentEvent).getId()==11)
                            toolCard11Part2();
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

    /**
     * manages the other player's turns
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     * @throws FullDataStructureException
     */
    public void notMyTurn() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, FullDataStructureException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException {
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

    /**
     * applies the move event in the model
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */

    public void applyMove() throws InvalidIntArgumentException, GenericInvalidArgumentException {
        DraftPoolMP tempDraft = modelManagerMP.getDraft();
        Die tempDie = tempDraft.returnDie(((MoveEvent)currentEvent).getIndex());
        tempDraft.pickUpDie(((MoveEvent)currentEvent).getIndex());
        SchemeCard tempScheme = matchManager.getPlayer(((MoveEvent) currentEvent).getId()).getPlayerScheme();
        tempScheme.setDie(tempDie, ((MoveEvent)currentEvent).getX(), ((MoveEvent)currentEvent).getY());
        matchManager.setPlayerScheme(((MoveEvent) currentEvent).getId(), tempScheme);
        logger.log("ApplyMove scheme: "+Integer.toString(matchManager.getGraphicsUpdate()[((MoveEvent) currentEvent).getId()].getPlayerScheme().getDie(((MoveEvent) currentEvent).getX(), ((MoveEvent) currentEvent).getY()).getColor()));
    }

    /**
     * applies the tool event in the model
     * @throws InvalidIntArgumentException
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws FullDataStructureException
     */
    public void applyTool() throws InvalidIntArgumentException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException {
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

                if(tempDie.getValue()==2 && !changed) {
                    tempDie.setValue(5);
                    changed=true;
                }

                if(tempDie.getValue()==3 && !changed) {
                    tempDie.setValue(4);
                    changed=true;
                }

                if(tempDie.getValue()==4 && !changed) {
                    tempDie.setValue(3);
                    changed=true;
                }

                if(tempDie.getValue()==5 && !changed) {
                    tempDie.setValue(2);
                    changed=true;
                }

                if(tempDie.getValue()==6 && !changed) {
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
     * manages the second part of the tool card six event
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     * @throws IOException
     * @throws InvalidIntArgumentException
     */
    public void toolCard6Part2() throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, IOException, InvalidIntArgumentException, GenericInvalidArgumentException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException {
        if(((ToolCardSixEvent)currentEvent).isApplyOne())
        {
            graphicsManager.toolCard6Part2((ToolCardSixEvent)currentEvent);
            toCheck.resetValidation();
            connectionManager.sendEvent(toCheck);
            connectionManager.getEvent();
        }

    }

    /**
     * manages the second part of the tool card eleven event
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     * @throws IOException
     * @throws InvalidIntArgumentException
     */

    public void toolCard11Part2() throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, IOException, InvalidIntArgumentException, GenericInvalidArgumentException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException {
        if(((ToolCardElevenEvent)currentEvent).isFirstCheck())
            graphicsManager.toolCard11Part2((ToolCardElevenEvent)currentEvent);
        toCheck.resetValidation();
        connectionManager.sendEvent(toCheck);
        connectionManager.getEvent();
    }

    /**
     * manages the end of the match
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     * @throws IOException
     */
    private void endMatch() throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, IOException {
        boolean winner = (((ScoreEvent)currentEvent).getPlayers().get(0).getName().equals(myUsername));
        graphicsManager.showScores((ScoreEvent)currentEvent, winner);
    }

    /**
     * MPExecute Constructor
     * @param ip ip address to set
     * @param toSet parameters for mode, connection and graphics information
     * @throws GenericInvalidArgumentException
     */

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

    /**
     * updates the GraphicsManager, passing by parameters all the updated elements of the current model
     * @throws InvalidIntArgumentException
     */

    private void updateGraphicsManager() throws InvalidIntArgumentException {
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
