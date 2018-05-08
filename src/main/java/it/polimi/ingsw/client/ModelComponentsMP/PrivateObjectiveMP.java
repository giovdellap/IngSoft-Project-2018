package it.polimi.ingsw.client.ModelComponentsMP;

import it.polimi.ingsw.client.ModelComponentMP;

public class PrivateObjectiveMP implements ModelComponentMP {

    private int id;
    private int color;

    public PrivateObjectiveMP(int id, int color)
    {

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
