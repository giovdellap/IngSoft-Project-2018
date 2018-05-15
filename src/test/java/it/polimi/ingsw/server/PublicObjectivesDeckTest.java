package it.polimi.ingsw.server;


import it.polimi.ingsw.server.ModelComponent.PublicObjective;
import it.polimi.ingsw.server.ModelComponent.PublicObjectivesDeck;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class PublicObjectivesDeckTest {


    PublicObjectivesDeck publicObjectivesDeckTest;
    PublicObjective[] publicObjectivesDeckTemp;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {

        publicObjectivesDeckTest = new PublicObjectivesDeck();
        publicObjectivesDeckTemp = publicObjectivesDeckTest.extractPubObjs();

    }


    @Test
    public void checkExtractPublicObjectives() {


        boolean flag = true;

        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (i!=j) {
                    if (publicObjectivesDeckTemp[i].getId() == publicObjectivesDeckTemp[j].getId())
                        flag = false;
                }
            }
        }

        Assertions.assertEquals(true, flag);
    }


}


