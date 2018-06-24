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

    /**
     * DraftPool Constructor
     * @param players number of players
     * @throws InvalidIntArgumentException
     */
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

    /**
     * deletes the die in the parameter position
     * @param index position
     * @throws InvalidIntArgumentException
     */
    public void pickUpDie(int index) throws InvalidIntArgumentException
    {
        if (index>=dim||index<0) throw new InvalidIntArgumentException();

        draft.remove(index);

    }

    /**
     * updates draft dice at the beginning of the round and shifts remaining dice to round track
     * @return new round dice
     * @throws InvalidIntArgumentException
     * @throws FullDataStructureException
     */

    public RoundDice updateDraftDice() throws InvalidIntArgumentException, FullDataStructureException
    {
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
        if (index>=draft.size()||index<0) throw new InvalidIntArgumentException();
        if (toPlace==null) throw new GenericInvalidArgumentException();

        Die tempDie = draft.get(index);
        draft.remove(index);
        if(index==draft.size())
            draft.add(tempDie);
        else
            draft.add(index,toPlace);
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

        if (pos>=dim||pos<0) throw new InvalidIntArgumentException();

        return draft.get(pos);

    }

    /**
     * gets number of dice in draft
     * @return draft size
     */
    public int getDiceNum()
    {
        return draft.size();
    }

    /**
     * gets ArrayList of dice from draft
     * @return draft
     */
    public ArrayList<Die> getDraft()
    {
        return draft;
    }

    /**
     * replaces draft pool with an ArrayList of dice (for tool card seven)
     * @param temp ArrayList of dice
     */
    public void replaceDraft(ArrayList<Die> temp)
    {
        draft = temp;
    }

    /**
     * tool card eleven replacement method
     * @param index draft pool's index
     * @return old die
     * @throws InvalidIntArgumentException
     */
    public Die toolCardElevenReplacement(int index) throws InvalidIntArgumentException {
        Die draftDie = draft.get(index);
        Die containerDie = container.replaceDie(draftDie);
        draft.remove(index);
        return containerDie;
    }
}