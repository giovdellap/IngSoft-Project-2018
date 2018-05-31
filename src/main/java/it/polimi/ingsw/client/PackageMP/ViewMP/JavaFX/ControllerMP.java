package it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.Player;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;

import java.util.List;

public class ControllerMP
{
    /*
    //MY SCHEME LABELS

    @FXML
    private Label myScheme00;
    @FXML
    private Label myScheme01;
    @FXML
    private Label myScheme02;
    @FXML
    private Label myScheme03;
    @FXML
    private Label myScheme04;
    @FXML
    private Label myScheme10;
    @FXML
    private Label myScheme11;
    @FXML
    private Label myScheme12;
    @FXML
    private Label myScheme13;
    @FXML
    private Label myScheme14;
    @FXML
    private Label myScheme20;
    @FXML
    private Label myScheme21;
    @FXML
    private Label myScheme22;
    @FXML
    private Label myScheme23;
    @FXML
    private Label myScheme24;
    @FXML
    private Label myScheme30;
    @FXML
    private Label myScheme31;
    @FXML
    private Label myScheme32;
    @FXML
    private Label myScheme33;
    @FXML
    private Label myScheme34;

    //OPPONENT 1 SCHEME LABELS

    @FXML
    private Label opp1Scheme00;
    @FXML
    private Label opp1Scheme01;
    @FXML
    private Label opp1Scheme02;
    @FXML
    private Label opp1Scheme03;
    @FXML
    private Label opp1Scheme04;
    @FXML
    private Label opp1Scheme10;
    @FXML
    private Label opp1Scheme11;
    @FXML
    private Label opp1Scheme12;
    @FXML
    private Label opp1Scheme13;
    @FXML
    private Label opp1Scheme14;
    @FXML
    private Label opp1Scheme20;
    @FXML
    private Label opp1Scheme21;
    @FXML
    private Label opp1Scheme22;
    @FXML
    private Label opp1Scheme23;
    @FXML
    private Label opp1Scheme24;
    @FXML
    private Label opp1Scheme30;
    @FXML
    private Label opp1Scheme31;
    @FXML
    private Label opp1Scheme32;
    @FXML
    private Label opp1Scheme33;
    @FXML
    private Label opp1Scheme34;

    //OPPONENT 2 SCHEME LABELS

    @FXML
    private Label opp2Scheme00;
    @FXML
    private Label opp2Scheme01;
    @FXML
    private Label opp2Scheme02;
    @FXML
    private Label opp2Scheme03;
    @FXML
    private Label opp2Scheme04;
    @FXML
    private Label opp2Scheme10;
    @FXML
    private Label opp2Scheme11;
    @FXML
    private Label opp2Scheme12;
    @FXML
    private Label opp2Scheme13;
    @FXML
    private Label opp2Scheme14;
    @FXML
    private Label opp2Scheme20;
    @FXML
    private Label opp2Scheme21;
    @FXML
    private Label opp2Scheme22;
    @FXML
    private Label opp2Scheme23;
    @FXML
    private Label opp2Scheme24;
    @FXML
    private Label opp2Scheme30;
    @FXML
    private Label opp2Scheme31;
    @FXML
    private Label opp2Scheme32;
    @FXML
    private Label opp2Scheme33;
    @FXML
    private Label opp2Scheme34;

    //OPPONENT 3 SCHEME LABELS

    @FXML
    private Label opp3Scheme00;
    @FXML
    private Label opp3Scheme01;
    @FXML
    private Label opp3Scheme02;
    @FXML
    private Label opp3Scheme03;
    @FXML
    private Label opp3Scheme04;
    @FXML
    private Label opp3Scheme10;
    @FXML
    private Label opp3Scheme11;
    @FXML
    private Label opp3Scheme12;
    @FXML
    private Label opp3Scheme13;
    @FXML
    private Label opp3Scheme14;
    @FXML
    private Label opp3Scheme20;
    @FXML
    private Label opp3Scheme21;
    @FXML
    private Label opp3Scheme22;
    @FXML
    private Label opp3Scheme23;
    @FXML
    private Label opp3Scheme24;
    @FXML
    private Label opp3Scheme30;
    @FXML
    private Label opp3Scheme31;
    @FXML
    private Label opp3Scheme32;
    @FXML
    private Label opp3Scheme33;
    @FXML
    private Label opp3Scheme34;

    //MY SCHEME IMAGES

    @FXML
    private ImageView myScheme00Img;
    @FXML
    private ImageView myScheme01Img;
    @FXML
    private ImageView myScheme02Img;
    @FXML
    private ImageView myScheme03Img;
    @FXML
    private ImageView myScheme04Img;
    @FXML
    private ImageView myScheme10Img;
    @FXML
    private ImageView myScheme11Img;
    @FXML
    private ImageView myScheme12Img;
    @FXML
    private ImageView myScheme13Img;
    @FXML
    private ImageView myScheme14Img;
    @FXML
    private ImageView myScheme20Img;
    @FXML
    private ImageView myScheme21Img;
    @FXML
    private ImageView myScheme22Img;
    @FXML
    private ImageView myScheme23Img;
    @FXML
    private ImageView myScheme24Img;
    @FXML
    private ImageView myScheme30Img;
    @FXML
    private ImageView myScheme31Img;
    @FXML
    private ImageView myScheme32Img;
    @FXML
    private ImageView myScheme33Img;
    @FXML
    private ImageView myScheme34Img;

    //OPPONENT 1 SCHEME IMAGES

    @FXML
    private ImageView opp1Scheme00Img;
    @FXML
    private ImageView opp1Scheme01Img;
    @FXML
    private ImageView opp1Scheme02Img;
    @FXML
    private ImageView opp1Scheme03Img;
    @FXML
    private ImageView opp1Scheme04Img;
    @FXML
    private ImageView opp1Scheme10Img;
    @FXML
    private ImageView opp1Scheme11Img;
    @FXML
    private ImageView opp1Scheme12Img;
    @FXML
    private ImageView opp1Scheme13Img;
    @FXML
    private ImageView opp1Scheme14Img;
    @FXML
    private ImageView opp1Scheme20Img;
    @FXML
    private ImageView opp1Scheme21Img;
    @FXML
    private ImageView opp1Scheme22Img;
    @FXML
    private ImageView opp1Scheme23Img;
    @FXML
    private ImageView opp1Scheme24Img;
    @FXML
    private ImageView opp1Scheme30Img;
    @FXML
    private ImageView opp1Scheme31Img;
    @FXML
    private ImageView opp1Scheme32Img;
    @FXML
    private ImageView opp1Scheme33Img;
    @FXML
    private ImageView opp1Scheme34Img;

    //OPPONENT 2 SCHEME IMAGES

    @FXML
    private ImageView opp2Scheme00Img;
    @FXML
    private ImageView opp2Scheme01Img;
    @FXML
    private ImageView opp2Scheme02Img;
    @FXML
    private ImageView opp2Scheme03Img;
    @FXML
    private ImageView opp2Scheme04Img;
    @FXML
    private ImageView opp2Scheme10Img;
    @FXML
    private ImageView opp2Scheme11Img;
    @FXML
    private ImageView opp2Scheme12Img;
    @FXML
    private ImageView opp2Scheme13Img;
    @FXML
    private ImageView opp2Scheme14Img;
    @FXML
    private ImageView opp2Scheme20Img;
    @FXML
    private ImageView opp2Scheme21Img;
    @FXML
    private ImageView opp2Scheme22Img;
    @FXML
    private ImageView opp2Scheme23Img;
    @FXML
    private ImageView opp2Scheme24Img;
    @FXML
    private ImageView opp2Scheme30Img;
    @FXML
    private ImageView opp2Scheme31Img;
    @FXML
    private ImageView opp2Scheme32Img;
    @FXML
    private ImageView opp2Scheme33Img;
    @FXML
    private ImageView opp2Scheme34Img;

    //OPPONENT 3 SCHEME LABELS

    @FXML
    private ImageView opp3Scheme00Img;
    @FXML
    private ImageView opp3Scheme01Img;
    @FXML
    private ImageView opp3Scheme02Img;
    @FXML
    private ImageView opp3Scheme03Img;
    @FXML
    private ImageView opp3Scheme04Img;
    @FXML
    private ImageView opp3Scheme10Img;
    @FXML
    private ImageView opp3Scheme11Img;
    @FXML
    private ImageView opp3Scheme12Img;
    @FXML
    private ImageView opp3Scheme13Img;
    @FXML
    private ImageView opp3Scheme14Img;
    @FXML
    private ImageView opp3Scheme20Img;
    @FXML
    private ImageView opp3Scheme21Img;
    @FXML
    private ImageView opp3Scheme22Img;
    @FXML
    private ImageView opp3Scheme23Img;
    @FXML
    private ImageView opp3Scheme24Img;
    @FXML
    private ImageView opp3Scheme30Img;
    @FXML
    private ImageView opp3Scheme31Img;
    @FXML
    private ImageView opp3Scheme32Img;
    @FXML
    private ImageView opp3Scheme33Img;
    @FXML
    private ImageView opp3Scheme34Img;

    //MY LABELS

    @FXML
    private Label myUsername;
    @FXML
    private Label myTokens;

    //OPPONENTS' LABELS

    @FXML
    private Label opp1Username;
    @FXML
    private Label opp1Tokens;
    @FXML
    private Label opp2Username;
    @FXML
    private Label opp2Tokens;
    @FXML
    private Label opp3Username;
    @FXML
    private Label opp3Tokens;

*/
    //PLAYERS' LABELS

