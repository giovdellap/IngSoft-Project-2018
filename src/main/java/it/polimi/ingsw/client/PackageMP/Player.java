package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class Player
{
    private int id;
    private String name;

    private SchemeCardMP playerScheme;
    private int tokens;

    public Player(int index, String username)
    {
        id=index;
        name = username;
    }

    public void setPlayerScheme(SchemeCardMP temp) throws it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException {
        playerScheme = temp;
        tokens = temp.getDiff(temp.getfb());
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


}
