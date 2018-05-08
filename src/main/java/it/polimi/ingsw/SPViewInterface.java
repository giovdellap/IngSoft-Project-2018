package it.polimi.ingsw;

import it.polimi.ingsw.ModelComponentsSP.DraftPoolSP;
import it.polimi.ingsw.ModelComponentsSP.SchemeCardSP;

import java.util.*;


public interface SPViewInterface {


    public void setPrivObjs(int id1, int id2);


    public void setPossibleSchemes(int id1, int id2);


    public void setPublicObjectives(int id1, int id2);


    public void setRound(int round);


    public void setTurn(int turn);


    public void refreshDraftPool(DraftPoolSP dp);


    public void setScore(int score);


    public void move();


    public void useTool();


    public void refreshScheme(SchemeCardSP sc);


    public void getScheme(int id, int fb);


    public void getDiff(int diff);

}