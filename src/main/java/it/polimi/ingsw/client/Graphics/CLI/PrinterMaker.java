package it.polimi.ingsw.client.Graphics.CLI;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;
import it.polimi.ingsw.client.ModelComponentsMP.*;
import it.polimi.ingsw.client.PlayerClient;
import it.polimi.ingsw.commons.Events.ScoreEvent;
import it.polimi.ingsw.commons.Events.ScorePlayer;
import it.polimi.ingsw.commons.Events.ToolsEvents.*;
import it.polimi.ingsw.commons.SchemeCardManagement.SchemeCard;

import java.util.ArrayList;

public class PrinterMaker
{
    
    private ModelGenerator modelGenerator;
    private CLIToolsManager cliToolsManager;
    private ComponentFactory componentFactory;
    private int [] toolsId;

    private final int width;

    /**
     * PrinterMaker Constructor
     * @param z
     */
    public PrinterMaker(int z)
    {
        modelGenerator = new ModelGenerator();
        cliToolsManager = new CLIToolsManager();
        componentFactory = new ComponentFactory();

        if (z==0) width=40;
        else width=80;
    }

    /**
     * gets the username insertion
     * @return
     */
    public String[] getUsernameInsertion()
    {
        //returns a string[30] width width asking for username insertion

        String[] temp = cliToolsManager.blankLinesInitializer(20, 10, width);
        temp[10]=cliToolsManager.printSpaces(2)+"INSERISCI USERNAME:"+cliToolsManager.printSpaces(19);

        return cliToolsManager.blankLinesEnder(temp, 11);
    }

    /**
     * gets the beginning scheme selection scene
     * @param scheme1
     * @param scheme2
     * @param username
     * @param privObj
     * @param pubObjs
     * @param tools
     * @return
     * @throws InvalidIntArgumentException
     */
    public String[] getSelectionScene(SchemeCard scheme1, SchemeCard scheme2, String username, PrivateObjectiveMP privObj, PublicObjectiveMP[] pubObjs, int[] tools) throws InvalidIntArgumentException
    {
        //returns the final selection scene string widthx21
        String[] temp = new String[21];

        toolsId=tools;

        int j;

        //Gs ROW
        for(int i=0;i<7;i++)
            temp[i]=componentFactory.selectionG(scheme1, 1, 1)[i]+""+componentFactory.selectionG(scheme1, 2, 2)[i]+""+componentFactory.selectionG(scheme2,1,3)[i]+""+componentFactory.selectionG(scheme2,2,4)[i];

        //BLANK LINE
        temp[7]=cliToolsManager.printSpaces(width);


        //C-D
        for(int i=8;i<13;i++)
        {
            j=0;
            temp[i] = componentFactory.selectionC(privObj, username, 0)[i-8];

            for (j=0;j<3;j++)
                temp[i]+=componentFactory.selectionD(pubObjs[j])[i-8];
        }


        //C-T
        j=0;

        for (int i=13;i<18;i++)
        {
            j=0;
            temp[i] = componentFactory.selectionC(privObj, username, 0)[i-8] + componentFactory.selectionT(toolsId[j],0)[i-13];

            for (j=1;j<3;j++)
                temp[i]+=componentFactory.selectionT(toolsId[j],0)[i-13];
        }


        //I ROW
        for(int i=18;i<21;i++)
            temp[i]=componentFactory.selectionI()[i-18];


        return temp;
    }

    /**
     * gets the game Scene from ComponentFactory
     * @param players
     * @param draft
     * @param round
     * @param privObj
     * @param pubObjs
     * @param toolsTokens
     * @param activePlayers
     * @param me
     * @return
     * @throws InvalidIntArgumentException
     */

