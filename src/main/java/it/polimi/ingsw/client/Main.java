package it.polimi.ingsw.client;


import it.polimi.ingsw.client.Loggers.MajorLogger;


public class Main
{

    /**
     * MAIN Constructor
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        MajorLogger logger =new MajorLogger();
        logger.majorLog("Main logger operative");

        LauncherExecute launcherExe = new LauncherExecute();
        launcherExe.go();

    }


}