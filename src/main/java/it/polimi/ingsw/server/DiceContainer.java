package it.polimi.ingsw.server;

import java.util.*;

public class DiceContainer
{
    private Die[] dice;

    public DiceContainer()
    {
        dice = new Die[90];
        for(int i=0;i<18;i++)
        {
            dice[i]= new Die(1);
            dice[i+18]=new Die(2);
            dice[i+36]=new Die(3);
            dice[i+54]=new Die(4);
            dice[i+72]=new Die(5);
        }
    }

    public Die[] throwDice(int n)
    {
        // tira n dadi dal sacchetto
        Die[] tempVector = new Die[n];
        boolean flag = false;
        int index=0;
        for(int i=0;i<n;i++)
        {
            while (!flag)
            {
                index = (int) (Math.random() * 89 + 1);
                if (dice[index].getColor() != 0)
                    flag = true;
            }
            Die tempDie = dice[index];
            tempVector[i]=tempDie;
            dice[index].disableDie();

        }

        return tempVector;
    }



}