package it.polimi.ingsw.server.ToolCards;

public abstract class ToolCard {

    int id;
    int favorTokens;
    String toolCardName;

    public ToolCard() {

        favorTokens=0;
        toolCardName="";

    }

    public void setToolCardName (String name) {
        this.toolCardName=name;
    }

    public String getToolCardName () {
        return this.toolCardName;
    }

    public void setFavorTokens (int tokens) {
        this.favorTokens = tokens;
    }

    public int getFavorTokens() {
        return this.favorTokens;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
