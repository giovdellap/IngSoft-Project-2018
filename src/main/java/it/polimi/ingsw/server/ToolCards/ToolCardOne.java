package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;


public class ToolCardOne extends ToolCard {

    public ToolCardOne() {
        setToolCardName("Grozing Pliers");
    }

    public boolean checkToolCardOne(int pos, int modify, SchemeCard scheme, DraftPool draft, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);

        // modify = 1 +   ,   modify = 2 -

        if (toPlace == null || scheme == null || draft.returnDie(pos).isDisabled())
            throw new GenericInvalidArgumentException();

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (modify != 1 && modify != 2)
            throw new InvalidIntArgumentException();

        if (!scheme.getDie(x, y).isDisabled()) {
            System.out.println("That position is already occupied");
            return false;
        }

        if (modify == 1) {
            if (toPlace.getValue() == 6)
                return false;
        }

        if (modify == 2) {
            if (toPlace.getValue() == 1)
                return false;
        }

            return true;

    }


    public SchemeCard applyModifies(int pos, int modify, SchemeCard scheme, DraftPool draft, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);

        // modify = 1 +   ,   modify = 2 -

        if(modify==1)
            toPlace.setValueTest(toPlace.getValue()+1);

        if(modify==2)
            toPlace.setValueTest(toPlace.getValue()-1);

        scheme.setDie(toPlace, x, y);
        draft.pickUpDie(pos);
        return scheme;
    }


}
