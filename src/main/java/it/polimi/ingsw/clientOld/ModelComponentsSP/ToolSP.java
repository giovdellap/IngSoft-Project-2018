package it.polimi.ingsw.clientOld.ModelComponentsSP;


public class ToolSP {

    private int id;
    private String name;
    private int favorTokenCost;


    public ToolSP()
    {

        this.id = id;
        this.name = name;
        this.favorTokenCost = favorTokenCost;
        //costruttore
    }

    public int getId()
    {
        return id;
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

    public void setId(int id) {
        this.id = id;

    }


}