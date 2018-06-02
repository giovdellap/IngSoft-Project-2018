package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.RoundDice;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.*;


public class DraftPoolTest
{
    private DraftPool testDP;
    int players;
    int dim;
    Die tempDie;
    Die tempDie2;
    RoundDice testRoudDice;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {
        players=3;
        dim=(players*2)+1;
        testDP = new DraftPool(players);
        tempDie=new Die(0);
        tempDie2=new Die(1);


    }

    @Test
    public void checkReturnDie() throws InvalidIntArgumentException {
        tempDie = testDP.returnDie(1);
        Assertions.assertEquals(false, tempDie.isDisabled());
    }

    @Test
    public void checkReturnException1(){
        try {
            tempDie=testDP.returnDie(-2);
        }
        catch (InvalidIntArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }
    }

    @Test
    public void checkPickUpDie() {
        try {
            testDP.pickUpDie(3);
            testDP.returnDie(3);
        }
        catch (InvalidIntArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }
    }
    @Test
    public void checkPickUpException(){
        try {
            testDP.pickUpDie(12);
        }
        catch (InvalidIntArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }
    }

    @Test
    public void checkUpdateDraftDice() throws InvalidIntArgumentException, FullDataStructureException, GenericInvalidArgumentException {

        boolean flag=true;

        tempDie=testDP.returnDie(0);
        tempDie2=testDP.returnDie(1);
        testDP.pickUpDie(2);
        testDP.pickUpDie(2);
        testDP.pickUpDie(2);
        testDP.pickUpDie(2);
        //testDP.pickUpDie(2);
        testRoudDice = new RoundDice(2);
        testRoudDice=testDP.updateDraftDice();

        if (!tempDie2.equals(testRoudDice.getDie(1))) flag=false;
        if (!tempDie.equals(testRoudDice.getDie(0))) flag=false;

        Assertions.assertEquals(true, flag);

    }

    @Test
    public void checkReplaceDieOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        tempDie2 = testDP.replaceDie(1,tempDie);

        Assertions.assertEquals(true,testDP.returnDie(1).equals(tempDie));

    }

    @Test
    public void checkReplaceDieTwo() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        Die tempDie3 = testDP.returnDie(1);
        tempDie2 = testDP.replaceDie(1,tempDie);

        Assertions.assertEquals(true,tempDie3.equals(tempDie2));
    }

}
