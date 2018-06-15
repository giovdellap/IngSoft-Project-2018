package it.polimi.ingsw.commons.Events.Initialization;

import it.polimi.ingsw.commons.Events.Event;

public class ModelInitializationEvent extends Event {

    private int schemeId1;
    private int schemeId2;
    private int privObj;
    private int[] pubObjs;
    private int[] toolIds;


    public ModelInitializationEvent() {
        super("ModelInitializationEvent");
    }

    //SET METHODS
    public void setSchemes(int id1, int id2)
    {
        schemeId1=id1;
        schemeId2=id2;
    }
    public void setPrivateObjective(int id)
    {
        privObj=id;
    }
    public void setPublicObjectives(int[] ids)
    {
        pubObjs=ids;
    }
    public void setToolIds(int[] tools)
    {
        toolIds=tools;
    }

    //GET METHODS
    public int[] getSchemes()
    {
        int[] temp = new int[2];
        temp[0]=schemeId1;
        temp[1]=schemeId2;
        return temp;
    }
    public int getPrivateObjective()
    {
        return privObj;
    }
    public int[] getPublicObjectives()
    {
        return pubObjs;
    }
    public int[] getToolIds()
    {
        return toolIds;
    }

}
