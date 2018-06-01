package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;


public class PublicObjectivesDeck {


    private PublicObjective[] fullDeck;
    int id;


    public PublicObjectivesDeck() throws InvalidIntArgumentException
    {

        fullDeck = new PublicObjective[10];

        for (id=1;id<11;id++) {
            fullDeck[id-1] = new PublicObjective(id);
        }


    }


    public PublicObjective[] extractPubObjs() {

        PublicObjective[] tempDeck = new PublicObjective[3];

        int tempCounter = 0;

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
