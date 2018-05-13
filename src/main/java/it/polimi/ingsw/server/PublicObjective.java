package it.polimi.ingsw.server;

import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.*;

public class PublicObjective
{
    private int id;
    private int bonus;
    private boolean isDisabled= false;

    public PublicObjective(int id) throws InvalidIntArgumentException
    {

        if (id<0 || id>10)
            throw new InvalidIntArgumentException();

        this.id=id;
        this.bonus=0;

    }

    public int getId() {

        return id;
    }

    public int getBonus() {

        return bonus;
    }


    public void disableScheme() {

        if (!isDisabled)
            isDisabled=true;

    }

    public boolean checkDisabled() {

        return isDisabled;

    }


    public int setBonus(SchemeCard scheme) throws InvalidIntArgumentException {

        if (id==1) {
            bonus = calculateOne(scheme);
        }

        if (id==2) {
            this.bonus = 5;
        }

        if (id==3) {
            this.bonus = 5;
        }

        if (id==4) {
            this.bonus = 4;
        }

        if (id==5) {
            this.bonus = 2;
        }

        if (id==6) {
            this.bonus = 2;
        }

        if (id==7) {
            this.bonus = 2;
        }

        if (id==8) {
            this.bonus = 5;
        }

        if (id==9) {
            this.bonus = 0;   // # TO DO
        }

        if (id==10) {
            this.bonus = 4;
        }

        return bonus;
    }

    public String toString() {

        int tempID = this.getId();
        int tempBonus = this.getBonus();
        String list = "";


        list = "Id" + " " + tempID + " " + "Bonus" + " " + tempBonus;

        return list;

        }


    private int calculateOne(SchemeCard scheme) throws InvalidIntArgumentException {

        SchemeCard tempScheme = scheme;
        int bonus = 0;
        int[] temp = new int[5];

        for(int i=0;i<4;i++)
        {
            for(int j=0;j<5;j++)
            {
                temp[j]=1;
            }

            boolean flag=true;

            for(int k=0;k<5;k++)
            {
                if(temp[scheme.getDie(i,k).getColor()-1]==0)
                    flag = false;

                temp[scheme.getDie(i,k).getColor()-1]=0;
            }

            if(flag)
                bonus=bonus+6;

        }

        return bonus;
    }

    private int calculateTwo(SchemeCard scheme) throws InvalidIntArgumentException {
        int bonus=0;
        int[] temp = new int[5];
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<4;j++)
                temp[j]=1;
            for(int z=0;z<4;z++)
                temp[scheme.getDie(i,z).getColor()-1]=0;
            int check=2;
            for(int j=0;j<4;j++)
                if(temp[j]==1)
                    check--;
            if(check==1)
                bonus=bonus+5;
        }
        return bonus;
    }

    private int calculateThree(SchemeCard scheme) throws InvalidIntArgumentException {
        int bonus=0;
        int[] temp = new int[6];
        for(int i=0;i<4;i++)
        {
            for(int j=0;j<6;j++)
                temp[j]=1;
            for(int j=0;j<5;j++)
                temp[scheme.getDie(i,j).getValue()-1]=0;
            int test=2;
            for(int j=0;j<6;j++)
                if(temp[j]==1)
                    test--;
            if(test==1)
                bonus=bonus+5;
        }
        return bonus;
    }

    private int calculateFour(SchemeCard scheme) throws InvalidIntArgumentException {
        int bonus=0;
        int[] temp = new int[6];
        for(int i=0;i<5;i++)
        {
            for(int j=0;j<6;j++)
                temp[j]=1;
            for(int j=0;j<4;j++)
                temp[scheme.getDie(j,i).getValue()-1]=0;
            int test=3;
            for(int j=0;j<6;j++)
                if(temp[j]==1)
                    test--;
            if(test==1)
                bonus=bonus+4;
        }
        return bonus;
    }
}


