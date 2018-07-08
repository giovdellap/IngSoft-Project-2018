package it.polimi.ingsw.client.Graphics.JSONReaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.*;
import java.util.ArrayList;

public class PublicObjectiveReader {


    private Gson gson;
    private BufferedReader br;
    private PublicObjectiveObj pubObj;

    public ArrayList<String> readPublicObjective(int id) throws FileNotFoundException {

        ArrayList<String> temp = new ArrayList<String>();
        gson = new GsonBuilder().setLenient().create();


        InputStream in = this.getClass().getResourceAsStream("/JsonPackage/PublicObjectives/PublicObjective" + id + ".json");

        br = new BufferedReader(new InputStreamReader(in));
        pubObj = gson.fromJson(br, PublicObjectiveObj.class);

        temp.add(pubObj.getResult().get(0).getDescription());
        temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

        return temp;
    }

}
