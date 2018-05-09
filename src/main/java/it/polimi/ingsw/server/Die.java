package it.polimi.ingsw.server;

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

}