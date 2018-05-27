package it.polimi.ingsw.client.PackageSP.ModelComponentsSP;

public class PrivateObjectiveSP
{
    private int id;
    private int color;

    public PrivateObjectiveSP(int id, int color)
    {

        //costruttore
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
