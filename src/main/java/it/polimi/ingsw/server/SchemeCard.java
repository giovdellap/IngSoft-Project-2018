package it.polimi.ingsw.server;

import java.util.*;

public class SchemeCard
{
    private int id;
    private String frontName;
    private String backName;
    private int frontDiff;
    private int backDiff;
    private int back[][];
    private int front[][];
    private Die diceScheme[][];

    public SchemeCard()
    {

    }

    public int[][] getFront()
    {
        // ritorna lo schema fronte
        return null;
    }

    public int[][] getBack()
    {
        // ritorna lo schema retro
        return null;
    }

    public void setCell(int fb, int x, int y, int toInsert)
    {
        // imposta il contenuto della cella x,y
    }

    public int getCell(int fb, int x, int y)
    {
        // ritorna il contenuto della cella x,y
        return 0;
    }

    public void getID()
    {
        // ritorna l'id
    }

    public void setDiff(int fb, int diff)
    {
        // imposta la difficoltà dello schema
    }

    public int getDiff(int fb, int diff)
    {
        // ritorna la difficoltà
        return 0;
    }

    public Die getDie(int fb, int x, int y)
    {
        // ritorna il dado in posizione x,y
        return null;
    }

    public void setDie(Die toPlace, int x, int y)
    {
        // posiziona il dado passato come parametro in posizione x,y
    }

}