package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;

public class DieMP {

    private int value;
    private int color;
    //COLORI: 1 GIALLO/2 ROSSO/3 VERDE/4 BLU/ 5 VIOLA

    public DieMP(int i)
    {
        color=i;
        value=1;
    }

    public void throwDie()
    {
        value=(int)(Math.random()*5+1);
    }

    public void disableDie()
    {
        this.value=0;
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

    public void setValueTest(int n) throws InvalidIntArgumentException
    {
        if (n<0||n>6)throw new InvalidIntArgumentException(); value=n;
    }

    public String toString()
    {

        String tempString = "";

        if (this.color==1)
            tempString = "Die color: yellow , Die value: " + Integer.toString(this.getValue());

        if (this.color==2)
            tempString = "Die color: red , Die value: " + Integer.toString(this.getValue());

        if (this.color==3)
            tempString = "Die color: green , Die value: " + Integer.toString(this.getValue());

        if (this.color==4)
            tempString = "Die color: blue , Die value: " + Integer.toString(this.getValue());

        if (this.color==5)
            tempString = "Die color: violet , Die value: " + Integer.toString(this.getValue());

        return tempString;

    }
}