package it.polimi.ingsw.clientOld.ClientExceptions;

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
