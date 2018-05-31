package it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX.Initialization.ModelInitialization;

import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PrivateObjectiveMP;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.net.URL;

public class InitializationSceneController
{

    private int schemeSelected=0;
    private int fbSelected=0;

    private int schemeID1;
    private int schemeID2;

    //FXML COMPONENTS

    @FXML
    private ImageView sc1f;

    @FXML
    private ImageView sc1b;

    @FXML
    private ImageView sc2f;

    @FXML
    private ImageView sc2b;

    @FXML
    private Button sc1fButton;

    @FXML
    private Button sc1bButton;

    @FXML
    private Button sc2fButton;

    @FXML
    private Button sc2bButton;

    @FXML
    private ImageView prObj;

    @FXML
    private ImageView pubObj1;

    @FXML
    private ImageView pubObj2;

    @FXML
    private ImageView pubObj3;


    //IMAGES

    private Image sc1fImage;
    private Image sc2fImage;
    private Image sc1bImage;
    private Image sc2bImage;

    private Image prObjImage;
    private Image pubObj1Image;
    private Image pubObj2Image;
    private Image pubObj3Image;

    //SELECTION METHODS

    @FXML
    private void schemeSelection(ActionEvent event)
    {
        if(event.getSource()==sc1fButton)
        {
            schemeSelected=schemeID1;
            fbSelected=1;
        }
        if(event.getSource()==sc1bButton)
        {
            schemeSelected=schemeID1;
            fbSelected=2;
        }
        if(event.getSource()==sc2fButton)
        {
            schemeSelected=schemeID2;
            fbSelected=1;
        }
        if(event.getSource()==sc2bButton)
        {
            schemeSelected=schemeID2;
            fbSelected=2;
        }
    }

    //RETURN METHOD

    public int getSelectedScheme()
    {
        return schemeSelected;
    }

    public int getFbSelected()
    {
        return fbSelected;
    }


    //LOADING IMAGES

    public void setPrObj(int id)
    {
        switch (id)
        {
            case 1:
            {
                prObjImage = new Image("priv1.svg");
                prObj.setImage(prObjImage);
                break;
            }
            case 2:
            {
                prObjImage = new Image("priv2.svg");
                prObj.setImage(prObjImage);
                break;
            }
            case 3:
            {
                prObjImage = new Image("priv3.svg");
                prObj.setImage(prObjImage);
                break;
            }
            case 4:
            {
                prObjImage = new Image("priv4.svg");
                prObj.setImage(prObjImage);
                break;
            }
            case 5:
            {
                prObjImage = new Image("priv5.svg");
                prObj.setImage(prObjImage);
                break;
            }
        }
    }

    public void setPublicObjectives(int id1, int id2, int id3)
    {
        pubObj1Image = new Image(returnPubObjURL(id1));
        pubObj2Image = new Image(returnPubObjURL(id2));
        pubObj3Image = new Image(returnPubObjURL(id3));
        pubObj1 = new ImageView(pubObj1Image);
        pubObj2 = new ImageView(pubObj2Image);
        pubObj3 = new ImageView(pubObj3Image);
    }

    private String returnPubObjURL(int id)
    {
        switch (id)
        {
            case 1:
                return "pub1.svg";
            case 2:
                return "pub2.svg";
            case 3:
                return "pub3.svg";
            case 4:
                return "pub4.svg";
            case 5:
                return "pub5.svg";
            case 6:
                return "pub6.svg";
            case 7:
                return "pub7.svg";
            case 8:
                return "pub8.svg";
            case 9:
                return "pub9.svg";
            case 10:
                return "pub10.svg";
        }
        return null;
    }

    public void setSchemes(int id1, int id2)
    {
        schemeID1 = id1;
        schemeID2 = id2;

        sc1fImage = new Image(returnSchemeURL(id1, 1));
        sc1f = new ImageView(sc1fImage);

        sc1bImage = new Image(returnSchemeURL(id1, 2));
        sc1b = new ImageView(sc1bImage);

        sc2fImage = new Image(returnSchemeURL(id2, 2));
        sc2f = new ImageView(sc2fImage);

        sc2bImage = new Image(returnSchemeURL(id2, 2));
        sc2b = new ImageView(sc2bImage);
    }

    private String returnSchemeURL(int id, int fb)
    {
        switch (id)
        {
            case 1:
            {
                if(fb==1)
                    return "scheme1f.svg";
                if(fb==2)
                    return "scheme1b.svg";
                break;
            }
            case 2:
            {
                if(fb==1)
                    return "scheme2f.svg";
                if(fb==2)
                    return "scheme2b.svg";
                break;
            }
            case 3:
            {
                if(fb==1)
                    return "scheme3f.svg";
                if(fb==2)
                    return "scheme3b.svg";
                break;
            }
            case 4:
            {
                if(fb==1)
                    return "scheme4f.svg";
                if(fb==2)
                    return "scheme4b.svg";
                break;
            }
            case 5:
            {
                if(fb==1)
                    return "scheme5f.svg";
                if(fb==2)
                    return "scheme5b.svg";
                break;
            }
            case 6:
            {
                if(fb==1)
                    return "scheme6f.svg";
                if(fb==2)
                    return "scheme6b.svg";
                break;
            }
            case 7:
            {
                if(fb==1)
                    return "scheme7f.svg";
                if(fb==2)
                    return "scheme7b.svg";
                break;
            }
            case 8:
            {
                if(fb==1)
                    return "scheme8f.svg";
                if(fb==2)
                    return "scheme8b.svg";
                break;
            }
            case 9:
            {
                if(fb==1)
                    return "scheme9f.svg";
                if(fb==2)
                    return "scheme9b.svg";
                break;
            }
            case 10:
            {
                if(fb==1)
                    return "scheme10f.svg";
                if(fb==2)
                    return "scheme10b.svg";
                break;
            }
            case 11:
            {
                if(fb==1)
                    return "scheme11f.svg";
                if(fb==2)
                    return "scheme11b.svg";
                break;
            }
            case 12:
            {
                if(fb==1)
                    return "scheme12f.svg";
                if(fb==2)
                    return "scheme12b.svg";
                break;
            }
        }
        return null;
    }



}
