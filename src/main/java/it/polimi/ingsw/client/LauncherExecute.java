package it.polimi.ingsw.client;
import it.polimi.ingsw.client.PackageMP.MPExecute;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.CLIToolsManager;
import javafx.application.Application;

import javafx.stage.Stage;

import java.io.*;

public class LauncherExecute extends Application
{


    private int[] settings;// temp[0] = mode, temp[1] = connection, temp[2] = graphics
    private String ip;

    /**
     * starts the CLI
     * @param stage
     * @throws Exception
     */
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

        outVideo.println("PREMI Y PER DEBUG MODE, N PER LA SHOW MODE");
        String msgIn = inKeyboard.readLine();
        if(msgIn.equals("Y"))
        {
            ip="localhost";
            settings[0]=2;
            MPExecute mpExecute = new MPExecute(ip, settings);
            mpExecute.start(stage);
        }
        else {
            boolean flag=false;
            while (!flag) {
                outVideo.println();
                outVideo.println();
                outVideo.println(cliToolsManager.simpleQuestionsMaker("PREMI 1 PER INIZIARE UNA PARTITA, 2 PER CREARE UNO SCHEMA PERSONALIZZATO", 40, true));
                outVideo.flush();
                String msgIN = inKeyboard.readLine();
                if (msgIN.charAt(0) == '1') {
                    settings[0] = 2;
                    outVideo.println(cliToolsManager.simpleQuestionsMaker("INSERISCI INDIRIZZO IP", 40, true));
                    outVideo.flush();
                    ip = inKeyboard.readLine();

                    MPExecute mpExecute = new MPExecute(ip, settings);
                    mpExecute.start(stage);
                }
                if (msgIN.charAt(0)=='2')
                {

                }
            }
        }
    }

    public LauncherExecute()
    {
        settings = new int[3];
        settings[0] = 0;
        settings[1] = 0;
        settings[2] = 0;
    }

    public int[] go()
    {
        LauncherExecute.launch(LauncherExecute.class);
        return settings;
    }
}