    public String[] getGameScene(PlayerClient[] players, DraftPoolMP draft, RoundTrackMP round, PrivateObjectiveMP privObj, PublicObjectiveMP[]pubObjs, int[]toolsTokens, int activePlayers, int me) throws InvalidIntArgumentException
    {
        ArrayList<String> temp;
        temp=new ArrayList<String>();

        for (int i=0;i<7;i++) {
            String that;
            that = componentFactory.selectionA(players[0].getPlayerScheme(), players[0].getName(), players[0].getTokens())[i] + componentFactory.selectionA(players[1].getPlayerScheme(), players[1].getName(), players[1].getTokens())[i];

            if (players.length == 3)
                that+=(componentFactory.selectionA(players[2].getPlayerScheme(), players[2].getName(), players[2].getTokens())[i]);
            if (players.length == 4)
                that+=(componentFactory.selectionA(players[2].getPlayerScheme(), players[2].getName(), players[2].getTokens())[i] + componentFactory.selectionA(players[3].getPlayerScheme(), players[3].getName(), players[3].getTokens())[i]);

            temp.add(that);
        }
        for (int i =0;i<3;i++)
            temp.add(componentFactory.selectionN(draft)[i]);

        for (int i =0;i<componentFactory.selectionM(round).length;i++) {
            temp.add(componentFactory.selectionM(round)[i]);

        }

        for (int i=0;i<5;i++)
        {
            String pippo;
            pippo=componentFactory.selectionC(privObj,players[me].getName(),players[me].getTokens())[i];

            for (int j=0;j<3;j++)
                pippo+=componentFactory.selectionD(pubObjs[j])[i];

            temp.add(pippo);

        }

        for (int i=0;i<5;i++)
        {
            String pluto;
            pluto=componentFactory.selectionC(privObj,players[me].getName(),players[me].getTokens())[i+5];

            for (int j=0;j<3;j++)
                pluto+=componentFactory.selectionT(toolsId[j],toolsTokens[j])[i];

            temp.add(pluto);
        }

        String[] tempArray= new String[temp.size()];

        for (int i=0;i<temp.size();i++)
            tempArray[i]=temp.get(i);

        return tempArray;

    }

    public void setToolsIdTest(int[]tools)
    {
        toolsId=tools;
    }

    public String round(int numround,String activeUsername)
    {
        String temp = new String(cliToolsManager.centerThatString(("ROUND: "+numround+"  "+"TURNO: "+activeUsername),width));

        return temp;
    }

    /**
     * prints out string array of disconnected players
     * @param username
     * @return
     */
    public String[] disconnectedPlayers(ArrayList<String> username)
    {
        int i = 0;
        String[] tempVect = new String[username.size()+1];
        tempVect[i]="GIOCATORI DISCONNESSI: ";
        i++;

        for(String str : username)
        {
            tempVect[i]=str;
            i++;
        }
        return tempVect;
    }

    /**
     * prints out the select action scene
     * @return
     */
    public String[] selectAction()
    {
        String[] tempVect = new String[2];

        tempVect[0]= new String(cliToolsManager.centerThatString(("COSA VUOI FARE..?"), width));

        tempVect[1]= new String(cliToolsManager.centerThatString(("0 PER PASSARE, 1 PER MUOVERE, 2 PER TOOL"), width));

        return tempVect;
    }

    public String canMove()
    {
        return new String(cliToolsManager.centerThatString(("PUOI MUOVERE "),width));
    }

    public String cantMove()
    {
        return new String(cliToolsManager.centerThatString(("HAI GIA MOSSO"),width));
    }

    public String insertDraftIndex()
    {
        return new String(cliToolsManager.centerThatString(("SELEZIONA LA DRAFT.."),width));
    }

    public String insertX()
    {
        return new String(cliToolsManager.centerThatString(("INSERISCI RIGA.."),width));
    }

    public String insertY()
    {
        return new String(cliToolsManager.centerThatString(("INSERISCI COLONNA.."),width));
    }

    public String moveAccepted()
    {
        return new String(cliToolsManager.centerThatString(("MOSSA ACCETTATA"),width));
    }

    public String moveRefused()
    {
        return new String(cliToolsManager.centerThatString(("MOSSA RIFIUTATA"),width));
    }

    public String wrongInsertion()
    {
        return new String(cliToolsManager.centerThatString(("INSERIMENTO NON VALIDO"),width));
    }

