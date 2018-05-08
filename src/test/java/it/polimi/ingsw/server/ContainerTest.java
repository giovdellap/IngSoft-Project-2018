<<<<<<< HEAD
package it.polimi.ingsw.server;

import org.junit.jupiter.api.*;


public class ContainerTest
{
    private static DiceContainer container;
    public static Die[] testVector;

    @BeforeAll
    public static void setUp()
    {
        container = new DiceContainer();
        Die[] testVector = container.throwDice(5);

    }

    @Test
    public void testSize()
    {
        Assertions.assertEquals(true, testVector.length==5);
    }
=======
package it.polimi.ingsw;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

public class ContainerTest
{
    DiceContainer container;

    @BeforeAll
    public void setUp()
    {
        container = new DiceContainer();
    }

    @Test
    public void test
>>>>>>> 8450c4b95595264bc4cf0238cd708dfb324729ab

}
