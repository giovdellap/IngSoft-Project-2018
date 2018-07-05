package it.polimi.ingsw.client.Graphics;

import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.RoundTrackMP;
import it.polimi.ingsw.client.PlayerClient;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardElevenEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardSixEvent;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.io.IOException;
import java.util.ArrayList;

public interface ViewInterface
{
    SchemeCard setInitializationScene(SchemeCard scheme1, SchemeCard scheme2, String username, PrivateObjectiveMP privateObjectiveMP, PublicObjectiveMP[] publicObjectiveMPS, int[] tools) throws InvalidIntArgumentException, IOException;

    String askUsername() throws IOException, InterruptedException;
    void setWaitScene();
    void setWaitScene2();
    void moveAccepted();
    void moveRefused();
    void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected) throws InvalidIntArgumentException, IOException;
    void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round) throws InvalidIntArgumentException, IOException;
    void showMove(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, MoveEvent event) throws InvalidIntArgumentException;
    void showTool(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, ToolCardEvent event) throws InvalidIntArgumentException;
    void useTool(int draftDim, int roundTrackDim, int round) throws IOException, InterruptedException;
    ToolCardSixEvent toolCardSixEventPartTwo(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ToolCardSixEvent previousEvent) throws InvalidIntArgumentException, IOException;
    ToolCardElevenEvent toolCardElevenEventPartTwo(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ToolCardElevenEvent previousEvent) throws InvalidIntArgumentException, IOException;
    void toolAccepted();
    boolean showScores(ScoreEvent event, boolean winner) throws InvalidIntArgumentException, IOException, InterruptedException;


}
