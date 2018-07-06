package it.polimi.ingsw.client;

import it.polimi.ingsw.client.JSONSettings.SettingsReader;
import it.polimi.ingsw.client.JSONSettings.SettingsWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;

public class JSONSettingsWriterReaderTest {

    int[] vect;
    SettingsWriter settingsWriter;
    SettingsReader settingsReader;


    @BeforeEach
    public void setUp() {
        vect = new int[3];
        settingsWriter = new SettingsWriter();
        settingsReader = new SettingsReader();

    }

    @Test
    public void checkWrite() throws IOException {

        settingsWriter.write("1","2","3","76587.6..5","trhstr");

    }

    @Test
    public void checkRead() throws FileNotFoundException {

        settingsReader.readSetting();

        String temp=settingsReader.getDebug();

        System.out.println(temp);
        System.out.println(settingsReader.getGraphics());
        System.out.println(settingsReader.getReconnection());
        System.out.println(settingsReader.getIP());
        System.out.println(settingsReader.getPort());

    }

}
