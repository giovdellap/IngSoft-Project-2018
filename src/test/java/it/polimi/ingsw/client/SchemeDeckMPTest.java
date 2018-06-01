package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;

import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemesDeckMP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SchemeDeckMPTest
{
    private SchemeCardMP schemeTest;
    private SchemesDeckMP testDeck;
    private SchemeCardMP[] testTempDeck;
    int random;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException
    {
        testDeck = new SchemesDeckMP();
        random = (int)(Math.random()*11+1);
        testTempDeck = new SchemeCardMP[random];
    }

    @Test
    public void checkForDifferentID() throws InvalidIntArgumentException
    {
        boolean flag=true;
        testTempDeck = testDeck.extractSchemes(6);

        for(int i=0; i<testTempDeck.length-1;i++) {
            for (int j = 0; j < testTempDeck.length; j++) {
                if(i!=j)
                    if (testTempDeck[i].getID() == testTempDeck[j].getID())
                        flag=false;

            }
        }
        Assertions.assertEquals(true, flag);
    }

    @Test
    public void checkExtractSchemesException() throws InvalidIntArgumentException
    {
        boolean flag = false;

        try {
            testTempDeck=testDeck.extractSchemes(10);
        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;
        }

        Assertions.assertEquals(true,flag);
    }


    @Test
    public void checkExtractSchemesID() throws InvalidIntArgumentException
    {
        boolean flag = true;
        testTempDeck = testDeck.extractSchemes(6);

        for(int i=0;i<6;i++) {

            if(testTempDeck[i]!=testDeck.extractSchemebyID(testTempDeck[i].getID()))
                flag = false;
        }

        Assertions.assertEquals(true,flag);
    }


    @Test
    public void checkExtractSchemesIdException() throws InvalidIntArgumentException
    {
        boolean flag = false;

        try {
            testDeck.extractSchemebyID(17);
        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;

        }
        Assertions.assertEquals(true,flag);
    }
}
