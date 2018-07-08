package it.polimi.ingsw.client.SchemeCreator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.io.*;

public class SchemeReader {
    private Gson gson;
    private BufferedReader br;
    private SchemeCard card;

    public SchemeCard read(String name) throws FileNotFoundException, InvalidIntArgumentException {
        gson = new GsonBuilder().setLenient().create();

        InputStream in = this.getClass().getResourceAsStream("/JsonPackage/SchemeCardsPers/" +name+ ".json");
        br = new BufferedReader(new InputStreamReader(in));

        card=gson.fromJson(br, SchemeCard.class);

        return card;

    }


}
