package it.polimi.ingsw.server.ServerExceptions;

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
