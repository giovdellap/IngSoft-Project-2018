package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.DiceContainer;
import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class ToolCardEleven extends ToolCard {

    DraftPool draft;
    SchemeCard scheme;

    public ToolCardEleven() {
        setToolCardName("Flux Remover");
    }

    public void setDraft(DraftPool d) {
        draft = d;
    }

    public void setScheme(SchemeCard s) {
        scheme = s;
    }

    public boolean checkToolCardEleven(DraftPool draft, int pos, DiceContainer container, int newValue, SchemeCard scheme, int x, int y) throws InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);

        //
        //
        //
        //      DICE CONTAINER
        //
        //
        //

        toPlace.setValueTest(newValue);

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


    public void applyModifies(int pos, DiceContainer container, int newValue, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        Die toPlace = draft.returnDie(pos);

        //
        //
        //
        //      DICE CONTAINER
        //
        //
        //

        toPlace.setValueTest(newValue);
        scheme.setDie(toPlace,x,y);
        draft.pickUpDie(pos);

    }


    public DraftPool getDraft() {
        return draft;
    }

    public SchemeCard getScheme() {
        return scheme;
    }



}

