package it.polimi.ingsw.client.PackageSP.ModelComponentsSP;

public class DiceContainer {

    private DieSP[] dice;

    public DiceContainer()
    {
        dice = new DieSP[90];
        for(int i=0;i<18;i++)
        {
            dice[i]= new DieSP(1);
            dice[i+18]=new DieSP(2);
            dice[i+36]=new DieSP(3);
            dice[i+54]=new DieSP(4);
            dice[i+72]=new DieSP(5);
        }
    }


    public DieSP throwDice()
    {
        // lancia il dado
        boolean flag = false;
        int index=0;
        while(!flag)
        {
            index=(int)(Math.random() * 89 + 1);
            if(dice[index].getColor()!=0)
                flag=true;
        }
        DieSP tempDie = dice[index];
        dice[index].disableDie();
        return tempDie;
    }

}