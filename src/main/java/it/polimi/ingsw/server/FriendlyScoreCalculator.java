package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ScorePlayer;
import it.polimi.ingsw.commons.FcknSimpleLogger;
import it.polimi.ingsw.server.ModelComponent.PublicObjective;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardUsageRecord;

import java.util.ArrayList;

public class FriendlyScoreCalculator
{
    private ScoreEvent event;
    private ArrayList<ScorePlayer> scorePlayers;

    private Model finalModel;
    private ArrayList<Player> players;

    private FcknSimpleLogger logger;

    public FriendlyScoreCalculator(Model model, ArrayList<Player> players)
    {
        finalModel=model;
        this.players=players;
        scorePlayers = new ArrayList<ScorePlayer>();
        event = new ScoreEvent();
        logger = new FcknSimpleLogger(0, false);
    }

    public ScoreEvent calculateScore() throws InvalidIntArgumentException, GenericInvalidArgumentException {
        for (int i = 0; i < players.size(); i++) {

            ScorePlayer tempPlayer = new ScorePlayer(players.get(i).getId(), players.get(i).getName());
            logger.log(" ");
            logger.log("Player " + Integer.toString(i) + " " + players.get(i).getName());

            //private objective bonus calculation
            tempPlayer.setPrivObj(finalModel.getPrivateObjective(i).calculateBonus(finalModel.getSchemebyIndex(i)));
            logger.log("Player " + Integer.toString(i) + " private objective bonus: " + Integer.toString(tempPlayer.getPrivObj()));

            //public objectives bonus calculation
            for (int j = 0; j < 3; j++) {
                PublicObjective tempPubObj = finalModel.getPubObj(j);
                tempPubObj.setBonus(finalModel.getSchemebyIndex(j));
                tempPlayer.setPubObj(j, tempPubObj.getBonus());
                logger.log("Player " + Integer.toString(i) + " public objective " + Integer.toString(j + 1) + " bonus: " + Integer.toString(tempPlayer.getPubObj(j)));
            }

            //tokens bonus calculation
            tempPlayer.setTokens(players.get(i).getTokens());
            logger.log("Player " + Integer.toString(i) + " tokens: " + Integer.toString(tempPlayer.getTokens()));

            //minus calculation
            int minus = 0;
            for (int x = 0; x < 4; x++)
                for (int y = 0; y < 5; y++)
                    if (finalModel.getSchemebyIndex(i).getDie(x, y).isDisabled())
                        minus++;
            tempPlayer.setMinus(minus);
            logger.log("Player " + Integer.toString(i) + " minus: " + Integer.toString(tempPlayer.getMinus()));

            //tot calculation
            int tot = tempPlayer.getPrivObj() + tempPlayer.getPubObj(0) + tempPlayer.getPubObj(1) + tempPlayer.getPubObj(2) + tempPlayer.getTokens() - tempPlayer.getMinus();
            tempPlayer.setTot(tot);
            logger.log("Player " + Integer.toString(i) + " points: " + Integer.toString(tempPlayer.getTot()));

            scorePlayers.add(tempPlayer);
        }


        int max = 0;
        int index = 0;
        for (int i = 0; i < scorePlayers.size(); i++) {
            for (int j = 0; j < scorePlayers.size(); i++)
                if (scorePlayers.get(j).getTot() > max) {
                    index = j;
                    max = scorePlayers.get(j).getTot();
                }
            event.addPlayer(scorePlayers.get(index));
            scorePlayers.remove(index);
            logger.log(Integer.toString(i + 1) + "  Player " + event.getPlayers().get(i).getName() + " points: " + Integer.toString(event.getPlayers().get(i).getTot()));
        }

        return event;
        }
    }



}
