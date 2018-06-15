package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.PlayerClient;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.server.TurnManager;

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

    public BeautifulCLI()
    {
        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        printerMaker = new PrinterMaker();
        cliToolsManager = new CLIToolsManager();
        msgIN = "";
    }

    public String askUsername() throws IOException
    {
        //asks for username

        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getUsernameInsertion());
        readIt();
        printOut(cliToolsManager.sceneEnder(40));

        return msgIN;

    }

    public String askUsernameAgain(String temp) throws IOException
    {

        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getUsernameInsertionAgain(temp));
        readIt();
        printOut(cliToolsManager.sceneEnder(40));

        return msgIN;
    }

    public void setWaitScene()
    {
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.waitingForPlayersScene());
        printOut(cliToolsManager.sceneEnder(40));
    }

    public SchemeCardMP setInitializationScene(SchemeCardMP scheme1, SchemeCardMP scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs, int[] tools) throws InvalidIntArgumentException, IOException
    {
        privateObjective = privObj;
        this.pubObjs = pubObjs;
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getSelectionScene(scheme1, scheme2, username, privObj, pubObjs, tools));
        readIt();
        while (Integer.parseInt(msgIN)<1||Integer.parseInt(msgIN)>4)
        {
            printOut(cliToolsManager.simpleQuestionsMaker("SCHEMA ERRATO! RIPROVA", 40, true));
            readIt();
        }
        SchemeCardMP temp = new SchemeCardMP(1);
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

    public void setWaitScene2()
    {
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.waitingForLobbyScene());
        printOut(cliToolsManager.sceneEnder(40));
    }

    //TURN SCENES

    public int askForWhat(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round) throws InvalidIntArgumentException, IOException {
        //asks user what to do
        //0 = pass, 1 = move, 2 = tool
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(40));

        printOut(printerMaker.round(round, players[me].getName()));
        printOut(printerMaker.selectAction());

        boolean flag = false;
        while(!flag)
        {
            readIt();
            if(Integer.parseInt(msgIN)>=0&&Integer.parseInt(msgIN)<3)
                flag = true;
            else
            {
                printOut(printerMaker.wrongInsertion());
                printOut(printerMaker.selectAction());
            }
        }
        return Integer.parseInt(msgIN);
    }
    public int askForWhat(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected) throws InvalidIntArgumentException, IOException
    {
        //asks user what to do
        //0 = pass, 1 = move, 2 = tool
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(40));

        printOut(printerMaker.round(round, players[me].getName()));

        //disconnected Players
        String[] tempNames = new String[disconnected.size()];
        for(int i=0;i<players.length;i++)
            tempNames[i] = players[disconnected.get(i)].getName();
        printOut(printerMaker.disconnectedPlayers(tempNames));

        printOut(printerMaker.selectAction());

        boolean flag = false;
        while(!flag)
        {
            readIt();
            if(Integer.parseInt(msgIN)>0&&Integer.parseInt(msgIN)<3)
                flag = true;
            else
            {
                printOut(printerMaker.wrongInsertion());
                printOut(printerMaker.selectAction());
            }
        }
        return Integer.parseInt(msgIN);
    }

    public int[] move() throws IOException
    {
        int[] temp = new int[3];
        printOut(printerMaker.canMove());
        printOut(printerMaker.insertDraftIndex());
        readIt();
        temp[0] = Integer.parseInt(msgIN)-1;
        printOut(printerMaker.insertX());
        readIt();
        temp[1] = Integer.parseInt(msgIN)-1;
        printOut(printerMaker.insertY());
        readIt();
        temp[2] = Integer.parseInt(msgIN)-1;

        return temp;
    }

    public void cantMove()
    {
        printOut(printerMaker.cantMove());
    }
    public void moveAccepted()
    {
        printOut(printerMaker.moveAccepted());
    }
    public void moveRefused()
    {
        printOut(printerMaker.moveRefused());
    }


    //NOT MY TURN

    public void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected) throws InvalidIntArgumentException, IOException
    {
        //asks user what to do
        //0 = pass, 1 = move, 2 = tool

        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(40));

        printOut(printerMaker.round(round, players[me].getName()));

        //disconnected Players
        String[] tempNames = new String[disconnected.size()];
        for(int i=0;i<players.length;i++)
            tempNames[i] = players[disconnected.get(i)].getName();
        printOut(printerMaker.disconnectedPlayers(tempNames));

        printOut(cliToolsManager.sceneEnder(40));
    }

    public void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round) throws InvalidIntArgumentException, IOException
    {
        //asks user what to do
        //0 = pass, 1 = move, 2 = tool

        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(40));

        printOut(printerMaker.round(round, players[activePlayer].getName()));

        printOut(cliToolsManager.sceneEnder(40));
    }


    public void showMove(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, MoveEvent event) throws InvalidIntArgumentException
    {
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));
        printOut(printerMaker.notMyTurnMove(players[activePlayer].getName(), event.getIndex(), event.getX(), event.getY()));
    }

    public void showTool(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, ToolCardEvent event) throws InvalidIntArgumentException {
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

    }



    public ToolCardEvent useTool() throws IOException {

        boolean flag=false;

        while(!flag) {
            printOut(cliToolsManager.simpleQuestionsMaker("Che tool vuoi usare?",40,true));
            readIt();
            if(Integer.parseInt(msgIN)>0 || Integer.parseInt(msgIN)<12)
                flag = true;
        }

        switch (Integer.parseInt(msgIN)) {

            case 1: {

                ToolCardOneEvent event = new ToolCardOneEvent(1);
                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da spostare",40,true));
                readIt();
                event.setIndex(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Premi + per aumentare il valore del dado, premi - per diminuire il valore del dado",40,true));
                readIt();
                event.setAction(msgIN.charAt(0));
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readIt();
                event.setX(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readIt();
                event.setY(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 2: {

                ToolCardTwoThreeEvent event = new ToolCardTwoThreeEvent(2);
                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del dado da spostare",40,true));
                readIt();
                event.setX0(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del dado da spostare",40,true));
                readIt();
                event.setY0(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readIt();
                event.setX1(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readIt();
                event.setY1(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 3: {

                ToolCardTwoThreeEvent event = new ToolCardTwoThreeEvent(3);
                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del dado da spostare",40,true));
                readIt();
                event.setX0(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del dado da spostare",40,true));
                readIt();
                event.setY0(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readIt();
                event.setX1(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readIt();
                event.setY1(Integer.parseInt(msgIN)-1);
                return event;
            }

            case 4: {

                ToolCardFourEvent event = new ToolCardFourEvent(4);

                printOut(cliToolsManager.simpleQuestionsMaker("Qual'è il primo dado che vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del primo dado da spostare",40,true));
                readIt();
                event.setX01(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del primo dado da spostare",40,true));
                readIt();
                event.setY01(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il primo dado",40,true));
                readIt();
                event.setX11(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il primo dado",40,true));
                readIt();
                event.setY11(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Qual'è il secondo dado che vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del secondo dado da spostare",40,true));
                readIt();
                event.setX02(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del secondo dado da spostare",40,true));
                readIt();
                event.setY02(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il secondo dado",40,true));
                readIt();
                event.setX22(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il secondo dado",40,true));
                readIt();
                event.setY22(Integer.parseInt(msgIN)-1);
                return event;


            }

            case 5: {

                ToolCardFiveEvent event = new ToolCardFiveEvent(5);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi sostituire?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da sostituire",40,true));
                readIt();
                event.setIndex(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Con quale dado lo vuoi sostituire?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli il turno nel tracciato dei round dove è presente il dado che ti serve",40,true));
                readIt();
                event.setTurn(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione del dado che ti serve",40,true));
                readIt();
                event.setPos(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 6: {

                ToolCardSixEvent event = new ToolCardSixEvent(6);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi prendere?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da prendere",40,true));
                readIt();
                event.setIndex((Integer.parseInt(msgIN))-1);
                return event;

            }

            case 7: {

                ToolCardSevenEvent event = new ToolCardSevenEvent(7);


            }

            case 8: {


            }

            case 9: {

                ToolCardEightNineTenEvent event = new ToolCardEightNineTenEvent(9);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi prendere?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da prendere",40,true));
                readIt();
                event.setIndex(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readIt();
                event.setX(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readIt();
                event.setY(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 10: {

                ToolCardEightNineTenEvent event = new ToolCardEightNineTenEvent(10);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi prendere?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da prendere",40,true));
                readIt();
                event.setIndex(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                readIt();
                event.setX(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                readIt();
                event.setY(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 11: {

                ToolCardElevenEvent event = new ToolCardElevenEvent(11);

                printOut(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi prendere?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da prendere",40,true));
                readIt();
                event.setIndex(Integer.parseInt(msgIN)-1);
                return event;

            }

            case 12: {

                ToolCardTwelveEvent event = new ToolCardTwelveEvent(12);

                printOut(cliToolsManager.simpleQuestionsMaker("Scegli il turno nel tracciato dei round dove sta il dado di cui vuoi prendere il colore",40,true));
                readIt();
                event.setTurn(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la posizione dove sta il dado di cui vuoi prendere il colore",40,true));
                readIt();
                event.setPos(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Qual'è il primo dado che vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del primo dado da spostare",40,true));
                readIt();
                event.setX01(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del primo dado da spostare",40,true));
                readIt();
                event.setY01(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il primo dado",40,true));
                readIt();
                event.setX11(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il primo dado",40,true));
                readIt();
                event.setY11(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Qual'è il secondo dado che vuoi spostare?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga del secondo dado da spostare",40,true));
                readIt();
                event.setX02(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del secondo dado da spostare",40,true));
                readIt();
                event.setY02(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Dove vuoi spostare il dado?",40,true));
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il secondo dado",40,true));
                readIt();
                event.setX22(Integer.parseInt(msgIN)-1);
                printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il secondo dado",40,true));
                readIt();
                event.setY22(Integer.parseInt(msgIN)-1);
                return event;

            }
        }
        return null;
    }

    public ToolCardSixEvent toolCardSixEventPartTwo(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ToolCardSixEvent previousEvent) throws InvalidIntArgumentException, IOException {

        ToolCardSixEvent event = previousEvent;
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));
        printOut(cliToolsManager.printSpaces(40));
        printOut(cliToolsManager.simpleQuestionsMaker("Il dado è stato tirato, nuovo valore " + event.getNewValue(),40,true));
        printOut(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
        readIt();
        event.setX(Integer.parseInt(msgIN)-1);
        printOut(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizioanre il dado",40,true));
        readIt();
        event.setY(Integer.parseInt(msgIN)-1);
        event.resetValidation();
        return event;
    }

    public void toolAccepted()
    {
        printOut(cliToolsManager.simpleQuestionsMaker("Strumento accettato", 40, true));
    }

    //UTILS
    private void printOut(String[] printerMakerResult)
    {
        //shows user the printerMaker result

        for(int i=0;i<printerMakerResult.length;i++)
        {
            outVideo.println(printerMakerResult[i]);
            outVideo.flush();
        }
    }

    private void printOut(String s)
    {
        outVideo.println(s);
        outVideo.flush();
    }

    private void readIt() throws IOException
    {
        //resets the buffer and reads from it
        outVideo.println("==>");
        outVideo.flush();
        msgIN = inKeyboard.readLine();
        //System.out.println(msgIN);
    }

    public void setToolsID(int[] toolsID)
    {
        printerMaker.setToolsID(toolsID);
    }


}
