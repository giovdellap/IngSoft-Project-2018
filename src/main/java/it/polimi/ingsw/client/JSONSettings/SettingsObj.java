package it.polimi.ingsw.client.JSONSettings;

import java.util.ArrayList;
import java.util.List;

public class SettingsObj {

    private List<String> settings;

    public SettingsObj(String debug, String graphic, String reconnection, String ip, String port ) {

        settings = new ArrayList<String>();

        settings.add(debug);
        settings.add(graphic);
        settings.add(reconnection);
        settings.add(ip);
        settings.add(port);

    }

    public List<String> getSettings() {
        return settings;
    }


}
