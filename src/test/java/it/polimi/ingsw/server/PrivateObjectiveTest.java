package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.PrivateObjective;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ModelComponent.SchemesDeck;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrivateObjectiveTest
{
    PrivateObjective prTest1;
    PrivateObjective prTest2;

    SchemesDeck schemesDeckTest;
    SchemeCard[] schemesTest;
    SchemeCard schemeTest;

    Die DieTest1;
    Die DieTest2;
    Die DieTest3;
    Die DieTest4;
    Die DieTest5;


    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {
        prTest1 = new PrivateObjective(1);
        prTest2 = new PrivateObjective(5);

        schemesDeckTest = new SchemesDeck();
        schemesTest = schemesDeckTest.extractSchemes(2);
        schemeTest = schemesTest[1];
    }

    @Test
    public void checkGetColor()
    {
        boolean flag = true;
        if(prTest1.getColor()!=1)
            flag=false;
        if(prTest2.getColor()!=5)
            flag=false;
        Assertions.assertEquals(true, flag);
    }

    @Test
    public void checkCalculateBonus() throws InvalidIntArgumentException, GenericInvalidArgumentException
    {


    DieTest1 = new Die(1);
    DieTest2 = new Die(2);
    DieTest3 = new Die(1);
    DieTest4 = new Die(4);
    DieTest5 = new Die(1);

    DieTest1.setValueTest(4);
    DieTest3.setValueTest(2);
    DieTest5.setValueTest(5);

    schemeTest.setDie(DieTest1,1,0);
    schemeTest.setDie(DieTest2,1,1);
    schemeTest.setDie(DieTest3,1,2);
    schemeTest.setDie(DieTest4,1,3);
    schemeTest.setDie(DieTest5,1,4);


    Assertions.assertEquals(11, prTest1.calculateBonus(schemeTest));

    }
}
