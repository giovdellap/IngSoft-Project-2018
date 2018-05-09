package it.polimi.ingsw.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoundDiceTest
{
    private RoundDice testRD;
    private int testDim=2;
    private Die testDie1;
    private Die testDie2;

    @BeforeEach
    public void setUp()
    {
        testRD = new RoundDice(testDim);
        testDie1 = new Die(3);
        testDie2 = new Die(5);
    }

    @Test
    public void testAddandGet()
    {
        //checks that methods add and return dice correctly
        testRD.addDie(testDie1);
        Die tempDie = testRD.getDie(0);
        Assertions.assertEquals(3,tempDie.getColor());
    }

    @Test
    public void testDelete()
    {
        testRD.addDie(testDie1);
        testRD.addDie(testDie2);
        testRD.deleteDie(1);
        Assertions.assertEquals(null,testRD.getDie(1));
    }
}
