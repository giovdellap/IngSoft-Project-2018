package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;


public class ToolCardOne extends ToolCard {

    DraftPool draft;
    SchemeCard scheme;

    /**
     * ToolCardOne Constructor
     */
    public ToolCardOne() {
        setToolCardName("Grozing Pliers");
        setId(1);
    }

    /**
     * sets draft
     * @param d draft
     */
    public void setDraft(DraftPool d) {
        draft = d;
    }

    /**
     * sets scheme
     * @param s scheme
     */
    public void setScheme(SchemeCard s) {
        scheme = s;
    }

    /**
     * checks if the tool card can be used or not
     * @param pos draft pool's index
     * @param modify 1 if the user wants to increase the value of the die, 2 if the user wants to decrease the value of the die
     * @param scheme scheme to check
     * @param draft draft from which to pick the die from
     * @param x row
     * @param y column
     * @return true if the move can be executed, false if it can't
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public boolean checkToolCardOne(int pos, int modify, SchemeCard scheme, DraftPool draft, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);

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

    /**
     * applies the tool card effects
     * @param pos draft pool's index
     * @param modify 1 if the user wants to increase the value of the die, 2 if the user wants to decrease the value of the die
     * @param x row where to place the die
     * @param y column where to place the die
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public void applyModifies(int pos, int modify, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);

        if(modify==1)
            toPlace.setValue(toPlace.getValue()+1);

        if(modify==2)
            toPlace.setValue(toPlace.getValue()-1);

        scheme.setDie(toPlace, x, y);
        draft.pickUpDie(pos);

    }

    /**
     * gets scheme card
     * @return scheme
     */
    public SchemeCard getScheme() {
        return scheme;
    }

    /**
     * gets draft pool
     * @return draft
     */
    public DraftPool getDraft() {
        return draft;
    }


}
