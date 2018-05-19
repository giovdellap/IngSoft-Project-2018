package it.polimi.ingsw.client.ClientExceptions;

public class InvalidIntArgumentException extends Exception
{
    public InvalidIntArgumentException()
    {
        super();
    }
    public String getMessage()
    {
        return "The int argument is invalid";
    }

}
