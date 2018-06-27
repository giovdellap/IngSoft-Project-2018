package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.util.ArrayList;


public class ToolCardSeven extends ToolCard {
    /**
     * ToolCardSeven Constructor
     */
    public ToolCardSeven() {
        setToolCardName("Glazing Hammer");
        setId(7);
    }

    /**
     * apply modifies to draft pool
     * @param draft draft pool on which to apply the tool card
     * @return modified draft pool
     * @throws InvalidIntArgumentException
     */

    public DraftPool applyModifies(DraftPool draft) throws InvalidIntArgumentException {

        ArrayList<Die> tempDice = new ArrayList<Die>();
        for(int i=0;i<draft.getDiceNum();i++)
        {
            tempDice.add(draft.returnDie(i));
            tempDice.get(i).throwDie();
        }
        draft.replaceDraft(tempDice);

        return draft;

    }


}
