package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardOne;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class ToolCardOneTest {

    SchemeCard testScheme;
    int id = (int)(Math.random()*12+1);
    Die testDie;
    Die testDie2;
    int randomColor= (int)(Math.random()*5+1);
    ToolCardOne toolCardOneTest;
    DraftPool draftTest;
    SchemesDeck schemesDeckTest;
    Model model;

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FileNotFoundException {

        schemesDeckTest = new SchemesDeck();
        testScheme = schemesDeckTest.extractSchemebyID(6);
        testScheme = new SchemeCard(id);
        testDie = new Die(randomColor);
        testDie2 = new Die(randomColor);
        toolCardOneTest = new ToolCardOne();
        draftTest = new DraftPool(4);
        System.out.println(testScheme.getName(2));
        toolCardOneTest.setDraft(draftTest);
        toolCardOneTest.setScheme(testScheme);
        model = new Model(2);

    }

    @Test
    public void checkDieOneToSix() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;
        testDie.setValue(1);
        draftTest.replaceDie(3,testDie);
        flag = toolCardOneTest.checkToolCardOne(3,2,testScheme,draftTest,2,4);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkDieSixToOne() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;
        testDie.setValue(6);
        draftTest.replaceDie(3,testDie);
        flag = toolCardOneTest.checkToolCardOne(3,1,testScheme,draftTest,2,4);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkSchemeFullCell() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        boolean flag = true;

        testDie.setValue(4);
        testScheme.setDie(testDie,2,4);
        draftTest.replaceDie(3,testDie2);

        flag = toolCardOneTest.checkToolCardOne(3,1,testScheme,draftTest,2,4);

        Assertions.assertEquals(false, flag);


    }

}
