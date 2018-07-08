package it.polimi.ingsw.client.Graphics;

import it.polimi.ingsw.client.GraphicsManager;
import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.RoundTrackMP;
import it.polimi.ingsw.client.PlayerClient;
import it.polimi.ingsw.commons.Events.Initialization.PersonalSchemeEvent;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.io.IOException;
import java.util.ArrayList;

public abstract class AbstractGraphic implements Runnable{

    public ThreadUpdater threadUpdater;

    public GraphicsManager.State state;
    public GraphicsManager.SecondaryState secondaryState;
    public boolean stopCLI=false;

    public int askForWhat=0;
    public int[] move;
    public ToolCardEvent useTool;

    public PersonalSchemeEvent personalSchemeEvent;

    public void updateThatShit(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<String> disconnected)
    {
        threadUpdater=new ThreadUpdater(players, draft, track, tools, activePlayer, me, round, disconnected);

    }

    public void setState(GraphicsManager.State state){this.state=state;}
    public void setSecondaryState(GraphicsManager.SecondaryState secondaryState) {this.secondaryState = secondaryState;}
    public void stopGraphics()
    {
        stopCLI=true;
    }
    public int getAskForWhat()
    {
        return askForWhat;
    }
    public int[] getMove() {
        return move;
    }
    public ToolCardEvent getUseTool() {
        return useTool;
    }
    public abstract String askUsername() throws IOException, InterruptedException;
    public abstract void setWaitScene();
    public abstract SchemeCard setInitializationScene(SchemeCard scheme1, SchemeCard scheme2, String username, PrivateObjectiveMP privateObjectiveMP, PublicObjectiveMP[] publicObjectiveMPS, int[] tools) throws InvalidIntArgumentException, IOException, InterruptedException;
    public abstract void setWaitScene2();
    public abstract void moveAccepted();
    public abstract void moveRefused();
    public abstract void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<String> disconnected) throws InvalidIntArgumentException, IOException;
    public abstract void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round) throws InvalidIntArgumentException, IOException;
    public abstract void toolAccepted();
    public abstract void showMove(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, MoveEvent event) throws InvalidIntArgumentException;
    public abstract void showTool(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, ToolCardEvent event) throws InvalidIntArgumentException;
    public abstract void setUseTool(ToolCardEvent event);
    public abstract boolean showScores(ScoreEvent event, boolean winner) throws InvalidIntArgumentException, IOException, InterruptedException;
    public abstract void forfaitCli() throws IOException, InterruptedException;
    public abstract void setGraphics(PrivateObjectiveMP privateObjective, PublicObjectiveMP[] pubObjs, int[] toolsID);


    public class ThreadUpdater
    {
        public PlayerClient[] players;
        public DraftPoolMP draft;
        public RoundTrackMP track;
        public int[] tools;
        public int activePlayer;
        public int me;
        public int round;
        public ArrayList<String> disconnected;


        public ThreadUpdater(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<String> disconnected)
        {
            this.players = players;
            this.draft = draft;
            this.track = track;
            this.tools = tools;
            this.activePlayer = activePlayer;
            this.me = me;
            this.round = round;
            this.disconnected = disconnected;
        }
    }

}
