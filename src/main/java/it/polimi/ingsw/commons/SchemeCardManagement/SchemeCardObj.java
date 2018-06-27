package it.polimi.ingsw.commons.SchemeCardManagement;

import java.util.ArrayList;
import java.util.List;

public class SchemeCardObj {

    //attribute
    private List<FrontOrBackObj> schemeCard =new ArrayList<FrontOrBackObj>();

    //get and set
    public List<FrontOrBackObj> getResult() {return schemeCard;}

    public void setResult(List<FrontOrBackObj> schemeCard1) {this.schemeCard = schemeCard1;}



}
