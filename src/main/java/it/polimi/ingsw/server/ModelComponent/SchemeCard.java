package it.polimi.ingsw.server.ModelComponent;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

//COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
//NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

public class SchemeCard
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

    private Die diceScheme[][];

    /**
     * SchemeCard Constructor
     * @param i
     * @throws InvalidIntArgumentException
     */
    public SchemeCard(int i) throws InvalidIntArgumentException
    {

        if (i<0 || i>12)
            throw new InvalidIntArgumentException();

        fb=0;
        id=i;
        frontName="";
        backName="";
        frontDiff=0;
        backDiff=0;
        front = new int[4][5];
        back = new int[4][5];
        diceScheme = new Die[4][5];

        for(int j=0;j<4;j++)
            for(int z=0;z<5;z++)
            {
                front[j][z]=0;
                back[j][z]=0;
            }

        for(int j=0;j<4;j++)
            for(int z=0;z<5;z++)
            {
                diceScheme[j][z]= new Die(0);
                diceScheme[j][z].disableDie();
            }

    }

    /**
     * sets scheme's name
     * @param fb 1 for front, 2 for back
     * @param name scheme card's name
     * @throws InvalidIntArgumentException
     */
    public void setName(int fb, String name) throws InvalidIntArgumentException {


        if (fb==1)
            this.frontName=name;

        if (fb==2)
            this.backName=name;

        if (fb < 1 || fb > 2)
            throw new InvalidIntArgumentException();

    }

    /**
     * gets scheme's name
     * @param fb 1 for front, 2 for back
     * @return string with scheme's name
     * @throws InvalidIntArgumentException
     */
    public String getName(int fb) throws InvalidIntArgumentException {

        if (fb==1)
            return frontName;

        if (fb == 2)
            return backName;

        if (fb < 1 || fb > 2)
            throw new InvalidIntArgumentException();

        else
            return null;

    }

    /**
     * sets front / back
     * @param fb 1 for front, 2 for back
     * @throws InvalidIntArgumentException
     */
    public void setfb(int fb) throws InvalidIntArgumentException
    {
        if (fb==1)
            this.fb=1;

        if (fb==2)
            this.fb=2;

        if(fb==0)
            this.fb=0;
        if (fb < 0 || fb > 2)
            throw new InvalidIntArgumentException();

    }

    /**
     * gets front / back
     * @return integer with front / back
     */
    public int getfb()
    {

        return fb;

    }

    /**
     * sets a cell into the dice scheme
     * @param fb 1 for front, 2 for back
     * @param x row where to set the cell
     * @param y column where to set the cell
     * @param toInsert integer representing the number / color on the dice scheme
     * @throws InvalidIntArgumentException
     */
    public void setCell(int fb, int x, int y, int toInsert) throws InvalidIntArgumentException
    {

        if (x < 0 || x > 3 || y < 0 || y > 4 || toInsert < 0 || toInsert > 11)
            throw new InvalidIntArgumentException();

                if (fb == 1)
                    front[x][y] = toInsert;
                if (fb == 2)
                    back[x][y] = toInsert;

        if (fb < 1 || fb > 2)
            throw new InvalidIntArgumentException();

    }

    /**
     * gets cell
     * @param fb 1 for front, 2 for back
     * @param x row of the cell to get
     * @param y column of the cell to get
     * @return integer representing the number / color on the dice scheme
     * @throws InvalidIntArgumentException
     */

    public int getCell(int fb, int x, int y) throws InvalidIntArgumentException
    {

        if (x < 0 || x > 3 || y < 0 || y > 4 )
            throw new InvalidIntArgumentException();

        int temp=0;

        if(fb==1)
            temp = front[x][y];

        if(fb==2)
            temp = back[x][y];

        if (fb < 1 || fb > 2)
            throw new InvalidIntArgumentException();

        return temp;

    }

    /**
     * gets scheme card's id
     * @return id
     */
    public int getID()
    {
        return id;
    }

    /**
     * disables a scheme card
     */
    public void disableScheme() {

        if (!isDisabled)
            isDisabled=true;

    }

    /**
     * checks if a scheme card is disabled
     * @return true if the scheme is disabled, false if it's not
     */
    public boolean checkDisabled() {

        return isDisabled;

    }

    /**
     * sets difficulty to the scheme card
     * @param fb 1 for front, 2 for back
     * @param diff difficulty to set
     * @throws InvalidIntArgumentException
     */
    public void setDiff(int fb, int diff) throws InvalidIntArgumentException
    {

        if (diff < 0 || diff > 6)
            throw new InvalidIntArgumentException();

        if (fb==1)
            this.frontDiff = diff;
        if (fb==2)
            this.backDiff = diff;

        if (fb < 1 || fb > 2)
            throw new InvalidIntArgumentException();
    }

    /**
     * gets the scheme card's difficulty
     * @param fb 1 for front, 2 for back
     * @return integer representing the scheme card's difficulty
     * @throws InvalidIntArgumentException
     */
    public int getDiff(int fb) throws InvalidIntArgumentException
    {

        if (fb == 1)
            return this.frontDiff;

        if (fb == 2)
            return this.backDiff;

        if (fb < 1 || fb > 2)
            throw new InvalidIntArgumentException();

        else
            return 0;


    }

    /**
     * gets selected Die
     * @param x row where the selected die is
     * @param y column where the selected die is
     * @return the selected die
     * @throws InvalidIntArgumentException
     */
    public Die getDie(int x, int y) throws InvalidIntArgumentException
    {
        if (x < 0 || x > 3 || y < 0 || y > 4 )
            throw new InvalidIntArgumentException();


        return diceScheme[x][y];

    }

    /**
     * sets a precise die
     * @param toPlace die to place
     * @param x row where to place the die
     * @param y column where to place the die
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */
    public void setDie(Die toPlace, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (toPlace == null || toPlace.isDisabled())
            throw new GenericInvalidArgumentException();


        diceScheme[x][y] = toPlace;

    }

    /**
     * replaces a die with another one
     * @param toReplace die to replace
     * @param x row where to place the die
     * @param y column where to place the die
     * @return the old die
     * @throws InvalidIntArgumentException
     * @throws GenericInvalidArgumentException
     */
    public Die replaceDie(Die toReplace, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException {


        Die temp;

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (toReplace == null || toReplace.isDisabled() || diceScheme[x][y].isDisabled())
            throw new GenericInvalidArgumentException();

        temp = diceScheme[x][y];
        diceScheme[x][y] = toReplace;

        return temp;

    }

    /**
     * returns the number of present dice on the dice scheme
     * @return
     */
    public int getNumDice()
    {
        int temp=0;
        for(int x=0;x<4;x++)
            for(int y=0;y<5;y++)
                if(!diceScheme[x][y].isDisabled())
                    temp++;
        return temp;
    }

    /**
     * shifts a die from one position of the dice scheme to another
     * @param x0 row of the die to shift
     * @param y0 column of the die to shift
     * @param x1 row where to place the die
     * @param y1 column where to place the die
     * @throws InvalidIntArgumentException
     */
    public void shiftDie(int x0, int y0, int x1, int y1) throws InvalidIntArgumentException {

        int color = diceScheme[x0][y0].getColor();
        int value = diceScheme[x0][y0].getValue();

        Die tempDie = new Die(color);
        tempDie.setValue(value);

        diceScheme[x1][y1] = tempDie;
        diceScheme[x0][y0].disableDie();

    }


}
