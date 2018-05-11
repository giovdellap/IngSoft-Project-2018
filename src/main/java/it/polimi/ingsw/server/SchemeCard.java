package it.polimi.ingsw.server;

import java.util.*;

public class SchemeCard
{
    private int id;
    private String frontName;
    private String backName;
    private int frontDiff;
    private int backDiff;
    private int fb;

    private int front[][];
    private int back[][];

    private Die diceScheme[][];

    public SchemeCard(int i)
    {
        fb=0;
        id=i;
        frontName="";
        backName="";
        frontDiff=0;
        backDiff=0;
        front = new int[4][5];
        back = new int[4][5];

        for(int j=0;j<4;j++)
            for(int z=0;z<5;z++)
            {
                front[j][z]=0;
                back[j][z]=0;
            }

        for(int j=0;j<4;j++)
            for(int z=0;z<5;z++)
            {
                diceScheme[j][z]=null;
            }
    }

    public int[][] getFront()
    {
        int[][] temp = new int[4][5];
        for(int i=0;i<4;i++)
            for(int j=0;j<5;j++)
            {
                temp[i][j] = front[i][j];
            }
        return temp;

    }

    public int[][] getBack()
    {
        int[][] temp = new int[4][5];
        for(int i=0;i<4;i++)
            for(int j=0;j<5;j++)
            {
                temp[i][j] = back[i][j];
            }
        return temp;
    }

    public void setfb(int fb)
    {
        if (fb==1)
            this.fb=1;

        if (fb==2)
            this.fb=2;

    }

    public int getfb()
    {

        return fb;

    }


    public void setCell(int fb, int x, int y, int toInsert)
    {
        //fronte: fr=1 / retro: fr=2

        if(fb==1)
            front[x][y]=toInsert;
        if(fb==2)
            back[x][y]=toInsert;

    }

    public int getCell(int fb, int x, int y)
    {
        //fronte: fr=1 / retro: fr=2

        int temp=0;

        if(fb==1)
            temp = front[x][y];

        if(fb==2)
            temp = back[x][y];

        return temp;

    }

    public int getID()
    {
        return id;
    }

    public void setDiff(int fb, int diff)
    {
        //fronte: fr=1 / retro: fr=2

        if (fb==1)
            this.frontDiff = diff;
        if (fb==2)
            this.backDiff = diff;
    }

    public int getDiff(int fb) {
        //fronte: fr=1 / retro: fr=2

        if (fb == 1)
            return this.frontDiff;

       else if (fb == 2)
            return this.backDiff;
                else
                    return 0;


    }



    public Die getDie(int x, int y)
    {
        // ritorna il dado in posizione x,y
        return diceScheme[x][y];

    }


    public void setDie(Die toPlace, int x, int y)
    {

        // posiziona il dado passato come parametro in posizione x,y

        if (toPlace!=null) {
            if (x > 0 && x < 4) {
                if (y > 0 && y < 5) {
                    if (diceScheme[x][y]==null)
                        diceScheme[x][y] = toPlace;

                }

            }
        }
    }

}
