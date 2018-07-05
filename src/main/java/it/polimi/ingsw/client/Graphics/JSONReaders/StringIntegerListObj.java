package it.polimi.ingsw.client.Graphics.JSONReaders;
public class StringIntegerListObj {

    String description;
    int bonus;

    public StringIntegerListObj(String description, int bonus) {
        super();
        this.description=description;
        this.bonus=bonus;
    }

    public int getBonus() {
        return bonus;
    }

    public String getDescription() {
        return description;
    }

}
