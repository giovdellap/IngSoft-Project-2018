package it.polimi.ingsw.commons.Events.ToolsEvents;

import it.polimi.ingsw.commons.Events.Event;

public abstract class ToolCardEvent extends Event {
    private int id;
    private int player;

    public ToolCardEvent(String s, int id)
    {
        super(s);
        this.id=id;
    }
    public int getId()
    {
        return id;
    }

    public int getPlayer()
    {
        return player;
    }

    public void setPlayer(int player)
    {
        this.player = player;
    }
}
