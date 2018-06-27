package it.polimi.ingsw.commons.Socket.SocketTools;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class SocketEncoder {
    private SocketProtocolTransformer transformer;

    /**
     * SocketEncoder Constructor
     */
    public SocketEncoder() {
        transformer = new SocketProtocolTransformer();
    }

    /**
     * Encodes the elements (dice) of a draft pool, specifying index, color and value of each die
     *
     * @param draft draft to encode
     * @return array of strings containing the draft pool as said above
     * @throws InvalidIntArgumentException
     */
    public String[] draftEncoder(DraftPool draft) throws InvalidIntArgumentException {
        String[] temp = new String[(draft.getDiceNum() * 3) + 2];

        temp[0] = transformer.simpleEncode("model", "draft");
        int i = 1;
        int index = 0;
        while (i < (draft.getDiceNum() * 3) + 1) {
            temp[i] = transformer.simpleEncode("index", Integer.toString(index));

            i++;
            temp[i] = transformer.simpleEncode("color", Integer.toString(draft.returnDie(index).getColor()));

            i++;
            temp[i] = transformer.simpleEncode("value", Integer.toString(draft.returnDie(index).getValue()));

            i++;
            index++;
        }
        temp[i] = transformer.simpleEncode("end", "draft");
        return temp;
    }

    /**
     * parses an ArrayList of dice into an array of strings
     *
     * @param dice ArrayList of dice to parse
     * @return array of strings containing dice information as said above
     */
    public String[] arrayListEncoder(ArrayList<Die> dice) {

        String[] temp = new String[(dice.size()) * 3];
        int i = 0;
        int index = 0;
        while (index < dice.size()) {
            temp[i] = transformer.simpleEncode("index", Integer.toString(index));

            i++;
            temp[i] = transformer.simpleEncode("color", Integer.toString(dice.get(index).getColor()));

            i++;
            temp[i] = transformer.simpleEncode("value", Integer.toString(dice.get(index).getValue()));

            i++;
            index++;

        }

        return temp;

    }

    /**
     * parses a scheme card into an array of strings
     *
     * @param schemeCard scheme card to parse
     * @return array of strings containing scheme card information as said above
     * @throws InvalidIntArgumentException
     */
    public String[] schemeCardEncoder(SchemeCard schemeCard) throws InvalidIntArgumentException {
        String[] temp = new String[(schemeCard.getNumDice() * 4) + 2];
        temp[0] = transformer.simpleEncode("model", "scheme");
        int index = 1;
        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 5; y++)
                if (!schemeCard.getDie(x, y).isDisabled()) {
                    temp[index] = transformer.simpleEncode("x", Integer.toString(x));
                    index++;
                    temp[index] = transformer.simpleEncode("y", Integer.toString(y));
                    index++;
                    temp[index] = transformer.simpleEncode("color", Integer.toString(schemeCard.getDie(x, y).getColor()));
                    index++;
                    temp[index] = transformer.simpleEncode("value", Integer.toString(schemeCard.getDie(x, y).getValue()));
                    index++;
                }
        temp[(schemeCard.getNumDice() * 4) + 1] = transformer.simpleEncode("end", "scheme");
        return temp;
    }


}
