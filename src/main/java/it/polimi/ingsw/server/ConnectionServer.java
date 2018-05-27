package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.IOException;

public interface ConnectionServer
{

    public void sendPrivObj(int player, int id) throws GenericInvalidArgumentException, IOException;

    public void sendSchemes(int player, int id1, int id2) throws GenericInvalidArgumentException, IOException;

    public void sendPubObjs(int player, int id1, int id2, int id3) throws GenericInvalidArgumentException, IOException;

    public int[] getSelectedScheme(int player) throws IOException, InvalidinSocketException, GenericInvalidArgumentException;

    public void sendDraftPool(DraftPool draft);

    public SchemeCard checkMove();

    public int checkTool();

    public DraftPool checkDraft();

    public void sendCorrectMove(boolean ok);

    public void sendCorrectToolUsed(boolean ok);

    public void sendNewToolTokens(int toolId,int tokens);

    public void schemeSelected(int id, int fb, int player);

}