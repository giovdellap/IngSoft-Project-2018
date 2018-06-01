package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;

import it.polimi.ingsw.client.ClientExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.PackageMP.ModelComponentMP;

public class PublicObjectiveMP implements ModelComponentMP{
    //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
    //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX


    private int id;
    private int bonus;
    private boolean isDisabled = false;

    public PublicObjectiveMP(int id) throws InvalidIntArgumentException {
        if (id < 0 || id > 10)
            throw new InvalidIntArgumentException();
        this.id = id;
        this.bonus = 0;
    }

    public int getId()
    {
        return id;
    }

    public int getBonus()
    {
        return bonus;
    }


    public void disableScheme()
    {
        if (!isDisabled)
            isDisabled = true;
    }

    public boolean checkDisabled()
    {
        return isDisabled;
    }


    public int setBonus(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        if (id == 1) {
            bonus = calculateOne(scheme);
        }

        if (id == 2) {
            bonus = calculateTwo(scheme);
        }

        if (id == 3) {
            bonus = calculateThree(scheme);
        }

        if (id == 4) {
            bonus = calculateFour(scheme);
        }

        if (id == 5) {
            bonus = calculateFive(scheme);
        }

        if (id == 6) {
            bonus = calculateSix(scheme);
        }

        if (id == 7) {
            bonus = calculateSeven(scheme);
        }

        if (id == 8) {
            bonus = calculateEight(scheme);
        }

        if (id == 9) {
            bonus = calculateNine(scheme);
        }

        if (id == 10) {
            bonus = calculateTen(scheme);
        }

        return bonus;
    }

    public String toString()
    {
        int tempID = this.getId();
        int tempBonus = this.getBonus();
        String list = "";


        list = "Id" + " " + tempID + " " + "Bonus" + " " + tempBonus;

        return list;
    }

    private int calculateOne(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        if (scheme==null)
            throw new GenericInvalidArgumentException();

        int bonus = 0;
        int[] temp = new int[5];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 5; j++) {
                temp[j] = 1;
            }

            boolean flag = true;

            for (int k = 0; k < 5; k++) {
                if (scheme.getDie(i, k).getColor() != 0) {
                    if (temp[scheme.getDie(i, k).getColor() - 1] == 0)
                        flag = false;

                    temp[scheme.getDie(i, k).getColor() - 1] = 0;
                }

                if (scheme.getDie(i, k).getColor() == 0)
                    flag = false;

            }

