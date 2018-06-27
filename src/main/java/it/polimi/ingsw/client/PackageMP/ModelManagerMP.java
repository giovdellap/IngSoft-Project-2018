package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.SimpleLogger;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ModelManagerMP
{
    private SimpleLogger logger;

    private int numPlayers=0;
    private DraftPoolMP draft;
    private RoundTrackMP track;

    private SchemesDeck scDeck;
    private PublicObjectiveMP[] pubObjs;

    private SchemeCard myScheme;
    private PrivateObjectiveMP myPrObj;

    private SchemeCard[] tempSchemes;

    /**
     * ModelManagerMP Constructor
     * @throws InvalidIntArgumentException
     */
    public ModelManagerMP() throws InvalidIntArgumentException {
        scDeck = new SchemesDeck();
        pubObjs = new PublicObjectiveMP[3];
        track = new RoundTrackMP();

        logger = new SimpleLogger(0, false);
    }

    public void setMyPrivObj(int id) throws InvalidIntArgumentException {
        myPrObj = new PrivateObjectiveMP(id);
    }

    public void setPubObjs(int[] ids) throws InvalidIntArgumentException {
        for(int i=0; i<3; i++)
        {
            pubObjs[i] = new PublicObjectiveMP(ids[i]);
        }
    }

    public void setMyScheme(int index, int fb) throws InvalidIntArgumentException {
        if(index==tempSchemes[0].getID())
            myScheme = tempSchemes[0];
        else
            myScheme=tempSchemes[1];
        myScheme.setfb(fb);
    }


    public void setTempSchemes(int id1, int id2) throws InvalidIntArgumentException, FileNotFoundException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException {
        tempSchemes = new SchemeCard[2];
        tempSchemes[0] = scDeck.extractSchemebyID(id1);
        tempSchemes[1] = scDeck.extractSchemebyID(id2);
    }

    public PrivateObjectiveMP getMyPrObj()
    {
        return myPrObj;
    }

    public SchemeCard getTempScheme(int index)
    {
        return tempSchemes[index];
    }

    public PublicObjectiveMP getPubObjs(int index)
    {
        return pubObjs[index];
    }

    //DRAFTPOOL
    public void setDraft(ArrayList<Die> arg) throws InvalidIntArgumentException {draft = new DraftPoolMP(arg); }

    public void setDraft(DraftPoolMP draft) {
        this.draft = draft;
    }

    public DraftPoolMP getDraft()
    {
        return draft;
    }

    //TRACK
    public void setTrack(ArrayList<Die> arg, int round) throws InvalidIntArgumentException, FullDataStructureException {
        RoundDiceMP tempRD = new RoundDiceMP(arg.size());
        for(int i=0;i<arg.size();i++) {
            logger.log("COLOR: "+Integer.toString(arg.get(i).getColor()));
            tempRD.addDie(arg.get(i));
            System.out.println("Value: "+Integer.toString(arg.get(i).getValue())+" color: "+Integer.toString(arg.get(i).getColor()));
        }
        track.setSpecificRoundDice(tempRD, round);
    }
    public RoundTrackMP getTrack()
    {
        return track;
    }

    public void addRound(ArrayList<Die> arg) throws InvalidIntArgumentException, FullDataStructureException {
        RoundDiceMP tempRD = new RoundDiceMP(arg.size());
        for(int i=0;i<arg.size();i++)
            tempRD.addDie(arg.get(i));
        track.addRound(tempRD);
    }


}


