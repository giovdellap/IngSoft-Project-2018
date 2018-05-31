package it.polimi.ingsw.client.Launcher;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.DemoMode.DemoApplication;
import it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX.JavaFXApplication;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import javafx.fxml.FXML;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.control.Menu;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class LauncherController
{
    private static int mode = 0; // 1 = SP, 2 = MP, 3 = DEMO
    private static int connection = 0;// 1 = SOCKET, 2 = RMI
    private static int graphicMode = 0;// 1 = GUI, 2 = CLI
    private static String ip ="";

    private boolean hasDone=false;

    @FXML
    private TextField ipField;

    private void itsAnAlpha()
    {
        connection = 1;
        graphicMode = 1;
    }

    public boolean checkDone()
    {
        System.out.println("checkDone: "+hasDone);
        return hasDone;
    }

    @FXML
    private void enterDemoMode(ActionEvent demoPressed) throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException {

        itsAnAlpha();
        mode=3;
        System.out.println("demo pressed");

    }

    @FXML
    private void enterMPMode(ActionEvent mpPressed) throws GenericInvalidArgumentException, InvalidIntArgumentException, IOException {
        itsAnAlpha();
        mode=2;
        System.out.println("mpPressed");
    }


    @FXML
    private void setIP(ActionEvent ipEntered)
    {
        ip = ipField.getText();
    }

    public String getIp()
    {
        return ip;
    }


    public int getMode()
    {
        return mode;
    }

    public int getConnection()
    {
        return connection;
    }

    public int getGraphicMode()
    {
        return graphicMode;
    }



}
