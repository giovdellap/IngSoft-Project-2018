package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import it.polimi.ingsw.client.ModelComponentsMP.PublicObjectiveMP;


import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class PublicObjectiveMPTest {

    private PublicObjectiveMP publicObjectiveTest;
    private SchemeCard sc1;
    private SchemesDeck testSD;

    int idTest = (int)(Math.random()*10+1);

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FileNotFoundException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, UnsupportedEncodingException {
        publicObjectiveTest = new PublicObjectiveMP(idTest);
        testSD = new SchemesDeck();
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
