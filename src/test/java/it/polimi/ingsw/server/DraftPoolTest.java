package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
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
        testRoudDice= new RoundDice(1);


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
    public void checkupdateDraftDice() throws InvalidIntArgumentException, FullDataStructureException {

        boolean flag=true;

        testDP.pickUpDie(1);
        testDP.pickUpDie(2);
        testDP.pickUpDie(3);
        testDP.pickUpDie(4);
        testDP.pickUpDie(5);

        tempDie=testDP.returnDie(0);
        tempDie2=testDP.returnDie(6);

        testDP.updateDraftDice();


        if (!tempDie2.equals(testRoudDice.getDie(0))) flag=false;
        if (!tempDie.equals(testRoudDice.getDie(1))) flag=false;

        Assertions.assertEquals(true, flag);



    }

}
