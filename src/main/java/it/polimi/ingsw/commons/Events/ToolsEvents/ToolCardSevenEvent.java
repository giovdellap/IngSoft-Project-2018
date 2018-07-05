package it.polimi.ingsw.commons.Events.ToolsEvents;
import it.polimi.ingsw.commons.Die;

import java.util.ArrayList;

public class ToolCardSevenEvent extends ToolCardEvent {

    private ArrayList<Die> dice;

    public ToolCardSevenEvent(int id) {
        super("ToolCardSevenEvent", id);
    }

    public void setDice(ArrayList<Die> dice) {
        this.dice = dice;
    }

    public ArrayList<Die> getDice() {
        return dice;
    }

}
