package it.polimi.ingsw.client.ClientExceptions;

public class FullDataStructureException extends Exception
{
    /**
     * FullDataStructureException Constructor
     */

    public FullDataStructureException()
    {
        super();
    }

    /**
     * gets message for this kind of exception
     * @return string with message
     */
    public String getMessage()
    {
        return "Data Structure full: cannot add";
    }

}
