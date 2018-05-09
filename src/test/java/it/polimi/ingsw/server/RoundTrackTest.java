package it.polimi.ingsw.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoundTrackTest
{
    private RoundTrack testRT;
    private RoundDice testRD1;
    private RoundDice testRD2;
    private Die testDie1;
    private Die testDie2;
    private Die testDie3;
    private Die testDie4;
    private Die testDie5;

    @BeforeEach
    public void setUp()
    {
        testRT = new RoundTrack();
        testRD1 = new RoundDice(2);
        testRD2 = new RoundDice(3);
        testDie1 = new Die(1);
        testDie2 = new Die(2);
        testDie3 = new Die(3);
        testDie4 = new Die(4);
        testDie5 = new Die(5);
        testRD1.addDie(testDie1);
        testRD1.addDie(testDie2);
        testRD2.addDie(testDie3);
        testRD2.addDie(testDie4);
        testRD2.addDie(testDie5);
    }

    @Test
    public void testAddandReturnTurn()
    {
        //checks addRound and returnActualTurn methods work
        testRT.addRound(testRD1,2);
        Assertions.assertEquals(1,testRT.returnActualTurn());
    }

    @Test
    public void testRoundDiceisReturned()
    {
        testRT.addRound(testRD1,2);
        testRT.addRound(testRD2,3);
        Assertions.assertEquals(testRT.returnNTurnRoundDice(0),testRD1);
    }

    @Test
    public void testSpecificSet()
    {
        testRT.addRound(testRD1,2);
        testRT.addRound(testRD2,3);
        testRT.setSpecificRoundDice(testRD2,0);
        Assertions.assertEquals(testRT.returnNTurnRoundDice(0),testRD2);
    }
}
