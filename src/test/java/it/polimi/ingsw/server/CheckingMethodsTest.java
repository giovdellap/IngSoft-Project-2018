package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ModelComponent.SchemesDeck;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CheckingMethodsTest {

    CheckingMethods checkingMethodsTest;
    SchemeCard schemeCardTest;
    SchemesDeck schemesDeckTest;
    Die dieTest1;
    Die dieTest2;

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {

        checkingMethodsTest = new CheckingMethods();
        schemesDeckTest = new SchemesDeck();
        schemeCardTest = schemesDeckTest.extractSchemebyID(6);
        dieTest1 = new Die(0);
        dieTest2 = new Die(0);
        schemeCardTest.setfb(2);
        System.out.println(schemeCardTest.getName(2));

    }


    @Test
    public void checkMoveOne() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;

        dieTest1 = new Die(3);
        dieTest1.throwDie();
        dieTest2 = new Die(5);
        dieTest2.throwDie();

        schemeCardTest.setDie(dieTest1,3,4);

        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,1,3);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkMoveTwo() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;

        dieTest1 = new Die(1);
        dieTest1.throwDie();
        dieTest2 = new Die(5);

        schemeCardTest.setDie(dieTest2,2,3);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest1,1,2);

        Assertions.assertEquals(false,flag);

    }


    @Test
    public void checkMoveThree() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;

        dieTest1 = new Die(2);
        dieTest1.setValueTest(3);
        dieTest2 = new Die(5);
        dieTest2.setValueTest(6);

        schemeCardTest.setDie(dieTest1,3,2);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,3,1);

        Assertions.assertEquals(false,flag);


    }


    @Test
    public void checkMoveFour() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;

        dieTest1 = new Die(2);
        dieTest1.setValueTest(3);
        dieTest2 = new Die(2);
        dieTest2.setValueTest(5);

        schemeCardTest.setDie(dieTest1,3,2);

        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,3,3);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkMoveFive() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;

        dieTest1 = new Die(2);
        dieTest1.setValueTest(5);
        dieTest2 = new Die(1);
        dieTest2.setValueTest(5);

        schemeCardTest.setDie(dieTest1,3,2);

        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,2,2);

        Assertions.assertEquals(false,flag);


    }

}
