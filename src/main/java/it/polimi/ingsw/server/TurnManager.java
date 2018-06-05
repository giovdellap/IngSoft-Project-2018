package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

public class TurnManager
{
    private Player[] players;
    private int turnIndex;
    private int maxTurn;
    private int round;
    private int activePlayer;

    public MinorLogger turnLogger;

    //PAY ATTENTION
    //This class doesn't manage disconnections

    public TurnManager(Player[] temp) throws GenericInvalidArgumentException {
        players=temp;
        maxTurn=players.length*2;
        turnLogger= new MinorLogger();
    }

    public int start() throws GenericInvalidArgumentException {
        //starts first turn
        //returns the index of first player
        round=0;
        turnIndex=0;
        players[0].activate();
        activePlayer=0;
        turnLogger.minorLog("Match started - "+activePlayer+"'s turn");
        return activePlayer;
    }

    public void endTurn() throws GenericInvalidArgumentException
    {
        //active player ends his turn
        players[activePlayer].deactivate();
        calculateNext();
        players[activePlayer].activate();
    }

    public int getActivePlayer() throws GenericInvalidArgumentException {
        //returns active player index if connected, active player index+10 if not
        turnLogger.minorLog("Active player: "+activePlayer);
        return activePlayer;

    }

    private void calculateNext() throws GenericInvalidArgumentException {

        if(turnIndex==maxTurn-1) {
            round++;
            for (int i = 0; i < players.length; i++)
                if (players[i].checkEight())
                    players[i].roundReset();
            turnIndex=0;
            activePlayer=0;
            turnLogger.minorLog("Calculated: NEW TURN");
        }
        if(turnIndex<players.length-1) {
            turnIndex++;
            activePlayer=turnIndex;
            turnLogger.minorLog("Calculated: turnIndex = "+turnIndex+" activePlayer = "+activePlayer);
        }
        if(turnIndex>=players.length&&turnIndex!=maxTurn-1)
        {
            if(turnIndex==players.length-1)
            {
                turnIndex++;
                turnLogger.minorLog("Calculated: turnIndex = "+turnIndex+" activePlayer = "+activePlayer);
            }
            if(turnIndex>=players.length&&turnIndex!=maxTurn-1)
            {
                turnIndex++;
                activePlayer--;
                turnLogger.minorLog("Calculated: turnIndex = "+turnIndex+" activePlayer = "+activePlayer);
            }
        }
    }

    public boolean theEnd()
    {
        return round==10;
    }

    public int getRound()
    {
        return round+1;
    }

    public Player getPlayer(int i)
    {
        return players[i];
    }

    //TOKENS

    public int getTokens()
    {
        return players[activePlayer].getTokens();
    }

    public void decreaseTokens(int n)
    {
        players[activePlayer].usedTokens(n);
    }

    public boolean checkFirst() { return round==0&&turnIndex<players.length; }
}
