package it.polimi.ingsw.client.PackageMP;


import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;

import java.util.ArrayList;

public class MatchManager
{
    private PlayerClient[] players;
    private int round;
    private ArrayList<Integer> disconnectedPlayers;
    private int activePlayer;

    //PLAYERS
    public MatchManager(PlayerClient[] temp)
    {
        players=temp;
    }
    public PlayerClient getPlayer(int index)
    {
        return players[index];
    }
    public void setPlayer(int index, PlayerClient player)
    {
        players[index]=player;
    }

    //ROUND

    public void setRound(int round) {
        this.round = round;
    }
    public int getRound()
    {
        return round;
    }

    //DISCONNECTED PLAYERS

    public void setDisconnectedPlayers(ArrayList<Integer> temp)
    {
        disconnectedPlayers = temp;
    }
    public ArrayList<Integer> getDisconnectedPlayers()
    {
        return disconnectedPlayers;
    }

    //ACTIVE PLAYER

    public void setActivePlayer(int activePlayer) {
        this.activePlayer = activePlayer;
    }
    public int getActivePlayer() {
        return activePlayer;
    }

    public SchemeCardMP getMyScheme()
    {
        for(int i=0;i<players.length;i++)
        {
            if(players[i].itsMe())
                return players[i].getPlayerScheme();
        }
        return null;
    }
    public void setMyScheme(SchemeCardMP scheme) throws InvalidIntArgumentException {
        for(int i=0;i<players.length;i++)
            if(players[i].itsMe())
                players[i].setPlayerScheme(scheme);
    }

    public PlayerClient[] getGraphicsUpdate()
    {
        return players;
    }
}
