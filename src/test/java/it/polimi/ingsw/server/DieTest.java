package it.polimi.ingsw.server;

import org.junit.jupiter.api.*;

public class DieTest
{
    private Die die;

    @BeforeEach
    public  void SetUp()
    {
        die = new Die((int)Math.random()*5+1);
        die.throwDie();
    }

    @Test
    public void checkColor()
    {
        Assertions.assertEquals(true, die.getColor()>0&&die.getColor()<6);
    }

    @Test
    public void checkValue()
    {
        Assertions.assertEquals(true, die.getValue()>0 && die.getValue()<7);
    }

    @Test
    public void checkThrow()
    {
        die.throwDie();
        Assertions.assertEquals(true, die.getValue()>0 && die.getValue()<7);
    }

    @Test
    public void checkDisabling()
    {
        die.disableDie();
        Assertions.assertEquals(0, die.getValue());
    }

}
