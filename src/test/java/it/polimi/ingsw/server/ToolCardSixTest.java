package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardSix;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class ToolCardSixTest {

    int id = (int) (Math.random() * 12 + 1);
    Die testDie;
    Die testDie2;
    Die testDie3;
    Die testDie4;
    int randomColor = (int) (Math.random() * 5 + 1);
    ToolCardSix toolCardSixTest;
    DraftPool draftTest;
    SchemeCard scheme;
    SchemesDeck schemesDeck;

    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMBERS 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FullDataStructureException, GenericInvalidArgumentException, FileNotFoundException, UnsupportedEncodingException {

        testDie = new Die(1);
        testDie.setValue(1);
        testDie2 = new Die(2);
        testDie2.setValue(2);
        testDie3 = new Die(3);
        testDie4 = new Die(4);
        toolCardSixTest = new ToolCardSix();
        draftTest = new DraftPool(4);
        schemesDeck = new SchemesDeck();
        scheme = schemesDeck.extractSchemebyID(6);
        scheme.setfb(2);
        draftTest.replaceDie(1,testDie);
        toolCardSixTest.setScheme(scheme);
        toolCardSixTest.setDraft(draftTest);

    }


    @Test
    public void checkApplyModifiesToScheme() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie = draftTest.returnDie(2);
        testDie.setValue(3);
        scheme.setDie(testDie2,0,4);

        toolCardSixTest.setScheme(scheme);
        toolCardSixTest.setDraft(draftTest);

        toolCardSixTest.applyModifiesToScheme(2,0,3);

        Assertions.assertEquals(true,toolCardSixTest.getScheme().getDie(0,3).equals(testDie));

    }


    @Test
    public void checkApplyModifiesToDraft() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        testDie = draftTest.returnDie(2);
        testDie.setValue(3);

        toolCardSixTest.setScheme(scheme);
        toolCardSixTest.setDraft(draftTest);

        toolCardSixTest.applyModifiesToDraft(2);

        Assertions.assertEquals(true,toolCardSixTest.getDraft().returnDie(2).equals(testDie));

    }





}
