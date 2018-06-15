package it.polimi.ingsw.commons.Socket.EventHandling;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;

import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.PassEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.Events.TurnEvent;
import it.polimi.ingsw.commons.FcknSimpleLogger;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketDecoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.ArrayList;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Int;

public class EventDecoder
{
    private SocketProtocolTransformer transformer;

    private SocketDecoder decoder;

    private FcknSimpleLogger logger;

    public EventDecoder()
    {
        transformer = new SocketProtocolTransformer();
        decoder = new SocketDecoder();
        logger = new FcknSimpleLogger(0, false);
    }

    public Event decodeEvent(ArrayList<String> toDecode) throws InvalidIntArgumentException
    {
        transformer.simpleDecode(toDecode.get(0));

        if(transformer.getArg().equals("UsernameEvent"))
            return decodeUsernameEvent(toDecode);

        if(transformer.getArg().equals("SchemeSelectionEvent"))
            return decodeSchemeSelectionEvent(toDecode);

        if(transformer.getArg().equals("ModelInitializationEvent"))
            return decodeModelInitializatonEvent(toDecode);

        if(transformer.getArg().equals("TurnEvent"))
            return decodeTurnEvent(toDecode);

        if(transformer.getArg().equals("ToolCardOneEvent"))
            return decodeToolCardOneEvent(toDecode);

        if(transformer.getArg().equals("ToolCardTwoThreeEvent"))
            return decodeToolCardTwoThreeEvent(toDecode);

        if(transformer.getArg().equals("ToolCardFourEvent"))
            return decodeToolCardFourEvent(toDecode);

        if(transformer.getArg().equals("ToolCardFiveEvent"))
            return decodeToolCardFiveEvent(toDecode);

        if(transformer.getArg().equals("ToolCardSixEvent"))
            return decodeToolCardSixEvent(toDecode);

        if(transformer.getArg().equals("ToolCardSevenEvent"))
            return decodeToolCardSevenEvent(toDecode);

        if(transformer.getArg().equals("ToolCardEightNineTenEvent"))
            return decodeToolCardEightNineTenEvent(toDecode);

        if(transformer.getArg().equals("ToolCardElevenEvent"))
            return decodeToolCardElevenEvent(toDecode);

        if(transformer.getArg().equals("ToolCardTwelveEvent"))
            return decodeToolCardTwelveEvent(toDecode);

        if(transformer.getArg().equals("MoveEvent"))
            return decodeMoveEvent(toDecode);

        if(transformer.getArg().equals("PassEvent"))
            return new PassEvent();

        if (transformer.getArg().equals("Initialization2Event"))
            return decodeInitialization2Event(toDecode);

        else
            return null;

    }

    private SchemeSelectionEvent decodeSchemeSelectionEvent(ArrayList<String> toDecode)
    {
        SchemeSelectionEvent event;

        transformer.simpleDecode(toDecode.get(2));

        String temp = transformer.getArg();

        transformer.simpleDecode(toDecode.get(3));

        String temp1 = transformer.getArg();

        event = new SchemeSelectionEvent(Integer.parseInt(temp),Integer.parseInt(temp1));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        return event;

    }


    private UsernameEvent decodeUsernameEvent(ArrayList<String> toDecode)
    {
        UsernameEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new UsernameEvent(transformer.getArg());

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();
        return event;
    }

    private Initialization2Event decodeInitialization2Event(ArrayList<String> toDecode)
    {
        Initialization2Event event;

        event = new Initialization2Event();

        int i=1;


        while (i<toDecode.size()-1)
        {
            i++;
            transformer.simpleDecode(toDecode.get(i));

            int player = Integer.parseInt(transformer.getArg());

            i++;
            transformer.simpleDecode(toDecode.get(i));
            String username = transformer.getArg();

            i++;
            transformer.simpleDecode(toDecode.get(i));
            int scheme = Integer.parseInt(transformer.getArg());

            i++;
            transformer.simpleDecode(toDecode.get(i));
            int fb = Integer.parseInt(transformer.getArg());

            i++;
            transformer.simpleDecode(toDecode.get(i));
            int favortokens = Integer.parseInt(transformer.getArg());

            event.addEventPlayer(player, username, scheme, fb, favortokens);

        }
        return event;
    }

