package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardEight;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class ToolCardEightTest {

    ToolCardEight toolCardEightTest;
    SchemesDeck schemesDeckTest;
    SchemeCard schemeCard;
    DraftPool draft;
    Die dieTest1;
    Die dieTest2;

    @BeforeEach
    public void setUp() throws GenericInvalidArgumentException, InvalidIntArgumentException, FileNotFoundException, UnsupportedEncodingException {

        schemesDeckTest = new SchemesDeck();
        schemeCard = schemesDeckTest.extractSchemebyID(6);
        schemeCard.setfb(2);
        toolCardEightTest = new ToolCardEight();
        draft = new DraftPool(4);
        dieTest1 = new Die(2);
        dieTest2 = new Die(4);


        toolCardEightTest.setDraft(draft);
        toolCardEightTest.setScheme(schemeCard);

    }

    @Test
    public void checkApplyModifiesOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        draft.replaceDie(1,dieTest2);
        schemeCard.setDie(dieTest1,0,0);
        toolCardEightTest.applyModifies(1,1,0);

        Assertions.assertEquals(true,toolCardEightTest.getScheme().getDie(1,0).equals(dieTest2));

    }

    @Test
    public void checkApplyModifiesTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        draft.replaceDie(1,dieTest2);
        schemeCard.setDie(dieTest1,0,0);
        toolCardEightTest.applyModifies(1,1,0);

        Assertions.assertEquals(false,toolCardEightTest.getDraft().returnDie(1).equals(dieTest2));

    }

}
