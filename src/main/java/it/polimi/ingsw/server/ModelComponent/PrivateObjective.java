package it.polimi.ingsw.server.ModelComponent;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;


public class PrivateObjective {
    private int color;


    public PrivateObjective(int num) {

        color = num;
    }

    public int getColor() {
        return color;
    }


    public int calculateBonus(SchemeCard toCalc) throws InvalidIntArgumentException {
        int bonus = 0;

        for (int i = 0; i < 4; i++)
        {
            for (int j=0; j<5; j++)
            {
                if (toCalc.getDie(i,j).getColor()==color)
                    bonus=bonus+toCalc.getDie(i,j).getValue();
            }
        }
        return bonus;
    }

}