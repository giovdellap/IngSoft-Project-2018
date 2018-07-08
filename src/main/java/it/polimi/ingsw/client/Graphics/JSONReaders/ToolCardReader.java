package it.polimi.ingsw.client.Graphics.JSONReaders;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class ToolCardReader {

    private Gson gson;
    private BufferedReader br;
    private ToolCardObj cardObj;

    public ArrayList<String> readToolCard(int id) throws FileNotFoundException {

        ArrayList<String> temp = new ArrayList<String>();
        gson = new GsonBuilder().setLenient().create();


        InputStream in = this.getClass().getResourceAsStream("/JsonPackage/ToolCards/ToolCard" + id + ".json");
        br = new BufferedReader(new InputStreamReader(in));

        cardObj = gson.fromJson(br,ToolCardObj.class);

        temp.add(cardObj.getResult().get(0).getName());
        temp.add(cardObj.getResult().get(0).getDescriptionOne());
        temp.add(cardObj.getResult().get(0).getDescriptionTwo());

        return temp;
    }

}
