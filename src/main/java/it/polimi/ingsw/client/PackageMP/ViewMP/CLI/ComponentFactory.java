package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import org.fusesource.jansi.Ansi;

public class ComponentFactory
{

    private CLIToolsManager cliToolsManager;
    private ModelGenerator modelGenerator;

    public ComponentFactory()
    {
        cliToolsManager = new CLIToolsManager();
        modelGenerator = new ModelGenerator();
    }
    //SCENE SELECTION
    public String[] schemeCardExposure(SchemeCardMP scheme, int fb, int num) throws InvalidIntArgumentException {
        //receives a schemecard and the front/back
        //returns a String[7] (String.lenght = 20) representing the schemecard(20x20)
        //SELECTION G

        SchemeCardMP tempScheme = scheme;
        tempScheme.setfb(fb);
        String[] gComponent = new String[7];

        for(int i=0;i<5;i++)
            gComponent[i]=cliToolsManager.printSpaces(7)+modelGenerator.getScheme(tempScheme)[i]+cliToolsManager.printSpaces(7);

        gComponent[5]=cliToolsManager.centerThatString(scheme.getName(fb)+cliToolsManager.printSpaces(3)+Integer.toString(scheme.getDiff(fb)), 20);

        gComponent[6]=cliToolsManager.centerThatString("PRESS "+Integer.toString(num), 20);

        return gComponent;
    }

    public String[] showUsername(String username)
    {
        //returns a String[3] (string.lenght = 40) showing the username
        //SELECTION H

        String[] hComponent = new String[3];

        hComponent[0]=cliToolsManager.printSpaces(40);
        hComponent[1]=cliToolsManager.centerThatString("USERNAME: "+ username, 40);
        hComponent[2]=cliToolsManager.printSpaces(40);

        return hComponent;
    }

    public String[] showPubObj(PublicObjectiveMP pubobj)
    {
        //returns a String[5] (string.lenght = 10) showing the private objective
        //SELECTION D

        String[] dComponent = new String[5];

        dComponent[0]=cliToolsManager.centerThatString("OBIETTIVO" , 10);
        dComponent[1]=cliToolsManager.centerThatString("PUBBLICO", 10);
        dComponent[2]=cliToolsManager.printSpaces(10);
        dComponent[3]=cliToolsManager.centerThatString("ID: "+Integer.toString(pubobj.getId()), 10);
        dComponent[4]=cliToolsManager.printSpaces(10);

        return dComponent;
    }

    public String[] showPrivObj(PrivateObjectiveMP privobj)
    {
        //returns a String[5] (string.lenght = 10) showing the private objective
        //SELECTION C

        String[] cComponent = new String[5];


        cComponent[0]=cliToolsManager.centerThatString("OBIETTIVO",10);
        cComponent[1]=cliToolsManager.centerThatString("PRIVATO:",10);


        if (privobj.getColor()==1) {

            cComponent[2] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.YELLOW) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[3] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.YELLOW) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[4] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.YELLOW) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
        }

        if (privobj.getColor()==2) {

            cComponent[2] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.RED) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[3] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.RED) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[4] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.RED) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);

        }

        if (privobj.getColor()==3) {

            cComponent[2] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.GREEN) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[3] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.GREEN) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[4] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.GREEN) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);

        }

        if (privobj.getColor()==4) {

            cComponent[2] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.BLUE) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[3] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.BLUE) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[4] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.BLUE) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);

        }

        if (privobj.getColor()==5) {

            cComponent[2] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.CYAN)+"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[3] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.CYAN)+"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[4] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.CYAN)+"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);

        }

        return cComponent;

    }

    public String[] askingScheme()
    {
        //returns the asking scheme strings below everything
        //String[3] (string.lenght = 40)
        //SELECTION I

        String[] iComponent = new String[3];
        iComponent[0]=cliToolsManager.printSpaces(40);
        iComponent[1]=cliToolsManager.simpleQuestionsMaker("SCEGLI LO SCHEMA", 40, true);
        iComponent[2]=cliToolsManager.printSpaces(40);

        return iComponent;
    }
}
