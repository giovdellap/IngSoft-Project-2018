package it.polimi.ingsw.commons.Exceptions;

public class GenericInvalidArgumentException extends Exception
{
    /**
     * GenericInvalidArgumentException Constructor
     */
    public GenericInvalidArgumentException()
    {
        super();
    }

    /**
     * gets message for this kind of exception
     * @return string with message
     */
    public String getMessage()
    {
        return "Generic invalid argument";
    }

}
