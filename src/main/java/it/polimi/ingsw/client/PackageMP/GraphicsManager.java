package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.BeautifulCLI;
import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class GraphicsManager
{
    public MinorLogger gmLogger;

    private int graphics;//1 = GUI, 2 = CLI

    private BeautifulCLI beautifulCLI;

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
    }

    public String askUsername(Stage stage) throws IOException, GenericInvalidArgumentException {
        if(graphics==2)
            return beautifulCLI.askUsername();
        else
            return null;
    }

    public String askUsernameAgain(Stage stage, String badUsername) throws IOException, GenericInvalidArgumentException {
        if(graphics==2)
            return beautifulCLI.askUsernameAgain(badUsername);
        else return null;

    }

    public void waitForPlayers()
    {
        if(graphics==2)
            beautifulCLI.setWaitScene();
    }

    public SchemeCardMP getSelectedScheme(SchemeCardMP scheme1, SchemeCardMP scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs, int[] tools) throws InvalidIntArgumentException, IOException {
        if(graphics==2)
             return beautifulCLI.setInitializationScene(scheme1, scheme2, username, privObj, pubObjs, tools);
        else
            return null;
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
    public int askForWhat() throws InvalidIntArgumentException, IOException {
        if(graphics==2) {
            if (!disconnected.isEmpty())
                return beautifulCLI.askForWhat(players, draft, track, toolsUsage, activePlayer, me, round, disconnected);
            else
                return beautifulCLI.askForWhat(players, draft, track, toolsUsage, activePlayer, me, round);

        }
        else
            return 0;
    }

    //move
    public int[] move() throws IOException {
        if(graphics==2)
            return beautifulCLI.move();
        else
            return null;
    }
    public void cantMove()
    {
        if(graphics==2)
            beautifulCLI.cantMove();
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

    public void setToolsId(int[] tools)
    {
        beautifulCLI.setToolsID(tools);
    }


    //not my turn
    public void showTurn() throws InvalidIntArgumentException, IOException {
        if(graphics==2)
        {
            if(disconnected.isEmpty())
                beautifulCLI.showTurn(players, draft, track, toolsUsage, activePlayer, me, round);
            else
                beautifulCLI.showTurn(players, draft, track, toolsUsage, activePlayer, me, round, disconnected);
        }
    }
    public void showMove(int color, int value, int x, int y) throws InvalidIntArgumentException {
        if(graphics==2)
            beautifulCLI.showMove(players, draft, track, toolsUsage, activePlayer, me, color, value, x, y);
    }



}
