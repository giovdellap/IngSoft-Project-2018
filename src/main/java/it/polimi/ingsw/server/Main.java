package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.io.*;

public class Main {

    private static Match thisMatch;

    /**
     * MAIN, starts a MATCH
     * @param args
     * @throws IOException
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     * @throws FullDataStructureException
     */
    public static void main(String[] args) throws IOException, InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException, InterruptedException {

        BufferedReader inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        PrintWriter outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);

        outVideo.println("SELEZIONA LA PORTA DA USARE");
        outVideo.flush();
        String port = inKeyboard.readLine();

        thisMatch = new Match(Integer.parseInt(port));



    }
}