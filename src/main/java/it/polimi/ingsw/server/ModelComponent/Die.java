package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.*;

public class Die
{
    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

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
    }

    public void disableDie()
    {
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

    public String toString() {

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