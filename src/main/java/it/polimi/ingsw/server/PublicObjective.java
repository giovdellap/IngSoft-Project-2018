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
        this.bonus=setBonus();

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


    public int setBonus() {

        if (id==1) {
            this.bonus = 6;
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
}