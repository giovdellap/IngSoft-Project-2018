package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;

import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemesDeckMP;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PublicObjectiveMPTest {

    private PublicObjectiveMP publicObjectiveTest;
    private SchemeCardMP sc1;
    private SchemesDeckMP testSD;

    int idTest = (int)(Math.random()*10+1);

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException
    {
        publicObjectiveTest = new PublicObjectiveMP(idTest);
        testSD = new SchemesDeckMP();
        sc1=testSD.extractSchemebyID(1);
        sc1.setfb(1);
    }

    @Test
    public void checkInvalidIntArgumentException()
    {
        boolean flag = false;
        try
        {
            publicObjectiveTest = new PublicObjectiveMP(49);
        } catch(InvalidIntArgumentException e) {

            if(e.getMessage().equals("The int argument is invalid"))
                flag = true;
        }

        Assertions.assertEquals(true,flag);
    }


    @Test
    public void checkGetID()
    {
        Assertions.assertEquals(true,publicObjectiveTest.getId()==idTest);
    }


}
