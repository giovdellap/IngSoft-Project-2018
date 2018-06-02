package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class ToolCardSix extends ToolCard {

    public ToolCardSix() {
        setToolCardName("Flux Brush");
    }


    public boolean checkToolCardSixSchemeCard(Die toPlace, SchemeCard scheme, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {


        if (toPlace == null || scheme == null)
            throw new GenericInvalidArgumentException();

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (!scheme.getDie(x, y).isDisabled()) {
            System.out.println("That position is already occupied");
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

    public SchemeCard ApplyModifiesToScheme(Die toPlace, SchemeCard scheme, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {
        scheme.setDie(toPlace,x,y);
        return scheme;
    }

    public DraftPool ApplyModifiesToDraft(Die toPlace, DraftPool draft, int pos) throws GenericInvalidArgumentException, InvalidIntArgumentException {
        draft.replaceDie(pos,toPlace);
        return draft;
    }



}
