package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardSixEvent;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.io.IOException;

public class ToolCardSix extends ToolCard {
    /**
     * ToolCardSix Constructor
     */
    public ToolCardSix() {
        setToolCardName("Flux Brush");
        setId(6);
    }

    public ToolCardEvent useTool(ToolCardEvent currentEvent) throws InvalidIntArgumentException, GenericInvalidArgumentException, IOException {

        ToolCardSixEvent event = (ToolCardSixEvent)currentEvent;

        modelInstance.getDraft().returnDie(event.getIndex()).throwDie();
        event.setNewValue(modelInstance.getDraft().returnDie(event.getIndex()).getValue());
        event.validate();
        event.setApplyOne(true);
        player.sendEvent(event);
        player.getEvent();
        event = (ToolCardSixEvent)player.getLastEventReceive();

        Boolean check = afterDraftingCheck(modelInstance.getDraft().returnDie(event.getIndex()), event.getX(), event.getY());
                event.setApplyTwo(check);
                if (check) {
                    setDraft(modelInstance.getDraft());
                    setScheme(modelInstance.getSchemebyIndex(player.getId()));
                    applyModifiesToScheme(event.getIndex(), event.getX(), event.getY());
                    modelInstance.setDraft(getDraft());
                    modelInstance.setPlayerScheme(player.getId(), getScheme());
                } else {
                    setDraft(modelInstance.getDraft());
                    setScheme(modelInstance.getSchemebyIndex(player.getId()));
                    applyModifiesToDraft(event.getIndex());
                    modelInstance.setDraft(getDraft());
                    modelInstance.setPlayerScheme(player.getId(), getScheme());
                }

                event.validate();
                return event;

        }

    DraftPool draft;
    SchemeCard scheme;

    /**
     * sets draft pool to tool card
     * @param draft
     */
    public void setDraft(DraftPool draft) {
        this.draft = draft;
    }

    /**
     * sets scheme card to tool card
     * @param scheme
     */
    public void setScheme(SchemeCard scheme) {
        this.scheme = scheme;
    }

    /**
     * apply modifies to scheme
     * @param pos draft pool's index
     * @param x row where to place die
     * @param y column where to place die
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */

    public void applyModifiesToScheme(int pos, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {
        Die toPlace = draft.returnDie(pos);
        scheme.setDie(toPlace,x,y);
        draft.pickUpDie(pos);
    }

    /**
     * apply modifies to draft
     * @param pos draft pool's index
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public void applyModifiesToDraft(int pos) throws GenericInvalidArgumentException, InvalidIntArgumentException {
        Die toPlace = draft.returnDie(pos);
        draft.replaceDie(pos,toPlace);
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

