package it.polimi.ingsw.server;

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
    Die tempDie3;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {
        players=3;
        dim=(players*2)+1;
        testDP = new DraftPool(players);
        tempDie=new Die(0);
        tempDie2=new Die(1);
        tempDie3=null;

    }

    @Test
    public void checkReturnDie() throws InvalidIntArgumentException {
        tempDie = testDP.returnDie(1);
        Assertions.assertEquals(false, tempDie.isDisabled());
    }

    @Test
    public void checkReturnException(){
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
    public void checkReplaceDie() throws InvalidIntArgumentException, GenericInvalidArgumentException {
        boolean flag=false;
        testDP.replaceDie(3,tempDie);
        if (testDP.returnDie(3).getColor()==0) flag=true;
        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkReplaceDieException1() throws GenericInvalidArgumentException {

        try {
            testDP.replaceDie(12,tempDie);

        } catch (InvalidIntArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }


    }

    @Test
    public void checkReplaceDieException2() throws InvalidIntArgumentException {

        try {
            testDP.replaceDie(4,tempDie3);
        } catch (GenericInvalidArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"Generic invalid argument");
        }
    }

    @Test
    public void checkReplaceDieException3() throws GenericInvalidArgumentException {
        try {
            testDP.pickUpDie(5);
            testDP.replaceDie(5,tempDie);
        } catch (InvalidIntArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }
    }

    @Test
    public void checkReplaceDieException4() throws InvalidIntArgumentException {
        try {
            tempDie.disableDie();
            testDP.replaceDie(5,tempDie);
        } catch (GenericInvalidArgumentException e) {
            Assertions.assertEquals(e.getMessage(),"Generic invalid argument");
        }

    }



}
