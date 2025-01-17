package it.polimi.ingsw.commons.Socket.EventHandling;

import it.polimi.ingsw.commons.Events.*;
import it.polimi.ingsw.commons.Events.Disconnection.ReconnectionEvent;
import it.polimi.ingsw.commons.Events.Disconnection.ReconnectionPlayer;
import it.polimi.ingsw.commons.Events.Initialization.*;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketEncoder;
import it.polimi.ingsw.commons.Socket.SocketTools.SocketProtocolTransformer;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

import java.util.ArrayList;

public class EventEncoder
{
    private SocketEncoder encoder;
    private SocketProtocolTransformer transformer;

    /**
     * EventEncoder Constructor
     */
    public EventEncoder()
    {
        encoder = new SocketEncoder();
        transformer = new SocketProtocolTransformer();
    }

    /**
     * encodes an event
     * @param event event to encode
     * @return ArrayList of strings containing information about the encoded event
     * @throws InvalidIntArgumentException
     */
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

        if(event.getType().equals("ScoreEvent"))
            temp.addAll(encodeScoreEvent((ScoreEvent) event));

        if(event.getType().equals("ReconnectionEvent"))
            temp.addAll(encodeReconnectionEvent((ReconnectionEvent)event));
        if(event.getType().equals("PersonalSchemeEvent"))
            temp.addAll(encodePersonalSchemeEvent(((PersonalSchemeEvent)event)));


        temp.add(transformer.simpleEncode("end", "event"));

        return temp;
    }

    /**
     * encodes an username event
     * @param event event to encode
     * @return string containing information about the encoded event
     */
    public String encodeUsernameEvent(UsernameEvent event)
    {
        return transformer.simpleEncode("username", event.getUserName());
    }

    /**
     * encodes a scheme selection event
     * @param event event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
    public ArrayList<String> encodeSchemeSelectionEvent(SchemeSelectionEvent event)
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(event.getId())));

        temp.add(transformer.simpleEncode("fb",Integer.toString(event.getFb())));

        return temp;
    }

    /**
     * encodes a model initialization event
     * @param event event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes an initialization 2 event
     * @param event event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes a tool card one event
     * @param toolCardOneEvent event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes a tool card two / tool card three event
     * @param toolCardTwoThreeEvent event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes a tool card four event
     * @param toolCardFourEvent event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes a tool card five event
     * @param toolCardFiveEvent event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes a tool card six event
     * @param toolCardSixEvent event to encode
     * @return ArrayList of strings containing information about the encoded event
     */

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

    /**
     * encodes a tool card seven event
     * @param toolCardSevenEvent event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes a tool card eight / tool card nine / tool card ten event
     * @param toolCardEightNineTenEvent event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes a tool card eleven event
     * @param toolCardElevenEvent event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes a tool card twelve event
     * @param toolCardTwelveEvent event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
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

    /**
     * encodes a turn event
     * @param event event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
    public ArrayList<String> encodeTurnEvent(TurnEvent event)
    {
        ArrayList<String> temp = new ArrayList<String>();
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

    /**
     * encodes a move event
     * @param event event to encode
     * @return ArrayList of strings containing information about the encoded event
     */
    public ArrayList<String> encodeMoveEvent(MoveEvent event)          
    {
        ArrayList<String> temp = new ArrayList<String>();

        temp.add(transformer.simpleEncode("id",Integer.toString(event.getId())));
        temp.add(transformer.simpleEncode("index",Integer.toString(event.getIndex())));
        temp.add(transformer.simpleEncode("x",Integer.toString(event.getX())));
        temp.add(transformer.simpleEncode("y",Integer.toString(event.getY())));

        return temp;

    }

    /**
     * encodes a score event
     * @param event event to encode
     * @return ArrayList of strings containing information about the encoded event
     */

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

    public ArrayList<String> encodeReconnectionEvent(ReconnectionEvent event)
    {
        ArrayList<String> temp = new ArrayList<String>();
        for(int i=0;i<event.getPlayers().size();i++)
        {
            ReconnectionPlayer player = event.getPlayers().get(i);
            temp.add(transformer.simpleEncode("player", Integer.toString(i)));
            temp.add(transformer.simpleEncode("name", player.getName()));
            temp.add(transformer.simpleEncode("tokens", Integer.toString(player.getTokens())));
            temp.add(transformer.simpleEncode("id", Integer.toString(player.getSchemeId())));
            temp.add(transformer.simpleEncode("fb", Integer.toString(player.getFb())));
            ArrayList<ReconnectionPlayer.ReconnectionSchemeDie> tempArr = player.getSchemeDice();
            for(int j=0;j<player.getSchemeDice().size();j++)
            {
                temp.add(transformer.simpleEncode("x", Integer.toString(tempArr.get(j).getX())));
                temp.add(transformer.simpleEncode("y", Integer.toString(tempArr.get(j).getY())));
                temp.add(transformer.simpleEncode("color", Integer.toString(tempArr.get(j).getColor())));
                temp.add(transformer.simpleEncode("value", Integer.toString(tempArr.get(j).getValue())));
            }
        }

        temp.add(transformer.simpleEncode("privobj", Integer.toString(event.getPrivObj())));
        temp.add(transformer.simpleEncode("pubobj0", Integer.toString(event.getPubObjs()[0])));
        temp.add(transformer.simpleEncode("pubobj1", Integer.toString(event.getPubObjs()[1])));
        temp.add(transformer.simpleEncode("pubobj2", Integer.toString(event.getPubObjs()[2])));
        temp.add(transformer.simpleEncode("toolid0", Integer.toString(event.getToolsIds()[0])));
        temp.add(transformer.simpleEncode("toolid1", Integer.toString(event.getToolsIds()[1])));
        temp.add(transformer.simpleEncode("toolid2", Integer.toString(event.getToolsIds()[2])));
        temp.add(transformer.simpleEncode("tokenstool0", Integer.toString(event.getToolsTokens()[0])));
        temp.add(transformer.simpleEncode("tokenstool1", Integer.toString(event.getToolsTokens()[1])));
        temp.add(transformer.simpleEncode("tokenstool2", Integer.toString(event.getToolsTokens()[2])));

        ArrayList<ReconnectionEvent.ReconnectionRoundDice> tempRD = event.getReconnectionTrack();
        for(int z=0;z<tempRD.size();z++)
        {
            temp.add(transformer.simpleEncode("round", Integer.toString(z)));
            for(int h=0;h<tempRD.get(z).getRd().size();h++)
                temp.add(encoder.arrayListEncoder(tempRD.get(z).getRd())[h]);
        }

        return temp;
    }

    public ArrayList<String> encodePersonalSchemeEvent(PersonalSchemeEvent event) throws InvalidIntArgumentException {
        ArrayList<String> temp = new ArrayList<String>();
        temp.add(transformer.simpleEncode("playerId", Integer.toString(event.getPlayer())));

        for(int x=0;x<4;x++)
        {
            for(int y=0;y<5;y++)
            {
                if(event.getScheme().getCell(1, x, y)!=0)
                {
                    temp.add(transformer.simpleEncode("x", Integer.toString(x)));
                    temp.add(transformer.simpleEncode("y", Integer.toString(y)));
                    temp.add(transformer.simpleEncode("cell", Integer.toString(event.getScheme().getCell(1, x, y))));
                }
            }
        }
        return temp;
    }

}
