package it.polimi.ingsw.client;


import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.SchemeCardMP;

public interface ConnectionClient {


    public void toolCardUsed(int id);

    public void getPrivObj();

    public int[] getSchemes();

    public int getScoreMarkers();


    public int[] getPublicObjs();


    public SchemeCardMP getOppSchemes();


    public DraftPoolMP getDraftPool();


    public void sendScheme(SchemeCardMP sc);


    public SchemeCardMP receiveOppScheme();


    public int receiveOppTokens();


    public int oppUsesaTool();


    public int changeTurn();


    public int getIdScoreBoard();


    public int getScores();


    public boolean checkedToolCardUsed();


    public boolean checkedMove();


    public void sendDraft(DraftPoolMP draft);


    public int[] receiveNewToolTokens();


    public void selectScheme(int id, int fb);

}