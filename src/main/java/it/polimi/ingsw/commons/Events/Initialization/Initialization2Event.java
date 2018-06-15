package it.polimi.ingsw.commons.Events.Initialization;

import it.polimi.ingsw.commons.Events.Event;

import java.util.ArrayList;

public class Initialization2Event extends Event {


    private ArrayList<EventPlayer> players;

    public Initialization2Event()
    {
        super("Initialization2Event");
        players=new ArrayList<EventPlayer>();
    }

    public class EventPlayer
    {
        int id;
        String name;
        int schemeId;
        int fb;
        int tokens;

        //COSTRUCTOR
        public EventPlayer(int id, String name, int schemeId, int fb, int tokens)
        {
            this.id=id;
            this.name=name;
            this.schemeId=schemeId;
            this.fb=fb;
            this.tokens=tokens;
        }

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getSchemeId() {
            return schemeId;
        }

        public int getFb() {
            return fb;
        }

        public int getTokens() {
            return tokens;
        }
    }

    public void addEventPlayer(int id, String name, int schemeId, int fb, int tokens)
    {

        players.add(new EventPlayer(id, name, schemeId, fb, tokens) );

    }

    public EventPlayer getEventPlayer(int index)
    {
        return players.get(index);
    }

    public int getPlayerSize(){return players.size();}
}
