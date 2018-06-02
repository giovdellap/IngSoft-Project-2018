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

        testDie = new Die(0);
        testDie2 = new Die(0);
        testDie3 = new Die(0);
        testDie4 = new Die(0);
        toolCardSixTest = new ToolCardSix();
        draftTest = new DraftPool(4);
        schemesDeck = new SchemesDeck();
        scheme = schemesDeck.extractSchemebyID(6);
        scheme.setfb(2);

    }


}
