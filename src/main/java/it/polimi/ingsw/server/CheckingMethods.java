package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;

public class CheckingMethods
{

    public CheckingMethods()
    {

    }

    public void checkToolUsed(int player, int toolId, SchemeCard tempScheme)
    {
        // controlla se il tool viene usato in maniera corretta
    }

    public void checkToolUsed(int player, int toolId, SchemeCard tempScheme, DraftPool draft)
    {
        // controlla se il tool viene usato in maniera corretta (modifica draftpool)
    }

    public void checkMove(int player, SchemeCard scheme, DraftPool draft)
    {
        // controlla la validità della mossa
    }

}