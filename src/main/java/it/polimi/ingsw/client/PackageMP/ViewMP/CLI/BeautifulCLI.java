package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.*;
import it.polimi.ingsw.client.PackageMP.PlayerClient;
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

    public String askUsername() throws IOException {
        //asks for username

        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getUsernameInsertion());
        readIt();
        printOut(cliToolsManager.sceneEnder(40));
        return msgIN;

    }

    public String askUsernameAgain(String temp) throws IOException {

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

    public SchemeCardMP setInitializationScene(SchemeCardMP scheme1, SchemeCardMP scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs, int[] tools) throws InvalidIntArgumentException, IOException {

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
            System.out.println("msgIN: "+msgIN);
            System.out.println(Integer.parseInt(msgIN));
            if(Integer.parseInt(msgIN)>0&&Integer.parseInt(msgIN)<3)
            {
                flag = true;
            }
            else
            {
                printOut(printerMaker.wrongInsertion());
                printOut(printerMaker.selectAction());
            }
        }
        return Integer.parseInt(msgIN);
    }
    public int askForWhat(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected) throws InvalidIntArgumentException, IOException {
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

    public int[] move() throws IOException {

        int[] temp = new int[3];
        printOut(printerMaker.canMove());
        printOut(printerMaker.insertDraftIndex());
        readIt();
        temp[0] = Integer.parseInt(msgIN);
        printOut(printerMaker.insertX());
        readIt();
        temp[1] = Integer.parseInt(msgIN);
        printOut(printerMaker.insertY());
        readIt();
        temp[2] = Integer.parseInt(msgIN);

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

    public void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round, ArrayList<Integer> disconnected) throws InvalidIntArgumentException, IOException {
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
    public void showTurn(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int round) throws InvalidIntArgumentException, IOException {
        //asks user what to do
        //0 = pass, 1 = move, 2 = tool
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));

        printOut(cliToolsManager.printSpaces(40));

        printOut(printerMaker.round(round, players[activePlayer].getName()));

        printOut(cliToolsManager.sceneEnder(40));
    }


    public void showMove(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP track, int[] tools, int activePlayer, int me, int color, int value, int x, int y) throws InvalidIntArgumentException {
        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getGameScene(players, draft, track, privateObjective, pubObjs, tools, activePlayer, me));
        printOut(printerMaker.notMyTurnMove(players[activePlayer].getName(), color, value, x, y));
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

    private void readIt() throws IOException {
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
