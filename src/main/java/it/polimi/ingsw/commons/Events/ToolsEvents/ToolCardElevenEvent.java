package it.polimi.ingsw.commons.Events.ToolsEvents;

public class ToolCardElevenEvent extends ToolCardEvent {

    private int index;
    private int newValue;
    private int newColor;
    private int x;
    private int y;
    private boolean applyOne=false;
    private boolean applyTwo=false;
    private boolean firstCheck=false;

    public ToolCardElevenEvent(int id) {
        super("ToolCardElevenEvent", id);
    }

    public void setX(int x){
        this.x=x;
    }

    public void setY(int y) {
        this.y=y;
    }

    public void setIndex(int index) {
        this.index=index;
    }

    public void setNewValue(int newValue) {
        this.newValue = newValue;
    }

    public void setNewColor(int newColor) {
        this.newColor = newColor;
    }

    public void setApplyOne(boolean applyOne) {
        this.applyOne = applyOne;
    }

    public void setApplyTwo(boolean applyTwo) {
        this.applyTwo = applyTwo;
    }

    public void setFirstCheck(boolean firstCheck) {
        this.firstCheck = firstCheck;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

    public int getNewValue() {
        return newValue;
    }

    public int getNewColor() {
        return newColor;
    }

    public boolean isApplyOne() {
        return applyOne;
    }

    public boolean isApplyTwo() {
        return applyTwo;
    }

    public boolean isFirstCheck() {
        return firstCheck;
    }
}
