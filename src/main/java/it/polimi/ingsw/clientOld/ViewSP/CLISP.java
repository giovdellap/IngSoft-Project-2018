package it.polimi.ingsw.clientOld.ViewSP;


import it.polimi.ingsw.clientOld.ModelComponentsSP.DraftPoolSP;
import it.polimi.ingsw.clientOld.ModelComponentsSP.SchemeCardSP;
import it.polimi.ingsw.clientOld.SinglePackage.SPViewInterface;

public class CLISP implements SPViewInterface
{
    public CLISP()
    {
        //Costruttore
    }

    public void setPrivObjs(int id1, int id2) {

        // imposta l'obbiettivo privato

    }


    public void setPossibleSchemes(int id1, int id2) {

        // imposta gli schemi da cui sceglierne uno
    }


    public void setPublicObjectives(int id1, int id2)
    {

        // imposta gli obbiettivi pubblici
    }


    public void setRound(int round)
    {

        // imposta il round
    }


    public void setTurn(int turn)
    {

        // imposta il turno
    }


    public void refreshDraftPool(DraftPoolSP dp)
    {

        // aggiorna la draft pool
    }


    public void setScore(int score) {

        // imposta lo score
    }


    public void move() {

        // gestisce la mossa
    }


    public void useTool() {

        // utilizza uno strumento
    }


    public void refreshScheme(SchemeCardSP sc) {

        // aggiorna lo Schema
    }



    public void getScheme(int id, int fb) {

        // ritorna lo schema
    }


    public void getDiff(int diff) {

        // ritorna la difficoltà
    }
}
