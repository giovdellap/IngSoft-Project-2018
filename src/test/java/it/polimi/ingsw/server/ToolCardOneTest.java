package it.polimi.ingsw.server;

import com.sun.xml.internal.ws.policy.AssertionSet;
import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardOne;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToolCardOneTest {

    SchemeCard testScheme;
    int id = (int)(Math.random()*11+1);
    Die testDie;
    int randomColor= (int)(Math.random()*4+1);
    ToolCardOne toolCardOneTest;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {
        testScheme = new SchemeCard(id);
        testDie = new Die(randomColor);
        toolCardOneTest = new ToolCardOne();
    }

    @Test
    public void checkDieOneToSix() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;
        testDie.setValueTest(1);
        flag = toolCardOneTest.checkToolCardOne(testDie,2,testScheme,2,4);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkDieSixToOne() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;
        testDie.setValueTest(6);
        flag = toolCardOneTest.checkToolCardOne(testDie,1,testScheme,2,4);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkFullCellException() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        testDie.setValueTest(4);
        testScheme.setDie(testDie,2,4);


        try {

            toolCardOneTest.checkToolCardOne(testDie,1,testScheme,2,4);

        }

        catch (GenericInvalidArgumentException e) {

            Assertions.assertEquals(true,e.getMessage().equals("Generic invalid argument"));

        }

    }


    @Test
    public void checkApplyModifies() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        SchemeCard testScheme2;
        testDie.setValueTest(4);

        testScheme2 = toolCardOneTest.applyModifies(testScheme,testDie,2,4);

        Assertions.assertEquals(true,testDie.equals(testScheme2.getDie(2,4)));

    }

}
