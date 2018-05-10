package it.polimi.ingsw.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;


public class SocketServer implements ConnectionServer {

    public static int PORT = 7777;
    private int counter=1;

    public SocketServer()
    {
        ServerSocket serverSocket = null;

        try
        {
            serverSocket = new ServerSocket(PORT);
            System.out.println("check 6");

            while(true)
            {
                Socket socket = serverSocket.accept();
                System.out.println("check 7");
                System.out.println("Client "+Integer.toString(counter)+" connected");
                System.out.println("check 8");
                new ServerPlayer(socket, counter);
                System.out.println("check 9");

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