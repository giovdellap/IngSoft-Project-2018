package it.polimi.ingsw.server.ToolCards;

import it.polimi.ingsw.server.ModelComponent.Die;
import it.polimi.ingsw.server.ModelComponent.DraftPool;
import it.polimi.ingsw.server.ModelComponent.SchemeCard;
import it.polimi.ingsw.server.ServerExceptions.GenericInvalidArgumentException;
import it.polimi.ingsw.server.ServerExceptions.InvalidIntArgumentException;

public class ToolCardTen extends ToolCard {

    DraftPool draft;
    SchemeCard scheme;

    public ToolCardTen() {
        setToolCardName("Grinding Stone");
    }

    public void setDraft(DraftPool d) {
        draft = d;
    }

    public void setScheme(SchemeCard s) {
        scheme = s;
    }

    public boolean checkToolCardTen(DraftPool draft, int pos, SchemeCard scheme, int x, int y) throws GenericInvalidArgumentException, InvalidIntArgumentException {

        Die toPlace = draft.returnDie(pos);

        if(toPlace.getValue()==1)
            toPlace.setValueTest(6);
        if(toPlace.getValue()==2)
            toPlace.setValueTest(5);
        if(toPlace.getValue()==3)
            toPlace.setValueTest(4);
        if(toPlace.getValue()==4)
            toPlace.setValueTest(3);
        if(toPlace.getValue()==5)
            toPlace.setValueTest(2);
        if(toPlace.getValue()==6)
            toPlace.setValueTest(1);


        if (toPlace == null || scheme == null || draft.returnDie(pos).isDisabled())
            throw new GenericInvalidArgumentException();

        if (x < 0 || x > 3 || y < 0 || y > 4)
            throw new InvalidIntArgumentException();

        if (!scheme.getDie(x, y).isDisabled()) {
            System.out.println("That position is already occupied");
            return false;
        }


        boolean flag = false;

        for (int i = -1; i < 2; i++)
            for (int j = -1; j < 2; j++)
                if ((x + i < 4) && (x + i > -1) && (y + j < 5) && (y + j > -1))
                    if(!scheme.getDie(x + i, y + j).isDisabled())
                        flag = true;


        if (!flag) {
            System.out.println("You must position your die orthogonally or diagonally adjacent to another die");
            return false;
        }


        if (scheme.getCell(scheme.getfb(), x, y) > 0 && scheme.getCell(scheme.getfb(), x, y) < 6 && toPlace.getColor() != scheme.getCell(scheme.getfb(), x, y)) {
            System.out.println("You must position your die on the same color cell of your scheme");
            return false;
        }


        if (scheme.getCell(scheme.getfb(), x, y) > 5 && scheme.getCell(scheme.getfb(), x, y) < 12 && toPlace.getValue() != (scheme.getCell(scheme.getfb(), x, y) - 5)) {
            System.out.println("You must position your die on the same number cell of your scheme");
            return false;
        }


        if (x + 1 < 4) {
            if (!scheme.getDie(x + 1, y).isDisabled())
                if (scheme.getDie(x + 1, y).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x + 1, y).isDisabled())
                if (scheme.getDie(x + 1, y).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (x - 1 > -1) {
            if (!scheme.getDie(x - 1, y).isDisabled())
                if (scheme.getDie(x - 1, y).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x - 1, y).isDisabled())
                if (scheme.getDie(x - 1, y).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y + 1 < 5) {
            if (!scheme.getDie(x, y + 1).isDisabled())
                if (scheme.getDie(x, y + 1).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x, y + 1).isDisabled())
                if (scheme.getDie(x, y + 1).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        if (y - 1 > -1) {
            if (!scheme.getDie(x, y - 1).isDisabled())
                if (scheme.getDie(x, y - 1).getColor() == toPlace.getColor()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same color");
                    return false;
                }
            if (!scheme.getDie(x, y - 1).isDisabled())
                if (scheme.getDie(x, y - 1).getValue() == toPlace.getValue()) {
                    System.out.println("You can't position your die orthogonally adjacent to another die of the same value");
                    return false;
                }
        }

        return true;

    }


    public void applyModifies(int pos, int x, int y) throws InvalidIntArgumentException, GenericInvalidArgumentException {

        Die toPlace = draft.returnDie(pos);

        boolean changed=false;

        if(toPlace.getValue()==1 && !changed)
        {
            toPlace.setValueTest(6);
            changed=true;
        }

        if(toPlace.getValue()==2 && !changed) {
            toPlace.setValueTest(5);
            changed=true;
        }

        if(toPlace.getValue()==3 && !changed) {
            toPlace.setValueTest(4);
            changed=true;
        }

        if(toPlace.getValue()==4 && !changed) {
            toPlace.setValueTest(3);
            changed=true;
        }

        if(toPlace.getValue()==5 && !changed) {
            toPlace.setValueTest(2);
            changed=true;
        }

        if(toPlace.getValue()==6 && !changed) {
            toPlace.setValueTest(1);
            changed=true;
        }

        scheme.setDie(toPlace,x,y);

        draft.pickUpDie(pos);

    }


    public DraftPool getDraft() {
        return draft;
    }

    public SchemeCard getScheme() {
        return scheme;
    }



}
