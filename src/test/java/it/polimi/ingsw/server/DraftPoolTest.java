package it.polimi.ingsw.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/*
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
    public void testUpdateNoDice() {

        for(int i=0;i<dim;i++) {
            testDP.pickUpDie(i);

        }
        boolean flag = true;
        testDP.updateDraftNoDice();
        Die[] temp = new Die[dim];
        for(int i=0;i<dim;i++)
        {
            temp[i] = testDP.returnDie(i);
            testDP.pickUpDie(i);
        }
        for(int i=0;i<dim;i++)
        {
            if(temp[i]==null)
                flag=false;
            if(temp[i].getValue()<1||temp[i].getValue()>6)
                flag=false;
            if(temp[i].getColor()<1||temp[i].getColor()>5)
                flag=false;
            if(testDP.returnDie(i)!=null)
                flag=false;
        }
        Assertions.assertEquals(true, flag);

    }

    @Test
    public void checkReturnDie()
    {
        Die tempDie = testDP.returnDie(0);
        Assertions.assertEquals(null, tempDie);
    }
}
*/