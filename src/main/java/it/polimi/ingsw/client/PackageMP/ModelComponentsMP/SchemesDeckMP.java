package it.polimi.ingsw.client.PackageMP.ModelComponentsMP;

import it.polimi.ingsw.client.ClientExceptions.InvalidIntArgumentException;

public class SchemesDeckMP {

    private SchemeCardMP[] deck;

    public SchemesDeckMP() throws InvalidIntArgumentException
    {
        //COLORS 0 WHITE/1 YELLOW/2 RED/3 GREEN/4 BLUE/5 VIOLET
        //NUMERI 6 ONE/7 TWO/8 THREE/9 FOUR/10 FIVE/11 SIX

        deck = new SchemeCardMP[12];
        for (int i=0;i<12;i++)
            deck[i] = new SchemeCardMP(i+1);


        //SCHEMECARD[0] FRONT

        deck[0].setName(1,"Kaleidoscope");
        deck[0].setDiff(1,4);

        deck[0].setCell(1,0,0,1);
        deck[0].setCell(1,0,1,4);
        deck[0].setCell(1,0,2,0);
        deck[0].setCell(1,0,3,0);
        deck[0].setCell(1,0,4,6);
        deck[0].setCell(1,1,0,3);
        deck[0].setCell(1,1,1,0);
        deck[0].setCell(1,1,2,10);
        deck[0].setCell(1,1,3,0);
        deck[0].setCell(1,1,4,9);
        deck[0].setCell(1,2,0,8);
        deck[0].setCell(1,2,1,0);
        deck[0].setCell(1,2,2,2);
        deck[0].setCell(1,2,3,0);
        deck[0].setCell(1,2,4,3);
        deck[0].setCell(1,3,0,7);
        deck[0].setCell(1,3,1,0);
        deck[0].setCell(1,3,2,0);
        deck[0].setCell(1,3,3,4);
        deck[0].setCell(1,3,4,1);


        //SCHEMECARD[0] BACK

        deck[0].setName(2,"Virtus");
        deck[0].setDiff(2,5);

        deck[0].setCell(2,0,0,9);
        deck[0].setCell(2,0,1,0);
        deck[0].setCell(2,0,2,7);
        deck[0].setCell(2,0,3,10);
        deck[0].setCell(2,0,4,3);
        deck[0].setCell(2,1,0,0);
        deck[0].setCell(2,1,1,0);
        deck[0].setCell(2,1,2,11);
        deck[0].setCell(2,1,3,3);
        deck[0].setCell(2,1,4,7);
        deck[0].setCell(2,2,0,0);
        deck[0].setCell(2,2,1,8);
        deck[0].setCell(2,2,2,3);
        deck[0].setCell(2,2,3,9);
        deck[0].setCell(2,2,4,0);
        deck[0].setCell(2,3,0,10);
        deck[0].setCell(2,3,1,3);
        deck[0].setCell(2,3,2,6);
        deck[0].setCell(2,3,3,0);
        deck[0].setCell(2,3,4,0);


        //SCHEMECARD[1] FRONT

        deck[1].setName(1,"Aurorae Magnificus");
        deck[1].setDiff(1,5);

        deck[1].setCell(1,0,0,10);
        deck[1].setCell(1,0,1,3);
        deck[1].setCell(1,0,2,4);
        deck[1].setCell(1,0,3,5);
        deck[1].setCell(1,0,4,7);
        deck[1].setCell(1,1,0,5);
        deck[1].setCell(1,1,1,0);
        deck[1].setCell(1,1,2,0);
        deck[1].setCell(1,1,3,0);
        deck[1].setCell(1,1,4,1);
        deck[1].setCell(1,2,0,1);
        deck[1].setCell(1,2,1,0);
        deck[1].setCell(1,2,2,11);
        deck[1].setCell(1,2,3,0);
        deck[1].setCell(1,2,4,5);
        deck[1].setCell(1,3,0,6);
        deck[1].setCell(1,3,1,0);
        deck[1].setCell(1,3,2,0);
        deck[1].setCell(1,3,3,3);
        deck[1].setCell(1,3,4,9);


        //SCHEMECARD[1] BACK

        deck[1].setName(2,"Via Lux");
        deck[1].setDiff(2,4);

        deck[1].setCell(2,0,0,1);
        deck[1].setCell(2,0,1,0);
        deck[1].setCell(2,0,2,11);
        deck[1].setCell(2,0,3,0);
        deck[1].setCell(2,0,4,0);
        deck[1].setCell(2,1,0,0);
        deck[1].setCell(2,1,1,6);
        deck[1].setCell(2,1,2,10);
        deck[1].setCell(2,1,3,0);
        deck[1].setCell(2,1,4,7);
        deck[1].setCell(2,2,0,8);
        deck[1].setCell(2,2,1,1);
        deck[1].setCell(2,2,2,2);
        deck[1].setCell(2,2,3,5);
        deck[1].setCell(2,2,4,0);
        deck[1].setCell(2,3,0,0);
        deck[1].setCell(2,3,1,0);
        deck[1].setCell(2,3,2,9);
        deck[1].setCell(2,3,3,8);
        deck[1].setCell(2,3,4,2);


        //SCHEMECARD[2] FRONT

        deck[2].setName(1,"Sun Catcher");
        deck[2].setDiff(1,3);

        deck[2].setCell(1,0,0,0);
        deck[2].setCell(1,0,1,4);
        deck[2].setCell(1,0,2,7);
        deck[2].setCell(1,0,3,0);
        deck[2].setCell(1,0,4,1);
        deck[2].setCell(1,1,0,0);
        deck[2].setCell(1,1,1,9);
        deck[2].setCell(1,1,2,0);
        deck[2].setCell(1,1,3,2);
        deck[2].setCell(1,1,4,0);
        deck[2].setCell(1,2,0,0);
        deck[2].setCell(1,2,1,0);
        deck[2].setCell(1,2,2,10);
        deck[2].setCell(1,2,3,1);
        deck[2].setCell(1,2,4,0);
        deck[2].setCell(1,3,0,3);
        deck[2].setCell(1,3,1,8);
        deck[2].setCell(1,3,2,0);
        deck[2].setCell(1,3,3,0);
        deck[2].setCell(1,3,4,5);


        //SCHEMECARD[2] BACK

        deck[2].setName(2,"Bellesguard");
        deck[2].setDiff(2,3);

        deck[2].setCell(2,0,0,4);
        deck[2].setCell(2,0,1,11);
        deck[2].setCell(2,0,2,0);
        deck[2].setCell(2,0,3,0);
        deck[2].setCell(2,0,4,1);
        deck[2].setCell(2,1,0,0);
        deck[2].setCell(2,1,1,8);
        deck[2].setCell(2,1,2,4);
        deck[2].setCell(2,1,3,0);
        deck[2].setCell(2,1,4,0);
        deck[2].setCell(2,2,0,0);
        deck[2].setCell(2,2,1,10);
        deck[2].setCell(2,2,2,11);
        deck[2].setCell(2,2,3,7);
        deck[2].setCell(2,2,4,0);
        deck[2].setCell(2,3,0,0);
        deck[2].setCell(2,3,1,9);
        deck[2].setCell(2,3,2,0);
        deck[2].setCell(2,3,3,6);
        deck[2].setCell(2,3,4,3);


        //SCHEMECARD[3] FRONT

        deck[3].setName(1,"Firmitas");
        deck[3].setDiff(1,5);

        deck[3].setCell(1,0,0,5);
        deck[3].setCell(1,0,1,11);
        deck[3].setCell(1,0,2,0);
        deck[3].setCell(1,0,3,0);
        deck[3].setCell(1,0,4,8);
        deck[3].setCell(1,1,0,10);
        deck[3].setCell(1,1,1,5);
        deck[3].setCell(1,1,2,8);
        deck[3].setCell(1,1,3,0);
        deck[3].setCell(1,1,4,0);
        deck[3].setCell(1,2,0,0);
        deck[3].setCell(1,2,1,7);
        deck[3].setCell(1,2,2,5);
        deck[3].setCell(1,2,3,6);
        deck[3].setCell(1,2,4,0);
        deck[3].setCell(1,3,0,0);
        deck[3].setCell(1,3,1,6);
        deck[3].setCell(1,3,2,10);
        deck[3].setCell(1,3,3,5);
        deck[3].setCell(1,3,4,9);


        //SCHEMECARD[3] BACK

        deck[3].setName(2,"Symphony of Light");
        deck[3].setDiff(2,6);

        deck[3].setCell(2,0,0,7);
        deck[3].setCell(2,0,1,0);
        deck[3].setCell(2,0,2,10);
        deck[3].setCell(2,0,3,0);
        deck[3].setCell(2,0,4,6);
        deck[3].setCell(2,1,0,1);
        deck[3].setCell(2,1,1,11);
        deck[3].setCell(2,1,2,5);
        deck[3].setCell(2,1,3,7);
        deck[3].setCell(2,1,4,2);
        deck[3].setCell(2,2,0,0);
        deck[3].setCell(2,2,1,4);
        deck[3].setCell(2,2,2,9);
        deck[3].setCell(2,2,3,3);
        deck[3].setCell(2,2,4,0);
        deck[3].setCell(2,3,0,0);
        deck[3].setCell(2,3,1,8);
        deck[3].setCell(2,3,2,0);
        deck[3].setCell(2,3,3,10);
        deck[3].setCell(2,3,4,0);


        //SCHEMECARD[4] FRONT

        deck[4].setName(1,"Aurora Sagradis");
        deck[4].setDiff(1,4);

        deck[4].setCell(1,0,0,2);
        deck[4].setCell(1,0,1,0);
        deck[4].setCell(1,0,2,4);
        deck[4].setCell(1,0,3,0);
        deck[4].setCell(1,0,4,1);
        deck[4].setCell(1,1,0,9);
        deck[4].setCell(1,1,1,5);
        deck[4].setCell(1,1,2,8);
        deck[4].setCell(1,1,3,3);
        deck[4].setCell(1,1,4,7);
        deck[4].setCell(1,2,0,0);
        deck[4].setCell(1,2,1,6);
        deck[4].setCell(1,2,2,0);
        deck[4].setCell(1,2,3,10);
        deck[4].setCell(1,2,4,0);
        deck[4].setCell(1,3,0,0);
        deck[4].setCell(1,3,1,0);
        deck[4].setCell(1,3,2,11);
        deck[4].setCell(1,3,3,0);
        deck[4].setCell(1,3,4,0);


        //SCHEMECARD[4] BACK

        deck[4].setName(2,"Industria");
        deck[4].setDiff(2,5);

        deck[4].setCell(2,0,0,6);
        deck[4].setCell(2,0,1,2);
        deck[4].setCell(2,0,2,8);
        deck[4].setCell(2,0,3,0);
        deck[4].setCell(2,0,4,11);
        deck[4].setCell(2,1,0,10);
        deck[4].setCell(2,1,1,9);
        deck[4].setCell(2,1,2,2);
        deck[4].setCell(2,1,3,7);
        deck[4].setCell(2,1,4,0);
        deck[4].setCell(2,2,0,0);
        deck[4].setCell(2,2,1,0);
        deck[4].setCell(2,2,2,10);
        deck[4].setCell(2,2,3,2);
        deck[4].setCell(2,2,4,6);
        deck[4].setCell(2,3,0,0);
        deck[4].setCell(2,3,1,0);
        deck[4].setCell(2,3,2,0);
        deck[4].setCell(2,3,3,8);
        deck[4].setCell(2,3,4,2);


        //SCHEMECARD[5] FRONT

        deck[5].setName(1,"Shadow Thief");
        deck[5].setDiff(1,5);

        deck[5].setCell(1,0,0,11);
        deck[5].setCell(1,0,1,5);
        deck[5].setCell(1,0,2,0);
        deck[5].setCell(1,0,3,0);
        deck[5].setCell(1,0,4,10);
        deck[5].setCell(1,1,0,10);
        deck[5].setCell(1,1,1,0);
        deck[5].setCell(1,1,2,5);
        deck[5].setCell(1,1,3,0);
        deck[5].setCell(1,1,4,0);
        deck[5].setCell(1,2,0,2);
        deck[5].setCell(1,2,1,11);
        deck[5].setCell(1,2,2,0);
        deck[5].setCell(1,2,3,5);
        deck[5].setCell(1,2,4,0);
        deck[5].setCell(1,3,0,1);
        deck[5].setCell(1,3,1,2);
        deck[5].setCell(1,3,2,10);
        deck[5].setCell(1,3,3,9);
        deck[5].setCell(1,3,4,8);


        //SCHEMECARD[5] BACK

        deck[5].setName(2,"Batllo");
        deck[5].setDiff(2,5);

        deck[5].setCell(2,0,0,0);
        deck[5].setCell(2,0,1,0);
        deck[5].setCell(2,0,2,11);
        deck[5].setCell(2,0,3,0);
        deck[5].setCell(2,0,4,0);
        deck[5].setCell(2,1,0,0);
        deck[5].setCell(2,1,1,10);
        deck[5].setCell(2,1,2,4);
        deck[5].setCell(2,1,3,9);
        deck[5].setCell(2,1,4,0);
        deck[5].setCell(2,2,0,8);
        deck[5].setCell(2,2,1,3);
        deck[5].setCell(2,2,2,1);
        deck[5].setCell(2,2,3,5);
        deck[5].setCell(2,2,4,7);
        deck[5].setCell(2,3,0,6);
        deck[5].setCell(2,3,1,9);
        deck[5].setCell(2,3,2,2);
        deck[5].setCell(2,3,3,10);
        deck[5].setCell(2,3,4,8);


        //SCHEMECARD[6] FRONT

        deck[6].setName(1,"Gravitas");
        deck[6].setDiff(1,5);

        deck[6].setCell(1,0,0,6);
        deck[6].setCell(1,0,1,0);
        deck[6].setCell(1,0,2,8);
        deck[6].setCell(1,0,3,4);
        deck[6].setCell(1,0,4,0);
        deck[6].setCell(1,1,0,0);
        deck[6].setCell(1,1,1,7);
        deck[6].setCell(1,1,2,4);
        deck[6].setCell(1,1,3,0);
        deck[6].setCell(1,1,4,0);
        deck[6].setCell(1,2,0,11);
        deck[6].setCell(1,2,1,4);
        deck[6].setCell(1,2,2,0);
        deck[6].setCell(1,2,3,9);
        deck[6].setCell(1,2,4,0);
        deck[6].setCell(1,3,0,4);
        deck[6].setCell(1,3,1,10);
        deck[6].setCell(1,3,2,7);
        deck[6].setCell(1,3,3,0);
        deck[6].setCell(1,3,4,6);


        //SCHEMECARD[6] BACK

        deck[6].setName(2,"Fractal Drops");
        deck[6].setDiff(2,3);

        deck[6].setCell(2,0,0,0);
        deck[6].setCell(2,0,1,9);
        deck[6].setCell(2,0,2,0);
        deck[6].setCell(2,0,3,1);
        deck[6].setCell(2,0,4,11);
        deck[6].setCell(2,1,0,2);
        deck[6].setCell(2,1,1,0);
        deck[6].setCell(2,1,2,7);
        deck[6].setCell(2,1,3,0);
        deck[6].setCell(2,1,4,0);
        deck[6].setCell(2,2,0,0);
        deck[6].setCell(2,2,1,0);
        deck[6].setCell(2,2,2,2);
        deck[6].setCell(2,2,3,5);
        deck[6].setCell(2,2,4,6);
        deck[6].setCell(2,3,0,4);
        deck[6].setCell(2,3,1,1);
        deck[6].setCell(2,3,2,0);
        deck[6].setCell(2,3,3,0);
        deck[6].setCell(2,3,4,0);


        //SCHEMECARD[7] FRONT

        deck[7].setName(1,"Lux Astram");
        deck[7].setDiff(1,5);

        deck[7].setCell(1,0,0,0);
        deck[7].setCell(1,0,1,6);
        deck[7].setCell(1,0,2,3);
        deck[7].setCell(1,0,3,5);
        deck[7].setCell(1,0,4,9);
        deck[7].setCell(1,1,0,11);
        deck[7].setCell(1,1,1,5);
        deck[7].setCell(1,1,2,7);
        deck[7].setCell(1,1,3,10);
        deck[7].setCell(1,1,4,3);
        deck[7].setCell(1,2,0,6);
        deck[7].setCell(1,2,1,3);
        deck[7].setCell(1,2,2,10);
        deck[7].setCell(1,2,3,8);
        deck[7].setCell(1,2,4,5);
        deck[7].setCell(1,3,0,0);
        deck[7].setCell(1,3,1,0);
        deck[7].setCell(1,3,2,0);
        deck[7].setCell(1,3,3,0);
        deck[7].setCell(1,3,4,0);


        //SCHEMECARD[7] BACK

        deck[7].setName(2,"Chromatic Splendor");
        deck[7].setDiff(2,4);

        deck[7].setCell(2,0,0,0);
        deck[7].setCell(2,0,1,0);
        deck[7].setCell(2,0,2,3);
        deck[7].setCell(2,0,3,0);
        deck[7].setCell(2,0,4,0);
        deck[7].setCell(2,1,0,7);
        deck[7].setCell(2,1,1,1);
        deck[7].setCell(2,1,2,10);
        deck[7].setCell(2,1,3,4);
        deck[7].setCell(2,1,4,6);
        deck[7].setCell(2,2,0,0);
        deck[7].setCell(2,2,1,2);
        deck[7].setCell(2,2,2,8);
        deck[7].setCell(2,2,3,5);
        deck[7].setCell(2,2,4,0);
        deck[7].setCell(2,3,0,6);
        deck[7].setCell(2,3,1,0);
        deck[7].setCell(2,3,2,11);
        deck[7].setCell(2,3,3,0);
        deck[7].setCell(2,3,4,9);


        //SCHEMECARD[8] FRONT

        deck[8].setName(1,"Firelight");
        deck[8].setDiff(1,5);

        deck[8].setCell(1,0,0,8);
        deck[8].setCell(1,0,1,9);
        deck[8].setCell(1,0,2,6);
        deck[8].setCell(1,0,3,10);
        deck[8].setCell(1,0,4,0);
        deck[8].setCell(1,1,0,0);
        deck[8].setCell(1,1,1,11);
        deck[8].setCell(1,1,2,7);
        deck[8].setCell(1,1,3,0);
        deck[8].setCell(1,1,4,1);
        deck[8].setCell(1,2,0,0);
        deck[8].setCell(1,2,1,0);
        deck[8].setCell(1,2,2,0);
        deck[8].setCell(1,2,3,1);
        deck[8].setCell(1,2,4,2);
        deck[8].setCell(1,3,0,10);
        deck[8].setCell(1,3,1,0);
        deck[8].setCell(1,3,2,1);
        deck[8].setCell(1,3,3,2);
        deck[8].setCell(1,3,4,11);


        //SCHEMECARD[8] BACK

        deck[8].setName(2,"Luz Celestial");
        deck[8].setDiff(2,3);

        deck[8].setCell(2,0,0,0);
        deck[8].setCell(2,0,1,0);
        deck[8].setCell(2,0,2,2);
        deck[8].setCell(2,0,3,10);
        deck[8].setCell(2,0,4,0);
        deck[8].setCell(2,1,0,5);
        deck[8].setCell(2,1,1,9);
        deck[8].setCell(2,1,2,0);
        deck[8].setCell(2,1,3,3);
        deck[8].setCell(2,1,4,8);
        deck[8].setCell(2,2,0,11);
        deck[8].setCell(2,2,1,0);
        deck[8].setCell(2,2,2,0);
        deck[8].setCell(2,2,3,4);
        deck[8].setCell(2,2,4,0);
        deck[8].setCell(2,3,0,0);
        deck[8].setCell(2,3,1,1);
        deck[8].setCell(2,3,2,7);
        deck[8].setCell(2,3,3,0);
        deck[8].setCell(2,3,4,0);


        //SCHEMECARD[9] FRONT

        deck[9].setName(1,"Water of Life");
        deck[9].setDiff(1,6);

        deck[9].setCell(1,0,0,11);
        deck[9].setCell(1,0,1,4);
        deck[9].setCell(1,0,2,0);
        deck[9].setCell(1,0,3,0);
        deck[9].setCell(1,0,4,6);
        deck[9].setCell(1,1,0,0);
        deck[9].setCell(1,1,1,10);
        deck[9].setCell(1,1,2,4);
        deck[9].setCell(1,1,3,0);
        deck[9].setCell(1,1,4,0);
        deck[9].setCell(1,2,0,9);
        deck[9].setCell(1,2,1,2);
        deck[9].setCell(1,2,2,7);
        deck[9].setCell(1,2,3,4);
        deck[9].setCell(1,2,4,0);
        deck[9].setCell(1,3,0,3);
        deck[9].setCell(1,3,1,11);
        deck[9].setCell(1,3,2,1);
        deck[9].setCell(1,3,3,8);
        deck[9].setCell(1,3,4,5);


        //SCHEMECARD[9] BACK

        deck[9].setName(2,"Ripples of Light");
        deck[9].setDiff(2,5);

        deck[9].setCell(2,0,0,0);
        deck[9].setCell(2,0,1,0);
        deck[9].setCell(2,0,2,0);
        deck[9].setCell(2,0,3,2);
        deck[9].setCell(2,0,4,10);
        deck[9].setCell(2,1,0,0);
        deck[9].setCell(2,1,1,0);
        deck[9].setCell(2,1,2,5);
        deck[9].setCell(2,1,3,9);
        deck[9].setCell(2,1,4,4);
        deck[9].setCell(2,2,0,0);
        deck[9].setCell(2,2,1,4);
        deck[9].setCell(2,2,2,8);
        deck[9].setCell(2,2,3,1);
        deck[9].setCell(2,2,4,11);
        deck[9].setCell(2,3,0,1);
        deck[9].setCell(2,3,1,7);
        deck[9].setCell(2,3,2,3);
        deck[9].setCell(2,3,3,6);
        deck[9].setCell(2,3,4,2);


        //SCHEMECARD[10] FRONT

        deck[10].setName(1,"Lux Mundi");
        deck[10].setDiff(1,6);

        deck[10].setCell(1,0,0,0);
        deck[10].setCell(1,0,1,0);
        deck[10].setCell(1,0,2,6);
        deck[10].setCell(1,0,3,0);
        deck[10].setCell(1,0,4,0);
        deck[10].setCell(1,1,0,6);
        deck[10].setCell(1,1,1,3);
        deck[10].setCell(1,1,2,8);
        deck[10].setCell(1,1,3,4);
        deck[10].setCell(1,1,4,7);
        deck[10].setCell(1,2,0,4);
        deck[10].setCell(1,2,1,10);
        deck[10].setCell(1,2,2,9);
        deck[10].setCell(1,2,3,11);
        deck[10].setCell(1,2,4,3);
        deck[10].setCell(1,3,0,0);
        deck[10].setCell(1,3,1,4);
        deck[10].setCell(1,3,2,10);
        deck[10].setCell(1,3,3,3);
        deck[10].setCell(1,3,4,0);


        //SCHEMECARD[10] BACK

        deck[10].setName(2,"Comitas");
        deck[10].setDiff(2,5);

        deck[10].setCell(2,0,0,1);
        deck[10].setCell(2,0,1,0);
        deck[10].setCell(2,0,2,7);
        deck[10].setCell(2,0,3,0);
        deck[10].setCell(2,0,4,11);
        deck[10].setCell(2,1,0,0);
        deck[10].setCell(2,1,1,9);
        deck[10].setCell(2,1,2,0);
        deck[10].setCell(2,1,3,10);
        deck[10].setCell(2,1,4,1);
        deck[10].setCell(2,2,0,0);
        deck[10].setCell(2,2,1,0);
        deck[10].setCell(2,2,2,0);
        deck[10].setCell(2,2,3,1);
        deck[10].setCell(2,2,4,10);
        deck[10].setCell(2,3,0,6);
        deck[10].setCell(2,3,1,7);
        deck[10].setCell(2,3,2,1);
        deck[10].setCell(2,3,3,8);
        deck[10].setCell(2,3,4,0);


        //SCHEMECARD[11] FRONT

        deck[11].setName(1,"Sun's Glory");
        deck[11].setDiff(1,6);

        deck[11].setCell(1,0,0,6);
        deck[11].setCell(1,0,1,5);
        deck[11].setCell(1,0,2,1);
        deck[11].setCell(1,0,3,0);
        deck[11].setCell(1,0,4,9);
        deck[11].setCell(1,1,0,5);
        deck[11].setCell(1,1,1,1);
        deck[11].setCell(1,1,2,0);
        deck[11].setCell(1,1,3,0);
        deck[11].setCell(1,1,4,11);
        deck[11].setCell(1,2,0,1);
        deck[11].setCell(1,2,1,0);
        deck[11].setCell(1,2,2,0);
        deck[11].setCell(1,2,3,10);
        deck[11].setCell(1,2,4,8);
        deck[11].setCell(1,3,0,0);
        deck[11].setCell(1,3,1,10);
        deck[11].setCell(1,3,2,9);
        deck[11].setCell(1,3,3,7);
        deck[11].setCell(1,3,4,6);


        //SCHEMECARD[11] BACK

        deck[11].setName(2,"Fulgor del Cielo");
        deck[11].setDiff(2,5);

        deck[11].setCell(2,0,0,0);
        deck[11].setCell(2,0,1,4);
        deck[11].setCell(2,0,2,2);
        deck[11].setCell(2,0,3,0);
        deck[11].setCell(2,0,4,0);
        deck[11].setCell(2,1,0,0);
        deck[11].setCell(2,1,1,9);
        deck[11].setCell(2,1,2,10);
        deck[11].setCell(2,1,3,0);
        deck[11].setCell(2,1,4,4);
        deck[11].setCell(2,2,0,4);
        deck[11].setCell(2,2,1,7);
        deck[11].setCell(2,2,2,0);
        deck[11].setCell(2,2,3,2);
        deck[11].setCell(2,2,4,10);
        deck[11].setCell(2,3,0,11);
        deck[11].setCell(2,3,1,2);
        deck[11].setCell(2,3,2,8);
        deck[11].setCell(2,3,3,6);
        deck[11].setCell(2,3,4,0);
    }

    public SchemeCardMP[] extractSchemes(int n) throws InvalidIntArgumentException
    {

        if (n<0 || n>8 || n%2!=0) throw new InvalidIntArgumentException();

        SchemeCardMP[] tempVett = new SchemeCardMP[n];

        for (int i = 0; i < n; i++) {
            boolean exists = false;
            int random = 0;
            while (!exists) {

                random = (int) (Math.random() * 12);

                if (!deck[random].checkDisabled()) {
                    tempVett[i] = deck[random];
                    deck[random].disableScheme();
                    exists = true;
                }

            }

        }
        return tempVett;
    }

    public SchemeCardMP extractSchemebyID (int id) throws InvalidIntArgumentException
    {
        SchemeCardMP scheme;

        if(id<1 || id>12)
            throw new InvalidIntArgumentException();

        scheme = deck[id-1];
        return scheme;

    }

}