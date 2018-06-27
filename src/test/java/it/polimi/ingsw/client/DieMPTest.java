package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.Die;
import org.junit.jupiter.api.*;




public class DieMPTest
{
        private Die die;

        @BeforeEach
        public void SetUp()
        {
                die = new Die((int)(Math.random()*5)+1);
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

        @Test
        public void checksetValueTest() throws InvalidIntArgumentException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException {
                die.setValue(5);
                Assertions.assertEquals(5,die.getValue());
        }

        @Test
        public void checksetValueTestException(){
                try {
                        die.setValue(7);
                } catch (it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException e) {
                        Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
                }
        }

}

