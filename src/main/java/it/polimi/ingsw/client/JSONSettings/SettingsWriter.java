package it.polimi.ingsw.client.JSONSettings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class SettingsWriter {

    public void write(int debug, int graphic, int reconnection) throws IOException {

        Gson gson = new GsonBuilder().setLenient().create();

        SettingsObj sett = new SettingsObj(debug,graphic,reconnection);

        String json;
        FileWriter writer = null;

        try
        {
                json = gson.toJson(sett);
                writer = new FileWriter("src/main/resources/JsonPackage/Settings/Settings.json");
                writer.write(json);
                writer.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        } finally {
            writer.close();
        }

    }

    }

    /*
public class SchemeCardWriter {


    public void write(SchemeCard scheme) throws IOException, InvalidIntArgumentException {

        Gson gson = new GsonBuilder().setLenient().create();

        String json = gson.toJson(scheme);

        FileWriter writer = null;

        try
        {
            writer = new FileWriter(".\\src\\main\\Resources\\JsonPackage\\SchemeCardPers\\"+scheme.getName(1)+".json");
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
    */