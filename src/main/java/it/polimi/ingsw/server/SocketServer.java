package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;


public class SocketServer implements ConnectionServer {

    public static int PORT = 7777;
    private int counter=1;
    private boolean firstPlayerHasDone=false;
    private ArrayList<ServerPlayer> players;
    private int numPlayers=1;
    private String[] playersNames;

    public SocketServer()
    {
        ServerSocket serverSocket = null;

        try
        {
            serverSocket = new ServerSocket(PORT);
            System.out.println("check 6");
            Socket socket = serverSocket.accept();
            System.out.println("Client "+Integer.toString(counter)+" connected");
            players.add(new ServerPlayer(socket, counter));
            counter++;

            while(!firstPlayerHasDone)
                firstPlayerHasDone=players.get(0).hasFPDone();
            numPlayers=players.get(0).getNumPlayers();
            playersNames = new String[numPlayers];
            playersNames[0]=players.get(0).getUsername();

            while(counter!=numPlayers)
            {
                serverSocket = new ServerSocket(PORT+counter-1);
                socket = serverSocket.accept();
                System.out.println("Client "+Integer.toString(counter)+" connected");
                players.add(new ServerPlayer(socket, counter, playersNames));
                counter++;
            }


        } catch (Exception e)
        {}
    }

    public void sendPrivObj(int player, int id)
    {

    }

    public void sendSchemes(SchemeCard tempScheme, int player)
    {

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

}