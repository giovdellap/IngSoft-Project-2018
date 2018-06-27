package it.polimi.ingsw.server;

import it.polimi.ingsw.server.Loggers.MajorLogger;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

public class MajorLoggerTest
{
    private MajorLogger testMLog;

    @BeforeEach
    public void setUp()
    {
        testMLog = new MajorLogger();
    }

    @Test
    public void checkLogandReturnLog() throws GenericInvalidArgumentException {
        testMLog.majorLog("Test 1");
        testMLog.majorLog("Test 2");
        ArrayList<String> temp = testMLog.returnLog();

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
        try
        {
            String temp = null;
            testMLog.majorLog(temp);
        } catch(GenericInvalidArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(), "Generic invalid argument");
        }
    }

    @Test
    public void checkGetLog() throws GenericInvalidArgumentException {
        testMLog.majorLog("Test 1");
        testMLog.majorLog("Test 2");
        testMLog.getLog();
        Assertions.assertEquals(true, true);
    }

    @Test
    public void checkStackLog() throws GenericInvalidArgumentException {
        ArrayList<String> test = new ArrayList<String>();
        test.add("Test 1");
        test.add("Test 2");

        testMLog.stackLog(test);
        boolean flag = true;
        if(testMLog.returnLog().get(0)!="Test 1")
            flag = false;
        if(testMLog.returnLog().get(1)!="Test 2")
            flag=false;
        Assertions.assertEquals(true, flag);
    }

    @Test
    public void checkStackLogException()
    {
        ArrayList<String> temp = null;
        try
        {
            testMLog.stackLog(temp);
        } catch(GenericInvalidArgumentException e)
        {
            Assertions.assertEquals(e.getMessage(), "Generic invalid argument");
        }
    }
}
