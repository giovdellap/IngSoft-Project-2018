package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DieMP;
import org.junit.jupiter.api.*;




public class DieMPTest
{
        private DieMP die;

        @BeforeEach
        public void SetUp()
        {
                die = new DieMP((int)(Math.random()*5)+1);
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
        public void checksetValueTest() throws InvalidIntArgumentException {
                die.setValueTest(5);
                Assertions.assertEquals(5,die.getValue());
        }

        @Test
        public void checksetValueTestException(){
                try {
                        die.setValueTest(7);
                } catch (InvalidIntArgumentException e) {
                        Assertions.assertEquals(e.getMessage(),"The int argument is invalid");
                }
        }

        @Test
        public void checkToString() {

                int tempColor = die.getColor();
                int tempValue = die.getValue();
                String tempString1 = die.toString();

                if (tempColor==1)
                        Assertions.assertEquals(true,tempString1.equals("Die color: yellow , Die value: " + Integer.toString(tempValue)));

                if (tempColor==2)
                        Assertions.assertEquals(true,tempString1.equals("Die color: red , Die value: " + Integer.toString(tempValue)));

                if (tempColor==3)
                        Assertions.assertEquals(true,tempString1.equals("Die color: green , Die value: " + Integer.toString(tempValue)));

                if (tempColor==4)
                        Assertions.assertEquals(true,tempString1.equals("Die color: blue , Die value: " + Integer.toString(tempValue)));

                if (tempColor==5)
                        Assertions.assertEquals(true,tempString1.equals("Die color: violet , Die value: " + Integer.toString(tempValue)));

        }



}

