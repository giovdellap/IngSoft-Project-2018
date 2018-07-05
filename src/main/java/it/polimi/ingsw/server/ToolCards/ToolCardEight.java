package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEightNineTenEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

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

    public ToolCardEvent useTool(ToolCardEvent currentEvent) throws InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException {


        ToolCardEightNineTenEvent event = (ToolCardEightNineTenEvent)currentEvent;

        boolean check = (!turnManager.canUseSevenOrCantUseEight());
        if(check)
            check = checkingMethods.checkMove(modelInstance.getSchemebyIndex(player.getId()), modelInstance.getDraft().returnDie(((ToolCardEightNineTenEvent) currentEvent).getIndex()), ((ToolCardEightNineTenEvent) currentEvent).getX(), ((ToolCardEightNineTenEvent) currentEvent).getY());
        if(check)
        {
            turnManager.usedEight(player.getId());
            ToolCardEight toolCardEight = new ToolCardEight();
            toolCardEight.setDraft(modelInstance.getDraft());
            toolCardEight.setScheme(modelInstance.getSchemebyIndex(player.getId()));
            toolCardEight.applyModifies(((ToolCardEightNineTenEvent) currentEvent).getIndex(), ((ToolCardEightNineTenEvent) currentEvent).getX(), ((ToolCardEightNineTenEvent) currentEvent).getY());
            modelInstance.setDraft(toolCardEight.getDraft());
            modelInstance.setPlayerScheme(player.getId(), toolCardEight.getScheme());
            event.validate();
            return event;
        }


        event.resetValidation();
        return event;
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
