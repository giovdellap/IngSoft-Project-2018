package it.polimi.ingsw.client.ModelComponentsMP;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class PrivateObjectiveMP {

    private int color;

    /**
     * PrivateObjectiveMP
     * @param color
     * @throws InvalidIntArgumentException
     */
    public PrivateObjectiveMP(int color) throws InvalidIntArgumentException {
        if (color<0||color>5) throw new InvalidIntArgumentException();
        this.color = color;
    }

    public int getColor()
    {
        return color;
    }

}
