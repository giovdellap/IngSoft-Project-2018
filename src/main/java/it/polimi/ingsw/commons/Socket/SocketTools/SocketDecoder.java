package it.polimi.ingsw.commons.Socket.SocketTools;

import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.PlayerClient;
import it.polimi.ingsw.commons.Die;

import java.util.ArrayList;

public class SocketDecoder
{
    private SocketProtocolTransformer transformer;
    private String[] names;
    private int[][] scoresPlayers;

    public SocketDecoder()
    {
        transformer = new SocketProtocolTransformer();
    }


    public ArrayList<Die> arrayListDecoder(ArrayList<String> dice) throws it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException {

        ArrayList<Die> temp = new ArrayList<Die>();

        int i=0;
        int index;

        while (i<dice.size())
        {
            transformer.simpleDecode(dice.get(i));

            index = Integer.parseInt(transformer.getArg());
            i++;

            transformer.simpleDecode(dice.get(i));
            temp.add(new Die(Integer.parseInt(transformer.getArg())));
            i++;

            transformer.simpleDecode(dice.get(i));
            temp.get(index).setValueTest(Integer.parseInt(transformer.getArg()));
            i++;

        }

        return temp;

    }
    public String[] getNames()
    {
        return names;
    }

    public int[][] getScoresPlayers()
    {
        return scoresPlayers;
    }

}
