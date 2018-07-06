package it.polimi.ingsw.commons.SchemeCardManagement;

import java.util.List;

public class FrontOrBackObj {

    private String name;
    private int diff;
    private List<String> cells;

    public FrontOrBackObj(String name, int diff, List<String> cells) {
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
