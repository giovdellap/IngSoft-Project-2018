package it.polimi.ingsw.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SchemeCardTest {


    private SchemeCard testScheme;
    private Die testDie;
    private int testFront[][];
    private int testBack[][];
    int id = (int) (Math.random() * 23 + 1);
    int fb = (int) (Math.random() * 1 + 1);
    int toInsert = (int) (Math.random() * 10 + 1);
    int diff = (int) (Math.random() * 5 + 1);

    @BeforeEach
    public void setUp() {

        testFront = new int[4][5];
        testBack = new int[4][5];
        testScheme = new SchemeCard(id);
        testDie = new Die((int) (Math.random() * 5 + 1));
        testDie.throwDie();
        testScheme.setfb(fb);


        for (int j = 0; j < 4; j++)
            for (int z = 0; z < 5; z++) {
                testFront[j][z] = 0;
                testBack[j][z] = 0;
            }


    }


    @Test
    public void checkGetID() {

       Assertions.assertEquals(true,testScheme.getID()==id);

    }
    @Test
    public void checkSetGetDie() {

        testScheme.setDie(testDie, 2, 2);
        Assertions.assertEquals(true, testScheme.getDie(2, 2) == testDie);

    }

    @Test
    public void checkSetGetCell() {

        testScheme.setCell(fb, 3, 4, toInsert);
        Assertions.assertEquals(true, testScheme.getCell(fb, 3, 4) == toInsert);

    }

    @Test
    public void checkSetGetDiff() {
        testScheme.setDiff(fb, diff);
        Assertions.assertEquals(true, testScheme.getDiff(fb) == diff);
    }

    @Test
    public void checkSetGetFb() {
        testScheme.setfb(fb);
        Assertions.assertEquals(true, testScheme.getfb() == fb);

    }

    @Test
    public void checkGetFront() {

        boolean flag = true;

        testScheme.setCell(1, 1, 4, 3);
        testScheme.setCell(1, 4, 5, 2);
        testScheme.setCell(1, 3, 1, 8);
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
    public void checkGetBack() {

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


}

