package it.polimi.ingsw.commons;

public class SimpleLogger
{

    private boolean debugMode;

    private String debugType="";

    public SimpleLogger(int i, boolean debugMode)
    {
        this.debugMode=debugMode;

        switch (i){
            case (1): {
                debugType="CONNECTION";
                break;
            }
            case(2):
            {
                debugType="GRAPHICS";
                break;
            }
            case(3):
            {
                debugType="MATCH";
                break;
            }
        }
    }

    public void log(String s)
    {
        System.out.println(s);
    }

    public void debugLog(String s)
    {
        if(debugMode)
            System.out.println(debugType+" : "+s);

    }


    public void printNBlankLines(int n)
    {
        for(int i=0;i<n;i++)
            System.out.println(" ");
    }

}
