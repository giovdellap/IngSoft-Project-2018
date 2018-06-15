package it.polimi.ingsw.commons.Events.Initialization;

import it.polimi.ingsw.commons.Events.Event;

public class SchemeSelectionEvent extends Event {

    private int id;
    private int fb;

    public SchemeSelectionEvent(int id, int fb) {
        super("SchemeSelectionEvent");
        this.id=id;
        this.fb=fb;
    }

    public int getId()
    {
        return id;
    }
    public int getFb()
    {
        return fb;
    }
}
