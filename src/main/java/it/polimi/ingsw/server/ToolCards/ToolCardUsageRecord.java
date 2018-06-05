package it.polimi.ingsw.server.ToolCards;

public class ToolCardUsageRecord
{
    private int[] selectedId;
    private int[] tokens;

    public ToolCardUsageRecord()
    {
        tokens = new int[3];
        for (int i=0;i<3;i++)
            tokens[i]=0;

        int extracted=0;
        selectedId = new int[3];
        while(extracted<3)
        {
            int i = (int)(Math.random()*12+1);
            boolean flag=false;
            for(int n=0;n<extracted;n++)
                if(selectedId[n]==i)
                    flag=true;
            if(flag==false)
            {
                selectedId[extracted]=i;
                extracted++;
            }
        }
    }

    public int checkAndApplyUsage(int playerTokens, int toolCard)
    {
        //returns 0 if player cannot use that tool, 1 or 2 if player can use it

        if (tokens[toolCard]==0&&playerTokens>0)
        {
            tokens[toolCard]=1;
            return 1;
        }
        if(tokens[toolCard]>0&&playerTokens>1)
        {
            tokens[toolCard]=tokens[toolCard]+2;
            return 2;
        }
        else
            return 0;
    }

    public int[] getRecord()
    {
        return tokens;
    }

    public int[] getSelectedId() {
        return selectedId;
    }
}
