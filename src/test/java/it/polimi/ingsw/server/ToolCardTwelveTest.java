package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardTwelve;
import  it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void setUp() throws GenericInvalidArgumentException, InvalidIntArgumentException, FullDataStructureException {

        toolCardTwelveTest = new ToolCardTwelve();
        schemesDeck = new SchemesDeck();
        schemeTest = schemesDeck.extractSchemebyID(6);
        schemeTest.setfb(2);
        testDie1 = new Die(5);
        testDie2 = new Die(2);
        testDie3 = new Die(3);
        testDie3.setValueTest(1);
        testDie4 = new Die(4);
        testDie4.setValueTest(2);
        testDie5 = new Die(5);
        testDie5.setValueTest(4);
        testDie6 = new Die(5);
        testDie6.setValueTest(3);
        testDie7 = new Die(2);
        testDie8 = new Die(3);

        trackTest = new RoundTrack();
        dice = new RoundDice(3);
        dice.addDie(testDie1);
        dice.addDie(testDie2);
        trackTest.addRound(dice);

    }

    @Test
    public void checkToolCardTwelveOne() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;

        schemeTest.setDie(testDie3,0,3);
        schemeTest.setDie(testDie4,0,4);
        schemeTest.setDie(testDie5,0,0);
        schemeTest.setDie(testDie6,1,0);

        flag = toolCardTwelveTest.checkToolCardTwelve(trackTest,0, 0,0,0,1,0,schemeTest,1,3,1,4);

        Assertions.assertEquals(true,flag);

    }


    @Test
    public void checkToolCardTwelveTwo() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;

        schemeTest.setDie(testDie3,0,3);
        schemeTest.setDie(testDie4,0,4);
        schemeTest.setDie(testDie5,0,0);
        schemeTest.setDie(testDie6,1,0);

        flag = toolCardTwelveTest.checkToolCardTwelve(trackTest,0, 1,0,0,1,0,schemeTest,1,3,1,4);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkToolCardTwelveThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;

        schemeTest.setDie(testDie3,0,3);
        schemeTest.setDie(testDie4,0,4);
        schemeTest.setDie(testDie7,0,0);
        schemeTest.setDie(testDie8,1,0);

        flag = toolCardTwelveTest.checkToolCardTwelve(trackTest,0, 1,0,0,1,0,schemeTest,1,3,1,4);

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

        schemeTest = toolCardTwelveTest.applyModifies(0,0,1,0,schemeTest,1,3,1,4);

        if(schemeTest.getDie(1,3).equals(testDie7) && schemeTest.getDie(1,4).equals(testDie8))
            flag = true;

        Assertions.assertEquals(true,flag);


    }



}
