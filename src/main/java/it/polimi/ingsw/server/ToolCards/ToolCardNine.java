package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEightNineTenEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class ToolCardNine extends ToolCard {

    private DraftPool draft;
    SchemeCard scheme;

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
     * ToolCardNine Constructor
     */
    public ToolCardNine() {
        setToolCardName("Cork-backed Straightedge");
        setId(9);
    }

    public ToolCardEvent useTool(ToolCardEvent currentEvent) throws InvalidIntArgumentException, GenericInvalidArgumentException {
        ToolCardEightNineTenEvent event = (ToolCardEightNineTenEvent)currentEvent;

        boolean check = checkToolCardNine(modelInstance.getDraft(),event.getIndex(),modelInstance.getSchemebyIndex(player.getId()), event.getX(),event.getY());

        if(check)
            if(!player.getIPlayedFirstMove())
                check = checkingMethods.checkFirstMove(modelInstance.getSchemebyIndex(player.getId()), modelInstance.getDraft().returnDie(event.getIndex()), event.getX(), event.getY());

        if(check)
        {
            setDraft(modelInstance.getDraft());
            setScheme(modelInstance.getSchemebyIndex(player.getId()));
            applyModifies( event.getIndex(),event.getX(),event.getY());
            modelInstance.setDraft(getDraft());
            modelInstance.setPlayerScheme(player.getId(),getScheme());
            event.validate();
            return event;
        }
        event.resetValidation();
        return event;
    }

    /**
     * checks if the tool card can be used or not
     * @param draft draft pool
     * @param pos draft pool's index
     * @param scheme scheme card
     * @param x row where to place the die
     * @param y column where to place the die
     * @return true if the move can be executed, false if it can't
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public boolean checkToolCardNine(DraftPool draft, int pos, SchemeCard scheme, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);


        if (toPlace == null || scheme == null || draft.returnDie(pos).isDisabled())
            throw new GenericInvalidArgumentException();

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (!scheme.getDie(x, y).isDisabled()) {
            System.out.println("That position is already occupied");
            return false;
        }

        if (scheme.getCell(scheme.getfb(), x, y) > 0 && scheme.getCell(scheme.getfb(), x, y) < 6 && toPlace.getColor() != scheme.getCell(scheme.getfb(), x, y)) {
            System.out.println("You must position your die on the same color cell of your scheme");
            return false;
        }


        if (scheme.getCell(scheme.getfb(), x, y) > 5 && scheme.getCell(scheme.getfb(), x, y) < 12 && toPlace.getValue() != (scheme.getCell(scheme.getfb(), x, y) - 5)) {
            System.out.println("You must position your die on the same number cell of your scheme");
            return false;
        }


        if (x + 1 < 4) {
            if (!scheme.getDie(x + 1, y).isDisabled())
                if (scheme.getDie(x + 1, y).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x + 1, y).isDisabled())
                if (scheme.getDie(x + 1, y).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (x - 1 > -1) {
            if (!scheme.getDie(x - 1, y).isDisabled())
                if (scheme.getDie(x - 1, y).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x - 1, y).isDisabled())
                if (scheme.getDie(x - 1, y).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y + 1 < 5) {
            if (!scheme.getDie(x, y + 1).isDisabled())
                if (scheme.getDie(x, y + 1).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x, y + 1).isDisabled())
                if (scheme.getDie(x, y + 1).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y - 1 > -1) {
            if (!scheme.getDie(x, y - 1).isDisabled())
                if (scheme.getDie(x, y - 1).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x, y - 1).isDisabled())
                if (scheme.getDie(x, y - 1).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        return true;

    }

    /**
     * applies the tool card effects to the scheme card
     * @param pos draft pool's index
     * @param x row where to place the die
     * @param y column where to place the die
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public void applyModifies(int pos, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);
        scheme.setDie(toPlace,x,y);

        draft.pickUpDie(pos);

    }

    public DraftPool getDraft() {
        return draft;
    }

    public SchemeCard getScheme() {
        return scheme;
    }


}
