package it.polimi.ingsw.client.ModelComponentsMP;

import it.polimi.ingsw.client.ModelComponentMP;

import java.util.*;

public class DraftPoolMP implements ModelComponentMP {

    private DieMP[] draft;


    public DraftPoolMP()
    {
        //costruttore
    }


    public void pickUpDie(int index) {

        // raccoglie il dado
    }


    public void updateDraft() {

        // aggiorna il draft
    }


    public void replaceDie(int index, DieMP toPlace) {

        // rimpiazza il dado (da mettere tipo ritorno DieMP)
    }


    public void returnDie(int pos) {

        // ritorna il dado (da mettere tipo ritorno DieMP)
    }

}