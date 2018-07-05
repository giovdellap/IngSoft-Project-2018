package it.polimi.ingsw.commons.Events.ToolsEvents;

public class ToolCardFiveEvent extends ToolCardEvent {

    private int index;
    private int turn;
    private int pos;
    private int x;
    private int y;

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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }
}
