package it.polimi.ingsw.client.ModelComponentsMP;

import it.polimi.ingsw.client.ModelComponentMP;

import java.util.*;

public class DraftPoolMP implements ModelComponentMP {

    private DieMP[] draft;


<<<<<<< HEAD

    public DraftPoolMP()
    {

=======
    public DraftPoolMP()
    {
        //costruttore
>>>>>>> 8450c4b95595264bc4cf0238cd708dfb324729ab
    }


    public void pickUpDie(int index) {

        // raccoglie il dado
    }


    public void updateDraft() {

        // aggiorna il draft
    }


<<<<<<< HEAD
    public DieMP replaceDie(int index, DieMP toPlace) {

        // rimpiazza il dado
        return null;
    }


    public DieMP returnDie(int pos) {

        // ritorna il dado
        return null;
=======
    public void replaceDie(int index, DieMP toPlace) {

        // rimpiazza il dado (da mettere tipo ritorno DieMP)
    }


    public void returnDie(int pos) {

        // ritorna il dado (da mettere tipo ritorno DieMP)
>>>>>>> 8450c4b95595264bc4cf0238cd708dfb324729ab
    }

}