package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.PrivateObjective;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;

import java.util.ArrayList;

public class ModelManagerMP
{
    public MinorLogger mmLogger;

    private int numPlayers=0;
    private DraftPoolMP draft;
    private RoundTrackMP track;

    private SchemesDeckMP scDeck;
    private PublicObjectiveMP[] pubObjs;

    private SchemeCardMP myScheme;
    private PrivateObjectiveMP myPrObj;

    private SchemeCardMP[] tempSchemes;

    public ModelManagerMP() throws InvalidIntArgumentException {
        scDeck = new SchemesDeckMP();
        pubObjs = new PublicObjectiveMP[3];
        track = new RoundTrackMP();
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


    public void setTempSchemes(int id1, int id2) throws InvalidIntArgumentException {
        tempSchemes = new SchemeCardMP[2];
        tempSchemes[0] = scDeck.extractSchemebyID(id1);
        tempSchemes[1] = scDeck.extractSchemebyID(id2);
    }

    public PrivateObjectiveMP getMyPrObj()
    {
        return myPrObj;
    }

    public SchemeCardMP getTempScheme(int index)
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
        for(int i=0;i<arg.size();i++)
            tempRD.addDie(arg.get(i));

        track.setSpecificRoundDice(tempRD, round);
    }
    public RoundTrackMP getTrack()
    {
        return track;
    }


}


