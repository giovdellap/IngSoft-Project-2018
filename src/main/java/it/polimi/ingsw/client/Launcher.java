package it.polimi.ingsw.client;

import java.io.*;

public class Launcher
{
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    public Launcher()
    {
        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
    }

}
