package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class RoundDice
{
    private Die[] diceVector;
    private int index=0;
    private int dim=0;

    /**
     * RoundDice Constructor
     * @param par round dice dimension
     * @throws InvalidIntArgumentException
     */
    public RoundDice(int par) throws InvalidIntArgumentException
    {
        if(par<0||par>9)
            throw new InvalidIntArgumentException();
        dim=par;
        diceVector = new Die[dim];
        for(int i=0;i<dim;i++)
            diceVector[i]=new Die(0);
    }

    /**
     * adds a die to the round dice vector
     * @param thisDie die to add
     * @throws FullDataStructureException
     */
    public void addDie(Die thisDie) throws FullDataStructureException
    {
        if(index<dim)
        {

            diceVector[index] = thisDie;
            index++;
        }
        else
            throw new FullDataStructureException();
    }

    /**
     * gets die at parameter position
     * @param pos position of round dice
     * @return die to get
     * @throws InvalidIntArgumentException
     */
    public Die getDie(int pos) throws InvalidIntArgumentException
    {
        if(pos>index||pos<0||diceVector[pos].isDisabled())
            throw new InvalidIntArgumentException();
        return diceVector[pos];
    }

    /**
     * returns round dice dimension
     * @return integer representing round dice dimension
     */

    public int returnDim()
    {
        return dim;
    }

    /**
     * replaces a die
     * @param toPlace die to replace
     * @param pos round dice index
     * @return old die
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public Die replaceDie(Die toPlace, int pos) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        if (pos>=dim||pos<0||diceVector[pos].isDisabled()) throw new InvalidIntArgumentException();
        if (toPlace==null) throw new GenericInvalidArgumentException();

        Die tempDie = diceVector[pos];
        diceVector[pos]=toPlace;
        return tempDie;
    }
}
