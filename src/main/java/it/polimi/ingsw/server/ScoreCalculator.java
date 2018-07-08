package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ScorePlayer;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.server.ModelComponent.PublicObjective;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class ScoreCalculator
{
    private ScoreEvent event;
    private ArrayList<ScorePlayer> scorePlayers;

    private Model finalModel;
    private ArrayList<PlayerThread> players;

    private SimpleLogger logger;

    public ScoreCalculator(Model model, ArrayList<PlayerThread> players)
    {
        finalModel=model;
        this.players=players;
        scorePlayers = new ArrayList<ScorePlayer>();
        event = new ScoreEvent();
        logger = new SimpleLogger(0, false);
    }

    /**
     * calculates scoreboard
     * @return a scorevent
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */
    public ScoreEvent calculateScore() throws InvalidIntArgumentException, GenericInvalidArgumentException {
        int[] tieVector = new int[players.size()];

        if(players.size()==4)
            for(int i=0;i<4;i++)
                tieVector[i] = i;

        if(players.size()==3) {
            tieVector[0] = 2;
            tieVector[1] = 0;
            tieVector[2] = 1;
        }

        if(players.size()==2) {
            tieVector[0] = 0;
            tieVector[1] = 1;
        }


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
                if(tempPubObj.getId()==9)
                    tempPubObj.setTemp(finalModel.getSchemebyIndex(i));

                tempPubObj.setBonus(finalModel.getSchemebyIndex(i));
                tempPlayer.setPubObj(j, tempPubObj.getBonus());
                logger.log("Player " + Integer.toString(i) + " public objective " + Integer.toString(j + 1) + " bonus: " + Integer.toString(tempPlayer.getPubObj(j)));
            }

            //tokens bonus calculation
            tempPlayer.setTokens(players.get(i).getTokens());
            logger.log("Player " + Integer.toString(i) + " tokens: " + Integer.toString(tempPlayer.getTokens()));

            for(int c1=0;c1<4;c1++)
                for(int c2=0;c2<5;c2++)
                    System.out.println("Die "+Integer.toString(c1)+" "+Integer.toString(c2)+" disabled: "+Boolean.toString(finalModel.getSchemebyIndex(i).getDie(c1, c2).isDisabled()));

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

        ArrayList<ScorePlayer> tempPlayers = new ArrayList<ScorePlayer>();
            for (int i = 0; i < scorePlayers.size(); i++) {
                int max = -1000;
                int index = 0;
                for (int j = 0; j < scorePlayers.size(); j++) {
                    if (scorePlayers.get(j).getTot() > max) {
                        {
                            boolean check = false;
                            for (int z = 0; z < tempPlayers.size(); z++) {
                                if (tempPlayers.get(z).getName().equals(scorePlayers.get(j).getName())) {
                                    check = true;
                                }
                            }
                            if (!check) {
                                index = j;
                                max = scorePlayers.get(j).getTot();
                            }
                        }
                    }
                }
                tempPlayers.add(scorePlayers.get(index));

        }
        for(int i=0;i<tempPlayers.size();i++)
            System.out.println(Integer.toString(i)+" "+tempPlayers.get(i).getId());
        while (tempPlayers.size()!=1)
        {
            if(tempPlayers.get(0).getTot()>tempPlayers.get(1).getTot())
            {
                event.addPlayer(tempPlayers.get(0));
                tempPlayers.remove(0);
            }
            else
            {
                if (tempPlayers.get(0).getPrivObj() < tempPlayers.get(1).getPrivObj()) {
                    event.addPlayer(tempPlayers.get(1));
                    tempPlayers.remove(1);
                }
                if(tempPlayers.get(0).getPrivObj() > tempPlayers.get(1).getPrivObj())
                {
                    event.addPlayer(tempPlayers.get(0));
                    tempPlayers.remove(0);
                }
                else {
                    if (tempPlayers.get(0).getTokens() < tempPlayers.get(1).getTokens()) {
                        event.addPlayer(tempPlayers.get(1));
                        tempPlayers.remove(1);
                    }
                    if(tempPlayers.get(0).getTokens() > tempPlayers.get(1).getTokens())
                    {
                        event.addPlayer(tempPlayers.get(0));
                        tempPlayers.remove(0);
                    }
                    else
                    {
                        boolean flag = false;
                        int i =0;
                        while(!flag)
                        {
                            if(tieVector[i]==tempPlayers.get(0).getId())
                            {
                                event.addPlayer(tempPlayers.get(0));
                                tempPlayers.remove(0);
                                flag=true;
                            }
                            if(tieVector[i]==tempPlayers.get(1).getId())
                            {
                                event.addPlayer(tempPlayers.get(1));
                                tempPlayers.remove(1);
                                flag=true;
                            }
                            else
                                i++;
                        }
                    }
                }
            }
        }
        event.addPlayer(tempPlayers.get(0));
        return event;
    }
}


