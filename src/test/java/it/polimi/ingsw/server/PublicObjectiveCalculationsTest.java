package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PublicObjectiveCalculationsTest {

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    SchemesDeck schemesDeckTest;
    SchemeCard[] schemesTest;
    SchemeCard schemeTest;
    PublicObjective publicObjectiveTest;
    int bonusTest;
    int[][] diceSchemeTest;
    Die DieTest1;
    Die DieTest2;
    Die DieTest3;
    Die DieTest4;
    Die DieTest5;


    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {

        schemesDeckTest = new SchemesDeck();
        schemesTest = schemesDeckTest.extractSchemes(2);
        schemeTest = schemesTest[1];

    }


    @Test
    public void checkCalculateOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(1);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);
        DieTest5 = new Die(5);

        schemeTest.setDie(DieTest1,1,0);
        schemeTest.setDie(DieTest2,1,1);
        schemeTest.setDie(DieTest3,1,2);
        schemeTest.setDie(DieTest4,1,3);
        schemeTest.setDie(DieTest5,1,4);

        schemeTest.setDie(DieTest1,3,0);
        schemeTest.setDie(DieTest2,3,1);
        schemeTest.setDie(DieTest3,3,2);
        schemeTest.setDie(DieTest4,3,3);
        schemeTest.setDie(DieTest5,3,4);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(12,bonusTest);


    }

    @Test
    public void checkCalculateTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(2);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);

        schemeTest.setDie(DieTest1,0,1);
        schemeTest.setDie(DieTest2,1,1);
        schemeTest.setDie(DieTest3,2,1);
        schemeTest.setDie(DieTest4,3,1);

        schemeTest.setDie(DieTest1,0,4);
        schemeTest.setDie(DieTest2,1,4);
        schemeTest.setDie(DieTest3,2,4);
        schemeTest.setDie(DieTest4,3,4);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(10,bonusTest);


    }


    @Test
    public void checkCalculateThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(3);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);
        DieTest5 = new Die(5);


        DieTest1.setValueTest(1);
        DieTest2.setValueTest(3);
        DieTest3.setValueTest(2);
        DieTest4.setValueTest(6);
        DieTest5.setValueTest(5);

        schemeTest.setDie(DieTest1,1,0);
        schemeTest.setDie(DieTest2,1,1);
        schemeTest.setDie(DieTest3,1,2);
        schemeTest.setDie(DieTest4,1,3);
        schemeTest.setDie(DieTest5,1,4);

        schemeTest.setDie(DieTest1,3,0);
        schemeTest.setDie(DieTest2,3,1);
        schemeTest.setDie(DieTest3,3,2);
        schemeTest.setDie(DieTest4,3,3);
        schemeTest.setDie(DieTest5,3,4);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(10,bonusTest);


    }

    @Test
    public void checkCalculateFour() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(4);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);


        DieTest1.setValueTest(1);
        DieTest2.setValueTest(3);
        DieTest3.setValueTest(2);
        DieTest4.setValueTest(6);

        schemeTest.setDie(DieTest1,0,1);
        schemeTest.setDie(DieTest2,1,1);
        schemeTest.setDie(DieTest3,2,1);
        schemeTest.setDie(DieTest4,3,1);

        schemeTest.setDie(DieTest1,0,4);
        schemeTest.setDie(DieTest2,1,4);
        schemeTest.setDie(DieTest3,2,4);
        schemeTest.setDie(DieTest4,3,4);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(8,bonusTest);


    }


    
}
