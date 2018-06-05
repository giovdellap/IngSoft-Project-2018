package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class RoundTrack
{
    private RoundDice[] roundVector;
    private int index;

    public RoundTrack()
    {
        roundVector = new RoundDice[10];
        index=0;
    }

    public void addRound(RoundDice temp) throws InvalidIntArgumentException
    {

        roundVector[index]= new RoundDice(temp.returnDim());
        roundVector[index]=temp;
        index++;
    }

    public int returnActualTurn()
    {
        return index;
    }

    public RoundDice returnNTurnRoundDice(int turn) throws InvalidIntArgumentException
    {
        if(turn<0||turn>index)
            throw new InvalidIntArgumentException();
        return roundVector[turn];
    }

    public void setSpecificRoundDice(RoundDice toSet, int turn) throws InvalidIntArgumentException
    {
        if(turn<0||turn>=index)
            throw new InvalidIntArgumentException();
        roundVector[turn] = toSet;
    }
}
