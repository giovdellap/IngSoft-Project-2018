package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class ToolCardNine extends ToolCard {

    public ToolCardNine() {
        setToolCardName("Cork-backed Straightedge");
    }

    public boolean checkToolCardNine(DraftPool draft, int pos, SchemeCard scheme, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);


        if (toPlace == null || scheme == null || draft.returnDie(pos).isDisabled())
            throw new GenericInvalidArgumentException();

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (!scheme.getDie(x, y).isDisabled()) {
            System.out.println("That position is already occupied");
            return false;
        }

        if (scheme.getCell(scheme.getfb(), x, y) > 0 && scheme.getCell(scheme.getfb(), x, y) < 6 && toPlace.getColor() != scheme.getCell(scheme.getfb(), x, y)) {
            System.out.println("You must position your die on the same color cell of your scheme");
            return false;
        }


        if (scheme.getCell(scheme.getfb(), x, y) > 5 && scheme.getCell(scheme.getfb(), x, y) < 12 && toPlace.getValue() != (scheme.getCell(scheme.getfb(), x, y) - 5)) {
            System.out.println("You must position your die on the same number cell of your scheme");
            return false;
        }


        if (x + 1 < 4) {
            if (!scheme.getDie(x + 1, y).isDisabled())
                if (scheme.getDie(x + 1, y).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x + 1, y).isDisabled())
                if (scheme.getDie(x + 1, y).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (x - 1 > -1) {
            if (!scheme.getDie(x - 1, y).isDisabled())
                if (scheme.getDie(x - 1, y).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x - 1, y).isDisabled())
                if (scheme.getDie(x - 1, y).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y + 1 < 5) {
            if (!scheme.getDie(x, y + 1).isDisabled())
                if (scheme.getDie(x, y + 1).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x, y + 1).isDisabled())
                if (scheme.getDie(x, y + 1).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y - 1 > -1) {
            if (!scheme.getDie(x, y - 1).isDisabled())
                if (scheme.getDie(x, y - 1).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x, y - 1).isDisabled())
                if (scheme.getDie(x, y - 1).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        return true;

    }

    public SchemeCard applyModifies(DraftPool draft, int pos, SchemeCard scheme, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);

        scheme.setDie(toPlace,x,y);
        draft.pickUpDie(pos);
        return scheme;

    }


}
