package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ModelComponent.SchemesDeck;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardOne;
import it.polimi.ingsw.server.ToolCards.ToolCardTwo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
    public void setUp() throws InvalidIntArgumentException {

        schemesDeckTest = new SchemesDeck();
        testScheme = schemesDeckTest.extractSchemebyID(6);
        testDie = new Die(randomColor);
        testDie2 = new Die(randomColor);
        toolCardTwoTest = new ToolCardTwo();
        testScheme.setfb(2);
        System.out.println(testScheme.getName(2));
    }


    @Test
    public void checkToolCardTwoTestOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie.setValueTest(3);

        flag = toolCardTwoTest.checkToolCardTwo(2,3,testScheme,3,3);

        Assertions.assertEquals(false, flag);


    }

    @Test
    public void checkToolCardTwoTestTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie.setValueTest(3);
        testDie2.setValueTest(4);

        testScheme.setDie(testDie,2,3);
        testScheme.setDie(testDie2,1,4);

        flag = toolCardTwoTest.checkToolCardTwo(2,3,testScheme,1,4);

        Assertions.assertEquals(false,flag);

    }


    @Test
    public void checkToolCardTwoTestThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie.setValueTest(3);
        testDie2.setValueTest(3);

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
        testDie.setValueTest(4);
        testDie2.setValueTest(5);

        testScheme.setDie(testDie,2,3);
        testScheme.setDie(testDie2,1,4);

        flag = toolCardTwoTest.checkToolCardTwo(2,3,testScheme,0,4);

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkToolCardTwoTestFive() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;
        testDie = new Die(3);
        testDie.setValueTest(4);


        testScheme.setDie(testDie,0,0);
        testScheme.setDie(testDie2,1,4);

        flag = toolCardTwoTest.checkToolCardTwo(0,0,testScheme,2,3);

        Assertions.assertEquals(true,flag);


    }

    @Test
    public void checkToolCardTwoTestSix() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;
        testDie = new Die(3);
        testDie.setValueTest(2);


        testScheme.setDie(testDie,0,0);
        testScheme.setDie(testDie2,1,4);

        flag = toolCardTwoTest.checkToolCardTwo(0,0,testScheme,2,4);

        Assertions.assertEquals(true,flag);


    }

    @Test
    public void checkApplyModifiesOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie.setValueTest(4);
        testScheme.setDie(testDie,2,3);

        testScheme = toolCardTwoTest.applyModifies(2,3,testScheme,2,1);

        Assertions.assertEquals(true,testScheme.getDie(2,1).equals(testDie));


    }

    @Test
    public void checkApplyModifiesTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie.setValueTest(2);
        testScheme.setDie(testDie,3,3);

        testScheme = toolCardTwoTest.applyModifies(3,3,testScheme,1,1);

        Assertions.assertEquals(true,testScheme.getDie(3,3).isDisabled());

    }


}
