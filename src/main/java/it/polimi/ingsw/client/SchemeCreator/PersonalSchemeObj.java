package it.polimi.ingsw.client.SchemeCreator;

import java.util.List;

public class PersonalSchemeObj {

    private String name;
    private int diff;
    private List<String> cells;

    public PersonalSchemeObj(String name, int diff, List<String> cells) {
        super();
        this.name = name;
        this.diff = diff;
        this.cells = cells;
    }

    public List<String> getCells() {
        return cells;
    }

    public String getName() {
        return name;
    }

    public int getDiff() {return diff;}

}
