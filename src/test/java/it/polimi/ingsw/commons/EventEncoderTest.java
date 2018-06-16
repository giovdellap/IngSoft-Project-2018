package it.polimi.ingsw.commons;

import it.polimi.ingsw.commons.Events.Event;
import it.polimi.ingsw.commons.Events.Initialization.ModelInitializationEvent;
import it.polimi.ingsw.commons.Events.Initialization.SchemeSelectionEvent;
import it.polimi.ingsw.commons.Events.Initialization.UsernameEvent;
import it.polimi.ingsw.commons.Events.MoveEvent;
import it.polimi.ingsw.commons.Events.PassEvent;
import it.polimi.ingsw.commons.Events.TurnEvent;
import it.polimi.ingsw.commons.Socket.EventHandling.EventDecoder;
import it.polimi.ingsw.commons.Socket.EventHandling.EventEncoder;
import it.polimi.ingsw.server.ModelComponent.PublicObjective;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.ToolCardOne;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;

import java.util.ArrayList;

public class EventEncoderTest
{
    EventEncoder encoder;
    EventDecoder decoder;
    UsernameEvent event;
    SchemeSelectionEvent event1;
    ModelInitializationEvent event2;
    TurnEvent event3;

    ToolCardOneEvent eventT1;
    ToolCardTwoThreeEvent eventT23;
    ToolCardFourEvent eventT4;
    ToolCardFiveEvent eventT5;
    ToolCardSixEvent eventT6;
    ToolCardSevenEvent eventT7;
    ToolCardEightNineTenEvent eventT8910;
    ToolCardElevenEvent eventT11;
    ToolCardTwelveEvent eventT12;
    MoveEvent eventM;
    Event eventP;

    @BeforeEach public void setUp() throws InvalidIntArgumentException
    {
        encoder=new EventEncoder();
        decoder=new EventDecoder();
        event = new UsernameEvent("ciccio");
        event1= new SchemeSelectionEvent(3,2);
        event2= new ModelInitializationEvent();
        event3 = new TurnEvent();

        eventT1=new ToolCardOneEvent(1);
        eventT23=new ToolCardTwoThreeEvent(1);
        eventT4=new ToolCardFourEvent(1);
        eventT5=new ToolCardFiveEvent(1);
        eventT6=new ToolCardSixEvent(1);
        eventT7=new ToolCardSevenEvent(1);
        eventT8910=new ToolCardEightNineTenEvent(1);
        eventT11=new ToolCardElevenEvent(1);
        eventT12=new ToolCardTwelveEvent(1);

        eventM=new MoveEvent(2,3,4);
        eventP=new PassEvent();

    }

    @Test
    public void checkUsernameEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(event);
        Event tempEvent = decoder.decodeEvent(tempArray);

        UsernameEvent e= (UsernameEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );
    }

    @Test
    public void checkSchemeSelectionEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(event1);
        Event tempEvent = decoder.decodeEvent(tempArray);

        SchemeSelectionEvent e= (SchemeSelectionEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );
    }

    @Test
    public void checkModelInitializationEvent() throws InvalidIntArgumentException
    {
        int [] pubObjs = {2, 3, 4, 5};
        int [] toolsIds = {2, 3, 4, 5};

        event2.setPublicObjectives(pubObjs);
        event2.setToolIds(toolsIds);

        ArrayList<String> tempArray = encoder.encodeEvent(event2);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ModelInitializationEvent e= (ModelInitializationEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );
    }

    @Test
    public void checkTurnEvent() throws InvalidIntArgumentException
    {
        // create tempDisc
        ArrayList<String> tempDisc= new ArrayList<String>();

        String a="pippo";
        String b="guido";

        tempDisc.add(a);
        tempDisc.add(b);

        event3.setDisconnected(tempDisc);

        // create tempDraft
        ArrayList<Die> tempDraft=new ArrayList<Die>();

        Die die= new Die(3);
        die.throwDie();

        Die die1=new Die(5);
        die1.throwDie();

        tempDraft.add(die);
        tempDraft.add(die1);

        event3.setDraft(tempDraft);

        // create tempTools
        int [] toolsIds = {2, 3, 4, 5};

        event3.setToolsUpdate(toolsIds);

        // create lastRound (recycle tempdraft)
        event3.setLastRound(tempDraft);

        //setActive
        event3.setActive(3);

        //test
        ArrayList<String> tempArray = encoder.encodeEvent(event3);
        Event tempEvent = decoder.decodeEvent(tempArray);

        TurnEvent e= (TurnEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );
    }

    @Test
    public void checkToolCardOneEvents() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventT1);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ToolCardOneEvent e= (ToolCardOneEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkToolCardTwoThreeEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventT23);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ToolCardTwoThreeEvent e= (ToolCardTwoThreeEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkToolCardFourEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventT4);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ToolCardFourEvent e= (ToolCardFourEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkToolCardFiveEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventT5);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ToolCardFiveEvent e= (ToolCardFiveEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkToolCardSixEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventT6);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ToolCardSixEvent e= (ToolCardSixEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkToolCardSevenEvent() throws InvalidIntArgumentException
    {
        //create tempdraft

        ArrayList<Die> tempDraft=new ArrayList<Die>();

        Die die= new Die(3);
        die.throwDie();

        Die die1=new Die(5);
        die1.throwDie();

        tempDraft.add(die);
        tempDraft.add(die1);

        eventT7.setDice(tempDraft);

        ArrayList<String> tempArray = encoder.encodeEvent(eventT7);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ToolCardSevenEvent e= (ToolCardSevenEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkToolCardEightNineTenEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventT8910);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ToolCardEightNineTenEvent e= (ToolCardEightNineTenEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkToolCardElevenEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventT11);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ToolCardElevenEvent e= (ToolCardElevenEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkToolCardTwelveEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventT12);
        Event tempEvent = decoder.decodeEvent(tempArray);

        ToolCardTwelveEvent e= (ToolCardTwelveEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkMoveEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventM);
        Event tempEvent = decoder.decodeEvent(tempArray);

        MoveEvent e= (MoveEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }

    @Test
    public void checkPassEvent() throws InvalidIntArgumentException
    {
        ArrayList<String> tempArray = encoder.encodeEvent(eventP);
        Event tempEvent = decoder.decodeEvent(tempArray);

        PassEvent e= (PassEvent)tempEvent;

        Assertions.assertEquals(tempEvent, e );

    }
}
