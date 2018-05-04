package it.polimi.ingsw;

import it.polimi.ingsw.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.ModelComponentsMP.SchemeCardMP;

import java.util.*;

public abstract class AbstractMPView {

    private DraftPoolMP tempDraft;
    public SchemeCardMP tempScheme;
    public int tempTokens;

    public AbstractMPView()
    {

    }

    public void setPrivObj(int id)
    {
        // imposta Obiettivo Privato
    }

    public void setPossibleSchemes(int id1,int id2)
    {
        // passaggio schemi fra cui sceglierne 1
    }

    public void setTokens(int num)
    {
        // imposta num token del giocatore
    }

    public void setMarkers(int[ ] markers)
    {
        // imposta l'ordine dei markers
    }


    public void setPublicObjectives(int id1, int id2, int id3)
    {
        // imposta obiettivi pubblici
    }

    public void setOppScheme(int opp, SchemeCardMP scheme)
    {
        // refresh schema avversario
    }

    public void setOppToken(int opp, int tokens)
    {
        // refresh tokens avversario
    }

    public void setRound(int turn)
    {
        // refresh num round
    }

    public void setTurn(int opp)
    {
        // cambio turno
    }

    public void refreshDraftPool(DraftPoolMP dp)
    {

    }

    public void addTokensonTool(int idTool, int numTokens)
    {
        // setta il numero di token sul singolo tool
    }

    public void setIdScoreBoard(int[ ] id)
    {
        // classifica finale per username
    }

    public void setScores(int[ ] scores)
    {

        // imposta gli score
    }


    public void move()
    {

        // gestisce la mossa
    }


    public void useTool()
    {

        // utilizza uno strumento
    }

    public void applyModifies()
    {

        // applica le modifiche
    }

    public void selectOneScheme(int id, int fb)
    {

        // seleziona uno schema tra i due possibili
    }

}
