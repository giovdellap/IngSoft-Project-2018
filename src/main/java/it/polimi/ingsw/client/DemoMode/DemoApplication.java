package it.polimi.ingsw.client.DemoMode;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.DemoMode.MoveDemo.MoveDemoExecute;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.beans.EventHandler;
import java.io.IOException;
import java.net.Socket;

public class DemoApplication extends Application
{
    private String ip;

    //GRAPHICS
    private FXMLLoader loader;
    private SelectDemoController selectController;
    private Parent root;
    private Scene currentScene;

    public DemoApplication(String s)
    {
        ip=s;
    }

    @Override
    public void start(Stage stage) throws Exception
    {
        //Starting username scene
        loader = new FXMLLoader(getClass().getClassLoader().getResource("selectDemo.fxml"));
        root = loader.load();
        selectController = loader.getController();
        currentScene = new Scene(root);
        stage.setTitle("DEMO PANEL");
        stage.setScene(currentScene);
        stage.show();

        javafx.event.EventHandler<ActionEvent> demoHandler = new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                switch (selectController.getDemo())
                {
                    case (1):
                    {
                        MoveDemoExecute demo1 = new MoveDemoExecute(ip);
                    }
                }
            }
        };

        //root.addEventHandler(ActionEvent, demoHandler);

    }
}
