package it.polimi.ingsw.client.ModelComponentsMP;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.Die;

import java.util.ArrayList;

public class RoundDiceMP {

    private ArrayList<Die> diceVector;
    /**
     * RoundDice Constructor
     */
    public RoundDiceMP()
    {
        diceVector = new ArrayList<Die>();

    }

    /**
     * adds a die to the round dice vector
     * @param thisDie die to add
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
