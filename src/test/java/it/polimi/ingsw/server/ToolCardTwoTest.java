package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardTwo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class ToolCardTwoTest {

    SchemeCard testScheme;
    int id = (int)(Math.random()*12+1);
    Die testDie;
    Die testDie2;
    int randomColor= (int)(Math.random()*5+1);
    ToolCardTwo toolCardTwoTest;
    SchemesDeck schemesDeckTest;

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FileNotFoundException, UnsupportedEncodingException {

        schemesDeckTest = new SchemesDeck();
        testScheme = schemesDeckTest.extractSchemebyID(6);
        testDie = new Die(randomColor);
        testDie2 = new Die(randomColor);
        toolCardTwoTest = new ToolCardTwo();
        testScheme.setfb(2);
        toolCardTwoTest.setTempScheme(testScheme);
    }


    @Test
    public void checkToolCardTwoTestOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie.setValue(3);

        flag = toolCardTwoTest.checkToolCardTwo(2,3,testScheme,3,3);

        Assertions.assertEquals(false, flag);


    }

    @Test
    public void checkToolCardTwoTestTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie.setValue(3);
        testDie2.setValue(4);

        testScheme.setDie(testDie,2,3);
        testScheme.setDie(testDie2,1,4);

        flag = toolCardTwoTest.checkToolCardTwo(2,3,testScheme,1,4);

        Assertions.assertEquals(false,flag);

    }


    @Test
    public void checkToolCardTwoTestThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie.setValue(3);
        testDie2.setValue(3);

        testScheme.setDie(testDie,2,3);
        testScheme.setDie(testDie2,1,4);

        flag = toolCardTwoTest.checkToolCardTwo(2,3,testScheme,1,3);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkToolCardTwoTestFour() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;
        testDie = new Die(3);
        testDie2 = new Die(3);
        testDie.setValue(4);
        testDie2.setValue(5);

        testScheme.setDie(testDie,2,3);
        testScheme.setDie(testDie2,1,4);

        flag = toolCardTwoTest.checkToolCardTwo(2,3,testScheme,0,4);

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkToolCardTwoTestFive() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;
        testDie = new Die(3);
        testDie.setValue(4);


        testScheme.setDie(testDie,0,0);
        testScheme.setDie(testDie2,1,4);

        flag = toolCardTwoTest.checkToolCardTwo(0,0,testScheme,2,3);

        Assertions.assertEquals(true,flag);


    }

    @Test
    public void checkToolCardTwoTestSix() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;
        testDie = new Die(3);
        testDie.setValue(2);


        testScheme.setDie(testDie,0,0);
        testScheme.setDie(testDie2,1,4);

        flag = toolCardTwoTest.checkToolCardTwo(0,0,testScheme,2,4);

        Assertions.assertEquals(true,flag);


    }

    @Test
    public void checkApplyModifiesOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;

        testDie = new Die(2);
        testDie.setValue(4);
        testScheme.setDie(testDie,2,3);
        toolCardTwoTest.setTempScheme(testScheme);

        int value = testDie.getValue();

        testScheme = toolCardTwoTest.applyModifies(2,3,2,1);

        if(testScheme.getDie(2,1).getColor()==testDie.getColor())
            if(testScheme.getDie(2,1).getValue()==value)
                flag = true;

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkApplyModifiesTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie.setValue(2);
        testScheme.setDie(testDie,3,3);
        toolCardTwoTest.setTempScheme(testScheme);

        testScheme = toolCardTwoTest.applyModifies(3,3,1,1);

        Assertions.assertEquals(true,testScheme.getDie(3,3).isDisabled());

    }


}
