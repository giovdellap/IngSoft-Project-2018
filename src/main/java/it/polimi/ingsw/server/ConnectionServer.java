package it.polimi.ingsw.server;

import java.util.*;

public interface ConnectionServer
{

    public void sendPrivObj(int player, int id);

    public void sendSchemes(SchemeCard tempScheme, int player);

    public void sendScoreMarkers(int[] scoreVector);

    public void sendPubObjs(int[] pubVect);

    public SchemeCard getSelectedScheme(int player);

    public void sendDraftPool(DraftPool draft);

    public SchemeCard checkMove();

    public int checkTool();

    public DraftPool checkDraft();

    public void sendCorrectMove(boolean ok);

    public void sendCorrectToolUsed(boolean ok);

    public void sendNewToolTokens(int toolId,int tokens);

    public void schemeSelected(int id, int fb, int player);

}