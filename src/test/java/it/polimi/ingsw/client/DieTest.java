package it.polimi.ingsw;
import it.polimi.ingsw.ModelComponentsMP.DieMP;
import org.junit.jupiter.api.*;



public class DieTest {

        DieMP dieTest;

        @BeforeAll
        public void setUp() {dieTest = new DieMP()}

        @Test
        public void checkColorTrue() {Assertions.assertEquals(true, dieTest.getColor()>0 || dieTest.getColor()<6);}
        public void checkColorFalse() {Assertions.assertEquals(false, dieTest.getColor()<0 || dieTest.getColor()>6);}
        public void checkValueTrue() {Assertions.assertEquals(true, dieTest.getValue()>0 || dieTest.getValue()<7);}
        public void checkValueFalse() {Assertions.assertEquals(false, dieTest.getValue()<0 || dieTest.getValue()>7);}
}
