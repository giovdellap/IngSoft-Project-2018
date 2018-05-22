package it.polimi.ingsw.client.MultiPackage;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.*;
import it.polimi.ingsw.server.ModelComponent.PublicObjective;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;

public class ModelMP
{

    private int numPlayers=0;
    private DraftPoolMP draft;
    //TO INSERT: RoundtrackMP
    //TO INSERT: RoundDiceMP


    private SchemesDeckMP scDeck;
    private PublicObjectiveMP[] pubObjs;

    private SchemeCardMP myScheme;
    private PrivateObjectiveMP myPrObj;

    private SchemeCardMP[] playersSchemes;

    public ModelMP() throws InvalidIntArgumentException {
        scDeck = new SchemesDeckMP();
        pubObjs = new PublicObjectiveMP[3];

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

    public void setMyScheme(int[] arg) throws InvalidIntArgumentException {
        myScheme = scDeck.extractSchemebyID(arg[0]);
        myScheme.setfb(arg[1]);
    }

    public void setPlayerSchemesVect(int num)
    {
        playersSchemes = new SchemeCardMP[num];
    }

    public void setPlayerSchemes(int index, int id, int fb) throws InvalidIntArgumentException {
        playersSchemes[index] = scDeck.extractSchemebyID(id);
        playersSchemes[index].setfb(fb);
    }
}


