package it.polimi.ingsw.client.PackageMP.ViewMP.JavaFX;


import it.polimi.ingsw.client.PackageMP.AbstractMPView;
import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.io.Console;

public class CLIMP extends AbstractMPView
{
    private MinorLogger cliMPlogger;

    private Console cnsl;

    private String username;


    private int id=0;

    public CLIMP(int n) throws GenericInvalidArgumentException, InvalidIntArgumentException {
        cliMPlogger = new MinorLogger();
        cliMPlogger.minorLog("CLI logger operative");

        id=n;
        cnsl = System.console();

        window1();

    }

    //GENERAL METHODS

    private void newlines(int n) throws InvalidIntArgumentException {
        if(n<1)
            throw new InvalidIntArgumentException();

        for(int i=0;i<n;i++)
        {
            cnsl.printf("%n");
        }
    }


    //INITIALIZATION 1: PHASE 1

    public String getUsername() throws InvalidIntArgumentException {
        cnsl.flush();
        String temp = window2();
        return temp;
    }

    public String usernameNotValid() throws InvalidIntArgumentException {
        cnsl.flush();
        String temp = window3();
        return temp;
    }

    //INITIALIZATION 1 WINDOWS

    private void window1() throws InvalidIntArgumentException {
        String temp = "YOU ARE THE "+(Integer.toString(id)+" PLAYER");

        newlines(2);
        cnsl.printf(fmt1(), "", temp, "");
        newlines(2);
    }

    private String window2() throws InvalidIntArgumentException {


        newlines(1);
        cnsl.printf(fmt1(), "", "Player "+Integer.toString(id), "");
        cnsl.printf(fmt1(), "", "INSERT USERNAME", "");
        String temp = cnsl.readLine(fmt1(), "");
        newlines(1);

        return temp;
    }

    private String window3() throws InvalidIntArgumentException {
        newlines(1);
        cnsl.printf(fmt1(), "", "USERNAME ALREADY IN USE", "");
        cnsl.printf(fmt1(), "", "INSERT USERNAME", "");
        String temp = cnsl.readLine(fmt1(), "");
        newlines(1);

        return temp;
    }

    //FORMAT STRINGS

    private String fmt1()
    {
        return "%1$10s %2$10s %3$10s%n";
    }
}
