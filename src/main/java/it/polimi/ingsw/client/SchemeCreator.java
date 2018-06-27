package it.polimi.ingsw.client;

import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.CLIToolsManager;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.ModelGenerator;
import it.polimi.ingsw.client.PackageMP.ViewMP.CLI.PrinterMaker;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.io.*;

public class SchemeCreator
{
    private ModelGenerator modelGenerator;
    private SchemeCard newScheme;
    private PrinterMaker printerMaker;
    private CLIToolsManager clito;
    private String msgIN;

    private BufferedReader inKeyboard;
    private PrintWriter outVideo;

    public SchemeCreator() throws InvalidIntArgumentException {
        modelGenerator = new ModelGenerator();
        newScheme = new SchemeCard(100);
        newScheme.setfb(1);
        this.inKeyboard = new BufferedReader(new InputStreamReader(System.in));
        this.outVideo = new PrintWriter(new BufferedWriter(new OutputStreamWriter(System.out)), true);
        printerMaker = new PrinterMaker(1);
        clito = new CLIToolsManager();
    }

    public void createScheme() throws IOException, InvalidIntArgumentException {
        printOut(clito.simpleQuestionsMaker("BENVENUTO NELLA CREAZIONE SCHEMA", 80, false));
        boolean end=false;
        while(!end)
        {
            printOut(clito.simpleQuestionsMaker("Questo è il tuo schema", 80, false));
            printOut(" ");
            printOut(modelGenerator.getScheme(newScheme));
            printOut(" ");

            printOut(clito.simpleQuestionsMaker("Seleziona la riga della cella da modificare", 80, true));
            readWithExceptions(1, 4);
            int x = Integer.parseInt(msgIN);

            printOut(clito.simpleQuestionsMaker("Seleziona la colonna della cella da modificare", 80, true));
            readWithExceptions(1, 5);
            int y = Integer.parseInt(msgIN);

            printOut(clito.simpleQuestionsMaker("Valore o colore?", 80, false));
            printOut(clito.simpleQuestionsMaker("Premere 1 per valore, 2 per colore", 80, true));
            readWithExceptions(1, 2);
            if(msgIN=="1")
            {
                printOut(clito.simpleQuestionsMaker("Quale valore vuoi scegliere?", 80, true));
                readWithExceptions(1, 6);
            }
            else
            {
                printOut(clito.simpleQuestionsMaker("Quale colore vuoi scegliere?", 80, true));
                printOut(clito.simpleQuestionsMaker("1 = GIALLO - 2 = ROSSO - 3 = VERDE - 4 = BLU - 5 = VIOLA", 80, false));
                readWithExceptions(1, 5);
            }
            int toInsert = Integer.parseInt(msgIN);

            if(newScheme.getCell(1, x, y)==0)
                newScheme.setCell(1, x, y, toInsert);
            else
            {
                printOut(clito.simpleQuestionsMaker("Sei sicuro di voler cambiare la tua scelta precedente?", 80, false));
                printOut(clito.simpleQuestionsMaker("1 = si, 2 = no", 80, false));
                readWithExceptions(1, 2);
                if(Integer.parseInt(msgIN)==1)
                    newScheme.setCell(1, x, y, toInsert);
            }



        }

    }



    /**
     * our read from keyboard message with NumberFormat Exceptions and recursive call if integer is out of bounds
     * @param leftBound
     * @param rightBound
     * @throws IOException
     */

    public void readWithExceptions(int leftBound, int rightBound) throws IOException {

        try {

            readIt();

            if (Integer.parseInt(msgIN) < leftBound || Integer.parseInt(msgIN) > rightBound) {
                printOut(printerMaker.wrongInsertion());
                readWithExceptions(leftBound, rightBound);
            }
        }

        catch (NumberFormatException e) {
            printOut(printerMaker.wrongInsertion());
            readWithExceptions(leftBound, rightBound);
        }

    }

    private void printOut(String[] printerMakerResult)
    {
        //shows user the printerMaker result

        for(int i=0;i<printerMakerResult.length;i++)
        {
            outVideo.println(printerMakerResult[i]);
            outVideo.flush();
        }
    }

    /**
     * our print out method
     * @param s
     */
    private void printOut(String s)
    {
        outVideo.println(s);
        outVideo.flush();
    }

    /**
     * our read from keyboard method
     * @throws IOException
     */
    private void readIt() throws IOException
    {
        //resets the buffer and reads from it
        outVideo.println("==>");
        outVideo.flush();
        msgIN = inKeyboard.readLine();
        //System.out.println(msgIN);
    }
}
