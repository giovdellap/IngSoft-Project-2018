package it.polimi.ingsw.server.Connection;

import it.polimi.ingsw.server.ConnectionServer;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;

public class RMI implements ConnectionServer
{
    public RMI()
    {

    }

    public void sendPrivObj(int player, int id)
    {

    }

    public void sendSchemes(int player, int id1, int id2) {

    }

    public void sendPubObjs(int player, int id1, int id2, int id3) {

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

    public int[] getSelectedScheme(int player)
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