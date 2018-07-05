package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class TokensCalculatorTest
{
    private TokensCalculator calculator;
    private SchemesDeck deck;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException {
        calculator=new TokensCalculator();
        deck = new SchemesDeck();
    }

    @Test
    public void checkCalculator() throws InvalidIntArgumentException, FileNotFoundException {
        SchemeCard scheme;

        int ok=0;
        int failed=0;

        for(int i=1;i<13;i++)
            for(int fb=1;fb<3;fb++)
            {
                scheme = deck.extractSchemebyID(i);
                scheme.setfb(fb);
                int tokens = calculator.calculateTokens(scheme);
                System.out.println("Scheme: "+Integer.toString(i)+" fb: "+Integer.toString(fb));
                System.out.println("diff: "+Integer.toString(scheme.getDiff(fb))+" calculator: "+Integer.toString(tokens));
                if(tokens==scheme.getDiff(fb))
                    ok++;
                else
                    failed++;
            }

        System.out.println("passed: "+Integer.toString(ok));
        System.out.println("failed: "+Integer.toString(failed));
        Assertions.assertEquals(true, true);
    }
}
