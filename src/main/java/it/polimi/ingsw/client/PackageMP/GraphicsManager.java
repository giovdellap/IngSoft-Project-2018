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
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardSixEvent;
import javafx.stage.Stage;

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



    public GraphicsManager(int graphics) throws GenericInvalidArgumentException {
        this.graphics=graphics;
        gmLogger = new MinorLogger();
        gmLogger.minorLog("graphics operative");
        if(graphics==2)
        {
            beautifulCLI = new BeautifulCLI();
        }
        disconnected=new ArrayList<Integer>();
    }

    public void askUsername() throws IOException, GenericInvalidArgumentException {
        if(graphics==2) {
            String username = beautifulCLI.askUsername();
            currentEvent = new UsernameEvent(username);
            setChanged();
            notifyObservers(currentEvent);
        }

    }


    public void waitForPlayers()
    {
        if(graphics==2)
            beautifulCLI.setWaitScene();
    }

    public void getSelectedScheme(SchemeCardMP scheme1, SchemeCardMP scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs, int[] tools) throws InvalidIntArgumentException, IOException {
        if(graphics==2) {
            SchemeCardMP temp = beautifulCLI.setInitializationScene(scheme1, scheme2, username, privObj, pubObjs, tools);
            currentEvent = new SchemeSelectionEvent(temp.getID(), temp.getfb());
            setChanged();
            notifyObservers(currentEvent);
        }

    }

    public void waitForPlayers2()
    {
        if(graphics==2)
            beautifulCLI.setWaitScene2();
    }

    //TURN
    public void gmUpdate(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected)
    {
        this.players=players;
        this.draft=draft;
        this.track=track;
        this.toolsUsage=tools;
        this.activePlayer=activePlayer;
        this.me=me;
        this.round=round;
        this.disconnected=disconnected;
    }

    //MY TURN
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
                int[] move = beautifulCLI.move();
                currentEvent = new MoveEvent(move[0], move[1], move[2]);
                setChanged();
                notifyObservers(currentEvent);
            }

        }
    }

    public void notMyTurn() throws InvalidIntArgumentException, IOException {
        if (disconnected!=null)
            beautifulCLI.showTurn(players, draft, track, toolsUsage, activePlayer, me, round, disconnected);
        else
            beautifulCLI.showTurn(players, draft, track, toolsUsage, activePlayer, me, round);
    }

    public void moveAccepted()
    {
        if(graphics==2)
            beautifulCLI.moveAccepted();
    }
    public void moveRefused()
    {
        if(graphics==2)
            beautifulCLI.moveRefused();
    }

    public void toolAccepted()
    {
        beautifulCLI.toolAccepted();
    }
    public void showMove(MoveEvent event) throws InvalidIntArgumentException {
        if(graphics==2)
            beautifulCLI.showMove(players, draft, track, toolsUsage, activePlayer, me, event);
    }
    public void showTool(ToolCardEvent event) throws InvalidIntArgumentException {
        if(graphics==2)
            beautifulCLI.showTool(players, draft, track, toolsUsage, activePlayer, me, event);
    }
    public void toolCard6Part2(ToolCardSixEvent currentEvent)
    {
        System.out.println("SCHERZONE!!!");
    }



    //move

    public void cantMove()
    {
        if(graphics==2)
            beautifulCLI.cantMove();
    }


    public void setToolsId(int[] tools)
    {
        beautifulCLI.setToolsID(tools);
    }





}
