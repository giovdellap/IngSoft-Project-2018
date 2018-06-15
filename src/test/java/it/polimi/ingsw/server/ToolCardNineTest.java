package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ModelComponent.SchemesDeck;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardNine;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToolCardNineTest {

    ToolCardNine toolCardNineTest;
    SchemeCard testScheme;
    SchemesDeck testDeck;
    DraftPool testDraft;
    Die testDie1;
    Die testDie2;
    Die testDie3;

    @BeforeEach
    public void setUp() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        toolCardNineTest = new ToolCardNine();
        testDeck = new SchemesDeck();
        testScheme = testDeck.extractSchemebyID(6);
        testScheme.setfb(2);
        testDraft = new DraftPool(4);

        testDie1 = new Die(1);
        testDie2 = new Die(2);
        testDie3 = new Die(3);

        toolCardNineTest.setDraft(testDraft);
        toolCardNineTest.setScheme(testScheme);

    }

    @Test
    public void checkToolCardNineOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        boolean flag = false;

        flag = toolCardNineTest.checkToolCardNine(testDraft,1,testScheme,0,4);

        Assertions.assertEquals(true,flag);

    }


    @Test
    public void checkApplyModifiesOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie1 = testDraft.returnDie(1);
        toolCardNineTest.applyModifies(1,0,4);

        Assertions.assertEquals(true,toolCardNineTest.getScheme().getDie(0,4).equals(testDie1));
    }


    @Test
    public void checkApplyModifiesTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        toolCardNineTest.applyModifies(1,0,4);

        Assertions.assertEquals(false,toolCardNineTest.getScheme().getDie(0,4).equals(toolCardNineTest.getDraft().returnDie(1)));
    }

}
