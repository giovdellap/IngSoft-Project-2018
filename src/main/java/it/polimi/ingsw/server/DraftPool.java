package it.polimi.ingsw.server;

import java.util.*;

public class DraftPool
{
    private Die[] draft;
    private DiceContainer container;
    private int dim;

    public DraftPool(int players)
    {
        dim=players*2+1;
        Die[] draft = new Die[dim];
        DiceContainer container = new DiceContainer();
        draft=container.throwDice(dim);
        for(int i=0;i<dim;i++)
            draft[i].throwDie();
    }

    public void pickUpDie(int index)
    {
        // deletes the die in the declared position
        int counter=0;
        while(draft[counter]!=null)
            counter++;
        for(int i=index;i<counter;i++)
            draft[i]=draft[i++];
        draft[counter]=null;
    }

    public RoundDice updateDraft()
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

    public Die replaceDie(int index, Die toPlace)
    {
        // replaces a die in a certain position with a declared die
        Die tempDie = draft[index];
        draft[index]=toPlace;
        return tempDie;
    }

    public Die returnDie(int pos)
    {
        Die tempDie=null;
        if(pos<dim)
        {
            if (draft[pos] == null)
                return null;
            else {
                tempDie = draft[pos];
                return tempDie;
            }
        }
        else
            return tempDie;

    }

}