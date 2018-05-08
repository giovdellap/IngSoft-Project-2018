package it.polimi.ingsw.server;
import java.util.*;


public class PrivateObjective {
    private int id;
    private int color;

    public PrivateObjective(int id, int color) {

        this.id = id;
        this.color = color;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }


}