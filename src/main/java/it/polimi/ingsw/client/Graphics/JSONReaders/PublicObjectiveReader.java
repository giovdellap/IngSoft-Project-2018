package it.polimi.ingsw.client.Graphics.JSONReaders;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

public class PublicObjectiveReader {


    private Gson gson;
    private BufferedReader br;
    private PublicObjectiveObj pubObj;

    public ArrayList<String> readPublicObjective(int id) throws FileNotFoundException {

        ArrayList<String> temp = new ArrayList<String>();
        gson = new GsonBuilder().setLenient().create();

        if(id ==1) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective1.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }

        if(id ==2) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective2.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }

        if(id ==3) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective3.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }

        if(id ==4) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective4.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }

        if(id ==5) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective5.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }

        if(id ==6) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective6.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }

        if(id ==7) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective7.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }

        if(id ==8) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective8.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }

        if(id ==9) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective9.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }

        if(id ==10) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\PublicObjectives\\PublicObjective10.json")));
            pubObj = gson.fromJson(br,PublicObjectiveObj.class);

            temp.add(pubObj.getResult().get(0).getDescription());
            temp.add(Integer.toString(pubObj.getResult().get(0).getBonus()));

            return temp;
        }


        return null;

    }

}
