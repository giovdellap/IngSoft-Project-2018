package it.polimi.ingsw.server.ToolCards;

public abstract class ToolCard {

    int id;
    int favorTokens;
    String toolCardName;

    /**
     * ToolCard Abstract Constructor
     */
    public ToolCard() {

        favorTokens=0;
        toolCardName="";

    }

    /**
     * sets tool card name
     * @param name name
     */
    public void setToolCardName (String name) {
        this.toolCardName=name;
    }

    /**
     * gets tool card name
     * @return name
     */
    public String getToolCardName () {
        return this.toolCardName;
    }

    /**
     * sets tool card favor tokens
     * @param tokens
     */
    public void setFavorTokens (int tokens) {
        this.favorTokens = tokens;
    }

    /**
     * gets tool card favor tokens
     * @return
     */
    public int getFavorTokens() {
        return this.favorTokens;
    }

    /**
     * sets id to tool card
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets tool card id
     * @return id
     */
    public int getId() {
        return id;
    }
}
