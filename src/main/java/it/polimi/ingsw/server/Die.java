package it.polimi.ingsw.server;

import java.util.*;

public class Die
{
    private int value;
    private int color;

    public Die(int n)
    {
        color=n;
    }

    public void throwDie()
    {
        // tira dado
    }

    public void disableDie()
    {
        // usato per disabilitare i dadi nel sacchetto
        color=0;
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