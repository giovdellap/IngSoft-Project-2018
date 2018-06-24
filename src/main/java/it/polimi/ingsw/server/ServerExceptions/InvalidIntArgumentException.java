package it.polimi.ingsw.server.ServerExceptions;

public class InvalidIntArgumentException extends Exception
{

    /**
     * InvalidIntArgumentException Constructo
     */
    public InvalidIntArgumentException()
    {
        super();
    }

    /**
     * gets message for this kind of exception
     * @return string with message
     */
    public String getMessage()
    {
        return "The int argument is invalid";
    }

}
