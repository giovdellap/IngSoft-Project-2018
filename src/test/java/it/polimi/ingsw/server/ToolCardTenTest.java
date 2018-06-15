package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ModelComponent.SchemesDeck;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardNine;
import it.polimi.ingsw.server.ToolCards.ToolCardTen;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import java.util.ArrayList;

public class ToolCardTenTest {

    ToolCardTen toolCardTenTest;
    SchemeCard testScheme;
    SchemesDeck testDeck;
    DraftPool testDraft;
    Die testDie1;
    Die testDie2;
    Die testDie3;

    @BeforeEach
    public void setUp() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        toolCardTenTest = new ToolCardTen();
        testDeck = new SchemesDeck();
        testScheme = testDeck.extractSchemebyID(6);
        testScheme.setfb(2);
        testDraft = new DraftPool(4);
        testDie1 = new Die(1);
        testDie1.setValueTest(4);
        testDie2 = new Die(2);
        testDie2.setValueTest(6);
        testDie3 = new Die(3);
        testDie3.setValueTest(2);

        testScheme.setDie(testDie2,0,4);

    }

    @Test
    public void checkToolCardTenTest() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;

        testDraft.replaceDie(1,testDie1);
        toolCardTenTest.setDraft(testDraft);
        toolCardTenTest.setScheme(testScheme);

        flag = toolCardTenTest.checkToolCardTen(testDraft,1,testScheme,0,3);

        Assertions.assertEquals(true,flag);

    }

    @Test
    public void checkApplyModifiesOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDraft.replaceDie(2,testDie1);

        toolCardTenTest.setDraft(testDraft);
        toolCardTenTest.setScheme(testScheme);

        toolCardTenTest.applyModifies(2,0,3);

        Assertions.assertEquals(true,toolCardTenTest.getScheme().getDie(0,3).getValue()==3);

    }

    @Test
    public void checkApplyModifiesTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDraft.replaceDie(5,testDie2);

        toolCardTenTest.setDraft(testDraft);
        toolCardTenTest.setScheme(testScheme);

        toolCardTenTest.applyModifies(5,0,3);

        Assertions.assertEquals(true,toolCardTenTest.getScheme().getDie(0,3).getValue()==1);

    }

    @Test
    public void checkApplyModifiesThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDraft.replaceDie(1,testDie3);

        toolCardTenTest.setDraft(testDraft);
        toolCardTenTest.setScheme(testScheme);

        toolCardTenTest.applyModifies(1,0,3);

        Assertions.assertEquals(true,toolCardTenTest.getScheme().getDie(0,3).getValue()==5);

    }

}
