package it.polimi.ingsw.client.ClientExceptions;

public class FullDataStructureException extends Exception
{
    public FullDataStructureException()
    {
        super();
    }

    public String getMessage()
    {
        return "Data Structure full: cannot add";
    }

}
