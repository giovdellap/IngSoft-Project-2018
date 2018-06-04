package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.PlayerClient;

import java.util.ArrayList;

public class PrinterMaker
{
    private ModelGenerator modelGenerator;
    private CLIToolsManager cliToolsManager;
    private ComponentFactory componentFactory;
    private int token=0;
    private int [] toolsId;

    public PrinterMaker()
    {
        modelGenerator = new ModelGenerator();
        cliToolsManager = new CLIToolsManager();
        componentFactory = new ComponentFactory();
    }

    //USERNAME INSERTION
    public String[] getUsernameInsertion()
    {
        //returns a string[30] 40 width asking for username insertion

        String[] temp = cliToolsManager.blankLinesInitializer(20, 10, 40);
        temp[10]=cliToolsManager.printSpaces(2)+"INSERISCI USERNAME:"+cliToolsManager.printSpaces(19);

        return cliToolsManager.blankLinesEnder(temp, 11);
    }
    public String[] getUsernameInsertionAgain(String badUsername)
    {
        //returns a string[40] 30 width claiming badUsername is wrong and asking to type another one

        String[] temp = cliToolsManager.blankLinesInitializer(20, 16, 40);
        temp[16] = cliToolsManager.printSpaces(2)+"Username "+badUsername+" errato"+cliToolsManager.printSpacesEnder(temp[24], 40);
        temp[17] = cliToolsManager.printSpaces(40);
        temp[18] = cliToolsManager.simpleQuestionsMaker("INSERISCI USERNAME", 40, true);

        return cliToolsManager.blankLinesEnder(temp, 19);
    }


    public String[] getSelectionScene(SchemeCardMP scheme1, SchemeCardMP scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs, int[] tools) throws InvalidIntArgumentException
    {
        //returns the final selection scene string 40x21
        String[] temp = new String[21];

        toolsId=tools;

        int j;


        //Gs ROW
        for(int i=0;i<7;i++)
            temp[i]=componentFactory.selectionG(scheme1, 1, 1)[i]+componentFactory.selectionG(scheme1, 2, 2)[i]+componentFactory.selectionG(scheme2,1,3)[i]+componentFactory.selectionG(scheme2,2,4)[i];

        //BLANK LINE
        temp[7]=cliToolsManager.printSpaces(40);


        //C-D
        for(int i=8;i<13;i++)
        {
            j=0;
            temp[i] = componentFactory.selectionC(privObj, username, 0)[i-8];

            for (j=0;j<3;j++)
                temp[i]+=componentFactory.selectionD(pubObjs[j])[i-8];

        }

        //C-T
        j=0;

        for (int i=13;i<18;i++)
        {
            j=0;
            temp[i] = componentFactory.selectionC(privObj, username, 0)[i-13] + componentFactory.selectionT(toolsId[j],0)[i-13];

            for (j=1;j<3;j++)
                temp[i]+=componentFactory.selectionT(toolsId[j],0)[i-13];
        }

        //I ROW
        for(int i=18;i<21;i++)
            temp[i]=componentFactory.selectionI()[i-18];


        return temp;
    }

