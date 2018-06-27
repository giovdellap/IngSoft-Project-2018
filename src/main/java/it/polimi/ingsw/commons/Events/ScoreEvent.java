package it.polimi.ingsw.commons.Events;

import java.util.ArrayList;

public class ScoreEvent extends Event
{

    private ArrayList<ScorePlayer> players;

    public ScoreEvent() {
        super("ScoreEvent");
        players=new ArrayList<ScorePlayer>();
    }


    public void addPlayer(ScorePlayer player)
    {
        players.add(player);
    }
    public ArrayList<ScorePlayer> getPlayers()
    {
        return players;
    }

    public void setPlayer(int index, ScorePlayer player)
    {
        players.set(index,player);
    }

}
