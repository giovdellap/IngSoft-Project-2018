package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;


public class ToolCardThree extends ToolCard {

    public ToolCardThree() {
        setToolCardName("Copper Foil Burnisher");
        setId(3);
    }

    public boolean checkToolCardThree(int x0, int y0, SchemeCard scheme, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {


        Die dieToPlace = scheme.getDie(x0,y0);

        if (dieToPlace == null || scheme == null)
            throw new GenericInvalidArgumentException();

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (scheme.getDie(x0,y0).isDisabled()) {
            System.out.println("Die to position not found");
            return false;
        }

        if (!scheme.getDie(x, y).isDisabled()) {
            System.out.println("That position is already occupied");
            return false;
        }

        boolean flag = false;

        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if ((x + i < 4) && (x + i > -1) && (y + j < 5) && (y + j > -1))
                    if(!scheme.getDie(x + i, y + j).isDisabled())
                        flag = true;


        if (!flag) {
            System.out.println("You must position your die orthogonally or diagonally adjacent to another die");
            return false;
        }

        if (scheme.getCell(scheme.getfb(), x, y) > 0 && scheme.getCell(scheme.getfb(), x, y) < 6 && dieToPlace.getColor() != scheme.getCell(scheme.getfb(), x, y)) {
            System.out.println("You must position your die on the same color cell of your scheme");
            return false;
        }

        if (x + 1 < 4) {
            if (!scheme.getDie(x + 1, y).isDisabled())
                if (scheme.getDie(x + 1, y).getColor() == dieToPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
        }

        if (x - 1 > -1) {
            if (!scheme.getDie(x - 1, y).isDisabled())
                if (scheme.getDie(x - 1, y).getColor() == dieToPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
        }

        if (y + 1 < 5) {
            if (!scheme.getDie(x, y + 1).isDisabled())
                if (scheme.getDie(x, y + 1).getColor() == dieToPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
        }

        if (y - 1 > -1) {
            if (!scheme.getDie(x, y - 1).isDisabled())
                if (scheme.getDie(x, y - 1).getColor() == dieToPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
        }

        return true;
    }


    public SchemeCard applyModifies(int x0, int y0, SchemeCard scheme, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {
        Die toPlace = scheme.getDie(x0,y0);
        scheme.setDie(toPlace, x, y);
        scheme.getDie(x0,y0).disableDie();
        return scheme;
    }

}
