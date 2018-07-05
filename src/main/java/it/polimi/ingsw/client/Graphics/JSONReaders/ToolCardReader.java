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

        if(id==1) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard1.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==2) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard2.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==3) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard3.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==4) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard4.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==5) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard5.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==6) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard6.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==7) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard7.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==8) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard8.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==9) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard9.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==10) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard10.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==11) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard11.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        if(id==12) {

            br = new BufferedReader(new FileReader(new File(".\\src\\main\\resources\\JsonPackage\\ToolCards\\ToolCard12.json")));
            cardObj = gson.fromJson(br,ToolCardObj.class);

            temp.add(cardObj.getResult().get(0).getName());
            temp.add(cardObj.getResult().get(0).getDescriptionOne());
            temp.add(cardObj.getResult().get(0).getDescriptionTwo());

            return temp;

        }

        return null;
    }

}
