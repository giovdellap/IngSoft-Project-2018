package it.polimi.ingsw.client;
import it.polimi.ingsw.client.ModelComponentsMP.DieMP;
import org.junit.jupiter.api.*;




public class DieMPTest {

        private static DieMP dieTest;

        @BeforeAll
        public static void setUp()
        {
            dieTest = new DieMP((int)(Math.random()*5+1));
            dieTest.throwDie();
        }

        @Test
        public void checkColorTrue() {Assertions.assertEquals(true, dieTest.getColor()>0 && dieTest.getColor()<6, "controlloColore");}

        @Test
        public void checkColorFalse() {Assertions.assertEquals(false, dieTest.getColor()<0 || dieTest.getColor()>6, "controlloColore");}

        @Test
        public void checkValueTrue() {Assertions.assertEquals(true, dieTest.getValue()>0 && dieTest.getValue()<7, "controlloValore");}

        @Test
        public void checkValueFalse() {Assertions.assertEquals(false, dieTest.getValue()<0 || dieTest.getValue()>7, "controlloValore");}
}

