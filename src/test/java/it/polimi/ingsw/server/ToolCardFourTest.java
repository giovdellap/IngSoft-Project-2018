package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardFour;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class ToolCardFourTest {

    SchemeCard testScheme;
    int id = (int) (Math.random() * 12 + 1);
    Die testDie;
    Die testDie2;
    Die testDie3;
    Die testDie4;
    int randomColor = (int) (Math.random() * 5 + 1);
    ToolCardFour toolCardFourTest;
    SchemesDeck schemesDeckTest;

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FileNotFoundException {

        schemesDeckTest = new SchemesDeck();
        testScheme = schemesDeckTest.extractSchemebyID(6);
        testDie = new Die(randomColor);
        testDie2 = new Die(randomColor);
        testDie3 = new Die(randomColor);
        testDie4 = new Die(randomColor);
        toolCardFourTest = new ToolCardFour();
        testScheme.setfb(2);
        toolCardFourTest.setTempScheme(testScheme);

    }

    @Test
    public void checkToolCardFourTestOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;

        flag = toolCardFourTest.checkToolCardFour(0,0,1,3,testScheme,2,3,3,1);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkToolCardFourTestTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;

        testScheme.setDie(testDie,0,0);
        testScheme.setDie(testDie2,0,1);
        testScheme.setDie(testDie3,0,3);
        testScheme.setDie(testDie4,0,4);

        flag = toolCardFourTest.checkToolCardFour(0,0,0,1,testScheme,0,3,0,4);

        Assertions.assertEquals(false,flag);



    }

    @Test
    public void checkToolCardFourTestThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;
        testDie = new Die(2);
        testDie2 = new Die(4);
        testDie3 = new Die(2);
        testDie4 = new Die(4);

        testScheme.setDie(testDie,0,0);
        testScheme.setDie(testDie2,1,0);
        testScheme.setDie(testDie3,0,3);
        testScheme.setDie(testDie4,0,4);

        flag = toolCardFourTest.checkToolCardFour(0,0,1,0,testScheme,1,3,1,4);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkToolCardFourTestFour() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;

        testDie = new Die(2);
        testDie2 = new Die(4);
        testDie3 = new Die(3);
        testDie4 = new Die(5);

        testDie.setValue(2);
        testDie2.setValue(3);
        testDie3.setValue(2);
        testDie4.setValue(3);

        testScheme.setDie(testDie,2,4);
        testScheme.setDie(testDie2,3,4);
        testScheme.setDie(testDie3,0,0);
        testScheme.setDie(testDie4,1,0);

        flag = toolCardFourTest.checkToolCardFour(0,0,1,0,testScheme,1,4,0,4);

        Assertions.assertEquals(false,flag);


    }

    @Test
    public void checkApplyModifiesOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie = new Die(2);
        testDie2 = new Die(4);
        testDie3 = new Die(3);
        testDie4 = new Die(5);

        testDie.setValue(2);
        testDie2.setValue(3);
        testDie3.setValue(2);
        testDie4.setValue(3);

        testScheme.setDie(testDie,2,4);
        testScheme.setDie(testDie2,3,4);
        testScheme.setDie(testDie3,0,0);
        testScheme.setDie(testDie4,1,0);

        testScheme = toolCardFourTest.applyModifies(0,0,1,0,0,3,0,4);

        Assertions.assertEquals(true, testScheme.getDie(0,0).isDisabled() && testScheme.getDie(1,0).isDisabled());

    }

    @Test
    public void checkApplyModifiesTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;

        testDie = new Die(2);
        testDie2 = new Die(4);
        testDie3 = new Die(3);
        testDie4 = new Die(5);

        testDie.setValue(2);
        testDie2.setValue(3);
        testDie3.setValue(2);
        testDie4.setValue(3);

        testScheme.setDie(testDie,2,4);
        testScheme.setDie(testDie2,3,4);
        testScheme.setDie(testDie3,0,0);
        testScheme.setDie(testDie4,1,0);

        int value1 = testDie3.getValue();
        int value2 = testDie4.getValue();

        toolCardFourTest.setTempScheme(testScheme);

        testScheme = toolCardFourTest.applyModifies(0,0,1,0,0,3,0,4);

        if(testScheme.getDie(0,3).getColor()==testDie3.getColor())
            if(testScheme.getDie(0,3).getValue()==value1)
                if(testScheme.getDie(0,4).getColor()==testDie4.getColor())
                    if(testScheme.getDie(0,4).getValue()==value2)
                        flag = true;


        Assertions.assertEquals(true,flag);
    }

}
