package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.AwesomeFXMLLoader;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.MyCLIController;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GraphicsManager
{
    public MinorLogger gmLogger;

    private int graphics;//1 = GUI, 2 = CLI

    private FXMLLoader loader;
    private Scene scene;
    private Parent root;

    //controllers
    private MyCLIController cliController;

    public GraphicsManager(int graphics) throws GenericInvalidArgumentException {
        this.graphics=graphics;
        loader= new AwesomeFXMLLoader();
        gmLogger = new MinorLogger();
        gmLogger.minorLog("graphics operative");
    }

    public String askUsername(Stage stage) throws IOException, GenericInvalidArgumentException {
        if(graphics==2)
        {
            loader = new FXMLLoader(getClass().getClassLoader().getResource("CLI.fxml"));
            cliController = new MyCLIController();
            loader.setController(cliController);
            cliController.setUp();
            root = loader.load();
            cliController.usernameInsertion();
            stage.setScene(new Scene(root));
            stage.show();

            gmLogger.minorLog("Username scene showed");

            EventHandler<ActionEvent> usernameHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        gmLogger.minorLog("Username inserted: "+cliController.getRefresh());
                    } catch (GenericInvalidArgumentException e) {
                        e.printStackTrace();
                    }

                }
            };
            root.addEventHandler(ActionEvent.ACTION, usernameHandler);
            return cliController.getRefresh();
        }
        else
            return null;
    }

    public String askUsernameAgain(Stage stage, String badUsername) throws IOException, GenericInvalidArgumentException {
        if(graphics==2)
        {
            cliController.usernameInsertionAgain(badUsername);
            root = loader.load();
            stage.setScene(new Scene(root));
            gmLogger.minorLog("Username scene showed");

            EventHandler<ActionEvent> usernameHandler = new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    try {
                        gmLogger.minorLog("Username inserted: "+cliController.getRefresh());
                    } catch (GenericInvalidArgumentException e) {
                        e.printStackTrace();
                    }

                }
            };
            root.addEventHandler(ActionEvent.ACTION, usernameHandler);
            return cliController.getRefresh();
        }
        else
            return null;
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
