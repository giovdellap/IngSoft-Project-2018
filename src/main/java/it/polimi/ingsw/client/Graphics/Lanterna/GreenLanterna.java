package it.polimi.ingsw.client.Graphics.Lanterna;

import com.googlecode.lanterna.SGR;
import com.googlecode.lanterna.TerminalSize;
import com.googlecode.lanterna.TextCharacter;
import com.googlecode.lanterna.TextColor;
import com.googlecode.lanterna.graphics.TextGraphics;
import com.googlecode.lanterna.input.KeyStroke;
import com.googlecode.lanterna.input.KeyType;
import com.googlecode.lanterna.screen.Screen;
import com.googlecode.lanterna.screen.TerminalScreen;
import com.googlecode.lanterna.terminal.Terminal;
import com.googlecode.lanterna.terminal.DefaultTerminalFactory;
import it.polimi.ingsw.client.Graphics.AbstractGraphic;
import it.polimi.ingsw.client.Graphics.CLI.CLIToolsManager;
import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.RoundTrackMP;
import it.polimi.ingsw.client.PlayerClient;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardElevenEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardSixEvent;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.io.IOException;
import java.util.ArrayList;

public class GreenLanterna extends AbstractGraphic implements Runnable
{
    private Terminal terminal;
    private TextGraphics textGraphics;
    private Screen screen;
    private TerminalSize size;
    private GreenLanternaPrinter printer;
    private CLIToolsManager cliToolsManager;

    public GreenLanterna() throws IOException {

        DefaultTerminalFactory factory = new DefaultTerminalFactory();

        //creating terminal
        terminal=factory.createTerminal();
        //entering private mode
        terminal.enterPrivateMode();

        //creating screen
        screen = new TerminalScreen(terminal);
        screen.startScreen();

        textGraphics = screen.newTextGraphics();
        size = screen.doResizeIfNecessary();

        terminal.setBackgroundColor(new TextColor.RGB(149, 149, 149));

        printer= new GreenLanternaPrinter(terminal, textGraphics);

    }

    public void run() {

    }


    public SchemeCard setInitializationScene(SchemeCard scheme1, SchemeCard scheme2, String username, PrivateObjectiveMP privateObjectiveMP, PublicObjectiveMP[] publicObjectiveMPS, int[] tools) throws InvalidIntArgumentException, IOException {
        return null;
    }

    public String askUsername() throws IOException {
        printer.askUsername();
        String username = readIt();
        printIt(cliToolsManager.sceneEnder(40), TextColor.ANSI.MAGENTA, TextColor.ANSI.GREEN, SGR.BLINK);
        return username;
    }

    public void setWaitScene() {

    }

    public void setWaitScene2() {

    }

    public void askForWhat(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected) throws InvalidIntArgumentException, IOException {
        System.out.println("Sieg heil");
    }

    public void move(int draftDim) throws IOException {
    }

    public void moveAccepted() {

    }

    public void moveRefused() {

    }

    public void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<String> disconnected) throws InvalidIntArgumentException, IOException {

    }


    public void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round) throws InvalidIntArgumentException, IOException {

    }

    public void showMove(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, MoveEvent event) throws InvalidIntArgumentException {

    }

    public void showTool(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, ToolCardEvent event) throws InvalidIntArgumentException {

    }

    public void setUseTool(ToolCardEvent event) {

    }

    public void useTool(int draftDim, int roundTrackDim, int round) throws IOException {
    }

    public ToolCardSixEvent toolCardSixEventPartTwo(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ToolCardSixEvent previousEvent) throws InvalidIntArgumentException, IOException {
        return null;
    }

    public ToolCardElevenEvent toolCardElevenEventPartTwo(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ToolCardElevenEvent previousEvent) throws InvalidIntArgumentException, IOException {
        return null;
    }

    public void toolAccepted() {

    }

    public boolean showScores(ScoreEvent event, boolean winner) throws InvalidIntArgumentException, IOException {
        return false;
    }


    //READ/PRINT
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
