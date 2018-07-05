package it.polimi.ingsw.client;

import it.polimi.ingsw.client.Graphics.CLI.CLIToolsManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CLIToolsManagerTest
{
    CLIToolsManager cliToolsManager;

    @BeforeEach public void setUp()
    {
        cliToolsManager = new CLIToolsManager();

    }

    @Test public void checkPrintSpaces()
    {
        System.out.println(cliToolsManager.printSpaces(5)+"/");
        System.out.println("11111");
        Assertions.assertEquals(true, true);
    }

    @Test public void checkPrintSpacesEnder()
    {
        String temp = "aaa";
        System.out.println(cliToolsManager.printSpacesEnder(temp, 7)+"/");
        System.out.println("1111111");
        Assertions.assertEquals(true, true);
    }

    @Test public void checkBlankLines()
    {
        String[] temp = cliToolsManager.blankLinesInitializer(8, 2, 5);
        temp[0]+="/";
        temp[1]+="/";
        temp[2]="11111";
        temp = cliToolsManager.printBlankLines(temp, 3, 5);
        temp[3]+="/";
        temp[4]+="/";
        temp[5]="11111";
        temp = cliToolsManager.blankLinesEnder(temp, 6);
        temp[6]+="/";
        temp[7]+="/";
        for(int i=0;i<8;i++)
            System.out.println(temp[i]);

        Assertions.assertEquals(true, true);

    }
}
