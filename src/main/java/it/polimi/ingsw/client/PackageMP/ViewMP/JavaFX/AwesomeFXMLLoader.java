package it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.util.BuilderFactory;

import java.io.IOException;

public class AwesomeFXMLLoader extends FXMLLoader
{
    public void setUsernameScene() throws IOException {
        this.setLocation(getClass().getClassLoader().getResource("Initialization/UsernameInsertion/usernameScene.fxml"));
        this.setRoot(getClass().getClassLoader().getResource("Initialization/UsernameInsertion/usernameScene.fxml"));
    }

    public void setWaitScene1() throws IOException {
        this.setLocation(getClass().getClassLoader().getResource("WaitScenes/waitScene1.fxml"));
        this.setRoot(getClass().getClassLoader().getResource("WaitScenes/waitScene1.fxml"));
    }

    public void setModelInitializationScene() throws IOException {
        this.setLocation(getClass().getClassLoader().getResource("ModelInitialization/initializationScene.fxml"));
    }

    public void setWait2Scene() throws IOException {
        this.setLocation(getClass().getClassLoader().getResource("WaitScenes/waitScene2.fxml"));
    }

    public void setGameScene() throws IOException {
        this.setLocation(getClass().getClassLoader().getResource("gameScene.fxml"));
    }
}
