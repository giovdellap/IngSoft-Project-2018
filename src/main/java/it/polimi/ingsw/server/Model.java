package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class Model
{
    private int numPlayers=0;
    private DraftPool draft;
    private SchemesDeck scDeck;
    private RoundTrack track;
    private PrivateObjectivesDeck prDeck;
    private PublicObjectivesDeck pubDeck;
    private PublicObjective[] pubObjs;

    private SchemeCard[] tempSchemes;
    private SchemeCard[] playerSchemes;
    private PrivateObjective[] playersPrObjs;

    public Model(int numPlayers)
    {
        this.numPlayers=numPlayers;
    }

    public void setPrivateObjectives() throws InvalidIntArgumentException
    {
        prDeck = new PrivateObjectivesDeck();
        playersPrObjs = new PrivateObjective[numPlayers];
        playersPrObjs = prDeck.extractPrObj(numPlayers);
    }

    public PrivateObjective getPrivateObjective(int index) throws InvalidIntArgumentException
    {
        if(index<0||index>=numPlayers)
            throw new InvalidIntArgumentException();

        return playersPrObjs[index];
    }

    public void setSchemesDeck() throws InvalidIntArgumentException
    {
        scDeck = new SchemesDeck();
        tempSchemes = scDeck.extractSchemes(numPlayers*2);
        playerSchemes = new SchemeCard[numPlayers];
    }

    public SchemeCard getTempSchemes(int index)
    {
        return tempSchemes[index];
    }

    public void setPubObjs() throws InvalidIntArgumentException
    {
        pubDeck = new PublicObjectivesDeck();
        pubObjs = pubDeck.extractPubObjs();
    }

    public PublicObjective getPubObj(int index) throws InvalidIntArgumentException
    {
        if(index<0||index>=3)
            throw new InvalidIntArgumentException();
        return pubObjs[index];
    }

    public void setSelectedScheme(int player, int id, int fb) throws InvalidIntArgumentException
    {
        if(player<0||player>=numPlayers)
            throw new InvalidIntArgumentException();
        else {
            playerSchemes[player] = scDeck.extractSchemebyID(id);
            playerSchemes[player].setfb(fb);
        }
    }

    public SchemeCard getSchemebyIndex(int index) throws InvalidIntArgumentException
    {
        if(index<0||index>=numPlayers)
            throw  new InvalidIntArgumentException();
        return playerSchemes[index];
    }
}
