package it.polimi.ingsw.clientOld.MultiPackage;


import it.polimi.ingsw.clientOld.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.clientOld.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.IOException;

public interface ConnectionClient {



    public int getPrivObj() throws IOException, GenericInvalidArgumentException;

    public int[] getSchemes() throws IOException, GenericInvalidArgumentException;


    public int[] getPublicObjs() throws IOException, GenericInvalidArgumentException;

    public void sendScheme(int[] arg);

    public int[] getOppSchemes() throws IOException, GenericInvalidArgumentException;

    public void toolCardUsed(int id);

    public DraftPoolMP getDraftPool();




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