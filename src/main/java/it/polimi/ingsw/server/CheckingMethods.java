package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class CheckingMethods {

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMBERS 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    /**
     * CheckingMethods Constructor
     */

    public CheckingMethods() {

    }

    /**
     * method which check if a move can be executed
     * @param scheme scheme to check move on
     * @param dieToPlace Die to place on scheme
     * @param x row
     * @param y column
     * @return true if move can be executed, false if it can't
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */
    public boolean checkMove(SchemeCard scheme, Die dieToPlace, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;

        if (!scheme.getDie(x, y).isDisabled()) {
            System.out.println("That position is already occupied");
            return false;
        }

        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if ((x + i < 4) && (x + i > -1) && (y + j < 5) && (y + j > -1)) {
                    if (!scheme.getDie(x + i, y + j).isDisabled()) {
                        flag = true;
                    }
                }

        if (!flag) {
            System.out.println("You must position your die orthogonally or diagonally adjacent to another die");
            return false;
        }


        if (scheme.getCell(scheme.getfb(), x, y) > 0 && scheme.getCell(scheme.getfb(), x, y) < 6 && dieToPlace.getColor() != scheme.getCell(scheme.getfb(), x, y)) {
            System.out.println("You must position your die on the same color cell of your scheme");
            return false;
        }


        if (scheme.getCell(scheme.getfb(), x, y) > 5 && scheme.getCell(scheme.getfb(), x, y) < 12 && dieToPlace.getValue() != (scheme.getCell(scheme.getfb(), x, y) - 5)) {
            System.out.println("You must position your die on the same number cell of your scheme");
            return false;
        }


        if (x + 1 < 4) {
            if (!scheme.getDie(x + 1, y).isDisabled())
                if (scheme.getDie(x + 1, y).getColor() == dieToPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x + 1, y).isDisabled())
                if (scheme.getDie(x + 1, y).getValue() == dieToPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (x - 1 > -1) {
            if (!scheme.getDie(x - 1, y).isDisabled())
                if (scheme.getDie(x - 1, y).getColor() == dieToPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x - 1, y).isDisabled())
                if (scheme.getDie(x - 1, y).getValue() == dieToPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y + 1 < 5) {
            if (!scheme.getDie(x, y + 1).isDisabled())
                if (scheme.getDie(x, y + 1).getColor() == dieToPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x, y + 1).isDisabled())
                if (scheme.getDie(x, y + 1).getValue() == dieToPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y - 1 > -1) {
            if (!scheme.getDie(x, y - 1).isDisabled())
                if (scheme.getDie(x, y - 1).getColor() == dieToPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x, y - 1).isDisabled())
                if (scheme.getDie(x, y - 1).getValue() == dieToPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        return true;

    }

    /**
     * method which check if the first move of a player's match can be executed
     * @param scheme scheme to check move on
     * @param dieToPlace Die to place on scheme
     * @param x row
     * @param y column
     * @return true if the move can be executed, false if it can't
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */
    public boolean checkFirstMove(SchemeCard scheme, Die dieToPlace, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        if(x!=0 && x!=3 && y!=0 && y!=4)
            return false;
        if(scheme.getCell(scheme.getfb(), x, y)>0&&scheme.getCell(scheme.getfb(), x, y)<6&&scheme.getCell(scheme.getfb(), x, y)!=dieToPlace.getColor())
            return false;
        if(scheme.getCell(scheme.getfb(), x, y)>5&&scheme.getCell(scheme.getfb(), x, y)<12&&(scheme.getCell(scheme.getfb(), x, y)-5)!=dieToPlace.getValue())
            return false;

        return true;
    }
}