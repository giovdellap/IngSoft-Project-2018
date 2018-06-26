package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.BeautifulCLI;
import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.PassEvent;
import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardElevenEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardSixEvent;
import it.polimi.ingsw.commons.SimpleLogger;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Observable;

public class GraphicsManager extends Observable
{
    public MinorLogger gmLogger;

    private int graphics;//1 = GUI, 2 = CLI

    private BeautifulCLI beautifulCLI;

    private Event currentEvent;

    //TURN SETTINGS
    private PlayerClient[] players;
    private DraftPoolMP draft;
    private RoundTrackMP track;
    private int[] toolsUsage;
    private int activePlayer;
    private int me;
    private int round;
    private ArrayList<Integer> disconnected;

    private SimpleLogger logger;

    /**
     * GraphicsManager Constructor
     * @param graphics type of graphic
     * @throws GenericInvalidArgumentException
     */

    public GraphicsManager(int graphics) throws GenericInvalidArgumentException {
        this.graphics=graphics;
        gmLogger = new MinorLogger();
        gmLogger.minorLog("graphics operative");
        if(graphics==2)
        {
            beautifulCLI = new BeautifulCLI(1);
        }
        disconnected=new ArrayList<Integer>();
        logger = new SimpleLogger(2, true);
    }

    /**
     * asks user for username in CLI
     * @throws IOException
     * @throws GenericInvalidArgumentException
     */
    public void askUsername() throws IOException, GenericInvalidArgumentException {
        if(graphics==2) {
            String username = beautifulCLI.askUsername();
            currentEvent = new UsernameEvent(username);
            setChanged();
            notifyObservers(currentEvent);
        }

    }

    /**
     * waits for other players
     */
    public void waitForPlayers()
    {
        if(graphics==2)
            beautifulCLI.setWaitScene();
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
    public void getSelectedScheme(SchemeCardMP scheme1, SchemeCardMP scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs, int[] tools) throws InvalidIntArgumentException, IOException {
        if(graphics==2) {
            SchemeCardMP temp = beautifulCLI.setInitializationScene(scheme1, scheme2, username, privObj, pubObjs, tools);
            currentEvent = new SchemeSelectionEvent(temp.getID(), temp.getfb());
            setChanged();
            notifyObservers(currentEvent);
        }

    }

    /**
     * waits for 2 players
     */
    public void waitForPlayers2()
    {
        if(graphics==2)
            beautifulCLI.setWaitScene2();
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
     * @throws InvalidIntArgumentException
     */
    public void gmUpdate(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected) throws InvalidIntArgumentException {
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
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public void myTurn() throws InvalidIntArgumentException, IOException {
        int whatToDo;
        if(graphics==2) {
            if (disconnected!=null)
                whatToDo = beautifulCLI.askForWhat(players, draft, track, toolsUsage, activePlayer, me, round, disconnected);
            else
                whatToDo = beautifulCLI.askForWhat(players, draft, track, toolsUsage, activePlayer, me, round);

            if(whatToDo==0) {
                currentEvent = new PassEvent();
                setChanged();
                notifyObservers(currentEvent);
            }
            if(whatToDo==1)
            {
                int[] move = beautifulCLI.move(draft.getSize());
                currentEvent = new MoveEvent(move[0], move[1], move[2]);
                setChanged();
                notifyObservers(currentEvent);
            }
            if(whatToDo==2)
            {
               currentEvent = beautifulCLI.useTool(draft.getSize(),track.returnActualTurn(),round);
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
    public void notMyTurn() throws InvalidIntArgumentException, IOException {
        if (disconnected!=null)
            beautifulCLI.showTurn(players, draft, track, toolsUsage, activePlayer, me, round, disconnected);
        else
            beautifulCLI.showTurn(players, draft, track, toolsUsage, activePlayer, me, round);
    }

    /**
     * prints a message for an accepted move
     */
    public void moveAccepted()
    {
        if(graphics==2)
            beautifulCLI.moveAccepted();
    }

    /**
     * prints a message for a refused move
     */
    public void moveRefused()
    {
        if(graphics==2)
            beautifulCLI.moveRefused();
    }

    /**
     * prints a message for an accepted tool
     */
    public void toolAccepted()
    {
        beautifulCLI.toolAccepted();
    }

    /**
     * shows a move to the other players
     * @param event Move event
     * @throws InvalidIntArgumentException
     */
    public void showMove(MoveEvent event) throws InvalidIntArgumentException {
        logger.debugLog(" color: "+Integer.toString(players[event.getId()].getPlayerScheme().getDie(event.getX(), event.getY()).getColor()));
        if(graphics==2)
            beautifulCLI.showMove(players, draft, track, toolsUsage, activePlayer, me, event);
    }

    /**
     * shows a tool to the other players
     * @param event
     * @throws InvalidIntArgumentException
     */
    public void showTool(ToolCardEvent event) throws InvalidIntArgumentException {
        if(graphics==2)
            beautifulCLI.showTool(players, draft, track, toolsUsage, activePlayer, me, event);
    }

    /**
     * sets and notifies the tool card six second part event to the CLI
     * @param currentEvent ToolCardSixEvent
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public void toolCard6Part2(ToolCardSixEvent currentEvent) throws InvalidIntArgumentException, IOException {
        ToolCardSixEvent event = beautifulCLI.toolCardSixEventPartTwo(players, draft, track, toolsUsage, activePlayer, me, round, currentEvent);
        setChanged();
        notifyObservers(event);
    }

    /**
     * sets and notifies the tool card eleven second part event to the CLI
     * @param currentEvent ToolCardElevenEvent
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public void toolCard11Part2(ToolCardElevenEvent currentEvent) throws InvalidIntArgumentException, IOException {
        ToolCardElevenEvent event = beautifulCLI.toolCardElevenEventPartTwo(players, draft, track, toolsUsage, activePlayer, me, round, currentEvent);
        setChanged();
        notifyObservers(event);
    }


    public void showScores(ScoreEvent event, boolean winner) throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, IOException {
        beautifulCLI.showScores(event, winner);
    }


}
