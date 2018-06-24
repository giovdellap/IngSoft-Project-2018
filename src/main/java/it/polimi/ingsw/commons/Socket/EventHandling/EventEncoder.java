package it.polimi.ingsw.commons.Socket.EventHandling;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.*;
import it.polimi.ingsw.commons.Events.Initialization.Initialization2Event;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class EventEncoder
{
    private SocketEncoder encoder;
    private SocketProtocolTransformer transformer;

    public EventEncoder()
    {
        encoder = new SocketEncoder();
        transformer = new SocketProtocolTransformer();
    }

    public ArrayList<String> encodeEvent(Event event) throws InvalidIntArgumentException {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(transformer.simpleEncode("event", event.getType()));
        temp.add(transformer.simpleEncode("validated", Boolean.toString(event.isValidated())));

        if(event.getType().equals("UsernameEvent"))
            temp.add(encodeUsernameEvent((UsernameEvent)event));

        if(event.getType().equals("SchemeSelectionEvent"))
            temp.addAll(encodeSchemeSelectionEvent((SchemeSelectionEvent)event));

        if(event.getType().equals("ModelInitializationEvent"))
            temp.addAll(encodeModelInitializationEvent((ModelInitializationEvent)event));

        if(event.getType().equals("Initialization2Event"))
            temp.addAll(encodeInitialization2Event((Initialization2Event)event));

        if(event.getType().equals("ToolCardOneEvent"))
            temp.addAll(encodeToolCardOneEvent((ToolCardOneEvent)event));

        if(event.getType().equals("ToolCardTwoThreeEvent"))
            temp.addAll(encodeToolCardTwoThreeEvent((ToolCardTwoThreeEvent)event));

        if(event.getType().equals("ToolCardFourEvent"))
            temp.addAll(encodeToolCardFourEvent((ToolCardFourEvent)event));

        if(event.getType().equals("ToolCardFiveEvent"))
            temp.addAll(encodeToolCardFiveEvent((ToolCardFiveEvent)event));

        if(event.getType().equals("ToolCardSixEvent"))
            temp.addAll(encodeToolCardSixEvent((ToolCardSixEvent)event));

        if(event.getType().equals("ToolCardSevenEvent"))
            temp.addAll(encodeToolCardSevenEvent((ToolCardSevenEvent)event));

        if(event.getType().equals("ToolCardEightNineTenEvent"))
            temp.addAll(encodeToolCardEightNineTenEvent((ToolCardEightNineTenEvent)event));

        if(event.getType().equals("ToolCardElevenEvent"))
            temp.addAll(encodeToolCardElevenEvent((ToolCardElevenEvent)event));

        if(event.getType().equals("ToolCardTwelveEvent"))
            temp.addAll(encodeToolCardTwelveEvent((ToolCardTwelveEvent)event));

        if(event.getType().equals("TurnEvent"))
            temp.addAll(encodeTurnEvent((TurnEvent)event));

        if(event.getType().equals("MoveEvent"))
            temp.addAll(encodeMoveEvent((MoveEvent)event));

        if(event.getType().equals("PassEvent"))
        {
            temp.add(transformer.simpleEncode("end", "event"));
            return temp;
        }

        if(event.getType().equals("ScoreEvent"))
            temp.addAll(encodeScoreEvent((ScoreEvent) event));


        temp.add(transformer.simpleEncode("end", "event"));

        return temp;
    }


    public String encodeUsernameEvent(UsernameEvent event)
    {
        ArrayList<String> temp = new ArrayList<String>();
        return transformer.simpleEncode("username", event.getUserName());
    }

    public ArrayList<String> encodeSchemeSelectionEvent(SchemeSelectionEvent event)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(event.getId())));

        temp.add(transformer.simpleEncode("fb",Integer.toString(event.getFb())));

        return temp;
    }

    public ArrayList<String> encodeModelInitializationEvent(ModelInitializationEvent event)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id1",Integer.toString(event.getSchemes()[0])));

        temp.add(transformer.simpleEncode("id2",Integer.toString(event.getSchemes()[1])));

        temp.add(transformer.simpleEncode("privObj",Integer.toString(event.getPrivateObjective())));

        for (int i=0;i<event.getPublicObjectives().length;i++)
            temp.add(transformer.simpleEncode("pubObj",Integer.toString(event.getPublicObjectives()[i])));

        //
        for (int j=0; j<event.getToolIds().length;j++)
            temp.add(transformer.simpleEncode("tool",Integer.toString(event.getToolIds()[j])));

        return temp;
    }


    public ArrayList<String> encodeInitialization2Event(Initialization2Event event)
    {
        ArrayList<String> temp = new ArrayList<String>();

        for(int i=0;i<event.getPlayerSize();i++)
        {
            temp.add(transformer.simpleEncode("player",Integer.toString(event.getEventPlayer(i).getId())));
            temp.add(transformer.simpleEncode("username",event.getEventPlayer(i).getName()));
            temp.add(transformer.simpleEncode("scheme",Integer.toString(event.getEventPlayer(i).getSchemeId())));
            temp.add(transformer.simpleEncode("fb",Integer.toString(event.getEventPlayer(i).getFb())));
            temp.add(transformer.simpleEncode("favortokens",Integer.toString(event.getEventPlayer(i).getTokens())));
        }

        return temp;

    }

    public ArrayList<String> encodeToolCardOneEvent(ToolCardOneEvent toolCardOneEvent)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(toolCardOneEvent.getId())));
        temp.add(transformer.simpleEncode("index",Integer.toString(toolCardOneEvent.getIndex())));
        temp.add(transformer.simpleEncode("x",Integer.toString(toolCardOneEvent.getX())));
        temp.add(transformer.simpleEncode("y",Integer.toString(toolCardOneEvent.getY())));
        temp.add(transformer.simpleEncode("action",Character.toString(toolCardOneEvent.getAction())));
        temp.add(transformer.simpleEncode("player",Integer.toString(toolCardOneEvent.getPlayer())));

        return temp;
    }

    public ArrayList<String> encodeToolCardTwoThreeEvent(ToolCardTwoThreeEvent toolCardTwoThreeEvent)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(toolCardTwoThreeEvent.getId())));
        temp.add(transformer.simpleEncode("x0",Integer.toString(toolCardTwoThreeEvent.getX0())));
        temp.add(transformer.simpleEncode("y0",Integer.toString(toolCardTwoThreeEvent.getY0())));
        temp.add(transformer.simpleEncode("x1",Integer.toString(toolCardTwoThreeEvent.getX1())));
        temp.add(transformer.simpleEncode("y1",Integer.toString(toolCardTwoThreeEvent.getY1())));
        temp.add(transformer.simpleEncode("player",Integer.toString(toolCardTwoThreeEvent.getPlayer())));


        return temp;
    }

    public ArrayList<String> encodeToolCardFourEvent(ToolCardFourEvent toolCardFourEvent)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(toolCardFourEvent.getId())));
        temp.add(transformer.simpleEncode("x01",Integer.toString(toolCardFourEvent.getX01())));
        temp.add(transformer.simpleEncode("y01",Integer.toString(toolCardFourEvent.getY01())));
        temp.add(transformer.simpleEncode("x02",Integer.toString(toolCardFourEvent.getX02())));
        temp.add(transformer.simpleEncode("y02",Integer.toString(toolCardFourEvent.getY02())));
        temp.add(transformer.simpleEncode("x11",Integer.toString(toolCardFourEvent.getX11())));
        temp.add(transformer.simpleEncode("y11",Integer.toString(toolCardFourEvent.getY11())));
        temp.add(transformer.simpleEncode("x22",Integer.toString(toolCardFourEvent.getX22())));
        temp.add(transformer.simpleEncode("y22",Integer.toString(toolCardFourEvent.getY22())));
        temp.add(transformer.simpleEncode("player",Integer.toString(toolCardFourEvent.getPlayer())));


        return temp;
    }

    public ArrayList<String> encodeToolCardFiveEvent(ToolCardFiveEvent toolCardFiveEvent)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(toolCardFiveEvent.getId())));
        temp.add(transformer.simpleEncode("index",Integer.toString(toolCardFiveEvent.getIndex())));
        temp.add(transformer.simpleEncode("roundtrackturn",Integer.toString(toolCardFiveEvent.getTurn())));
        temp.add(transformer.simpleEncode("rounddicepos",Integer.toString(toolCardFiveEvent.getPos())));
        temp.add(transformer.simpleEncode("x", Integer.toString(toolCardFiveEvent.getX())));
        temp.add(transformer.simpleEncode("y", Integer.toString(toolCardFiveEvent.getY())));
        temp.add(transformer.simpleEncode("player",Integer.toString(toolCardFiveEvent.getPlayer())));

        return temp;
    }

    public ArrayList<String> encodeToolCardSixEvent(ToolCardSixEvent toolCardSixEvent)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(toolCardSixEvent.getId())));
        temp.add(transformer.simpleEncode("index",Integer.toString(toolCardSixEvent.getIndex())));
        temp.add(transformer.simpleEncode("newValue", Integer.toString(toolCardSixEvent.getNewValue())));
        temp.add(transformer.simpleEncode("x",Integer.toString(toolCardSixEvent.getX())));
        temp.add(transformer.simpleEncode("y",Integer.toString(toolCardSixEvent.getY())));
        temp.add(transformer.simpleEncode("booleanapplyone",Boolean.toString(toolCardSixEvent.isApplyOne())));
        temp.add(transformer.simpleEncode("booleanapplytwo",Boolean.toString(toolCardSixEvent.isApplyTwo())));
        temp.add(transformer.simpleEncode("player",Integer.toString(toolCardSixEvent.getPlayer())));

        return temp;
    }

    public ArrayList<String> encodeToolCardSevenEvent(ToolCardSevenEvent toolCardSevenEvent)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(toolCardSevenEvent.getId())));
        if(toolCardSevenEvent.isValidated()) {
            String[] tempVector = encoder.arrayListEncoder(toolCardSevenEvent.getDice());
            for (int i = 0; i < tempVector.length; i++)
                temp.add(tempVector[i]);
            temp.add(transformer.simpleEncode("draft", "end"));
        }
        temp.add(transformer.simpleEncode("player",Integer.toString(toolCardSevenEvent.getPlayer())));


        return temp;

    }

    public ArrayList<String> encodeToolCardEightNineTenEvent(ToolCardEightNineTenEvent toolCardEightNineTenEvent)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(toolCardEightNineTenEvent.getId())));
        temp.add(transformer.simpleEncode("index",Integer.toString(toolCardEightNineTenEvent.getIndex())));
        temp.add(transformer.simpleEncode("x",Integer.toString(toolCardEightNineTenEvent.getX())));
        temp.add(transformer.simpleEncode("y",Integer.toString(toolCardEightNineTenEvent.getY())));
        temp.add(transformer.simpleEncode("player",Integer.toString(toolCardEightNineTenEvent.getPlayer())));


        return temp;

    }

    public ArrayList<String> encodeToolCardElevenEvent(ToolCardElevenEvent toolCardElevenEvent)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(toolCardElevenEvent.getId())));
        temp.add(transformer.simpleEncode("index",Integer.toString(toolCardElevenEvent.getIndex())));
        temp.add(transformer.simpleEncode("newvalue",Integer.toString(toolCardElevenEvent.getNewValue())));
        temp.add(transformer.simpleEncode("newcolor",Integer.toString(toolCardElevenEvent.getNewColor())));
        temp.add(transformer.simpleEncode("x",Integer.toString(toolCardElevenEvent.getX())));
        temp.add(transformer.simpleEncode("y",Integer.toString(toolCardElevenEvent.getY())));
        temp.add(transformer.simpleEncode("applyOne", Boolean.toString(toolCardElevenEvent.isApplyOne())));
        temp.add(transformer.simpleEncode("applyTwo", Boolean.toString(toolCardElevenEvent.isApplyTwo())));
        temp.add(transformer.simpleEncode("firstCheck", Boolean.toString(toolCardElevenEvent.isFirstCheck())));
        temp.add(transformer.simpleEncode("player",Integer.toString(toolCardElevenEvent.getPlayer())));

        return temp;

    }

    public ArrayList<String> encodeToolCardTwelveEvent(ToolCardTwelveEvent toolCardTwelveEvent)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(toolCardTwelveEvent.getId())));
        temp.add(transformer.simpleEncode("roundtrackturn",Integer.toString(toolCardTwelveEvent.getTurn())));
        temp.add(transformer.simpleEncode("rounddicepos",Integer.toString(toolCardTwelveEvent.getPos())));
        temp.add(transformer.simpleEncode("x01",Integer.toString(toolCardTwelveEvent.getX01())));
        temp.add(transformer.simpleEncode("y01",Integer.toString(toolCardTwelveEvent.getY01())));
        temp.add(transformer.simpleEncode("x02",Integer.toString(toolCardTwelveEvent.getX02())));
        temp.add(transformer.simpleEncode("y02",Integer.toString(toolCardTwelveEvent.getY02())));
        temp.add(transformer.simpleEncode("x11",Integer.toString(toolCardTwelveEvent.getX11())));
        temp.add(transformer.simpleEncode("y11",Integer.toString(toolCardTwelveEvent.getY11())));
        temp.add(transformer.simpleEncode("x22",Integer.toString(toolCardTwelveEvent.getX22())));
        temp.add(transformer.simpleEncode("y22",Integer.toString(toolCardTwelveEvent.getY22())));
        temp.add(transformer.simpleEncode("onlyOne", Boolean.toString(toolCardTwelveEvent.isOnlyOne())));
        temp.add(transformer.simpleEncode("player",Integer.toString(toolCardTwelveEvent.getPlayer())));


        return temp;

    }

    public ArrayList<String> encodeTurnEvent(TurnEvent event)
    {
        ArrayList<String> temp = new ArrayList<String>();
        int i;

        int j;

        temp.add(transformer.simpleEncode("round",Integer.toString(event.getRound())));

        temp.add(transformer.simpleEncode("noDisconnected",Boolean.toString(event.getNoDisconnected())));
        if(!event.getNoDisconnected())
            for (String temp1: event.getDisconnected())
                temp.add(transformer.simpleEncode("disconnected",temp1));

        temp.add(transformer.simpleEncode("active", Integer.toString((event.getActive()))));

        for(j=0;j<encoder.arrayListEncoder(event.getDraft()).length;j++)
            temp.add(encoder.arrayListEncoder(event.getDraft())[j]);

        for (Integer temp1: event.getToolsUpdate())
            temp.add(transformer.simpleEncode("toolsUpdate",Integer.toString(temp1)));

        temp.add(transformer.simpleEncode("myTurn",Boolean.toString(event.itsMyTurn())));
        if(event.getLastRound()!=null)
            for(int k=0;k<encoder.arrayListEncoder(event.getLastRound()).length;k++)
                temp.add(encoder.arrayListEncoder(event.getLastRound())[k]);

        temp.add(transformer.simpleEncode("nextRound",Boolean.toString(event.isNextRound())));

        return temp;

    }

    public ArrayList<String> encodeMoveEvent(MoveEvent event)          
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(event.getId())));
        temp.add(transformer.simpleEncode("index",Integer.toString(event.getIndex())));
        temp.add(transformer.simpleEncode("x",Integer.toString(event.getX())));
        temp.add(transformer.simpleEncode("y",Integer.toString(event.getY())));

        return temp;

    }

    public ArrayList<String> encodeScoreEvent(ScoreEvent event) throws InvalidIntArgumentException {
        ArrayList<String> temp = new ArrayList<String>();

        for(ScorePlayer player : event.getPlayers())
        {
            temp.add(transformer.simpleEncode("id", Integer.toString(player.getId())));
            temp.add(transformer.simpleEncode("name", player.getName()));
            temp.add(transformer.simpleEncode("privobj", Integer.toString(player.getPrivObj())));
            temp.add(transformer.simpleEncode("pubobj0", Integer.toString(player.getPubObj(0))));
            temp.add(transformer.simpleEncode("pubobj1", Integer.toString(player.getPubObj(1))));
            temp.add(transformer.simpleEncode("pubobj2", Integer.toString(player.getPubObj(2))));
            temp.add(transformer.simpleEncode("tokens", Integer.toString(player.getTokens())));
            temp.add(transformer.simpleEncode("minus", Integer.toString(player.getMinus())));
            temp.add(transformer.simpleEncode("tot", Integer.toString(player.getTot())));
        }
        return temp;
    }





}
