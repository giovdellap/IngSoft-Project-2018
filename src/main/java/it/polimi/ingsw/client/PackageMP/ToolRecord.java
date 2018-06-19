package it.polimi.ingsw.client.PackageMP;

public class ToolRecord
{
    private int[] id;
    private int[] tokens;

    public ToolRecord(int[] tools)
    {
        id=tools;
        tokens = new int[3];
    }

    public void setTokens(int[] tokens)
    {
        this.tokens=tokens;
    }
    public int[] getTokens() { return tokens; }
    public int[] getID() { return id; }

    public int getTokensDecrease(int toolId)
    {
        int c = getIndexbyId(toolId);

        if(tokens[c]==0)
            return 1;
        else
            return 2;

    }

    public int getIndexbyId(int id)
    {
        for(int i=0;i<3;i++)
        {
            if(this.id[i]==id)
                return i;
        }
        return  0;
    }
}
