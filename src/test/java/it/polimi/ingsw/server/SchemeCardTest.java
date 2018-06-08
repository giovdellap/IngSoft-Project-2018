package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SchemeCardTest {


    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    private SchemeCard testScheme;
    private Die testDie;
    private int testFront[][];
    private int testBack[][];
    private String frontName;
    private String backName;

    int id = (int) (Math.random() * 12 + 1);
    int fb = (int) (Math.random() * 2 + 1);
    int toInsert = (int) (Math.random() * 11 + 1);
    int diff = (int) (Math.random() * 6 + 1);

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {

        testFront = new int[4][5];
        testBack = new int[4][5];
        testScheme = new SchemeCard(id);
        testDie = new Die((int) (Math.random() * 5 + 1));
        testDie.throwDie();
        testScheme.setfb(fb);
        testScheme.setName(1,"Test Nome Front");
        testScheme.setName(2,"Test Nome Back");


        for (int j = 0; j < 4; j++)
            for (int z = 0; z < 5; z++) {
                testFront[j][z] = 0;
                testBack[j][z] = 0;
            }

    }


    @Test
    public void checkGetID()  {

      Assertions.assertEquals(true,testScheme.getID()==id);

    }


    @Test
    public void checkDisabledScheme() {

        testScheme.disableScheme();
        Assertions.assertEquals(true,testScheme.checkDisabled());

    }

    @Test
    public void checkSetGetName() throws InvalidIntArgumentException {

        boolean flag = false;

        if (fb == 1) {
            frontName = testScheme.getName(fb);
            if (frontName.equals("Test Nome Front"))
                flag = true;
        }

        if (fb == 2) {
            backName = testScheme.getName(fb);
            if (backName.equals("Test Nome Back"))
                flag = true;
        }

        Assertions.assertEquals(true,flag);
    }

    @Test
    public void checkSetNameException() throws InvalidIntArgumentException {

        boolean flag = false;

        try {

            testScheme.setName(3,"Test Name Scheme");
        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }


    @Test
    public void checkGetNameException() throws InvalidIntArgumentException {

        boolean flag = false;

        try {

            testScheme.getName(3);
        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }


    @Test
    public void checkSetGetDie() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        testScheme.setDie(testDie, 2, 2);
        Assertions.assertEquals(true, testScheme.getDie(2, 2) == testDie);

    }

    @Test
    public void checkSetDieException1() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;

        try {

            testScheme.setDie(testDie,8,5);

        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);


    }


    @Test
    public void checkSetDieException2() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;
        testDie = null;

        try {

            testScheme.setDie(testDie,2,3);

        }

        catch (GenericInvalidArgumentException e) {

            if (e.getMessage().equals("Generic invalid argument"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);


    }

    @Test
    public void checkSetDieException3() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;

        try {

            testScheme.setDie(testDie,2,3);
            testDie.throwDie();
            testScheme.setDie(testDie,2,3);
        }

        catch (GenericInvalidArgumentException e) {

            if (e.getMessage().equals("Generic invalid argument"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkGetDieException() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;

        try {

            testScheme.getDie(8,8);

        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
            flag = true;

        }

        Assertions.assertEquals(true, flag);

    }

    @Test
    public void checkSetGetCell() throws InvalidIntArgumentException{

        testScheme.setCell(fb, 3, 4, toInsert);
        Assertions.assertEquals(true, testScheme.getCell(fb, 3, 4) == toInsert);

    }

    @Test
    public void checkSetCellException1() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;

        try {

            testScheme.setCell(3,2,3,toInsert);

        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);


    }


    @Test
    public void checkSetCellException2() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;
        testDie = null;

        try {

            testScheme.setCell(2,8,3,2);

        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);


    }

    @Test
    public void checkSetCellException3() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = false;

        try {

            testScheme.setCell(1,3,4,14);

        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkSetGetDiff() throws InvalidIntArgumentException {
        testScheme.setDiff(fb, diff);
        Assertions.assertEquals(true, testScheme.getDiff(fb) == diff);
    }


    @Test
    public void checkSetDiffException1() throws InvalidIntArgumentException {

        boolean flag = false;

        try {
            testScheme.setDiff(4,3);
        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true, flag);

    }

    @Test
    public void checkSetDiffException2() throws InvalidIntArgumentException {

        boolean flag = false;

        try {
            testScheme.setDiff(1,11);
        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true, flag);
    }

    @Test
    public void checkGetDiffException() throws InvalidIntArgumentException {

        boolean flag = false;
        int tempDiff;

        try {
            tempDiff = testScheme.getDiff(3);
        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true, flag);
    }

    @Test
    public void checkSetGetFb() throws InvalidIntArgumentException {
        testScheme.setfb(fb);
        Assertions.assertEquals(true, testScheme.getfb() == fb);

    }


    @Test
    public void checkSetFbException() throws InvalidIntArgumentException {

        boolean flag=false;

        try {
            testScheme.setfb(4);
        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true, flag);

    }


    @Test
    public void checkGetFront() throws InvalidIntArgumentException {

        boolean flag = true;

        testScheme.setCell(1, 1, 4, 3);
        testScheme.setCell(1, 3, 4, 2);
        testScheme.setCell(1, 0, 1, 8);
        testScheme.setCell(1, 2, 2, 9);

        testFront = testScheme.getFront();


        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++) {

                System.out.println((String)Integer.toString(testFront[i][j])+" = "+(String)Integer.toString(testScheme.getCell(1,i,j)));

                if (testFront[i][j]!=testScheme.getCell(1,i,j)) {
                    System.out.println("TEST FAILED");
                    flag = false;
                }
            }

            Assertions.assertEquals(true,flag);

    }


    @Test
    public void checkGetBack() throws InvalidIntArgumentException {

        boolean flag = true;

        testScheme.setCell(2, 3, 1, 2);
        testScheme.setCell(2, 1, 3, 5);
        testScheme.setCell(2, 2, 4, 8);
        testScheme.setCell(2, 1, 2, 11);

        testFront = testScheme.getBack();


        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 5; j++) {

                System.out.println((String)Integer.toString(testFront[i][j])+" = "+(String)Integer.toString(testScheme.getCell(2,i,j)));

                if (testFront[i][j]!=testScheme.getCell(2,i,j)) {
                    System.out.println("TEST FAILED");
                    flag = false;
                }
            }

        Assertions.assertEquals(true,flag);

    }


    @Test
    public void checkReplaceDie() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        Die testReplaceDie1 = new Die((int)Math.random()*5+1);
        Die testReplaceDie2;

        testDie.throwDie();
        testScheme.setDie(testDie, 3, 4);

        testReplaceDie1.throwDie();
        testReplaceDie2 = testScheme.replaceDie(testReplaceDie1, 3, 4);

        Assertions.assertEquals(true,testDie.equals(testReplaceDie2));


    }

    @Test
    public void checkReplaceDieException1() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag= false;
        testScheme.setDie(testDie,1,2);
        testDie = null;

        try {

            testScheme.replaceDie(testDie,1,2);
        }

        catch (GenericInvalidArgumentException e) {

            if (e.getMessage().equals("Generic invalid argument"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkReplaceDieException2() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag= false;

        try {

            testScheme.replaceDie(testDie,2,3);
        }

        catch (GenericInvalidArgumentException e) {

            if (e.getMessage().equals("Generic invalid argument"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }


}

