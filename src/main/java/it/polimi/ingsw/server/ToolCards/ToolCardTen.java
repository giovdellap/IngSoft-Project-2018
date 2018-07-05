package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEightNineTenEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class ToolCardTen extends ToolCard {

    DraftPool draft;
    SchemeCard scheme;

    /**
     * ToolCardTen Constructor
     */
    public ToolCardTen() {
        setToolCardName("Grinding Stone");
        setId(10);
    }

    public ToolCardEvent useTool(ToolCardEvent currentEvent) throws InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException {
        ToolCardEightNineTenEvent event = (ToolCardEightNineTenEvent)currentEvent;
        boolean check = (afterDraftingCheck(checkToolCardTen(modelInstance.getDraft(),  event.getIndex()), event.getX(),event.getY()));
        if(check)
        {
            setDraft(modelInstance.getDraft());
            setScheme(modelInstance.getSchemebyIndex(player.getId()));
            applyModifies(event.getIndex(), event.getX(), event.getY());
            modelInstance.setDraft(getDraft());
            modelInstance.setPlayerScheme(player.getId(),getScheme());
            event.validate();
            return event;
        }
        event.resetValidation();
        return event;
    }

    /**
     * sets draft pool to tool card
     * @param d
     */
    public void setDraft(DraftPool d) {
        draft = d;
    }

    /**
     * sets scheme card to tool card
     * @param s
     */
    public void setScheme(SchemeCard s) {
        scheme = s;
    }

    /**
     * checks if the tool card can be used or not
     * @param draft draft poolfrom which to take die
     * @param pos draft pool's index
     * @return Die with it's value modified
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public Die checkToolCardTen(DraftPool draft, int pos) throws InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);

        boolean changed=false;

        if(toPlace.getValue()==1 && !changed)
        {
            toPlace.setValue(6);
            changed=true;
        }

        if(toPlace.getValue()==2 && !changed) {
            toPlace.setValue(5);
            changed=true;
        }

        if(toPlace.getValue()==3 && !changed) {
            toPlace.setValue(4);
            changed=true;
        }

        if(toPlace.getValue()==4 && !changed) {
            toPlace.setValue(3);
            changed=true;
        }

        if(toPlace.getValue()==5 && !changed) {
            toPlace.setValue(2);
            changed=true;
        }

        if(toPlace.getValue()==6 && !changed) {
            toPlace.setValue(1);
            changed=true;
        }

        return toPlace;

    }


    public void applyModifies(int pos, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        Die toPlace = draft.returnDie(pos);

        boolean changed=false;

        if(toPlace.getValue()==1 && !changed)
        {
            toPlace.setValue(6);
            changed=true;
        }

        if(toPlace.getValue()==2 && !changed) {
            toPlace.setValue(5);
            changed=true;
        }

        if(toPlace.getValue()==3 && !changed) {
            toPlace.setValue(4);
            changed=true;
        }

        if(toPlace.getValue()==4 && !changed) {
            toPlace.setValue(3);
            changed=true;
        }

        if(toPlace.getValue()==5 && !changed) {
            toPlace.setValue(2);
            changed=true;
        }

        if(toPlace.getValue()==6 && !changed) {
            toPlace.setValue(1);
            changed=true;
        }

        scheme.setDie(toPlace,x,y);

        draft.pickUpDie(pos);

    }

    /**
     * gets draft pool
     * @return draft pool
     */

    public DraftPool getDraft() {
        return draft;
    }

    /**
     * gets scheme card
     * @return scheme card
     */

    public SchemeCard getScheme() {
        return scheme;
    }



}
