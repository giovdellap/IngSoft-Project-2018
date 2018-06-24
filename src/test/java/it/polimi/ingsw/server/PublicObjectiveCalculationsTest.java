package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.PublicObjective;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ModelComponent.SchemesDeck;
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

    Die DieTest1;
    Die DieTest2;
    Die DieTest3;
    Die DieTest4;
    Die DieTest5;
    Die DieTest6;
    Die DieTest7;
    Die DieTest8;
    Die DieTest9;
    Die DieTest10;
    Die DieTest11;
    Die DieTest12;
    Die DieTest13;
    Die DieTest14;
    Die DieTest15;
    Die DieTest16;
    Die DieTest17;
    Die DieTest18;
    Die DieTest19;
    Die DieTest20;



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
    public void checkCalculateOneException() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(1);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

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
    public void checkCalculateTwoException() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(2);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }


    @Test
    public void checkCalculateThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(3);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);
        DieTest5 = new Die(5);


        DieTest1.setValue(1);
        DieTest2.setValue(3);
        DieTest3.setValue(2);
        DieTest4.setValue(6);
        DieTest5.setValue(5);

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
    public void checkCalculateThreeException() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(3);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }



    @Test
    public void checkCalculateFour() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(4);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);

        DieTest1.setValue(1);
        DieTest2.setValue(3);
        DieTest3.setValue(2);
        DieTest4.setValue(6);

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


    @Test
    public void checkCalculateFourException() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(4);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }


    @Test
    public void checkCalculateFive() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        publicObjectiveTest = new PublicObjective(5);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);
        DieTest5 = new Die(4);

        DieTest1.setValue(1);
        DieTest2.setValue(1);
        DieTest3.setValue(2);
        DieTest4.setValue(2);
        DieTest5.setValue(5);

        schemeTest.setDie(DieTest1,0,1);
        schemeTest.setDie(DieTest2,3,3);
        schemeTest.setDie(DieTest3,1,4);
        schemeTest.setDie(DieTest4,2,4);
        schemeTest.setDie(DieTest5,1,2);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(4,bonusTest);


    }


    @Test
    public void checkCalculateFiveException() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(5);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }


    @Test
    public void checkCalculateSix() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        publicObjectiveTest = new PublicObjective(6);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);
        DieTest5 = new Die(4);

        DieTest1.setValue(3);
        DieTest2.setValue(2);
        DieTest3.setValue(3);
        DieTest4.setValue(4);
        DieTest5.setValue(4);

        schemeTest.setDie(DieTest1,0,1);
        schemeTest.setDie(DieTest2,3,3);
        schemeTest.setDie(DieTest3,1,4);
        schemeTest.setDie(DieTest4,2,4);
        schemeTest.setDie(DieTest5,1,2);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(4,bonusTest);


    }


    @Test
    public void checkCalculateSixException() throws InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(6);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }

    @Test
    public void checkCalculateSeven() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        publicObjectiveTest = new PublicObjective(7);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);
        DieTest5 = new Die(4);

        DieTest1.setValue(5);
        DieTest2.setValue(6);
        DieTest3.setValue(6);
        DieTest4.setValue(6);
        DieTest5.setValue(4);

        schemeTest.setDie(DieTest1,0,1);
        schemeTest.setDie(DieTest2,3,3);
        schemeTest.setDie(DieTest3,1,4);
        schemeTest.setDie(DieTest4,2,4);
        schemeTest.setDie(DieTest5,1,2);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(2,bonusTest);


    }


    @Test
    public void checkCalculateSevenException() throws InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(7);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }


    @Test
    public void checkCalculateEight() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        publicObjectiveTest = new PublicObjective(8);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);
        DieTest5 = new Die(4);
        DieTest6 = new Die(4);

        DieTest1.setValue(1);
        DieTest2.setValue(2);
        DieTest3.setValue(3);
        DieTest4.setValue(4);
        DieTest5.setValue(5);
        DieTest6.setValue(6);

        schemeTest.setDie(DieTest1,0,1);
        schemeTest.setDie(DieTest2,3,3);
        schemeTest.setDie(DieTest3,1,4);
        schemeTest.setDie(DieTest4,2,4);
        schemeTest.setDie(DieTest5,1,2);
        schemeTest.setDie(DieTest6,3,1);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(5,bonusTest);


    }


    @Test
    public void checkCalculateEightException() throws InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(8);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }

    @Test
    public void checkCalculateNine() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        publicObjectiveTest = new PublicObjective(9);

        DieTest1 = new Die(1);
        DieTest2 = new Die(1);
        DieTest3 = new Die(1);
        DieTest4 = new Die(1);

        DieTest5 = new Die(2);
        DieTest6 = new Die(2);
        DieTest7 = new Die(2);

        DieTest8 = new Die(3);
        DieTest9 = new Die(3);
        DieTest10 = new Die(3);
        DieTest11 = new Die(3);

        DieTest12 = new Die(4);
        DieTest13 = new Die(4);
        DieTest14 = new Die(4);
        DieTest15 = new Die(4);

        DieTest16 = new Die(5);
        DieTest17 = new Die(5);
        DieTest18 = new Die(5);
        DieTest19 = new Die(5);
        DieTest20 = new Die(5);

        schemeTest.setDie(DieTest1,0,1);
        schemeTest.setDie(DieTest2,1,2);
        schemeTest.setDie(DieTest3,1,4);
        schemeTest.setDie(DieTest4,2,3);

        schemeTest.setDie(DieTest5,1,0);
        schemeTest.setDie(DieTest6,2,1);
        schemeTest.setDie(DieTest7,3,0);

        schemeTest.setDie(DieTest8,0,4);
        schemeTest.setDie(DieTest9,1,3);
        schemeTest.setDie(DieTest10,3,1);
        schemeTest.setDie(DieTest11,3,2);

        schemeTest.setDie(DieTest12,0,0);
        schemeTest.setDie(DieTest13,1,1);
        schemeTest.setDie(DieTest14,2,2);
        schemeTest.setDie(DieTest15,2,0);

        schemeTest.setDie(DieTest16,0,2);
        schemeTest.setDie(DieTest17,0,3);
        schemeTest.setDie(DieTest18,2,4);
        schemeTest.setDie(DieTest19,3,4);
        schemeTest.setDie(DieTest20,3,3);


        publicObjectiveTest.setTemp(schemeTest);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(15,bonusTest);

    }

    @Test
    public void checkCalculateNineException() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(9);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }

    @Test
    public void checkCalculateTen() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        publicObjectiveTest = new PublicObjective(10);

        DieTest1 = new Die(1);
        DieTest2 = new Die(2);
        DieTest3 = new Die(3);
        DieTest4 = new Die(4);
        DieTest5 = new Die(5);

        schemeTest.setDie(DieTest1,0,1);
        schemeTest.setDie(DieTest2,3,3);
        schemeTest.setDie(DieTest3,1,4);
        schemeTest.setDie(DieTest4,2,4);
        schemeTest.setDie(DieTest5,1,2);

        bonusTest = publicObjectiveTest.setBonus(schemeTest);

        Assertions.assertEquals(4,bonusTest);


    }


    @Test
    public void checkCalculateTenException() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(10);
        schemeTest = null;

        try {
            publicObjectiveTest.setBonus(schemeTest);
        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }


}



