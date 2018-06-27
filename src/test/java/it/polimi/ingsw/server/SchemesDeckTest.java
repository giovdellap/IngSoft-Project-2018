package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class SchemesDeckTest {

        private SchemeCard schemeTest;
        private SchemesDeck testDeck;
        private SchemeCard[] testTempDeck;
        int random;


        @BeforeEach
        public void setUp() throws InvalidIntArgumentException {

            testDeck = new SchemesDeck();
            random = (int)(Math.random()*11+1);
            testTempDeck = new SchemeCard[random];

        }


    @Test
    public void checkForDifferentID() throws InvalidIntArgumentException, FileNotFoundException {

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
    public void checkExtractSchemesException() throws InvalidIntArgumentException {

        boolean flag = false;

        try {
            testTempDeck=testDeck.extractSchemes(10);
        }

        catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        Assertions.assertEquals(true,flag);
    }


    @Test
    public void checkExtractSchemesID() throws InvalidIntArgumentException, FileNotFoundException {

            boolean flag = true;
            testTempDeck = testDeck.extractSchemes(6);

            for(int i=0;i<6;i++) {

                if(testTempDeck[i]!=testDeck.extractSchemebyID(testTempDeck[i].getID()))
                    flag = false;
            }

            Assertions.assertEquals(true,flag);

    }


    @Test
    public void checkExtractSchemesIdException() throws InvalidIntArgumentException {

            boolean flag = false;

            try {
               testDeck.extractSchemebyID(17);
            }

            catch (InvalidIntArgumentException e) {

                if (e.getMessage().equals("The int argument is invalid"))
                    flag = true;

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }

        Assertions.assertEquals(true,flag);

    }

}
