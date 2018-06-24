package it.polimi.ingsw.commons;

public class SimpleLogger
{
    private boolean connectionDebug = false; //1
    private boolean graphicsDebug=false; //2
    private boolean serverMatchDebug=false; //3

    private boolean debugMode;

    private String debugType="";

    public SimpleLogger(int i, boolean debugMode)
    {
        this.debugMode=debugMode;

        switch (i){
            case (1): {
                connectionDebug = true;
                debugType="CONNECTION";
                break;
            }
            case(2):
            {
                graphicsDebug=true;
                debugType="GRAPHICS";
                break;
            }
            case(3):
            {
                serverMatchDebug=true;
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

    public void printDebugType()
    {
        if(debugMode)
            System.out.println(debugType);
    }

    public void printDebugStarter(String s)
    {
        if(debugMode)
        {
            System.out.println();
            System.out.println(debugType+" "+s);
            System.out.println();
        }
    }

    public void printNBlankLines(int n)
    {
        for(int i=0;i<n;i++)
            System.out.println(" ");
    }

}
