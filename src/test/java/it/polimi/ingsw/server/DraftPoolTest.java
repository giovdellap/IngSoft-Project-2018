package it.polimi.ingsw.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class DraftPoolTest
{
    private DraftPool testDP;
    private RoundDice testRD;
    int players;
    int dim;

    @BeforeEach
    public void setUp()
    {
        players=3;
        dim=players*2+1;
        testDP = new DraftPool(players);
    }

    @Test
    public void testInitialUpdate()
    {
        boolean flag = true;
        System.out.println("Check 1");
        Die tempDie = new Die(0);
        System.out.println("Check 2");
        for(int i=0;i<dim;i++)
        {
            System.out.println("Check 3");
            tempDie=testDP.returnDie(i);
            System.out.println("Check 4");

            if(tempDie==null)
                flag=false;
            if(tempDie.getColor()<1||tempDie.getColor()>5)
                flag=false;
            if(tempDie.getValue()<1||tempDie.getValue()>6)
                flag=false;
        }
        Assertions.assertEquals(true, flag);
    }
}
