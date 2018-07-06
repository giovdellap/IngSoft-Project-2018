package it.polimi.ingsw.server;

import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.ToolCards.*;

import java.io.IOException;

public class TurnActionHandler
{
    private Model modelInstance;
    private PlayerThread player;
    private ToolCardUsageRecord toolRecord;
    private TurnManager turnManager;



    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public ToolCardEvent useTool(ToolCardEvent event) throws InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException, IOException {

        //tokens check
        int tokens = toolRecord.checkUsage(player.getTokens(), event.getId());

        if(tokens==0)
            return event;

        ToolCard card = toolRecord.getCard(event.getId());

        card.setModel(modelInstance);
        card.setPlayer(player);
        card.setToolCardUsageRecord(toolRecord);
        card.setTurnManager(turnManager);

        ToolCardEvent toReturn = card.useTool(event);

        modelInstance = card.getModel();
        player = card.getPlayer();
        toolRecord = card.getToolCardUsageRecord();
        turnManager = card.getTurnManager();

        if(toReturn.isValidated()) {
            toolRecord.applyUsage(event.getId());
            int previousTokens = player.getTokens();

            player.setTokens(previousTokens-tokens);
        }

        return toReturn;
    }

        public void setModel(Model model)
    {
        this.modelInstance=model;
    }

    public void setPlayer(PlayerThread player)
    {
        this.player=player;
    }

    public void setToolRecord(ToolCardUsageRecord toolRecord) {
        this.toolRecord = toolRecord;
    }

    public Model getModel()
    {
        return modelInstance;
    }

    public PlayerThread getPlayer()
    {
        return player;
    }

    public ToolCardUsageRecord getToolRecord() {
        return toolRecord;
    }


}
