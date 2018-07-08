package it.polimi.ingsw.client.Graphics;

import it.polimi.ingsw.client.Graphics.CLI.CLIToolsManager;

import java.util.ArrayList;

public class StringCreator
{
    public enum State
    {
        DRAFTPOS,
        TOOL1,
        FINALPOSX,
        FINALPOSY,
        STARTPOSX,
        STARTPOSY,
        STARTPOS1X,
        STARTPOS1Y,
        FINALPOS1X,
        FINALPOS1Y,
        STARTPOS2X,
        STARTPOS2Y,
        FINALPOS2X,
        FINALPOS2Y,
        ROUND,
        ROUNDICE,
        ROUNDTOOL12,
        ROUNDDICETOOL12,
        ASKTOOLCARD,
        ASKSECONDDIE
    }

    /**
     * returns strings for cli questions
     * @param state
     * @return
     */
    public ArrayList<String> getString(State state)
    {
        CLIToolsManager cliToolsManager = new CLIToolsManager();
        ArrayList<String> temp = new ArrayList<String>();
        switch (state)
        {
            case DRAFTPOS:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Quale dado vuoi spostare?",40,true));
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la posizione nella riserva del dado da spostare",40,true));
                return temp;
            }
            case TOOL1: {
                temp.add(cliToolsManager.simpleQuestionsMaker("Premi + per aumentare il valore del dado, premi - per diminuire il valore del dado",40,true));
                return temp;
            }
            case FINALPOSX: {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il dado",40,true));
                return  temp;
            }
            case FINALPOSY:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il dado",40,true));
                return temp;
            }
            case STARTPOSX:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la riga del dado da spostare",40,true));
                return temp;
            }
            case STARTPOSY: {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del dado da spostare",40,true));
                return temp;
            }
            case STARTPOS1X:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la riga del primo dado da spostare",40,true));
                return temp;
            }
            case STARTPOS1Y:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del primo dado da spostare",40,true));
                return temp;
            }

            case FINALPOS1X:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il primo dado",40,true));
                return temp;

            }
            case FINALPOS1Y:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il primo dado",40,true));
                return temp;

            }
            case STARTPOS2X:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la riga del secondo dado da spostare",40,true));
                return temp;

            }
            case STARTPOS2Y:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la colonna del secondo dado da spostare",40,true));
                return temp;

            }
            case FINALPOS2X:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la riga dove posizionare il secondo dado",40,true));
                return temp;

            }
            case FINALPOS2Y:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la colonna dove posizionare il secondo dado",40,true));
                return temp;

            }
            case ROUND:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli il turno nel tracciato dei round dove è presente il dado che ti serve",40,true));
                return temp;

            }
            case ROUNDICE:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la posizione del dado che ti serve",40,true));
                return temp;

            }
            case ROUNDTOOL12:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli il round nel tracciato dei round del dado di cui vuoi prendere il colore",40,true));
                return temp;

            }
            case ROUNDDICETOOL12:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Scegli la posizione del dado di cui vuoi prendere il colore",40,true));
                return temp;

            }
            case ASKTOOLCARD:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Quale toolcard vuoi usare?", 40, true));
                return temp;
            }
            case ASKSECONDDIE:
            {
                temp.add(cliToolsManager.simpleQuestionsMaker("Vuoi spostare un secondo dado?  1 = SI, 2 = NO", 40, true));
                return temp;
            }
        }
        return null;   //----------------------------
    }
}
