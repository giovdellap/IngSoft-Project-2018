package it.polimi.ingsw.clientOld;


import it.polimi.ingsw.clientOld.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.clientOld.Loggers.MajorLogger;
import it.polimi.ingsw.clientOld.MultiPackage.Multiplayer;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.IOException;

public class Main {


    public static void main(String[] args) throws GenericInvalidArgumentException, IOException, InvalidIntArgumentException {

        MajorLogger logger =new MajorLogger();
        logger.majorLog("Main logger operative");

        Multiplayer matchMP = new Multiplayer();
    }


}