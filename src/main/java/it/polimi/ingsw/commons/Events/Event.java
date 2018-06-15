package it.polimi.ingsw.commons.Events;

public abstract class Event
{
    private String eventType;
    private boolean validated;
    //validate represents server check
    //server boolean in the constructor is true only if the event is created server-side

    public Event(String s)
    {
        eventType = s;
        validated = false;
    }
    public String getType() {
        return eventType;
    }

    public void validate()
    {
        validated=true;
    }
    public boolean isValidated()
    {
        return validated;
    }
    public void resetValidation()
    {
        validated=false;
    }
}
