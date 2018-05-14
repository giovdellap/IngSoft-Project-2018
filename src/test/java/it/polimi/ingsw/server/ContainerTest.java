package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.*;

import java.util.Timer;


public class ContainerTest
{
    private DiceContainer container;
    private Die[] testVector;
    private int testCase=9;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException
    {
        container = new DiceContainer();
        Die[] testVector = new Die[testCase];

    }

    @Test
    public void checkNotNull() throws InvalidIntArgumentException
    {
        testVector = container.throwDice(testCase);
        boolean flag = true;
        for(int i=0;i<testCase;i++)
        {
            if(testVector[i]==null)
                flag=false;
        }

        Assertions.assertEquals(true, flag);
    }

    @Test
    public void checkInvalidThrow()
    {
        boolean flag = false;

        try {

            testVector = container.throwDice(12);
        }

        catch (InvalidIntArgumentException e)
        {
            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }

        Assertions.assertEquals(true,flag);

    }

    //COLORI: 1 GIALLO/2 ROSSO/3 VERDE/4 BLU/ 5 VIOLA

    @Test
    public void testThatDice() throws InvalidIntArgumentException
    {
        //checks that extracts 18 dices for each color
        int red=0;
        int blue=0;
        int purple=0;
        int yellow=0;
        int green=0;
        int temp;
        for(int i=0;i<10;i++)
        {
            testVector = container.throwDice(testCase);
            for(int n=0;n<testCase;n++)
            {
                temp=testVector[n].getColor();
                if(temp==1)
                    yellow++;
                if(temp==2)
                    red++;
                if(temp==3)
                    green++;
                if(temp==4)
                    blue++;
                if(temp==5)
                    purple++;
            }
        }
        boolean flag=false;
        if(red==yellow)
            if(yellow==blue)
                if(blue==purple)
                    if(purple==green)
                        if(green==18)
                            flag=true;
        Assertions.assertEquals(true,flag);

    }


}
