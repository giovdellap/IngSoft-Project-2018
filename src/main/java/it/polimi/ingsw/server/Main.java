package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MajorLogger;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.IOException;

public class Main {



    public static void main(String[] args) throws IOException, InvalidIntArgumentException, InvalidinSocketException {

        MajorLogger logger = new MajorLogger();
        logger.majorLog("Logger created");
        Match thisMatch = new Match();
        logger.stackLog(thisMatch.matchLog.updateFather());
        logger.majorLog("PROGRAM ENDED");
        logger.getLog();

    }


}