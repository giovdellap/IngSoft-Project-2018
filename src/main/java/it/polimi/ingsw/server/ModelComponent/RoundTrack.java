package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class RoundTrack
{
    private RoundDice[] roundVector;
    private int index;

    /**
     * RoundTrack Constructor
     */
    public RoundTrack()
    {
        roundVector = new RoundDice[10];
        index=0;
    }

    /**
     * adds a round to the round track
     * @param temp round dice to add to the round track
     * @throws InvalidIntArgumentException
     */
    public void addRound(RoundDice temp) throws InvalidIntArgumentException
    {

        roundVector[index]= new RoundDice(temp.returnDim());
        roundVector[index]=temp;
        index++;
    }

    /**
     * returns current round
     * @return round's index
     */

    public int returnActualTurn()
    {
        return index;
    }

    /**
     * returns specific round dice at parameter round
     * @param turn round from which to get round dice
     * @return round dice at specific round
     * @throws InvalidIntArgumentException
     */
    public RoundDice returnNTurnRoundDice(int turn) throws InvalidIntArgumentException
    {
        if(turn<0||turn>index)
            throw new InvalidIntArgumentException();
        return roundVector[turn];
    }

    /**
     * sets specific round dice at parameter round
     * @param toSet round dice to set
     * @param turn round at which to set
     * @throws InvalidIntArgumentException
     */
    public void setSpecificRoundDice(RoundDice toSet, int turn) throws InvalidIntArgumentException
    {
        if(turn<0||turn>=index)
            throw new InvalidIntArgumentException();
        roundVector[turn] = toSet;
    }
}
