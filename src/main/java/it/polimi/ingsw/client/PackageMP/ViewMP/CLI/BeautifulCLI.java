package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.PlayerClient;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.io.*;
import java.util.ArrayList;

public class BeautifulCLI
{
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    private PrinterMaker printerMaker;
    private CLIToolsManager cliToolsManager;

    private String msgIN;

    private PrivateObjectiveMP privateObjective;
    private PublicObjectiveMP[] pubObjs;
    private int[] toolsID;
    private int width;

    /**
     * BeautifulCLI Constructor
     * @param settings
     */
    public BeautifulCLI(int settings)
    {
        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        printerMaker = new PrinterMaker(1);
        cliToolsManager = new CLIToolsManager();
        msgIN = "";

        if(settings==0)
            width=40;
        else
            width=80;
    }

    /**
     * asks for Username
     * @return
     * @throws IOException
     */
    public String askUsername() throws IOException
    {

        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getUsernameInsertion());
        readIt();
        printOut(cliToolsManager.sceneEnder(40));

        return msgIN;

    }

    /**
     * sets waiting scene from CLIToolsManager
     */
    public void setWaitScene()
    {
        printOut(printerMaker.printTitle());
        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.waitingForPlayersScene());
        printOut(cliToolsManager.sceneEnder(width));
    }

    /**
     * sets initialization scene with all the model's components
     * @param scheme1
     * @param scheme2
     * @param username
     * @param privObj
     * @param pubObjs
     * @param tools
     * @return
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public SchemeCard setInitializationScene(SchemeCard scheme1, SchemeCard scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs, int[] tools) throws InvalidIntArgumentException, IOException
    {
        privateObjective = privObj;
        this.pubObjs = pubObjs;
        this.toolsID = tools;
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getSelectionScene(scheme1, scheme2, username, privObj, pubObjs, tools));

        readWithExceptions(1,4);

        SchemeCard temp = new SchemeCard(1);
        if(Integer.parseInt(msgIN)==1)
        {
            temp=scheme1;
            temp.setfb(1);
        }
        if(Integer.parseInt(msgIN)==2)
        {
            temp=scheme1;
            temp.setfb(2);
        }
        if(Integer.parseInt(msgIN)==3)
        {
            temp=scheme2;
            temp.setfb(1);
        }
        if(Integer.parseInt(msgIN)==4)
        {
            temp=scheme2;
            temp.setfb(2);
        }
        printOut(cliToolsManager.sceneEnder(40));

        return temp;
    }

    /**
     * Sets the second waiting scene
     */
    public void setWaitScene2()
    {
        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.waitingForLobbyScene());
        printOut(cliToolsManager.sceneEnder(width));
    }

    //TURN SCENES

    /**
     * asks the player what action does he want to take
     * @param players
     * @param draft
     * @param track
     * @param tools
     * @param activePlayer
     * @param me
     * @param round
     * @return
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public int askForWhat(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round) throws InvalidIntArgumentException, IOException {
        //asks user what to do
        //0 = pass, 1 = move, 2 = tool
        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(width));

        printOut(printerMaker.round(round, players[me].getName()));
        printOut(printerMaker.selectAction());

        readWithExceptions(0,2);
        return Integer.parseInt(msgIN);
    }

    /**
     * override
     * @param players
     * @param draft
     * @param track
     * @param tools
     * @param activePlayer
     * @param me
     * @param round
     * @param disconnected
     * @return
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public int askForWhat(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected) throws InvalidIntArgumentException, IOException
    {
        //asks user what to do
        //0 = pass, 1 = move, 2 = tool
        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(width));

        printOut(printerMaker.round(round, players[me].getName()));

        //disconnected Players
        String[] tempNames = new String[disconnected.size()];
        for(int i=0;i<disconnected.size();i++)
            tempNames[i] = players[disconnected.get(i)].getName();
        printOut(printerMaker.disconnectedPlayers(tempNames));

        printOut(printerMaker.selectAction());

        readWithExceptions(0,2);
        return Integer.parseInt(msgIN);
    }

    /**
     * asks for moving positions and executes them
     * @param draftDim
     * @return
     * @throws IOException
     */
    public int[] move(int draftDim) throws IOException
    {
        int[] temp = new int[3];
        printOut(printerMaker.canMove());
        printOut(printerMaker.insertDraftIndex());
        readWithExceptions(1,draftDim);
        temp[0] = Integer.parseInt(msgIN)-1;
        printOut(printerMaker.insertX());
        readWithExceptions(1,4);
        temp[1] = Integer.parseInt(msgIN)-1;
        printOut(printerMaker.insertY());
        readWithExceptions(1,5);
        temp[2] = Integer.parseInt(msgIN)-1;

        return temp;
    }

    /**
     * prints out a move accepted message
     */
    public void moveAccepted()
    {
        printOut(printerMaker.moveAccepted());
    }

    /**
     * prints out a move refused message
     */
    public void moveRefused()
    {
        printOut(printerMaker.moveRefused());
    }

    /**
     * shows the other players turns and actions
     * @param players
     * @param draft
     * @param track
     * @param tools
     * @param activePlayer
     * @param me
     * @param round
     * @param disconnected
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected) throws InvalidIntArgumentException, IOException
    {
        //asks user what to do
        //0 = pass, 1 = move, 2 = tool

        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(width));

        printOut(printerMaker.round(round, players[me].getName()));

        //disconnected Players
        String[] tempNames = new String[disconnected.size()];
        for(int i=0;i<disconnected.size();i++)
            tempNames[i] = players[disconnected.get(i)].getName();
        printOut(printerMaker.disconnectedPlayers(tempNames));

        printOut(cliToolsManager.sceneEnder(width));
    }

    /**
     * override
     * @param players
     * @param draft
     * @param track
     * @param tools
     * @param activePlayer
     * @param me
     * @param round
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round) throws InvalidIntArgumentException, IOException
    {
        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(width));

        printOut(printerMaker.round(round, players[activePlayer].getName()));

        printOut(cliToolsManager.sceneEnder(width));
    }

    /**
     * shows the move
     * @param players
     * @param draft
     * @param track
     * @param tools
     * @param activePlayer
     * @param me
     * @param event
     * @throws InvalidIntArgumentException
     */
    public void showMove(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, MoveEvent event) throws InvalidIntArgumentException
    {
        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));
        printOut(printerMaker.notMyTurnMove(players[activePlayer].getName(), event.getIndex(), event.getX(), event.getY()));
    }

    /**
     * shows the tool
     * @param players
     * @param draft
     * @param track
     * @param tools
     * @param activePlayer
     * @param me
     * @param event
     * @throws InvalidIntArgumentException
     */
    public void showTool(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, ToolCardEvent event) throws InvalidIntArgumentException {
        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

    }

    /**
     * uses a tool, asking to the player what actions does he want to take
     * @param draftDim
     * @return
     * @throws IOException
     */


    public ToolCardEvent useTool(int draftDim, int roundTrackDim, int round) throws IOException {

        printOut(cliToolsManager.simpleQuestionsMaker("Che tool vuoi usare?",40,true));
        readWithExceptionsToolsEdition();

        if((Integer.parseInt(msgIN)==5 || Integer.parseInt(msgIN)==12) && round==0) {
            printOut(cliToolsManager.simpleQuestionsMaker("Non puoi usare questo tool al primo round essendo la round track vuota",40,false));
            readWithExceptionsToolsEdition();
        }

        switch (Integer.parseInt(msgIN)) {

            case 1: {

                ToolCardOneEvent event = new ToolCardOneEvent(1);
                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da spostare",40,true));
                readWithExceptions(1,draftDim);
                event.setIndex(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Premi + per aumentare il valore del dado, premi - per diminuire il valore del dado",40,true));
                readIt();
                event.setAction(msgIN.charAt(0));
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readWithExceptions(1,4);
                event.setX(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readWithExceptions(1,5);
                event.setY(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 2: {

                ToolCardTwoThreeEvent event = new ToolCardTwoThreeEvent(2);
                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del dado da spostare",40,true));
                readWithExceptions(1,4);
                event.setX0(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del dado da spostare",40,true));
                readWithExceptions(1,5);
                event.setY0(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readWithExceptions(1,4);
                event.setX1(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readWithExceptions(1,5);
                event.setY1(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 3: {

                ToolCardTwoThreeEvent event = new ToolCardTwoThreeEvent(3);
                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del dado da spostare",40,true));
                readWithExceptions(1,4);
                event.setX0(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del dado da spostare",40,true));
                readWithExceptions(1,5);
                event.setY0(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readWithExceptions(1,4);
                event.setX1(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readWithExceptions(1,5);
                event.setY1(Integer.parseInt(msgIN)-1);
                return event;
            }

            case 4: {

                ToolCardFourEvent event = new ToolCardFourEvent(4);

                printOut(cliToolsManager.simpleQuestionsMaker("Qual'è il primo dado che vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del primo dado da spostare",40,true));
                readWithExceptions(1,4);
                event.setX01(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del primo dado da spostare",40,true));
                readWithExceptions(1,5);
                event.setY01(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il primo dado",40,true));
                readWithExceptions(1,4);
                event.setX11(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il primo dado",40,true));
                readWithExceptions(1,5);
                event.setY11(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Qual'è il secondo dado che vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del secondo dado da spostare",40,true));
                readWithExceptions(1,4);
                event.setX02(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del secondo dado da spostare",40,true));
                readWithExceptions(1,5);
                event.setY02(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il secondo dado",40,true));
                readWithExceptions(1,4);
                event.setX22(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il secondo dado",40,true));
                readWithExceptions(1,5);
                event.setY22(Integer.parseInt(msgIN)-1);
                return event;


            }

            case 5: {

                ToolCardFiveEvent event = new ToolCardFiveEvent(5);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi sostituire?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da sostituire",40,true));
                readWithExceptions(1,draftDim);
                event.setIndex(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Con quale dado lo vuoi sostituire?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli il turno nel tracciato dei round dove è presente il dado che ti serve",40,true));
                readWithExceptions(1,roundTrackDim);
                event.setTurn(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione del dado che ti serve",40,true));
                readWithExceptions(1,9);
                event.setPos(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove piazzare il dado", 40, true));
                readWithExceptions(1,4 );
                event.setX(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove piazzare il dado", 40, true));
                readWithExceptions(1,5 );
                event.setY(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 6: {

                ToolCardSixEvent event = new ToolCardSixEvent(6);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi prendere?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da prendere",40,true));
                readWithExceptions(1,draftDim);
                event.setIndex((Integer.parseInt(msgIN))-1);
                return event;

            }

            case 7: {

                ToolCardSevenEvent event = new ToolCardSevenEvent(7);
                return event;

            }

            case 8: {

                ToolCardEightNineTenEvent event = new ToolCardEightNineTenEvent(8);
                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi prendere?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado che ti serve",40,true));
                readWithExceptions(1,draftDim);
                event.setIndex(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove piazzare il dado", 40, true));
                readWithExceptions(1,4 );
                event.setX(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove piazzare il dado", 40, true));
                readWithExceptions(1,5 );
                event.setY(Integer.parseInt(msgIN)-1);
                return event;
            }

            case 9: {

                ToolCardEightNineTenEvent event = new ToolCardEightNineTenEvent(9);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi prendere?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da prendere",40,true));
                readWithExceptions(1,draftDim);
                event.setIndex(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readWithExceptions(1,4);
                event.setX(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readWithExceptions(1,5);
                event.setY(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 10: {

                ToolCardEightNineTenEvent event = new ToolCardEightNineTenEvent(10);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi prendere?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da prendere",40,true));
                readWithExceptions(1,draftDim);
                event.setIndex(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readWithExceptions(1,4);
                event.setX(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readWithExceptions(1,5);
                event.setY(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 11: {

                ToolCardElevenEvent event = new ToolCardElevenEvent(11);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi prendere?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da prendere",40,true));
                readWithExceptions(1,draftDim);
                event.setIndex(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 12: {

                ToolCardTwelveEvent event = new ToolCardTwelveEvent(12);

                printOut(cliToolsManager.simpleQuestionsMaker("Scegli il turno nel tracciato dei round dell dado di cui vuoi prendere il colore",40,true));
                readWithExceptions(1,roundTrackDim);
                event.setTurn(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione del dado di cui vuoi prendere il colore",40,true));
                readWithExceptions(1,9);
                event.setPos(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Qual'è il primo dado che vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del primo dado da spostare",40,true));
                readWithExceptions(1,4);
                event.setX01(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del primo dado da spostare",40,true));
                readWithExceptions(1,5);
                event.setY01(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il primo dado",40,true));
                readWithExceptions(1,4);
                event.setX11(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il primo dado",40,true));
                readWithExceptions(1,5);
                event.setY11(Integer.parseInt(msgIN)-1);

                printOut(cliToolsManager.simpleQuestionsMaker("Vuoi spostare un secondo dado?  1 = SI, 2 = NO", 40, true));
                readWithExceptions(1, 2);
                if(Integer.parseInt(msgIN)==2)
                    event.setOnlyOne(true);
                else {
                    printOut(cliToolsManager.simpleQuestionsMaker("Qual'è il secondo dado che vuoi spostare?", 40, true));
                    printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del secondo dado da spostare", 40, true));
                    readWithExceptions(1, 4);
                    event.setX02(Integer.parseInt(msgIN) - 1);
                    printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del secondo dado da spostare", 40, true));
                    readWithExceptions(1, 5);
                    event.setY02(Integer.parseInt(msgIN) - 1);
                    printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?", 40, true));
                    printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il secondo dado", 40, true));
                    readWithExceptions(1, 4);
                    event.setX22(Integer.parseInt(msgIN) - 1);
                    printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il secondo dado", 40, true));
                    readWithExceptions(1, 5);
                    event.setY22(Integer.parseInt(msgIN) - 1);
                }
                return event;

            }
        }
        return null;
    }

    /**
     * manages and uses the tool card six event part two, asking the player where does he want to place the die
     * @param players
     * @param draft
     * @param track
     * @param tools
     * @param activePlayer
     * @param me
     * @param round
     * @param previousEvent
     * @return
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public ToolCardSixEvent toolCardSixEventPartTwo(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ToolCardSixEvent previousEvent) throws InvalidIntArgumentException, IOException {

        ToolCardSixEvent event = previousEvent;
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));
        printOut(cliToolsManager.printSpaces(40));
        printOut(cliToolsManager.simpleQuestionsMaker("Il dado in posizione "+Integer.toString(previousEvent.getIndex()+1)+" è stato tirato, nuovo valore " + event.getNewValue(),40,false));
        printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
        readWithExceptions(1,4);
        event.setX(Integer.parseInt(msgIN)-1);
        printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
        readWithExceptions(1,5);
        event.setY(Integer.parseInt(msgIN)-1);
        return event;
    }

    /**
     * manages and uses the tool card eleven event part two, asking the player where does he want to place the die
     * @param players
     * @param draft
     * @param track
     * @param tools
     * @param activePlayer
     * @param me
     * @param round
     * @param previousEvent
     * @return
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public ToolCardElevenEvent toolCardElevenEventPartTwo(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ToolCardElevenEvent previousEvent) throws InvalidIntArgumentException, IOException {

        ToolCardElevenEvent event = previousEvent;
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));
        printOut(cliToolsManager.printSpaces(40));
        printOut(cliToolsManager.simpleQuestionsMaker("Dado ripescato, nuovo colore: " + cliToolsManager.getColor(event.getNewColor()),40 ,false ));
        printOut(cliToolsManager.simpleQuestionsMaker("Scegli il nuovo valore del dado", 40, true));
        readWithExceptions(1,6);
        event.setNewValue(Integer.parseInt(msgIN));
        printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
        readWithExceptions(1,4);
        event.setX(Integer.parseInt(msgIN)-1);
        printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
        readWithExceptions(1,5);
        event.setY(Integer.parseInt(msgIN)-1);
        return event;
    }

    /**
     * prints out a tool accepted message
     */
    public void toolAccepted()
    {
        printOut(cliToolsManager.simpleQuestionsMaker("Strumento accettato", 40, true));
    }

    /**
     * shows the final scores and prints out a winner message if the player won
     * @param event
     * @param winner
     * @return
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     * @throws IOException
     */
    public boolean showScores(ScoreEvent event, boolean winner) throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException, IOException {
        printOut(printerMaker.showScores(event));
        if(winner) {
            printOut(cliToolsManager.centerThatString("HAI VINTO!!!", 80));
            printOut(cliToolsManager.centerThatString("Antoni Gaudi sarebbe fiero di te", 80));
        }
        printOut("PREMI QUALSIASI TASTO PER USCIRE");
        readIt();
        return true;
    }

    /**
     * our print out method
     * @param printerMakerResult
     */
    private void printOut(String[] printerMakerResult)
    {
        //shows user the printerMaker result

        for(int i=0;i<printerMakerResult.length;i++)
        {
            outVideo.println(printerMakerResult[i]);
            outVideo.flush();
        }
    }

    /**
     * our print out method
     * @param s
     */
    private void printOut(String s)
    {
        outVideo.println(s);
        outVideo.flush();
    }

    /**
     * our read from keyboard method
     * @throws IOException
     */
    private void readIt() throws IOException
    {
        //resets the buffer and reads from it
        outVideo.println("==>");
        outVideo.flush();
        msgIN = inKeyboard.readLine();
        //System.out.println(msgIN);
    }

    /**
     * our read from keyboard method with exceptions for the tools
     * @throws IOException
     */

    public void readWithExceptionsToolsEdition() throws IOException {

        readWithExceptions(1, 12);
        boolean flag = false;
        for(int i=0;i<3;i++)
            if(Integer.parseInt(msgIN)==toolsID[i])
                flag=true;

        if(!flag) {
            printOut(cliToolsManager.simpleQuestionsMaker("Che tool vuoi usare?",40,true));
            readWithExceptionsToolsEdition();
        }

    }

    /**
     * our read from keyboard message with NumberFormat Exceptions and recursive call if integer is out of bounds
     * @param leftBound
     * @param rightBound
     * @throws IOException
     */

    public void readWithExceptions(int leftBound, int rightBound) throws IOException {

        try {

            readIt();

            if (Integer.parseInt(msgIN) < leftBound || Integer.parseInt(msgIN) > rightBound) {
                printOut(printerMaker.wrongInsertion());
                readWithExceptions(leftBound, rightBound);
            }
        }

        catch (NumberFormatException e) {
            printOut(printerMaker.wrongInsertion());
            readWithExceptions(leftBound, rightBound);
        }

    }

}
