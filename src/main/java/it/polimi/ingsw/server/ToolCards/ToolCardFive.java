package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardFiveEvent;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.RoundDice;
import it.polimi.ingsw.server.ModelComponent.RoundTrack;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class ToolCardFive extends ToolCard {

    private RoundTrack roundTrack;
    private DraftPool draft;
    SchemeCard scheme;

    /**
     * ToolCardFive Constructor
     */
    public ToolCardFive() {
        setToolCardName("Lens Cutter");
        setId(5);
    }

    public ToolCardEvent useTool(ToolCardEvent currentEvent) throws InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException {

        ToolCardFiveEvent event = ((ToolCardFiveEvent)currentEvent);

        boolean check = checkToolCardFive(modelInstance.getDraft(),event.getIndex(),modelInstance.getTrack(),event.getTurn(),event.getPos());
        if(check)
            check=afterDraftingCheck(modelInstance.getTrack().returnNTurnRoundDice(event.getTurn()).getDie(event.getPos()), event.getX(),event.getY());
        if(check)
        {
            setRoundTrack(modelInstance.getTrack());
            setDraft(modelInstance.getDraft());
            setScheme(modelInstance.getSchemebyIndex(player.getId()));
            applyModifies(event.getIndex(),event.getTurn(),event.getPos(),event.getX(),event.getY());
            modelInstance.setDraft(getDraft());
            modelInstance.setTrack(getTrack());
            modelInstance.setPlayerScheme(player.getId(),getScheme());
            event.validate();
            return event;
        }

        event.resetValidation();
        return event;
    }

    /**
     * checks if the tool card can be used or not
     * @param draft draft pool from which to pick the die from
     * @param posDraft draft pool's index
     * @param roundTrack round track
     * @param turn round track's round
     * @param pos round dice position
     * @return true if it can be used, false if it can't
     * @throws InvalidIntArgumentException
     */
    public boolean checkToolCardFive(DraftPool draft, int posDraft, RoundTrack roundTrack, int turn, int pos) throws InvalidIntArgumentException {

        if (draft.returnDie(posDraft).isDisabled()) {
            System.out.println("Die in Draft Pool not found");
            return false;
        }

        if(roundTrack.returnNTurnRoundDice(turn).getDie(pos).isDisabled()) {
            System.out.println("Die in RoundTrack not found");
            return false;
        }

        return true;
    }

    /**
     * sets draft pool
     * @param d draft
     */
    public void setDraft(DraftPool d)
    {
        draft=d;
    }

    /**
     * sets round track
     * @param r track
     */
    public void setRoundTrack(RoundTrack r)
    {
        roundTrack=r;
    }

    /**
     * sets scheme card
     * @param s scheme
     */
    public void setScheme(SchemeCard s) {
        scheme = s;
    }

    /**
     * gets draft pool
     * @return draft
     */
    public DraftPool getDraft() {

        return draft;
    }

    /**
     * gets round track
     * @return track
     */
    public RoundTrack getTrack() {

        return roundTrack;
    }

    /**
     * gets scheme card
     * @return scheme
     */
    public SchemeCard getScheme() {

        return scheme;
    }

    /**
     * applies the tool card's effects
     * @param posDraft draft pool's index
     * @param turn draft pool's round
     * @param pos round dice's index
     * @param x row where to place the die
     * @param y column where to place the die
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws FullDataStructureException
     */
    public void applyModifies(int posDraft, int turn, int pos, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        RoundDice temp = roundTrack.returnNTurnRoundDice(turn);
        Die toPlace2 = temp.getDie(pos);
        Die toPlace1 = draft.replaceDie(posDraft,toPlace2);
        temp.replaceDie(toPlace1,pos);
        roundTrack.setSpecificRoundDice(temp,turn);
        scheme.setDie(toPlace2, x,y );
        draft.pickUpDie(posDraft);

    }


}

