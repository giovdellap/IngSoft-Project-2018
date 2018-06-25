package it.polimi.ingsw.client.PackageMP.ViewMP.CLI;

public class CLIToolsManager
{
    /**
     * prints a number of spaces equal to the parameter number
     * @param num
     * @return
     */
    public String printSpaces(int num)
    {
        String temp = "";
        for(int i=0;i<num;i++)
        {
            temp+=" ";
        }
        return temp;
    }

    /**
     * prints spaces in the end
     * @param arg
     * @param endIndex
     * @return
     */
    public String printSpacesEnder(String arg, int endIndex)
    {
        String temp = arg;
        temp+=printSpaces(endIndex-arg.length());
        return temp;

    }

    /**
     * prints a string[finalRow] with the first blankrows strings made of " "
     * @param finalRows
     * @param blankRows
     * @param width
     * @return
     */
    public String[] blankLinesInitializer(int finalRows, int blankRows, int width)
    {
        String[] temp = new String[finalRows];
        for(int i=0; i<blankRows;i++)
        {
            temp[i]=printSpaces(width);
        }
        return temp;
    }

    /**
     * returns the argument String[] filled with " "
     * @param arg
     * @param startIndex
     * @param endIndex
     * @return
     */

    public String[] printBlankLines(String[] arg, int startIndex, int endIndex)
    {
        String[] temp = arg;
        for(int i=startIndex;i<endIndex;i++)
            temp[i] = printSpaces(arg[0].length()-1);
        return temp;
    }

    /**
     * returns the argument String[] filled with " "
     * @param arg
     * @param startIndex
     * @return
     */
    public String[] blankLinesEnder(String[] arg, int startIndex)
    {

        String[] temp = arg;
        for(int i=startIndex;i<arg.length;i++)
            temp[i] = printSpaces(arg[0].length()-1);
        return temp;
    }

    /**
     * prints out a question left-oriented
     * @param question
     * @param width
     * @param starter
     * @return
     */
    public String simpleQuestionsMaker(String question, int width, boolean starter)
    {

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

    /**
     * centers a string
     * @param temp
     * @param width
     * @return
     */
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

    /**
     * initializes the scene
     * @param width
     * @return 1 #s string and 2 blank strings
     */
    public String[] sceneInitializer(int width)
    {
        String[] temp= new String[1];
        temp[0] = printHashtag(width);

        return temp;
    }

    /**
     * closes the scene
     * @param width
     * @return 5 blank strings and 1 # string
     */
    public String[] sceneEnder(int width)
    {
        String[] temp = new String[2];
        temp[0] = printSpaces(width);
        temp[1] = printHashtag(width);
        return temp;
    }

    /**
     * prints out hashtags
     * @param num
     * @return
     */
    public String printHashtag(int num)
    {
        String temp="";
        for(int i=0;i<num;i++)
            temp+="#";
        return temp;
    }

    /**
     * parses a number representing a color into a string with the color name
     * @param color number representing the color
     * @return string with the color name
     */

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
