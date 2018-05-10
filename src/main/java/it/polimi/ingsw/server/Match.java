package it.polimi.ingsw.server;

import java.util.*;

public class Match
{
    private String[] playerNames;
    private int numPlayers;
    private SchemeCard[] playersSchemes;
    private DraftPool draft;
    private int[] playersPrivObjs;
    private ToolsDeck tDeck;
    private int round;
    private int playerTurn;
    private PublicObjective[] pubObjs;
    private int[] scores;
    private int[] playerTokens;
    private int[] schemesFb;
    private RoundTrack track;
    private SchemesDeck scDeck;
    private PrivateObjective[] playersPrObjs;

    public Match()
    {
        System.out.println("check 2");
        track = new RoundTrack();
        System.out.println("check 3");
        SocketServer server = new SocketServer();
        System.out.println("check 4");


    }

    public void CalculateScores()
    {
        // calcolo finale dei punteggi
    }

    public SchemeCard getPlayerScheme(int player) {
        // ritorna lo schema del giocatore in questione
        return null;
    }

    public int getPlayerFb(int player)
    {
        //
        return 0;
    }

    public DraftPool getDraft()
    {
        // ritorna la draftpool
        return null;
    }

    public void setPlayerScheme(int player, SchemeCard scheme)
    {
        // associa lo schema al giocatore
    }

    public void setDraft(DraftPool draft)
    {
        // imposta la draftpool passata come parametro come nuova draftpool
    }

}