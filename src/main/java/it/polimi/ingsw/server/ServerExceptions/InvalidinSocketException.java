package it.polimi.ingsw.server.ServerExceptions;

public class InvalidinSocketException extends Exception
{
    /**
     * InvalidinSocketException Constructor
     */
    public InvalidinSocketException()
    {
        super();
    }

    /**
     * gets message for this kind of exception
     * @return string with message
     */
    public String getMessage()
    {
        return "Invalid inSocket tempArg";
    }
}
