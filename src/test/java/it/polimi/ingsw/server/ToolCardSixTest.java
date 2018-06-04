package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardFive;
import it.polimi.ingsw.server.ToolCards.ToolCardSix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToolCardSixTest {

    int id = (int) (Math.random() * 11 + 1);
    Die testDie;
    Die testDie2;
    Die testDie3;
    Die testDie4;
    int randomColor = (int) (Math.random() * 4 + 1);
    ToolCardSix toolCardSixTest;
    DraftPool draftTest;
    SchemeCard scheme;
    SchemesDeck schemesDeck;

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMBERS 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FullDataStructureException, GenericInvalidArgumentException {

        testDie = new Die(1);
        testDie2 = new Die(2);
        testDie3 = new Die(3);
        testDie4 = new Die(4);
        toolCardSixTest = new ToolCardSix();
        draftTest = new DraftPool(4);
        schemesDeck = new SchemesDeck();
        scheme = schemesDeck.extractSchemebyID(6);
        scheme.setfb(2);

    }


    @Test
    public void checkToolCardSixTestOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;

        testDie = draftTest.returnDie(2);
        testDie.setValueTest(3);
        scheme.setDie(testDie2,0,3);

        flag = toolCardSixTest.checkToolCardSixSchemeCard(testDie,scheme,0,4);

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkToolCardSixTestTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = true;

        testDie = draftTest.returnDie(2);
        testDie.setValueTest(3);
        scheme.setDie(testDie2,0,4);

        flag = toolCardSixTest.checkToolCardSixSchemeCard(testDie,scheme,1,3);

        Assertions.assertEquals(false,flag);

    }

    @Test
    public void checkApplyModifiesToScheme() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie = draftTest.returnDie(2);
        testDie.setValueTest(3);
        scheme.setDie(testDie2,0,4);

        scheme = toolCardSixTest.ApplyModifiesToScheme(testDie,scheme,0,3);

        Assertions.assertEquals(true,scheme.getDie(0,3).equals(testDie));

    }


    @Test
    public void checkApplyModifiesToDraft() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie = draftTest.returnDie(2);
        testDie.setValueTest(3);

        draftTest = toolCardSixTest.ApplyModifiesToDraft(testDie,draftTest,2);

        Assertions.assertEquals(true,draftTest.returnDie(2).equals(testDie));

    }





}
