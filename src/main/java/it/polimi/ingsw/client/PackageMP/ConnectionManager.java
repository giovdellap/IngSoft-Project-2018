package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.ClientExceptions.FullDataStructureException;
import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.Connection.RMIClient;
import it.polimi.ingsw.client.PackageMP.Connection.SocketClient;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.RoundTrackMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;

import java.io.IOException;
import java.util.ArrayList;

public class ConnectionManager
{
    public MinorLogger cmLogger;

    private String ip;
    private int connection;
    private SocketClient socketClient;
    private RMIClient rmiClient;
    private String username;


    public ConnectionManager(String ip, int connection) throws IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {
        cmLogger = new MinorLogger();
        cmLogger.minorLog("socket logger started");

        this.ip=ip;
        this.connection=connection;
        enstablishConnection();
    }

    public void enstablishConnection() throws IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {
        if(connection==1) {
            socketClient = new SocketClient(ip);
            socketLoggerUpdate();
        }
    }

    public boolean confirmUsername(String username) throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {
        if(connection==1) {
            boolean temp = socketClient.usernameConfirm(username);
            if(temp)
                this.username=username;
            return temp;
        }
        else
            return false;
    }

    public int getPrivObj() throws GenericInvalidArgumentException, IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {
        if(connection==1)
        {
            int temp = socketClient.getPrivObj();
            socketLoggerUpdate();
            return temp;
        }
        else
            return 0;
    }

    public int[] getTempSchemes() throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException, it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException {
        if(connection==1)
        {
            int[] temp = socketClient.getSchemes();
            socketLoggerUpdate();
            return temp;
        }

        else
            return null;
    }

    public int[] getPubObjs() throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        if(connection==1)
        {
            int[] temp = socketClient.getPubObjs();
            socketLoggerUpdate();
            return temp;
        }
        else
            return null;
    }

    public int[] getToolsID() throws IOException {
        if(connection==1)
        {
            return socketClient.getTools();
        }
        else
            return null;
    }

    public void getSelectionCheck() throws IOException {
        if(connection==1)
            socketClient.getSelectionCheck();

    }

    public boolean getSchemeConfirm(SchemeCardMP temp) throws IOException {
        if(connection==1)
            return socketClient.getSchemeConfirmation(temp.getID(), temp.getfb());
        else
            return false;
    }

    public PlayerClient[] getPlayers() throws IOException, InvalidIntArgumentException {
        if(connection==1)
            return socketClient.getPlayersStart(username);
        else
            return null;
    }


    //TURN MANAGEMENT

    public int getRound() throws IOException {
        if(connection==1)
            return socketClient.newTurn();
        else
            return 0;
    }
    public ArrayList<Integer> getDisconnectedPlayers() throws IOException {
        if(connection==1)
            return socketClient.getDiscPlayers();
        else
            return null;
    }
    public int getActivePlayer() throws IOException {
        if(connection==1)
            return socketClient.getActive();
        else
            return 0;
    }
    public boolean askForActive() throws IOException {
        if(connection==1)
            return socketClient.askForActive();
        else
            return false;
    }

    //MY TURN
    public boolean toDo(int arg) throws IOException {
        if(connection==1)
            return socketClient.toDo(arg);
        else
            return false;
    }
    public boolean move(int[] arg) throws IOException {
        if(connection==1)
            return socketClient.move(arg);
        else
            return false;
    }

    //NOT MY TURN
    public int[] notifyAction() throws IOException {
        if(connection==1)
            return socketClient.getAction();
        else
            return null;
    }


    //GET MODEL COMPONENTS METHODS

    public DraftPoolMP getDraft() throws IOException, InvalidIntArgumentException {
        if(connection==1)
            return socketClient.getDraft();
        else
            return null;
    }
    public RoundTrackMP getTrack() throws FullDataStructureException, InvalidIntArgumentException, IOException {
        if(connection==1)
            return socketClient.getTrack();
        else
            return null;
    }
    public int[] getToolsUpdate() throws IOException {
        if(connection==1) {
            return socketClient.toolCardUpdate();
        }
        else
            return null;
    }
    public PlayerClient getSchemeModifies(PlayerClient previousPlayer) throws GenericInvalidArgumentException, InvalidIntArgumentException, IOException {
        if(connection==1)
            return socketClient.getSchemeCard(previousPlayer);
        else
            return null;
    }
    public int[] getNotMyTurnMove()
    {
        if(connection==1)
            return socketClient.getNotMyTurnMove();
        else
            return null;
    }

    //LOGGERS METHODS
    private void socketLoggerUpdate() throws GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {

        cmLogger.stackLog(socketClient.socketLogger.updateFather());
        socketClient.socketLogger.reinitialize();
    }


}
