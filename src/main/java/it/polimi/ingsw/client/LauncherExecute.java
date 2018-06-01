package it.polimi.ingsw.client;

import it.polimi.ingsw.client.DemoMode.DemoApplication;
import it.polimi.ingsw.client.PackageMP.MPExecute;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.CLIToolsManager;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.*;

public class LauncherExecute extends Application
{

    public Boolean hasDone=false;

    private int[] settings;// temp[0] = mode, temp[1] = connection, temp[2] = graphics
    private String ip;



    public void start(final Stage stage) throws Exception
    {
        BufferedReader inKeyboard;
        PrintWriter outVideo;
        CLIToolsManager cliToolsManager;
        settings[1]=1;
        settings[2]=2;

        inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        cliToolsManager = new CLIToolsManager();

        outVideo.println();
        outVideo.println();
        outVideo.println(cliToolsManager.simpleQuestionsMaker("PREMI S PER GIOCATORE SINGOLO, M PER MULTIGIOCATORE", 40, true));
        outVideo.flush();
        String msgIN = inKeyboard.readLine();
        if(msgIN.charAt(0)=='M')
        {
            settings[0] = 2;
            outVideo.println(cliToolsManager.simpleQuestionsMaker("INSERISCI INDIRIZZO IP", 40, true));
            outVideo.flush();
            ip = inKeyboard.readLine();

            MPExecute mpExecute = new MPExecute(ip, settings);
            mpExecute.start(stage);
        }

    }

    public LauncherExecute()
    {
        settings = new int[3];
        settings[0] = 0;
        settings[1] = 0;
        settings[2] = 0;
    }

    public void done()
    {
        hasDone=true;
    }


    public void setSettings(int setMode, int conn, int graph)
    {
        settings[0]=setMode;
        settings[1]=conn;
        settings[2]=graph;
    }

    public int[] returnSettings()
    {
        return settings;
    }

    public int[] go()
    {
        LauncherExecute.launch(LauncherExecute.class);
        return settings;
    }
}
