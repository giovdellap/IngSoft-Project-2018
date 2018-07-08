package it.polimi.ingsw.client;

import it.polimi.ingsw.client.Graphics.AbstractGraphic;
import it.polimi.ingsw.client.Graphics.CLI.BeautifulCLI;
import it.polimi.ingsw.commons.Events.Initialization.PersonalSchemeEvent;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.*;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.PassEvent;
import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardElevenEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardSixEvent;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.server.ModelComponent.PrivateObjective;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


public class GraphicsManager extends Observable implements Runnable
{
    private AbstractGraphic graphic;

    private Event currentEvent;

    //TURN SETTINGS
    private PlayerClient[] players;
    private DraftPoolMP draft;
    private RoundTrackMP track;
    private int[] toolsUsage;
    private int activePlayer;
    private int me;
    private int round;
    private ArrayList<String> disconnected;

    //GRAPHICS MANAGER SETTINGS
    private State state;
    private SecondaryState secondaryState=SecondaryState.VIRGIN;
    private Event received;

    private SimpleLogger logger;
    private boolean stop=false;
    private ArrayList<String> settings;

    /**
     * GraphicsManager Constructor
     */

    public GraphicsManager(ArrayList<String> settings) {
        this.settings=settings;
        if(settings.get(1).equals("CLI"))
            graphic = new BeautifulCLI();

        disconnected=new ArrayList<String>();
        logger = new SimpleLogger(2, true);
    }

