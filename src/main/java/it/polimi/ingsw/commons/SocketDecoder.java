package it.polimi.ingsw.commons;

import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.PlayerClient;

import java.util.ArrayList;

public class SocketDecoder
{
    private SocketProtocolTransformer transformer;

    public SocketDecoder()
    {
        transformer = new SocketProtocolTransformer();
    }

    private String[] names;
    private int[][] scoresPlayers;

    //DRAFTPOOL
    public DraftPoolMP draftPoolDecoder(ArrayList<String> arg) throws InvalidIntArgumentException {

        //returns a full DraftPoolMP
        DieMP[] temp = new DieMP[(arg.size()-1)/3];
        int i=0;
        int index;

        //passing from strings to dice
        transformer.simpleDecode(arg.get(i));
        while(!(transformer.getCmd().equals("end")&&transformer.getArg().equals("draft")))
        {
            index=Integer.parseInt(transformer.getArg());
            i++;
            transformer.simpleDecode(arg.get(i));
            DieMP tempDie = new DieMP(Integer.parseInt(transformer.getArg()));
            i++;
            transformer.simpleDecode(arg.get(i));
            tempDie.setValueTest(Integer.parseInt(transformer.getArg()));
            temp[index]=tempDie;
            i++;
            transformer.simpleDecode(arg.get(i));
        }

        //creating and returning new DraftPool
        DraftPoolMP draft = new DraftPoolMP(temp);
        return draft;
    }
    public SchemeCardMP schemeCardDecoder(ArrayList<String> arg,PlayerClient player) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        //returns player with new schemecard
        SchemesDeckMP deck = new SchemesDeckMP();
        SchemeCardMP scheme = deck.extractSchemebyID(player.getPlayerScheme().getID());
        scheme.setfb(player.getPlayerScheme().getfb());
        int i=0;
        transformer.simpleDecode(arg.get(i));
        while (!(transformer.getCmd().equals("end")&&transformer.getArg().equals("scheme")))
        {
            int x=Integer.parseInt(transformer.getArg());
            i++;
            transformer.simpleDecode(arg.get(i));
            int y=Integer.parseInt(transformer.getArg());
            i++;
            transformer.simpleDecode(arg.get(i));
            DieMP tempDie = new DieMP(Integer.parseInt(transformer.getArg()));
            i++;
            transformer.simpleDecode(arg.get(i));
            tempDie.setValueTest(Integer.parseInt(transformer.getArg()));
            scheme.setDie(tempDie, x, y);

            i++;
            transformer.simpleDecode(arg.get(i));
        }
        return scheme;
    }

    public RoundTrackMP roundTrackDecoder(ArrayList<String> arg) throws InvalidIntArgumentException, FullDataStructureException {
        //returns new roundTrack
        RoundTrackMP temp = new RoundTrackMP();
        int i=0;
        int round;

        //builds a dice ArrayList
        transformer.simpleDecode(arg.get(i));
        while(!(transformer.getCmd().equals("end")&&transformer.getArg().equals("round")))
        {
            round=Integer.parseInt(transformer.getArg());
            i++;
            transformer.simpleDecode(arg.get(i));
            ArrayList<DieMP> tempAL = new ArrayList<DieMP>();
            while(transformer.getCmd().equals("round")) {

                int index=Integer.parseInt(transformer.getArg());
                i++;
                transformer.simpleDecode(arg.get(i));
                DieMP tempDie = new DieMP(Integer.parseInt(transformer.getArg()));
                i++;
                transformer.simpleDecode(arg.get(i));
                tempDie.setValueTest(Integer.parseInt(transformer.getArg()));
                tempAL.add(tempDie);
                i++;
            }
            //Transforms ArrayList to RoundDice
            RoundDiceMP tempRD = new RoundDiceMP(tempAL.size());
            for(int n=0;n<tempAL.size();n++)
                tempRD.addDie(tempAL.get(n));

            //adds roundDice to roundTrack
            temp.setSpecificRoundDice(tempRD, round);
        }
        return temp;
    }

    public int[] toolTokensDecode(String[] arg)
    {
        int[] temp = new int[3];
        for(int i=0;i<6;i++)
        {
            transformer.simpleDecode(arg[i]);
            int index= Integer.parseInt(transformer.getArg());
            i++;
            transformer.simpleDecode(arg[i]);
            temp[index] = Integer.parseInt(transformer.getArg());
        }
        return temp;
    }

    public void bonusDecoder(String[] bonus)
    {
        int i=0;
        int j=0;
        int z=0;
        int h=0;


        names=new String[bonus.length/9];
        scoresPlayers=new int[bonus.length/9][7];

        for (i=0;i<bonus.length/9;i++)                         //num players
        {
            transformer.simpleDecode(bonus[0+h]);
            names[i] = transformer.getArg();

            for (z = 1; z < 9; z++)
            {
                transformer.simpleDecode(bonus[z+h]);
                for (j=0;j<7;j++)
                    scoresPlayers[i][j] = Integer.parseInt(transformer.getArg());
            }
            h=h+9;
        }
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
