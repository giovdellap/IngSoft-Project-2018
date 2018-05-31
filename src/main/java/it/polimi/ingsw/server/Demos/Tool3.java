package it.polimi.ingsw.server.Demos;

import it.polimi.ingsw.server.Model;
import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ModelComponent.SchemesDeck;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCard;
import it.polimi.ingsw.server.ToolCards.ToolCardThree;

import java.io.*;
import java.net.Socket;

public class Tool3 {

    // connections
    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private String tempArg;
    private String tempCmd;
    private String msgIn;

    // model components
    ToolCardThree toolCardThree;
    SchemesDeck schemesDeck;
    SchemeCard schemeCard;
    Die[] testDice;


    public void Tool3(Socket s) throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException {

        socket = s;
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);
        modelComponentsInitialization();
        demoModePreparation();

    }

    private void modelComponentsInitialization() throws InvalidIntArgumentException, GenericInvalidArgumentException {

        testDice = new Die[8];
        schemesDeck = new SchemesDeck();
        schemeCard = schemesDeck.extractSchemebyID(6);
        schemeCard.setfb(2);
        toolCardThree = new ToolCardThree();

        testDice[0] = new Die(2);
        testDice[1] = new Die(4);
        testDice[2] = new Die(5);
        testDice[3] = new Die(3);
        testDice[4] = new Die(3);
        testDice[5] = new Die(1);
        testDice[6] = new Die(2);
        testDice[7] = new Die(1);

        testDice[0].throwDie();
        testDice[1].throwDie();
        testDice[2].throwDie();
        testDice[3].throwDie();
        testDice[4].throwDie();
        testDice[5].throwDie();
        testDice[6].throwDie();
        testDice[7].throwDie();

        schemeCard.setDie(testDice[0],0,0);
        schemeCard.setDie(testDice[1],1,0);
        schemeCard.setDie(testDice[2],0,3);
        schemeCard.setDie(testDice[3],0,4);
        schemeCard.setDie(testDice[4],2,1);
        schemeCard.setDie(testDice[5],3,1);
        schemeCard.setDie(testDice[6],3,4);
        schemeCard.setDie(testDice[7],3,3);


    }

    private void demoModePreparation() throws InvalidIntArgumentException, IOException {

        outSocket.println(simpleEncode("model", "scheme"));
        outSocket.flush();
        outSocket.println(simpleEncode("id", Integer.toString(schemeCard.getID())));
        outSocket.flush();
        outSocket.println(simpleEncode("id", Integer.toString(schemeCard.getfb())));
        outSocket.flush();

        int dice = 0;

        for (int x = 0; x < 4; x++)
            for (int y = 0; y < 5; y++) {

                if (!schemeCard.getDie(x, y).isDisabled()) {

                    outSocket.println(simpleEncode("color",Integer.toString(schemeCard.getDie(x,y).getColor())));
                    outSocket.flush();
                    outSocket.println(simpleEncode("value",Integer.toString(schemeCard.getDie(x,y).getValue())));
                    outSocket.flush();
                    outSocket.println(simpleEncode("x",Integer.toString(x)));
                    outSocket.flush();
                    outSocket.println(simpleEncode("y",Integer.toString(y)));
                    outSocket.flush();

                }
            }

        outSocket.println(simpleEncode("end","scheme"));
        outSocket.flush();

        boolean isReceived = false;
        msgIn = inSocket.readLine();
        simpleDecode(msgIn);

        if(tempCmd.equals("scheme") && tempArg.equals("received"))
            isReceived = true;

        else
            demoModePreparation();

    }

    private void checkAndUpdate() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException {

        int posx0 = 0;
        int posy0 = 0;
        int posx1 = 0;
        int posy1 = 0;

        msgIn = inSocket.readLine();
        simpleDecode(msgIn);

        if (tempCmd.equals("x0"))
            posx0 = Integer.parseInt(tempArg);

        msgIn = inSocket.readLine();
        simpleDecode(msgIn);

        if (tempCmd.equals("y0"))
            posy0 = Integer.parseInt(tempArg);

        msgIn = inSocket.readLine();
        simpleDecode(msgIn);

        if (tempCmd.equals("x1"))
            posx1 = Integer.parseInt(tempArg);

        msgIn = inSocket.readLine();
        simpleDecode(msgIn);

        if (tempCmd.equals("y1"))
            posy1 = Integer.parseInt(tempArg);

        boolean flag = toolCardThree.checkToolCardThree(posx0,posy0,schemeCard,posx1,posy1);

        if(flag) {
            schemeCard = toolCardThree.applyModifies(posx0,posy0,schemeCard,posx1,posy1);
            outSocket.println(simpleEncode("check","1"));
        }

        if(!flag) {
            outSocket.println(simpleEncode("check","2"));
            checkAndUpdate();
        }


    }


    // simple encode/decode


    public String simpleEncode(String cmd, String arg)
    {
        String temp = "#"+cmd+"#$"+arg+"$";
        return temp;
    }

    private void simpleDecode(String tempStr)
    {
        //parts the message into command and argument
        tempCmd="";
        tempArg="";
        int index=0;
        if(tempStr.charAt(index)=='#')
        {
            index++;
            while (tempStr.charAt(index) != '#')
            {
                tempCmd+=Character.toString(tempStr.charAt(index));
                index++;
            }
            index++;
            if(tempStr.charAt(index)=='$');
            {
                index++;
                while(tempStr.charAt(index)!='$')
                {
                    tempArg+=Character.toString(tempStr.charAt(index));
                    index++;
                }
            }
        }
    }



}
