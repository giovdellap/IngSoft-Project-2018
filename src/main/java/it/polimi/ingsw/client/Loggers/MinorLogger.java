package it.polimi.ingsw.client.Loggers;

import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.util.ArrayList;

public class MinorLogger
{
    private ArrayList<String> log;

    public MinorLogger()
    {
        log = new ArrayList<String>();
    }

    public void minorLog(String s) throws GenericInvalidArgumentException {
        if(s==null)
            throw new GenericInvalidArgumentException();
        log.add(s);
    }

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

    public ArrayList<String> updateFather()
    {
        return log;
    }

    public  void reinitialize()
    {
        log = new ArrayList<String>();
    }
}
