package it.polimi.ingsw.client;

import it.polimi.ingsw.client.JSONSettings.SettingsReader;

import java.util.ArrayList;

public class Main
{
    /**
     * MAIN Constructor
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        boolean quit=false;
        ClientLauncher launcher= new ClientLauncher();
        Match match;
        int choice;
        ArrayList<String> settings;


        while (!quit) {
            choice = launcher.launch();
            if(choice==0)
                quit=true;
            if(choice==1)
            {
                settings=launcher.getSettings();
                if(settings.get(2).equals("false"))
                {
                    match=new Match(settings);
                    match.start();
                }
                else
                {
                    match=new Match(settings);
                    match.tryReconnection();
                }
            }


        }

    }

}