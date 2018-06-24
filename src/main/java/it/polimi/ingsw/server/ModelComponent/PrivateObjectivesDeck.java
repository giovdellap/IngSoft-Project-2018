package it.polimi.ingsw.server.ModelComponent;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class PrivateObjectivesDeck
{
    /**
     * PrivateObjectivesDeck Constructor
     */
    public PrivateObjectivesDeck()
    {
        PrivateObjective[] deck = new PrivateObjective[5];
        for(int i=0;i<5;i++)
            deck[i] = new PrivateObjective(i+1);
    }

    /**
     * extracts four casual private objectives
     * @param n private objectives array dimension
     * @return private objectives array
     * @throws InvalidIntArgumentException
     */
    public PrivateObjective[] extractPrObj(int n) throws InvalidIntArgumentException
    {
        if(n<2||n>4)
            throw new InvalidIntArgumentException();
        PrivateObjective[] temp = new PrivateObjective[n];
        for(int i=0;i<n;i++)
        {
            boolean flag=false;
            while(!flag) {
                int rand = (int)(Math.random()*5+1);
                flag=true;
                if(i>0)
                    for (int p = 0; p < i; p++)
                        if (rand == temp[p].getColor())
                            flag = false;
                if(flag)
                    temp[i] = new PrivateObjective(rand);
            }
        }
        return temp;

    }

}