package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;

import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentMP;
import it.polimi.ingsw.commons.Die;

import java.util.ArrayList;

public class DraftPoolMP implements ModelComponentMP {

    private ArrayList<Die> draft;

    /**
     * DraftPoolMP Constructor
     */

    public DraftPoolMP(ArrayList<Die> dice)
    {
        draft = dice;
    }

    /**
     * deletes the die in the parameter position
     * @param index position
     * @throws InvalidIntArgumentException
     */

    public void pickUpDie(int index) throws InvalidIntArgumentException
    {
        if (index>=draft.size()||index<0) throw new InvalidIntArgumentException();

        draft.remove(index);
    }

    /**
     * replaces a die
     * @param index draft pool's index
     * @param toPlace die to place
     * @return old die
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */
    public Die replaceDie(int index, Die toPlace) throws InvalidIntArgumentException, GenericInvalidArgumentException
    {

        if (index>=draft.size() || index<0) throw new InvalidIntArgumentException();
        if (toPlace==null) throw new GenericInvalidArgumentException();

        Die tempDie = draft.get(index);
        draft.set(index, toPlace);

        return tempDie;
    }
    /**
     * returns die at parameter position
     * @param pos draft pool's index
     * @return die to return
     * @throws InvalidIntArgumentException
     */
    public Die returnDie(int pos) throws InvalidIntArgumentException
    {
        if (pos>=draft.size()||pos<0) throw new InvalidIntArgumentException();
        return draft.get(pos);

    }

    public int getSize()
    {
        return draft.size();
    }

}