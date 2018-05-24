package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.DieMP;
import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.RoundDiceMP;
import it.polimi.ingsw.server.ModelComponent.Die;
import org.junit.jupiter.api.*;

public class DraftPoolMPTest {

    DraftPoolMP draftPoolTest;
    DieMP[] dice;
    DieMP tempDie;
    DieMP tempDie2;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {

        dice = new DieMP[7];

        for (int i=0;i<7;i++) {
            dice[i] = new DieMP(0);
            dice[i].throwDie();
        }

        draftPoolTest=new DraftPoolMP(dice);
        tempDie=new DieMP(0);
        tempDie2=new DieMP(1);

    }

    @Test
    public void checkReturnDie() throws InvalidIntArgumentException {

        tempDie = draftPoolTest.returnDie(1);
        Assertions.assertEquals(false, tempDie.isDisabled());
    }

    @Test
    public void checkReturnException1(){
        try {
            tempDie=draftPoolTest.returnDie(-2);
        }
        catch (InvalidIntArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }
    }

    @Test
    public void checkPickUpDie() {

        try {
            draftPoolTest.pickUpDie(3);
            draftPoolTest.returnDie(3);
        }
        catch (InvalidIntArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }
    }

    @Test
    public void checkPickUpException() {
        try {
            draftPoolTest.pickUpDie(12);
        }
        catch (InvalidIntArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
        }
    }

}