    public void run()
    {
        logger.debugLog("graphics manager started: "+state);

        if(state==State.ASKMAIN)
        {
            if(secondaryState!=SecondaryState.VIRGIN)
            {
                if(secondaryState==SecondaryState.MOVEACCEPTED)
                    moveAccepted();
                if(secondaryState==SecondaryState.MOVEREFUSED)
                    moveRefused();
                if(secondaryState==SecondaryState.TOOLACCEPTED)
                    toolAccepted();
                if(secondaryState==SecondaryState.TOOLREFUSED)
                    System.out.println("MISSING");
            }
            try {
                myTurn();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(state==State.ASKTOOL6)
        {
            try {
                toolCard6Part2((ToolCardSixEvent)received);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(state==State.ASKTOOL11)
        {
            try {
                toolCard11Part2((ToolCardElevenEvent)received);
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        if(state==State.SHOWTURN)
        {
            try {
                notMyTurn();
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(state==State.SHOWMOVE)
        {
            try {
                showMove((MoveEvent)received);
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(state==State.SHOWTOOL)
        {
            try {
                showTool((ToolCardEvent)received);
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        if(state==State.SHOWSCORE)
        {
            try {
                showScores((ScoreEvent)received, ((ScoreEvent)received).getPlayers().get(0).getId()==me);
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        logger.log("graphics manager ended: "+state);
    }



    /**
     * updates the Graphics Manager with all the updated elements of the model
     * @param players
     * @param draft
     * @param track
     * @param tools
     * @param activePlayer
     * @param me
     * @param round
     * @param disconnected
     */
    public void gmUpdate(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<String> disconnected)
    {
        this.players = players;
        this.draft = draft;
        this.track = track;
        this.toolsUsage = tools;
        this.activePlayer = activePlayer;
        this.me = me;
        this.round = round;
        this.disconnected = disconnected;
    }

    /**
     * manages my turn in the CLI
     */
    private void myTurn() throws InterruptedException {
        stop=false;
        int whatToDo=4;
        System.out.println("DISCONNECTED MYTURN GRAPHMANAGER: "+Integer.toString(disconnected.size()));

        graphicsUpdate();
        Executor executor = Executors.newSingleThreadExecutor();
        graphic.setState(State.ASKMAIN);
        executor.execute(graphic);
        ((ExecutorService) executor).shutdown();
        ((ExecutorService) executor).awaitTermination(75, TimeUnit.SECONDS);
        whatToDo = graphic.getAskForWhat();

        if(!stop) {

            if (whatToDo == 0) {
                currentEvent = new PassEvent();
                setChanged();
                notifyObservers(currentEvent);
            }
            if (whatToDo == 1) {

                executor=Executors.newSingleThreadExecutor();
                graphic.setState(State.CLIMOVE);
                executor.execute(graphic);
                ((ExecutorService) executor).shutdown();
                ((ExecutorService) executor).awaitTermination(75, TimeUnit.SECONDS);

                int[] move = graphic.getMove();
                currentEvent = new MoveEvent(move[0], move[1], move[2]);
                setChanged();
                notifyObservers(currentEvent);
            }
            if (whatToDo == 2) {
                executor=Executors.newSingleThreadExecutor();
                graphic.setState(State.CLITOOL);
                executor.execute(graphic);
                ((ExecutorService) executor).shutdown();
                ((ExecutorService) executor).awaitTermination(75, TimeUnit.SECONDS);

                currentEvent = graphic.getUseTool();
                setChanged();
                notifyObservers(currentEvent);

            }
        }
    }

    /**
     * manages the other player's turns in the CLI
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    private void notMyTurn() throws InvalidIntArgumentException, IOException
    {
        if (!disconnected.isEmpty())
            graphic.showTurn(players, draft, track, toolsUsage, activePlayer, me, round, disconnected);
        else
            graphic.showTurn(players, draft, track, toolsUsage, activePlayer, me, round);
    }

    /**
     * prints a message for an accepted move
     */
    private void moveAccepted()
    {
        graphic.moveAccepted();
    }

    /**
     * prints a message for a refused move
     */
    private void moveRefused()
    {
        graphic.moveRefused();
    }

    /**
     * prints a message for an accepted tool
     */
    private void toolAccepted()
    {
        graphic.toolAccepted();
    }

    /**
     * shows a move to the other players
     * @param event Move event
     * @throws InvalidIntArgumentException
     */
    private void showMove(MoveEvent event) throws InvalidIntArgumentException, FileNotFoundException {
        logger.debugLog(" color: "+Integer.toString(players[event.getId()].getPlayerScheme().getDie(event.getX(), event.getY()).getColor()));
        graphic.showMove(players, draft, track, toolsUsage, activePlayer, me, event);
    }

    /**
     * shows a tool to the other players
     * @param event
     * @throws InvalidIntArgumentException
     */
    private void showTool(ToolCardEvent event) throws InvalidIntArgumentException, FileNotFoundException {
        graphic.showTool(players, draft, track, toolsUsage, activePlayer, me, event);
    }

    /**
     * sets and notifies the tool card six second part event to the CLI
     * @param currentEvent ToolCardSixEvent
     * @throws InterruptedException
     */
    private void toolCard6Part2(ToolCardSixEvent currentEvent) throws InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        graphic.setState(State.ASKTOOL6);
        graphic.setUseTool(currentEvent);
        exec.execute(graphic);
        exec.shutdown();
        exec.awaitTermination(75, TimeUnit.SECONDS);

        ToolCardSixEvent event = (ToolCardSixEvent)graphic.getUseTool();
        setChanged();
        notifyObservers(event);
    }

    /**
     * sets and notifies the tool card eleven second part event to the CLI
     * @param currentEvent ToolCardElevenEvent
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    private void toolCard11Part2(ToolCardElevenEvent currentEvent) throws InvalidIntArgumentException, IOException, InterruptedException {
        ExecutorService exec = Executors.newSingleThreadExecutor();
        graphic.setState(State.ASKTOOL11);
        graphic.setUseTool(currentEvent);
        exec.execute(graphic);
        exec.shutdown();
        exec.awaitTermination(75, TimeUnit.SECONDS);

        ToolCardElevenEvent event = (ToolCardElevenEvent)graphic.getUseTool();
        setChanged();
        notifyObservers(event);
    }

    private void showScores(ScoreEvent event, boolean winner) throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, IOException, InterruptedException {
        graphic.showScores(event, winner);
    }

    public enum State
    {
        SHOWMOVE,
        SHOWTOOL,
        SHOWTURN,
        SHOWSCORE,
        ASKMAIN,
        ASKTOOL6,
        ASKTOOL11,
        CLIMOVE,
        CLITOOL,
    }

    public enum SecondaryState
    {
        MOVEACCEPTED,
        MOVEREFUSED,
        TOOLACCEPTED,
        TOOLREFUSED,
        VIRGIN,
    }

    public void setState(State state)
    {
        this.state=state;
    }
    public void setSecondaryState(SecondaryState secondaryState)
    {
        this.secondaryState=secondaryState;
    }
    public void setReceivedEvent(Event event)
    {
        received=event;
    }
    //INITIALIZATION
    /**
     * asks user for username in CLI
     * @throws IOException
     */
    public void askUsername() throws IOException, InterruptedException {
        String username = graphic.askUsername();
        currentEvent = new UsernameEvent(username);
        setChanged();
        notifyObservers(currentEvent);
    }

    /**
     * waits for other players
     */
    public void waitForPlayers()
    {
        graphic.setWaitScene();
    }

    /**
     * gets one the selected schemes
     * @param scheme1
     * @param scheme2
     * @param username
     * @param privObj
     * @param pubObjs
     * @param tools
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public void getSelectedScheme(SchemeCard scheme1, SchemeCard scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs, int[] tools) throws InvalidIntArgumentException, IOException, InterruptedException {
        SchemeCard temp = graphic.setInitializationScene(scheme1, scheme2, username, privObj, pubObjs, tools);
        if(temp.getID()!=100)
            currentEvent = new SchemeSelectionEvent(temp.getID(), temp.getfb());
        else
            currentEvent=new PersonalSchemeEvent(temp, 0);
        setChanged();
        notifyObservers(currentEvent);
    }


    /**
     * waits for 2 players
     */
    public void waitForPlayers2()
    {
        graphic.setWaitScene2();
    }

    public String toString() {return "graphics";}

    public void graphicsUpdate()
    {
        System.out.println("GRAPHICS update disconnected: "+Integer.toString(disconnected.size()));
        graphic.updateThatShit(players, draft, track, toolsUsage, activePlayer, me, round, disconnected);
    }

    public void stopView()
    {
        System.out.println("GRAPHIC STOP");
        graphic.stopGraphics();
    }

    public void forfait() throws IOException, InterruptedException {
        graphic.forfaitCli();
    }

    public void reconnectionSet(PrivateObjectiveMP prObj, PublicObjectiveMP[] pubObjs, int[] toolsID)
    {
        graphic.setGraphics(prObj, pubObjs, toolsID);
    }
}
