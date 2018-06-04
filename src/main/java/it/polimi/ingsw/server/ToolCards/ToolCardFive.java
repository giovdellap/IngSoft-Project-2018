package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.RoundDice;
import it.polimi.ingsw.server.ModelComponent.RoundTrack;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class ToolCardFive extends ToolCard {

    RoundTrack roundTrack;
    DraftPool draft;


    public ToolCardFive() throws InvalidIntArgumentException {
        setToolCardName("Lens Cutter");
    }

    public boolean checkToolCardFive(DraftPool draft, int posDraft, RoundTrack roundTrack, int turn, int pos) throws GenericInvalidArgumentException, InvalidIntArgumentException {

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

    public void setDraft(DraftPool d)
    {
        draft=d;
    }

    public void setRoundTrack(RoundTrack r)
    {
        roundTrack=r;
    }

    public DraftPool getDraft() {

        return draft;
    }

    public RoundTrack getTrack() {

        return roundTrack;
    }

    public void applyModifies(int posDraft, int turn, int pos) throws InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException {

        RoundDice temp = roundTrack.returnNTurnRoundDice(turn);
        Die toPlace2 = temp.getDie(pos);
        Die toPlace1 = draft.replaceDie(posDraft,toPlace2);
        temp.replaceDie(toPlace1,pos);
        roundTrack.setSpecificRoundDice(temp,turn);

    }


}

