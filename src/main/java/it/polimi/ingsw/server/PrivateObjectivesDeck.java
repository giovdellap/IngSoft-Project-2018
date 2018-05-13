package it.polimi.ingsw.server;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.*;

public class PrivateObjectivesDeck
{
    private PrivateObjective[] deck;

    public PrivateObjectivesDeck()
    {
        PrivateObjective[] deck = new PrivateObjective[5];
        for(int i=0;i<5;i++)
            deck[i] = new PrivateObjective(i+1);
    }

    public PrivateObjective[] extractPrObj(int n) throws InvalidIntArgumentException
    {
        if(n<2||n>4)
            throw new InvalidIntArgumentException();
        PrivateObjective[] temp = new PrivateObjective[n];
        for(int i=0;i<n;i++)
        {
            boolean flag=false;
            while(!flag) {
                System.out.println("check 1");
                int rand = (int)(Math.random()*4);
                System.out.println("check 2");
                flag=true;
                if(i>0) {
                    for (int p = 0; p < i; p++) {
                        System.out.println("check p: " + Integer.toString(p));
                        if (rand+1 == temp[p].getColor()) {
                            System.out.println("check if");
                            flag = false;
                        }
                    }
                }
                System.out.println("check flag: "+Boolean.toString(flag)+" i="+Integer.toString(i));
                if(flag)
                    temp[i] = new PrivateObjective(rand+1);
                System.out.println("temp[i] color = "+Integer.toString(temp[i].getColor()));
            }
        }
        return temp;

    }

}