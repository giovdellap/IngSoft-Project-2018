package it.polimi.ingsw.client.SchemeCreator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SchemeReader {
    private Gson gson;
    private BufferedReader br;
    private SchemeCard card;

    public SchemeCard read(String name) throws FileNotFoundException, InvalidIntArgumentException {
        gson = new GsonBuilder().setLenient().create();

        String file = getClass().getClassLoader().getResource("JsonPackage/SchemeCardsPers/" + name + ".json").getFile();
        br = new BufferedReader(new FileReader(file));

        card=gson.fromJson(br, SchemeCard.class);

        return card;

    }


}
