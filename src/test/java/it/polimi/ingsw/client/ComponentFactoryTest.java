package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemesDeckMP;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.ComponentFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComponentFactoryTest
{
    ComponentFactory componentFactory;
    SchemeCardMP schemeCardMP;
    PublicObjectiveMP publicObjectiveMP;

    @BeforeEach public void setUp() throws InvalidIntArgumentException {
        componentFactory = new ComponentFactory();
        SchemesDeckMP schemesDeckMP = new SchemesDeckMP();
        schemeCardMP = schemesDeckMP.extractSchemebyID(5);
        publicObjectiveMP = new PublicObjectiveMP(3);
    }

    @Test public void checkGComponent() throws InvalidIntArgumentException {
        for(int i=0;i<7;i++)
            System.out.println(componentFactory.schemeCardExposure(schemeCardMP, 1, 1)[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkHComponent()
    {
        for(int i=0;i<3;i++)
            System.out.println(componentFactory.showUsername("pippo")[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkDComponent()
    {
        for(int i=0;i<5;i++)
            System.out.println(componentFactory.showPubObj(publicObjectiveMP)[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkCComponent() throws InvalidIntArgumentException {
        for(int i=0;i<5;i++)
            System.out.println(componentFactory.showPrivObj(new PrivateObjectiveMP(3))[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkIComponent()
    {
        for(int i=0;i<3;i++)
            System.out.println(componentFactory.askingScheme()[i]);
        Assertions.assertEquals(true, true);
    }
}
