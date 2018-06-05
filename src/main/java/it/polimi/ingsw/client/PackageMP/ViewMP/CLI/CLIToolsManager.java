package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

public class CLIToolsManager
{
    public String printSpaces(int num)
    {
        //returns a string made of num spaces
        String temp = "";
        for(int i=0;i<num;i++)
        {
            temp+=" ";
        }
        return temp;
    }

    public String printSpacesEnder(String arg, int endIndex)
    {
        String temp = arg;
        temp+=printSpaces(endIndex-arg.length());
        return temp;

    }

    public String[] blankLinesInitializer(int finalRows, int blankRows, int width)
    {
        //returns a string[finalRow] with the first #blankrows strings made of " "

        String[] temp = new String[finalRows];
        for(int i=0; i<blankRows;i++)
        {
            temp[i]=printSpaces(width);
        }
        return temp;
    }

    public String[] printBlankLines(String[] arg, int startIndex, int endIndex)
    {
        //returns the argument String[] filled with " "
        //same width of temp[0]

        String[] temp = arg;
        for(int i=startIndex;i<endIndex;i++)
            temp[i] = printSpaces(arg[0].length()-1);
        return temp;
    }

    public String[] blankLinesEnder(String[] arg, int startIndex)
    {
        //returns the argument String[] filled with " "
        //same width of temp[0]

        String[] temp = arg;
        for(int i=startIndex;i<arg.length;i++)
            temp[i] = printSpaces(arg[0].length()-1);
        return temp;
    }

    public String simpleQuestionsMaker(String question, int width, boolean starter)
    {
        //returns a left-oriented string

        if(starter)

        {
            String temp = printSpaces(2)+question;
            return printSpacesEnder(temp, width);
        }
        else
        {
            return printSpacesEnder(question, width);
        }

    }

    public String centerThatString(String temp, int width)
    {
        if((width-temp.length())%2==0)
        {
            String t = printSpaces((width-temp.length())/2)+temp;
            return printSpacesEnder(t, width);
        }
        else
        {
            String t = printSpaces(((width-temp.length())/2)-1)+temp;
            return printSpacesEnder(t, width);
        }
    }

    public String[] sceneInitializer(int width)
    {
        //initializes the scene
        //returns 1 #s string and 2 blank strings

        String[] temp= new String[1];
        temp[0] = printHashtag(width);

        return temp;
    }

    public String[] sceneEnder(int width)
    {
        //closes the scene
        //returns 5 blank strings and 1 # string

        String[] temp = new String[2];
        temp[0] = printSpaces(width);
        temp[1] = printHashtag(width);
        return temp;
    }

    public String printHashtag(int num)
    {
        String temp="";
        for(int i=0;i<num;i++)
            temp+="#";
        return temp;
    }

    public String getColor(int color)
    {
        if(color==1)
            return "giallo";
        if(color==2)
            return "rosso";
        if(color==3)
            return "verde";
        if(color==4)
            return "blu";
        if(color==5)
            return "viola";
        else
            return null;
    }


}
