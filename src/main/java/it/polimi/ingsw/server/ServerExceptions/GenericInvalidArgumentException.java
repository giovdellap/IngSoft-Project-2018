package it.polimi.ingsw.server.ServerExceptions;

public class GenericInvalidArgumentException extends Exception
{
    public GenericInvalidArgumentException()
    {
        super();
    }

    public String getMessage()
    {
        return "Generic invalid argument";
    }

}
