package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.FcknSimpleLogger;
import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

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

    private FcknSimpleLogger logger;

    //PAY ATTENTION
    //This class doesn't manage disconnections

    public TurnManager(ArrayList<Player> temp) throws GenericInvalidArgumentException {
        players=temp;
        maxTurn=players.size()*2;
        logger = new FcknSimpleLogger(0, false);
    }

    public int start() throws GenericInvalidArgumentException {
        //starts first turn
        //returns the index of first player
        round=0;
        turnIndex=0;
        players.get(0).activate();
        activePlayer=0;
        return activePlayer;
    }

    public void endTurn() throws GenericInvalidArgumentException
    {
        //active player ends his turn
        players.get(activePlayer).deactivate();
        logger.log("prima: "+Boolean.toString(players.get(activePlayer).isActive()));
        calculateNext();
        players.get(activePlayer).activate();
        logger.log("Dopo: "+Boolean.toString(players.get(activePlayer).isActive()));

        logger.log("END ENDTURN - activePlayer = "+Integer.toString(activePlayer));

    }

    public int getActivePlayer() throws GenericInvalidArgumentException {
        //returns active player index if connected, active player index+10 if not
        return activePlayer;

    }

    private void calculateNext() throws GenericInvalidArgumentException {

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

        logger.log(" ");
        logger.log("ActivePlayer: "+Integer.toString(activePlayer));
        logger.log("TurnIndex: "+Integer.toString(turnIndex));
        logger.log(" ");
    }

    public boolean theEnd()
    {
        return round==10;
    }

    public ArrayList<Player> getPlayers()
    {
        return players;
    }
    public void setPlayers(ArrayList<Player> toSet)
    {
        players = toSet;
    }
    public int getRound()
    {
        return round;
    }
    public boolean getFirst()
    {
        if(round==0&&turnIndex<players.size())
            return true;
        else
            return false;
    }
    public int getTurnIndex() {
        return turnIndex;
    }

    public boolean isNextRound() {

        boolean toReturn=nextRound;
        nextRound=false;
        return toReturn;

    }
}
