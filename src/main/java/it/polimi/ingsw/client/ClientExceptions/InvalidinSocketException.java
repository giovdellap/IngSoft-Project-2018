package it.polimi.ingsw.client.ClientExceptions;

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
