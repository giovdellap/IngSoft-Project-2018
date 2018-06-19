package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.FcknSimpleLogger;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
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

    private FcknSimpleLogger logger;

    public Model(int numPlayers)
    {
            this.numPlayers=numPlayers;
            logger = new FcknSimpleLogger(0, false);
    }

    //INITIALIZATION
    public void setPrivateObjectives() throws InvalidIntArgumentException
    {
        prDeck = new PrivateObjectivesDeck();
        playersPrObjs = new PrivateObjective[numPlayers];
        playersPrObjs = prDeck.extractPrObj(numPlayers);
    }

    public void setSchemesDeck() throws InvalidIntArgumentException
    {
        scDeck = new SchemesDeck();
        tempSchemes = scDeck.extractSchemes(numPlayers*2);
        playerSchemes = new SchemeCard[numPlayers];
    }

    public void setPubObjs() throws InvalidIntArgumentException
    {
        pubDeck = new PublicObjectivesDeck();
        pubObjs = pubDeck.extractPubObjs();
    }

    public SchemeCard getTempSchemes(int index)
    {
        return tempSchemes[index];
    }


    public PrivateObjective getPrivateObjective(int index) throws InvalidIntArgumentException
    {
        if(index<0||index>=numPlayers)
            throw new InvalidIntArgumentException();

        return playersPrObjs[index];
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

    public void setFinalNumPlayers(int numPlayers)
    {
        this.numPlayers=numPlayers;
    }

    public void draftPoolInitialization() throws InvalidIntArgumentException {
        draft = new DraftPool(numPlayers);
    }

    public void roundTrackInitialization()
    {
        track = new RoundTrack();
    }


    //GET METHODS

    public PublicObjective getPubObj(int index) throws InvalidIntArgumentException
    {
        if(index<0||index>=3)
            throw new InvalidIntArgumentException();
        return pubObjs[index];
    }

    public SchemeCard getSchemebyIndex(int index) throws InvalidIntArgumentException
    {
        if(index<0||index>=numPlayers)
            throw  new InvalidIntArgumentException();
        return playerSchemes[index];
    }

    public DraftPool getDraft()
    {
        return draft;
    }

    public RoundTrack getTrack()
    {
        return track;
    }


    //SET METHODS
    public void setTrack(RoundTrack roundTrack)
    {
        track=roundTrack;
    }
    public void setDraft(DraftPool draftPool)
    {
        draft=draftPool;
    }
    public void setPlayerScheme(int id, SchemeCard scheme)
    {
        playerSchemes[id] = scheme;
    }

    public void roundEnd() throws InvalidIntArgumentException, FullDataStructureException {
        RoundDice tempRD = draft.updateDraftDice();
        track.addRound(tempRD);
        }

}
