package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardEleven;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class ToolCardElevenTest {

    SchemeCard scheme;
    SchemesDeck deck;
    DraftPool draft;
    Die toPlace1;
    Die toPlace2;
    ToolCardEleven toolCardElevenTest;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, GenericInvalidArgumentException, FileNotFoundException {

        deck = new SchemesDeck();
        scheme = deck.extractSchemebyID(6);
        scheme.setfb(2);
        draft = new DraftPool(4);
        toPlace1 = new Die(1);
        toPlace2 = new Die(2);
        scheme.setDie(toPlace1,0,0);
        toolCardElevenTest = new ToolCardEleven();

        toolCardElevenTest.setDraft(draft);
        toolCardElevenTest.setScheme(scheme);
    }

    @Test
    public void checkApplyModifiesOne() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        toolCardElevenTest.applyModifiesOne(toPlace2,2);
        Assertions.assertEquals(true,toolCardElevenTest.getDraft().returnDie(2).equals(toPlace2));

    }

    @Test
    public void checkApplyModifiesTwo() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        toolCardElevenTest.applyModifiesTwo(toPlace2,1,0,2);
        Assertions.assertEquals(true,toolCardElevenTest.getScheme().getDie(1,0).equals(toPlace2));

    }

    @Test
    public void checkApplyModifiesThree() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        toolCardElevenTest.applyModifiesTwo(toPlace2,1,0,2);
        Assertions.assertEquals(false,toolCardElevenTest.getDraft().returnDie(2).equals(toPlace2));

    }

}
