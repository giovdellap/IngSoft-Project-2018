package it.polimi.ingsw.client.Launcher;

import it.polimi.ingsw.client.DemoMode.DemoApplication;
import it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX.JavaFXApplication;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Menu;
import javafx.stage.Stage;

import java.io.IOException;

public class LauncherExecute extends Application
{

    public LauncherController controller;
    public Boolean hasDone=false;

    private int[] settings;// temp[0] = mode, temp[1] = connection, temp[2] = graphics



    public void start(final Stage stage) throws Exception
    {

        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("Launcher.fxml"));
        Parent root = loader.load();

        controller = loader.getController();

        Scene scene = new Scene(root);
        stage.setTitle("SAGRADA");
        stage.setScene(scene);
        stage.show();

        javafx.event.EventHandler<ActionEvent> buttonHandler = new javafx.event.EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                setSettings(controller.getMode(), controller.getConnection(), controller.getGraphicMode());
                done();
                if(settings[0]==2&&(!controller.getIp().equals("")))
                {
                    JavaFXApplication fxApp = new JavaFXApplication(controller.getIp());
                    try {
                        fxApp.start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                if(settings[0]==3&&(!controller.getIp().equals("")))
                {
                    DemoApplication demoApp = new DemoApplication();
                    try {
                        demoApp.start(stage);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        root.addEventHandler(ActionEvent.ACTION, buttonHandler);

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
