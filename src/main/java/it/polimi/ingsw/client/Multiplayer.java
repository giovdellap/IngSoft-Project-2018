package it.polimi.ingsw.client;


import it.polimi.ingsw.client.ModelComponentsMP.*;

public class Multiplayer {

    private DraftPoolMP draft;
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
