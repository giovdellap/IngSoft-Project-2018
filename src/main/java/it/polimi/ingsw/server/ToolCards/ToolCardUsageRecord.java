package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.SimpleLogger;

public class ToolCardUsageRecord
{
    private int[] selectedId;
    private int[] tokens;
    private ToolCard[] cards;

    private SimpleLogger logger;

    /**
     * ToolCardUsageRecord Constructor
     */
    public ToolCardUsageRecord() {
        logger = new SimpleLogger(0, false);
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
            if(!flag)
            {
                selectedId[extracted]=i;
                logger.log("Tool extracted - ID: "+Integer.toString(i));
                extracted++;
            }
        }

        selectedId[0] = 4;
        selectedId[1] = 5;
        selectedId[2] = 6;

        cards = new ToolCard[3];
        toolCardConstructor();

    }


    /**
     * checks if a particular tool card can be used or not
     * @param playerTokens
     * @param idTool
     * @return 0 if player cannot use that tool, 1 or 2 if player can use it
     */
    public int checkUsage(int playerTokens, int idTool)
    {
        int toolCard=0;
        for(int i=0;i<3;i++)
            if(selectedId[i]==idTool)
                toolCard=i;

        if (tokens[toolCard]==0&&playerTokens>0)
            return 1;
        if(tokens[toolCard]>0&&playerTokens>1)
            return 2;
        else
            return 0;
    }

    public void applyUsage(int idTool)
    {
        int toolCard=0;
        for(int i=0;i<3;i++)
            if(selectedId[i]==idTool)
                toolCard=i;
        if(tokens[toolCard]==0)
            tokens[toolCard]=1;
        else
        {
            int previousTokens = tokens[toolCard];
            tokens[toolCard]=previousTokens+2;
        }
    }

    /**
     *
     * @return tool card tokens
     */
    public int[] getRecord()
    {
        return tokens;
    }

    /**
     *
     * @return selectedId
     */
    public int[] getSelectedId() {
        return selectedId;
    }

    /**
     * Constructs the selected tool card based on the selected id
     */
    public void toolCardConstructor() {
        for(int i=0;i<3;i++)
        {
            switch (selectedId[i]){
                case 1: {
                    cards[i] = new ToolCardOne();
                    break;
                }
                case 2:
                {
                    cards[i] = new ToolCardTwo();
                    break;
                }
                case 3:
                {
                    cards[i] = new ToolCardThree();
                    break;
                }
                case 4:
                {
                    cards[i] = new ToolCardFour();
                    break;
                }
                case 5:
                {
                    cards[i] = new ToolCardFive();
                    break;
                }
                case 6:
                {
                    cards[i] = new ToolCardSix();
                    break;
                }
                case 7:
                {
                    cards[i] = new ToolCardSeven();
                    break;
                }
                case 8:
                {
                    cards[i] = new ToolCardEight();
                    break;
                }
                case 9:
                {
                    cards[i] = new ToolCardNine();
                    break;
                }
                case 10:
                {
                    cards[i] = new ToolCardTen();
                    break;
                }
                case 11:
                {
                    cards[i] = new ToolCardEleven();
                    break;
                }
                case 12:
                {
                    cards[i] = new ToolCardTwelve();
                    break;
                }
            }
        }
    }

    /**
     * gets selected tool card by id
     * @param id tool card's id
     * @return selected tool card
     */
    public ToolCard getCard(int id)
    {
        for(int i=0;i<3;i++)
            if(cards[i].getId()==id)
                return cards[i];
        return null;
    }

}
