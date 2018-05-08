package it.polimi.ingsw;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DieTest
{
    Die die;

    @BeforeAll
    public void SetUp()
    {
        die = new Die();
    }

    @Test
    public void checkColor()
    {
        Assertions.assertEquals(true, die.getColor()>0 && die.getColor()<6);
    }
    public void checkValue()
    {
        Assertions.assertEquals(true, die.getValue()>0 && die.getValue()<7);
    }
    public void checkThrow()
    {
        die.throwDie();
        Assertions.assertEquals(true, die.getValue()>0 && die.getValue()<7);
    }
    public void checkDisabling()
    {
        die.disableDie();
        Assertions.assertEquals(0, die.getValue());
    }

}
