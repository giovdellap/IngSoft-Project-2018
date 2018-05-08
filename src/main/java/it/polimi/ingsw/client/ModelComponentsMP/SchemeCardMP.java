package it.polimi.ingsw.client.ModelComponentsMP;


import java.util.*;

public class SchemeCardMP
{

    private int id;
    private String frontName;
    private String backName;
    private int frontDiff;
    private int backDiff;
    private int back[][];
    private int front[][];
    private DieMP diceScheme[][];

    public SchemeCardMP()
    {

        //costruttore
    }


    public void getFront() {

        // ritorna il fronte (da mettere tipo ritorno vettore bidimensionale)


    }



    public void getBack() {

        // ritorna il retro (da mettere tipo ritorno vettore bidimensionale)

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