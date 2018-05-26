package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ModelComponent.SchemesDeck;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardOne;
import it.polimi.ingsw.server.ToolCards.ToolCardThree;
import it.polimi.ingsw.server.ToolCards.ToolCardTwo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ToolCardThreeTest {

    SchemeCard testScheme;
    int id = (int) (Math.random() * 11 + 1);
    Die testDie;
    Die testDie2;
    int randomColor = (int) (Math.random() * 4 + 1);
    ToolCardThree toolCardThreeTest;
    SchemesDeck schemesDeckTest;

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {

        schemesDeckTest = new SchemesDeck();
        testScheme = schemesDeckTest.extractSchemebyID(6);
        testDie = new Die(randomColor);
        testDie2 = new Die(randomColor);
        toolCardThreeTest = new ToolCardThree();
        testScheme.setfb(2);
        System.out.println(testScheme.getName(2));
    }


    @Test
    public void checkToolCardThreeTestOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie.setValueTest(3);

        flag = toolCardThreeTest.checkToolCardThree(2, 3, testScheme, 3, 3);

        Assertions.assertEquals(false, flag);


    }

    @Test
    public void checkToolCardThreeTestTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie.setValueTest(3);
        testDie2.setValueTest(4);

        testScheme.setDie(testDie, 2, 3);
        testScheme.setDie(testDie2, 1, 4);

        flag = toolCardThreeTest.checkToolCardThree(2, 3, testScheme, 1, 4);

        Assertions.assertEquals(false, flag);

    }

    @Test
    public void checkToolCardThreeTestThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie = new Die(3);
        testDie.setValueTest(2);
        testDie2 = new Die(3);

        testScheme.setDie(testDie, 2, 4);
        testScheme.setDie(testDie2, 0, 0);

        flag = toolCardThreeTest.checkToolCardThree(0, 0, testScheme, 1, 4);

        Assertions.assertEquals(false, flag);

    }


    @Test
    public void checkToolCardThreeTestFour() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;
        testDie = new Die(3);
        testDie.setValueTest(2);
        testDie2 = new Die(4);
        testDie2.setValueTest(2);

        testScheme.setDie(testDie, 0, 0);
        testScheme.setDie(testDie2, 1, 4);

        flag = toolCardThreeTest.checkToolCardThree(0, 0, testScheme, 0, 4);

        Assertions.assertEquals(true, flag);

    }

    @Test
    public void checkToolCardThreeTestFive() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;
        testDie = new Die(2);
        testDie.setValueTest(3);
        testDie2 = new Die(1);
        testDie2.setValueTest(4);

        testScheme.setDie(testDie, 0, 4);
        testScheme.setDie(testDie2, 0, 0);

        flag = toolCardThreeTest.checkToolCardThree(0, 0, testScheme, 1, 3);

        Assertions.assertEquals(true, flag);
    }

    @Test
    public void checkToolCardThreeTestSix() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;

        testDie = new Die(2);
        testDie.setValueTest(3);
        testDie2 = new Die(1);
        testDie2.setValueTest(4);

        testScheme.setDie(testDie, 0, 0);
        testScheme.setDie(testDie2, 1, 4);

        flag = toolCardThreeTest.checkToolCardThree(0, 0, testScheme, 2, 3);

        Assertions.assertEquals(false, flag);

    }

    @Test
    public void checkApplyModifiesOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie.setValueTest(4);
        testScheme.setDie(testDie,2,3);

        testScheme = toolCardThreeTest.applyModifies(2,3,testScheme,2,1);

        Assertions.assertEquals(true,testScheme.getDie(2,1).equals(testDie));


    }

    @Test
    public void checkApplyModifiesTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie.setValueTest(2);
        testScheme.setDie(testDie,3,3);

        testScheme = toolCardThreeTest.applyModifies(3,3,testScheme,1,1);

        Assertions.assertEquals(true,testScheme.getDie(3,3).isDisabled());

    }



}
