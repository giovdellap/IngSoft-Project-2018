package it.polimi.ingsw.client.Graphics.CLI;

import it.polimi.ingsw.client.Graphics.JSONReaders.ToolCardReader;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.*;

import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import org.fusesource.jansi.Ansi;

import java.io.FileNotFoundException;
import java.util.ArrayList;

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
    public String[] selectionG(SchemeCard scheme, int fb, int num) throws InvalidIntArgumentException
    {

        SchemeCard tempScheme = scheme;
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
        String[] cComponent = new String[3];
        cComponent[0]=cliToolsManager.centerThatString("USERNAME: "+user,26);
        cComponent[1]=cliToolsManager.centerThatString("TOKENS DISPONIBILI: "+tokens,26);
        cComponent[2]=cliToolsManager.centerThatString("OBIETTIVO PRIVATO: "+privobj.getColor(),26);

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

    public String[] selectionA(SchemeCard scheme, String username, int tokens) throws InvalidIntArgumentException
    {

        String[] aComponent = new String[7];

        SchemeCard tempScheme = scheme;
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
     */

    public String[] selectionT(int id, int token) throws FileNotFoundException {
        ToolCardReader reader=new ToolCardReader();

        ArrayList<String> testo=reader.readToolCard(id);

        String[] tComponent = new String[3];

        tComponent[0]=cliToolsManager.simpleQuestionsMaker("   N. "+id+"    ---"+testo.get(0)+"---",72,false)+"Tokens: "+token+"    ";

        tComponent[1]=cliToolsManager.simpleQuestionsMaker(testo.get(1),80,false);
        tComponent[2]=cliToolsManager.simpleQuestionsMaker(testo.get(2),80,false);

        return tComponent;

    }

}
