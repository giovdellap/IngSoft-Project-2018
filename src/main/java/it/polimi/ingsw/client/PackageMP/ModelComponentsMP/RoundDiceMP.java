package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;
import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.Die;

public class RoundDiceMP {

    private Die[] diceVector;
    private int index=0;
    private int dim=0;

    public RoundDiceMP(int par) throws InvalidIntArgumentException
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
