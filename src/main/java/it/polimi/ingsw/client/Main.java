package it.polimi.ingsw.client;


import it.polimi.ingsw.client.Loggers.MajorLogger;


public class Main
{
    //settings
    private static int mode =0;// 1 = SP, 2 = MP, 3 = DEMO
    private static int connection=0;
    private static int graphicsMode=0;


    public static void main(String[] args) throws Exception {

        MajorLogger logger =new MajorLogger();
        logger.majorLog("Main logger operative");

        LauncherExecute launcherExe = new LauncherExecute();
        launcherExe.go();

    }


}