package it.polimi.ingsw.commons.Events.ToolsEvents;

public class ToolCardElevenEvent extends ToolCardEvent {

    int index;
    int newValue;
    int x;
    int y;

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
}