    /**
     * prints out the other players moves
     * @param username
     * @param index
     * @param x
     * @param y
     * @return
     */
    public String[] notMyTurnMove(String username, int index, int x, int y)
    {
        String[] temp = new String[3];
        temp[0] = cliToolsManager.simpleQuestionsMaker(username+" ha effettuato una mossa", width, false);
        temp[1] = cliToolsManager.simpleQuestionsMaker("ha spostato il dado in posizione "+Integer.toString(index+1), width, false);
        temp[2] = cliToolsManager.simpleQuestionsMaker("nella cella x:"+Integer.toString(x+1)+", y: "+Integer.toString(y+1), width, false);
        return temp;
    }

    /**
     * prints out the waiting lobby scene
     * @return
     */
    public String[] waitingForPlayersScene()
    {
        String[] temp = cliToolsManager.blankLinesInitializer(20, 12, width);
        temp[12]=cliToolsManager.centerThatString("IN ATTESA CHE LA LOBBY SIA PIENA", width);
        return cliToolsManager.blankLinesEnder(temp, 13);
    }

    /**
     * prints out the creation of the lobby scene
     * @return
     */
    public String[] waitingForLobbyScene()
    {
        String[] temp = cliToolsManager.blankLinesInitializer(20, 12, width);
        temp[12]=cliToolsManager.centerThatString("CREAZIONE LOBBY IN CORSO", width);
        return cliToolsManager.blankLinesEnder(temp, 13);
    }


    public String[] showTool(ToolCardEvent event)
    {
        String[] temp;

        switch (event.getId()) {
            case (1):
            {
                temp = new String[2];
                if (((ToolCardOneEvent) event).getAction() == '+')
                    temp[0] = cliToolsManager.simpleQuestionsMaker("Il giocatore attivo ha aumentato il valore del dado in posizione " + Integer.toString(((ToolCardOneEvent) event).getIndex()+1), width, false);
                temp[1] = cliToolsManager.simpleQuestionsMaker("e lo ha spostato in posizione " + Integer.toString(((ToolCardOneEvent) event).getX()+1) + ", " + Integer.toString(((ToolCardOneEvent) event).getY()+1), width, false);
                return temp;
            }
            case (2):
            {
                temp = new String[2];
                temp[0] = cliToolsManager.simpleQuestionsMaker("Il giocatore attivo ha spostato il dado dalla posizione "+Integer.toString(((ToolCardTwoThreeEvent)event).getX0()+1)+", "+Integer.toString(((ToolCardTwoThreeEvent) event).getY0()+1), width, false);
                temp[1] = cliToolsManager.simpleQuestionsMaker("alla posizione "+Integer.toString(((ToolCardTwoThreeEvent) event).getX1()+1)+", "+Integer.toString(((ToolCardTwoThreeEvent) event).getY1()+1), width, false);
                return temp;
            }
            case (3):
            {
                temp = new String[2];
                temp[0] = cliToolsManager.simpleQuestionsMaker("Il giocatore attivo ha spostato il dado in posizione "+Integer.toString(((ToolCardTwoThreeEvent)event).getX0()+1)+", "+Integer.toString(((ToolCardTwoThreeEvent) event).getY0()+1), width, false);
                temp[1] = cliToolsManager.simpleQuestionsMaker("in posizione "+Integer.toString(((ToolCardTwoThreeEvent) event).getX1()+1)+", "+Integer.toString(((ToolCardTwoThreeEvent) event).getY1()+1), width, false);
                return temp;
            }
            case (4):
            {
                temp = new String[3];
                temp[0] = cliToolsManager.simpleQuestionsMaker("Il giocatore attivo ha spostato il dado in posizione "+Integer.toString(((ToolCardFourEvent)event).getX01()+1)+", "+Integer.toString(((ToolCardFourEvent) event).getY01()+1), width, false);
                temp[1] = cliToolsManager.simpleQuestionsMaker("in posizione "+Integer.toString(((ToolCardFourEvent) event).getX11()+1)+", "+Integer.toString(((ToolCardFourEvent) event).getY11()+1), width, false);
                temp[2] = cliToolsManager.simpleQuestionsMaker("il dado "+Integer.toString(((ToolCardFourEvent) event).getX02()+1)+", "+Integer.toString(((ToolCardFourEvent) event).getY02()+1)+ " in posizione "+Integer.toString(((ToolCardFourEvent) event).getX22()+1)+", "+Integer.toString(((ToolCardFourEvent) event).getY22()+1),width ,false);
                return temp;
            }
            case (5):
            {
                temp = new String[2];
                temp[0] = cliToolsManager.simpleQuestionsMaker("Il giocatore attivo ha sostituito il dado in posizione "+Integer.toString(((ToolCardFiveEvent)event).getIndex()+1), width, false);
                temp[1] = cliToolsManager.simpleQuestionsMaker("con il dado in posizione "+Integer.toString(((ToolCardFiveEvent) event).getPos()+1)+" del turno "+Integer.toString(((ToolCardFiveEvent) event).getTurn()+1), width, false);
            }

        }
        return null;
    }

