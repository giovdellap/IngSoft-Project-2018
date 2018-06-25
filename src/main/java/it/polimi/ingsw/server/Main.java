package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MajorLogger;
import it.polimi.ingsw.server.ServerExceptions.FullDataStructureException;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.IOException;

public class Main {

    /**
     * MAIN, starts a MATCH
     * @param args
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws InvalidinSocketException
     * @throws GenericInvalidArgumentException
     * @throws FullDataStructureException
     */
    public static void main(String[] args) throws IOException, InvalidIntArgumentException, InvalidinSocketException, GenericInvalidArgumentException, FullDataStructureException, it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException, it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException {

        Match thisMatch = new Match();
    }
}