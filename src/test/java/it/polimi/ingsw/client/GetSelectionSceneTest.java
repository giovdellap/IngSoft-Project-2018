package it.polimi.ingsw.client;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.PublicObjectiveMP;

import it.polimi.ingsw.client.Graphics.CLI.PrinterMaker;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;

public class GetSelectionSceneTest
{
    private PrinterMaker printerMakerTest;
    private SchemesDeck schemesDeckMP;
    private SchemeCard testScheme;
    private SchemeCard testScheme1;
    private PrivateObjectiveMP testPrivObj;
    private int [] tempTools;
    private PublicObjectiveMP [] tempPubObj;

    @BeforeEach

    public void setUp() throws InvalidIntArgumentException, FileNotFoundException, it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException {
        printerMakerTest=new PrinterMaker(1);

        schemesDeckMP = new SchemesDeck();
        testScheme = schemesDeckMP.extractSchemebyID(5);
        testScheme1 = schemesDeckMP.extractSchemebyID(7);

        testPrivObj= new PrivateObjectiveMP(3);


        tempPubObj= new PublicObjectiveMP[3];
        tempPubObj[0]=new PublicObjectiveMP(3);
        tempPubObj[1]=new PublicObjectiveMP(4);
        tempPubObj[2]=new PublicObjectiveMP(7);

        tempTools= new int[3];
        tempTools[0]=1;
        tempTools[1]=2;
        tempTools[2]=3;

    }

    @Test
    public void getSelectionSceneTest() throws InvalidIntArgumentException
    {
        for(int i=0;i<21;i++)
            System.out.println(printerMakerTest.getSelectionScene(testScheme,testScheme1,"pippo",testPrivObj,tempPubObj,tempTools)[i]);

        Assertions.assertEquals(true, true);

    }



}
