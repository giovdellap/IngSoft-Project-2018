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

        String file = getClass().getClassLoader().getResource("JsonPackage/SchemeCards/SchemeCard" + index + ".json").getFile();
        br = new BufferedReader(new FileReader(file));
        card = gson.fromJson(br, SchemeCardObj.class);

        SchemeCard tempScheme = new SchemeCard(index);

        return buildTempScheme(tempScheme);

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








