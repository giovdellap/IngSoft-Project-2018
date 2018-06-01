package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;

public class PrinterMaker
{
    private ModelGenerator modelGenerator;
    private CLIToolsManager cliToolsManager;
    private ComponentFactory componentFactory;

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


    public String[] getSelectionScene(SchemeCardMP scheme1, SchemeCardMP scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs) throws InvalidIntArgumentException {
        //returns the final selection scene string 80x50
        String[] temp = cliToolsManager.blankLinesInitializer(27, 1, 40);

        //G ROW 1
        for(int i=1;i<8;i++)
            temp[i]=componentFactory.schemeCardExposure(scheme1, 1, 1)[i-1]+componentFactory.schemeCardExposure(scheme1, 2, 2)[i-1];

        //BLANK LINE
        temp[8]=cliToolsManager.printSpaces(40);

        //G ROW 2
        for(int i=9;i<16;i++)
            temp[i]=componentFactory.schemeCardExposure(scheme2, 1, 3)[i-9]+componentFactory.schemeCardExposure(scheme2, 2, 4)[i-9];

        //H ROW
        temp[16]=componentFactory.showUsername(username)[0];
        temp[17]=componentFactory.showUsername(username)[1];
        temp[18]=componentFactory.showUsername(username)[2];

        //OBJECTIVES ROW (C/D)
        for(int i=19;i<24;i++)
            temp[i]=componentFactory.showPrivObj(privObj)[i-19]+componentFactory.showPubObj(pubObjs[0])[i-19]+componentFactory.showPubObj(pubObjs[1])[i-19]+componentFactory.showPubObj(pubObjs[2])[i-19];

        //I ROW
        for(int i=24;i<27;i++)
            temp[i]=componentFactory.askingScheme()[i-24];

        return temp;
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




}
