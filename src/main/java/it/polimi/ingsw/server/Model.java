package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
    private ArrayList<SchemeCard> playerSchemes;
    private ArrayList<PrivateObjective> playersPrObjs;

    /**
     * Model Constructor
     * @param numPlayers number of players
     */
    public Model(int numPlayers)
    {
            this.numPlayers=numPlayers;
    }

    /**
     * initializes and extracts private objectives
     * @throws InvalidIntArgumentException
     */
    public void setPrivateObjectives() throws InvalidIntArgumentException
    {
        prDeck = new PrivateObjectivesDeck();
        playersPrObjs = new ArrayList<PrivateObjective>();
        PrivateObjective[] tempPr = prDeck.extractPrObj(numPlayers);
        for(int i=0;i<tempPr.length;i++)
            playersPrObjs.add(tempPr[i]);
    }

    /**
     * initializes and extracts schemes from deck
     * @throws InvalidIntArgumentException
     */
    public void setSchemesDeck() throws InvalidIntArgumentException, FileNotFoundException {
        scDeck = new SchemesDeck();
        tempSchemes = scDeck.extractSchemes(numPlayers*2);
        playerSchemes = new ArrayList<SchemeCard>();
    }

    /**
     * initializes and extracts public objectives
     * @throws InvalidIntArgumentException
     */
    public void setPubObjs() throws InvalidIntArgumentException
    {
        pubDeck = new PublicObjectivesDeck();
        pubObjs = pubDeck.extractPubObjs();
    }

    /**
     *
     * @param index scheme's index
     * @return temporary schemes before selection
     */
    public SchemeCard getTempSchemes(int index)
    {
        return tempSchemes[index];
    }

    /**
     *
     * @param index private objective's index
     * @return private objectives at index
     * @throws InvalidIntArgumentException
     */
    public PrivateObjective getPrivateObjective(int index) throws InvalidIntArgumentException
    {
        if(index<0||index>=numPlayers)
            throw new InvalidIntArgumentException();

        return playersPrObjs.get(index);
    }

    /**
     * sets the final number of players
     * @param numPlayers number of players
     */

    public void setFinalNumPlayers(int numPlayers)
    {
        this.numPlayers=numPlayers;
    }

    /**
     * initializes draftpool
     * @throws InvalidIntArgumentException
     */
    public void draftPoolInitialization() throws InvalidIntArgumentException {
        draft = new DraftPool(numPlayers);
    }

    /**
     * initializes roundtrack
     */
    public void roundTrackInitialization()
    {
        track = new RoundTrack();
    }


    /**
     *
     * @param index public objective's index
     * @return public objectives in index position
     * @throws InvalidIntArgumentException
     */
    public PublicObjective getPubObj(int index) throws InvalidIntArgumentException
    {
        if(index<0||index>=3)
            throw new InvalidIntArgumentException();
        return pubObjs[index];
    }

    /**
     *
     * @param index scheme's index
     * @return player's scheme in index's position
     * @throws InvalidIntArgumentException
     */
    public SchemeCard getSchemebyIndex(int index) throws InvalidIntArgumentException
    {
        if(index<0||index>=numPlayers)
            throw  new InvalidIntArgumentException();
        return playerSchemes.get(index);
    }

    /**
     *
     * @return draftpool
     */
    public DraftPool getDraft()
    {
        return draft;
    }

    /**
     *
     * @return roundtrack
     */
    public RoundTrack getTrack()
    {
        return track;
    }


    /**
     * sets roundtrack
     * @param roundTrack
     */
    public void setTrack(RoundTrack roundTrack)
    {
        track=roundTrack;
    }

    /**
     * sets draftpool
     * @param draftPool
     */
    public void setDraft(DraftPool draftPool)
    {
        draft=draftPool;
    }

    /**
     * sets players scheme
     * @param id player's id
     * @param scheme player's scheme
     */
    public void setPlayerScheme(int id, SchemeCard scheme) {
        playerSchemes.set(id, scheme);

    }

    public void setPlayerSchemeFirstTime(int id, int schemeID, int fb) throws InvalidIntArgumentException {
        if(tempSchemes[id+(tempSchemes.length/2)].getID()==schemeID)
            tempSchemes[id]=tempSchemes[id+(tempSchemes.length/2)];
        tempSchemes[id].setfb(fb);

    }
    public void setPlayerSchemeFirstTime(SchemeCard scheme, int id)
    {
        tempSchemes[id]=scheme;
    }

    public void initializationEnd()
    {
        playerSchemes=new ArrayList<SchemeCard>();
        for(int i=0;i<(tempSchemes.length/2);i++)
            playerSchemes.add(tempSchemes[i]);
    }

    /**
     * updates the roundtrack with dice remaining in the draft
     * @throws InvalidIntArgumentException
     * @throws FullDataStructureException
     */
    public void roundEnd() throws InvalidIntArgumentException, FullDataStructureException {
        RoundDice tempRD = draft.updateDraftDice();
        track.addRound(tempRD);

    }

    public void firstRoundCleaner(ArrayList<Integer> ids)
    {
        for(Integer id : ids)
            playersPrObjs.remove(id);

    }

}
