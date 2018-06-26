package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.RoundTrackMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.CLIToolsManager;
import it.polimi.ingsw.commons.Die;
import org.fusesource.jansi.Ansi;

import java.util.ArrayList;

public class ModelGenerator
{
    private CLIToolsManager clito;

    public ModelGenerator()
    {
        clito= new CLIToolsManager();
    }

    /**
     * uses the JANSI library to get and print the dice scheme of the parameter schemecard with colors, grey dice values and white cells
     * @param scheme
     * @return
     * @throws InvalidIntArgumentException
     */
    public String[] getScheme(SchemeCardMP scheme) throws InvalidIntArgumentException
    {
        //returns a String[5] representing the schemecard
        //1 vector's element = 1 row

        String[] schemeCard = new String[5];

        int z=1;

        schemeCard[0]=" 12345";

        for (int i = 0; i < 4; i++)
        {
            String row = Integer.toString(z);

            for (int j = 0; j < 5; j++)
            {
                if (!scheme.getDie(i, j).isDisabled())
                    row += toUnicode(scheme.getDie(i, j));
                else
                {
                    if (scheme.getCell(scheme.getfb(),i,j) == 0)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.WHITE) + "\u25fc" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 1)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u25fc" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 2)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u25fc" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 3)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u25fc" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 4)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u25fc" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 5)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.MAGENTA) + "\u25fc" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 6)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.WHITE) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 7)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.WHITE) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 8)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.WHITE) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 9)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.WHITE) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 10)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.WHITE) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                    if (scheme.getCell(scheme.getfb(),i,j) == 11)
                        row +=Ansi.ansi().reset().fg(Ansi.Color.WHITE) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);
                }
            }
            schemeCard[i+1]=row;
            z++;
        }
        return schemeCard;
    }

    /**
     *  generates a string representing the draft pool
     * @param draft
     * @return
     * @throws InvalidIntArgumentException
     */
    public String getDraft(DraftPoolMP draft) throws InvalidIntArgumentException
    {


        int i=0;
        String diceDraft = "";

        while(i<draft.getSize())
        {
            diceDraft += toUnicode(draft.returnDie(i))+" ";
            i++;
        }
        diceDraft+=printSpaces(10-draft.getSize());
        return diceDraft;
    }

    /**
     * generates a string representing the round track
     * @param roundtrack
     * @return
     * @throws InvalidIntArgumentException
     */
    public String[] getRoundTrack(RoundTrackMP roundtrack) throws InvalidIntArgumentException
    {
        CLIToolsManager clito = new CLIToolsManager();
        String[] track;
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(clito.printSpaces(5)+"ROUNDTRACK"+clito.printSpaces(5));
        temp.add("1 2 3 4 5 6 7 8 9 10");
        for(int i=0;i<calculateDim(roundtrack);i++)
        {
            String util = "";
            for(int j=0;j<roundtrack.returnActualTurn();j++)
            {
                if(i>=roundtrack.returnNTurnRoundDice(j).returnDim())
                    util+="\u2612";
                else
                    util+=toUnicode(roundtrack.returnNTurnRoundDice(j).getDie(i));
                util+=" ";
            }
            temp.add(util);
        }
        track = new String[temp.size()];
        for(int i=0;i<track.length;i++)
        {
            track[i]=temp.get(i);
        }
        return track;
    }

    /**
     * generates a string made of num spaces
     * @param num
     * @return
     */
    private String printSpaces(int num)
    {
        String temp = "";
        for(int i=0;i<num;i++)
            temp+=" ";

        return temp;
    }

    /**
     * encodes a die (using it's information: value and color) to an unicode die
     * @param tempDie
     * @return
     */
    public String toUnicode(Die tempDie)
    {
        String temp="";

        if (tempDie.getColor() == 1 && tempDie.getValue()==1)
            temp+= Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 1 && tempDie.getValue()==2)
            temp += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 1 && tempDie.getValue()==3)
            temp += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 1 && tempDie.getValue()==4)
            temp += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 1 && tempDie.getValue()==5)
            temp += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 1 && tempDie.getValue()==6)
            temp += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);



        if (tempDie.getColor() == 2 && tempDie.getValue()==1)
            temp += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 2 && tempDie.getValue()==2)
            temp += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 2 && tempDie.getValue()==3)
            temp += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 2 && tempDie.getValue()==4)
            temp += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 2 && tempDie.getValue()==5)
            temp += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 2 && tempDie.getValue()==6)
            temp += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);



        if (tempDie.getColor() == 3 && tempDie.getValue()==1)
            temp += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 3 && tempDie.getValue()==2)
            temp += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 3 && tempDie.getValue()==3)
            temp += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 3 && tempDie.getValue()==4)
            temp += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 3 && tempDie.getValue()==5)
            temp += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 3 && tempDie.getValue()==6)
            temp += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);



        if (tempDie.getColor() == 4 && tempDie.getValue()==1)
            temp += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 4 && tempDie.getValue()==2)
            temp += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 4 && tempDie.getValue()==3)
            temp += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 4 && tempDie.getValue()==4)
            temp += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 4 && tempDie.getValue()==5)
            temp += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 4 && tempDie.getValue()==6)
            temp += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);


        if (tempDie.getColor() == 5 && tempDie.getValue()==1)
            temp += Ansi.ansi().reset().fg(Ansi.Color.MAGENTA) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 5 && tempDie.getValue()==2)
            temp += Ansi.ansi().reset().fg(Ansi.Color.MAGENTA) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 5 && tempDie.getValue()==3)
            temp += Ansi.ansi().reset().fg(Ansi.Color.MAGENTA) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 5 && tempDie.getValue()==4)
            temp += Ansi.ansi().reset().fg(Ansi.Color.MAGENTA) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 5 && tempDie.getValue()==5)
            temp += Ansi.ansi().reset().fg(Ansi.Color.MAGENTA) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        if (tempDie.getColor() == 5 && tempDie.getValue()==6)
            temp += Ansi.ansi().reset().fg(Ansi.Color.MAGENTA) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

        return temp;

    }

    /**
     * calculates the actual dimension of the round track
     * @param round
     * @return integer representing the round track's dimension
     * @throws InvalidIntArgumentException
     */
    public int calculateDim(RoundTrackMP round) throws InvalidIntArgumentException
    {
        int max=0;

        for (int i=0;i<round.returnActualTurn();i++)
        {
            if (round.returnNTurnRoundDice(i).returnDim()>max)
                max=round.returnNTurnRoundDice(i).returnDim();
        }
        return max;
    }
}
