package it.polimi.ingsw.ModelComponentsSP;

public class RoundTrackSP
{
    private RoundDice[] roundVector;
    private int index=0;
    public RoundTrackSP()
    {
        roundVector = new RoundDice[10];
    }

    public void addRound(RoundDice temp, int dim)
    {
        roundVector[index] = new RoundDice(dim);
        roundVector[index] = temp;
    }
}
