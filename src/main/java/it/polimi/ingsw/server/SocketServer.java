package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;


public class SocketServer implements ConnectionServer {

    public static int PORT = 7777;
    private int counter=0;
    private boolean firstPlayerHasDone=false;
    private ArrayList<ServerPlayer> players;
    private int numPlayers=1;
    private String[] playersNames;
    private boolean matchEnd = false;
    private ServerSocket serverSocket;
    private Socket generalSocket;
    private ServerPlayer test;

    public SocketServer()
    {
        //ServerSocket serverSocket = null;
        players=new ArrayList<ServerPlayer>();


    }

    public int initializeFPS() throws IOException {
        serverSocket = new ServerSocket(PORT);
        Socket generalSocket = serverSocket.accept();

        System.out.println("Client "+Integer.toString(counter)+" connected");
        players.add(new ServerPlayer(generalSocket, counter));
        System.out.println(players.get(0).getUsername());

        players.get(0).initializeFirst();
        numPlayers=players.get(0).getNumPlayers();

        playersNames = new String[numPlayers];
        playersNames[0]=players.get(0).getUsername();
        for(int i=1;i<numPlayers;i++)
            playersNames[i]="";
        counter++;
        return numPlayers;
    }

    public void initializeNPS() throws IOException
    {
        String[] temp = new String[counter];
        for(int i=0;i<counter;i++)
        {
            temp[i]=playersNames[i];
        }
        serverSocket = new ServerSocket(PORT+counter);
        generalSocket = serverSocket.accept();
        System.out.println("Client "+Integer.toString(counter)+" connected");
        players.add(new ServerPlayer(generalSocket, counter, temp));
        players.get(counter).initializeN();
        playersNames[counter]=players.get(counter).getUsername();
        counter++;
    }

    public void initialization1Phase2()
    {
        playersNames = new String[numPlayers];
        for(int i=0;i<numPlayers;i++)
        {
            players.get(i).sendPlayersUsernames(playersNames);
        }
    }

    public void sendPrivObj(int player, int id)
    {
        players.get(player).sendPrivateObj(id);
    }

    public void sendScheme(int player, int id1, int id2)
    {
        players.get(player).sendScheme(id1);
        players.get(player).sendScheme(id2);

    }

    public void sendScoreMarkers(int[] scoreVector)
    {

    }

    public void sendPubObjs(int[] pubVec)
    {

    }

    public SchemeCard getSelectedScheme(int player)
    {
        return null;
    }

    public void sendDraftPool(DraftPool draft)
    {

    }

    public SchemeCard checkMove()
    {
        return null;

    }

    public int checkTool()
    {
        return 0;

    }

    public DraftPool checkDraft()
    {
        return null;

    }

    public void sendCorrectMove(boolean ok)
    {

    }

    public void sendCorrectToolUsed(boolean ok)
    {

    }

    public void sendNewToolTokens(int toolId,int tokens)
    {

    }

    public void schemeSelected(int id, int fb, int player)
    {

    }

    public void sendSchemes(SchemeCard sc, int id)
    {

    }
}