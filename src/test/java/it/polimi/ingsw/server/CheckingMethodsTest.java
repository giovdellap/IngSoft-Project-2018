package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class CheckingMethodsTest {

    CheckingMethods checkingMethodsTest;
    SchemeCard schemeCardTest;
    SchemesDeck schemesDeckTest;
    Die dieTest1;
    Die dieTest2;

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FileNotFoundException, UnsupportedEncodingException {

        checkingMethodsTest = new CheckingMethods();
        schemesDeckTest = new SchemesDeck();
        schemeCardTest = schemesDeckTest.extractSchemebyID(6);
        dieTest1 = new Die(0);
        dieTest2 = new Die(0);
        schemeCardTest.setfb(2);

    }


    @Test
    public void checkMoveOneFalse() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(3);
        dieTest1.throwDie();
        dieTest2 = new Die(5);
        dieTest2.throwDie();

        schemeCardTest.setDie(dieTest1,3,4);

        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,1,3);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkFirstMoveOneTrue() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(3);
        dieTest1.throwDie();
        dieTest2 = new Die(5);
        dieTest2.throwDie();

        schemeCardTest.setDie(dieTest1,2,1);

        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,1,0);

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkMoveTwoFalse() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(1);
        dieTest1.throwDie();
        dieTest2 = new Die(5);

        schemeCardTest.setDie(dieTest2,2,3);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest1,1,2);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkMoveTwoTrue() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(4);
        dieTest1.throwDie();
        dieTest2 = new Die(5);

        schemeCardTest.setDie(dieTest2,2,3);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest1,1,2);

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkMoveThreeFalse() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(2);
        dieTest1.setValue(3);
        dieTest2 = new Die(5);
        dieTest2.setValue(6);

        schemeCardTest.setDie(dieTest1,3,2);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,3,1);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkMoveThreeTrue() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(2);
        dieTest1.setValue(3);
        dieTest2 = new Die(5);
        dieTest2.setValue(4);

        schemeCardTest.setDie(dieTest1,3,2);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,3,1);

        Assertions.assertEquals(true,flag);


    }


    @Test
    public void checkMoveFourFalse() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(2);
        dieTest1.setValue(3);
        dieTest2 = new Die(2);
        dieTest2.setValue(5);

        schemeCardTest.setDie(dieTest1,3,2);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,3,3);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkMoveFourTrue() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(1);
        dieTest1.setValue(3);
        dieTest2 = new Die(1);
        dieTest2.setValue(4);

        schemeCardTest.setDie(dieTest1,2,2);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,1,3);

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkMoveFiveFalse() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(2);
        dieTest1.setValue(5);
        dieTest2 = new Die(1);
        dieTest2.setValue(5);

        schemeCardTest.setDie(dieTest1,3,2);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,2,2);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkMoveFiveTrue() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(2);
        dieTest1.setValue(5);
        dieTest2 = new Die(3);
        dieTest2.setValue(5);

        schemeCardTest.setDie(dieTest1,3,2);
        flag = checkingMethodsTest.checkMove(schemeCardTest,dieTest2,2,1);

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkFirstMoveOne() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(4);
        dieTest1.throwDie();

        flag = checkingMethodsTest.checkFirstMove(schemeCardTest,dieTest1,2,2);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkFirstMoveTwo() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(4);
        dieTest1.throwDie();

        flag = checkingMethodsTest.checkFirstMove(schemeCardTest,dieTest1,1,4);

        Assertions.assertEquals(true,flag);



    }

    @Test
    public void checkFirstMoveThree() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(4);
        dieTest1.throwDie();

        flag = checkingMethodsTest.checkFirstMove(schemeCardTest,dieTest1,0,0);

        Assertions.assertEquals(true,flag);


    }

    @Test
    public void checkFirstMoveFour() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(4);
        dieTest1.throwDie();

        flag = checkingMethodsTest.checkFirstMove(schemeCardTest,dieTest1,3,2);

        Assertions.assertEquals(false,flag);



    }

    @Test
    public void checkFirstMoveFive() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag;

        dieTest1 = new Die(4);
        dieTest1.setValue(3);

        flag = checkingMethodsTest.checkFirstMove(schemeCardTest,dieTest1,3,4);

        Assertions.assertEquals(true,flag);

    }



}
