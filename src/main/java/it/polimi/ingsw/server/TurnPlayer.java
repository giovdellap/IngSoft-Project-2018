package it.polimi.ingsw.server;

public class TurnPlayer
{
    private int id;// player id
    private boolean eightUsed;//player ToolCard 8 usage flag

    public TurnPlayer(PlayerThread player)
    {
        id = player.getId();
        eightUsed = false;
    }

    public int getId() {
        return id;
    }

    public void setEightUsed(boolean eightUsed) {
        this.eightUsed = eightUsed;
    }

    public boolean isEightUsed() {
        return eightUsed;
    }
}
