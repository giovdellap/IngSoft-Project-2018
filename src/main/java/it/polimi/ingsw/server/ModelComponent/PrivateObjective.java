package it.polimi.ingsw.server.ModelComponent;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;


public class PrivateObjective {
    private int color;

    /**
     * PrivateObjective Constructor
     * @param num
     */
    public PrivateObjective(int num) {

        color = num;
    }

    /**
     * gets color of the private objective
     * @return integer representing the color
     */
    public int getColor() {
        return color;
    }

    /**
     * calculates the bonus for each colored die
     * @param toCalc scheme card on which to calculate the bonus
     * @return integer representing the bonus to apply
     * @throws InvalidIntArgumentException
     */
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