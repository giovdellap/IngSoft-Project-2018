package it.polimi.ingsw.commons.Events.ToolsEvents;

public class ToolCardEightNineTenEvent extends ToolCardEvent {

    private int index;
    private int x;
    private int y;

    public ToolCardEightNineTenEvent(int id) {
        super("ToolCardEightNineTenEvent", id);
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

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getIndex() {
        return index;
    }

}
