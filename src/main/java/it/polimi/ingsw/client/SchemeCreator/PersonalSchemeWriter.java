package it.polimi.ingsw.client.SchemeCreator;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.io.*;

public class PersonalSchemeWriter {


    public void write(SchemeCard scheme) throws IOException, InvalidIntArgumentException {

        Gson gson = new GsonBuilder().setLenient().create();

        String json = gson.toJson(scheme);

        FileWriter writer = null;

        try
        {
            writer = new FileWriter("./src/main/resources/JsonPackage/SchemeCardsPers/"+scheme.getName(1)+".json");
            writer.write(json);
            writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally {
            writer.close();
        }


    }


}
