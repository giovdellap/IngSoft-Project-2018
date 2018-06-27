package it.polimi.ingsw.server.Loggers;

import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;

import java.util.ArrayList;

public class MinorLogger
{
    private ArrayList<String> log;

    /**
     * MinorLogger Constructor
     */
    public MinorLogger()
    {
        log = new ArrayList<String>();
    }

    /**
     * prints and adds the parameter string to the log
     * @param s string to print and add to the log
     * @throws GenericInvalidArgumentException
     */
    public void minorLog(String s) throws GenericInvalidArgumentException {
        if(s==null)
            throw new GenericInvalidArgumentException();
        System.out.println(s);
        log.add(s);
    }

    /**
     * adds the strings array to the log
     * @param temp ArrayList of strings
     * @throws GenericInvalidArgumentException
     */
    public void stackLog(ArrayList<String> temp) throws GenericInvalidArgumentException {
        if(temp==null)
            throw new GenericInvalidArgumentException();
        int i=0;
        while(i<temp.size())
        {
            log.add(temp.get(i));
            i++;
        }
    }

    /**
     * updates the parent log
     * @return ArrayList of strings
     */
    public ArrayList<String> updateFather()
    {
        return log;
    }

    /**
     * resets the log
     */
    public  void reinitialize()
    {
        log = new ArrayList<String>();
    }
}
