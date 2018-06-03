package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.BeautifulCLI;
import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphicsManager
{
    public MinorLogger gmLogger;

    private int graphics;//1 = GUI, 2 = CLI

    private BeautifulCLI beautifulCLI;

    private int [] toolsId;

    public GraphicsManager(int graphics) throws GenericInvalidArgumentException {

        toolsId = new int[3];

        this.graphics=graphics;
        gmLogger = new MinorLogger();
        gmLogger.minorLog("graphics operative");
        if(graphics==2)
        {
            beautifulCLI = new BeautifulCLI();
        }
    }

    public String askUsername(Stage stage) throws IOException, GenericInvalidArgumentException {
        if(graphics==2)
            return beautifulCLI.askUsername();
        else
            return null;
    }

    public String askUsernameAgain(Stage stage, String badUsername) throws IOException, GenericInvalidArgumentException {
        if(graphics==2)
            return beautifulCLI.askUsernameAgain(badUsername);
        else return null;

    }

    public void waitForPlayers()
    {
        if(graphics==2)
            beautifulCLI.setWaitScene();
    }

    public SchemeCardMP getSelectedScheme(SchemeCardMP scheme1, SchemeCardMP scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs) throws InvalidIntArgumentException, IOException {
        if(graphics==2)
             return beautifulCLI.setInitializationScene(scheme1, scheme2, username, privObj, pubObjs,toolsId);
        else
            return null;
    }

    public void waitForPlayers2()
    {
        if(graphics==2)
            beautifulCLI.setWaitScene2();
    }
/*
    public int[] getSelectedScheme(Stage stage, ModelManagerMP mm)
    {
        if(graphics==2)
        {
            cliController.setSelectionScene();
            //missing part

            EventHandler<ActionEvent> schemeHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    if(Integer.parseInt(cliController.getChangedText())>0)
                }
            }

        }
    }
    */
}
