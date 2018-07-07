package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.Events.Initialization.PersonalSchemeEvent;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class MatchManager
{
    private PlayerClient[] players;
    private int round;
    private ArrayList<String> disconnectedPlayers;
    private int activePlayer;

    /**
     * MatchManager Constructor, sets scheme to newly initialized players
     * @param event initialization 2 event
     * @param me
     * @throws InvalidIntArgumentException
     */
    public MatchManager(Initialization2Event event, String me) throws FileNotFoundException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
    {
        SchemesDeck deck = new SchemesDeck();
        players = new PlayerClient[event.getPlayerSize()];
        for(int i=0;i<players.length;i++)
        {
            players[i] = new PlayerClient(event.getEventPlayer(i).getName(), me.equals(event.getEventPlayer(i).getName()));
            if(event.getEventPlayer(i).getSchemeId()!=100) {
                SchemeCard scheme = deck.extractSchemebyID(event.getEventPlayer(i).getSchemeId());
                scheme.setfb(event.getEventPlayer(i).getFb());
                players[i].setPlayerScheme(scheme);
            }
            players[i].setTokens(event.getEventPlayer(i).getTokens());
        }
        disconnectedPlayers = new ArrayList<String>();
    }

    public void addPersonalScheme(PersonalSchemeEvent event)
    {
        players[event.getPlayer()].setPlayerScheme(event.getScheme());
    }

    public MatchManager()
    {

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
        disconnectedPlayers = temp;

    }
    public ArrayList<String> getDisconnectedPlayers()
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

    public void setPlayerScheme(int id, SchemeCard scheme) {
        players[id].setPlayerScheme(scheme);
    }


}
