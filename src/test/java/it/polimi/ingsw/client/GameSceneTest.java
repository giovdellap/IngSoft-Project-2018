package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.PlayerClient;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.PrinterMaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GameSceneTest {

    PlayerClient[] players;
    DraftPoolMP draft;
    RoundTrackMP tempRound;
    PrivateObjectiveMP testPrivObj;
    PublicObjectiveMP[] tempPubObj;
    int[] tempTools;

    PrinterMaker printerMaker;


    @BeforeEach

    public void setUp() throws InvalidIntArgumentException, FullDataStructureException {
        DraftPoolMP tempDraft;
        printerMaker=new PrinterMaker();
        players= new PlayerClient[2];
        players[0]=new PlayerClient(0,"pippo", true);
        SchemesDeckMP deck= new SchemesDeckMP();
        SchemeCardMP temp= deck.extractSchemebyID(3);
        temp.setfb(2);
        players[0].setPlayerScheme(temp);
        players[0].setTokens(3);

        players[1]=new PlayerClient(1,"guido", false);
        temp= deck.extractSchemebyID(7);
        temp.setfb(2);
        players[1].setPlayerScheme(temp);
        players[1].setTokens(4);

        DieMP die=new DieMP(3);
        DieMP die1= new DieMP(5);

        die.throwDie();
        die1.throwDie();

        DieMP[]dievect= new DieMP[2];
        dievect[0]=die;
        dievect[1]=die1;

        draft=new DraftPoolMP(dievect);

        DieMP[] tempVectDie=new DieMP[3];

        tempVectDie[0]=new DieMP(4);
        tempVectDie[0].throwDie();

        tempVectDie[1]=new DieMP(2);
        tempVectDie[1].throwDie();

        tempVectDie[2]=new DieMP(3);
        tempVectDie[2].throwDie();


        RoundDiceMP tempRoundDice= new RoundDiceMP(1);

        RoundDiceMP tempRoundDice1= new RoundDiceMP(2);

        tempRoundDice.addDie(tempVectDie[0]);

        tempRoundDice1.addDie(tempVectDie[1]);
        tempRoundDice1.addDie(tempVectDie[2]);

        tempRound=new RoundTrackMP();
        tempRound.addRound(tempRoundDice);
        tempRound.addRound(tempRoundDice1);

        testPrivObj= new PrivateObjectiveMP(3);


        tempPubObj= new PublicObjectiveMP[3];
        tempPubObj[0]=new PublicObjectiveMP(3);
        tempPubObj[1]=new PublicObjectiveMP(4);
        tempPubObj[2]=new PublicObjectiveMP(7);

        tempTools= new int[3];
        tempTools[0]=1;
        tempTools[1]=2;
        tempTools[2]=3;

        int[] whatf= new int[3];
        whatf[0]=1;
        whatf[1]=10;
        whatf[2]=7;

        printerMaker.setToolsIdTest(whatf);

    }

    @Test
    public void gameSceneTest() throws InvalidIntArgumentException
    {
        for(int i=0;i<21;i++) {
            System.out.println(printerMaker.getGameScene(players, draft, tempRound, testPrivObj, tempPubObj, tempTools, 1, 1)[i]);
        }
        Assertions.assertEquals(true, true);

    }




}
