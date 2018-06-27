package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentMP;

public class PublicObjectiveMP implements ModelComponentMP{

    private int id;
    private int bonus;

    /**
     * PublicObjectiveMP
     * @param id
     * @throws InvalidIntArgumentException
     */
    public PublicObjectiveMP(int id) throws InvalidIntArgumentException {
        if (id < 0 || id > 10)
            throw new InvalidIntArgumentException();
        this.id = id;
        this.bonus = 0;
    }

    public int getId()
    {
        return id;
    }

}
