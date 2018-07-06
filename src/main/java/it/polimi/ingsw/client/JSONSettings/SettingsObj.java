package it.polimi.ingsw.client.JSONSettings;

import java.util.ArrayList;
import java.util.List;

public class SettingsObj {

    private List<Integer> settings;

    public SettingsObj(int debug, int graphic, int reconnection) {

        settings = new ArrayList<Integer>();

        settings.add(debug);
        settings.add(graphic);
        settings.add(reconnection);

    }

    public List<Integer> getSettings() {
        return settings;
    }


}
