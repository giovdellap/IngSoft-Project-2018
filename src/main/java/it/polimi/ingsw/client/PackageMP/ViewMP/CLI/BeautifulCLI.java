package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;

import java.io.*;

public class BeautifulCLI
{
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    private PrinterMaker printerMaker;
    private CLIToolsManager cliToolsManager;

    private String msgIN;

    public BeautifulCLI()
    {
        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        printerMaker = new PrinterMaker();
        cliToolsManager = new CLIToolsManager();
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

    public SchemeCardMP setInitializationScene(SchemeCardMP scheme1, SchemeCardMP scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs) throws InvalidIntArgumentException, IOException {

        printOut(cliToolsManager.sceneInitializer(40));
        printOut(printerMaker.getSelectionScene(scheme1, scheme2, username, privObj, pubObjs));
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
        outVideo.print("==>");
        outVideo.flush();
        msgIN = inKeyboard.readLine();
    }


}
