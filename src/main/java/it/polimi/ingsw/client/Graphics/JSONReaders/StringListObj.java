package it.polimi.ingsw.client.Graphics.JSONReaders;
public class StringListObj {

    private String name;
    private String descriptionOne;
    private String descriptionTwo;

    /**
     * contains infos about toolcards objs
     * @param name
     * @param descriptionOne
     * @param descriptionTwo
     */
    public StringListObj(String name, String descriptionOne, String descriptionTwo) {
        super();
        this.name=name;
        this.descriptionOne=descriptionOne;
        this.descriptionTwo=descriptionTwo;
    }


    public String getDescriptionOne() {
        return descriptionOne;
    }

    public String getDescriptionTwo() {
        return descriptionTwo;
    }

    public String getName() {
        return name;
    }

}
