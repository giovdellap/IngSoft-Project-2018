package it.polimi.ingsw.server.Loggers;

import java.util.ArrayList;

public class MajorLogger
{
    private ArrayList<String> log;

    public MajorLogger()
    {
        log = new ArrayList<String>();
    }

    public void majorLog(String s)
    {
        log.add(s);
        System.out.println(s);
    }

    public void getLog()
    {
        System.out.println("SERVER LOG");
        System.out.println("");

        int i=0;
        while(i<log.size())
        {
            System.out.println(log.get(i));
        }

        System.out.println("");
        System.out.println("LOG END");
    }

    public void stackLog(ArrayList<String> temp)
    {
        int i=0;
        while(i<temp.size()) {
            log.add(temp.get(i));
            i++;
        }
    }

}
