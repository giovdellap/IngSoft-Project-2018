package it.polimi.ingsw.commons.Listeners;


import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.FcknSimpleLogger;

import java.util.ArrayList;
import java.util.List;

public class MagnificentListenersManager
{
    private ArrayList<EventListener> listeners;

    private FcknSimpleLogger logger;

    public MagnificentListenersManager()
    {
        listeners = new ArrayList<EventListener>();
        logger = new FcknSimpleLogger(0, false);
    }

    public void registerListener(EventListener listener)
    {
        listeners.add(listener);
    }
    public void unRegisterListener(EventListener listener)
    {
        listeners.remove(listener);
    }
    public void notify(Event event)
    {
        for(EventListener listener: listeners)
            listener.onEvent(event);
    }
}
