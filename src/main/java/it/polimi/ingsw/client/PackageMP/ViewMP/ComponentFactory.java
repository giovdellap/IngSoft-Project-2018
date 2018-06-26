package it.polimi.ingsw.client.PackageMP.ViewMP;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.CLIToolsManager;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.ModelGenerator;
import org.fusesource.jansi.Ansi;

public class ComponentFactory
{
    private CLIToolsManager cliToolsManager;
    private ModelGenerator modelGenerator;

    /**
     * ComponentFactory Constructor
     */
    public ComponentFactory()
    {
        cliToolsManager = new CLIToolsManager();
        modelGenerator = new ModelGenerator();
    }

    /**
     * Scene selection generator
     * @param scheme
     * @param fb
     * @param num
     * @return
     * @throws InvalidIntArgumentException
     */
    public String[] selectionG(SchemeCardMP scheme, int fb, int num) throws InvalidIntArgumentException
    {

        SchemeCardMP tempScheme = scheme;
        tempScheme.setfb(fb);
        String[] gComponent = new String[7];

        for(int i=0;i<5;i++)
            gComponent[i]=cliToolsManager.printSpaces(7)+modelGenerator.getScheme(tempScheme)[i]+cliToolsManager.printSpaces(4);

        gComponent[5]=cliToolsManager.centerThatString(scheme.getName(fb)+"-->"+Integer.toString(scheme.getDiff(fb)), 20);

        gComponent[6]=cliToolsManager.centerThatString("NUMERO SELEZIONE:"+Integer.toString(num), 20);

        return gComponent;
    }

    /**
     * public objective generator
     * @param pubobj
     * @return
     */
    public String[] selectionD(PublicObjectiveMP pubobj)
    {

        String[] dComponent = new String[5];

        dComponent[0]=cliToolsManager.centerThatString(" OBIETTIVO" , 20);
        dComponent[1]=cliToolsManager.centerThatString("PUBBLICO", 20);
        dComponent[2]=cliToolsManager.printSpaces(20);
        dComponent[3]=cliToolsManager.centerThatString("ID: "+Integer.toString(pubobj.getId()), 20);
        dComponent[4]=cliToolsManager.printSpaces(20);

        return dComponent;
    }

    /**
     * private objective generator
     * @param privobj
     * @param user
     * @param tokens
     * @return
     */
    public String[] selectionC(PrivateObjectiveMP privobj, String user, int tokens)
    {

        String[] cComponent = new String[10];

        cComponent[0]=cliToolsManager.centerThatString("USERNAME: ",20);
        cComponent[1]=cliToolsManager.centerThatString(user,20);
        cComponent[2]=cliToolsManager.printSpaces(20);
        cComponent[3]=cliToolsManager.centerThatString("TOKENS: "+tokens,20);

        cComponent[4]=cliToolsManager.centerThatString("   OBIETTIVO",20);
        cComponent[5]=cliToolsManager.centerThatString("  PRIVATO:",20);

        if (privobj.getColor()==1) {

            cComponent[6] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.YELLOW) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[7] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.YELLOW) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[8] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.YELLOW) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
        }

        if (privobj.getColor()==2) {

            cComponent[6] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.RED) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[7] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.RED) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[8] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.RED) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
        }

        if (privobj.getColor()==3) {

            cComponent[6] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.GREEN) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[7] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.GREEN) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[8] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.GREEN) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
        }

        if (privobj.getColor()==4) {

            cComponent[6] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.BLUE) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[7] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.BLUE) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[8] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.BLUE) +"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+ Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
        }

        if (privobj.getColor()==5) {

            cComponent[6] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.MAGENTA)+"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[7] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.MAGENTA)+"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
            cComponent[8] = cliToolsManager.printSpaces(8)+Ansi.ansi().reset().fg(Ansi.Color.MAGENTA)+"\u25fc"+"\u25fc"+"\u25fc"+"\u25fc"+Ansi.ansi().reset().fg(Ansi.Color.DEFAULT)+cliToolsManager.printSpaces(8);
        }

        cComponent[9]=cliToolsManager.printSpaces(20);

        return cComponent;

    }

    /**
     * generates the beginning scheme selection event
     * @return
     */
    public String[] selectionI()
    {
        String[] iComponent = new String[3];
        iComponent[0]=cliToolsManager.printSpaces(80);
        iComponent[1]=cliToolsManager.simpleQuestionsMaker("SCEGLI LO SCHEMA..", 40, true);
        iComponent[2]=cliToolsManager.printSpaces(80);

        return iComponent;
    }

    /**
     * shows the selected scheme card, ready for the game
     * @param scheme
     * @param username
     * @param tokens
     * @return
     * @throws InvalidIntArgumentException
     */

    public String[] selectionA(SchemeCardMP scheme, String username, int tokens) throws InvalidIntArgumentException
    {

        String[] aComponent = new String[7];

        SchemeCardMP tempScheme = scheme;
        aComponent[0]="";
        aComponent[0]=cliToolsManager.centerThatString(modelGenerator.getScheme(tempScheme)[0], 20);
        for(int i=1;i<5;i++)
            aComponent[i]=cliToolsManager.printSpaces(7)+modelGenerator.getScheme(tempScheme)[i]+cliToolsManager.printSpaces(4);

        aComponent[5]=cliToolsManager.centerThatString(username,16)+cliToolsManager.printSpaces(4);
        aComponent[6]=cliToolsManager.centerThatString("TOKENS: "+tokens,20);
        return aComponent;

    }

    /**
     * generates a draftpool with all of its unicoded dice
     * @param draft
     * @return
     * @throws InvalidIntArgumentException
     */
    public String[] selectionN(DraftPoolMP draft) throws InvalidIntArgumentException
    {
        String[] nComponent = new String[3];

        DraftPoolMP tempdraft = draft;

        int j=0;

        nComponent[j]=cliToolsManager.centerThatString("DRAFTPOOL",20);
        j++;

        nComponent[j]="  1 2 3 4 5 6 7 8 9 ";
        j++;

        nComponent[j]=cliToolsManager.printSpaces(2)+modelGenerator.getDraft(tempdraft)+cliToolsManager.printSpaces(3);
        j++;



        return nComponent;

    }

    /**
     * generates the empty (at the beginning) round track
     * @param trackMP
     * @return
     * @throws InvalidIntArgumentException
     */
    public String[] selectionM(RoundTrackMP trackMP) throws InvalidIntArgumentException {
        return modelGenerator.getRoundTrack(trackMP);
    }

    /**
     * generates the extracted tool cards
     * @param id
     * @param token
     * @return
     * @throws InvalidIntArgumentException
     */

    public String[] selectionT(int id, int token) throws InvalidIntArgumentException
    {

        String[] tComponent = new String[5];

        tComponent[0]=cliToolsManager.printSpaces(20);
        tComponent[1]=cliToolsManager.centerThatString("TOOL: "+id,20);
        tComponent[2]=cliToolsManager.printSpaces(20);
        tComponent[3]=cliToolsManager.centerThatString("TOKENS: "+token,20);
        tComponent[4]=cliToolsManager.printSpaces(20);

        return tComponent;

    }

}
