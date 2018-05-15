package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.IOException;
import java.util.*;

public class Main {

    private Match thisMatch;

    public static void main(String[] args) throws IOException, InvalidIntArgumentException, InvalidinSocketException {
        Match thisMatch = new Match();
        System.out.println("end of match constructor");

    }


}