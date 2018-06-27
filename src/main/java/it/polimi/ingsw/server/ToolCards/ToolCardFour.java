package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.CheckingMethods;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class ToolCardFour extends ToolCard {

    SchemeCard tempScheme;

    /**
     * ToolCardFour Constructor
     */
    public ToolCardFour() {
        setToolCardName("Lathekin");
        setId(4);
    }

    /**
     * sets scheme
     * @param tempScheme
     */
    public void setTempScheme(SchemeCard tempScheme) {
        this.tempScheme = tempScheme;
    }

    /**
     * checks if the tool card can be used or not
     * @param x01 row of the first die to shift
     * @param y01 column of the first die to shift
     * @param x02 row of the second die to shift
     * @param y02 column of the second die to shift
     * @param scheme scheme on which to check the move
     * @param x11 row where to place the first die
     * @param y11 column where to place the first die
     * @param x22 row where to place the second die
     * @param y22 column where to place the second die
     * @return true if it can be used, false if it can't
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public boolean checkToolCardFour(int x01, int y01, int x02, int y02, SchemeCard scheme, int x11, int y11, int x22, int y22) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;

        CheckingMethods checkingMethods = new CheckingMethods();

        if (x01 < 0 || x01 > 3 || y01 < 0 || y01 > 4 || x02 < 0 || x02 > 3 || y02 < 0 || y02 > 4 || x11 < 0 || x11 > 3 || y11 < 0 || y11 > 4 || x22 < 0 || x22 > 3 || y22 < 0 || y22 > 4)
            throw new InvalidIntArgumentException();

        if (scheme.getDie(x01, y01).isDisabled() || scheme.getDie(x02, y02).isDisabled()) {
            System.out.println("Dice to position not found");
            flag = false;
        }

        if (!scheme.getDie(x11, y11).isDisabled() || !scheme.getDie(x22, y22).isDisabled()) {
            System.out.println("Positions are already occupied");
            flag = false;
        }

        if (flag) {
            Die toPlace1 = new Die(scheme.getDie(x01, y01).getColor());
            toPlace1.setValue(scheme.getDie(x01, y01).getValue());
            Die toPlace2 = new Die(scheme.getDie(x02, y02).getColor());
            toPlace2.setValue(scheme.getDie(x02, y02).getValue());
            tempScheme.getDie(x01, y01).disableDie();
            tempScheme.getDie(x02, y02).disableDie();

            boolean check = false;

            if (checkingMethods.checkMove(tempScheme, toPlace1, x11, y11))
                if (checkingMethods.checkMove(tempScheme, toPlace2, x22, y22))
                    check = true;

            tempScheme.setDie(toPlace1, x01, y01);
            tempScheme.setDie(toPlace2, x02, y02);

            return check;

        }

            return false;

        }

    /**
     * applies the tool card effects to the scheme card
     * @param x01 row of the first die to shift
     * @param y01 column of the first die to shift
     * @param x02 row of the second die to shift
     * @param y02 column of the second die to shift
     * @param x11 row where to place the first die
     * @param y11 column where to place the first die
     * @param x22 row where to place the second die
     * @param y22 column where to place the second die
     * @return modified scheme card
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */
    public SchemeCard applyModifies(int x01, int y01, int x02, int y02, int x11, int y11, int x22, int y22) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        tempScheme.shiftDie(x01, y01, x11, y11);
        tempScheme.shiftDie(x02, y02, x22, y22);

        return tempScheme;

    }


}
