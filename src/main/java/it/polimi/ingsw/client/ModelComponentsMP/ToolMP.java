package it.polimi.ingsw.client.ModelComponentsMP;

import java.util.*;

public class ToolMP {

    private int id;
    private String name;
    private int favorTokenCost;


    public ToolMP()
    {

        this.id = id;
        this.name = name;
        this.favorTokenCost = favorTokenCost;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getFavorTokenCost() {
        return favorTokenCost;
    }

    public void setFavorTokenCost(int favorTokenCost) {
        this.favorTokenCost = favorTokenCost;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

}