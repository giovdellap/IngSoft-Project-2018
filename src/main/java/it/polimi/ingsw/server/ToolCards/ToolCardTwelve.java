package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.RoundDice;
import it.polimi.ingsw.server.ModelComponent.RoundTrack;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class ToolCardTwelve extends ToolCard {

    public ToolCardTwelve() {
        setToolCardName("Tap Wheel");
        setId(12);
    }

    public boolean checkToolCardTwelve(RoundTrack track, int turn, int pos, int x01, int y01, int x02, int y02, SchemeCard scheme, int x11, int y11, int x22, int y22) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        RoundDice temp = track.returnNTurnRoundDice(turn);
        int color = temp.getDie(pos).getColor();

        if (color!=scheme.getDie(x01,y01).getColor() || color!=scheme.getDie(x02,y02).getColor()) {
            System.out.println("The colors are not equal to the chosen die");
            return false;
        }

        if(scheme.getDie(x01,y01).getColor()!=scheme.getDie(x02,y02).getColor()) {
            System.out.println("The colors of the two dice are not the same");
            return false;
        }

        if (scheme.getDie(x01,y01).isDisabled() || scheme.getDie(x02,y02).isDisabled()) {
            System.out.println("Dice to position not found");
            return false;
        }

        if (!scheme.getDie(x11, y11).isDisabled() || !scheme.getDie(x22, y22).isDisabled()) {
            System.out.println("Positions are already occupied");
            return false;
        }

        Die toPlace1 = scheme.getDie(x01,y01);
        Die toPlace2 = scheme.getDie(x02,y02);


        boolean flag = false;

        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if ((x11 + i < 4) && (x11 + i > -1) && (y11 + j < 5) && (y11 + j > -1))
                    if(!scheme.getDie(x11 + i, y11 + j).isDisabled())
                        flag = true;


        if (!flag) {
            System.out.println("You must position your die orthogonally or diagonally adjacent to another die");
            return false;
        }

        flag = false;

        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if ((x22 + i < 4) && (x22 + i > -1) && (y22 + j < 5) && (y22 + j > -1))
                    if(!scheme.getDie(x22 + i, y22 + j).isDisabled())
                        flag = true;


        if (!flag) {
            System.out.println("You must position your die orthogonally or diagonally adjacent to another die");
            return false;
        }

        if (scheme.getCell(scheme.getfb(), x11, y11) > 0 && scheme.getCell(scheme.getfb(), x11, y11) < 6 && toPlace1.getColor() != scheme.getCell(scheme.getfb(), x11, y11)) {
            System.out.println("You must position your die on the same color cell of your scheme");
            return false;
        }


        if (scheme.getCell(scheme.getfb(), x11, y11) > 5 && scheme.getCell(scheme.getfb(), x11, y11) < 12 && toPlace1.getValue() != (scheme.getCell(scheme.getfb(), x11, y11) - 5)) {
            System.out.println("You must position your die on the same number cell of your scheme");
            return false;
        }


        if (scheme.getCell(scheme.getfb(), x22, y22) > 0 && scheme.getCell(scheme.getfb(), x22, y22) < 6 && toPlace2.getColor() != scheme.getCell(scheme.getfb(), x22, y22)) {
            System.out.println("You must position your die on the same color cell of your scheme");
            return false;
        }


        if (scheme.getCell(scheme.getfb(), x22, y22) > 5 && scheme.getCell(scheme.getfb(), x22, y22) < 12 && toPlace2.getValue() != (scheme.getCell(scheme.getfb(), x22, y22) - 5)) {
            System.out.println("You must position your die on the same number cell of your scheme");
            return false;
        }

        // check color and value restrictions for the placement of the first die

        if (x11 + 1 < 4) {
            if (!scheme.getDie(x11 + 1, y11).isDisabled())
                if (scheme.getDie(x11 + 1, y11).getColor() == toPlace1.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x11 + 1, y11).isDisabled())
                if (scheme.getDie(x11 + 1, y11).getValue() == toPlace1.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (x11 - 1 > -1) {
            if (!scheme.getDie(x11 - 1, y11).isDisabled())
                if (scheme.getDie(x11 - 1, y11).getColor() == toPlace1.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x11 - 1, y11).isDisabled())
                if (scheme.getDie(x11 - 1, y11).getValue() == toPlace1.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y11 + 1 < 5) {
            if (!scheme.getDie(x11, y11 + 1).isDisabled())
                if (scheme.getDie(x11, y11 + 1).getColor() == toPlace1.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x11, y11 + 1).isDisabled())
                if (scheme.getDie(x11, y11 + 1).getValue() == toPlace1.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y11 - 1 > -1) {
            if (!scheme.getDie(x11, y11 - 1).isDisabled())
                if (scheme.getDie(x11, y11 - 1).getColor() == toPlace1.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x11, y11 - 1).isDisabled())
                if (scheme.getDie(x11, y11 - 1).getValue() == toPlace1.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        //check color and value restrictions for the placement of the second die

        if (x22 + 1 < 4) {
            if (!scheme.getDie(x22 + 1, y22).isDisabled())
                if (scheme.getDie(x22 + 1, y22).getColor() == toPlace2.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x22 + 1, y22).isDisabled())
                if (scheme.getDie(x22 + 1, y22).getValue() == toPlace2.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (x22 - 1 > -1) {
            if (!scheme.getDie(x22 - 1, y22).isDisabled())
                if (scheme.getDie(x22 - 1, y22).getColor() == toPlace2.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x22 - 1, y22).isDisabled())
                if (scheme.getDie(x22 - 1, y22).getValue() == toPlace2.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y22 + 1 < 5) {
            if (!scheme.getDie(x22, y22 + 1).isDisabled())
                if (scheme.getDie(x22, y22 + 1).getColor() == toPlace2.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x22, y22 + 1).isDisabled())
                if (scheme.getDie(x22, y22 + 1).getValue() == toPlace2.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y22 - 1 > -1) {
            if (!scheme.getDie(x22, y22 - 1).isDisabled())
                if (scheme.getDie(x22, y22 - 1).getColor() == toPlace2.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x22, y22 - 1).isDisabled())
                if (scheme.getDie(x22, y22 - 1).getValue() == toPlace2.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        return true;
    }

    public SchemeCard applyModifies(int x01, int y01, int x02, int y02, SchemeCard scheme, int x11, int y11, int x22, int y22) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        Die toPlace1 = scheme.getDie(x01,y01);
        Die toPlace2 = scheme.getDie(x02,y02);

        scheme.setDie(toPlace1, x11, y11);
        scheme.setDie(toPlace2, x22, y22);

        scheme.getDie(x01,y01).disableDie();
        scheme.getDie(x02,y02).disableDie();

        return scheme;

    }

}
