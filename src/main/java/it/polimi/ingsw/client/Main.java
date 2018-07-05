package it.polimi.ingsw.client;

public class Main
{
    /**
     * MAIN Constructor
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        Match match = new Match("localhost");
        match.start();

    }

}