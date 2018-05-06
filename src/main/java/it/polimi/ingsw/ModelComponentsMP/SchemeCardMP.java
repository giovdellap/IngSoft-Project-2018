package it.polimi.ingsw.ModelComponentsMP;

import it.polimi.ingsw.ModelComponentMP;

import java.util.*;

public class SchemeCardMP
{

    private int id;
    private String frontName;
    private String backName;
    private int frontDiff;
    private int backDiff;
    private int back[4][5];
    private int front[4][5];
    private DieMP diceScheme[4][5];

    public SchemeCardMP()
    {

        //costruttore
    }


    public int[4][5] getFront() {

        // ritorna il fronte

    }



    public int[4][5] getBack() {

        // ritorna il retro
    }


    public void setCell(int fb, int x, int y, int toInsert) {

        // imposta la cella
    }


    public int getCell(int fb, int x, int y) {

        // ritorna la cella
        return 0;
    }


    public int getID() {

        // ritorna l'ID
        return 0;
    }


    public void setDiff(int fb, int diff) {

        // imposta la difficoltà
    }


    public int getDiff(int fb, int diff) {

        // ritorna la difficoltà
        return 0;
    }


    public DieMP getDie(int fb, int x, int y) {

        // ritorna il dado
        return null;
    }


    public void setDie(DieMP toPlace, int x, int y) {

        // imposta il dado
    }

}