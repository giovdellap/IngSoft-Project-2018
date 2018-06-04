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
}
