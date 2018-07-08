package it.polimi.ingsw.client.Graphics.JSONReaders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class ToolCardReader {

    private Gson gson;
    private BufferedReader br;
    private ToolCardObj cardObj;

    public ArrayList<String> readToolCard(int id) throws FileNotFoundException {

        ArrayList<String> temp = new ArrayList<String>();
        gson = new GsonBuilder().setLenient().create();


        String file = getClass().getClassLoader().getResource("JsonPackage/ToolCards/ToolCard" + id + ".json").getFile();
        br = new BufferedReader(new FileReader(file));

        cardObj = gson.fromJson(br,ToolCardObj.class);

        temp.add(cardObj.getResult().get(0).getName());
        temp.add(cardObj.getResult().get(0).getDescriptionOne());
        temp.add(cardObj.getResult().get(0).getDescriptionTwo());

        return temp;
    }

}
