package it.polimi.ingsw.client;


import it.polimi.ingsw.client.DemoMode.DemoApplication;
import it.polimi.ingsw.client.Launcher.LauncherExecute;
import it.polimi.ingsw.client.Loggers.MajorLogger;
import it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX.JavaFXApplication;
import javafx.stage.Stage;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main
{
    //settings
    private static int mode =0;// 1 = SP, 2 = MP, 3 = DEMO
    private static int connection=0;
    private static int graphicsMode=0;
    private static URL mainURL;


    public static void main(String[] args) throws Exception {

        MajorLogger logger =new MajorLogger();
        logger.majorLog("Main logger operative");

        LauncherExecute launcherExe = new LauncherExecute();
        int[] temp = launcherExe.go();


    }



}