package it.polimi.ingsw.server;

public class Player
{
    private int id;// player id
    private String name;// player username
    private boolean active;//playing player flag
    private int tokens;//player tokens counter
    private boolean eightUsed;//player ToolCard 8 usage flag

    public Player(int id, String name, int tokens)
    {
        this.id=id;
        this.name=name;
        active=false;
        this.tokens=tokens;
        eightUsed=false;
    }

    //GET METHODS
    public String getName()
    {
        return name;
    }

    public int getId()
    {
        return id;
    }





    //ACTIVATION
    public void activate()
    {
        active=true;
    }

    public void deactivate()
    {
        active=false;
    }

    public boolean isActive() {
        return active;
    }


    //TOKENS
    public int getTokens() { return tokens; }

    public void usedTokens(int num)
    {
        //player used num tokens
        tokens=tokens-num;
    }

    public boolean canUse(int toDecrease)
    {
        //can player use those tokens?
        if(tokens-toDecrease>=0)
            return true;
        else return false;
    }

    //TOKENS
    public void useEight() { eightUsed=true; }

    public boolean checkEight() { return eightUsed; }

    public void roundReset() {
        //resets eightUsed when round changes
        eightUsed=false;
    }
}
