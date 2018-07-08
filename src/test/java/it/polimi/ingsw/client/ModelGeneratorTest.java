package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Graphics.CLI.ModelGenerator;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class ModelGeneratorTest
{
    private SchemesDeck schemesDeckMP;
    private SchemeCard emptyScheme;
    private SchemeCard testScheme;
    private ModelGenerator modelGenerator;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException, FileNotFoundException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, UnsupportedEncodingException {
        schemesDeckMP = new SchemesDeck();
        emptyScheme = schemesDeckMP.extractSchemebyID(5);
        emptyScheme.setfb(1);
        testScheme = schemesDeckMP.extractSchemebyID(7);
        testScheme.setfb(1);
        modelGenerator = new ModelGenerator();
    }

    @Test
    public void testEmptyScheme() throws InvalidIntArgumentException
    {
        for(int i=0;i<5;i++)
            System.out.println(modelGenerator.getScheme(emptyScheme)[i]+"/");

        Assertions.assertEquals(true, true);
    }
}
