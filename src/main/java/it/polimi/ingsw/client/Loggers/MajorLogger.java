package it.polimi.ingsw.client.Loggers;

import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;

import java.util.ArrayList;

public class MajorLogger
{
    private ArrayList<String> log;

    public MajorLogger()
    {
        log = new ArrayList<String>();
    }

    public void majorLog(String s) throws GenericInvalidArgumentException {
        if(s==null)
            throw new GenericInvalidArgumentException();
        log.add(s);
    }

    public void getLog()
    {
        System.out.println("SERVER LOG");
        System.out.println("");

        int i=0;
        while(i<log.size())
        {
            System.out.println(log.get(i));
            i++;
        }

        System.out.println("");
        System.out.println("LOG END");
    }

    public void stackLog(ArrayList<String> temp) throws GenericInvalidArgumentException {
        if(temp==null)
            throw new GenericInvalidArgumentException();
        int i=0;
        while(i<temp.size()) {
            log.add(temp.get(i));
            i++;
        }
    }

    public ArrayList<String> returnLog()
    {
        return log;
    }
}
