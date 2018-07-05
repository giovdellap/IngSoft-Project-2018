package it.polimi.ingsw.client.ModelComponentsMP;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class PublicObjectiveMP {

    private int id;

    /**
     * PublicObjectiveMP
     * @param id
     * @throws InvalidIntArgumentException
     */
    public PublicObjectiveMP(int id) throws InvalidIntArgumentException {
        if (id < 0 || id > 10)
            throw new InvalidIntArgumentException();
        this.id = id;
    }

    public int getId()
    {
        return id;
    }

}
