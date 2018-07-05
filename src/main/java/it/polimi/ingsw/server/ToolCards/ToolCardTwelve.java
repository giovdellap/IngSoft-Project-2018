package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardTwelveEvent;
import it.polimi.ingsw.server.CheckingMethods;
import it.polimi.ingsw.server.ModelComponent.RoundDice;
import it.polimi.ingsw.server.ModelComponent.RoundTrack;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class ToolCardTwelve extends ToolCard {

    private SchemeCard tempScheme;

    /**
     * ToolCard Twelve Constructor
     */
    public ToolCardTwelve() {
        setToolCardName("Tap Wheel");
        setId(12);
    }

    public ToolCardEvent useTool(ToolCardEvent currentEvent) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        ToolCardTwelveEvent event = (ToolCardTwelveEvent)currentEvent;
        if(event.isOnlyOne())
        {
            setTempScheme(modelInstance.getSchemebyIndex(player.getId()));
            if(checkToolCardTwelve1Die(modelInstance.getTrack(), event.getTurn(), event.getPos(), modelInstance.getSchemebyIndex(player.getId()), event.getX01(), event.getY01(), event.getX11(), event.getY11()))
            {
                modelInstance.setPlayerScheme(player.getId(), applyModifies(event.getX01(), event.getY01(), event.getX11(),event.getY11()));
                event.validate();
                return event;
            }
        }
        else
        {
            setTempScheme(modelInstance.getSchemebyIndex(player.getId()));
            if(checkToolCardTwelve2Dice(modelInstance.getTrack(), event.getTurn(), event.getPos(), event.getX01(), event.getY01(), event.getX02(), event.getY02(), modelInstance.getSchemebyIndex(player.getId()), event.getX11(),event.getY11(), event.getX22(), event.getY22()));
            {
                modelInstance.setPlayerScheme(player.getId(),applyModifies(event.getX01(), event.getY01(), event.getX02(), event.getY02(), event.getX11(), event.getY11(), event.getX22(), event.getY22()));
                event.validate();
                return event;
            }
        }
        event.resetValidation();
        return event;
    }

    /**
     * set scheme to tool card
     * @param sc scheme to set
     */
    public void setTempScheme(SchemeCard sc) {
        tempScheme = sc;
    }

    /**
     * checks if the tool card can be used for 2 dice or not
     * @param track round track from which to take the color
     * @param turn round track's turn
     * @param pos round dice position
     * @param x01 row of the first die to shift
     * @param y01 column of the first die to shift
     * @param x02 row of the second die to shift
     * @param y02 column of the second die to shift
     * @param scheme scheme on which to check the move
     * @param x11 row where to place the first die
     * @param y11 column where to place the first die
     * @param x22 row where to place the second die
     * @param y22 column where to place the second die
     * @return true if the dice can be placed, false if not
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */

    public boolean checkToolCardTwelve2Dice(RoundTrack track, int turn, int pos, int x01, int y01, int x02, int y02, SchemeCard scheme, int x11, int y11, int x22, int y22) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        RoundDice temp = track.returnNTurnRoundDice(turn);
        int color = temp.getDie(pos).getColor();

        CheckingMethods checkingMethods = new CheckingMethods();

        boolean flag=true;


        if (color!=scheme.getDie(x01,y01).getColor() || color!=scheme.getDie(x02,y02).getColor()) {
            System.out.println("The colors are not equal to the chosen die");
            flag = false;
        }

        if(scheme.getDie(x01,y01).getColor()!=scheme.getDie(x02,y02).getColor()) {
            System.out.println("The colors of the two dice are not the same");
            flag = false;
        }

        if (scheme.getDie(x01,y01).isDisabled() || scheme.getDie(x02,y02).isDisabled()) {
            System.out.println("Dice to position not found");
            flag = false;
        }

        if (!scheme.getDie(x11, y11).isDisabled() || !scheme.getDie(x22, y22).isDisabled()) {
            System.out.println("Positions are already occupied");
            flag = false;
        }

        if(flag) {
            Die toPlace1 = new Die(scheme.getDie(x01, y01).getColor());
            toPlace1.setValue(scheme.getDie(x01, y01).getValue());
            Die toPlace2 = new Die(scheme.getDie(x02, y02).getColor());
            toPlace2.setValue(scheme.getDie(x02, y02).getValue());

            tempScheme.getDie(x01, y01).disableDie();
            tempScheme.getDie(x02, y02).disableDie();

            boolean check = false;

            if (checkingMethods.checkMove(tempScheme, toPlace1, x11, y11))
                if (checkingMethods.checkMove(tempScheme, toPlace2, x22, y22))
                    check = true;

            tempScheme.setDie(toPlace1,x01 ,y01 );
            tempScheme.setDie(toPlace2, x02, y02);

            if(check)
                return true;

        }
            return false;

    }

    /**
     * checks if the tool card can be used for 1 die or not
     * @param track round track from which to take the color
     * @param turn round track's turn
     * @param pos round dice position
     * @param scheme scheme on which to check the move
     * @param x0 row of die to shift
     * @param y0 column of die to shift
     * @param scheme scheme on which to check the move
     * @param x1 row where to place the die
     * @param y1 column where to place the die
     * @return true if the die can be placed, false if it can't
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */

    public boolean checkToolCardTwelve1Die(RoundTrack track, int turn, int pos, SchemeCard scheme, int x0, int y0, int x1, int y1) throws InvalidIntArgumentException, GenericInvalidArgumentException {
        int color = 0;
        RoundDice temp = track.returnNTurnRoundDice(turn);
        color = temp.getDie(pos).getColor();

        boolean flag=true;


        if (color!=scheme.getDie(x0,y0).getColor()) {
            System.out.println("The colors are not equal to the chosen die");
            flag = false;
        }


        if (scheme.getDie(x0,y0).isDisabled()) {
            System.out.println("Dice to position not found");
            flag = false;
        }

        if (!scheme.getDie(x1, y1).isDisabled()) {
            System.out.println("Positions are already occupied");
            flag = false;
        }

        if(flag) {

            Die tempDie = new Die(scheme.getDie(x0, y0).getColor());
            tempDie.setValue(scheme.getDie(x0, y0).getValue());
            tempScheme.getDie(x0, y0).disableDie();

            CheckingMethods checkingMethods = new CheckingMethods();
            boolean check = checkingMethods.checkMove(tempScheme, tempDie, x1, y1);

            tempScheme.setDie(tempDie, x0, y0);

            return check;

        }
        return false;
    }

    /**
     * applies modifies for 2 dice to scheme card
     * @param x01 row of the first die to shift
     * @param y01 column of the first die to shift
     * @param x02 row of the second die to shift
     * @param y02 column of the second die to shift
     * @param x11 row where to place the first die
     * @param y11 column where to place the first die
     * @param x22 row where to place the second die
     * @param y22 column where to place the second die
     * @return modified scheme card
     * @throws InvalidIntArgumentException
     */

    public SchemeCard applyModifies(int x01, int y01, int x02, int y02, int x11, int y11, int x22, int y22) throws InvalidIntArgumentException {

        tempScheme.shiftDie(x01, y01, x11, y11);
        tempScheme.shiftDie(x02, y02, x22, y22);

        return tempScheme;
    }

    /**
     * applies modifies for 1 die
     * @param x0 row of die to shift
     * @param y0 column of die to shift
     * @param x1 row where to place the die
     * @param y1 column where to place the die
     * @return modified scheme card
     * @throws InvalidIntArgumentException
     */

    public SchemeCard applyModifies(int x0, int y0, int x1, int y1) throws InvalidIntArgumentException {

        tempScheme.shiftDie(x0,y0,x1, y1);
        return tempScheme;
    }

}
