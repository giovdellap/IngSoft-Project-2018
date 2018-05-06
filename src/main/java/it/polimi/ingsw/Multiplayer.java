package it.polimi.ingsw;

import it.polimi.ingsw.ModelComponentsMP.*;

import java.util.*;

public class Multiplayer {

    private DraftPoolMP draft;
    private ToolMP[] toolPool;
    private SchemeCardMP mainScheme;
    private SchemeCardMP otherScheme;
    private SchemeCardMP oppSchemes;
    private PrivateObjectiveMP privObj;
    private PublicObjectiveMP[] publObj;
    private int favorTokens;
    private int oppFavTokens;
    private String oppNames;
    private int round;
    private int turn;

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
