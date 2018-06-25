package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;
import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.Die;

import java.util.ArrayList;

public class RoundDiceMP {

    private ArrayList<Die> diceVector;
    private int dim=0;
    /**
     * RoundDice Constructor
     * @param par round dice dimension
     * @throws InvalidIntArgumentException
     */
    public RoundDiceMP(int par) throws InvalidIntArgumentException
    {
        if(par<0||par>9)
            throw new InvalidIntArgumentException();
        dim=par;
        diceVector = new ArrayList<Die>();

    }

    /**
     * adds a die to the round dice vector
     * @param thisDie die to add
     * @throws FullDataStructureException
     */
    public void addDie(Die thisDie)
    {
        diceVector.add(thisDie);
    }
    /**
     * gets die at parameter position
     * @param pos position of round dice
     * @return die to get
     * @throws InvalidIntArgumentException
     */
    public Die getDie(int pos) throws InvalidIntArgumentException
    {
        if(pos>diceVector.size()||pos<0)
            throw new InvalidIntArgumentException();
        return diceVector.get(pos);
    }

    /**
     * deletes a die from parameter position
     * @param pos
     */
    public void deleteDie(int pos)
    {
        diceVector.remove(pos);
    }
    /**
     * returns round dice dimension
     * @return integer representing round dice dimension
     */
    public int returnDim()
    {
        return diceVector.size();
    }

    /**
     * gets ArrayList of dice
     * @return
     */
    public ArrayList<Die> getDiceVector() {

        return diceVector;
    }

    /**
     * adds a position to the dice vector
     * @param pos
     * @param temp
     */
    public void addPos(int pos, Die temp)
    {
        diceVector.add(pos, temp);
    }
}
