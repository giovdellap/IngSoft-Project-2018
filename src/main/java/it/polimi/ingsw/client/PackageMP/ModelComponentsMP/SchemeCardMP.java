package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;

import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;

//COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
//NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

public class SchemeCardMP
{
    private int id;
    private String frontName;
    private String backName;
    private int frontDiff;
    private int backDiff;
    private int fb;
    private boolean isDisabled = false;
    private int front[][];
    private int back[][];
    private DieMP diceScheme[][];

    public SchemeCardMP(int i) throws InvalidIntArgumentException
    {
        if (i<1 || i>12) throw new InvalidIntArgumentException();

        fb=0;
        id=i;
        frontName="";
        backName="";
        frontDiff=0;
        backDiff=0;
        front = new int[4][5];
        back = new int[4][5];
        diceScheme = new DieMP[4][5];

        for(int j=0;j<4;j++)
            for(int z=0;z<5;z++)
            {
                front[j][z]=0;
                back[j][z]=0;
            }

        for(int j=0;j<4;j++)
            for(int z=0;z<5;z++)
            {
                diceScheme[j][z]= new DieMP(0);
                diceScheme[j][z].disableDie();
            }

    }

    public void setName(int fb, String name) throws InvalidIntArgumentException
    {
        if (fb==1) this.frontName=name;

        if (fb==2) this.backName=name;

        if (fb < 1 || fb > 2) throw new InvalidIntArgumentException();
    }

    public String getName(int fb) throws InvalidIntArgumentException
    {
        if (fb==1) return frontName;

        if (fb == 2) return backName;

        if (fb < 1 || fb > 2) throw new InvalidIntArgumentException();

        else return null;
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

    public void setfb(int fb) throws InvalidIntArgumentException
    {
        if (fb==1) this.fb=1;

        if (fb==2) this.fb=2;

        if (fb < 1 || fb > 2) throw new InvalidIntArgumentException();

    }

    public int getfb()
    {
        return fb;
    }

    public void setCell(int fb, int x, int y, int toInsert) throws InvalidIntArgumentException
    {
        //fronte: fr=1 / retro: fr=2

        if (x < 0 || x > 3 || y < 0 || y > 4 || toInsert < 0 || toInsert > 11) throw new InvalidIntArgumentException();

        if (fb == 1) front[x][y] = toInsert;

        if (fb == 2) back[x][y] = toInsert;

        if (fb < 1 || fb > 2) throw new InvalidIntArgumentException();
    }

    public int getCell(int fb, int x, int y) throws InvalidIntArgumentException
    {
        //fronte: fr=1 / retro: fr=2

        if (x < 0 || x > 3 || y < 0 || y > 4 ) throw new InvalidIntArgumentException();

        int temp=0;

        if(fb==1) temp = front[x][y];

        if(fb==2) temp = back[x][y];

        if (fb < 1 || fb > 2) throw new InvalidIntArgumentException();

        return temp;

    }

    public int getID()
    {
        return id;
    }

    public void disableScheme()
    {
        if (!isDisabled)
            isDisabled=true;
    }

    public boolean checkDisabled()
    {
        return isDisabled;
    }


    public void setDiff(int fb, int diff) throws InvalidIntArgumentException
    {
        //fronte: fr=1 / retro: fr=2

        if (diff < 0 || diff > 6) throw new InvalidIntArgumentException();

        if (fb==1) this.frontDiff = diff;

        if (fb==2) this.backDiff = diff;

        if (fb < 1 || fb > 2) throw new InvalidIntArgumentException();
    }

    public int getDiff(int fb) throws InvalidIntArgumentException
    {
        //fronte: fr=1 / retro: fr=2

        if (fb == 1) return this.frontDiff;

        if (fb == 2) return this.backDiff;

        if (fb < 1 || fb > 2) throw new InvalidIntArgumentException();

        else return 0;

    }


    public DieMP getDie(int x, int y) throws InvalidIntArgumentException
    {
        if (x < 0 || x > 3 || y < 0 || y > 4 )
            throw new InvalidIntArgumentException();

        return diceScheme[x][y];
    }


    public void setDie(DieMP toPlace, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException
    {
        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (toPlace == null || toPlace.isDisabled() || !diceScheme[x][y].isDisabled())
            throw new GenericInvalidArgumentException();

        diceScheme[x][y] = toPlace;
    }


    public DieMP replaceDie(DieMP toReplace, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException
    {
        DieMP temp;

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (toReplace == null || toReplace.isDisabled() || diceScheme[x][y].isDisabled())
            throw new GenericInvalidArgumentException();

        temp = diceScheme[x][y];
        diceScheme[x][y] = toReplace;

        return temp;
    }

    public int getNumDice()
    {
        int temp=0;
        for(int x=0;x<4;x++)
            for(int y=0;y<5;y++)
                if(!diceScheme[x][y].isDisabled())
                    temp++;
        return temp;
    }
}