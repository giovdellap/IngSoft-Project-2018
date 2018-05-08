package it.polimi.ingsw.client.ModelComponentsMP;

import it.polimi.ingsw.client.ModelComponentMP;

public class PrivateObjectiveMP implements ModelComponentMP {
<<<<<<< HEAD

    private int id;
    private int color;

    public PrivateObjectiveMP(int id, int color)
    {

        this.id = id;
        this.color = color;
=======
    private int id;

    public PrivateObjectiveMP()
    {
        //costruttore
>>>>>>> 8450c4b95595264bc4cf0238cd708dfb324729ab
    }


    public int getId() {
<<<<<<< HEAD
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
=======

        // ritorna l'ID
        return 0;
>>>>>>> 8450c4b95595264bc4cf0238cd708dfb324729ab
    }
}
