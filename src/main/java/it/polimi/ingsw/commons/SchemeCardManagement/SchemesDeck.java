package it.polimi.ingsw.commons.SchemeCardManagement;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class SchemesDeck
{
    private SchemeCardReader reader;

    /**
     * SchemesDeck Constructor
     */
    public SchemesDeck()
    {

        //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
        //NUMBERS 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

        reader = new SchemeCardReader();

    }

    /**
     * extract casual schemes
     * @param n number of schemes to extract
     * @return scheme card vector containing extracted schemes
     * @throws InvalidIntArgumentException
     */
    public SchemeCard[] extractSchemes(int n) throws InvalidIntArgumentException, FileNotFoundException {

        if (n<0 || n>8 || n%2!=0)
            throw new InvalidIntArgumentException();

        SchemeCard[] tempVett = new SchemeCard[n];
        ArrayList<Integer> extracted = new ArrayList<Integer>();
        for (int i = 0; i < n; i++) {
            boolean exists = false;
            int random = 0;
            while (!exists) {
                random = (int) (Math.random() * 12+1);
                if (extracted.isEmpty())
                {
                    tempVett[i]=reader.readCard(random);
                    extracted.add(random);
                    exists=true;
                }
                else
                {
                    boolean flag=true;
                    for(int x : extracted)
                        if(random==x)
                            flag=false;
                    if(flag)
                    {
                        tempVett[i]=reader.readCard(random);
                        extracted.add(random);
                        exists=true;
                    }
                }


            }

        }
        return tempVett;
    }

    /**
     * extracts a precise scheme card by id
     * @param id id of the scheme card to extract
     * @return the extracted scheme card
     * @throws InvalidIntArgumentException
     */

    public SchemeCard extractSchemebyID (int id) throws InvalidIntArgumentException, FileNotFoundException {

        if(id==0)
            return new SchemeCard((0));
        if(id<1 || id>13)
            throw new InvalidIntArgumentException();

        return reader.readCard(id);

    }

}
