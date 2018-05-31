package it.polimi.ingsw.clientOld.ModelComponentsMP;
import it.polimi.ingsw.clientOld.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.clientOld.ClientExceptions.InvalidIntArgumentException;

public class RoundDiceMP {

    private DieMP[] diceVector;
    private int index=0;
    private int dim=0;

    public RoundDiceMP(int par) throws InvalidIntArgumentException
    {
        if(par<0||par>9)
            throw new InvalidIntArgumentException();
        dim=par;
        diceVector = new DieMP[dim];
        for(int i=0;i<dim;i++)
            diceVector[i]=new DieMP(0);
    }

    public void addDie(DieMP thisDie) throws FullDataStructureException
    {
        if(index<dim)
        {

            diceVector[index] = thisDie;
            index++;
        }
        else
            throw new FullDataStructureException();
    }

    public DieMP getDie(int pos) throws InvalidIntArgumentException
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
