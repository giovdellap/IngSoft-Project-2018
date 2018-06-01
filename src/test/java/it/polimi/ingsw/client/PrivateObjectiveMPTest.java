package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PrivateObjectiveMP;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;

public class PrivateObjectiveMPTest
{
    PrivateObjectiveMP prTest1;
    PrivateObjectiveMP prTest2;



    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {
        prTest1=new PrivateObjectiveMP(4);
   }

   @Test
    public void checksetIdException()
   {
       try {
           prTest2= new PrivateObjectiveMP(6);
       }
       catch (InvalidIntArgumentException e)
       {
           Assertions.assertEquals(e.getMessage(), "The int argument is invalid");

       }
   }

   @Test
    public void checkColor(){

       boolean flag=false;
       if (prTest1.getColor()==4) flag=true;
       Assertions.assertEquals(true,flag);
   }


}
