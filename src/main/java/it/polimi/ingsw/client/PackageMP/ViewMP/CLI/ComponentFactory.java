package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import org.fusesource.jansi.Ansi;

public class ComponentFactory
{
    private CLIToolsManager cliToolsManager;
    private ModelGenerator modelGenerator;

    //COSTRUCTOR
    public ComponentFactory()
    {
        cliToolsManager = new CLIToolsManager();
        modelGenerator = new ModelGenerator();
    }

    //SCENE SELECTION

    public String[] selectionG(SchemeCardMP scheme, int fb, int num) throws InvalidIntArgumentException
    {
        //receives a schemecard, front/back parameter and the selection number
        //returns a String[7] (String.lenght = 20) representing the schemecard(20x20)
        //SELECTION G

        SchemeCardMP tempScheme = scheme;
        tempScheme.setfb(fb);
        String[] gComponent = new String[7];

        for(int i=0;i<5;i++)
            gComponent[i]=cliToolsManager.printSpaces(7)+modelGenerator.getScheme(tempScheme)[i]+cliToolsManager.printSpaces(7);

        gComponent[5]=cliToolsManager.centerThatString(scheme.getName(fb)+cliToolsManager.printSpaces(3)+Integer.toString(scheme.getDiff(fb)), 20);

        gComponent[6]=cliToolsManager.centerThatString("NUMERO SELEZIONE: "+Integer.toString(num), 20);

        return gComponent;
    }

    public String[] selectionD(PublicObjectiveMP pubobj)
    {
        //receives a public objective
        //returns a String[5] (string.lenght = 10) showing the public objective
        //SELECTION D

        String[] dComponent = new String[5];

        dComponent[0]=cliToolsManager.centerThatString("OBIETTIVO" , 10);
        dComponent[1]=cliToolsManager.centerThatString("PUBBLICO", 10);
        dComponent[2]=cliToolsManager.printSpaces(10);
        dComponent[3]=cliToolsManager.centerThatString("ID: "+Integer.toString(pubobj.getId()), 10);
        dComponent[4]=cliToolsManager.printSpaces(10);

        return dComponent;
    }

    public String[] selectionC(PrivateObjectiveMP privobj, String user, int tokens)
    {
        //receives a private objective, an username and the tokens owned by the user
        //returns a String[10] (string.lenght = 10) showing the private objective
        //SELECTION C

        String[] cComponent = new String[10];

        cComponent[0]=cliToolsManager.centerThatString("USERNAME: ",10);
        cComponent[1]=cliToolsManager.centerThatString(user,10);
        cComponent[2]=cliToolsManager.printSpaces(10);
        cComponent[3]=cliToolsManager.centerThatString("TOKENS: "+tokens,10);

        cComponent[4]=cliToolsManager.centerThatString("OBIETTIVO",10);
        cComponent[5]=cliToolsManager.centerThatString("PRIVATO:",10);

        if (privobj.getColor()==1) {

            cComponent[6] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.YELLOW) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[7] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.YELLOW) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[8] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.YELLOW) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
        }

        if (privobj.getColor()==2) {

            cComponent[6] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.RED) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[7] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.RED) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[8] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.RED) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
        }

        if (privobj.getColor()==3) {

            cComponent[6] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.GREEN) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[7] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.GREEN) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[8] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.GREEN) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
        }

        if (privobj.getColor()==4) {

            cComponent[6] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.BLUE) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[7] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.BLUE) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[8] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.BLUE) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
        }

        if (privobj.getColor()==5) {

            cComponent[6] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.CYAN)+"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[7] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.CYAN)+"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
            cComponent[8] = cliToolsManager.printSpaces(3)+Ansi.ansi().reset().fg(Ansi.Color.CYAN)+"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(3);
        }

        cComponent[9]=cliToolsManager.printSpaces(10);

        return cComponent;

    }

    public String[] selectionI()
    {
        //returns the asking scheme strings below everything
        //String[3] (string.lenght = 40)
        //SELECTION I

        String[] iComponent = new String[3];
        iComponent[0]=cliToolsManager.printSpaces(40);
        iComponent[1]=cliToolsManager.simpleQuestionsMaker("SCEGLI LO SCHEMA..", 40, true);
        iComponent[2]=cliToolsManager.printSpaces(40);

        return iComponent;
    }

    public String[] selectionA(SchemeCardMP scheme, String username, int tokens) throws InvalidIntArgumentException
    {
        //receives a scheme and the f/b selection
        //returns a String[6] (string.lenght = 20) showing the scheme card
        //SELECTION A

        String[] aComponent = new String[6];

        SchemeCardMP tempScheme = scheme;

        for(int i=0;i<5;i++)
            aComponent[i]=cliToolsManager.printSpaces(7)+modelGenerator.getScheme(tempScheme)[i]+cliToolsManager.printSpaces(7);

        aComponent[5]=username+" > "+"TOKENS:"+tokens;

        return aComponent;

    }

    public String[] selectionN(DraftPoolMP draft) throws InvalidIntArgumentException
    {
        //receives a draft
        //returns a String[5] (string.lenght = 20) showing the draftpool
        //SELECTION N

        String[] nComponent = new String[5];

        DraftPoolMP tempdraft = draft;

        int j=0;

        nComponent[0]=cliToolsManager.printSpaces(20);
        nComponent[1]=cliToolsManager.centerThatString("DRAFTPOOL",20);

        nComponent[2]=cliToolsManager.printSpaces(2)+modelGenerator.getDraft(tempdraft)+cliToolsManager.printSpaces(3);

        nComponent[3]="  1 2 3 4 5 6 7 8 9 ";

        nComponent[4]=cliToolsManager.printSpaces(20);

        return nComponent;

    }

    public String[] selectionM(RoundTrackMP round) throws InvalidIntArgumentException
    {
        String[] mComponent = new String[5];

        RoundTrackMP tempround = round;

        int j=0;

        mComponent[0]=cliToolsManager.printSpaces(20);
        mComponent[1]=cliToolsManager.centerThatString("ROUNDTRACK",20);

        for(int i=2;i<4;i++)
        {
            mComponent[i] =cliToolsManager.printSpaces(2)+ modelGenerator.getRoundTrack(tempround)[j];
            j++;
        }

        mComponent[4]="  1 2 3 4 5 6 7 8 9 ";

        return mComponent;

    }

    public String[] selectionT(int id, int token) throws InvalidIntArgumentException
    {
        //receives the tool id and the tokens needed
        //returns a String[5] (string.lenght = 10) showing the tool
        //SELECTION T

        String[] tComponent = new String[5];

        tComponent[0]=cliToolsManager.printSpaces(10);
        tComponent[1]=cliToolsManager.centerThatString("TOOL: "+id,10);
        tComponent[2]=cliToolsManager.printSpaces(10);
        tComponent[3]=cliToolsManager.centerThatString("TOKENS: "+token,10);
        tComponent[4]=cliToolsManager.printSpaces(10);

        return tComponent;

    }
}
