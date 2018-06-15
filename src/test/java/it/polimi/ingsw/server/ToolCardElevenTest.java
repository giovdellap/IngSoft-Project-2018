package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.*;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ToolCardElevenTest {

    DiceContainer container;
    SchemeCard scheme;
    SchemesDeck deck;
    DraftPool draft;
    Die toPlace1;
    Die toPlace2;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {

        container = new DiceContainer();
        deck = new SchemesDeck();
        scheme = deck.extractSchemebyID(6);
        scheme.setfb(2);
        draft = new DraftPool(4);
        toPlace1 = new Die(1);
        toPlace2 = new Die(2);

    }

    @Test
    public void checkToolCardEleven() throws GenericInvalidArgumentException, InvalidIntArgumentException {








    }

}
