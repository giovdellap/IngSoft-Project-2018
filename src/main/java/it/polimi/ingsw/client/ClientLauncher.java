package it.polimi.ingsw.client;

import it.polimi.ingsw.client.Graphics.CLI.CLIToolsManager;
import java.io.*;
import java.util.ArrayList;

public class ClientLauncher
{
    private BufferedReader inKeyboard;
    private PrintWriter outVideo;
    private CLIToolsManager clito;
    private String msgIN;
    private ArrayList<String> settings;


    public ClientLauncher()
    {
        clito = new CLIToolsManager();
        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
    }

    /**
     * asks user on what wants to do, if he wants to start a new match asks for settings
     * SETTINGS: reconnection/debug mode/IP/port
     * @return an integer corrisponding to the main choice
     * @throws IOException
     */
    public int launch() throws IOException//1==PLAY, 2==SCHEMECREATOR
    {
        outVideo.println("");
        outVideo.flush();
        outVideo.println(clito.centerThatString("BENVENUTO SU SAGRADA", 80));
        outVideo.flush();
        outVideo.println(clito.simpleQuestionsMaker("Premi 0 per uscire, 1 per giocare, 2 per costruire uno schema pesonalizzato", 40, true));
        outVideo.flush();

        readWithExceptions(1, 2);
        int choice = Integer.parseInt(msgIN);

        if(Integer.parseInt(msgIN)==1)
        {
            settings = new ArrayList<String>();

            outVideo.println("");
            outVideo.flush();

            outVideo.println(clito.simpleQuestionsMaker("PREMI 1 PER LA DEBUG MODE, 2 PER PARTITA NORMALE",80 , true));
            outVideo.flush();

            readWithExceptions(1,2 );

            String debug;
            if (msgIN.equals("1")) {
                settings.add("true");
                settings.add("CLI");
                settings.add("false");
                settings.add("localhost");
                settings.add("7777");

            }
            else {
                settings.add("false");

                settings.add("CLI");

                outVideo.println(clito.simpleQuestionsMaker("PREMI 1 PER UNA NUOVA PARTITA, 2 PER RICONETTERTI AD UNA IN CORSO", 80, true));
                outVideo.flush();

                readWithExceptions(1, 2);

                String reconnection;
                if (msgIN.equals("1"))
                    settings.add("false");
                else {
                    settings.add("true");
                    settings.set(0, "true");
                }

                outVideo.println(clito.simpleQuestionsMaker("INSERISCI INDIRIZZO IP", 80, true));
                outVideo.flush();
                outVideo.println("==>");
                outVideo.flush();

                settings.add(inKeyboard.readLine());

                outVideo.println(clito.simpleQuestionsMaker("INSERISCI NUMERO DI PORTA", 80, true));
                outVideo.flush();

                readWithExceptions(1024, 65535);

                settings.add(msgIN);
            }
        }
        return choice;
    }

    private void readWithExceptions(int leftBound, int rightBound) throws IOException
    {
        try {
            outVideo.println("==>");
            outVideo.flush();
            msgIN=inKeyboard.readLine();
            if (Integer.parseInt(msgIN) < leftBound || Integer.parseInt(msgIN) > rightBound)
            {
                outVideo.println(clito.centerThatString("INSERIMENTO ERRATO", 80));
                outVideo.flush();
                readWithExceptions(leftBound, rightBound);
            }
        }
        catch (NumberFormatException e) {
            outVideo.println(clito.centerThatString("INSERIMENTO ERRATO", 80));
            outVideo.flush();
            readWithExceptions(leftBound, rightBound);
        }
    }

    /**return settings
     *
     * @return an arraylist representing settings
     */
    public ArrayList<String> getSettings()
    {
        return settings;
    }

}
