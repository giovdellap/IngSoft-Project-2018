package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.DraftPool;

public class ToolCardEleven extends ToolCard {

    public ToolCardEleven() {
        setToolCardName("Flux Remover");
        setId(11);
    }

    public boolean checkToolCardEleven(DraftPool draft, int pos, int newValue) {

        return true;
    }

    public void applyModifies() {

    }
}
