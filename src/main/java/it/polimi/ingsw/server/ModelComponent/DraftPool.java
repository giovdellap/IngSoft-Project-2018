package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class DraftPool
{
    private ArrayList<Die> draft;
    private DiceContainer container;
    private int dim;

    public DraftPool(int players) throws InvalidIntArgumentException
    {
        if (players<2||players>4)throw new InvalidIntArgumentException();
        dim=(players*2)+1;
        container = new DiceContainer();
        draft = new ArrayList<Die>();
        Die[] temp = container.throwDice(dim);

        for(int i=0;i<dim;i++) {
            draft.add(temp[i]);
            draft.get(i).throwDie();
        }

    }


    public void pickUpDie(int index) throws InvalidIntArgumentException
    {
        // deletes the die in the declared position
        if (index>=dim||index<0) throw new InvalidIntArgumentException();

        draft.remove(index);

    }


    public RoundDice updateDraftDice() throws InvalidIntArgumentException, FullDataStructureException
    {
        // draft update at the beginning of the round
        RoundDice dice = new RoundDice(draft.size());

        for(Die die:draft)
            dice.addDie(die);

        Die[] temp = container.throwDice(dim);
        draft = new ArrayList<Die>();
        for(int i=0;i<dim;i++) {
            draft.add(temp[i]);
            draft.get(i).throwDie();

        }

        return dice;
    }

    public Die replaceDie(int index, Die toPlace) throws InvalidIntArgumentException, GenericInvalidArgumentException
    {
        // replaces a die in a certain position with a declared die
        if (index>=draft.size()||index<0) throw new InvalidIntArgumentException();
        if (toPlace==null) throw new GenericInvalidArgumentException();

        Die tempDie = draft.get(index);
        draft.remove(index);
        draft.set(index,toPlace);
        return tempDie;
    }


    public Die returnDie(int pos) throws InvalidIntArgumentException
    {

        if (pos>=dim||pos<0) throw new InvalidIntArgumentException();

        return draft.get(pos);

    }

    public int getDiceNum()
    {
        return draft.size();
    }

    public ArrayList<Die> getDraft()
    {
        return draft;
    }
}