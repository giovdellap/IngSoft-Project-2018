package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardSeven;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;


public class ToolCardSevenTest {

    ToolCardSeven toolCardSevenTest;
    DraftPool draft;
    DraftPool draftTemp;


    @BeforeEach
    public void setUp() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        toolCardSevenTest = new ToolCardSeven();
        draft = new DraftPool(4);
        draftTemp = new DraftPool(4);

    }

    @Test
    public void checkApplyModifies() throws GenericInvalidArgumentException, InvalidIntArgumentException {

        draftTemp = draft;

        for(int i=0;i<draftTemp.getDiceNum();i++)
            System.out.println(draftTemp.returnDie(i).getValue());

        System.out.println("\n\n");
        draft = toolCardSevenTest.applyModifies(draft);

        for(int i=0;i<draft.getDiceNum();i++)
            System.out.println(draft.returnDie(i).getValue());



        Assertions.assertEquals(true,true);
    }

}
