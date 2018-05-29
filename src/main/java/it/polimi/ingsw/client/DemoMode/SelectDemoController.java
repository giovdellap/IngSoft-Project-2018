package it.polimi.ingsw.client.DemoMode;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class SelectDemoController
{
    private int demo=0;

    @FXML private Button moveButton;
    @FXML private Button firstMoveButton;
    @FXML private Button tool3Button;

    @FXML
    private void selectDemo(ActionEvent event)
    {
        if(event.getSource()==moveButton)
            demo=1;
        if(event.getSource()==firstMoveButton)
            demo=2;
        if(event.getSource()==tool3Button)
            demo=5;
    }

    public int getDemo()
    {
        return demo;
    }
}
