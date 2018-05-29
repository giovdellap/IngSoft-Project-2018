package it.polimi.ingsw.client.DemoMode.MoveDemo;

import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DieMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemesDeckMP;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.*;
import java.net.Socket;

public class MoveDemoExecute extends Application {

    MoveDemoController contr;

    //connections
    private String ip;
    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private String msgIN;
    private String msgOUT;
    private String tempCmd;
    private String tempArg;

    //model components
    private SchemeCardMP schemeCard;
    private SchemesDeckMP schemesDeckMP;
    private DraftPool draftPool;

    public MoveDemoExecute(String ip)
    {
        this.ip=ip;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {

        socket = new Socket(ip, 7777);
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        demoPreparation();
    }

    public void demoPreparation() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException {
        msgIN=inSocket.readLine();
        simpleDecode(msgIN);
        if(!tempCmd.equals("insert")&&!tempArg.equals("username"))
            demoPreparation();

        outSocket.println(simpleEncode("demo", "1"));
        outSocket.flush();

        //receiving schemecard
        msgIN=inSocket.readLine();
        simpleDecode(msgIN);
        if(tempCmd.equals("model")&&tempArg.equals("scheme"))
        {
            schemesDeckMP = new SchemesDeckMP();
            msgIN=inSocket.readLine();
            simpleDecode(msgIN);
            schemeCard = schemesDeckMP.extractSchemebyID(Integer.parseInt(tempArg));

            msgIN = inSocket.readLine();
            simpleDecode(msgIN);
            schemeCard.setfb(Integer.parseInt(tempArg));

            msgIN = inSocket.readLine();
            simpleDecode(msgIN);
            while(!tempCmd.equals("end"))
            {
                DieMP tempDie = new DieMP(Integer.parseInt(tempArg));

                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
                tempDie.setValueTest(Integer.parseInt(tempArg));

                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
                int x = Integer.parseInt(tempArg);
                msgIN=inSocket.readLine();
                simpleDecode(msgIN);
                int y = Integer.parseInt(tempArg);

                schemeCard.setDie(tempDie, x, y);

                msgIN = inSocket.readLine();
                simpleDecode(msgIN);
            }

            if(tempCmd.equals("end")&&tempArg.equals("scheme"))
            {
                outSocket.println(simpleEncode("scheme", "received"));
            }
        }
        else
            demoPreparation();
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
