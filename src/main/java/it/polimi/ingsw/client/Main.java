package it.polimi.ingsw.client;


import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.Loggers.MajorLogger;
import it.polimi.ingsw.client.MultiPackage.Multiplayer;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException {

        MajorLogger logger =new MajorLogger();
        logger.majorLog("Main logger operative");

        Multiplayer matchMP = new Multiplayer();
    }


}