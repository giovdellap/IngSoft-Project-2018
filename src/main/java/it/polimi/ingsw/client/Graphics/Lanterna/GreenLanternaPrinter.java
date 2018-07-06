package it.polimi.ingsw.client.Graphics.Lanterna;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.terminal.Terminal;
import it.polimi.ingsw.client.Graphics.CLI.CLIToolsManager;
import it.polimi.ingsw.client.Graphics.CLI.PrinterMaker;
import it.polimi.ingsw.client.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.io.IOException;
import java.util.ArrayList;

public class GreenLanternaPrinter
{
    private Terminal terminal;
    private TextGraphics textGraphics;
    private CLIToolsManager cliToolsManager;
    private String msgIN;
    private PrinterMaker printerMaker;

    public GreenLanternaPrinter(Terminal terminal, TextGraphics textGraphics)
    {
        this.terminal=terminal;
        this.textGraphics=textGraphics;
        cliToolsManager=new CLIToolsManager();
        printerMaker = new PrinterMaker(1);
    }

    public SchemeCard selectScheme(SchemeCard scheme1, SchemeCard scheme2, String username, PrivateObjectiveMP privateObjectiveMP, PublicObjectiveMP[] publicObjectiveMPS, int[] tools)
    {
        
        return null;
    }

    public void askUsername() throws IOException {
        ArrayList<String> temp = new ArrayList<String>();
        printIt(cliToolsManager.sceneInitializer(40), TextColor.ANSI.MAGENTA, TextColor.ANSI.GREEN, SGR.BLINK);
        printIt(printerMaker.getUsernameInsertion(), TextColor.ANSI.MAGENTA, TextColor.ANSI.GREEN, SGR.BLINK);

    }


    public void printIt(ArrayList<String> toPrint, TextColor foreGround, TextColor backGround, SGR sgr)
    {
        textGraphics.setForegroundColor(foreGround);
        textGraphics.setBackgroundColor(backGround);
        textGraphics.enableModifiers(sgr);

        for(int x=0;x<toPrint.size();x++)
        {
            for(int y=0;y<toPrint.get(x).length();y++) {
                if(!(toPrint.get(x).charAt(y)==' '))
                    textGraphics.setCharacter(x, y, new TextCharacter(toPrint.get(x).charAt(y), foreGround, backGround));
            }
        }
    }
    public void printIt(String[] toPrint, TextColor foreGround, TextColor backGround, SGR sgr)
    {
        textGraphics.setForegroundColor(foreGround);
        textGraphics.setBackgroundColor(backGround);
        textGraphics.enableModifiers(sgr);

        for(int x=0;x<toPrint.length;x++)
        {
            for(int y=0;y<toPrint[x].length();y++) {
                if(!(toPrint[x].charAt(y)==' '))
                    textGraphics.setCharacter(x, y, new TextCharacter(toPrint[x].charAt(y), foreGround, backGround));
            }
        }

        textGraphics.disableModifiers(sgr);
    }
    public String readIt() throws IOException {
        String toReturn="";
        boolean endRead=false;
        while(!endRead)
        {
            KeyStroke temp = terminal.readInput();
            if(temp.getKeyType()==KeyType.Character)
                toReturn+=temp.toString();
            if(temp.getKeyType()==KeyType.Enter)
                endRead=true;
        }
        return toReturn;
    }


}
