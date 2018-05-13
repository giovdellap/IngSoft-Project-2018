package it.polimi.ingsw.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrivateObjectiveTest
{
    PrivateObjective prTest1;
    PrivateObjective prTest2;

    @BeforeEach
    public void setUp()
    {
        prTest1 = new PrivateObjective(1);
        prTest2 = new PrivateObjective(5);
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
}
