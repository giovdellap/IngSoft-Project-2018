package it.polimi.ingsw.server;

import java.util.*;

public class DraftPool
{
    private Die[] draft;
    private DiceContainer container;

    public DraftPool()
    {
        
    }

    public void pickUpDie(int index)
    {
        // elimina il dado dalla draft
    }

    public void updateDraft()
    {
        // update della draft all'inizio del round

    }

    public Die replaceDie(int index, Die toPlace)
    {
        // rimpiazza un dado nella draft
        return null;
    }

    public Die returnDie(int pos)
    {
        // ritorna il dado
        return null;
    }

}