    @FXML
    private Label myName;
    @FXML
    private Label myTokens;
    @FXML
    private Label opp1Name;
    @FXML
    private Label opp1Tokens;
    @FXML
    private Label opp2Name;
    @FXML
    private Label opp3Name;
    @FXML
    private Label opp3Tokens;

    //PLAYERS' SCHEMES' LABELS

    @FXML
    private Label[][] mySchemeLabel;
    private Label[][] opp1SchemeLabel;
    private Label[][] opp2SchemeLabel;
    private Label[][] opp3SchemeLabel;

    //PLAYERS' SCHEMES' IMAGEVIEWS

    private ImageView[][] mySchemeImgView;
    private ImageView[][] opp1SchemeImgView;
    private ImageView[][] opp2SchemeImgView;
    private ImageView[][] opp3SchemeImgView;


    public void setUpScheme(int player, SchemeCardMP sc) throws InvalidIntArgumentException {
        mySchemeLabel = new Label[4][5];
        mySchemeImgView = new ImageView[4][5];
        for(int x=0;x<4;x++)
            for(int y=0;y<5;y++)
            {
                if(sc.getCell(sc.getfb(), x, y)!=0)
                {
                    int temp = sc.getCell(sc.getfb(), x, y);
                    if(temp>0&&temp<6)
                    {
                        System.out.println("Ciao");
                    }
                }
            }
    }

    public void setUpPlayers(Player[] players, int id)
    {
        int currentOpp=1;

        for(int i=0;i<players.length;i++)
        {
            if(i==id)
            {
                myName.setText(players[i].getName());
            }
            else
            {

            }
        }
    }

}
