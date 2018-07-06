package it.polimi.ingsw.client.JSONSettings;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class SettingsReader {

    private Gson gson;
    private BufferedReader br;
    private SettingsObj settingsObj;

    public void readSetting() throws FileNotFoundException {

        gson = new GsonBuilder().setLenient().create();

        br = new BufferedReader(new FileReader(getClass().getClassLoader().getResource("JsonPackage/Settings/Settings.json").getFile()));
        settingsObj = gson.fromJson(br, SettingsObj.class);

    }

    public int getDebug() {
        return settingsObj.getSettings().get(0);
    }

    public int getGraphics() {
        return settingsObj.getSettings().get(1);
    }

    public int getReconnection() {
        return settingsObj.getSettings().get(2);
    }

}
