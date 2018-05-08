package it.polimi.ingsw.server;


public class PrivateObjective
{
    private int id;
    private int color;

    public PrivateObjective(int id, int color)
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

import java.util.*;

public class PrivateObjective
{
    private int id;

    public PrivateObjective()
    {

    }

    public int getId()
    {
        // ritorna l'id
        return 0;
    }


}