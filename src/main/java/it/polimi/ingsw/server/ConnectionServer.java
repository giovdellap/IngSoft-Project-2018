package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.IOException;
import java.util.*;

public interface ConnectionServer
{

    public void sendPrivObj(int player, int id);

    public void sendSchemes(int player, int id1, int id2);

    public void sendPubObjs(int id1, int id2, int id3);

    public int[] getSelectedScheme(int player) throws IOException, InvalidinSocketException;

    public void sendDraftPool(DraftPool draft);

    public SchemeCard checkMove();

    public int checkTool();

    public DraftPool checkDraft();

    public void sendCorrectMove(boolean ok);

    public void sendCorrectToolUsed(boolean ok);

    public void sendNewToolTokens(int toolId,int tokens);

    public void schemeSelected(int id, int fb, int player);

}