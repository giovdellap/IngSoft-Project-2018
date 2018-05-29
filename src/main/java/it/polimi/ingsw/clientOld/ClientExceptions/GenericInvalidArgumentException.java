package it.polimi.ingsw.clientOld.ClientExceptions;

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
