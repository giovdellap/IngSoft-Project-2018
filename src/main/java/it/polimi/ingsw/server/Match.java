package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.Events.TurnEvent;
import it.polimi.ingsw.commons.FcknSimpleLogger;
import it.polimi.ingsw.server.Connection.GeneralServer;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;
import it.polimi.ingsw.server.ToolCards.*;

import java.io.IOException;
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
    private GorgeousLobbyCreator lobbyCreator;

    //datas
    private ArrayList<Player> players;
    private Event currentEvent;

    //score
    private String[] ranking;
    private int[][] rankingSpecs;

    private FcknSimpleLogger logger;

    //COSTRUCTOR

    public Match() throws IOException, InvalidIntArgumentException, InvalidinSocketException, GenericInvalidArgumentException, FullDataStructureException {

        logger = new FcknSimpleLogger(3, true);

        //lobby creation
        lobbyCreator = new GorgeousLobbyCreator();
        players = lobbyCreator.createThatLobby();
        for(Player player: players)
            player.addObserver(this);
        logger.log("lobby created successfully");

        modelInstance = new Model(players.size());                      //MODELINSTANCE


        //INITIALIZATION 2
        initialization2();
    }

    //INITIALIZATION METHODS

    public void initialization2() throws InvalidIntArgumentException, IOException, InvalidinSocketException, GenericInvalidArgumentException, FullDataStructureException {
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
        updatePlayersInit();

        //schemes
        modelInstance.setSchemesDeck();
        updatePlayersInit();

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
        updatePlayersInit();

        //tools
        toolRecord = new ToolCardUsageRecord();
        updatePlayersInit();

        for(int i=0;i<players.size();i++)
        {
            logger.printNBlankLines(1);
            logger.log("PLAYER "+Integer.toString(i));
            modelEvents[i].setPrivateObjective(modelInstance.getPrivateObjective(i).getColor());
            logger.log("Private Objective: "+Integer.toString(modelInstance.getPrivateObjective(i).getColor()));
            modelEvents[i].setSchemes(modelInstance.getTempSchemes(i).getID(), modelInstance.getTempSchemes(i+4).getID());
            logger.log("Schemes "+Integer.toString(modelInstance.getTempSchemes(i).getID())+", "+Integer.toString(modelInstance.getTempSchemes(i+4).getID()));
            modelEvents[i].setPublicObjectives(pubObjs);
            modelEvents[i].setToolIds(toolRecord.getSelectedId());
            players.get(i).sendEvent(modelEvents[i]);
        }
        for(int i=0;i<players.size();i++)
        {
            boolean flag=false;
            while(!flag)
            {
                players.get(i).getEvent();
                logger.debugLog("event.getid = "+Integer.toString(((SchemeSelectionEvent)currentEvent).getId()));
                logger.debugLog("event.getfb = "+Integer.toString(((SchemeSelectionEvent)currentEvent).getFb()));

                if(((SchemeSelectionEvent)currentEvent).getId()==modelInstance.getTempSchemes(i).getID())
                {
                    SchemeCard scheme = modelInstance.getTempSchemes(i);
                    scheme.setfb(((SchemeSelectionEvent) currentEvent).getFb());
                    modelInstance.setPlayerScheme(i, scheme);
                    currentEvent.validate();
                    flag=true;
                }
                if(((SchemeSelectionEvent)currentEvent).getId()==modelInstance.getTempSchemes(i+players.size()).getID())
                {
                    SchemeCard scheme = modelInstance.getTempSchemes(i+players.size());
                    scheme.setfb(((SchemeSelectionEvent) currentEvent).getFb());
                    modelInstance.setPlayerScheme(i, scheme);
                    currentEvent.validate();
                    flag=true;
                }
                players.get(i).sendEvent(currentEvent);
            }
        }

        //PHASE 2

        //event construction
        Initialization2Event initEvent = new Initialization2Event();
        for(int i=0;i<players.size();i++)
        {
            initEvent.addEventPlayer(i, players.get(i).getName(), modelInstance.getSchemebyIndex(i).getID(), modelInstance.getSchemebyIndex(i).getfb(), players.get(i).getTokens());
            System.out.println(modelInstance.getSchemebyIndex(i).getfb());
        }
            for(int i=0;i<players.size();i++)
            players.get(i).sendEvent(initEvent);

        startMatch();
    }


    private void startMatch() throws InvalidIntArgumentException, GenericInvalidArgumentException, IOException, FullDataStructureException
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
        //START MATCH
        turnManager.start();
        while(!turnManager.theEnd())
        {
            turn();
            turnManager.endTurn();
            if(turnManager.isNextRound())
                modelInstance.roundEnd();
        }

        FriendlyScoreCalculator scoreCalculator = new FriendlyScoreCalculator(modelInstance, players);
        currentEvent=scoreCalculator.calculateScore();
        for(Player pl:players)
            pl.sendEvent(currentEvent);

    }

    //TURN
    private void turn() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException, FullDataStructureException
    {
        //manages turn progress
        boolean toolUsed=false;
        boolean moveUsed=false;
        boolean endTurn=false;

        sendTurnEvent();
        while(!endTurn)
        {
            if(moveUsed&&toolUsed)
                endTurn=true;
            players.get(turnManager.getActivePlayer()).getEvent();

            if((currentEvent.getType().equals("MoveEvent")&&moveUsed)||(currentEvent.getType().equals("ToolEvent")&&toolUsed))
                players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
            if(currentEvent.getType().equals("PassEvent"))
            {
                endTurn=true;
                for(int i=0;i<players.size();i++)
                    if(i!=turnManager.getActivePlayer())
                        players.get(i).sendEvent(currentEvent);
            }
            if(currentEvent.getType().equals("MoveEvent")&&!moveUsed)
                moveUsed=move();
            if(currentEvent instanceof ToolCardEvent&&!toolUsed)
            {
                if(moveUsed&&(((ToolCardEvent) currentEvent).getId()==1||((ToolCardEvent) currentEvent).getId()==5||((ToolCardEvent) currentEvent).getId()==6||((ToolCardEvent) currentEvent).getId()==9||((ToolCardEvent) currentEvent).getId()==10||((ToolCardEvent) currentEvent).getId()==11))
                    players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
                toolUsed=tool();
                if(toolUsed&&(((ToolCardEvent) currentEvent).getId()==1||((ToolCardEvent) currentEvent).getId()==5||((ToolCardEvent) currentEvent).getId()==6||((ToolCardEvent) currentEvent).getId()==9||((ToolCardEvent) currentEvent).getId()==10||((ToolCardEvent) currentEvent).getId()==11))
                    moveUsed=true;

            }

        }
    }

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
            for(int i=0;i<modelInstance.getTrack().returnNTurnRoundDice(modelInstance.getTrack().returnActualTurn()-1).returnDim();i++)
                temp.add(modelInstance.getTrack().returnNTurnRoundDice(modelInstance.getTrack().returnActualTurn()-1).getDie(i));
            event.setLastRound(temp);
        }
        else
            event.setNextRound(false);
        for(int i=0;i<players.size();i++)
        {

            if(i==turnManager.getActivePlayer())
            {
                logger.debugLog("active");
                TurnEvent playerEvent = event;
                playerEvent.setMyTurn(true);
                players.get(i).sendEvent(playerEvent);
                players.get(i).getEvent();
            }
            else
            {
                logger.debugLog("not active");
                TurnEvent playerEvent = event;
                playerEvent.setMyTurn(false);
                players.get(i).sendEvent(playerEvent);
                players.get(i).getEvent();
            }
        }
    }

    //MOVE
    private boolean move() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException
    {
        //checks modifies
        SchemeCard tempSC = modelInstance.getSchemebyIndex(turnManager.getActivePlayer());
        DraftPool tempDP = modelInstance.getDraft();
        Die tempDie = tempDP.returnDie(((MoveEvent)currentEvent).getIndex());
        boolean flag;
        if(turnManager.getFirst())
            flag = checkingMethods.checkFirstMove(tempSC, tempDie, ((MoveEvent)currentEvent).getX(), ((MoveEvent)currentEvent).getY());
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
                players.get(i).sendEvent(currentEvent);

            }return true;
        }
        else
        {
            logger.debugLog("move return false");
            players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
            return false;
        }
    }


    //TOOLCARD
    private boolean tool() throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException, FullDataStructureException
    {
        int toolId = ((ToolCardEvent)currentEvent).getId();

        //checks player can use that tool
        //check id
        boolean flag = false;
        for(int i=0;i<3;i++)
            if(toolRecord.getSelectedId()[i]==toolId)
                flag=true;
        if(!flag)
            return false;
        //check tokens
        int tokens = toolRecord.checkAndApplyUsage(players.get(turnManager.getActivePlayer()).getTokens(), toolId);
        if(tokens==0)
            return false;

        boolean applied = checkAndApplyToolCardModifies();
        if(applied)
        {
            players.get(turnManager.getActivePlayer()).usedTokens(tokens);
            currentEvent.validate();
            ((ToolCardEvent) currentEvent).setPlayer(turnManager.getActivePlayer());
            for(Player pl: players)
                pl.sendEvent(currentEvent);
            return true;
        }
        else {
            players.get(turnManager.getActivePlayer()).sendEvent(currentEvent);
            return false;
        }

    }

    public boolean checkAndApplyToolCardModifies() throws GenericInvalidArgumentException, InvalidIntArgumentException, IOException, FullDataStructureException
    {
        ToolCard card;
        ToolCardEvent event;
        switch (((ToolCardEvent)currentEvent).getId())
        {
            case 1:
            {
                card = toolRecord.getCard(1);
                event = (ToolCardOneEvent)currentEvent;
                int modify;
                if(((ToolCardOneEvent) event).getAction()=='+')
                    modify=1;
                else
                    modify=2;
                boolean check = ((ToolCardOne)card).checkToolCardOne(((ToolCardOneEvent) event).getIndex(), modify, modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), modelInstance.getDraft(), ((ToolCardOneEvent) event).getX(), ((ToolCardOneEvent) event).getY());
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
                boolean check = ((ToolCardTwo)card).checkToolCardTwo(((ToolCardTwoThreeEvent) event).getX0(), ((ToolCardTwoThreeEvent) event).getY0(), modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), ((ToolCardTwoThreeEvent) event).getX1(), ((ToolCardTwoThreeEvent) event).getY1());
                if(check)
                {
                    ((ToolCardTwo)card).applyModifies(((ToolCardTwoThreeEvent) event).getX0(), ((ToolCardTwoThreeEvent) event).getY0(), modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), ((ToolCardTwoThreeEvent) event).getX1(), ((ToolCardTwoThreeEvent) event).getY1());
                }
                return check;
            }
            case 3:
            {
                card = toolRecord.getCard(3);
                event = (ToolCardTwoThreeEvent)currentEvent;
                boolean check = ((ToolCardThree)card).checkToolCardThree(((ToolCardTwoThreeEvent) event).getX0(), ((ToolCardTwoThreeEvent) event).getY0(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()), ((ToolCardTwoThreeEvent) event).getX1(), ((ToolCardTwoThreeEvent) event).getY1());
                if(check)
                {
                    ((ToolCardThree)card).applyModifies(((ToolCardTwoThreeEvent) event).getX0(), ((ToolCardTwoThreeEvent) event).getY0(), modelInstance.getSchemebyIndex(turnManager.getActivePlayer()),((ToolCardTwoThreeEvent) event).getX1() ,((ToolCardTwoThreeEvent) event).getY1());
                }
                return check;
            }
            case 4:
            {
                card = toolRecord.getCard(4);
                event = (ToolCardFourEvent)currentEvent;
                boolean check = ((ToolCardFour) card).checkToolCardFour(((ToolCardFourEvent)event).getX01(),((ToolCardFourEvent) event).getY01(),((ToolCardFourEvent) event).getX11(),((ToolCardFourEvent) event).getY11(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()),((ToolCardFourEvent) event).getX11(),((ToolCardFourEvent) event).getY11(),((ToolCardFourEvent) event).getX22(),((ToolCardFourEvent) event).getY22());
                if(check)
                {
                    ((ToolCardFour) card).applyModifies(((ToolCardFourEvent) event).getX01(),((ToolCardFourEvent) event).getY01(),((ToolCardFourEvent) event).getX02(),((ToolCardFourEvent) event).getY02(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()),((ToolCardFourEvent) event).getX11(),((ToolCardFourEvent) event).getY11(),((ToolCardFourEvent) event).getX22(),((ToolCardFourEvent) event).getY22());
                }
                return check;
            }
            case 5:
            {
                card = toolRecord.getCard(5);
                event = (ToolCardFiveEvent)currentEvent;
                boolean check = ((ToolCardFive)card).checkToolCardFive(modelInstance.getDraft(),((ToolCardFiveEvent)event).getIndex(),modelInstance.getTrack(),((ToolCardFiveEvent)event).getTurn(),((ToolCardFiveEvent)event).getPos());
                if(check)
                {
                    ((ToolCardFive)card).setRoundTrack(modelInstance.getTrack());
                    ((ToolCardFive)card).setDraft(modelInstance.getDraft());
                    ((ToolCardFive)card).applyModifies(((ToolCardFiveEvent)event).getIndex(),((ToolCardFiveEvent)event).getTurn(),((ToolCardFiveEvent)event).getPos());
                    modelInstance.setDraft(((ToolCardFive)card).getDraft());
                    modelInstance.setTrack(((ToolCardFive)card).getTrack());
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
                players.get(turnManager.getActivePlayer()).sendEvent(event);
                players.get(turnManager.getActivePlayer()).getEvent();
                event = (ToolCardSixEvent)currentEvent;
                boolean check = ((ToolCardSix)card).checkToolCardSixSchemeCard(modelInstance.getDraft(),((ToolCardSixEvent)event).getIndex(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()),((ToolCardSixEvent) event).getX(),((ToolCardSixEvent) event).getY());
                if(check)
                {
                    ((ToolCardSix) card).setDraft(modelInstance.getDraft());
                    ((ToolCardSix) card).setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                    ((ToolCardSix) card).ApplyModifiesToScheme(((ToolCardSixEvent) event).getIndex(),((ToolCardSixEvent) event).getX(),((ToolCardSixEvent) event).getY());
                    modelInstance.setDraft(((ToolCardSix) card).getDraft());
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardSix) card).getScheme());
                }

                else {
                    ((ToolCardSix) card).setDraft(modelInstance.getDraft());
                    ((ToolCardSix) card).setScheme(modelInstance.getSchemebyIndex(turnManager.getActivePlayer()));
                    ((ToolCardSix) card).ApplyModifiesToDraft(((ToolCardSixEvent) event).getIndex());
                    modelInstance.setDraft(((ToolCardSix) card).getDraft());
                    modelInstance.setPlayerScheme(turnManager.getActivePlayer(),((ToolCardSix) card).getScheme());
                }

                return check;

            }
            case 7:
            {
                card = toolRecord.getCard(7);
                event = (ToolCardSevenEvent)currentEvent;
                modelInstance.setDraft(((ToolCardSeven)card).applyModifies(modelInstance.getDraft()));

            }
            case 8:
            {
                card = toolRecord.getCard(8);
                event = (ToolCardEightNineTenEvent)currentEvent;


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
                boolean check = ((ToolCardTen)card).checkToolCardTen(modelInstance.getDraft(),((ToolCardEightNineTenEvent)event).getIndex(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()),((ToolCardEightNineTenEvent) event).getX(),((ToolCardEightNineTenEvent) event).getY());
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

            }
            case 12:
            {
                card = toolRecord.getCard(12);
                event = (ToolCardTwelveEvent)currentEvent;
                boolean check = ((ToolCardTwelve)card).checkToolCardTwelve(modelInstance.getTrack(),((ToolCardTwelveEvent)event).getTurn(),((ToolCardTwelveEvent) event).getPos(),((ToolCardTwelveEvent) event).getX01(),((ToolCardTwelveEvent) event).getY01(),((ToolCardTwelveEvent) event).getX02(),((ToolCardTwelveEvent) event).getY02(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()),((ToolCardTwelveEvent) event).getX11(),((ToolCardTwelveEvent) event).getY11(),((ToolCardTwelveEvent) event).getX22(),((ToolCardTwelveEvent) event).getY22());
                if(check)
                {
                    ((ToolCardTwelve) card).applyModifies(((ToolCardTwelveEvent) event).getX01(),((ToolCardTwelveEvent) event).getY01(),((ToolCardTwelveEvent) event).getX02(),((ToolCardTwelveEvent) event).getY02(),modelInstance.getSchemebyIndex(turnManager.getActivePlayer()),((ToolCardTwelveEvent) event).getX11(),((ToolCardTwelveEvent) event).getY11(),((ToolCardTwelveEvent) event).getX22(),((ToolCardTwelveEvent) event).getY22());
                }
                return check;
            }
        }
        return false;
    }



    //DISCONNECTION MANAGEMENT
    public void updatePlayersInit()
    {

    }
    public boolean getNoDisconnected()
    {
        boolean flag = true;
        for(Player pl:players)
            if(pl.isDisconnected())
                flag=false;
        return flag;
    }
    public ArrayList<String> getDisconnected()
    {
        ArrayList<String> temp = new ArrayList<String>();
        for(Player pl: players)
            if(pl.isDisconnected())
                temp.add(pl.getName());
        return null;
    }


    public void update(Observable o, Object arg) {
        currentEvent = (Event)arg;
    }
}