            if (flag)
                bonus = bonus + 6;

        }

        return bonus;
    }


    private int calculateTwo(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        if (scheme==null)
            throw new GenericInvalidArgumentException();

        int bonus = 0;
        int[] temp = new int[5];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++)
                temp[j] = 1;
            boolean flag = true;
            for (int z = 0; z < 4; z++) {

                if (scheme.getDie(z, i).getColor() != 0)
                    temp[scheme.getDie(z, i).getColor() - 1] = 0;
                if (scheme.getDie(z, i).getColor() == 0)
                    flag = false;
            }
            int check = 2;

            for (int j = 0; j < 5; j++)
                if (temp[j] == 1)
                    check--;

            if (check == 1 && flag)
                bonus = bonus + 5;

        }
        return bonus;
    }


    private int calculateThree(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        if (scheme==null)
            throw new GenericInvalidArgumentException();

        int bonus = 0;
        int[] temp = new int[6];

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 6; j++)
                temp[j] = 1;
            for (int j = 0; j < 5; j++)
                if (scheme.getDie(i, j).getColor() != 0)
                    temp[scheme.getDie(i, j).getValue() - 1] = 0;
            int test = 2;
            for (int j = 0; j < 6; j++)
                if (temp[j] == 1)
                    test--;
            if (test == 1)
                bonus = bonus + 5;
        }
        return bonus;
    }

    private int calculateFour(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        if (scheme==null)
            throw new GenericInvalidArgumentException();

        int bonus = 0;
        int[] temp = new int[6];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 6; j++)
                temp[j] = 1;
            for (int j = 0; j < 4; j++)
                if (scheme.getDie(j, i).getColor() != 0)
                    temp[scheme.getDie(j, i).getValue() - 1] = 0;

            int test = 3;
            for (int j = 0; j < 6; j++)
                if (temp[j] == 1)
                    test--;
            if (test == 1)
                bonus = bonus + 4;
        }
        return bonus;
    }


    private int calculateFive(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        if (scheme==null)
            throw new GenericInvalidArgumentException();

        int bonus = 0;
        int cont1 = 0;
        int cont2 = 0;

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                if (scheme.getDie(i, j).getValue() == 1)
                    cont1++;
                if (scheme.getDie(i, j).getValue() == 2)
                    cont2++;

            }
        }

        if (cont1 < cont2) {
            bonus = cont1 * 2;
            return bonus;
        }

        if (cont2 < cont1) {
            bonus = cont2 * 2;
            return bonus;
        }

        else {
            bonus = cont1 * 2;
            return bonus;
        }
    }


    private int calculateSix(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        if (scheme==null)
            throw new GenericInvalidArgumentException();

        int bonus = 0;
        int cont3 = 0;
        int cont4 = 0;

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                if (scheme.getDie(i, j).getValue() == 3)
                    cont3++;
                if (scheme.getDie(i, j).getValue() == 4)
                    cont4++;

            }
        }

        if (cont3 < cont4) {
            bonus = cont3 * 2;
            return bonus;
        }

        if (cont4 < cont3) {
            bonus = cont4 * 2;
            return bonus;
        }

        else {
            bonus = cont3 * 2;
            return bonus;
        }
    }


    private int calculateSeven(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        if (scheme==null)
            throw new GenericInvalidArgumentException();

        int bonus = 0;
        int cont5 = 0;
        int cont6 = 0;

        for (int i = 0; i < 4; i++) {

            for (int j = 0; j < 5; j++) {

                if (scheme.getDie(i, j).getValue() == 5)
                    cont5++;
                if (scheme.getDie(i, j).getValue() == 6)
                    cont6++;

            }
        }

        if (cont5 < cont6) {
            bonus = cont5 * 2;
            return bonus;
        }

        if (cont6 < cont5) {
            bonus = cont6 * 2;
            return bonus;
        }

        else {
            bonus = cont5 * 2;
            return bonus;
        }

    }


    private int calculateEight(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        if (scheme==null)
            throw new GenericInvalidArgumentException();

        int bonus = 0;
        int min = 3;
        int[] temp = new int[6];

        for (int i=0;i<6;i++)
            temp[i]=0;

        for (int i=0;i<4;i++) {
            for(int j=0;j<5;j++) {
                if (scheme.getDie(i, j).getValue() != 0)
                    temp[scheme.getDie(i,j).getValue()-1]+=1;
            }
        }

        for(int z=0;z<6;z++) {
            if (temp[z]<=min)
                min = temp[z];
        }

        if (min==0)
            return bonus;

        else {
            bonus = 5 * min;
            return bonus;
        }
    }


    private int calculateNine(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        int bonus=0;

        if (scheme==null)
            throw new GenericInvalidArgumentException();

        SchemeCardMP temp = scheme;

        for(int x=0;x<4;x++)
            for(int y=0;y<5;y++)
            {
                boolean flag=false;
                if(!temp.getDie(x, y).isDisabled()) {

                    int color=temp.getDie(x,y).getColor();

                    if((x+1>-1 && x+1<4 && y-1>-1 && y-1<5 && (temp.getDie(x+1, y-1)!=null) && (flag==false) && color==temp.getDie(x+1,y-1).getColor())) {
                        bonus++;
                        flag=true;
                    }
                    if(x+1>-1 && x+1<4 && y+1>-1 && y+1<5 && (temp.getDie(x+1, y+1)!=null) && flag==false && color==temp.getDie(x+1,y+1).getColor())
                    {
                        flag=true;
                        bonus++;
                    }
                    if(x-1>-1 && x-1<4 && y+1>-1 && y+1<5 && (temp.getDie(x-1, y+1)!=null) && flag==false && color==temp.getDie(x-1,y+1).getColor())
                    {
                        flag=true;
                        bonus++;
                    }
                    if(x-1>-1 && x-1<4 && y-1>-1 && y-1<5 && (temp.getDie(x-1,y-1)!=null) && flag==false && color==temp.getDie(x-1,y-1).getColor())
                    {
                        flag=true;
                        bonus++;
                    }
                    temp.getDie(x,y).disableDie();
                }
            }
        return bonus;
    }


    private int calculateTen(SchemeCardMP scheme) throws GenericInvalidArgumentException, InvalidIntArgumentException
    {
        if (scheme==null)
            throw new GenericInvalidArgumentException();

        int bonus = 0;
        int min = 4;
        int[] temp = new int[5];

        for (int i=0;i<5;i++)
            temp[i]=0;

        for (int i=0;i<4;i++) {
            for(int j=0;j<5;j++) {
                if (scheme.getDie(i, j).getColor() != 0)
                    temp[scheme.getDie(i,j).getColor()-1]+=1;
            }
        }

        for(int z=0;z<5;z++) {
            if (temp[z]<=min)
                min = temp[z];
        }

        if (min==0)
            return bonus;

        else {
            bonus = 4 * min;
            return bonus;
        }
    }
}
