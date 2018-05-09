package it.polimi.ingsw.server;

public class RoundTrack
{
    private RoundDice[] roundVector;
    private int index;

    public RoundTrack()
    {
        roundVector = new RoundDice[10];
        index=0;
    }

    public void addRound(RoundDice temp, int dim)
    {
        roundVector[index]= new RoundDice(dim);
        roundVector[index]=temp;
        index++;
    }

    public int returnActualTurn()
    {
        return index;
    }

    public RoundDice returnNTurnRoundDice(int turn)
    {
        return roundVector[turn];
    }

    public void setSpecificRoundDice(RoundDice toSet, int turn)
    {
        roundVector[turn] = toSet;
    }
}
