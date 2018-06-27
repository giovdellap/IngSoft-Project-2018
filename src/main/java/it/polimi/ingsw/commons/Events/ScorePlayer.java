package it.polimi.ingsw.commons.Events;

import it.polimi.ingsw.commons.Exceptions.InvalidIntArgumentException;

public class ScorePlayer
{
        private int id;
        private String name;
        private int privObj;
        private int pubObj0;
        private int pubObj1;
        private int pubObj2;
        private int tokens;
        private int minus;
        private int tot;

        public ScorePlayer(int id, String name)
        {
            this.id = id;
            this.name=name;
        }

        //GET

        public int getId() {
            return id;
        }

        public String getName() {
            return name;
        }

        public int getPrivObj() {
            return privObj;
        }

       public int getPubObj(int index) throws InvalidIntArgumentException {
           if(index==0)
               return pubObj0;
           if(index==1)
               return pubObj1;
           if(index==2)
               return pubObj2;
           else
               throw new InvalidIntArgumentException();
       }

        public int getTokens() {
            return tokens;
        }

        public int getMinus() {
            return minus;
        }

        public int getTot() {
            return tot;
        }

        //SET

        public void setPrivObj(int privObj) {
            this.privObj = privObj;
        }

        public void setPubObj(int index, int bonus)
        {
            if (index==0)
                pubObj0=bonus;
            if(index==1)
                pubObj1=bonus;
            if(index==2)
                pubObj2=bonus;
        }

        public void setTokens(int tokens) {
            this.tokens = tokens;
        }

        public void setMinus(int minus) {
            this.minus = minus;
        }

        public void setTot(int tot) {
            this.tot = tot;
        }
}
