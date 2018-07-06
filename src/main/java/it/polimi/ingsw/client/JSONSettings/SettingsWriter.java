package it.polimi.ingsw.client.JSONSettings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileWriter;
import java.io.IOException;

public class SettingsWriter {

    public void write(String debug, String graphic, String reconnection, String ip, String port) throws IOException
    {

        Gson gson = new GsonBuilder().setLenient().create();

        SettingsObj sett = new SettingsObj(debug,graphic,reconnection, ip, port);

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
