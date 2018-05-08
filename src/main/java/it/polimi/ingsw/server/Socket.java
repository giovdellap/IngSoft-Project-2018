package it.polimi.ingsw.server;

import java.util.*;


public class Socket implements ConnectionServer {


    public Socket()
    {

    }

    public void sendPrivObj(int player, int id)
    {

    }

    public void sendSchemes(SchemeCard tempScheme, int player)
    {

    }

    public void sendScoreMarkers(int[] scoreVector)
    {

    }

    public void sendPubObjs(int[] pubVec)
    {

    }

    public SchemeCard getSelectedScheme(int player)
    {
        return null;
    }

    public void sendDraftPool(DraftPool draft)
    {

    }

    public SchemeCard checkMove()
    {
        return null;

    }

    public int checkTool()
    {
        return 0;

    }

    public DraftPool checkDraft()
    {
        return null;

    }

    public void sendCorrectMove(boolean ok)
    {

    }

    public void sendCorrectToolUsed(boolean ok)
    {

    }

    public void sendNewToolTokens(int toolId,int tokens)
    {

    }

    public void schemeSelected(int id, int fb, int player)
    {

    }

}