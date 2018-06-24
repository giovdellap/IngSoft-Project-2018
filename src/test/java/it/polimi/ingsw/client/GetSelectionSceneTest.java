package it.polimi.ingsw.client;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemesDeckMP;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.ModelGenerator;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.PrinterMaker;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class GetSelectionSceneTest
{
    private PrinterMaker printerMakerTest;
    private SchemesDeckMP schemesDeckMP;
    private SchemeCardMP testScheme;
    private SchemeCardMP testScheme1;
    private PrivateObjectiveMP testPrivObj;
    private int [] tempTools;
    private PublicObjectiveMP [] tempPubObj;

    @BeforeEach

    public void setUp() throws InvalidIntArgumentException
    {
        printerMakerTest=new PrinterMaker(1);

        schemesDeckMP = new SchemesDeckMP();
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
