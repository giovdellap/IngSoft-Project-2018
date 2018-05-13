package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestFactory;


public class PublicObjectiveTest {

    private PublicObjective publicObjectiveTest;
    private PublicObjective toTest;
    private int bonusTest;
    private SchemeCard sc1;
    private SchemeCard sc2;
    private SchemesDeck testSD;

    int idTest = (int)(Math.random()*9+1);


    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {

        publicObjectiveTest = new PublicObjective(idTest);
        bonusTest = publicObjectiveTest.getBonus();
        testSD = new SchemesDeck();

        sc1=testSD.extractSchemebyID(1);
        sc1.setfb(1);


    }

    @Test
    public void checkInvalidIntArgumentException() {

        boolean flag = false;

        try {

            publicObjectiveTest = new PublicObjective(49);

        } catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;
        }

        Assertions.assertEquals(true,flag);
    }


    @Test
    public void checkGetID() {

        Assertions.assertEquals(true,publicObjectiveTest.getId()==idTest);
    }

    @Test
    public void checkGetBonus() {

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
    public void checkDisabledPublicObjective() {

        publicObjectiveTest.disableScheme();
        Assertions.assertEquals(true,publicObjectiveTest.checkDisabled());

    }

    @Test
    public void checkToString() {

        int tempID = publicObjectiveTest.getId();
        int tempBonus = publicObjectiveTest.getBonus();
        String tempList1 = "Id" + " " + tempID + " " + "Bonus" + " " + tempBonus;
        String tempList2 = publicObjectiveTest.toString();

        Assertions.assertEquals(true,tempList1.equals(tempList2));


    }




}
