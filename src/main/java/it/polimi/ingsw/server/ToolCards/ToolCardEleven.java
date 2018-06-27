package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.*;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class ToolCardEleven extends ToolCard {

    DraftPool draft;
    SchemeCard scheme;

    /**
     * ToolCardEleven Constructor
     */
    public ToolCardEleven() {
        setToolCardName("Flux Remover");
        setId(11);
    }

    /**
     * sets draft pool
     * @param d draft
     */
    public void setDraft(DraftPool d) {
        draft = d;
    }

    /**
     * sets scheme card
     * @param s scheme
     */
    public void setScheme(SchemeCard s) {
        scheme = s;
    }

    /**
     * applies the tool card effects to the scheme card
     * @param tempDie die to place
     * @param x row where to place the die
     * @param y column where to place the die
     * @param index draft pool's index
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */


    public void applyModifiesTwo(Die tempDie, int x, int y, int index) throws InvalidIntArgumentException, GenericInvalidArgumentException {
        scheme.setDie(tempDie, x, y);
        //draft.pickUpDie(index);
    }

    /**
     * applies the tool card effects to the draft pool
     * @param tempDie die to place
     * @param index draft pool's index
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */
    public void applyModifiesOne(Die tempDie, int index) throws InvalidIntArgumentException, GenericInvalidArgumentException {
        //draft.replaceDie(index, tempDie);
        ArrayList<Die> temp = draft.getDraft();
        temp.add(index, tempDie);
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

