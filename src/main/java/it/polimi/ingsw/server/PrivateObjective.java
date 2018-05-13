package it.polimi.ingsw.server;
import java.util.*;


public class PrivateObjective {
    private int color;

    public PrivateObjective(int num) {

        color = num;
    }

    public int getColor() {
        return color;
    }

    public int calculateBonus(SchemeCard toCalc)
    {
        return 0;
    }


}