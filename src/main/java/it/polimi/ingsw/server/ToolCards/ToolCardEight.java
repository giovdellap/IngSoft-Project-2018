package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class ToolCardEight extends ToolCard {

    private SchemeCard scheme;
    private DraftPool draft;

    /**
     * ToolCardEight Constructor
     */
    public ToolCardEight() {
        setToolCardName("Running Pliers");
        setId(8);
    }

    /**
     * applies the tool card effects
     * @param index draft pool's index
     * @param x row where to place the die
     * @param y column where to place the die
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */

    public void applyModifies(int index, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException {
        scheme.setDie(draft.returnDie(index), x, y);
        draft.pickUpDie(index);
    }

    /**
     * sets draft
     * @param draft draft
     */
    public void setDraft(DraftPool draft) {
        this.draft = draft;
    }

    /**
     * sets scheme card
     * @param scheme scheme
     */
    public void setScheme(SchemeCard scheme)
    {
        this.scheme = scheme;
    }

    /**
     * gets draft pool
     * @return draft
     */
    public DraftPool getDraft() {
        return draft;
    }

    /**
     * gets scheme card
     * @return scheme
     */
    public SchemeCard getScheme() {
        return scheme;
    }
}
