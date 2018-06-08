package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ModelComponent.SchemesDeck;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardFour;
import it.polimi.ingsw.server.ToolCards.ToolCardOne;
import it.polimi.ingsw.server.ToolCards.ToolCardThree;
import it.polimi.ingsw.server.ToolCards.ToolCardTwo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToolCardFourTest {

    SchemeCard testScheme;
    int id = (int) (Math.random() * 12 + 1);
    Die testDie;
    Die testDie2;
    Die testDie3;
    Die testDie4;
    int randomColor = (int) (Math.random() * 5 + 1);
    ToolCardFour toolCardFourTest;
    SchemesDeck schemesDeckTest;

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {

        schemesDeckTest = new SchemesDeck();
        testScheme = schemesDeckTest.extractSchemebyID(6);
        testDie = new Die(randomColor);
        testDie2 = new Die(randomColor);
        testDie3 = new Die(randomColor);
        testDie4 = new Die(randomColor);
        toolCardFourTest = new ToolCardFour();
        testScheme.setfb(2);
        System.out.println(testScheme.getName(2));

    }

    @Test
    public void checkToolCardFourTestOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;

        flag = toolCardFourTest.checkToolCardFour(0,0,1,3,testScheme,2,3,3,1);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkToolCardFourTestTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;

        testScheme.setDie(testDie,0,0);
        testScheme.setDie(testDie2,0,1);
        testScheme.setDie(testDie3,0,3);
        testScheme.setDie(testDie4,0,4);

        flag = toolCardFourTest.checkToolCardFour(0,0,0,1,testScheme,0,3,0,4);

        Assertions.assertEquals(false,flag);



    }

    @Test
    public void checkToolCardFourTestThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie = new Die(2);
        testDie2 = new Die(4);
        testDie3 = new Die(2);
        testDie4 = new Die(4);

        testScheme.setDie(testDie,0,0);
        testScheme.setDie(testDie2,1,0);
        testScheme.setDie(testDie3,0,3);
        testScheme.setDie(testDie4,0,4);

        flag = toolCardFourTest.checkToolCardFour(0,0,1,0,testScheme,1,3,1,4);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkToolCardFourTestFour() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;

        testDie = new Die(2);
        testDie2 = new Die(4);
        testDie3 = new Die(3);
        testDie4 = new Die(5);

        testDie.setValueTest(2);
        testDie2.setValueTest(3);
        testDie3.setValueTest(2);
        testDie4.setValueTest(3);

        testScheme.setDie(testDie,2,4);
        testScheme.setDie(testDie2,3,4);
        testScheme.setDie(testDie3,0,0);
        testScheme.setDie(testDie4,1,0);

        flag = toolCardFourTest.checkToolCardFour(0,0,1,0,testScheme,1,4,0,4);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkApplyModifies() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie = new Die(2);
        testDie2 = new Die(4);
        testDie3 = new Die(3);
        testDie4 = new Die(5);

        testDie.setValueTest(2);
        testDie2.setValueTest(3);
        testDie3.setValueTest(2);
        testDie4.setValueTest(3);

        testScheme.setDie(testDie,2,4);
        testScheme.setDie(testDie2,3,4);
        testScheme.setDie(testDie3,0,0);
        testScheme.setDie(testDie4,1,0);

        testScheme = toolCardFourTest.applyModifies(0,0,1,0,testScheme,0,3,0,4);

        Assertions.assertEquals(true, (testScheme.getDie(0,3).equals(testDie3) && testScheme.getDie(0,4).equals(testDie4)));


    }

}
