package it.polimi.ingsw.clientOld.ClientExceptions;

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
