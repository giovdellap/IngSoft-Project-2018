package it.polimi.ingsw.server.Connection;

import it.polimi.ingsw.commons.SocketEncoder;
import it.polimi.ingsw.commons.SocketProtocolTransformer;
import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.RoundTrack;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidinSocketException;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class SocketPlayer
{

    private Socket socket;
    private BufferedReader inSocket;
    private PrintWriter outSocket;
    private String ip;

    //TOOLS
    private SocketProtocolTransformer transformer;
    private SocketEncoder socketEncoder;

    private int id;
    private String username="";
    private String tempCmd="";
    private String tempArg="";
    private String msg;
    private int numPlayers;

    private String[] tempNames;
    private String[] playerNames;
    private boolean connected = true;

    public MinorLogger sPlayerLog;

    //COSTRUCTOR

    public SocketPlayer(Socket s) throws IOException, GenericInvalidArgumentException {

        sPlayerLog = new MinorLogger();

        socket = s;
        inSocket = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        outSocket = new PrintWriter(new BufferedWriter(new OutputStreamWriter(socket.getOutputStream())), true);

        transformer = new SocketProtocolTransformer();
        socketEncoder= new SocketEncoder();
    }

    //TURN METHODS

    public void sendTurnData(int round, ArrayList<Integer> discPlayers, int active) throws IOException, GenericInvalidArgumentException {
        try {
            sendMessage("round", Integer.toString(round));
            sendMessage("disconnected", Integer.toString(discPlayers.size()));
            if(!discPlayers.isEmpty())
            {
                int i=0;
                while(i<discPlayers.size())
                {
                    sendMessage("id", Integer.toString(discPlayers.get(i)));
                    i++;
                }
            }
            sendMessage("active", Integer.toString(active));
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public void sendDraft(DraftPool draft) throws InvalidIntArgumentException, IOException, GenericInvalidArgumentException {
        System.out.println("prima");
        String[] temp = socketEncoder.draftEncoder(draft);
        System.out.println("dopo");
        System.out.println(temp[0]);
        for(int i=0;i<temp.length;i++)
        {
            try{
                sendReadyMessage(temp[i]);
            } catch (Exception e)
            {
                socket.close();
                disconnectionManager();
            }
        }

    }

    public void sendRoundTrack(RoundTrack track, int round) throws InvalidIntArgumentException, IOException, GenericInvalidArgumentException {
        String[] temp = socketEncoder.roundTrackEncoder(track, round);
        try{
            for(int i=0;i<temp.length;i++)
                sendReadyMessage(temp[i]);
            sendMessage("end", "track");
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }

    }

    public void toolCardsUpdate(int[] arg) throws IOException, GenericInvalidArgumentException {
        String[] temp = socketEncoder.toolCardUpdateEncoder(arg);
        try{
            for(int i=0;i<temp.length;i++)
                sendReadyMessage(temp[i]);
            sendMessage("end", "tool");
        }catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public void sendWait() throws IOException, GenericInvalidArgumentException {
        try {
            sendMessage("wait", "players");
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public int getToDo() throws GenericInvalidArgumentException, IOException {
        try {
            sendMessage("wait", "todo");
            System.out.println("Wait todo sent");
            receiveMessage();
            return Integer.parseInt(transformer.getArg());
        }catch (Exception e)
        {
            socket.close();
            disconnectionManager();
            return 0;
        }
    }

    public void serverCheckResponse(boolean response) throws GenericInvalidArgumentException, IOException {
        try{
            sendMessage("check", Boolean.toString(response));
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public void sendScheme(SchemeCard scheme) throws InvalidIntArgumentException, GenericInvalidArgumentException, IOException {
        String[] temp = socketEncoder.schemeCardEncoder(scheme);
        try{
            for(int i=0;i<temp.length;i++)
                sendReadyMessage(temp[i]);
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    //MOVE
    public int receiveDraftPoolMove() throws IOException, GenericInvalidArgumentException {

        try {
            int temp;
            receiveMessage();
            if (transformer.getArg() == "draft") ;
            {
                receiveMessage();
                temp = Integer.parseInt(tempArg);
            }
            receiveMessage();
            return temp;
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
            return -1;
        }
    }

    public int[] receiveSchemeCardMove() throws GenericInvalidArgumentException, IOException {
        int[] temp = new int[2];
        try{
            receiveMessage();
            if(transformer.getArg().equals("scheme"))
            {
                receiveMessage();
                temp[0] = Integer.parseInt(transformer.getArg());
                receiveMessage();
                temp[1] = Integer.parseInt(transformer.getArg());
                receiveMessage();
            }
        }catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
        return temp;
    }

    public void notifyAction(int id, int action) throws IOException, GenericInvalidArgumentException {
        //starts notify client about active player's action
        try{
            sendMessage("player", Integer.toString(id));
            if(action==0)
                sendMessage("action", "pass");
            if(action==1)
                sendMessage("action", "move");
            if(action==2)
                sendMessage("action", "tool");
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public void endAction(int action) throws GenericInvalidArgumentException, IOException {
        try{
            if(action==1)
                sendMessage("end", "move");
            if(action==2)
                sendMessage("end", "tool");
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public void endTurnNotifier() throws GenericInvalidArgumentException, IOException {
        try{
            sendMessage("end", "turn");
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public int getToolId() throws IOException, GenericInvalidArgumentException {
        int temp;
        try {
            receiveMessage();
        }catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
        temp=Integer.parseInt(transformer.getArg());
        return temp;
    }

    //TOOLS RECEIVE METHODS
    public int[] receiveSimpleXYTool() throws GenericInvalidArgumentException, IOException {
        //receives simple x,y coordinates

        int[] temp = new int[2];
        try {
            receiveMessage();
            temp[0] = Integer.parseInt(transformer.getArg());
            receiveMessage();
            temp[1] = Integer.parseInt(transformer.getArg());

        }catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
        return temp;
    }

    public char receiveTool1Action() throws GenericInvalidArgumentException, IOException {
        char temp='a';
        try{
            receiveMessage();
            temp = transformer.getArg().charAt(0);
        }catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
        return temp;
    }

    public int[] receiveDoubleXYTool() throws GenericInvalidArgumentException, IOException {
        int[] temp = new int[4];
        try{
            receiveMessage();
            temp[0] = Integer.parseInt(transformer.getArg());
            receiveMessage();
            temp[1] = Integer.parseInt(transformer.getArg());
            receiveMessage();
            temp[2] = Integer.parseInt(transformer.getArg());
            receiveMessage();
            temp[3] = Integer.parseInt(transformer.getArg());
        }catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
        return temp;
    }

    //INITIALIZATION 1

    public String insertUsername() throws GenericInvalidArgumentException, IOException
    {
        try
        {
            outSocket.println(simpleEncode("insert", "username"));
            outSocket.flush();

            simpleDecode(inSocket.readLine());
            return tempArg;
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
            return null;
        }
    }

    public void confirmUsername() throws IOException, GenericInvalidArgumentException
    {

        try
        {
            username = tempArg;
            outSocket.println(simpleEncode("confirm", "username"));
            outSocket.flush();

            outSocket.println(simpleEncode("wait", "players"));
            outSocket.flush();
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public String getUsername()
    {
        return username;
    }

    public void init1Phase2NumPl(int num) throws GenericInvalidArgumentException, IOException
    {
        numPlayers = num;
        try
        {
            outSocket.println(simpleEncode("numplayers", Integer.toString(numPlayers)));
            outSocket.flush();
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public void init1Phase2Player(int index, String name) throws GenericInvalidArgumentException, IOException
    {
        try
        {
            outSocket.println(simpleEncode("player", Integer.toString(index)));
            outSocket.flush();

            outSocket.println(simpleEncode("username", name));
            outSocket.flush();
        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }


    //INITIALIZATION 2

    public void sendPrivateObj(int id) throws GenericInvalidArgumentException, IOException
    {
       try
       {
           outSocket.println(simpleEncode("privobj", Integer.toString(id)));
           outSocket.flush();
       } catch (Exception e)
       {
           socket.close();
           disconnectionManager();
       }
    }

    public void sendScheme(int id) throws GenericInvalidArgumentException, IOException
    {
        try
        {
            outSocket.println(simpleEncode("scheme", Integer.toString(id)));
            outSocket.flush();
        } catch(Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    public void sendPubObjs(int id1, int id2, int id3) throws GenericInvalidArgumentException, IOException
    {
       try
       {
           outSocket.println(simpleEncode("pubobj", Integer.toString(id1)));
           outSocket.flush();

           outSocket.println(simpleEncode("pubobj", Integer.toString(id2)));
           outSocket.flush();

           outSocket.println(simpleEncode("pubobj", Integer.toString(id3)));
           outSocket.flush();
       } catch (Exception e)
       {
           socket.close();
           disconnectionManager();
       }
    }

    public void sendTools(int id1, int id2, int id3)
    {
        sendMessage("tool", Integer.toString(id1));
        sendMessage("tool", Integer.toString(id2));
        sendMessage("tool", Integer.toString(id3));
    }

    public void setPlayersNames(String[]usernames)
    {
        playerNames=usernames;
    }

    public int[] receiveScheme() throws IOException, InvalidinSocketException, GenericInvalidArgumentException
    {

        int[] temp = new int[2];
        try
        {
            outSocket.println("#insert#$scheme$");
            outSocket.flush();
        } catch (Exception e)
        {
            try
            {
                sPlayerLog.minorLog("Exception e");
                socket.close();
                sPlayerLog.minorLog("Socket closed");
                disconnectionManager();
            } catch (Exception ex)
            {
            }
        }

        try
        {
            msg = inSocket.readLine();
            simpleDecode(msg);
        } catch (Exception e)
        {
            try
            {
                sPlayerLog.minorLog("Exception e");
                socket.close();
                sPlayerLog.minorLog("Socket closed");
                disconnectionManager();
            } catch (Exception ex)
            {
            }
        }

        if(!connectionCheck())
        {
            temp[0]=0;
            temp[1]=0;
            disconnectionManager();
            return temp;
        }

        if(Integer.parseInt(tempArg)<1||Integer.parseInt(tempArg)>12)
            throw new InvalidinSocketException();

        temp[0]=Integer.parseInt(tempArg);

        msg=inSocket.readLine();
        simpleDecode(msg);

        if((!tempCmd.equals("fb"))||(Integer.parseInt(tempArg)!=1&&Integer.parseInt(tempArg)!=2))
            throw new InvalidinSocketException();

        temp[1]=Integer.parseInt(tempArg);
        try
        {
            outSocket.println("#wait#$players$");
            outSocket.flush();
        } catch (Exception e) {
            try
            {
                sPlayerLog.minorLog("Exception e");
                socket.close();
                sPlayerLog.minorLog("Socket closed");
                disconnectionManager();
            } catch (Exception ex)
            {
            }
        }

        if(socket.isClosed())
            disconnectionManager();

        return temp;
    }


    public void sendSchemeVect(SchemeCard[] vect) throws InvalidIntArgumentException, GenericInvalidArgumentException
    {
        for(int i=0;i<numPlayers;i++)
        {
            try
            {
                outSocket.println(simpleEncode("player", Integer.toString(i)));
                outSocket.flush();

                outSocket.println(simpleEncode("username", playerNames[i]));
                outSocket.flush();

                outSocket.println("#scheme#$" + Integer.toString(vect[i].getID()) + "$");
                outSocket.flush();

                outSocket.println("#fb#$" + Integer.toString(vect[i].getfb()) + "$");
                outSocket.flush();

                outSocket.println("#favtokens#$" + Integer.toString(vect[i].getDiff(vect[i].getfb()))+"$");

            } catch (Exception e)
            {
                try
                {
                    sPlayerLog.minorLog("Exception e");
                    socket.close();
                    sPlayerLog.minorLog("Socket closed");
                    disconnectionManager();
                } catch (Exception ex)
                {
                }
            }
        }
        if(socket.isClosed())
            disconnectionManager();
    }


    //CONNECTION LOST MANAGEMENT
    private void disconnectionManager() throws GenericInvalidArgumentException {
        connected = false;
        sPlayerLog.minorLog("CLIENT "+Integer.toString(id+1)+" DISCONNECTED");
    }

    public boolean connectionCheck()
    {
        //returns true if connected
        return connected;
    }

    public void notifyDisconnectedPlayer(int playerID) throws GenericInvalidArgumentException, IOException {
        try {
            outSocket.println("#fail#$" + Integer.toString(playerID) + "$");
            outSocket.flush();
            sPlayerLog.minorLog("Disconnection player " + Integer.toString(playerID) + " notified to player " + Integer.toString(id));

        } catch (Exception e)
        {
            socket.close();
            disconnectionManager();
        }
    }

    //ENCODE/DECODE


    public String simpleEncode(String cmd, String arg)
    {
        String temp = "#"+cmd+"#$"+arg+"$";
        return temp;
    }

    private void simpleDecode(String tempStr)
    {
        //parts the message into command and argument
        tempCmd="";
        tempArg="";
        int index=0;
        if(tempStr.charAt(index)=='#')
        {
            index++;
            while (tempStr.charAt(index) != '#')
            {
                tempCmd+=Character.toString(tempStr.charAt(index));
                index++;
            }
            index++;
            if(tempStr.charAt(index)=='$');
            {
                index++;
                while(tempStr.charAt(index)!='$')
                {
                    tempArg+=Character.toString(tempStr.charAt(index));
                    index++;
                }
            }
        }
    }

    //RECEIVE/SEND
    private void receiveMessage() throws IOException {
        transformer.simpleDecode(inSocket.readLine());
        System.out.println("received: "+transformer.getCmd()+" "+transformer.getArg());
    }

    private void sendMessage(String cmd, String arg)
    {
        System.out.println("sent: "+cmd+" "+arg);
        outSocket.println(transformer.simpleEncode(cmd, arg));
        outSocket.flush();;
    }

    private void sendReadyMessage(String s)
    {
        System.out.println("sent: "+s);
        outSocket.println(s);
        outSocket.flush();
    }
}

