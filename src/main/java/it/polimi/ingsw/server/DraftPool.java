package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.*;

public class DraftPool
{
    private Die[] draft;
    private DiceContainer container;
    private int dim;

    public DraftPool(int players) throws InvalidIntArgumentException
    {
        if (players<2||players>4)throw new InvalidIntArgumentException();
        dim=(players*2)+1;
        DiceContainer container = new DiceContainer();
        draft = container.throwDice(dim);
        for(int i=0;i<dim;i++) {
            draft[i].throwDie();

        }
    }


    public void pickUpDie(int index) throws InvalidIntArgumentException
    {
        // deletes the die in the declared position
        if (index>=dim||index<0||draft[index].isDisabled()) throw new InvalidIntArgumentException();
        int counter=index;
        boolean flag=true;
        while(flag)
        {
            if (draft[counter].isDisabled())
                flag=false;
            if (counter==dim-1)
                flag=false;
            if (flag)
                counter++;
        }


        for(int i = index; i <counter; i++)
        {
            draft[i] = draft[i++];

        }

        draft[counter].disableDie();

    }


    public RoundDice updateDraftDice() throws InvalidIntArgumentException, FullDataStructureException
    {
        // draft update at the beginning of the round
        int counter=0;
        while(!draft[counter].isDisabled())
            counter++;
        RoundDice tempRD = new RoundDice(counter);
        for(int i=0;i<counter;i++)
        {
            tempRD.addDie(draft[i]);
            draft[i].disableDie();
        }
        draft=container.throwDice(dim);
        for(int i=0;i<dim;i++)
            draft[i].throwDie();
        return tempRD;
    }

    public Die replaceDie(int index, Die toPlace) throws InvalidIntArgumentException, GenericInvalidArgumentException
    {
        // replaces a die in a certain position with a declared die
        if (index>=dim||index<0||draft[index].isDisabled()) throw new InvalidIntArgumentException();
        if (toPlace==null) throw new GenericInvalidArgumentException();

        Die tempDie = draft[index];
        draft[index]=toPlace;
        return tempDie;
    }


    public Die returnDie(int pos) throws InvalidIntArgumentException
    {

        if (pos>=dim||pos<0) throw new InvalidIntArgumentException();
        if(draft[pos]!=null&&draft[pos].isDisabled()) throw new InvalidIntArgumentException();
        Die tempDie = draft[pos];
        return tempDie;

    }

}