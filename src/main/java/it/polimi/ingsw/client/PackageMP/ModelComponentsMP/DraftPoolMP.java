package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;

import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentMP;
import it.polimi.ingsw.commons.Die;

import java.util.ArrayList;

public class DraftPoolMP implements ModelComponentMP {

    private ArrayList<Die> draft;


    public DraftPoolMP(ArrayList<Die> dice) throws InvalidIntArgumentException
    {
        draft = dice;
    }


    public void pickUpDie(int index) throws InvalidIntArgumentException
    {
        if (index>=draft.size()||index<0) throw new InvalidIntArgumentException();

        draft.remove(index);
    }


    public void updateDraft(ArrayList<Die> dice)
    {
        draft=dice;
    }


    public Die replaceDie(int index, Die toPlace) throws InvalidIntArgumentException, GenericInvalidArgumentException
    {

        if (index>=draft.size() || index<0) throw new InvalidIntArgumentException();
        if (toPlace==null) throw new GenericInvalidArgumentException();

        Die tempDie = draft.get(index);
        draft.set(index, toPlace);

        return tempDie;
    }

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