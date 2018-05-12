package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RoundDiceTest
{
    private RoundDice testRD;
    private int testDim=2;
    private Die testDie1;
    private Die testDie2;
    private Die testDie3;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FullDataStructureException
    {
        testRD = new RoundDice(testDim);
        testDie1 = new Die(3);
        testDie2 = new Die(5);
        testDie3 = new Die(4);

    }

    @Test
    public void checkAddandGet() throws FullDataStructureException, InvalidIntArgumentException
    {
        //checks that methods add and return dice correctly
        testRD.addDie(testDie1);
        Die tempDie = testRD.getDie(0);
        Assertions.assertEquals(3,tempDie.getColor());
    }

    @Test
    public void checkAddException()
    {
        boolean flag = false;

       try
       {
           testRD.addDie(testDie1);
           testRD.addDie(testDie2);
           try
           {
               testRD.addDie(testDie3);
           }

           catch(FullDataStructureException e)
           {
               if(e.getMessage().equals("Data Structure full: cannot add"))
                   flag = true;
           }
       } catch(FullDataStructureException ex)
       {}


        Assertions.assertEquals(true,flag);

}

    @Test
    public void checkGetException() throws FullDataStructureException
    {
        boolean flag = false;
        try
        {
            testRD.addDie(testDie1);
            testRD.addDie(testDie2);
            testRD.getDie(10);
        }
        catch (InvalidIntArgumentException e)
        {
            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkDelete() throws FullDataStructureException, InvalidIntArgumentException
    {
        boolean flag= false;

        testRD.addDie(testDie1);
        testRD.addDie(testDie2);
        testRD.deleteDie(1);
        try
        {
            testRD.getDie(1);
        } catch(InvalidIntArgumentException e)
        {
            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkDeleteException() throws FullDataStructureException
    {
        boolean flag= false;
        try
        {
            testRD.addDie(testDie1);
            testRD.addDie(testDie2);
            testRD.deleteDie(-3);
        }

        catch (InvalidIntArgumentException e)
        {
            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }

    @Test
    public  void  checkReturnDim() throws FullDataStructureException
    {
        testRD.addDie(testDie1);
        testRD.addDie(testDie2);
        Assertions.assertEquals(2, testRD.returnDim());
    }
}
