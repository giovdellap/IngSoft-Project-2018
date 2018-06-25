package it.polimi.ingsw.commons.Socket.SocketTools;

public class SocketProtocolTransformer
{
    private String tempCmd;
    private String tempArg;

    /**
     * gets the command
     * @return command
     */
    public String getCmd()
    {
        return tempCmd;
    }

    /**
     * gets the argument
     * @return argument
     */
    public String getArg()
    {
        return tempArg;
    }

    /**
     * encodes the command between #, and the arguments between $
     * @param cmd command
     * @param arg argument
     * @return String encoded as said above
     */
    public String simpleEncode(String cmd, String arg)
    {
        String temp = "#"+cmd+"#$"+arg+"$";
        return temp;
    }

    /**
     * decodes a string, parting it in cmd and arg
     * @param tempStr string in the format #cmd#$arg$
     */
    public void simpleDecode(String tempStr)
    {
        tempCmd="";
        tempArg="";
        int index=0;
        if(tempStr.charAt(index)=='#')
        {
            index++;
            while (tempStr.charAt(index) != '#')
            {
                tempCmd+=Character.toString(tempStr.charAt(index));
                index++;
            }
            index++;
            if(tempStr.charAt(index)=='$');
            {
                index++;
                while(tempStr.charAt(index)!='$')
                {
                    tempArg+=Character.toString(tempStr.charAt(index));
                    index++;
                }
            }
        }
    }
}
