package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;


public class ToolCardSeven extends ToolCard {

    public ToolCardSeven() {
        setToolCardName("Glazing Hammer");
    }

    public boolean checkToolCardSeven() {

        return true;

    }

    public DraftPool applyModifies(DraftPool draft) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        for(int i=0;draft.returnDie(i).isDisabled();i++)
            draft.returnDie(i).throwDie();

        return draft;

    }


}
