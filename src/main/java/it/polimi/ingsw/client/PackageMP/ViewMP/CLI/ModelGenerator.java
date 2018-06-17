package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.RoundTrackMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.commons.Die;
import org.fusesource.jansi.Ansi;

public class ModelGenerator
{
    private CLIToolsManager clito;

    public ModelGenerator()
    {
        clito= new CLIToolsManager();
    }

    public String[] getScheme(SchemeCardMP scheme) throws InvalidIntArgumentException
    {
        //returns a String[5] representing the schemecard
        //1 vector's element = 1 row

        String[] schemeCard = new String[5];

        int z=1;

        schemeCard[0]="  12345";

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

    public String getDraft(DraftPoolMP draft) throws InvalidIntArgumentException
    {
        //returns a string representing the draftpool

        int i=0;
        String diceDraft = "";

        while(i<draft.getSize())
        {
            diceDraft += toUnicode(draft.returnDie(i));
            i++;
        }
        diceDraft+=printSpaces(10-draft.getSize());
        return diceDraft;
    }

    public String[] getRoundTrack(RoundTrackMP roundtrack) throws InvalidIntArgumentException
    {
        //returns a string representing the roundtrack

        CLIToolsManager clito = new CLIToolsManager();
        String[] track = new String[2];

        int j=0;

        String temp = "";

        String temp1 = "";

        for(int i=0;i<roundtrack.returnActualTurn();i++)
            temp += toUnicode(roundtrack.returnNTurnRoundDice(i).getDie(j));



        track[0]=clito.printSpacesEnder(temp, 10);

        j++;

        for(int i=0;i<roundtrack.returnActualTurn();i++) {
            if (j < roundtrack.returnNTurnRoundDice(i).returnDim())
                temp1 += toUnicode(roundtrack.returnNTurnRoundDice(i).getDie(j));
            else
                temp1 += "-";
        }
        track[1]=clito.printSpacesEnder(temp1, 10);

        return track;
    }

    //TOOLS
    private String printSpaces(int num)
    {
        //returns a string made of num spaces
        String temp = "";
        for(int i=0;i<num;i++)
            temp+=" ";

        return temp;
    }

    public String toUnicode(Die tempDie)                  //encode die to unicode die
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
}
