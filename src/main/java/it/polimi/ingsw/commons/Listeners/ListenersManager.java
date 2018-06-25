package it.polimi.ingsw.commons.Listeners;


import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.SimpleLogger;

import java.util.ArrayList;

public class ListenersManager
{
    private ArrayList<EventListener> listeners;

    private SimpleLogger logger;

    /**
     * ListenersManager Constructor
     */
    public ListenersManager()
    {
        listeners = new ArrayList<EventListener>();
        logger = new SimpleLogger(0, false);
    }

    /**
     * notifies an event to listeners
     * @param event event to notify
     */
    public void notify(Event event)
    {
        for(EventListener listener: listeners)
            listener.onEvent(event);
    }
}
