package it.polimi.ingsw.client.ModelComponentsMP;
import it.polimi.ingsw.client.ClientExceptions.*;

public class RoundTrackMP {

    private RoundDiceMP[] roundVector;
    private int index;

    public RoundTrackMP()
    {
        roundVector = new RoundDiceMP[10];
        index=0;
    }

    public void addRound(RoundDiceMP temp) throws InvalidIntArgumentException
    {

        roundVector[index]= new RoundDiceMP(temp.returnDim());
        roundVector[index]=temp;
        index++;
    }

    public int returnActualTurn()
    {
        return index;
    }

    public RoundDiceMP returnNTurnRoundDice(int turn) throws InvalidIntArgumentException
    {
        if(turn<0||turn>=index)
            throw new InvalidIntArgumentException();
        return roundVector[turn];
    }

    public void setSpecificRoundDice(RoundDiceMP toSet, int turn) throws InvalidIntArgumentException
    {
        if(turn<0||turn>=index)
            throw new InvalidIntArgumentException();
        roundVector[turn] = toSet;
    }

}
