package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class DiceContainer {
    private ArrayList<Die> dice;

    /**
     * DiceContainer Constructor
     */
    public DiceContainer() {

        dice = new ArrayList<Die>();
        for (int i = 0; i < 90; i++)
        {
            Die tempDie = new Die((i%5)+1);
            tempDie.throwDie();
            dice.add(tempDie);
        }
    }

    /**
     * throws parameter number of dice from the dice container
     * @param n number of dice to extract and throw
     * @return array of extracted and thrown dice
     * @throws InvalidIntArgumentException
     */
    public Die[] throwDice(int n) throws InvalidIntArgumentException {
        if(n<1||n>9)
            throw new InvalidIntArgumentException();
        Die[] tempVector = new Die[n];
        int index=0;
        for (int i = 0; i < n; i++) {
            index = (int)(Math.random() * dice.size());
            tempVector[i]=dice.get(index);
            dice.remove(index);
        }

        return tempVector;
    }

    /**
     * replaces a die in the container (for tool card eleven)
     * @param temp die to replace
     * @return new die of a new color
     * @throws InvalidIntArgumentException
     */
    public Die replaceDie(Die temp) throws InvalidIntArgumentException {
        dice.add(temp);
        return throwDice(1)[0];
    }


}