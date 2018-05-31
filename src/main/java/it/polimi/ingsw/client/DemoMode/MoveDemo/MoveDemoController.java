package it.polimi.ingsw.client.DemoMode.MoveDemo;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import javafx.beans.DefaultProperty;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.List;

public class MoveDemoController
{
    @FXML private Label schemeName;
    @FXML private Label tokens;

    //-----SCHEMECARD---

    //LABELS
    @FXML private Label label00;
    @FXML private Label label01;
    @FXML private Label label02;
    @FXML private Label label03;
    @FXML private Label label04;
    @FXML private Label label10;
    @FXML private Label label11;
    @FXML private Label label12;
    @FXML private Label label13;
    @FXML private Label label14;
    @FXML private Label label20;
    @FXML private Label label21;
    @FXML private Label label22;
    @FXML private Label label23;
    @FXML private Label label24;
    @FXML private Label label30;
    @FXML private Label label31;
    @FXML private Label label32;
    @FXML private Label label33;
    @FXML private Label label34;

    //IMAGEVIEW
    @FXML private ImageView imgView00;
    @FXML private ImageView imgView01;
    @FXML private ImageView imgView02;
    @FXML private ImageView imgView03;
    @FXML private ImageView imgView04;
    @FXML private ImageView imgView10;
    @FXML private ImageView imgView11;
    @FXML private ImageView imgView12;
    @FXML private ImageView imgView13;
    @FXML private ImageView imgView14;
    @FXML private ImageView imgView20;
    @FXML private ImageView imgView21;
    @FXML private ImageView imgView22;
    @FXML private ImageView imgView23;
    @FXML private ImageView imgView24;
    @FXML private ImageView imgView30;
    @FXML private ImageView imgView31;
    @FXML private ImageView imgView32;
    @FXML private ImageView imgView33;
    @FXML private ImageView imgView34;

    //IMAGES
    @FXML private Image img00;
    @FXML private Image img01;
    @FXML private Image img02;
    @FXML private Image img03;
    @FXML private Image img04;
    @FXML private Image img10;
    @FXML private Image img11;
    @FXML private Image img12;
    @FXML private Image img13;
    @FXML private Image img14;
    @FXML private Image img20;
    @FXML private Image img21;
    @FXML private Image img22;
    @FXML private Image img23;
    @FXML private Image img24;
    @FXML private Image img30;
    @FXML private Image img31;
    @FXML private Image img32;
    @FXML private Image img33;
    @FXML private Image img34;

    //DRAFTPOOL
    @FXML private ImageView draftView0;
    @FXML private ImageView draftView1;
    @FXML private ImageView draftView2;
    @FXML private ImageView draftView3;
    @FXML private ImageView draftView4;
    @FXML private ImageView draftView5;
    @FXML private ImageView draftView6;
    @FXML private ImageView draftView7;
    @FXML private ImageView draftView8;


    public void setLabels(SchemeCardMP scheme) throws InvalidIntArgumentException {
        schemeName.setText(scheme.getName(scheme.getfb()));
        tokens.setText(Integer.toString(scheme.getDiff(scheme.getfb())));


        for(int x=0;x<4;x++)
            for(int y=0;y<5;y++)
            {

            }
    }
}
