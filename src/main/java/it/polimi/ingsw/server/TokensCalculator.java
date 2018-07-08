package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

public class TokensCalculator
{
    /**
     * sets up tokens for personalized scheme
     * @param scheme
     * @return
     * @throws InvalidIntArgumentException
     */
    public int calculateTokens(SchemeCard scheme) throws InvalidIntArgumentException {
        int tokens=0;
        int occupied=0;
        boolean bonus=true;
        for(int x=0;x<4;x++) {
            for (int y = 0; y < 5; y++) {
                if (scheme.getCell(scheme.getfb(), x, y) != 0)
                    occupied++;
                else {
                    int nearEmptyCellsCounter = 0;
                    for (int i = -1; i < 2; i++)
                        for (int j = 0; j < 2; j++)
                            if (x + i > -1 && x + i < 4 && y + j > -1 && y + j < 5)
                                if (scheme.getCell(scheme.getfb(), x + i, y + j) == 0)
                                    nearEmptyCellsCounter++;
                    if (nearEmptyCellsCounter >= 3)
                        bonus = false;
                }
            }
        }
        if(occupied<=11)
            tokens=3;
        if(occupied==12)
            tokens=4;
        if(occupied==13||occupied==14)
            tokens=5;
        if(occupied>14)
            tokens=6;
        if(bonus)
            tokens++;

        return tokens;
    }
}
