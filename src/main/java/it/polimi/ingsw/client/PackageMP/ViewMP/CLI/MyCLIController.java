package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

public class MyCLIController extends AnchorPane
{
    @FXML private Label myLabel;
    @FXML private TextField myTextField;

    public String refresh;
    private PrinterMaker printerMaker;


    @FXML public void textInserted(ActionEvent event)
    {
        if(event.getSource()==myTextField)
            refresh=myTextField.getText();
    }

    @FXML
    public void usernameInsertion() {
        myLabel.setText(printerMaker.getUsernameInsertion());
    }

    public void usernameInsertionAgain(String badUsername)
    {
        myLabel.setText(printerMaker.getUsernameInsertionAgain(badUsername));
    }

    public void setUp()
    {
        printerMaker = new PrinterMaker();
    }

    public String getRefresh()
    {
        return refresh;
    }
}
