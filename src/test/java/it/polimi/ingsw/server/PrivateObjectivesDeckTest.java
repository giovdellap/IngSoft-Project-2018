package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.PrivateObjective;
import it.polimi.ingsw.server.ModelComponent.PrivateObjectivesDeck;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PrivateObjectivesDeckTest
{
    PrivateObjectivesDeck deckTest;

    @BeforeEach
    public void setUp()
    {
        deckTest = new PrivateObjectivesDeck();
    }

    @Test
    public void checkExtract() throws InvalidIntArgumentException
    {

        PrivateObjective[] test = deckTest.extractPrObj(3);
        boolean flag = true;
        for(int i=0;i<3;i++)
            for(int j=0;j<3;j++)
                if(i!=j)
                    if(test[i].getColor()==test[j].getColor())
                        flag = false;
        Assertions.assertEquals(true,flag);
    }

    @Test
    public void checkExtractException()
    {
        try
        {
            PrivateObjective[] test = new PrivateObjective[9];
            deckTest.extractPrObj(9);
        }
        catch (InvalidIntArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(), "The int argument is invalid");
        }
    }
}