    public String[] getGameScene(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP round, PrivateObjectiveMP privObj, PublicObjectiveMP[]pubObjs, int[]toolsTokens, int activePlayers, int me) throws InvalidIntArgumentException
    {
        ArrayList<String> temp;
        temp=new ArrayList<String>();
        for (int i=0;i<6;i++)
            temp.add(componentFactory.selectionA(players[0].getPlayerScheme(),players[0].getName(),players[0].getTokens())[i]+componentFactory.selectionA(players[1].getPlayerScheme(),players[1].getName(),players[1].getTokens())[i]);

        temp.add(cliToolsManager.printSpaces(40));

        if (players.length==3)
            for (int i=0;i<6;i++)
                temp.add(componentFactory.selectionA(players[2].getPlayerScheme(),players[2].getName(),players[2].getTokens())[i]);

        if (players.length==4)
            for (int i=0;i<6;i++)
                temp.add(componentFactory.selectionA(players[2].getPlayerScheme(),players[2].getName(),players[2].getTokens())[i]+componentFactory.selectionA(players[3].getPlayerScheme(),players[3].getName(),players[3].getTokens())[i]);

        for (int i =0;i<5;i++)
            temp.add(componentFactory.selectionN(draft)[i]+componentFactory.selectionM(round)[i]);

        for (int i=0;i<5;i++)
        {
            String pippo;
            pippo=componentFactory.selectionC(privObj,players[me].getName(),players[me].getTokens())[i];

            for (int j=0;j<3;j++)

                pippo+=componentFactory.selectionD(pubObjs[j])[i];

            temp.add(pippo);

        }

        for (int i=0;i<5;i++)
        {
            String pluto;
            pluto=componentFactory.selectionC(privObj,players[me].getName(),players[me].getTokens())[i+5];

            for (int j=0;j<3;j++)
                pluto+=componentFactory.selectionT(toolsId[j],toolsTokens[j])[i];

            temp.add(pluto);
        }

        String[] tempArray= new String[temp.size()];

        for (int i=0;i<temp.size();i++)
            tempArray[i]=temp.get(i);

        return tempArray;

    }

    public void setToolsIdTest(int[]tools)
    {
        toolsId=tools;
    }

    public String round(int numround,String activeUsername)
    {
        String temp = new String(cliToolsManager.centerThatString(("ROUND: "+numround+"  "+"TURNO: "+activeUsername),40));

        return temp;
    }

    public String[] disconnectedPlayers(String[] username)
    {
        int i = 0;

        String[] tempVect = new String[username.length];

        tempVect[0]=new String("GIOCATORI SCONNESSI: ");

        while (i<username.length)
        {
            String temp = new String(username[i]);
            tempVect[i]=temp;
            i++;
        }

            return tempVect;
    }

    public String[] selectAction()
    {
        String[] tempVect = new String[2];

        tempVect[0]= new String(cliToolsManager.centerThatString(("COSA VUOI FARE..?"), 40));

        tempVect[1]= new String(cliToolsManager.centerThatString(("0 PER PASSARE, 2 PER MUOVERE, 3 PER TOOL"), 40));

        return tempVect;
    }

    public String canMove()
    {
        return new String(cliToolsManager.centerThatString(("PUOI MUOVERE "),40));
    }

    public String cantMove()
    {
        return new String(cliToolsManager.centerThatString(("HAI GIA MOSSO"),40));
    }

    public String insertDraftIndex()
    {
        return new String(cliToolsManager.centerThatString(("SELEZIONA LA DRAFT.."),40));
    }

    public String insertX()
    {
        return new String(cliToolsManager.centerThatString(("INSERISCI RIGA.."),40));
    }

    public String insertY()
    {
        return new String(cliToolsManager.centerThatString(("INSERISCI COLONNA.."),40));
    }

    public String moveAccepted()
    {
        return new String(cliToolsManager.centerThatString(("MOSSA ACCETTATA"),40));
    }

    public String moveRefused()
    {
        return new String(cliToolsManager.centerThatString(("MOSSA RIFIUTATA"),40));
    }

    public String wrongInsertion()
    {
        return new String(cliToolsManager.centerThatString(("INSERIMENTO NON VALIDO"),40));
    }


    //WAIT SCENES
    public String[] waitingForPlayersScene()
    {
        String[] temp = cliToolsManager.blankLinesInitializer(20, 12, 40);
        temp[12]=cliToolsManager.centerThatString("IN ATTESA CHE LA LOBBY SIA PIENA", 40);
        return cliToolsManager.blankLinesEnder(temp, 13);
    }

    public String[] waitingForLobbyScene()
    {
        String[] temp = cliToolsManager.blankLinesInitializer(20, 12, 40);
        temp[12]=cliToolsManager.centerThatString("CREAZIONE LOBBY IN CORSO", 40);
        return cliToolsManager.blankLinesEnder(temp, 13);
    }

    public void setToolsID(int[] tools)
    {
        this.toolsId=tools;

    }
}
