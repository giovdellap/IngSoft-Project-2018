package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.SimpleLogger;

import java.util.ArrayList;

public class TurnManager
{
    private ArrayList<Player> players;
    private int turnIndex;
    private int playerIndex;
    private int maxTurn;
    private int round;
    private int activePlayer;

    private boolean nextRound=false;

    private SimpleLogger logger;


    /**
     * Class constructor
     * @param temp ArrayList of players
     */

    public TurnManager(ArrayList<Player> temp) {
        players=temp;
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
        players.get(0).activate();
        activePlayer=0;
        return activePlayer;
    }

    /**
     * deactivates a player who's turn ends, activates the next one using calculateNext();
     */

    public void endTurn()
    {
        //active player ends his turn
        players.get(activePlayer).deactivate();
        logger.log("prima: "+Boolean.toString(players.get(activePlayer).isActive()));
        calculateNext();
        players.get(activePlayer).activate();
        logger.log("Dopo: "+Boolean.toString(players.get(activePlayer).isActive()));

        logger.log("END ENDTURN - activePlayer = "+Integer.toString(activePlayer));

    }

    /**
     * @return player's who is currently playing
     */

    public int getActivePlayer() {
        return activePlayer;

    }

    /**
     * calculates the next player who has to play
     */
    private void calculateNext() {

        if(turnIndex==maxTurn-1) {
            round++;
            for (int i = 0; i < players.size(); i++)
                if (players.get(i).checkEight())
                    players.get(i).roundReset();
            turnIndex=0;
            activePlayer=0;
            nextRound=true;
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
        if(players.get(activePlayer).checkEight())
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
        return round==5;
    }
    /**
     *
     * @return number of players
     */
    public ArrayList<Player> getPlayers()
    {
        return players;
    }
    /**
     *
     * @param toSet ArrayList of players to set
     */
    public void setPlayers(ArrayList<Player> toSet)
    {
        players = toSet;
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
        players.get(player).useEight();
    }

}
