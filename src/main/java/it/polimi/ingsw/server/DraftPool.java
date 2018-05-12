package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.*;

public class DraftPool
{
    private Die[] draft;
    private DiceContainer container;
    private int dim;

    public DraftPool(int players) throws InvalidIntArgumentException
    {
        dim=players*2+1;
        DiceContainer container = new DiceContainer();
        Die[] draft=container.throwDice(dim);
        for(int i=0;i<dim;i++)
            draft[i].throwDie();
    }

    public void pickUpDie(int index)
    {
        // deletes the die in the declared position
        int counter=0;
        while(draft[counter] != null)
            counter++;
        for(int i = index; i <=counter; i++)
            draft[i] = draft[i++];
        draft[counter] = null;
    }

    public RoundDice updateDraftDice() throws InvalidIntArgumentException, FullDataStructureException
    {
        // draft update at the beginning of the round
        int counter=0;
        while(draft[counter]!=null)
            counter++;
        RoundDice tempRD = new RoundDice(counter);
        for(int i=0;i<counter;i++)
        {
            tempRD.addDie(draft[i]);
            draft[i]=null;
        }
        draft=container.throwDice(dim);
        for(int i=0;i<dim;i++)
            draft[i].throwDie();
        return tempRD;
    }

    public void updateDraftNoDice() throws InvalidIntArgumentException
    {
        draft=container.throwDice(dim);
        for(int i=0;i<dim;i++)
            draft[i].throwDie();
    }

    public Die replaceDie(int index, Die toPlace)
    {
        // replaces a die in a certain position with a declared die
        Die tempDie = draft[index];
        draft[index]=toPlace;
        return tempDie;
    }

    public Die returnDie(int pos) {
        Die tempDie = null;
        if(pos<dim&&pos>=0)
        {
            if (draft[pos]!=null)
            {
                return draft[pos];
            }
            else if(draft[pos]==null)
            {
                return null;
            }
        }
        if(pos>=dim||pos<0) {
            return tempDie;
        }
        return tempDie;
    }

}