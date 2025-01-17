package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.PublicObjective;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;


public class PublicObjectiveTest {

    private PublicObjective publicObjectiveTest;
    private PublicObjective toTest;
    private int bonusTest;
    private SchemeCard sc1;
    private SchemeCard sc2;
    private SchemesDeck testSD;

    int idTest = (int)(Math.random()*10+1);


    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FileNotFoundException, UnsupportedEncodingException {

        publicObjectiveTest = new PublicObjective(idTest);
        bonusTest = publicObjectiveTest.getBonus();
        testSD = new SchemesDeck();

        sc1=testSD.extractSchemebyID(1);
        sc1.setfb(1);


    }

    @Test
    public void checkInvalidIntArgumentException() {

        boolean flag = false;

        try {

            publicObjectiveTest = new PublicObjective(49);

        } catch (InvalidIntArgumentException e) {

            if (e.getMessage().equals("The int argument is invalid"))
                flag = true;
        }

        Assertions.assertEquals(true,flag);
    }


    @Test
    public void checkGetID() {

        Assertions.assertEquals(true,publicObjectiveTest.getId()==idTest);
    }

    @Test
    public void checkGetBonus() {

        Assertions.assertEquals(true,publicObjectiveTest.getBonus()==bonusTest);
    }

    @Test
    public void checkDisabledPublicObjective() {

        publicObjectiveTest.disableScheme();
        Assertions.assertEquals(true,publicObjectiveTest.checkDisabled());

    }


}
