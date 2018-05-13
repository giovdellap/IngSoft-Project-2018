package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.io.IOException;
import java.util.*;

public class Match
{
    SocketServer server;

    private int numPlayers=0;
    private String[] playerNames;
    private int round;
    private int playerTurn;

    //General Model Components
    private DraftPool draft;
    private ToolsDeck tDeck;
    private SchemesDeck scDeck;
    private RoundTrack track;
    private PrivateObjectivesDeck prDeck;


    private SchemeCard[] playersSchemes;
    private PrivateObjective[] playersPrObjs;
    private int[] playerTokens;
    private int[] scores;

    public Match() throws IOException, InvalidIntArgumentException {
        track = new RoundTrack();

        //INITIALIZATION 1
        server = new SocketServer();
        numPlayers=server.initializeFPS();
        System.out.println("check intermedio");
        for(int i=1;i<numPlayers;i++)
            server.initializeNPS();
        server.initialization1Phase2();
        System.out.println("check 4");

        //INITIALIZATION 2
        initialization2();

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

    public void initialization2() throws InvalidIntArgumentException
    {

        //private objectives
        prDeck = new PrivateObjectivesDeck();
        playersPrObjs = new PrivateObjective[numPlayers];
        playersPrObjs = prDeck.extractPrObj(numPlayers);
        for(int i=0;i<numPlayers;i++)
            server.sendPrivObj(i, playersPrObjs[i].getColor());

        //schemes
        scDeck = new SchemesDeck();
        SchemeCard[] tempSCVector = new SchemeCard[numPlayers*2];
        tempSCVector = scDeck.extractSchemes(8);

        for(int i=0;i<numPlayers;i++) {
            server.sendScheme(i, tempSCVector[i].getID(), tempSCVector[i + 4].getID());
        }
    }
}