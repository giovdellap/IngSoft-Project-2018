package it.polimi.ingsw.client.Connection;


import it.polimi.ingsw.client.ConnectionClient;
import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.SchemeCardMP;

public class RMI implements ConnectionClient
{
    public RMI()
    {
        //costruttore
    }

    public void toolCardUsed(int id)
    {

    }

    public void getPrivObj()
    {

    }

    public int[] getSchemes()
    {
        return null;
    }

    public int getScoreMarkers()
    {
        return 0;

    }

    public int[] getPublicObjs()
    {
        return null;
    }

    public SchemeCardMP getOppSchemes()
    {

        return null;
    }

    public DraftPoolMP getDraftPool()
    {

        return null;
    }

    public void sendScheme(SchemeCardMP sc)
    {

    }

    public SchemeCardMP receiveOppScheme()
    {
        return null;
    }

    public int receiveOppTokens()
    {

        return 0;
    }

    public int oppUsesaTool()
    {
        return 0;

    }

    public int changeTurn()
    {
        return 0;

    }

    public int getIdScoreBoard()
    {

        return 0;
    }

    public int getScores()
    {

        return 0;
    }

    public boolean checkedToolCardUsed()
    {

        return true;
    }

    public boolean checkedMove()
    {
        return true;

    }

    public void sendDraft(DraftPoolMP draft)
    {

    }

    public int[] receiveNewToolTokens()
    {
        return null;

    }

    public void selectScheme(int id, int fb)
    {

    }
}
