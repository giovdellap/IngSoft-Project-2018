package it.polimi.ingsw.commons.SchemeCardManagement;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.io.*;

public class SchemeCardReader {

    private Gson gson;
    private BufferedReader br;
    private SchemeCardObj card;

    public SchemeCard readCard(int index) throws FileNotFoundException, InvalidIntArgumentException
    {
        gson = new GsonBuilder().setLenient().create();

        if (index==1)
        {
            //read from json and instantiate a SchemeCard to set
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard1.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            //set the SchemeCard
            return buildTempScheme(tempScheme);
        }

        if (index==2)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard2.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==3)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard3.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==4)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard4.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==5)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard5.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==6)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard6.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==7)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard7.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==8)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard8.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==9)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard9.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==10)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard10.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==11)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard11.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        if (index==12)
        {
            ClassLoader classLoader = getClass().getClassLoader();
            br = new BufferedReader(new FileReader(new File(classLoader.getResource("./JsonPackage/SchemeCards/SchemeCard12.json").getFile())));
            card = gson.fromJson(br, SchemeCardObj.class);

            SchemeCard tempScheme=new SchemeCard(index);

            return buildTempScheme(tempScheme);
        }

        return null;

    }

    public SchemeCard buildTempScheme(SchemeCard tempSchemeBuild) throws InvalidIntArgumentException
    {
        int z=0;

        tempSchemeBuild.setName(1,card.getResult().get(0).getName());
        tempSchemeBuild.setName(2,card.getResult().get(1).getName());

        tempSchemeBuild.setDiff(1,card.getResult().get(0).getDiff());
        tempSchemeBuild.setDiff(2,card.getResult().get(1).getDiff());

        for (int i=0;i<4;i++)
            for (int j=0;j<5;j++)
            {
                tempSchemeBuild.setCell(1, i, j, Integer.parseInt(card.getResult().get(0).getCells().get(z)));
                z++;
            }

        z=0;

        for (int i=0;i<4;i++)
            for (int j=0;j<5;j++)
            {
                tempSchemeBuild.setCell(2, i, j, Integer.parseInt(card.getResult().get(1).getCells().get(z)));
                z++;
            }

        return tempSchemeBuild;
    }


}








