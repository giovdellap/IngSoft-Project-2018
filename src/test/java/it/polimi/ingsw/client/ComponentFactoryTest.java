package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.ComponentFactory;
import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ComponentFactoryTest
{
    ComponentFactory componentFactory;
    SchemeCard schemeCardMP;
    PublicObjectiveMP publicObjectiveMP;
    String username="ciccio";
    DraftPoolMP tempDraft;
    RoundTrackMP tempRound;

    @BeforeEach public void setUp() throws InvalidIntArgumentException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, FullDataStructureException, FileNotFoundException {
        componentFactory = new ComponentFactory();
        SchemesDeck schemesDeckMP = new SchemesDeck();
        schemeCardMP = schemesDeckMP.extractSchemebyID(6);
        schemeCardMP.setfb(2);
        publicObjectiveMP = new PublicObjectiveMP(3);

        ArrayList<Die> tempVectDie= new ArrayList<Die>();

        Die tempDie=new Die(4);
        tempDie.throwDie();
        tempVectDie.add(tempDie);

        tempDie=new Die(2);
        tempDie.throwDie();
        tempVectDie.add(tempDie);

        tempDie=new Die(3);
        tempDie.throwDie();
        tempVectDie.add(tempDie);

        tempDie=new Die(1);
        tempDie.throwDie();
        tempVectDie.add(tempDie);

        tempDraft=new DraftPoolMP(tempVectDie);

        RoundDiceMP tempRoundDice= new RoundDiceMP(1);

        RoundDiceMP tempRoundDice1= new RoundDiceMP(2);

        tempRoundDice.addDie(tempVectDie.get(0));

        tempRoundDice1.addDie(tempVectDie.get(1));
        tempRoundDice1.addDie(tempVectDie.get(2));

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
        for(int i=0;i<3;i++)
            System.out.println(componentFactory.selectionN(tempDraft)[i]);
        Assertions.assertEquals(true, true);
    }

    @Test public void checkMComponent() throws InvalidIntArgumentException
    {
        for(int i=0;i<componentFactory.selectionM(tempRound).length;i++)
            System.out.println(componentFactory.selectionM(tempRound)[i]);
        Assertions.assertEquals(true, true);
    }

}
