package it.polimi.ingsw.client.PackageMP;

public class ToolRecord
{
    private int[] id;
    private int[] tokens;

    /**
     * ToolRecord constructor
     * @param tools
     */
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

    /**
     * decreases number of tokens
     * @param toolId toolsId
     * @return 1 if tokens are finished, else 2
     */
    public int getTokensDecrease(int toolId)
    {
        int c = getIndexbyId(toolId);

        if(tokens[c]==0)
            return 1;
        else
            return 2;

    }

    /**
     * gets index by id
     * @param id
     * @return
     */
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
