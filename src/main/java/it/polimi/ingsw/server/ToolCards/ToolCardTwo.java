package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardTwoThreeEvent;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class ToolCardTwo extends ToolCard {

    SchemeCard tempScheme;

    /**
     * ToolCardTwo Constructor
     */
    public ToolCardTwo() {
        setToolCardName("Eglomise Brush");
        setId(2);
    }

    public ToolCardEvent useTool(ToolCardEvent currentEvent) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        ToolCardTwoThreeEvent event = ((ToolCardTwoThreeEvent)currentEvent);

            setTempScheme(modelInstance.getSchemebyIndex(player.getId()));
            boolean check = checkToolCardTwo(event.getX0(), event.getY0(), modelInstance.getSchemebyIndex(player.getId()), event.getX1(), event.getY1());
            if (check) {
                modelInstance.setPlayerScheme(player.getId(), applyModifies(event.getX0(), event.getY0(), event.getX1(), event.getY1()));
                event.validate();
                return event;
            }

            event.resetValidation();
            return event;

    }

    /**
     * set scheme card to tool card
     * @param tempScheme
     */
    public void setTempScheme(SchemeCard tempScheme) {
        this.tempScheme = tempScheme;
    }

    /**
     * checks if the tool card can be used
     * @param x0 row of die to shift
     * @param y0 column of die to shift
     * @param scheme scheme on which to check the move
     * @param x row where to place the die
     * @param y column where to place the die
     * @return true if the die can be placed, false if not
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public boolean checkToolCardTwo(int x0, int y0, SchemeCard scheme, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die dieToPlace = new Die(scheme.getDie(x0, y0).getColor());
        dieToPlace.setValue(scheme.getDie(x0, y0).getValue());

        if (dieToPlace == null || scheme == null)
            throw new GenericInvalidArgumentException();

        if (x < 0 || x > 3 || y < 0 || y > 4 || x0 < 0 || x0 > 3 || y0 < 0 || y0 > 4)
            throw new InvalidIntArgumentException();

        if (tempScheme.getDie(x0,y0).isDisabled()) {
            System.out.println("Die to position not found");
            return false;
        }

        if (!scheme.getDie(x, y).isDisabled()) {
            System.out.println("That position is already occupied");
            return false;
        }

        tempScheme.getDie(x0, y0).disableDie();

        boolean flag = false;

        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if ((x + i < 4) && (x + i > -1) && (y + j < 5) && (y + j > -1))
                    if(!tempScheme.getDie(x + i, y + j).isDisabled())
                        flag = true;


        if (!flag) {
            System.out.println("You must position your die orthogonally or diagonally adjacent to another die");
            return false;
        }


        if (tempScheme.getCell(tempScheme.getfb(), x, y) > 5 && tempScheme.getCell(tempScheme.getfb(), x, y) < 12 && dieToPlace.getValue() != (tempScheme.getCell(tempScheme.getfb(), x, y) - 5)) {
            System.out.println("You must position your die on the same number cell of your scheme");
            return false;
        }

        if (x + 1 < 4) {
            if (!tempScheme.getDie(x + 1, y).isDisabled())
                if (tempScheme.getDie(x + 1, y).getValue() == dieToPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (x - 1 > -1) {
            if (!tempScheme.getDie(x - 1, y).isDisabled())
                if (tempScheme.getDie(x - 1, y).getValue() == dieToPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y + 1 < 5) {
            if (!tempScheme.getDie(x, y + 1).isDisabled())
                if (tempScheme.getDie(x, y + 1).getValue() == dieToPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y - 1 > -1) {
            if (!tempScheme.getDie(x, y - 1).isDisabled())
                if (tempScheme.getDie(x, y - 1).getValue() == dieToPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        tempScheme.setDie(dieToPlace , x0,y0);
        return true;

    }

    /**
     * Applies the tool card effects
     * @param x0 row of die to shift
     * @param y0 column of die to shift
     * @param x row where to place the die
     * @param y column where to place the die
     * @return the modified scheme card
     * @throws InvalidIntArgumentException
     */
    public SchemeCard applyModifies(int x0, int y0, int x, int y) throws InvalidIntArgumentException {
        tempScheme.shiftDie(x0, y0, x, y);
        return tempScheme;
    }

}
