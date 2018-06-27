package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MinorLogger;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MinorLoggerTest
{
    MinorLogger testLog;

    @BeforeEach
    public void setUp()
    {
        testLog = new MinorLogger();
    }

    @Test
    public void checkLogandUpdateFather() throws GenericInvalidArgumentException {
        testLog.minorLog("Test 1");
        testLog.minorLog("Test 2");
        ArrayList<String> temp = testLog.updateFather();

        boolean flag = true;
        if(temp.get(0)!="Test 1")
            flag=false;
        if(temp.get(1)!="Test 2")
            flag=false;
        Assertions.assertEquals(true, flag);
    }

    @Test
    public void checkLogException()
    {
        String s = null;
        try
        {
            testLog.minorLog(s);
        } catch (GenericInvalidArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(), "Generic invalid argument");
        }
    }

    @Test
    public void checkStackLog() throws GenericInvalidArgumentException {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add("Test 1");
        temp.add("Test 2");
        testLog.stackLog(temp);
        ArrayList<String> temp2 = testLog.updateFather();
        Assertions.assertEquals(temp, temp2);
    }

    @Test
    public void checkStackLogException()
    {
        ArrayList<String> temp = null;
        try
        {
            testLog.stackLog(temp);
        } catch (GenericInvalidArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(), "Generic invalid argument");
        }
    }

    @Test
    public void checkReinitialize() throws GenericInvalidArgumentException {
        testLog.minorLog("Test 1");
        testLog.reinitialize();
        Assertions.assertEquals(testLog.updateFather(), new ArrayList<String>());
    }
}
