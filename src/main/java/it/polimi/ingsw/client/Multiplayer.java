package it.polimi.ingsw.client;


<<<<<<< HEAD
import it.polimi.ingsw.client.ModelComponentsMP.*;
=======
import it.polimi.ingsw.client.ModelComponentsMP.DraftPoolMP;
import it.polimi.ingsw.client.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.ModelComponentsMP.SchemeCardMP;
import it.polimi.ingsw.client.ModelComponentsMP.ToolMP;
>>>>>>> 8450c4b95595264bc4cf0238cd708dfb324729ab

public class Multiplayer {

    private DraftPoolMP draft;
<<<<<<< HEAD
    private ToolsDeckMP toolPool;
    private SchemeCardMP mainScheme;
    private SchemeCardMP otherScheme;
    private SchemeCardMP[] oppSchemes;
    private PrivateObjectiveMP privObj;
    private int favorTokens;
    private int[] oppFavTokens;
    private String[] oppNames;
    private int round;
    private int turn;
    private int players;
=======
    private ToolMP[] toolPool;
    private SchemeCardMP mainScheme;
    private SchemeCardMP otherScheme;
    private SchemeCardMP oppSchemes;
    private PrivateObjectiveMP privObj;
    private int favorTokens;
    private int oppFavTokens;
    private String oppNames;
    private int round;
    private int turn;
>>>>>>> 8450c4b95595264bc4cf0238cd708dfb324729ab

    public Multiplayer()
    {

        //costruttore
    }



    public void play() {

        // metodo per giocare
    }


    public void myTurn() {

        // gestisce il mio turno
    }


    public void oppTurn() {

        // gestisce il turno dell'avversario
    }


    public void endOfTheGame() {

        // gestisce la fine del gioco
    }


    public void takeTokens(int n) {

        // ottiene i token
    }

}
