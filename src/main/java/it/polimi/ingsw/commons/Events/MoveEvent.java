package it.polimi.ingsw.commons.Events;

public class MoveEvent extends Event{

    private int id;
    private int index;
    private int x;
    private int y;

    public MoveEvent(int index, int x, int y) {
        super("MoveEvent");
        this.index=index;
        this.x=x;
        this.y=y;
    }

    public int getIndex()
    {
        return index;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setId(int id)
    {
        this.id=id;
    }

    public int getId() {
        return id;
    }
}
