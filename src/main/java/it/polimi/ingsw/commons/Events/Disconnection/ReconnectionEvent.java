package it.polimi.ingsw.commons.Events.Disconnection;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.server.ModelComponent.RoundDice;
import it.polimi.ingsw.server.ModelComponent.RoundTrack;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class ReconnectionEvent extends Event {

    private ArrayList<ReconnectionPlayer> players;
    private int privObj;
    private int[] pubObjs;
    private int[] toolsIds;
    private int[] toolsTokens;
    private ArrayList<ReconnectionRoundDice> reconnectionTrack;


    public ReconnectionEvent() {
        super("ReconnectionEvent");
        players=new ArrayList<ReconnectionPlayer>();
        reconnectionTrack = new ArrayList<ReconnectionRoundDice>();
    }

    public void addPlayer(String name, int tokens, SchemeCard schemeCard) throws InvalidIntArgumentException {
        players.add(new ReconnectionPlayer(name, tokens, schemeCard));
    }

    public void addPrivObj(int id)
    {
        privObj=id;
    }

    public void addPubObjs(int[] ids)
    {
        pubObjs=ids;
    }

    public void addToolsIds(int[] ids)
    {
        toolsIds=ids;
    }

    public void addToolsTokens(int[] tokens)
    {
        toolsTokens = tokens;
    }


    public void addTrack(RoundTrack track) throws InvalidIntArgumentException {
        for(int i=0;i<track.returnActualTurn();i++)
            reconnectionTrack.add(new ReconnectionRoundDice(track.returnNTurnRoundDice(i)));
    }

    public void addReconnectionRD(ArrayList<Die> temp)
    {
        reconnectionTrack.add(new ReconnectionRoundDice(temp));
    }

    public int getPrivObj() {
        return privObj;
    }

    public int[] getPubObjs() {
        return pubObjs;
    }

    public int[] getToolsIds() {
        return toolsIds;
    }

    public int[] getToolsTokens() {
        return toolsTokens;
    }

    public ArrayList<ReconnectionPlayer> getPlayers() {
        return players;
    }

    public ArrayList<ReconnectionRoundDice> getReconnectionTrack() {
        return reconnectionTrack;
    }

    public class ReconnectionRoundDice
    {
        private ArrayList<Die> rd;

        public ReconnectionRoundDice(RoundDice roundDice) throws InvalidIntArgumentException {
            for(int i=0;i<roundDice.returnDim();i++)
                rd.add(roundDice.getDie(i));
        }
        public ReconnectionRoundDice(ArrayList<Die> tempRd)
        {
            rd=tempRd;
        }
        public ArrayList<Die> getRd() {
            return rd;
        }
    }

}
