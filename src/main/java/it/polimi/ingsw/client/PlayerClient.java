package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

public class PlayerClient
{
    private String name;

    private SchemeCard playerScheme;
    private int tokens;

    private boolean me;

    /**
     * PlayerClient Constructor
     * @param username name of the player
     * @param flag true if it's me, false if its not
     */
    public PlayerClient(String username, boolean flag)
    {
        name = username;
        me = flag;
    }

    public void setPlayerScheme(SchemeCard temp) {
        playerScheme = temp;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getTokens() {
        return tokens;
    }

    public SchemeCard getPlayerScheme() {
        return playerScheme;
    }

    public String getName() {
        return name;
    }

    public boolean itsMe() { return me;}


}
