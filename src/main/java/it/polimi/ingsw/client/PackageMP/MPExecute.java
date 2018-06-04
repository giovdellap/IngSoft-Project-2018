package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MajorLogger;
import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DieMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PublicObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import javafx.application.Application;
import javafx.stage.Stage;

import java.io.IOException;

public class MPExecute extends Application {

    MajorLogger mpLogger;

    //connections
    private String ip;

    //settings
    private int mode; // 1 = SP, 2 = MP, 3 = DEMO
    private int connection; // 1 = SOCKET, 2 = RMIClient
    private int graphics; // 1 = GUI, 2 = CLI

    //managers
    private GraphicsManager graphicsManager;
    private ConnectionManager connectionManager;
    private ModelManagerMP modelManagerMP;
    private ToolRecord toolRecord;
    private MatchManager matchManager;

    //general
    private boolean firstFlag;
    private String myUsername;
    private boolean endMatch=false;

    @Override
    public void start(Stage stage) throws Exception {
        //starting managers
        graphicsManager = new GraphicsManager(graphics);
        gmLoggerUpdate();
        connectionManager = new ConnectionManager(ip, connection);
        cmLoggerUpdate();
        mpLogger.majorLog("managers started");

        //asking user for username and sending it to server while isn't correct
        firstFlag=false;
        String tempUsername=graphicsManager.askUsername(stage);
        gmLoggerUpdate();

        while(!firstFlag)
        {
            firstFlag=connectionManager.confirmUsername(tempUsername);
            cmLoggerUpdate();
            if(!firstFlag) {
                tempUsername = graphicsManager.askUsernameAgain(stage, tempUsername);
                gmLoggerUpdate();
            }
        }

        myUsername=tempUsername;
        graphicsManager.waitForPlayers();
        gmLoggerUpdate();
        modelManagerMP = new ModelManagerMP();
        mpLogger.majorLog("ModelManager started");

        //setting private objective
        modelManagerMP.setMyPrivObj(connectionManager.getPrivObj());
        cmLoggerUpdate();

        //setting schemes
        int[] tempSchemes = connectionManager.getTempSchemes();
        cmLoggerUpdate();
        modelManagerMP.setTempSchemes(tempSchemes[0], tempSchemes[1]);

        //setting public objectives
        modelManagerMP.setPubObjs(connectionManager.getPubObjs());
        cmLoggerUpdate();

        //setting tools
        toolRecord = new ToolRecord(connectionManager.getToolsID());

        //SCHEME SELECTION

        firstFlag=false;
        while(!firstFlag) {
            //getting connection check
            connectionManager.getSelectionCheck();

            //getting selected scheme
            PublicObjectiveMP[] tempPubObjs = new PublicObjectiveMP[3];
            for (int i = 0; i < 3; i++)
                tempPubObjs[i] = modelManagerMP.getPubObjs(i);
            SchemeCardMP toCheckScheme = graphicsManager.getSelectedScheme(modelManagerMP.getTempScheme(0), modelManagerMP.getTempScheme(1), myUsername, modelManagerMP.getMyPrObj(), tempPubObjs, toolRecord.getID());

            //sending scheme and get confirmation
            firstFlag = connectionManager.getSchemeConfirm(toCheckScheme);
        }
        graphicsManager.waitForPlayers2();

        matchManager = new MatchManager(connectionManager.getPlayers());
        while(!endMatch)
            turn();
    }

    public void turn() throws IOException, InvalidIntArgumentException, FullDataStructureException, GenericInvalidArgumentException {

        //turn initialization
        matchManager.setRound(connectionManager.getRound());
        matchManager.setDisconnectedPlayers(connectionManager.getDisconnectedPlayers());
        matchManager.setActivePlayer(connectionManager.getActivePlayer());
        modelManagerMP.setDraft(connectionManager.getDraft());
        modelManagerMP.setTrack(connectionManager.getTrack());
        toolRecord.setTokens(connectionManager.getToolsUpdate());

        boolean active = connectionManager.askForActive();
        if(active==true)
            itsMyTurn();
        else
            notMyTurn();

    }

    public void itsMyTurn() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException {
        boolean end = false;
        int advertise=0;
        int accepted=0;

        while(!end) {
            int whatToDo;

            whatToDo=graphicsManager.askForWhat();
            boolean toDoResponse = connectionManager.toDo(whatToDo);
            if(toDoResponse)
            {
                if(whatToDo==0)
                    end=true;
                if(whatToDo==1)
                {
                    boolean check = move();
                    if(check)
                        accepted=1;
                    else
                        advertise=1;
                }
            }
        }
    }
    public void notMyTurn() throws IOException {
        //gets player action
        int[] whoAndWhat = connectionManager.notifyAction();

    }

    public boolean move() throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException {
        int[] move = graphicsManager.move();
        boolean flag = connectionManager.move(move);
        if(flag)
        {
            //apply modifies
            DraftPoolMP tempDraft = modelManagerMP.getDraft();
            DieMP tempDie = tempDraft.returnDie(move[0]);
            tempDraft.pickUpDie(move[0]);
            modelManagerMP.setDraft(tempDraft);
            SchemeCardMP tempScheme = matchManager.getMyScheme();
            tempScheme.setDie(tempDie, move[1], move[2]);
            matchManager.setMyScheme(tempScheme);
        }
        return flag;
    }

    public MPExecute(String ip, int[] toSet) throws GenericInvalidArgumentException {
        //constructor
        //receives ip and settings from launcher
        mode=toSet[0];
        connection=toSet[1];
        graphics=toSet[2];
        this.ip=ip;

        mpLogger = new MajorLogger();
        mpLogger.majorLog("MPExecute Logger started");
    }

    private void updateGraphicsManager()
    {
        int me=0;
        for(int i=0;i<matchManager.getGraphicsUpdate().length;i++)
            if(matchManager.getGraphicsUpdate()[i].itsMe())
                me=i;
        graphicsManager.gmUpdate(matchManager.getGraphicsUpdate(), modelManagerMP.getDraft(), modelManagerMP.getTrack(), toolRecord.getTokens(), matchManager.getActivePlayer(), me, matchManager.getRound(), matchManager.getDisconnectedPlayers());
    }

    //LOGGERS UPDATE
    private void cmLoggerUpdate() throws GenericInvalidArgumentException {
        mpLogger.stackLog(connectionManager.cmLogger.updateFather());
        connectionManager.cmLogger.reinitialize();
    }
    private void gmLoggerUpdate() throws GenericInvalidArgumentException {
        mpLogger.stackLog(graphicsManager.gmLogger.updateFather());
        graphicsManager.gmLogger.reinitialize();
    }
    private void mmLoggerUpdate() throws GenericInvalidArgumentException {
        mpLogger.stackLog(modelManagerMP.mmLogger.updateFather());
        modelManagerMP.mmLogger.reinitialize();
    }
}
