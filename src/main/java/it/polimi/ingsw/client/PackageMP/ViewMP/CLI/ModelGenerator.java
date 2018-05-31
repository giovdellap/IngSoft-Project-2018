package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.RoundTrackMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import org.fusesource.jansi.Ansi;

public class ModelGenerator
{
    public String[] getScheme(SchemeCardMP scheme) throws InvalidIntArgumentException {
        //returns a String[11] representing the schemecard
        //1 vector's element = 1 row

        String[] schemeCard = new String[11];

        int w=2;
        int z=1;

        schemeCard[0]="  1 2 3 4 5";

        schemeCard[1]=printSpaces(11);

        for (int i = 0; i < 4; i++) {

            String row = "z";

            for (int j = 0; j < 5; j++) {


                if (scheme.getDie(i, j).isDisabled())
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.WHITE) + "\u25fc" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);


                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 1 && scheme.getDie(i,j).getValue()==1)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 1 && scheme.getDie(i,j).getValue()==2)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 1 && scheme.getDie(i,j).getValue()==3)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 1 && scheme.getDie(i,j).getValue()==4)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 1 && scheme.getDie(i,j).getValue()==5)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 1 && scheme.getDie(i,j).getValue()==6)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);



                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 2 && scheme.getDie(i,j).getValue()==1)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 2 && scheme.getDie(i,j).getValue()==2)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 2 && scheme.getDie(i,j).getValue()==3)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 2 && scheme.getDie(i,j).getValue()==4)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 2 && scheme.getDie(i,j).getValue()==5)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 2 && scheme.getDie(i,j).getValue()==6)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);



                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 3 && scheme.getDie(i,j).getValue()==1)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 3 && scheme.getDie(i,j).getValue()==2)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 3 && scheme.getDie(i,j).getValue()==3)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 3 && scheme.getDie(i,j).getValue()==4)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 3 && scheme.getDie(i,j).getValue()==5)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 3 && scheme.getDie(i,j).getValue()==6)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);



                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 4 && scheme.getDie(i,j).getValue()==1)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 4 && scheme.getDie(i,j).getValue()==2)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 4 && scheme.getDie(i,j).getValue()==3)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 4 && scheme.getDie(i,j).getValue()==4)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 4 && scheme.getDie(i,j).getValue()==5)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 4 && scheme.getDie(i,j).getValue()==6)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);


                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 5 && scheme.getDie(i,j).getValue()==1)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 5 && scheme.getDie(i,j).getValue()==2)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 5 && scheme.getDie(i,j).getValue()==3)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 5 && scheme.getDie(i,j).getValue()==4)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 5 && scheme.getDie(i,j).getValue()==5)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

                if ((!scheme.getDie(i, j).isDisabled()) && scheme.getDie(i, j).getColor() == 5 && scheme.getDie(i,j).getValue()==6)
                    row +=" "+ Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            }

            schemeCard[w]=row;

            schemeCard[++w]=printSpaces(11);
            ++w;
            z++;
        }
        schemeCard[++w]="    NOME    ";

        return schemeCard;
    }

    public String getDraft(DraftPoolMP draft) throws InvalidIntArgumentException {
        //returns a string representing the draftpool

        int i=0;
        String diceDraft = "";

        while(i<draft.getSize()) {

            if (draft.returnDie(i).getColor() == 1 && draft.returnDie(i).getValue()==1)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 1 && draft.returnDie(i).getValue()==2)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 1 && draft.returnDie(i).getValue()==3)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 1 && draft.returnDie(i).getValue()==4)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 1 && draft.returnDie(i).getValue()==5)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 1 && draft.returnDie(i).getValue()==6)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.YELLOW) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);



            if (draft.returnDie(i).getColor() == 2 && draft.returnDie(i).getValue()==1)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 2 && draft.returnDie(i).getValue()==2)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 2 && draft.returnDie(i).getValue()==3)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 2 && draft.returnDie(i).getValue()==4)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 2 && draft.returnDie(i).getValue()==5)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 2 && draft.returnDie(i).getValue()==6)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.RED) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);



            if (draft.returnDie(i).getColor() == 3 && draft.returnDie(i).getValue()==1)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 3 && draft.returnDie(i).getValue()==2)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 3 && draft.returnDie(i).getValue()==3)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 3 && draft.returnDie(i).getValue()==4)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 3 && draft.returnDie(i).getValue()==5)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 3 && draft.returnDie(i).getValue()==6)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.GREEN) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);



            if (draft.returnDie(i).getColor() == 4 && draft.returnDie(i).getValue()==1)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 4 && draft.returnDie(i).getValue()==2)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 4 && draft.returnDie(i).getValue()==3)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 4 && draft.returnDie(i).getValue()==4)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 4 && draft.returnDie(i).getValue()==5)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 4 && draft.returnDie(i).getValue()==6)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.BLUE) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);


            if (draft.returnDie(i).getColor() == 5 && draft.returnDie(i).getValue()==1)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2680" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 5 && draft.returnDie(i).getValue()==2)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2681" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 5 && draft.returnDie(i).getValue()==3)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2682" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 5 && draft.returnDie(i).getValue()==4)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2683" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 5 && draft.returnDie(i).getValue()==5)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2684" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            if (draft.returnDie(i).getColor() == 5 && draft.returnDie(i).getValue()==6)
                diceDraft += Ansi.ansi().reset().fg(Ansi.Color.CYAN) + "\u2685" + Ansi.ansi().reset().fg(Ansi.Color.DEFAULT);

            i++;
        }
        return diceDraft;
    }


    private String getRoundTrack(RoundTrackMP track)  {
        //returns a string representing the roundtrack

        return null;
    }

    //TOOLS
    private String printSpaces(int num)
    {
        //returns a string made of num spaces
        String temp = "";
        for(int i=0;i<num;i++)
        {
            temp+=" ";
        }
        return temp;
    }
}
