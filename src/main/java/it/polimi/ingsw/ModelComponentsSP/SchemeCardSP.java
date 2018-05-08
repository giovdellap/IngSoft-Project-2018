package it.polimi.ingsw.ModelComponentsSP;

import java.util.*;




public class SchemeCardSP {

    private int id;
    private String frontName;
    private String backName;
    private int frontDiff;
    private int backDiff;
    private int back[4][5];
    private int front[4][5];
    private DieSP diceScheme[4][5];


    public SchemeCardSP()
    {
        //costruttore

    }



    public int[4][5] getFront()
    {
        // ritorna il fronte dello schema
    }


    public int[4][5] getBack()
    {
        // ritorna il retro dello schema
    }


    public void setCell(int fb,int x, int y, int toInsert) {

        // imposta la cella

    }



    public int getCell(int fb, int x, int y) {

        // ritorna la cella
        return 0;
    }



    public void getID() {

        // ritorna l'ID dello schema

    }


    public void setDiff(int fb, int diff) {

        // imposta la difficoltà

    }


    public int getDiff(int fb, int diff) {

        // ritorna la difficoltà
        return 0;
    }


    public DieSP getDie(int fb, int x, int y) {

        // ritorna il dado

    }


    public void setDie(DieSP toPlace, int x, int y) {

        // imposta la posizione del dado

    }

}