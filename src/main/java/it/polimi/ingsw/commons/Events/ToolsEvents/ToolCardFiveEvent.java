package it.polimi.ingsw.commons.Events.ToolsEvents;

public class ToolCardFiveEvent extends ToolCardEvent {

    int index;
    int turn;
    int pos;

    public ToolCardFiveEvent(int id) {
        super("ToolCardFiveEvent", id);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setPos(int pos) {
        this.pos = pos;
    }

    public void setTurn(int turn) {
        this.turn = turn;
    }

    public int getIndex() {
        return index;
    }

    public int getPos() {
        return pos;
    }

    public int getTurn() {
        return turn;
    }


}
