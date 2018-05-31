package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
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
    private SchemeCardMP[] tempSchemes;

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

    public void setMyScheme(int index, int fb) throws InvalidIntArgumentException {
        myScheme = tempSchemes[index];
        myScheme.setfb(fb);
    }

    public void setPlayerSchemesVect(int num)
    {
        playersSchemes = new SchemeCardMP[num];
    }

    public void setPlayerSchemes(int index, int id, int fb) throws InvalidIntArgumentException {
        playersSchemes[index] = scDeck.extractSchemebyID(id);
        playersSchemes[index].setfb(fb);
    }

    public void setTempSchemes(int id1, int id2) throws InvalidIntArgumentException {
        tempSchemes = new SchemeCardMP[2];
        tempSchemes[0] = scDeck.extractSchemebyID(id1);
        tempSchemes[1] = scDeck.extractSchemebyID(id2);
    }

    public int getMyPrObj()
    {
        return myPrObj.getColor();
    }

    public int getTempScheme(int index)
    {
        return tempSchemes[index].getID();
    }

    public int getPubObjs(int index)
    {
        return pubObjs[index].getId();
    }


}


