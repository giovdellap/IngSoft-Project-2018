package it.polimi.ingsw.clientOld.ModelComponentsSP;


public class SchemeCardSP {

    private int id;
    private String frontName;
    private String backName;
    private int frontDiff;
    private int backDiff;
    private int back[][];
    private int front[][];
    private DieSP diceScheme[][];


    public SchemeCardSP()
    {
        //costruttore

    }



    public void getFront()
    {
        // ritorna il fronte dello schema (da mettere tipo ritorno vettore bidimensionale)
    }


    public void getBack()
    {
        // ritorna il retro dello schema (da mettere tipo ritorno vettore bidimensionale)
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


    public void getDie(int fb, int x, int y) {

        // ritorna il dado (da mettere tipo ritorno DieSP)

    }


    public void setDie(DieSP toPlace, int x, int y) {

        // imposta la posizione del dado

    }

}