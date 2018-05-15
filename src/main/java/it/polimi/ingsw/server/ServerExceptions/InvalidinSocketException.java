package it.polimi.ingsw.server.ServerExceptions;

public class InvalidinSocketException extends Exception
{
    public InvalidinSocketException()
    {
        super();
    }

    public String getMessage()
    {
        return "Invalid inSocket tempArg";
    }
}
