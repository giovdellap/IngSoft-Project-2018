package it.polimi.ingsw.commons.Events.ToolsEvents;

public class ToolCardTwoThreeEvent extends ToolCardEvent {

    int x0;
    int y0;
    int x1;
    int y1;

    public ToolCardTwoThreeEvent(int id) {
        super("ToolCardTwoThreeEvent", id);
    }

    public void setX0(int x0) {
        this.x0 = x0;
    }

    public void setY0(int y0) {
        this.y0 = y0;
    }

    public void setX1(int x1) {
        this.x1 = x1;
    }

    public void setY1(int y1) {
        this.y1 = y1;
    }

    public int getX0() {
        return x0;
    }

    public int getY0() {
        return y0;
    }

    public int getX1() {
        return x1;
    }

    public int getY1() {
        return y1;
    }


}
