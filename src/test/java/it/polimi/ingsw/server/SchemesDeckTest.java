package it.polimi.ingsw.server;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class SchemesDeckTest {

        private SchemeCard schemeTest;
        private SchemesDeck testDeck;
        private SchemeCard[] testTempDeck;
        int random;


        @BeforeEach
        public void setUp() {

            testDeck = new SchemesDeck();
            random = (int)(Math.random()*11+1);
            testTempDeck = new SchemeCard[random];

        }


    @Test
    public void checkForDifferentID() {

            boolean flag=false;
            testTempDeck = testDeck.extractSchemes(random);

        for(int i=0; i<testTempDeck.length-1;i++) {
            for (int j = 0; j < testTempDeck.length; j++) {

                if (testTempDeck[i].getID() != testTempDeck[j].getID())
                    flag=true;

            }
        }

        Assertions.assertEquals(true, flag);

    }

    @Test
    public void checkExtractSchemesID() {

            boolean flag = true;
            testTempDeck = testDeck.extractSchemes(random);

            for(int i=0;i<random;i++) {

                if(testTempDeck[i]!=testDeck.extractSchemebyID(testTempDeck[i].getID()))
                    flag = false;
            }

            Assertions.assertEquals(true,flag);

    }



}
