package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

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
    private PublicObjectivesDeck pubDeck;
    private PublicObjective[] pubObjs;


    private SchemeCard[] playerSchemes;
    private PrivateObjective[] playersPrObjs;
    private int[] playerTokens;
    private int[] scores;

    public Match() throws IOException, InvalidIntArgumentException, InvalidinSocketException {
        System.out.println("start match constructor");
        track = new RoundTrack();

        //INITIALIZATION 1
        System.out.println("START INITIALIZATION 1");
        server = new SocketServer();
        numPlayers=server.initializeFPS();
        for(int i=1;i<numPlayers;i++)
            server.initializeNPS();
        server.initialization1Phase2();
        System.out.println("END INITIALIZATION 1");

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

    public void initialization2() throws InvalidIntArgumentException, IOException, InvalidinSocketException {

        System.out.println("START INITIALIZATION 2");
        //private objectives
        prDeck = new PrivateObjectivesDeck();
        playersPrObjs = new PrivateObjective[numPlayers];
        playersPrObjs = prDeck.extractPrObj(numPlayers);
        for(int i=0;i<numPlayers;i++)
            server.sendPrivObj(i, playersPrObjs[i].getColor());
        System.out.println("PRIVATE OBJECTIVES OK");

        //schemes
        scDeck = new SchemesDeck();
        playerSchemes = new SchemeCard[numPlayers];
        SchemeCard[] tempSCVector = new SchemeCard[numPlayers*2];
        tempSCVector = scDeck.extractSchemes(numPlayers*2);
        for(int i=0;i<numPlayers;i++) {
            server.sendSchemes(i, tempSCVector[i].getID(), tempSCVector[i + numPlayers].getID());
        }
        System.out.println("SCHEMES SENDED OK");

        //public objectives
        PublicObjectivesDeck pubDeck = new PublicObjectivesDeck();
        pubObjs = pubDeck.extractPubObjs();
        server.sendPubObjs(pubObjs[0].getId(), pubObjs[1].getId(), pubObjs[2].getId());
        System.out.println("PUBLIC OBJECTIVES OK");

        //reception schemes
        for(int i=0;i<numPlayers;i++)
        {
            int[] temp = new int[2];
            temp=server.getSelectedScheme(i);
            playerSchemes[i]=scDeck.extractSchemebyID(temp[0]);
            playerSchemes[i].setfb(temp[1]);
            System.out.println("Player "+Integer.toString(i+1)+" Schemeid = "+Integer.toString(playerSchemes[i].getID()));
            System.out.println("Player "+Integer.toString(i+1)+" FB = "+Integer.toString(playerSchemes[i].getfb()));
        }
        System.out.println("SCHEMES RECEPTION OK");
        System.out.println("initialization 2: phase 1 ended");

        //INITIALIZATION 2: PHASE 2
        System.out.println("initialization 2: phase 2 started");
        server.sendSchemestoEveryOne(playerSchemes);

        System.out.println("End initialization 2");


    }
}