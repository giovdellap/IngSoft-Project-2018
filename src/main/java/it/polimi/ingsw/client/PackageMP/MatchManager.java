package it.polimi.ingsw.client.PackageMP;


import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemesDeckMP;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;

import java.util.ArrayList;

public class MatchManager
{
    private PlayerClient[] players;
    private int round;
    private ArrayList<Integer> disconnectedPlayers;
    private int activePlayer;

    /**
     * MatchManager Constructor, sets scheme to newly initialized players
     * @param event initialization 2 event
     * @param me
     * @throws InvalidIntArgumentException
     */
    public MatchManager(Initialization2Event event, String me) throws InvalidIntArgumentException {
        SchemesDeckMP deck = new SchemesDeckMP();
        players = new PlayerClient[event.getPlayerSize()];
        for(int i=0;i<players.length;i++)
        {
            players[i] = new PlayerClient(i, event.getEventPlayer(i).getName(), me.equals(event.getEventPlayer(i).getName()));
            SchemeCardMP scheme = deck.extractSchemebyID(event.getEventPlayer(i).getSchemeId());
            scheme.setfb(event.getEventPlayer(i).getFb());
            players[i].setPlayerScheme(scheme);
            players[i].setTokens(event.getEventPlayer(i).getTokens());
        }
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

    /**
     * sets disconnected players to an ArrayList of strings
     * @param temp
     */
    public void setDisconnectedPlayers(ArrayList<String> temp)
    {
        for(String str: temp)
            for(int i=0;i<players.length;i++)
                if(players[i].getName().equals(str))
                    disconnectedPlayers.add(i);

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



    public PlayerClient[] getGraphicsUpdate()
    {
        return players;
    }

    public void setPlayerScheme(int id, SchemeCardMP scheme) throws InvalidIntArgumentException {
        players[id].setPlayerScheme(scheme);
    }
}
