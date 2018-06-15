package it.polimi.ingsw.commons.Events.Initialization;

import it.polimi.ingsw.commons.Events.Event;

public class UsernameEvent extends Event {

    private String userName;

    public UsernameEvent(String name) {
        super("UsernameEvent");
        userName = name;
    }

    public String getUserName() {
        return userName;
    }
}
