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

    public boolean checkToolCardOne(Die initialDie, int modify, SchemeCard schemeCard, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        // modify = 1 +   ,   modify = 2 -

        if (initialDie == null || schemeCard == null)
            throw new GenericInvalidArgumentException();

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (modify != 1 && modify != 2)
            throw new InvalidIntArgumentException();

        if (!schemeCard.getDie(x, y).isDisabled())
            throw new GenericInvalidArgumentException();

        if (modify == 1) {
            if (initialDie.getValue() == 6)
                return false;
        }

        if (modify == 2) {
            if (initialDie.getValue() == 1)
                return false;
        }

            return true;

    }


    public SchemeCard applyModifies(SchemeCard schemeCard, Die toPlace, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {
        schemeCard.setDie(toPlace, x, y);
        return schemeCard;
    }


}
