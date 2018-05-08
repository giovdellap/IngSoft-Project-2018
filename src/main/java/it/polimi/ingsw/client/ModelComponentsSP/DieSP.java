package it.polimi.ingsw.client.ModelComponentsSP;

import java.util.*;


public class DieSP {

    private int value;
    private int color;
    //COLORI: 1 GIALLO/2 ROSSO/3 VERDE/4 BLU/ 5 VIOLA

    public DieSP(int i)
    {
        color=i;
    }

    public void throwDie()
    {
        value=(int)(Math.random()*5+1);
    }

    public void disableDie() {color=0;}

    public int getColor() {return color;}

    public int getValue()
    {
        return value;
    }

}