package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class RoundDice
{
    private Die[] diceVector;
    private int index=0;
    private int dim=0;

    public RoundDice(int par) throws InvalidIntArgumentException
    {
        if(par<0||par>9)
            throw new InvalidIntArgumentException();
        dim=par;
        diceVector = new Die[dim];
        for(int i=0;i<dim;i++)
            diceVector[i]=new Die(0);
    }

    public void addDie(Die thisDie) throws FullDataStructureException
    {
        if(index<dim)
        {

            diceVector[index] = thisDie;
            index++;
        }
        else
            throw new FullDataStructureException();
    }

    public Die getDie(int pos) throws InvalidIntArgumentException
    {
        if(pos>index||pos<0||diceVector[pos].isDisabled())
            throw new InvalidIntArgumentException();
        return diceVector[pos];
    }

    public void deleteDie(int pos) throws InvalidIntArgumentException
    {
        if(pos>index||pos<0||diceVector[pos].isDisabled())
            throw new InvalidIntArgumentException();
        diceVector[pos].disableDie();
    }

    public int returnDim()
    {
        return dim;
    }
}
