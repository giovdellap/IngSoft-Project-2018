package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.DraftPool;

public class ToolCardEleven extends ToolCard {

    public ToolCardEleven() {
        setToolCardName("Flux Remover");
    }

    public boolean checkToolCardEleven(DraftPool draft, int pos, int newValue) {

        return true;
    }

    public void applyModifies() {

    }
}
