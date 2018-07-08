package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.SimpleLogger;

import java.util.ArrayList;

public class TurnManager
{
    private ArrayList<TurnPlayer> players;
    private int turnIndex;
    private int maxTurn;
    private int round;
    private int activePlayer;

    private boolean nextRound=false;

    private SimpleLogger logger;


    /**
     * Class constructor
     * @param temp ArrayList of players
     */

    public TurnManager(ArrayList<PlayerThread> temp) {
        players= new ArrayList<TurnPlayer>();
        for(int i=0;i<temp.size();i++)
            players.add(new TurnPlayer(temp.get(i)));

        maxTurn=players.size()*2;
        logger = new SimpleLogger(0, false);

    }



    /**
     * starts the game, initializing the variables and activating the first player
     * @return active player
     */

    public int start() {
        round=0;
        turnIndex=0;
        activePlayer=0;
        return activePlayer;
    }

    /**
     * deactivates a player who's turn ends, activates the next one using calculateNext();
     */

    public void endTurn()
    {
        //active player ends his turn

        calculateNext();

        logger.log("END ENDTURN - activePlayer = "+Integer.toString(activePlayer));

    }

    /**
     * @return player's who is currently playing
     */

    public int getActivePlayer() {
        return players.get(activePlayer).getId();
    }

    /**
     * calculates the next player who has to play
     */
    private void calculateNext() {

        if(turnIndex==maxTurn-1) {
            round++;
            for (int i = 0; i < players.size(); i++)
                players.get(i).setEightUsed(false);
            turnIndex=0;
            activePlayer=0;
            nextRound=true;
            players.add(players.get(0));
            players.remove(0);
        }
        if(turnIndex>=players.size()&&turnIndex!=maxTurn-1)
        {
            turnIndex++;
            activePlayer--;
        }
        if(turnIndex==players.size()-1)
        {
            turnIndex++;
        }
        if(turnIndex<players.size()-1&&!nextRound) {
            turnIndex++;
            activePlayer=turnIndex;
        }
        if(players.get(activePlayer).isEightUsed())
            calculateNext();

        logger.log(" ");
        logger.log("ActivePlayer: "+Integer.toString(activePlayer));
        logger.log("TurnIndex: "+Integer.toString(turnIndex));
        logger.log(" ");
    }

    /**
     * @return true if round reached 10 (end of the game)
     */

    public boolean theEnd()
    {
        return round==2;
    }


    /**
     *
     * @return number of game's round
     */
    public int getRound()
    {
        return round;
    }

    /**
     *
     * @return turn's index
     */
    public int getTurnIndex() {
        return turnIndex;
    }

    /**
     *
     * @return boolean if it's a new round
     */
    public boolean isNextRound() {

        boolean toReturn=nextRound;
        nextRound=false;
        return toReturn;

    }

    /**
     *
     * @param player player who's using tool card 8
     */

    public void usedEight(int player)
    {
        players.get(player).setEightUsed(true);
    }

    public boolean canUseSevenOrCantUseEight() {
        return turnIndex>players.size()-1;
    }

    public int previousActive() throws GenericInvalidArgumentException {
        if(turnIndex==players.size())
            return (players.size()-1);
        if(turnIndex==0)
            return players.size()-1;
        if(turnIndex<players.size())
            return turnIndex-1;
        if(turnIndex>players.size())
            return activePlayer+1;
        else
            throw new GenericInvalidArgumentException();
    }

    public void stopMatch()
    {
        round=2;
    }

}
