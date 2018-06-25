package it.polimi.ingsw.commons.Events.Disconnection;

import it.polimi.ingsw.commons.Events.Event;

public class DisconnectionEvent extends Event {

    private int id;

    public DisconnectionEvent() {
        super("DisconnectionEvent");
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
