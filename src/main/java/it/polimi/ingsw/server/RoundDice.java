package it.polimi.ingsw.server;

public class RoundDice
{
    private Die[] diceVector;
    private int index=0;
    private int dim=0;

    public RoundDice(int par)
    {
        dim=par;
        diceVector = new Die[dim];
        for(int i=0;i<dim;i++)
            diceVector[i]=new Die(0);
    }

    public void addDie(Die thisDie)
    {
        if(index<dim)
        {
            diceVector[index] = thisDie;
            index++;
        }
    }

    public Die getDie(int pos)
    {
        return diceVector[pos];
    }

    public void deleteDie(int pos)
    {
        diceVector[pos]=null;
    }
}
