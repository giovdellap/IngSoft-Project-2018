package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.FcknSimpleLogger;
import it.polimi.ingsw.server.Connection.GeneralServer;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

public class GorgeousLobbyCreator
{
    private GeneralServer generalServer;
    private FcknSimpleLogger logger;

    public GorgeousLobbyCreator() throws IOException {
        generalServer = new GeneralServer();
        logger=new FcknSimpleLogger(0, false);
    }

    public ArrayList<Player> createThatLobby() throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {

        logger.log("lobby creation started");
        ArrayList<Player> temp = new ArrayList<Player>();
        ArrayList<String> tempStrings = new ArrayList<String>();

        long startTime = System.currentTimeMillis();
        long elapsedTime = 0;
        int maxPlayers=4;

        //adding first player to lobby
        temp.add(new Player(generalServer.accept()));
        temp.get(temp.size()-1).getUsername();
        tempStrings.add(temp.get(temp.size()-1).getName());
        logger.log("player "+tempStrings.get(tempStrings.size()-1)+" added to lobby");

        //timer
        while (elapsedTime < 120*1000 && temp.size()<maxPlayers)
        {
            temp.add(new Player(generalServer.accept()));
            temp.get(temp.size()-1).getUsername(makeThatVector(tempStrings));
            tempStrings.add(temp.get(temp.size()-1).getName());
            logger.log("player "+tempStrings.get(tempStrings.size()-1)+" added to lobby");

            if(elapsedTime>=60*1000&&elapsedTime<90*1000)
                maxPlayers=3;
            if(elapsedTime>=90*1000)
                maxPlayers=2;
            elapsedTime = (new Date()).getTime() - startTime;
        }

        return temp;
    }

    private String[] makeThatVector(ArrayList<String> arg)
    {
        String[] temp = new String[arg.size()];
        for(int i=0;i<temp.length;i++)
            temp[i] = arg.get(i);
        return temp;
    }
}
