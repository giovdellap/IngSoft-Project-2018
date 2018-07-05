package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.commons.Die;
import it.polimi.ingsw.commons.Events.ToolsEvents.ToolCardEvent;
import it.polimi.ingsw.commons.Exceptions.FullDataStructureException;
import it.polimi.ingsw.commons.Exceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.server.*;

import java.io.IOException;

public abstract class ToolCard {

    private int id;
    private int favorTokens;
    private String toolCardName;
    private ToolCardUsageRecord toolCardUsageRecord;
    Model modelInstance;
    PlayerThread player;
    CheckingMethods checkingMethods;
    TurnManager turnManager;

    /**
     * ToolCard Abstract Constructor
     */
    public ToolCard() {

        favorTokens=0;
        toolCardName="";
        checkingMethods = new CheckingMethods();

    }

    public void setTurnManager(TurnManager turnManager) {
        this.turnManager = turnManager;
    }

    public TurnManager getTurnManager() {
        return turnManager;
    }

    public Model getModel() {
        return modelInstance;
    }

    public PlayerThread getPlayer() {
        return player;
    }

    public ToolCardUsageRecord getToolCardUsageRecord() {
        return toolCardUsageRecord;
    }


    /**
     * sets tool card name
     * @param name name
     */
    public void setToolCardName (String name) {
        this.toolCardName=name;
    }

    /**
     * gets tool card name
     * @return name
     */
    public String getToolCardName () {
        return this.toolCardName;
    }

    /**
     * sets tool card favor tokens
     * @param tokens
     */
    public void setFavorTokens (int tokens) {
        this.favorTokens = tokens;
    }

    /**
     * gets tool card favor tokens
     * @return
     */

    public int getFavorTokens() {
        return this.favorTokens;
    }

    /**
     * sets id to tool card
     * @param id id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * gets tool card id
     * @return id
     */
    public int getId() {
        return id;
    }

    public abstract ToolCardEvent useTool(ToolCardEvent currentEvent) throws InvalidIntArgumentException, GenericInvalidArgumentException, FullDataStructureException, IOException;

    public void setModel(Model model) {
        this.modelInstance = model;
    }

    public void setPlayer(PlayerThread player) {
        this.player = player;
    }

    public void setToolCardUsageRecord(ToolCardUsageRecord toolCardUsageRecord) {
        this.toolCardUsageRecord = toolCardUsageRecord;
    }

    public boolean afterDraftingCheck(Die toPlace, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {
        if(!player.getIPlayedFirstMove())
            return checkingMethods.checkFirstMove(modelInstance.getSchemebyIndex(player.getId()), toPlace, x, y);
        else
            return checkingMethods.checkMove(modelInstance.getSchemebyIndex(player.getId()), toPlace, x, y);

    }
}
