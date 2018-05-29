package it.polimi.ingsw.clientOld.ViewMP;


import it.polimi.ingsw.clientOld.AbstractMPView;
import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;

import java.io.*;

public class CLIMP extends AbstractMPView
{
    private MinorLogger cliMPlogger;

    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    public CLIMP() throws GenericInvalidArgumentException {
        cliMPlogger = new MinorLogger();
        cliMPlogger.minorLog("CLI logger operative");

        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        cliMPlogger.minorLog("I/O CLI operative");



    }
}
