package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemesDeckMP;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.ModelGenerator;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelGeneratorTest
{
    private SchemesDeckMP schemesDeckMP;
    private SchemeCardMP emptyScheme;
    private SchemeCardMP testScheme;
    private ModelGenerator modelGenerator;

    @BeforeEach
    public void setUp() throws InvalidIntArgumentException
    {
        schemesDeckMP = new SchemesDeckMP();
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
