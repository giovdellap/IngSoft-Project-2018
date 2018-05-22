package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.ModelComponentsMP.SchemesDeckMP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PublicObjectiveMPTest {

    private PublicObjectiveMP publicObjectiveTest;
    private int bonusTest;
    private SchemeCardMP sc1;
    private SchemesDeckMP testSD;

    int idTest = (int)(Math.random()*9+1);

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException
    {
        publicObjectiveTest = new PublicObjectiveMP(idTest);
        bonusTest = publicObjectiveTest.getBonus();
        testSD = new SchemesDeckMP();
        sc1=testSD.extractSchemebyID(1);
        sc1.setfb(1);
    }

    @Test
    public void checkInvalidIntArgumentException()
    {
        boolean flag = false;
        try
        {
            publicObjectiveTest = new PublicObjectiveMP(49);
        } catch(InvalidIntArgumentException e) {

            if(e.getMessage().equals("The int argument is invalid"))
                flag = true;
        }

        Assertions.assertEquals(true,flag);
    }


    @Test
    public void checkGetID()
    {
        Assertions.assertEquals(true,publicObjectiveTest.getId()==idTest);
    }

    @Test
    public void checkGetBonus()
    {
        Assertions.assertEquals(true,publicObjectiveTest.getBonus()==bonusTest);
    }
    /*
        @Test
        public void checkSetBonus() {

            int bonusTest2 = publicObjectiveTest.setBonus();
            Assertions.assertEquals(true,bonusTest==bonusTest2);

        }
    */
    @Test
    public void checkDisabledPublicObjective()
    {
        publicObjectiveTest.disableScheme();
        Assertions.assertEquals(true,publicObjectiveTest.checkDisabled());
    }

    @Test
    public void checkToString()
    {
        int tempID = publicObjectiveTest.getId();
        int tempBonus = publicObjectiveTest.getBonus();
        String tempList1 = "Id" + " " + tempID + " " + "Bonus" + " " + tempBonus;
        String tempList2 = publicObjectiveTest.toString();
        Assertions.assertEquals(true,tempList1.equals(tempList2));
    }
}
