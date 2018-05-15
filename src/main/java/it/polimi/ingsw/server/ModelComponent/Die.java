package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.*;

public class Die
{
    private int value;
    private int color;

    public Die(int n)
    {
        color=n;
        value=1;
    }


    public void throwDie()
    {
        value = (int)(Math.random()*5+1);
        // tira dado
    }

    public void disableDie()
    {
        // usato per disabilitare i dadi nel sacchetto
        value=0;
    }

    public int getColor()
    {
        return color;
    }

    public int getValue()
    {
        return value;
    }

    public boolean isDisabled()
    {
        if(getValue()==0)
            return true;
        else
            return false;
    }

    public void setValueTest(int n) throws InvalidIntArgumentException {
        if (n<0||n>6)throw new InvalidIntArgumentException();
        value=n;
    }

}