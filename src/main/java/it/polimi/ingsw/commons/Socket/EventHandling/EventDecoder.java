package it.polimi.ingsw.commons.Socket.EventHandling;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.*;
import it.polimi.ingsw.commons.Events.Disconnection.ForfaitEvent;
import it.polimi.ingsw.commons.Events.Disconnection.ReconnectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;

import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.SimpleLogger;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketDecoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemesDeck;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class EventDecoder
{
    private SocketProtocolTransformer transformer;

    private SocketDecoder decoder;

    private SimpleLogger logger;

    /**
     * EventDecoder Constructor
     */
    public EventDecoder()
    {
        transformer = new SocketProtocolTransformer();
        decoder = new SocketDecoder();
        logger = new SimpleLogger(0, false);
    }

    /**
     * decodes an ArrayList of strings into an event
     * @param toDecode ArrayList of strings to decode
     * @return event decoded
     * @throws InvalidIntArgumentException
     */
    public Event decodeEvent(ArrayList<String> toDecode) throws InvalidIntArgumentException,  it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException, FileNotFoundException {
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

        if(transformer.getArg().equals("ForfaitEvent"))
            return new ForfaitEvent();

        if (transformer.getArg().equals("Initialization2Event"))
            return decodeInitialization2Event(toDecode);

        if(transformer.getArg().equals("ScoreEvent"))
            return decodeScoreEvent(toDecode);

        if(transformer.getArg().equals("ReconnectionEvent"))
            return decodeReconnectionEvent(toDecode);

        else
            return null;

    }

    /**
     * decodes an ArrayList of strings into an scheme selection event
     * @param toDecode ArrayList of strings to decode
     * @return scheme selection event decoded
     * @throws InvalidIntArgumentException
     */
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

    /**
     * decodes an ArrayList of strings into an username event
     * @param toDecode ArrayList of strings to decode
     * @return username event decoded
     * @throws InvalidIntArgumentException
     */

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

    /**
     * decodes an ArrayList of strings into an initialization 2 event
     * @param toDecode ArrayList of strings to decode
     * @return initialization 2 event decoded
     * @throws InvalidIntArgumentException
     */
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

    /**
     * decodes an ArrayList of strings into an model initialization event
     * @param toDecode ArrayList of strings to decode
     * @return model initialization event decoded
     */
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

    /**
     * decodes an ArrayList of strings into a turn event
     * @param toDecode ArrayList of strings to decode
     * @return turn event decoded
     * @throws InvalidIntArgumentException
     */
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

        transformer.simpleDecode(toDecode.get(i));

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
            event.setLastRound(decoder.arrayListDecoder(vectTemp1));
        }
        else
            event.setLastRound(null);
        transformer.simpleDecode(toDecode.get(i));
        event.setNextRound(Boolean.parseBoolean(transformer.getArg()));

        return event;

    }

    /**
     * decodes an ArrayList of strings into a tool card one event
     * @param toDecode ArrayList of strings to decode
     * @return tool card one event decoded
     */

    private ToolCardOneEvent decodeToolCardOneEvent(ArrayList<String> toDecode)
    {
        ToolCardOneEvent event;

        transformer.simpleDecode(toDecode.get(2));

        event = new ToolCardOneEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setX(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setY(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setAction(transformer.getArg().charAt(0));

        transformer.simpleDecode(toDecode.get(7));
        event.setPlayer(Integer.parseInt(transformer.getArg()));

        return event;
    }

    /**
     * decodes an ArrayList of strings into a tool card two / tool card three event
     * @param toDecode ArrayList of strings to decode
     * @return tool card two / tool card three event decoded
     */

    private ToolCardTwoThreeEvent decodeToolCardTwoThreeEvent(ArrayList<String> toDecode)
    {
        ToolCardTwoThreeEvent event;


        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardTwoThreeEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        transformer.simpleDecode(toDecode.get(3));
        event.setX0(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setY0(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setX1(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setY1(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(7));
        event.setPlayer(Integer.parseInt(transformer.getArg()));

        return event;
    }

    /**
     * decodes an ArrayList of strings into a tool card four event
     * @param toDecode ArrayList of strings to decode
     * @return tool card four event decoded
     */

    private ToolCardFourEvent decodeToolCardFourEvent(ArrayList<String> toDecode)
    {
        ToolCardFourEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardFourEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

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

        transformer.simpleDecode(toDecode.get(11));
        event.setPlayer(Integer.parseInt(transformer.getArg()));

        return event;
    }

    /**
     * decodes an ArrayList of strings into a tool card five event
     * @param toDecode ArrayList of strings to decode
     * @return tool card five event decoded
     */

    private ToolCardFiveEvent decodeToolCardFiveEvent(ArrayList<String> toDecode)
    {
        ToolCardFiveEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardFiveEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setTurn(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setPos(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setX(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(7));
        event.setY(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(8));
        event.setPlayer(Integer.parseInt(transformer.getArg()));

        return event;
    }

    /**
     * decodes an ArrayList of strings into a tool card six event
     * @param toDecode ArrayList of strings to decode
     * @return tool card six event decoded
     */

    private ToolCardSixEvent decodeToolCardSixEvent(ArrayList<String> toDecode)
    {
        ToolCardSixEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardSixEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setNewValue(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setX(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setY(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(7));
        event.setApplyOne(Boolean.parseBoolean((transformer.getArg())));

        transformer.simpleDecode(toDecode.get(8));
        event.setApplyTwo(Boolean.parseBoolean((transformer.getArg())));

        transformer.simpleDecode(toDecode.get(9));
        event.setPlayer(Integer.parseInt(transformer.getArg()));

        return event;
    }

    /**
     * decodes an ArrayList of strings into a tool card seven event
     * @param toDecode ArrayList of strings to decode
     * @return tool card seven event decoded
     */

    private ToolCardSevenEvent decodeToolCardSevenEvent(ArrayList<String> toDecode) throws InvalidIntArgumentException
    {
        ToolCardSevenEvent event;
        boolean flag=true;
        int i=3;

        ArrayList<String> vectTemp=new ArrayList();

        transformer.simpleDecode(toDecode.get(2));

        event = new ToolCardSevenEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        if(event.isValidated()) {
            while (flag) {
                transformer.simpleDecode(toDecode.get(i));
                vectTemp.add(toDecode.get(i));
                transformer.simpleDecode(toDecode.get(i + 1));
                if (transformer.getCmd().equals("draft"))
                    flag = false;
                i++;
            }

            event.setDice(decoder.arrayListDecoder(vectTemp));
        }
        return event;
    }

    /**
     * decodes an ArrayList of strings into a tool card eight / tool card nine / tool card ten event
     * @param toDecode ArrayList of strings to decode
     * @return tool card eight / tool card nine / tool card ten event decoded
     */
    private ToolCardEightNineTenEvent decodeToolCardEightNineTenEvent(ArrayList<String> toDecode)
    {
        ToolCardEightNineTenEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardEightNineTenEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setX(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setY(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setPlayer(Integer.parseInt(transformer.getArg()));

        return event;
    }

    /**
     * decodes an ArrayList of strings into a tool card eleven event
     * @param toDecode ArrayList of strings to decode
     * @return tool card eleven event decoded
     */

    private ToolCardElevenEvent decodeToolCardElevenEvent(ArrayList<String> toDecode)
    {
        ToolCardElevenEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardElevenEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        transformer.simpleDecode(toDecode.get(3));
        event.setIndex(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(4));
        event.setNewValue(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(5));
        event.setNewColor(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(6));
        event.setX(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(7));
        event.setY(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(8));
        event.setApplyOne(Boolean.parseBoolean(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(9));
        event.setApplyTwo(Boolean.parseBoolean(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(10));
        event.setFirstCheck(Boolean.parseBoolean(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(11));
        event.setPlayer(Integer.parseInt(transformer.getArg()));

        return event;

    }

    /**
     * decodes an ArrayList of strings into a tool card twelve event
     * @param toDecode ArrayList of strings to decode
     * @return tool card twelve event decoded
     */

    private ToolCardTwelveEvent decodeToolCardTwelveEvent(ArrayList<String> toDecode)
    {
        ToolCardTwelveEvent event;

        transformer.simpleDecode(toDecode.get(2));
        event = new ToolCardTwelveEvent(Integer.parseInt(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

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

        transformer.simpleDecode(toDecode.get(13));
        event.setOnlyOne(Boolean.parseBoolean(transformer.getArg()));

        transformer.simpleDecode(toDecode.get(14));
        event.setPlayer(Integer.parseInt(transformer.getArg()));

        return event;
    }
    /**
     * decodes an ArrayList of strings into a move event
     * @param toDecode ArrayList of strings to decode
     * @return move event decoded
     */

    private MoveEvent decodeMoveEvent(ArrayList<String> toDecode)
    {
        MoveEvent event;
        boolean validate=false;

        transformer.simpleDecode(toDecode.get(1));
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

    /**
     * decodes an ArrayList of strings into a score event
     * @param toDecode ArrayList of strings to decode
     * @return score event decoded
     */

    private ScoreEvent decodeScoreEvent(ArrayList<String> toDecode)
    {
        ScoreEvent event = new ScoreEvent();

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        int i=2;
        while(i<toDecode.size()) {

            transformer.simpleDecode(toDecode.get(i));
            int id = Integer.parseInt(transformer.getArg());
            i++;

            transformer.simpleDecode(toDecode.get(i));
            String name = transformer.getArg();
            i++;

            ScorePlayer player = new ScorePlayer(id, name);

            transformer.simpleDecode(toDecode.get(i));
            player.setPrivObj(Integer.parseInt(transformer.getArg()));
            i++;

            transformer.simpleDecode(toDecode.get(i));
            player.setPubObj(0, Integer.parseInt(transformer.getArg()));
            i++;

            transformer.simpleDecode(toDecode.get(i));
            player.setPubObj(1, Integer.parseInt(transformer.getArg()));
            i++;

            transformer.simpleDecode(toDecode.get(i));
            player.setPubObj(2, Integer.parseInt(transformer.getArg()));
            i++;

            transformer.simpleDecode(toDecode.get(i));
            player.setTokens(Integer.parseInt(transformer.getArg()));
            i++;

            transformer.simpleDecode(toDecode.get(i));
            player.setMinus(Integer.parseInt(transformer.getArg()));
            i++;

            transformer.simpleDecode(toDecode.get(i));
            player.setTot(Integer.parseInt(transformer.getArg()));
            i++;

            event.addPlayer(player);
        }

        return event;
    }

    private ReconnectionEvent decodeReconnectionEvent(ArrayList<String> toDecode) throws InvalidIntArgumentException, it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException, FileNotFoundException {
        ReconnectionEvent event = new ReconnectionEvent();

        int j =2;

        transformer.simpleDecode(toDecode.get(1));
        if(transformer.getArg().equals("true"))
            event.validate();

        transformer.simpleDecode(toDecode.get(j));

        while(!transformer.getCmd().equals("privobj"))
        {
            transformer.simpleDecode(toDecode.get(j));
            j++;

            transformer.simpleDecode(toDecode.get(j));
            String name = transformer.getArg();
            j++;

            transformer.simpleDecode(toDecode.get(j));
            int tokens = Integer.parseInt(transformer.getArg());
            j++;

            transformer.simpleDecode(toDecode.get(j));
            int id= Integer.parseInt(transformer.getArg());
            j++;

            transformer.simpleDecode(toDecode.get(j));
            int fb= Integer.parseInt(transformer.getArg());
            j++;

            SchemesDeck tempDeck = new SchemesDeck();
            SchemeCard tempScheme= tempDeck.extractSchemebyID(id);
            tempScheme.setfb(fb);

            transformer.simpleDecode(toDecode.get(j));
            while(!transformer.getCmd().equals("x"))
            {
                int x = Integer.parseInt(transformer.getArg());
                j++;

                transformer.simpleDecode(toDecode.get(j));
                int y= Integer.parseInt(transformer.getArg());
                j++;

                transformer.simpleDecode(toDecode.get(j));
                Die tempDie= new Die(Integer.parseInt(transformer.getArg()));
                j++;

                transformer.simpleDecode(toDecode.get(j));
                tempDie.setValue(Integer.parseInt(transformer.getArg()));

                tempScheme.setDie(tempDie,x ,y );
                j++;

                transformer.simpleDecode(toDecode.get(j));

            }
            event.addPlayer(name,tokens ,tempScheme );
            transformer.simpleDecode(toDecode.get(j));
        }

        event.addPrivObj(Integer.parseInt(transformer.getArg()));
        j++;

        int[] pubObjs=new int[3];

        transformer.simpleDecode(toDecode.get(j));
        pubObjs[0]= Integer.parseInt(transformer.getArg());
        j++;
        transformer.simpleDecode(toDecode.get(j));
        pubObjs[1]= Integer.parseInt(transformer.getArg());
        j++;
        transformer.simpleDecode(toDecode.get(j));
        pubObjs[2]= Integer.parseInt(transformer.getArg());
        j++;

        event.addPubObjs(pubObjs);

        int[] toolsIds= new int[3];
        transformer.simpleDecode(toDecode.get(j));
        toolsIds[0]= Integer.parseInt(transformer.getArg());
        j++;
        transformer.simpleDecode(toDecode.get(j));
        toolsIds[1]= Integer.parseInt(transformer.getArg());
        j++;
        transformer.simpleDecode(toDecode.get(j));
        toolsIds[2]= Integer.parseInt(transformer.getArg());
        j++;

        event.addToolsIds(toolsIds);

        int[] tokensTools= new int[3];
        transformer.simpleDecode(toDecode.get(j));
        tokensTools[0]= Integer.parseInt(transformer.getArg());
        j++;
        transformer.simpleDecode(toDecode.get(j));
        tokensTools[1]= Integer.parseInt(transformer.getArg());
        j++;
        transformer.simpleDecode(toDecode.get(j));
        tokensTools[2]= Integer.parseInt(transformer.getArg());
        j++;

        event.addToolsTokens(tokensTools);

        while(j<toDecode.size())
        {
            j++;
            transformer.simpleDecode(toDecode.get(j));
            ArrayList<String> tempRdStr = new ArrayList<String>();
            while(!transformer.getCmd().equals("round"))
            {
                tempRdStr.add(toDecode.get(j));
                j++;
                transformer.simpleDecode(toDecode.get(j));
            }

            event.addReconnectionRD(decoder.arrayListDecoder(tempRdStr));
        }
        return event;
    }

}