    /**
     * shows all the players final scores at the end of the match
     * @param event Score event
     * @return array of strings
     * @throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException
     */
    public String[] showScores(ScoreEvent event) throws it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException {
        ArrayList<String> temp = new ArrayList<String>();
        int pos=1;
        for(ScorePlayer player : event.getPlayers())
        {
            temp.add("POSIZIONE "+Integer.toString(pos));
            temp.add(cliToolsManager.printSpaces(2)+player.getName());
            temp.add(cliToolsManager.printSpaces(2)+"PUNTI OBIETTIVO PRIVATO: "+Integer.toString(player.getPrivObj()));
            temp.add(cliToolsManager.printSpaces(2)+"PUNTI OBIETTIVO PUBBLICO 1: "+ Integer.toString(player.getPubObj(0)));
            temp.add(cliToolsManager.printSpaces(2)+"PUNTI OBIETTIVO PUBBLICO 2: "+ Integer.toString(player.getPubObj(1)));
            temp.add(cliToolsManager.printSpaces(2)+"PUNTI OBIETTIVO PUBBLICO 3: "+ Integer.toString(player.getPubObj(2)));
            temp.add(cliToolsManager.printSpaces(2)+"PUNTI TOKENS: "+Integer.toString(player.getTokens()));
            temp.add(cliToolsManager.printSpaces(2)+"PUNTI DECURTATI PER CELLE VUOTE: "+Integer.toString(player.getMinus()));
            temp.add(cliToolsManager.printSpaces(5)+"PUNTI TOTALI: "+Integer.toString(player.getTot()));
        }
        String[] vect = new String[temp.size()];
        for(int i=0;i<temp.size();i++)
            vect[i]=temp.get(i);

        return vect;

    }

    public String[] printTitle()
    {
        String[] temp = new String[6];

        temp[0]=cliToolsManager.centerThatString("██╗██╗███████╗ █████╗  ██████╗ ██████╗  █████╗ ██████╗  █████╗ ██╗██╗", 80);
        temp[1]=cliToolsManager.centerThatString("██║██║██╔════╝██╔══██╗██╔════╝ ██╔══██╗██╔══██╗██╔══██╗██╔══██╗██║██║",80);
        temp[2]=cliToolsManager.centerThatString("██║██║███████╗███████║██║  ███╗██████╔╝███████║██║  ██║███████║██║██║",80);
        temp[3]=cliToolsManager.centerThatString("╚═╝╚═╝╚════██║██╔══██║██║   ██║██╔══██╗██╔══██║██║  ██║██╔══██║╚═╝╚═╝", 80);
        temp[4]=cliToolsManager.centerThatString("██╗██╗███████║██║  ██║╚██████╔╝██║  ██║██║  ██║██████╔╝██║  ██║██╗██╗",80);
        temp[5]=cliToolsManager.centerThatString("╚═╝╚═╝╚══════╝╚═╝  ╚═╝ ╚═════╝ ╚═╝  ╚═╝╚═╝  ╚═╝╚═════╝ ╚═╝  ╚═╝╚═╝╚═╝", 80);

        return temp;
    }
}
