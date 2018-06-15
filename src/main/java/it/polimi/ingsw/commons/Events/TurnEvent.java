package it.polimi.ingsw.commons.Events;

import it.polimi.ingsw.commons.Die;

import java.util.ArrayList;

public class TurnEvent extends Event
{
    private int round;
    private boolean noDisconnected;
    private ArrayList<String> disconnected;
    int active;

    private ArrayList<Die> draft;
    private int[] toolsUpdate;

    private boolean myTurn;
    private ArrayList<Die> lastRound;
    private boolean nextRound;


    public TurnEvent() {
        super("TurnEvent");
    }


    //SET METHODS
    public void setRound(int round) {
        this.round = round;
    }
    public void setDisconnected(ArrayList<String> disconnected) {
        noDisconnected=false;
        this.disconnected = disconnected;
    }
    public void noDisconnected()
    {
        noDisconnected=true;
    }
    public void setActive(int active) {
        this.active = active;
    }
    public void setDraft(ArrayList<Die> draft) {
        this.draft = draft;
    }
    public void setToolsUpdate(int[] tools)
    {
        toolsUpdate=tools;
    }
    public void setMyTurn(boolean bool)
    {
        myTurn=bool;
    }
    public void setLastRound(ArrayList<Die> lastRound) {
        this.lastRound = lastRound;
    }
    public void setNextRound(boolean bool)
    {
        nextRound=bool;
    }

    //GET METHODS
    public int getRound()
    {
        return round;
    }
    public boolean getNoDisconnected()
    {
        return noDisconnected;
    }
    public ArrayList<String> getDisconnected()
    {
        return disconnected;
    }
    public int getActive() {
        return active;
    }
    public ArrayList<Die> getDraft() {
        return draft;
    }
    public int[] getToolsUpdate() {
        return toolsUpdate;
    }
    public boolean itsMyTurn()
    {
        return myTurn;
    }
    public boolean isNextRound() {
        return nextRound;
    }
    public ArrayList<Die> getLastRound()
    {
        return lastRound;
    }
}
