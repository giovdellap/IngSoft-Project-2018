package it.polimi.ingsw.commons.Events.ToolsEvents;

public class ToolCardTwelveEvent extends ToolCardEvent {

    private int index;
    private int pos;
    private int turn;
    private int x01;
    private int y01;
    private int x02;
    private int y02;
    private int x11;
    private int y11;
    private int x22;
    private int y22;

    private boolean onlyOne=true;


    public ToolCardTwelveEvent(int id) {
        super("ToolCardTwelveEvent", id);
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

    public void setX01(int x01) {
        this.x01 = x01;
    }

    public void setX02(int x02) {
        this.x02 = x02;
    }

    public void setX11(int x11) {
        this.x11 = x11;
    }

    public void setX22(int x22) {
        this.x22 = x22;
    }

    public void setY01(int y01) {
        this.y01 = y01;
    }

    public void setY02(int y02) {
        this.y02 = y02;
    }

    public void setY11(int y11) {
        this.y11 = y11;
    }

    public void setY22(int y22) {
        this.y22 = y22;
    }

    public int getX01() {
        return x01;
    }

    public int getX02() {
        return x02;
    }

    public int getY01() {
        return y01;
    }

    public int getY02() {
        return y02;
    }

    public int getX11() {
        return x11;
    }

    public int getY11() {
        return y11;
    }

    public int getX22() {
        return x22;
    }

    public int getY22() {
        return y22;
    }

    public void setOnlyOne(boolean onlyOne) {
        this.onlyOne = onlyOne;
    }

    public boolean isOnlyOne() {
        return onlyOne;
    }
}
