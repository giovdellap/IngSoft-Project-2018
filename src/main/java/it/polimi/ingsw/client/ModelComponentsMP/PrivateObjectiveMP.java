package it.polimi.ingsw.client.ModelComponentsMP;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.MultiPackage.ModelComponentMP;

public class PrivateObjectiveMP implements ModelComponentMP {

    private int color;

    public PrivateObjectiveMP(int color) throws InvalidIntArgumentException {
        if (color<0||color>5) throw new InvalidIntArgumentException();
        this.color = color;
    }

    public int getColor()
    {
        return color;
    }

}
