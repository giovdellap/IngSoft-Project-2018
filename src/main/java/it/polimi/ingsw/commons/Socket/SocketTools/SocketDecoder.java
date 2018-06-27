package it.polimi.ingsw.commons.Socket.SocketTools;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import java.util.ArrayList;

public class SocketDecoder
{
    private SocketProtocolTransformer transformer;

    /**
     * SocketDecoder Constructor
     */
    public SocketDecoder()
    {
        transformer = new SocketProtocolTransformer();
    }

    /**
     * decodes an ArrayList of strings containing dice information into an ArrayList of dice
     * @param dice strings to decode
     * @return ArrayList of dice decoded as said above
     */

    public ArrayList<Die> arrayListDecoder(ArrayList<String> dice) throws InvalidIntArgumentException {

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
            temp.get(index).setValue(Integer.parseInt(transformer.getArg()));
            i++;

        }

        return temp;

    }

}
