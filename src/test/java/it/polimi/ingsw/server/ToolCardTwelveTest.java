package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardTwelve;
import  it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class ToolCardTwelveTest {

    ToolCardTwelve toolCardTwelveTest;
    SchemeCard schemeTest;
    SchemesDeck schemesDeck;
    RoundTrack trackTest;
    RoundDice dice;
    Die testDie1;
    Die testDie2;
    Die testDie3;
    Die testDie4;
    Die testDie5;
    Die testDie6;
    Die testDie7;
    Die testDie8;

    @BeforeEach
    public void setUp() throws GenericInvalidArgumentException, InvalidIntArgumentException, FullDataStructureException, FileNotFoundException {

        toolCardTwelveTest = new ToolCardTwelve();
        schemesDeck = new SchemesDeck();
        schemeTest = schemesDeck.extractSchemebyID(6);
        schemeTest.setfb(2);
        testDie1 = new Die(5);
        testDie2 = new Die(2);
        testDie3 = new Die(5);
        testDie3.setValue(1);
        testDie4 = new Die(4);
        testDie4.setValue(2);
        testDie5 = new Die(5);
        testDie5.setValue(4);
        testDie6 = new Die(5);
        testDie6.setValue(3);
        testDie7 = new Die(2);
        testDie8 = new Die(3);

        trackTest = new RoundTrack();
        dice = new RoundDice(3);
        dice.addDie(testDie1);
        dice.addDie(testDie2);
        trackTest.addRound(dice);

        toolCardTwelveTest.setTempScheme(schemeTest);
    }

    @Test
    public void checkToolCardTwelveOne() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;

        schemeTest.setDie(testDie3,0,3);
        schemeTest.setDie(testDie4,0,0);


        flag = toolCardTwelveTest.checkToolCardTwelve1Die(trackTest,0,0,schemeTest,0,3,1,0);

        Assertions.assertEquals(true,flag);

    }


    @Test
    public void checkToolCardTwelveTwo() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;

        schemeTest.setDie(testDie3,0,3);
        schemeTest.setDie(testDie4,0,4);
        schemeTest.setDie(testDie5,0,0);
        schemeTest.setDie(testDie6,1,0);

        flag = toolCardTwelveTest.checkToolCardTwelve2Dice(trackTest,0,1,0,0,1,0,schemeTest,1,3,1,4);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkToolCardTwelveThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;

        schemeTest.setDie(testDie3,0,3);
        schemeTest.setDie(testDie4,0,4);
        schemeTest.setDie(testDie7,0,0);
        schemeTest.setDie(testDie8,1,0);

        flag = toolCardTwelveTest.checkToolCardTwelve2Dice(trackTest,0,1,0,0,1,0,schemeTest,1,3,1,4);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkApplyModifies() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;

        testDie7 = new Die(2);
        testDie8 = new Die(2);

        schemeTest.setDie(testDie3,0,3);
        schemeTest.setDie(testDie4,0,4);
        schemeTest.setDie(testDie7,0,0);
        schemeTest.setDie(testDie8,1,0);

        int value1 = testDie7.getValue();
        int value2 = testDie8.getValue();

        schemeTest = toolCardTwelveTest.applyModifies(0,0,1,0,1,3,1,4);

        if(schemeTest.getDie(1,3).getColor()==testDie7.getColor())
            if(schemeTest.getDie(1,3).getValue()==value1)
                if(schemeTest.getDie(1,4).getColor()==testDie8.getColor())
                    if(schemeTest.getDie(1,4).getValue()==value2)
                        flag = true;

        Assertions.assertEquals(true,flag);

    }

}
