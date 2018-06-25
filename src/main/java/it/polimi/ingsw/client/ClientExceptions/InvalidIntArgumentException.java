package it.polimi.ingsw.client.ClientExceptions;

public class InvalidIntArgumentException extends Exception
{
    /**
    * InvalidIntArgumentException Constructor
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
