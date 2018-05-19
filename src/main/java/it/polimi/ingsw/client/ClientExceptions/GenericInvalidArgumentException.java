package it.polimi.ingsw.client.ClientExceptions;

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
