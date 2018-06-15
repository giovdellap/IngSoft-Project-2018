package it.polimi.ingsw.commons.Events.ToolsEvents;

public class ToolCardSixEvent extends ToolCardEvent {

    int index;
    int x;
    int y;
    boolean ApplyOne;
    boolean ApplyTwo;
    private int newValue;

    public ToolCardSixEvent(int id) {
        super("ToolCardSixEvent", id);
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setApplyOne(boolean applyOne) {
        ApplyOne = applyOne;
    }

    public void setApplyTwo(boolean applyTwo) {
        ApplyTwo = applyTwo;
    }

    public int getIndex() {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isApplyTwo() {
        return ApplyTwo;
    }

    public boolean isApplyOne() {
        return ApplyOne;
    }

    public void setNewValue(int n)
    {
        newValue = n;
    }

    public int getNewValue() {
        return newValue;
    }
}
