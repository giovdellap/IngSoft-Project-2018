package it.polimi.ingsw.client.PackageMP;

import it.polimi.ingsw.client.Loggers.MinorLogger;
import it.polimi.ingsw.client.PackageMP.Connection.RMIClient;
import it.polimi.ingsw.client.PackageMP.Connection.SocketClient;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.IOException;

public class ConnectionManager
{
    public MinorLogger cmLogger;

    private String ip;
    private int connection;
    private SocketClient socketClient;
    private RMIClient rmiClient;


    public ConnectionManager(String ip, int connection) throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        cmLogger = new MinorLogger();
        cmLogger.minorLog("socket logger started");

        this.ip=ip;
        this.connection=connection;
        enstablishConnection();
    }

    public void enstablishConnection() throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        if(connection==1) {
            socketClient = new SocketClient(ip);
            socketLoggerUpdate();
        }
    }

    public boolean confirmUsername(String username) throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        if(connection==1) {
            boolean temp = socketClient.usernameConfirm(username);
            socketLoggerUpdate();
            return temp;
        }
        else
            return false;
    }

    public int getPrivObj() throws GenericInvalidArgumentException, IOException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
        if(connection==1)
        {
            int temp = socketClient.getPrivObj();
            socketLoggerUpdate();
            return temp;
        }
        else
            return 0;
    }

    public int[] getTempSchemes() throws IOException, GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {
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



    //LOGGERS METHODS
    private void socketLoggerUpdate() throws GenericInvalidArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {

        cmLogger.stackLog(socketClient.socketLogger.updateFather());
        socketClient.socketLogger.reinitialize();
    }


}