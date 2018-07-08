package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.*;
import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.SimpleLogger;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class ModelManagerMP
{
    private SimpleLogger logger;

    private DraftPoolMP draft;
    private RoundTrackMP track;

    private SchemesDeck scDeck;
    private PublicObjectiveMP[] pubObjs;

    private SchemeCard myScheme;
    private PrivateObjectiveMP myPrObj;

    private SchemeCard[] tempSchemes;

    /**
     * ModelManagerMP Constructor
     */
    public ModelManagerMP()
    {
        scDeck = new SchemesDeck();
        pubObjs = new PublicObjectiveMP[3];
        track = new RoundTrackMP();

        logger = new SimpleLogger(0, false);
    }

    public void setMyPrivObj(int id) throws InvalidIntArgumentException {myPrObj = new PrivateObjectiveMP(id);}

    public void setPubObjs(int[] ids) throws InvalidIntArgumentException
    {
        for(int i=0; i<3; i++)
            pubObjs[i] = new PublicObjectiveMP(ids[i]);

    }

    public void setMyScheme(int index, int fb) throws InvalidIntArgumentException
    {
        if(index==tempSchemes[0].getID())
            myScheme = tempSchemes[0];
        else
            myScheme=tempSchemes[1];
        myScheme.setfb(fb);
    }
    public void setMyScheme(SchemeCard scheme)
    {
        myScheme=scheme;
    }


    public void setTempSchemes(int id1, int id2) throws FileNotFoundException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, UnsupportedEncodingException {
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
    public void setDraft(ArrayList<Die> arg) {draft = new DraftPoolMP(arg); }

    public void setDraft(DraftPoolMP draft) {
        this.draft = draft;
    }

    public DraftPoolMP getDraft()
    {
        return draft;
    }

    //TRACK
    public void setTrack(ArrayList<Die> arg, int round) throws InvalidIntArgumentException
    {
        RoundDiceMP tempRD = new RoundDiceMP();
        for(int i=0;i<arg.size();i++)
        {
            tempRD.addDie(arg.get(i));
        }
        track.setSpecificRoundDice(tempRD, round);
    }
    public RoundTrackMP getTrack()
    {
        return track;
    }

    public void addRound(ArrayList<Die> arg)
    {
        RoundDiceMP tempRD = new RoundDiceMP();
        for(int i=0;i<arg.size();i++)
            tempRD.addDie(arg.get(i));
        track.addRound(tempRD);
    }


}


