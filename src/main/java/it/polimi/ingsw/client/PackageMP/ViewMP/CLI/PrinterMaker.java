package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.PrivateObjectiveMP;
import it.polimi.ingsw.client.PackageMP.ModelComponentsMP.SchemeCardMP;

public class PrinterMaker
{
    private it.polimi.ingsw.client.PackageMP.ViewMP.CLI.ModelGenerator modelGenerator;

    //USERNAME INSERTION
    public String getUsernameInsertion()
    {
        //returns a string 80x30 asking for username insertion
        String temp = printBlankLines(20, 80);
        temp+=printSpaces(31)+"INSERISCI USERNAME"+printSpaces(31)+"%n";
        temp+=printBlankLines(9, 80);

        return temp;
    }
    public String getUsernameInsertionAgain(String badUsername)
    {
        //returns a string 80x30 claiming badUsername is wrong and asking to type another one
        String temp = printBlankLines(12, 80);
        String oldUsername;
        if(badUsername.length()%2==0)
        {
            int spaces=((30-badUsername.length())/2);
            temp+=printSpaces(25+spaces)+badUsername+printSpaces(25+spaces)+"%n";
        }
        else
        {
            int spaces=((30-badUsername.length()-1)/2);
            temp+=printSpaces(25+spaces)+badUsername+printSpaces(25+1+spaces)+"%n";
        }
        temp+=printBlankLines(7, 80);
        temp+=printSpaces(31)+"INSERISCI USERNAME"+printSpaces(31)+"%n";
        temp+=printBlankLines(9, 80);
        return temp;

    }

    //SCENE SELECTION
    private String[] schemeCardExposure(SchemeCardMP scheme, int fb)
    {
        //receives a schemecard and the front/back
        //returns a String[20] (String.lenght = 20) representing the schemecard(20x20)
        //SELECTION G
        return null;
    }

    private String[] showUsername(String username)
    {
        //returns a String[20] (string.lenght = 20) showing the username
        //SELECTION H
        return null;
    }

    private String[] showPubObj(PrivateObjectiveMP privobj)
    {
        //returns a String[20] (string.lenght = 15) showing the private objective
        //SELECTION D
        return null;
    }

    private String[] askingScheme()
    {
        //returns the asking scheme strings below everything
        //String[10] (string.lenght = 80)
        //SELECTION I
        return null;
    }

    public String getSelctionScene()
    {
        //returns the final selection scene string 80x50
        return null;
    }



    //TOOLS
    private String printSpaces(int num)
    {
        //returns a string made of num spaces
        String temp = "";
        for(int i=0;i<num;i++)
        {
            temp+=" ";
        }
        return temp;
    }

    private String printBlankLines(int rows, int width)
    {
        //returns a string made of "rows" new lines made of "width" spaces
        String temp1 = printSpaces(width);
        for(int i=1; i<rows;i++)
        {
            temp1+=("%n"+temp1);
        }
        return temp1;
    }
}
