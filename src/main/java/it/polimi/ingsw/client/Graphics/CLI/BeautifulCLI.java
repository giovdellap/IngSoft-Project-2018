package it.polimi.ingsw.client.Graphics.CLI;

import it.polimi.ingsw.client.Graphics.AbstractGraphic;
import it.polimi.ingsw.client.Graphics.StringCreator;
import it.polimi.ingsw.client.GraphicsManager;
import it.polimi.ingsw.client.SchemeCreator.SchemeReader;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.*;
import it.polimi.ingsw.client.PlayerClient;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.io.*;
import java.util.ArrayList;

public class BeautifulCLI extends AbstractGraphic implements Runnable
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

    private StringCreator stringCreator;
    private boolean initializationMode=true;

    private SchemeReader reader;


    /**
     * BeautifulCLI Constructor
     */
    public BeautifulCLI()
    {
        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        printerMaker = new PrinterMaker(1);
        cliToolsManager = new CLIToolsManager();
        stringCreator=new StringCreator();
        reader=new SchemeReader();

        msgIN = "";

        width=80;
    }

    public void run() {
        stopCLI=false;
        System.out.println("BEAUTIFULCLI DISCONNECTED: "+Integer.toString(threadUpdater.disconnected.size()));
        if (state==GraphicsManager.State.ASKMAIN)
        {
            askForWhat=4;
            move=null;
            useTool=null;
            if (secondaryState==GraphicsManager.SecondaryState.MOVEACCEPTED)
                moveAccepted();
            if (secondaryState==GraphicsManager.SecondaryState.MOVEREFUSED)
                moveRefused();
            if (secondaryState==GraphicsManager.SecondaryState.TOOLACCEPTED)
                toolAccepted();
            if (secondaryState==GraphicsManager.SecondaryState.TOOLREFUSED)
                System.out.println("MISSING");

            try {
                printOut(cliToolsManager.sceneInitializer(width));
                printOut(printerMaker.getGameScene(threadUpdater.players, threadUpdater.draft, threadUpdater.track, privateObjective, pubObjs, threadUpdater.tools, threadUpdater.activePlayer, threadUpdater.me));
                printOut(cliToolsManager.printSpaces(width));
                printOut(printerMaker.round(threadUpdater.round, threadUpdater.players[threadUpdater.activePlayer].getName()));

                if (threadUpdater.disconnected!=null)
                {
                    //disconnected Players
                    printOut(printerMaker.disconnectedPlayers(threadUpdater.disconnected));
                }

                printOut(printerMaker.selectAction());
                readWithExceptions(0,2 );

            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            askForWhat=Integer.parseInt(msgIN);
        }
        if(state==GraphicsManager.State.CLIMOVE)
        {
            move = new int[3];
            try {
                System.out.println();
                printOut(stringCreator.getString(StringCreator.State.DRAFTPOS));
                readWithExceptions(1, threadUpdater.draft.getSize());
                move[0]=Integer.parseInt(msgIN)-1;
            } catch (IOException e) {
                e.printStackTrace();
            }
            if(!stopCLI)
            {
                try{
                    printOut(stringCreator.getString(StringCreator.State.FINALPOSX));
                    readWithExceptions(1, 4);
                    move[1]=Integer.parseInt(msgIN)-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if(!stopCLI)
            {
                try{
                    printOut(stringCreator.getString(StringCreator.State.FINALPOSY));
                    readWithExceptions(1, 5);
                    move[2]=Integer.parseInt(msgIN)-1;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }
        if(state==GraphicsManager.State.ASKTOOL6)
        {
            try {
                ToolCardSixEvent event = (ToolCardSixEvent) useTool;
                printOut(cliToolsManager.sceneInitializer(40));
                printOut(printerMaker.getGameScene(threadUpdater.players, threadUpdater.draft, threadUpdater.track, privateObjective, pubObjs, threadUpdater.tools, threadUpdater.activePlayer, threadUpdater.me));
                printOut(cliToolsManager.printSpaces(40));
                printOut(cliToolsManager.simpleQuestionsMaker("Il dado in posizione " + Integer.toString(event.getIndex() + 1) + " è stato tirato, nuovo valore " + event.getNewValue(), 40, false));
                printOut(stringCreator.getString( StringCreator.State.FINALPOSX));
                if(!stopCLI) {
                    readWithExceptions(1, 4);
                    event.setX(Integer.parseInt(msgIN) - 1);
                }
                printOut(stringCreator.getString(StringCreator.State.FINALPOSY));
                if(!stopCLI) {
                    readWithExceptions(1, 5);
                    event.setY(Integer.parseInt(msgIN) - 1);
                }
                if(!stopCLI)
                    useTool=event;
            } catch (InvalidIntArgumentException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(state==GraphicsManager.State.ASKTOOL11)
        {
            if(!stopCLI)
            {
                try{
                    ToolCardElevenEvent event = (ToolCardElevenEvent)useTool;
                    printOut(cliToolsManager.sceneInitializer(40));
                    printOut(printerMaker.getGameScene(threadUpdater.players, threadUpdater.draft, threadUpdater.track, privateObjective, pubObjs, threadUpdater.tools, threadUpdater.activePlayer, threadUpdater.me));
                    printOut(cliToolsManager.printSpaces(40));
                    printOut(cliToolsManager.simpleQuestionsMaker("Dado ripescato, nuovo colore: " + cliToolsManager.getColor(event.getNewColor()),40 ,false ));
                    printOut(cliToolsManager.simpleQuestionsMaker("Scegli il nuovo valore del dado", 40, true));

                    if(!stopCLI) {
                        readWithExceptions(1, 6);
                        event.setNewValue(Integer.parseInt(msgIN));
                    }
                    if(!stopCLI) {
                        printOut(stringCreator.getString(StringCreator.State.FINALPOSX));
                        readWithExceptions(1, 4);
                        event.setX(Integer.parseInt(msgIN) - 1);
                    }
                    if(!stopCLI) {
                        printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado", 40, true));
                        readWithExceptions(1, 5);
                        event.setY(Integer.parseInt(msgIN) - 1);
                    }
                    if(!stopCLI)
                        useTool = event;

                } catch (InvalidIntArgumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(state==GraphicsManager.State.CLITOOL)
        {
            try {
                printOut(stringCreator.getString(StringCreator.State.ASKTOOLCARD));
                readWithExceptionsToolsEdition();

                while((Integer.parseInt(msgIN)==5 || Integer.parseInt(msgIN)==12) && threadUpdater.round==0) {
                    printOut(cliToolsManager.simpleQuestionsMaker("Non puoi usare questo tool al primo round essendo la round track vuota",40,false));
                    readWithExceptionsToolsEdition();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            switch (Integer.parseInt(msgIN))
            {
                case 1:
                {
                    if(!stopCLI)
                    {
                        try {
                            ToolCardOneEvent event = new ToolCardOneEvent(1);
                            printOut(stringCreator.getString(StringCreator.State.DRAFTPOS));
                            readWithExceptions(1, threadUpdater.draft.getSize());
                            event.setIndex(Integer.parseInt(msgIN)-1);

                            if(!stopCLI)
                            {
                                printOut(stringCreator.getString(StringCreator.State.TOOL1));
                                readIt();
                                event.setAction(msgIN.charAt(0));
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSX));
                                readWithExceptions(1, 4);
                                event.setX(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI)
                            {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSY));
                                readWithExceptions(1, 5);
                                event.setY(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI)
                                useTool=event;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 2:
                {
                    if(!stopCLI)
                    {
                        try {
                            ToolCardTwoThreeEvent event = new ToolCardTwoThreeEvent(2);
                            printOut(stringCreator.getString(StringCreator.State.STARTPOSX));
                            readWithExceptions(1, 4);
                            event.setX0(Integer.parseInt(msgIN)-1);

                            if(!stopCLI)
                            {
                                printOut(stringCreator.getString(StringCreator.State.STARTPOSY));
                                readWithExceptions(1, 5);
                                event.setY0(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI)
                            {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSX));
                                readWithExceptions(1, 4);
                                event.setX1(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI)
                            {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSY));
                                readWithExceptions(1, 5);
                                event.setY1(Integer.parseInt(msgIN)-1);
                            }
                            useTool=event;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 3:
                {
                    if(!stopCLI)
                    {
                        try {
                            ToolCardTwoThreeEvent event = new ToolCardTwoThreeEvent(3);
                            printOut(stringCreator.getString(StringCreator.State.STARTPOSX));
                            readWithExceptions(1, 4);
                            event.setX0(Integer.parseInt(msgIN)-1);

                            if(!stopCLI)
                            {
                                printOut(stringCreator.getString(StringCreator.State.STARTPOSY));
                                readWithExceptions(1, 5);
                                event.setY0(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI)
                            {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSX));
                                readWithExceptions(1, 4);
                                event.setX1(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI)
                            {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSY));
                                readWithExceptions(1, 5);
                                event.setY1(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI)
                                useTool=event;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                }
                case 4: {
                    if(!stopCLI) {
                        try {

                            ToolCardFourEvent event = new ToolCardFourEvent(4);
                            printOut(stringCreator.getString(StringCreator.State.STARTPOS1X));
                            readWithExceptions(1, 4);
                            event.setX01(Integer.parseInt(msgIN)-1);

                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.STARTPOS1Y));
                                readWithExceptions(1, 5);
                                event.setY01(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.STARTPOS2X));
                                readWithExceptions(1, 4);
                                event.setX02(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.STARTPOS2Y));
                                readWithExceptions(1, 5);
                                event.setY02(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOS1X));
                                readWithExceptions(1, 4);
                                event.setX11(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOS1Y));
                                readWithExceptions(1, 5);
                                event.setY11(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOS2X));
                                readWithExceptions(1, 4);
                                event.setX22(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOS2Y));
                                readWithExceptions(1, 5);
                                event.setY22(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI)
                                useTool = event;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                    break;
                }
                case 5: {
                    if(!stopCLI) {
                        try {
                            ToolCardFiveEvent event = new ToolCardFiveEvent(5);
                            printOut(stringCreator.getString(StringCreator.State.DRAFTPOS));
                            readWithExceptions(1,threadUpdater.draft.getSize());
                            event.setIndex(Integer.parseInt(msgIN)-1);
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.ROUND));
                                readWithExceptions(1, threadUpdater.round);
                                event.setTurn(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.ROUNDICE));
                                readWithExceptions(1, threadUpdater.track.returnNTurnRoundDice(event.getTurn()).returnDim());
                                event.setPos(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSX));
                                readWithExceptions(1,4 );
                                event.setX(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSY));
                                readWithExceptions(1, 5);
                                event.setY(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI)
                                useTool = event;
                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InvalidIntArgumentException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 6: {
                    if(!stopCLI) {
                        try {
                            ToolCardSixEvent event = new ToolCardSixEvent(6);
                            printOut(stringCreator.getString(StringCreator.State.DRAFTPOS));
                            readWithExceptions(1, threadUpdater.draft.getSize());
                            event.setIndex(Integer.parseInt(msgIN)-1);
                            useTool=event;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 7: {
                    if(!stopCLI) {
                        ToolCardSevenEvent event = new ToolCardSevenEvent(7);
                        useTool = event;
                    }
                    break;
                }
                case 8: {
                    if(!stopCLI) {
                        try {
                            ToolCardEightNineTenEvent event = new ToolCardEightNineTenEvent(8);
                            printOut(stringCreator.getString(StringCreator.State.DRAFTPOS));
                            readWithExceptions(1,threadUpdater.draft.getSize());
                            event.setIndex(Integer.parseInt(msgIN)-1);

                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSX));
                                readWithExceptions(1,4);
                                event.setX(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSY));
                                readWithExceptions(1,5);
                                event.setY(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI)
                                useTool = event;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 9: {
                    if(!stopCLI) {
                        try {
                            ToolCardEightNineTenEvent event = new ToolCardEightNineTenEvent(9);
                            printOut(stringCreator.getString(StringCreator.State.DRAFTPOS));
                            readWithExceptions(1,threadUpdater.draft.getSize());
                            event.setIndex(Integer.parseInt(msgIN)-1);
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSX));
                                readWithExceptions(1,4);
                                event.setX(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSY));
                                readWithExceptions(1,5);
                                event.setY(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI)
                                useTool = event;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 10: {
                    if(!stopCLI) {
                        try {
                            ToolCardEightNineTenEvent event = new ToolCardEightNineTenEvent(10);
                            printOut(stringCreator.getString(StringCreator.State.DRAFTPOS));
                            readWithExceptions(1,threadUpdater.draft.getSize());
                            event.setIndex(Integer.parseInt(msgIN)-1);
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSX));
                                readWithExceptions(1,4);
                                event.setX(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOSY));
                                readWithExceptions(1,5);
                                event.setY(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI)
                                useTool = event;

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 11: {
                    if(!stopCLI) {
                        try {
                            ToolCardElevenEvent event = new ToolCardElevenEvent(11);
                            printOut(stringCreator.getString(StringCreator.State.DRAFTPOS));
                            readWithExceptions(1, threadUpdater.draft.getSize());
                            event.setIndex(Integer.parseInt(msgIN)-1);
                            if(!stopCLI)
                                useTool = event;
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
                case 12: {
                    if(!stopCLI) {
                        try {
                            ToolCardTwelveEvent event = new ToolCardTwelveEvent(12);
                            printOut(stringCreator.getString(StringCreator.State.ROUNDTOOL12));
                            readWithExceptions(1,threadUpdater.track.returnActualTurn());
                            event.setTurn(Integer.parseInt(msgIN)-1);
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.ROUNDDICETOOL12));
                                readWithExceptions(1,threadUpdater.track.returnNTurnRoundDice(event.getTurn()).returnDim());
                                event.setPos(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.STARTPOS1X));
                                readWithExceptions(1,4);
                                event.setX01(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.STARTPOS1Y));
                                readWithExceptions(1,5);
                                event.setY01(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOS1X));
                                readWithExceptions(1,4);
                                event.setX11(Integer.parseInt(msgIN)-1);
                            }
                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.FINALPOS1Y));
                                readWithExceptions(1,5);
                                event.setY11(Integer.parseInt(msgIN)-1);
                            }

                            if(!stopCLI) {
                                printOut(stringCreator.getString(StringCreator.State.ASKSECONDDIE));
                                readWithExceptions(1, 2);
                            }

                            if (Integer.parseInt(msgIN) == 2)
                                event.setOnlyOne(true);

                            else {
                                if (!stopCLI) {
                                    printOut(stringCreator.getString(StringCreator.State.STARTPOS2X));
                                    readWithExceptions(1, 4);
                                    event.setX02(Integer.parseInt(msgIN) - 1);
                                }
                                if (!stopCLI) {
                                    printOut(stringCreator.getString(StringCreator.State.STARTPOS2Y));
                                    readWithExceptions(1, 5);
                                    event.setY02(Integer.parseInt(msgIN) - 1);
                                }
                                if (!stopCLI) {
                                    printOut(stringCreator.getString(StringCreator.State.FINALPOS2X));
                                    readWithExceptions(1, 4);
                                    event.setX22(Integer.parseInt(msgIN) - 1);
                                }
                                if (!stopCLI) {
                                    printOut(stringCreator.getString(StringCreator.State.FINALPOS2Y));
                                    readWithExceptions(1, 5);
                                    event.setY22(Integer.parseInt(msgIN) - 1);
                                }
                            }
                            if(!stopCLI)
                                useTool = event;

                        } catch (IOException e) {
                            e.printStackTrace();
                        } catch (InvalidIntArgumentException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
                }
            }
        }
    }



    /**
     * asks for Username
     * @return
     * @throws IOException
     */
    public String askUsername() throws IOException, InterruptedException {

        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getUsernameInsertion());
        boolean acceptable=false;
        while (!acceptable) {
            readIt();
            if(msgIN.length()<20)
                acceptable=true;
        }
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
     * @param privateObjectiveMP
     * @param publicObjectiveMPS
     * @param tools
     * @return
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public SchemeCard setInitializationScene(SchemeCard scheme1, SchemeCard scheme2, String username, PrivateObjectiveMP privateObjectiveMP, PublicObjectiveMP[] publicObjectiveMPS, int[] tools) throws InvalidIntArgumentException, IOException, InterruptedException {
        this.privateObjective = privateObjectiveMP;
        this.pubObjs = publicObjectiveMPS;
        this.toolsID = tools;
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getSelectionScene(scheme1, scheme2, username, privateObjectiveMP, pubObjs, tools));
        printOut(cliToolsManager.simpleQuestionsMaker("Premere 1/2/3/4 per scegliere uno schema fra quelli estratti, 0 per uno schema personalizzato", 80, false));

        readWithExceptions(0,4);


        SchemeCard temp=null;
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
        if(Integer.parseInt(msgIN)==0)
        {
            printOut(cliToolsManager.simpleQuestionsMaker("Scegli uno schema fra quelli che hai creato", 80, false));
            readIt();
            temp=reader.read(msgIN);
            for (int x=0;x<4;x++)
                for (int y=0;y<5;y++)
                    System.out.println("x: "+Integer.toString(x)+" y: "+Integer.toString(y)+" "+Integer.toString(temp.getCell(1, x, y)));

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
    public void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<String> disconnected) throws InvalidIntArgumentException, IOException
    {
        //asks user what to do
        //0 = pass, 1 = move, 2 = tool

        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(width));

        printOut(printerMaker.round(round, players[activePlayer].getName()));

        //disconnected Players
        printOut(printerMaker.disconnectedPlayers(disconnected));

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
    public void showMove(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, MoveEvent event) throws InvalidIntArgumentException, FileNotFoundException {
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
    public void showTool(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, ToolCardEvent event) throws InvalidIntArgumentException, FileNotFoundException {
        printOut(cliToolsManager.sceneInitializer(width));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        switch (event.getId()) {

            case 1: {
                ToolCardOneEvent currentEvent = (ToolCardOneEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha preso il dado in posizione " + (currentEvent.getIndex()+1) + " della draft\n",40,false));

                if(currentEvent.getAction() == '+')
                    printOut(cliToolsManager.simpleQuestionsMaker("L'ha aumentato di valore e piazzato in posizione " + (currentEvent.getX()+1) + "," + (currentEvent.getY()+1) + " dello schema\n\n",40,false));
                else
                    printOut(cliToolsManager.simpleQuestionsMaker("L'ha diminuito di valore e piazzato in posizione " + (currentEvent.getX()+1) + "," + (currentEvent.getY()+1) + " dello schema\n\n",40,false));

                break;
            }

            case 2: {
                ToolCardTwoThreeEvent currentEvent = (ToolCardTwoThreeEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha preso il dado in posizione " + (currentEvent.getX0()+1) + " , " + (currentEvent.getY0()+1) + " dello schema\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("e l'ha spostato in posizione " + (currentEvent.getX1()+1) + "," + (currentEvent.getY1()+1) + "\n\n",40,true));
                break;
            }

            case 3: {
                ToolCardTwoThreeEvent currentEvent = (ToolCardTwoThreeEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha preso il dado in posizione " + (currentEvent.getX0()+1) + "," + (currentEvent.getY0()+1) + " dello schema\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("e l'ha spostato in posizione " + (currentEvent.getX1()+1) + "," + (currentEvent.getY1()+1) + "\n\n",40,true));
                break;
            }

            case 4: {
                ToolCardFourEvent currentEvent = (ToolCardFourEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha preso i dadi in posizione " + (currentEvent.getX01()+1) + "," + (currentEvent.getY01()+1) + " e " + (currentEvent.getX02()+1) + "," + (currentEvent.getY02()+1) + "\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("e li ha spostati in posizione " + (currentEvent.getX11()+1) + "," + (currentEvent.getY11()+1) + " e " + (currentEvent.getX22()+1) + "," + (currentEvent.getY22()+1) + "\n\n",40,true));
                break;
            }

            case 5: {
                ToolCardFiveEvent currentEvent = (ToolCardFiveEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha preso il dado in posizione " + (currentEvent.getIndex()+1) + " della draft\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("e l'ha scambiato con il dado nel turno " + (currentEvent.getTurn()+1) + " della roundtrack, in posizione " + (currentEvent.getPos()+1),40,false));
                break;
            }

            case 6: {
                ToolCardSixEvent currentEvent = (ToolCardSixEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha scelto il dado in posizione " + (currentEvent.getIndex()+1) + " della draft e l'ha ritirato, nuovo valore " + currentEvent.getNewValue() + "\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("e l'ha piazzato in posizione " + (currentEvent.getX()+1) + "," + (currentEvent.getY()+1) + " dello schema",40,true));
                break;
            }

            case 7: {
                ToolCardSevenEvent currentEvent = (ToolCardSevenEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha ritirato tutti i dadi della draft\n\n",40,false));
                break;

            }

            case 8: {
                ToolCardEightNineTenEvent currentEvent = (ToolCardEightNineTenEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha preso un altro dado dalla draft, in posizione " + (currentEvent.getIndex()+1) + "\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("e l'ha piazzato in posizione " + (currentEvent.getX()+1) + " , " + (currentEvent.getY()+1) + " dello schema",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " salterà il suo secondo turno in questo round\n\n",40,false));
                break;
            }

            case 9: {
                ToolCardEightNineTenEvent currentEvent = (ToolCardEightNineTenEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha preso il dado in posizione " + (currentEvent.getIndex()+1) + " della draft\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("e l'ha piazzato in posizione " + (currentEvent.getX()+1) + " , " + (currentEvent.getY()+1) + " dello schema",40,false));
                break;
            }

            case 10: {
                ToolCardEightNineTenEvent currentEvent = (ToolCardEightNineTenEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha preso il dado in posizione " + (currentEvent.getIndex()+1) + " della draft , l'ha girato sulla faccia opposta\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("e l'ha piazzato in posizione " + (currentEvent.getX()+1) + " , " + (currentEvent.getY()+1) + " dello schema\n\n",40,false));
                break;
            }

            case 11: {
                ToolCardElevenEvent currentEvent = (ToolCardElevenEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha preso il dado in posizione " + (currentEvent.getIndex()+1) + " della draft e l'ha rimesso nel sacchetto\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("ha pescato un nuovo dado di colore " + cliToolsManager.getColor(currentEvent.getNewColor()) + " , ha scelto il valore " + currentEvent.getNewValue() + "\n",40,false));
                printOut(cliToolsManager.simpleQuestionsMaker("e l'ha piazzato in posizione " + (currentEvent.getX()+1) + " , " + (currentEvent.getY()+1) + " dello schema" + "\n\n",40,false));
                break;
            }

            case 12: {
                ToolCardTwelveEvent currentEvent = (ToolCardTwelveEvent)event;
                printOut(cliToolsManager.simpleQuestionsMaker("Il giocatore " + players[activePlayer].getName() + " ha usato la carta strumento " + currentEvent.getId(),40,false));
                printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha scelto il colore del dado nel turno " + (currentEvent.getTurn()+1) + " roundtrack, in posizione " + (currentEvent.getPos()+1) + "\n",40,false));

                if(currentEvent.isOnlyOne()) {
                    printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha deciso di spostare un solo dado\n",40,false));
                    printOut(cliToolsManager.simpleQuestionsMaker("ha preso il dado in posizione " + (currentEvent.getX01()+1) + "," + (currentEvent.getY01()+1) + " dello schema\n",40,false));
                    printOut(cliToolsManager.simpleQuestionsMaker("e l'ha spostato in posizione " + (currentEvent.getX11()+1) + "," + (currentEvent.getY11()+1) + "\n\n",40,true));
                }

                else {
                    printOut(cliToolsManager.simpleQuestionsMaker(players[activePlayer].getName() + " ha deciso di spostare un due dadi\n",40,false));
                    printOut(cliToolsManager.simpleQuestionsMaker("ha preso i dadi in posizione " + (currentEvent.getX01()+1) + "," + (currentEvent.getY01()+1) + " e " + (currentEvent.getX02()+1) + "," + (currentEvent.getY02()+1) + "\n", 40, false));
                    printOut(cliToolsManager.simpleQuestionsMaker("e li ha spostati in posizione " + (currentEvent.getX11()+1) + "," + (currentEvent.getY11()+1) + " e " + (currentEvent.getX22()+1) + "," + (currentEvent.getY22()+1) + "\n\n", 40, true));
                }
                break;
            }



        }


    }


    public void setUseTool(ToolCardEvent event)
    {
        useTool = event;
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
     * @throws InvalidIntArgumentException
     * @throws IOException
     */
    public boolean showScores(ScoreEvent event, boolean winner) throws InvalidIntArgumentException, IOException, InterruptedException {
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
    private void readIt() throws IOException, InterruptedException
    {
        //resets the buffer and reads from it
        outVideo.println("==>");
        outVideo.flush();
        if(!initializationMode)
            while (!inKeyboard.ready())
            {
                Thread.sleep(50);
            }
        msgIN = inKeyboard.readLine();
    }


    /**
     * our read from keyboard method with exceptions for the tools
     * @throws IOException
     */

    public void readWithExceptionsToolsEdition() throws IOException
    {
        readWithExceptions(1, 12);
        boolean flag = false;
        for(int i=0;i<3;i++)
            if(Integer.parseInt(msgIN)==toolsID[i])
                flag=true;

        if(!flag)
        {
            printOut(cliToolsManager.simpleQuestionsMaker("Che tool vuoi usare?",40,true));
            readWithExceptionsToolsEdition();
        }

    }

    /**
     * our read from keyboard method with the NumberFormatException and a recursive call if the integer is out of bounds
     * @param leftBound
     * @param rightBound
     * @throws IOException
     */

    public void readWithExceptions(int leftBound, int rightBound) throws IOException
    {
        try {
            readIt();
            if (Integer.parseInt(msgIN) < leftBound || Integer.parseInt(msgIN) > rightBound)
            {
                printOut(printerMaker.wrongInsertion());
                readWithExceptions(leftBound, rightBound);
            }
        }
        catch (NumberFormatException e) {
            printOut(printerMaker.wrongInsertion());
            readWithExceptions(leftBound, rightBound);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
    public void printOut(ArrayList<String> temp)
    {
        for(String str : temp)
            printOut(str);
    }

    public void forfaitCli() throws IOException, InterruptedException {
        printOut(cliToolsManager.sceneInitializer(80));

        for(int i=0;i<15;i++)
            printOut("");

        printOut(cliToolsManager.centerThatString("TUTTI GLI ALTRI GIOCATORI SI SONO DISCONNESSI", 80));
        printOut(cliToolsManager.centerThatString("HAI VINTO", 80));

        printOut("");
        printOut("");

        printOut(cliToolsManager.simpleQuestionsMaker("Premi qualsiasi tasto per uscire", 80, true));
        readIt();
    }

    public void setGraphics(PrivateObjectiveMP privateObjective, PublicObjectiveMP[] pubObjs, int[] toolsID)
    {
        this.privateObjective=privateObjective;
        this.pubObjs=pubObjs;
        this.toolsID=toolsID;
        printerMaker.setTools(toolsID);
        width=80;
    }


}