    private ModelInitializationEvent decodeModelInitializatonEvent(ArrayList<String> toDecode)
    {
        ModelInitializationEvent event;

        event = new ModelInitializationEvent();

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        transformer.simpleDecode(toDecode.get(2));
        String temp = transformer.getArg();

        transformer.simpleDecode(toDecode.get(3));
        String temp1 = transformer.getArg();

        event.setSchemes(Integer.parseInt(temp),Integer.parseInt(temp1));

        transformer.simpleDecode(toDecode.get(4));

        event.setPrivateObjective(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        temp = transformer.getArg();

        transformer.simpleDecode(toDecode.get(6));
        temp1 = transformer.getArg();

        transformer.simpleDecode(toDecode.get(7));
        String temp2 = transformer.getArg();

        int [] tempArray = {Integer.parseInt(temp), Integer.parseInt(temp1), Integer.parseInt(temp2)};

        event.setPublicObjectives(tempArray);

        transformer.simpleDecode(toDecode.get(8));
        temp = transformer.getArg();

        transformer.simpleDecode(toDecode.get(9));
        temp1 = transformer.getArg();

        transformer.simpleDecode(toDecode.get(10));
        temp2 = transformer.getArg();

        int []tempArray2 = {Integer.parseInt(temp), Integer.parseInt(temp1), Integer.parseInt(temp2)};

        event.setToolIds(tempArray2);

        return event;
    }

    private TurnEvent decodeTurnEvent(ArrayList<String> toDecode) throws InvalidIntArgumentException
    {
        TurnEvent event;
        event = new TurnEvent();
        int i = 1;
        boolean flag=true;

        transformer.simpleDecode(toDecode.get(i));          //1
        if(transformer.getArg().equals("true"))
            event.validate();
        i++;

        transformer.simpleDecode(toDecode.get(i));          //2
        event.setRound(Integer.parseInt(transformer.getArg()));
        i++;

        transformer.simpleDecode(toDecode.get(i));          //3
        if (Boolean.parseBoolean(transformer.getArg()))
        {
            event.noDisconnected();
            i++;
        }
        else
            {
                i++;                                        //4

                ArrayList temp= new ArrayList<String>();

                transformer.simpleDecode(toDecode.get(i));

                while (flag)
                {
                    temp.add(transformer.getArg());
                    i++;
                    transformer.simpleDecode(toDecode.get(i));
                    if (!(transformer.getCmd().equals("disconnected")))
                        flag = false;
                }
                event.setDisconnected(temp);
            }

        //active
        transformer.simpleDecode(toDecode.get(i));              //i=6

        event.setActive(Integer.parseInt(transformer.getArg()));
        i++;


        //draft
        ArrayList<String> vectTemp=new ArrayList<String>();         //i=7

        flag=true;

        transformer.simpleDecode(toDecode.get(i));          //legge primo dado

        while (flag)
        {
            vectTemp.add(toDecode.get(i));
            transformer.simpleDecode(toDecode.get(i+1));

            if (transformer.getCmd().equals("toolsUpdate")) flag=false;
            i++;

        }
        event.setDraft(decoder.arrayListDecoder(vectTemp));

        //tools
        flag=true;
        int k = 0;
        int z=i;

        while (flag)
        {
            transformer.simpleDecode(toDecode.get(z));
            if (transformer.getCmd().equals("toolsUpdate")) k++;
            else flag=false;
            z++;
        }

        int[] tempTool = new int[k];


        for (int p=0;p<k;p++)
        {
            transformer.simpleDecode(toDecode.get(i));
            tempTool[p]=Integer.parseInt(transformer.getArg());
            i++;
        }

        event.setToolsUpdate(tempTool);

        //myturn
        transformer.simpleDecode(toDecode.get(i));
        event.setMyTurn(Boolean.parseBoolean(transformer.getArg()));
        i++;

        //lastRound
        transformer.simpleDecode(toDecode.get(i));
        if(!transformer.getCmd().equals("nextRound")) {
            ArrayList<String> vectTemp1 = new ArrayList<String>();

            flag = true;

            while (flag) {
                vectTemp1.add(toDecode.get(i));
                transformer.simpleDecode(toDecode.get(i + 1));
                if (transformer.getCmd().equals("nextRound")) flag = false;
                i++;
            }
            event.setLastRound(decoder.arrayListDecoder(vectTemp));
        }
        else
            event.setLastRound(null);
        transformer.simpleDecode(toDecode.get(i));
        event.setNextRound(Boolean.parseBoolean(transformer.getArg()));

        return event;

    }

    private ToolCardOneEvent decodeToolCardOneEvent(ArrayList<String> toDecode)
    {
        ToolCardOneEvent event;

        transformer.simpleDecode(toDecode.get(2));

        event = new ToolCardOneEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setX(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setY(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setAction(transformer.getArg().charAt(0));

        return event;
    }

    private ToolCardTwoThreeEvent decodeToolCardTwoThreeEvent(ArrayList<String> toDecode)
    {
        ToolCardTwoThreeEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardTwoThreeEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(3));
        event.setX0(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setY0(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setX1(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setY1(Integer.parseInt(transformer.getArg()));


        return event;
    }

    private ToolCardFourEvent decodeToolCardFourEvent(ArrayList<String> toDecode)
    {
        ToolCardFourEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardFourEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(3));
        event.setX01(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setY01(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setX02(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setY02(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(7));
        event.setX11(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(8));
        event.setY11(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(9));
        event.setX22(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(10));
        event.setY22(Integer.parseInt(transformer.getArg()));

        return event;
    }

    private ToolCardFiveEvent decodeToolCardFiveEvent(ArrayList<String> toDecode)
    {
        ToolCardFiveEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardFiveEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setTurn(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setPos(Integer.parseInt(transformer.getArg()));

        return event;
    }

    private ToolCardSixEvent decodeToolCardSixEvent(ArrayList<String> toDecode)
    {
        ToolCardSixEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardSixEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setX(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setY(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setApplyOne(Boolean.parseBoolean((transformer.getArg())));

        transformer.simpleDecode(toDecode.get(7));
        event.setApplyTwo(Boolean.parseBoolean((transformer.getArg())));

        return event;
    }

    private ToolCardSevenEvent decodeToolCardSevenEvent(ArrayList<String> toDecode) throws InvalidIntArgumentException
    {
        ToolCardSevenEvent event;
        boolean flag=true;
        int i=3;

        ArrayList<String> vectTemp=new ArrayList();

        transformer.simpleDecode(toDecode.get(2));

        event = new ToolCardSevenEvent(Integer.parseInt(transformer.getArg()));

        while (flag)
        {
            transformer.simpleDecode(toDecode.get(i));
            vectTemp.add(toDecode.get(i));
            transformer.simpleDecode(toDecode.get(i+1));
            if (transformer.getCmd().equals("end")) flag=false;
            i++;
        }

        event.setDice(decoder.arrayListDecoder(vectTemp));

        return event;
    }

    private ToolCardEightNineTenEvent decodeToolCardEightNineTenEvent(ArrayList<String> toDecode)
    {
        ToolCardEightNineTenEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardEightNineTenEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setX(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setY(Integer.parseInt(transformer.getArg()));

        return event;
    }

    private ToolCardElevenEvent decodeToolCardElevenEvent(ArrayList<String> toDecode)
    {
        ToolCardElevenEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardElevenEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setNewValue(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setX(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setY(Integer.parseInt(transformer.getArg()));

        return event;

    }

    private ToolCardTwelveEvent decodeToolCardTwelveEvent(ArrayList<String> toDecode)
    {
        ToolCardTwelveEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardTwelveEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(3));
        event.setTurn(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setPos(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setX01(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setY01(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(7));
        event.setX02(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(8));
        event.setY02(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(9));
        event.setX11(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(10));
        event.setY11(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(11));
        event.setX22(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(12));
        event.setY22(Integer.parseInt(transformer.getArg()));
        return event;
    }

    private MoveEvent decodeMoveEvent(ArrayList<String> toDecode)
    {
        MoveEvent event;
        boolean validate=false;

        transformer.simpleDecode(toDecode.get(1));
        logger.log("dio bastardello "+toDecode.get(1));
        //1
        if(transformer.getArg().equals("true"))
            validate=true;

        transformer.simpleDecode(toDecode.get(2));
        int id=Integer.parseInt(transformer.getArg());

        transformer.simpleDecode(toDecode.get(3));
        int index=Integer.parseInt(transformer.getArg());

        transformer.simpleDecode(toDecode.get(4));
        int x=Integer.parseInt(transformer.getArg());

        transformer.simpleDecode(toDecode.get(5));
        int y=Integer.parseInt(transformer.getArg());

        event = new MoveEvent(index,x,y);
        if (validate) {
            event.validate();
            logger.log("validate true: "+Boolean.toString(event.isValidated()));
        }
        else {
            event.resetValidation();
            logger.log("validate false: "+Boolean.toString(event.isValidated()));
        }
        event.setId(id);

        return event;
    }



}
