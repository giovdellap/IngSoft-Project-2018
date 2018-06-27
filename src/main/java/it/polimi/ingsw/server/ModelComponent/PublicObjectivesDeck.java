package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;


public class PublicObjectivesDeck {


    private PublicObjective[] fullDeck;
    int id;

    /**
     * PublicObjectivesDeck Constructor
     * @throws InvalidIntArgumentException
     */

    public PublicObjectivesDeck() throws InvalidIntArgumentException
    {
        fullDeck = new PublicObjective[10];

        for (id=1;id<11;id++)
            fullDeck[id-1] = new PublicObjective(id);

    }

    /**
     * extracts 3 random public objectives
     * @return public objective array
     */
    public PublicObjective[] extractPubObjs() {

        PublicObjective[] tempDeck = new PublicObjective[3];

        for (int i = 0; i < 3; i++) {
            boolean exists = false;
            int random = 0;

            while (!exists) {

                random = (int) (Math.random()*9+1);

                if (!fullDeck[random].checkDisabled()) {
                    tempDeck[i] = fullDeck[random];
                    fullDeck[random].disableScheme();
                    exists = true;
                }

            }

          }

          return tempDeck;

        }


    }
