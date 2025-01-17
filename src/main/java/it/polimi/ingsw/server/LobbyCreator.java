package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.server.Connection.GeneralServer;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.io.IOException;
import java.net.Socket;
import java.util.ArrayList;

public class LobbyCreator
{
    private GeneralServer generalServer;
    private SimpleLogger logger;

    /**
     * LobbyCreator Constructor
     */
    public LobbyCreator(GeneralServer gs) {
        generalServer = gs;
        logger=new SimpleLogger(0, false);
    }

    /**
     * creates the lobby
     * @return an ArrayList of Players
     * @throws IOException
     * @throws GenericInvalidArgumentException
     * @throws InvalidIntArgumentException
     */
    public ArrayList<PlayerThread> createThatLobby() throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException
    {
        logger.log("Lobby creation started");
        ArrayList<PlayerThread> temp = new ArrayList<PlayerThread>();
        ArrayList<String> tempStrings = new ArrayList<String>();

        int maxPlayers=4;
        boolean flag=true;
        Socket tempgs;

        //adding first player to lobby
        while (flag)
        {
            tempgs = generalServer.accept(30);
            if (tempgs== null) logger.log("0 client connected\n");
            else {
                flag=false;
                temp.add(new PlayerThread(tempgs));
                temp.get(temp.size() - 1).getUsername();
                tempStrings.add(temp.get(temp.size() - 1).getName());
                logger.log("First player connected: " + tempStrings.get(tempStrings.size() - 1) + " added to lobby");
            }
        }

        //adding others player
        while (temp.size()<maxPlayers && maxPlayers!=2)
        {
            tempgs = generalServer.accept(30);
            if (tempgs==null)
            {
                maxPlayers--;
                logger.log("Lobby size set to: "+maxPlayers+"\n");
            }

            else {
                temp.add(new PlayerThread(tempgs));
                temp.get(temp.size() - 1).getUsername(makeThatVector(tempStrings));
                tempStrings.add(temp.get(temp.size() - 1).getName());
                logger.log("player " + tempStrings.get(tempStrings.size() - 1) + " added to lobby");

            }

        }

        //if only one connected
        if (temp.size()==1)
        {
            logger.log("You need a second player at least");
            flag=true;
            while (flag)
            {
                tempgs = generalServer.accept(30);
                if (tempgs!= null)
                {
                    flag = false;
                    temp.add(new PlayerThread(tempgs));
                    temp.get(temp.size() - 1).getUsername();
                    tempStrings.add(temp.get(temp.size() - 1).getName());
                    logger.log("First player connected: " + tempStrings.get(tempStrings.size() - 1) + " added to lobby");
                }
            }
        }

        return temp;
    }

    /**
     * transforms an arrayList of strings in an array of strings
     * @param arg ArrayList of strings
     * @return an array of strings
     */
    private String[] makeThatVector(ArrayList<String> arg)
    {
        String[] temp = new String[arg.size()];
        for(int i=0;i<temp.length;i++)
            temp[i] = arg.get(i);
        return temp;
    }
}
