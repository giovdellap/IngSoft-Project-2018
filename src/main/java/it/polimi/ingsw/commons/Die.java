package it.polimi.ingsw.commons;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class Die
{
    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    private int value;
    private int color;

    /**
     * Die Constructor
     * @param n color to assign to die
     */
    public Die(int n)
    {
        color=n;
        value=1;
    }

    /**
     * rolls a die
     */
    public void throwDie()
    {
        value = ((int)(Math.random()*6+1));
    }

    /**
     * disables a die setting its value to 0
     */
    public void disableDie()
    {
        value=0;
    }

    /**
     * gets the die color
     * @return color
     */
    public int getColor()
    {
        return color;
    }

    /**
     * gets the die value
     * @return
     */
    public int getValue()
    {
        return value;
    }

    /**
     * it tells us if the the die is disabled or not
     * @return
     */
    public boolean isDisabled()
    {
        if(getValue()==0)
            return true;
        else
            return false;
    }

    /**
     * sets a precise value to the die, used in most of the tool cards
     * @param n value to set to the die
     * @throws InvalidIntArgumentException
     */
    public void setValue(int n) throws InvalidIntArgumentException {
        if (n<0||n>6)throw new InvalidIntArgumentException();
        value=n;
    }


}