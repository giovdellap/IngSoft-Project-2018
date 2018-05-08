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


}
