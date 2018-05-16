package it.polimi.ingsw.server.Loggers;

import java.util.ArrayList;

public class MinorLogger
{
    private ArrayList<String> log;

    public MinorLogger()
    {
        log = new ArrayList<String>();
    }

    public void minorLog(String s)
    {
        System.out.println(s);
        log.add(s);
    }

    public void stackLog(ArrayList<String> temp)
    {
        int i=0;
        while(i<temp.size())
        {
            log.add(temp.get(i));
            i++;
        }
    }

    public ArrayList<String> updateFather()
    {
        int i=0;
        ArrayList<String> temp = new ArrayList<String>();
        while(i<temp.size())
        {
            temp.add(log.get(i));
            i++;
        }
        log = new ArrayList<String>();
        return temp;
    }
}
