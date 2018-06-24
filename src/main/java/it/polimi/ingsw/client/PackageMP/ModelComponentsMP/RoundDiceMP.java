package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;
import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.Die;

import java.util.ArrayList;

public class RoundDiceMP {

    private ArrayList<Die> diceVector;
    private int index=0;
    private int dim=0;

    public RoundDiceMP(int par) throws InvalidIntArgumentException
    {
        if(par<0||par>9)
            throw new InvalidIntArgumentException();
        dim=par;
        diceVector = new ArrayList<Die>();

    }

    public void addDie(Die thisDie) throws FullDataStructureException
    {
        diceVector.add(thisDie);
    }

    public Die getDie(int pos) throws InvalidIntArgumentException
    {
        if(pos>diceVector.size()||pos<0)
            throw new InvalidIntArgumentException();
        return diceVector.get(pos);
    }

    public void deleteDie(int pos) throws InvalidIntArgumentException
    {
        diceVector.remove(pos);
    }

    public int returnDim()
    {
        return diceVector.size();
    }

    public ArrayList<Die> getDiceVector() {

        return diceVector;
    }

    public void addPos(int pos, Die temp)
    {
        diceVector.add(pos, temp);
    }
}
