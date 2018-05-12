package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
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
    public void setUp() throws InvalidIntArgumentException, FullDataStructureException
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
    public void checkAddandReturnTurn() throws InvalidIntArgumentException
    {
        //checks addRound and returnActualTurn methods work
        testRT.addRound(testRD1);
        Assertions.assertEquals(1,testRT.returnActualTurn());
    }


    @Test
    public void checkRoundDiceisReturned() throws InvalidIntArgumentException
    {
        testRT.addRound(testRD1);
        testRT.addRound(testRD2);
        Assertions.assertEquals(testRT.returnNTurnRoundDice(0),testRD1);
    }

    @Test
    public void checkReturnNException() throws InvalidIntArgumentException
    {
        testRT.addRound(testRD1);
        testRT.addRound(testRD2);
        try
        {
            testRT.returnNTurnRoundDice(4);
        } catch (InvalidIntArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }
    }

    @Test
    public void checkSpecificSet() throws InvalidIntArgumentException
    {
        testRT.addRound(testRD1);
        testRT.addRound(testRD2);
        testRT.setSpecificRoundDice(testRD2,0);
        Assertions.assertEquals(testRT.returnNTurnRoundDice(0),testRD2);
    }

    @Test
    public void checkSpecificSetException() throws InvalidIntArgumentException
    {
        testRT.addRound(testRD1);
        testRT.addRound(testRD2);
        try
        {
            testRT.setSpecificRoundDice(testRD2,5);
        } catch (InvalidIntArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }
    }

}
