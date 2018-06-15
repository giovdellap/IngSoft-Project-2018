package it.polimi.ingsw.commons.Socket.SocketTools;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.RoundDice;
import it.polimi.ingsw.server.ModelComponent.RoundTrack;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class SocketEncoder
{
    private SocketProtocolTransformer transformer;

    public SocketEncoder()
    {
        transformer = new SocketProtocolTransformer();
    }

    //DRAFTPOOL
    public String[] draftEncoder(DraftPool draft) throws InvalidIntArgumentException
    {
        //SERVER VERSION
        //returns the String vector representing given draftpool
        String[] temp = new String[(draft.getDiceNum()*3)+2];

        temp[0] = transformer.simpleEncode("model", "draft");
        int i=1;
        int index=0;
        while(i<(draft.getDiceNum()*3)+1)
        {
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

    public String[] draftEncoder(int index)
    {
        String[] temp = new String[3];
        temp[0] = transformer.simpleEncode("model", "draft");
        temp[1] = transformer.simpleEncode("index", Integer.toString(index));
        temp[2] = transformer.simpleEncode("end", "draft");
        return temp;
    }

    public String[] arrayListEncoder(ArrayList<Die> dice) {

        String[] temp = new String[(dice.size())*3];
        int i=0;
        int index=0;
        while(index<dice.size())
        {
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

    //SCHEMECARD
    public String[] schemeCardEncoder(SchemeCard schemeCard) throws InvalidIntArgumentException
    {
        String[] temp = new String[(schemeCard.getNumDice()*4)+2];
        temp[0] = transformer.simpleEncode("model", "scheme");
        int index=1;
        for(int x=0;x<4;x++)
            for(int y=0;y<5;y++)
                if(!schemeCard.getDie(x, y).isDisabled())
                {
                    temp[index]=transformer.simpleEncode("x", Integer.toString(x));
                    index++;
                    temp[index]=transformer.simpleEncode("y", Integer.toString(y));
                    index++;
                    temp[index]=transformer.simpleEncode("color", Integer.toString(schemeCard.getDie(x, y).getColor()));
                    index++;
                    temp[index]=transformer.simpleEncode("value", Integer.toString(schemeCard.getDie(x, y).getValue()));
                    index++;
                }
        temp[(schemeCard.getNumDice()*4)+1] = transformer.simpleEncode("end", "scheme");
        return temp;
    }
    public String[] schemeCardEncoder(int x, int y)
    {
        String[] temp = new String[3];
        temp[0] = transformer.simpleEncode("model", "scheme");
        temp[1] = transformer.simpleEncode("x", Integer.toString(x));
        temp[2] = transformer.simpleEncode("y", Integer.toString(y));

        return temp;
    }


    //TOOL CARD UPDATE

    public String[] toolCardUpdateEncoder(int[] tokens)
    {
        String[] temp = new String[7];
        temp[0] = transformer.simpleEncode("model", "tool");
        int i=1;
        int id=0;
        while(i<7)
        {
            temp[i]=transformer.simpleEncode("id", Integer.toString(id));
            i++;
            temp[i]=transformer.simpleEncode("tokens", Integer.toString(tokens[id]));
            i++;
            id++;
        }

        return temp;
    }

    //ROUNDTRACK
    public String[] roundTrackEncoder(RoundTrack track, int round) throws InvalidIntArgumentException
    {
        ArrayList<String> tempAL = new ArrayList<String>();
        tempAL.add(transformer.simpleEncode("model", "track"));

        for(int r=0;r<round-1;r++)
        {
            tempAL.add(transformer.simpleEncode("round", Integer.toString(r)));
            RoundDice tempRD = track.returnNTurnRoundDice(r);
            for(int i=0;i<tempRD.returnDim();i++)
            {
                tempAL.add(transformer.simpleEncode("die", Integer.toString(i)));
                tempAL.add(transformer.simpleEncode("color", Integer.toString(tempRD.getDie(i).getColor())));
                tempAL.add(transformer.simpleEncode("value", Integer.toString(tempRD.getDie(i).getValue())));
            }
        }
        String[] temp = new String[tempAL.size()];
        for(int i=0;i<tempAL.size();i++)
            temp[i] = tempAL.get(i);

        return temp;
    }

    //BONUS
    public String[] bonusEncoder(String[]names, int[][]bonus)
    {
        String[] tempVect = new String[names.length*9];

        int z = 0;

        for (int i=0;i<names.length;i++)
        {
            int j = 0;

            tempVect[0+z] = transformer.simpleEncode("index", Integer.toString(i));
            tempVect[1+z]=transformer.simpleEncode("username",names[i]);
            tempVect[2+z]=transformer.simpleEncode("privobj",Integer.toString(bonus[i][j]));
            j++;
            tempVect[3+z]=transformer.simpleEncode("pubobj1",Integer.toString(bonus[i][j]));
            j++;
            tempVect[4+z]=transformer.simpleEncode("pubobj2",Integer.toString(bonus[i][j]));
            j++;
            tempVect[5+z]=transformer.simpleEncode("pubobj3",Integer.toString(bonus[i][j]));
            j++;
            tempVect[6+z]=transformer.simpleEncode("token",Integer.toString(bonus[i][j]));
            j++;
            tempVect[7+z]=transformer.simpleEncode("minus",Integer.toString(bonus[i][j]));
            j++;
            tempVect[8+z]=transformer.simpleEncode("tot",Integer.toString(bonus[i][j]));
            j++;

            z=z+9;
        }

        return tempVect;
    }
}
