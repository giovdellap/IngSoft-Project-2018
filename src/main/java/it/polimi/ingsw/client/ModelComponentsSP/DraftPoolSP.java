package it.polimi.ingsw.client.ModelComponentsSP;


import it.polimi.ingsw.client.ModelComponentSP;

public class DraftPoolSP implements ModelComponentSP {

    private DieSP[] draft;
    int dice;
    DiceContainer container;

    public DraftPoolSP(int players)
    {
        DiceContainer container = new DiceContainer();
        dice = players*2+1;
        DieSP[] draft = new DieSP[dice];
    }


    public void pickUpDie(int index)
    {
        //elimina il dado dalla draft
        for(int i=index; i<dice;i++)
        {
            if(draft[i]!=null)
                draft[i]=draft[i++];
        }
    }


    public void updateDraft()
    {
        // aggiorna il draft
        int dim=0;
        while(draft[dim]!=null)
        {
            dim++;
        }
        RoundDice temp = new RoundDice(dim);
        for(int i=0;i<dice;i++)
        {
            if(i<dim) {
                temp[i]=draft[i];
            }
            draft[i]=container.throwDice();
        }
    }


    public DieSP replaceDie(int index, DieSP toPlace)
    {
        // rimpiazza dado
        DieSP temp = draft[index];
        draft[index] = toPlace;
        return temp;
    }


    public DieSP returnDie(int pos)
    {
        // ritorna il Dado
        return draft[pos];
    }

}