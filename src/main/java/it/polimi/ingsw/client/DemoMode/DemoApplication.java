package it.polimi.ingsw.client.DemoMode;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class DemoApplication extends Application
{
    //GRAPHICS
    private FXMLLoader loader;
    private SelectDemoController selectController;
    private Parent root;
    private Scene currentScene;

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

    }
}
