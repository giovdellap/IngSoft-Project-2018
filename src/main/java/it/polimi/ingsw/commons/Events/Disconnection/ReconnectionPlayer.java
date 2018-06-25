package it.polimi.ingsw.commons.Events.Disconnection;

import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class ReconnectionPlayer
{
    private String name;
    private int tokens;
    private int schemeId;
    private int fb;
    private ArrayList<ReconnectionSchemeDie> schemeDice;

    public ReconnectionPlayer(String name, int tokens, SchemeCard scheme) throws InvalidIntArgumentException {
        this.name=name;
        this.tokens=tokens;
        schemeId=scheme.getID();
        fb=scheme.getfb();
        schemeDice = new ArrayList<ReconnectionSchemeDie>();
        addSchemeDie(scheme);
    }

    public void addSchemeDie(SchemeCard scheme) throws InvalidIntArgumentException {
        for(int x=0;x<4;x++)
            for(int y=0;y<5;y++)
                if(!scheme.getDie(x, y).isDisabled())
                    schemeDice.add(new ReconnectionSchemeDie(x, y, scheme.getDie(x, y).getValue(), scheme.getDie(x, y).getColor()));

    }
    public ArrayList<ReconnectionSchemeDie> getSchemeDice() {
        return schemeDice;
    }
    public String getName() {
        return name;
    }

    public int getTokens() {
        return tokens;
    }

    public int getSchemeId() {
        return schemeId;
    }

    public int getFb() {
        return fb;
    }

    public class ReconnectionSchemeDie
    {
        private int x;
        private int y;
        private int value;
        private int color;

        public ReconnectionSchemeDie(int x, int y, int value, int color)
        {
            this.x=x;
            this.y=y;
            this.value=value;
            this.color=color;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getValue() {
            return value;
        }

        public int getColor() {
            return color;
        }
    }
}
