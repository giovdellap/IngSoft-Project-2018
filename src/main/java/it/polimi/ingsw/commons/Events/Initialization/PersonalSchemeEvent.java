package it.polimi.ingsw.commons.Events.Initialization;

import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

public class PersonalSchemeEvent extends Event {

    private int playerId;
    private SchemeCard scheme;

    public PersonalSchemeEvent(SchemeCard scheme, int id) {
        super("PersonalSchemeEvent");

        this.scheme=scheme;
        this.playerId=id;
    }

    public int getPlayer()
    {
        return playerId;
    }

    public SchemeCard getScheme() {
        return scheme;
    }
}
