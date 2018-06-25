package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;

public class PlayerClient
{
    private int id;
    private String name;

    private SchemeCardMP playerScheme;
    private int tokens;

    private boolean me;

    /**
     * PlayerClient Constructor
     * @param index id to assign
     * @param username name of the player
     * @param flag true if it's me, false if its not
     */
    public PlayerClient(int index, String username, boolean flag)
    {
        id=index;
        name = username;
        me = flag;
    }

    public void setPlayerScheme(SchemeCardMP temp) {
        playerScheme = temp;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTokens(int tokens) {
        this.tokens = tokens;
    }

    public int getId() {
        return id;
    }

    public int getTokens() {
        return tokens;
    }

    public SchemeCardMP getPlayerScheme() {
        return playerScheme;
    }

    public String getName() {
        return name;
    }
    public boolean itsMe() { return me;}


}
