package it.polimi.ingsw.commons.Events.ToolsEvents;
import it.polimi.ingsw.server.ToolCards.ToolCardOne;

public class ToolCardOneEvent extends ToolCardEvent {

    int index;
    int x;
    int y;
    char action;

    public ToolCardOneEvent(int id) {
        super("ToolCardOneEvent",id);
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

    public void setAction(char action) {
        this.action = action;
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

    public char getAction() {
        return action;
    }

}
