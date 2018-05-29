package it.polimi.ingsw.clientOld.ModelComponentsMP;

import it.polimi.ingsw.clientOld.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.clientOld.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.clientOld.MultiPackage.ModelComponentMP;

public class DraftPoolMP implements ModelComponentMP {

    private DieMP[] draft;
    private int dim;


    public DraftPoolMP(DieMP[] dice) throws InvalidIntArgumentException
    {
        dim = dice.length;
        draft = dice;
    }


    public void pickUpDie(int index) throws InvalidIntArgumentException
    {
        if (index>=dim||index<0||draft[index].isDisabled()) throw new InvalidIntArgumentException();

        int counter=index;
        boolean flag=true;
        boolean reachedMax=false;
        while(flag)
        {
            if (counter==dim-1)
            {
                flag = false;
                reachedMax=true;
            }
            if(!reachedMax) {
                if (draft[counter].isDisabled())
                    flag = false;
                if (flag)
                    counter++;
            }
        }

        for(int i = index; i <counter-1; i++)
            draft[i] = draft[i++];

        draft[counter-1].disableDie();

    }


    public void updateDraft(DieMP[] dice)
    {
        draft=dice;
    }


    public DieMP replaceDie(int index, DieMP toPlace) throws InvalidIntArgumentException, GenericInvalidArgumentException
    {

        if (index>=dim || index<0 ||draft[index].isDisabled()) throw new InvalidIntArgumentException();
        if (toPlace==null) throw new GenericInvalidArgumentException();

        DieMP tempDie = draft[index];
        draft[index]=toPlace;

        return tempDie;
    }


    public DieMP returnDie(int pos) throws InvalidIntArgumentException
    {
        if (pos>=dim||pos<0) throw new InvalidIntArgumentException();
        if(draft[pos]!=null&&draft[pos].isDisabled()) throw new InvalidIntArgumentException();
        DieMP tempDie = draft[pos];
        return tempDie;

    }

}