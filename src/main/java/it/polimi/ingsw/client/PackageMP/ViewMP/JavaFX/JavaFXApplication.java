package it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.Connection.SocketClient;
import it.polimi.ingsw.client.PackageMP.ModelMP;
import it.polimi.ingsw.client.PackageMP.Player;
import it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX.Initialization.ModelInitialization.InitializationSceneController;
import it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX.Initialization.UsernameInsertion.UsernameSceneController;
import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class JavaFXApplication extends Application
{
    //GRAPHICS
    private FXMLLoader loader;

    private ControllerMP cont3;
    private InitializationSceneController cont2;
    private UsernameSceneController cont1;

    private Parent root;
    private Scene currentScene;

    //MATCH HANDLING
    private int round;
    private int turn;
    private int playersNum;
    private int myId;
    private String myName;
    private Player[] players;

    private boolean socketFlag;
    private boolean guiFlag;

    //CONNECTIONS
    private SocketClient cSocket;
    private String serverIP;

    public MinorLogger loggerMP;

    //MODEL
    private ModelMP modelSession;


    @Override
    public void start(final Stage stage) throws Exception
    {
        //INITIALIZATION 1: PHASE 1

        System.out.println("Qui ci arrivi?");

        loggerMP = new MinorLogger();
        loggerMP.minorLog("MultiPlayer logger operative");

        modelSession = new ModelMP();
        loggerMP.minorLog("modelSession started and initialized");

        //starting socket
        cSocket = new SocketClient(serverIP);
        socketLoggerUpdate();

        //waiting username insertion command from server
        socketFlag = false;
        while(!socketFlag)
            socketFlag=cSocket.getInsertUsername();
        socketLoggerUpdate();

        //Starting username scene
        loader = new FXMLLoader(getClass().getClassLoader().getResource("Initialization/UsernameInsertion/usernameScene.fxml"));
        root = loader.load();
        cont1 = loader.getController();
        currentScene = new Scene(root);
        stage.setTitle("SAGRADA");
        stage.setScene(currentScene);
        stage.show();
        loggerMP.minorLog("Graphics is operative");

        //username insertion and validation
        socketFlag = false;

        EventHandler<ActionEvent> nameHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("si handla");
                try {
                    if(checkUsername())
                    {
                        loadWaitScene(stage);
                        setupModel();
                        setUpInitializationScene(stage);
                        loadInitSceneHandler(stage);
                    }
                } catch (GenericInvalidArgumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (InvalidIntArgumentException e) {
                    e.printStackTrace();
                }
            }
        };

        root.addEventHandler(ActionEvent.ACTION, nameHandler);

    }

    //CONSTRUCTOR
    public JavaFXApplication(String ip)
    {
        serverIP = ip;
    }

    //INITIALIZATION 1: PHASE 1


    public String getUsername()
    {
        String temp;
        temp = cont1.getTempUsername();
        while(temp.equals(""))
            temp = cont1.getTempUsername();
        return temp;
    }

    private boolean checkUsername() throws GenericInvalidArgumentException, IOException {
        String temp;
        Boolean tempFlag=false;
        temp = cont1.getTempUsername();
        cSocket.sendUsername(temp);
        socketLoggerUpdate();
        tempFlag=cSocket.usernameConfirm();
        if(tempFlag) {
            myName = temp;
            return tempFlag;
        }
        else
            return tempFlag;
    }

    private void loadWaitScene(Stage stage) throws IOException {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("WaitScenes/waitScene1.fxml"));
        root = loader.load();
        currentScene = new Scene(root);
        stage.setScene(currentScene);
        stage.show();
    }

    //INITIALIZATION 2

    //setting up model components
    private void setupModel() throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {
         modelSession = new ModelMP();

         //getting priv objective
         modelSession.setMyPrivObj(cSocket.getPrivObj());
         socketLoggerUpdate();

         //getting schemes
        int[] temp = cSocket.getSchemes();
        socketLoggerUpdate();
        modelSession.setTempSchemes(temp[0], temp[1]);

        //getting pub objs
        modelSession.setPubObjs(cSocket.getPublicObjs());
        socketLoggerUpdate();
    }

    //initializing initialization scene by model components from server
    private void setUpInitializationScene(Stage thisStage) throws IOException {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("Initialization/ModelInitialization/initializationScene.fxml"));
        cont2 = loader.getController();

        //setting images on controller
        cont2.setPrObj(modelSession.getMyPrObj());
        cont2.setSchemes(modelSession.getTempScheme(0), modelSession.getTempScheme(0));
        cont2.setPublicObjectives(modelSession.getPubObjs(0), modelSession.getPubObjs(1), modelSession.getPubObjs(2));

        root = loader.load();
        currentScene = new Scene(root);

        thisStage.setScene(currentScene);
        thisStage.show();
    }

    //loading Handler for initializationScene
    private void loadInitSceneHandler(final Stage thisStage)
    {
        EventHandler<ActionEvent> initHandler = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                try {
                    if(cSocket.selectScheme(cont2.getSelectedScheme(), cont2.getFbSelected()))
                        socketLoggerUpdate();
                        modelSession.setMyScheme(cont2.getSelectedScheme(), cont2.getFbSelected());
                        setWaitScene2(thisStage);
                } catch (InvalidIntArgumentException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (GenericInvalidArgumentException e) {
                    e.printStackTrace();
                }
            }
        };
        root.addEventHandler(ActionEvent.ACTION, initHandler);
    }

    private void setWaitScene2(Stage thisStage) throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {
        loader = new FXMLLoader(getClass().getClassLoader().getResource("WaitScenes/waitScene2.fxml"));
        root = loader.load();
        currentScene = new Scene(root);
        thisStage.setScene(currentScene);
        thisStage.show();

        init2Phase2(thisStage);
    }

    //INITIALIZATION2: PHASE 2
    private void init2Phase2(Stage stage) throws IOException, GenericInvalidArgumentException, InvalidIntArgumentException {
        playersNum = cSocket.getNumPlayers();
        socketLoggerUpdate();

        players = new Player[playersNum];
        for(int i=0; i<playersNum;i++)
        {
            players[i] = cSocket.getPlayer();
        }
        for(int i=0;i<playersNum;i++)
        {
            if(myName.equals(players[1].getName()))
                myId = players[i].getId();
        }

        loader = new FXMLLoader(getClass().getClassLoader().getResource("WaitScenes/waitScene2.fxml"));
        root = loader.load();
        cont3 = loader.getController();
        cont3.setUpPlayers(players, myId);
    }




    //LOGGER UPDATE

    private void socketLoggerUpdate() throws GenericInvalidArgumentException {
        loggerMP.stackLog(cSocket.socketLogger.updateFather());
        cSocket.socketLogger.reinitialize();
    }

}
