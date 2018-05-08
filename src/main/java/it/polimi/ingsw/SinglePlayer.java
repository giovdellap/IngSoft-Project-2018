package it.polimi.ingsw;

import it.polimi.ingsw.ModelComponentsSP.*;

import java.util.*;

public class SinglePlayer {

    private DraftPoolSP draft;
    private RoundTrackSP track;
    private SchemeCardSP currScheme;
    private SchemeCardSP tempScheme;
    private int diff;
    private PrivateObjectiveSP[] privObjs;
    private PublicObjectiveSP[] pubObjs;
    private ToolSP[] tools;
    private int round;
    private int turn;
    private int score;

    public SinglePlayer()
    {

        //costruttore

    }


    public void calculateScore()
    {

        //calcola il punteggio


        return null;
    }


    public DraftPoolSP getDraftPool()
    {
        // refresh DraftPool
    }


    public void setDraftPool(DraftPoolSP draft)
    {
        // imposta la nuova draftpool dopo che è stata modificata
    }


    public SchemeCardSP getScheme()
    {
        // estrae uno schema
    }


    public void setScheme(SchemeCardSP scheme)
    {
        // imposta lo schema dopo che è stato modificato
    }

    public void setCurrentScheme(int id, int fb)
    {
        // imposta lo schema scelto dall'utente
    }


    public void setDiff(int diff)
    {
        // imposta difficoltà
    }

    public void addRound(RoundDice rd)
    {

    }

}