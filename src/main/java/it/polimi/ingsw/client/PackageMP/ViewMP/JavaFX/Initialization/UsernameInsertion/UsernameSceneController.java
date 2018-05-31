package it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX.Initialization.UsernameInsertion;

import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.control.TextField;

public class UsernameSceneController
{
    public String tempUsername="";

    @FXML
    private TextField userName;

    @FXML
    public void getUsernameInsertion(ActionEvent event)
    {
        tempUsername = userName.getText();
        System.out.println("testo inserito: "+tempUsername);
    }

    public String getTempUsername()
    {
        return tempUsername;

    }
}
