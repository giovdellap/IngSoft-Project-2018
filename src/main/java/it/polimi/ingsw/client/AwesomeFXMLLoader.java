package it.polimi.ingsw.client;

import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.MyCLIController;
import javafx.fxml.FXMLLoader;

public class AwesomeFXMLLoader extends FXMLLoader
{
    public void setCLI()
    {
        this.setLocation(MyCLIController.class.getClassLoader().getResource("CLI.fxml"));
    }
}
