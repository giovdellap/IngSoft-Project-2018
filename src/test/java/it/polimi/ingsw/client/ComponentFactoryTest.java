package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.ComponentFactory;
import it.polimi.ingsw.client.PackageSP.ModelComponentsSP.RoundDice;
import it.polimi.ingsw.server.ModelComponent.DiceContainer;
import it.polimi.ingsw.server.ModelComponent.Die;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ComponentFactoryTest
{
    ComponentFactory componentFactory;
    SchemeCardMP schemeCardMP;
    PublicObjectiveMP publicObjectiveMP;
    String username="ciccio";
    DraftPoolMP tempDraft;
    RoundTrackMP tempRound;

    @BeforeEach public void setUp() throws InvalidIntArgumentException, it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException, FullDataStructureException {
        componentFactory = new ComponentFactory();
        SchemesDeckMP schemesDeckMP = new SchemesDeckMP();
        schemeCardMP = schemesDeckMP.extractSchemebyID(6);
        schemeCardMP.setfb(2);
        publicObjectiveMP = new PublicObjectiveMP(3);

        DieMP[] tempVectDie= new DieMP[4];

        tempVectDie[0]=new DieMP(4);
        tempVectDie[0].throwDie();

        tempVectDie[1]=new DieMP(2);
        tempVectDie[1].throwDie();

        tempVectDie[2]=new DieMP(3);
        tempVectDie[2].throwDie();

        tempVectDie[3]=new DieMP(1);
        tempVectDie[3].throwDie();

        tempDraft=new DraftPoolMP(tempVectDie);

        RoundDiceMP tempRoundDice= new RoundDiceMP(1);

        RoundDiceMP tempRoundDice1= new RoundDiceMP(2);

        tempRoundDice.addDie(tempVectDie[0]);

        tempRoundDice1.addDie(tempVectDie[1]);
        tempRoundDice1.addDie(tempVectDie[2]);

        tempRound=new RoundTrackMP();
        tempRound.addRound(tempRoundDice);
        tempRound.addRound(tempRoundDice1);
    }

    @Test public void checkGComponent() throws InvalidIntArgumentException
    {
        for(int i=0;i<7;i++)
            System.out.println(componentFactory.selectionG(schemeCardMP, 1, 1)[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkDComponent()
    {
        for(int i=0;i<5;i++)
            System.out.println(componentFactory.selectionD(publicObjectiveMP)[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkCComponent() throws InvalidIntArgumentException
    {
        for(int i=0;i<9;i++)
            System.out.println(componentFactory.selectionC(new PrivateObjectiveMP(3),username,0)[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkIComponent()
    {
        for(int i=0;i<3;i++)
            System.out.println(componentFactory.selectionI()[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkAComponent() throws InvalidIntArgumentException
    {
        for(int i=0;i<6;i++)
            System.out.println(componentFactory.selectionA(schemeCardMP,username, 2)[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkTComponent() throws InvalidIntArgumentException
    {
        for(int i=0;i<5;i++)
            System.out.println(componentFactory.selectionT(4,5)[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkNComponent() throws InvalidIntArgumentException
    {
        for(int i=0;i<5;i++)
            System.out.println(componentFactory.selectionN(tempDraft)[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkMComponent() throws InvalidIntArgumentException
    {
        for(int i=0;i<5;i++)
            System.out.println(componentFactory.selectionM(tempRound)[i]);
        Assertions.assertEquals(true, true);
    }

}
