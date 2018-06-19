package it.polimi.ingsw.commons;

import java.util.Date;
import java.util.Observable;

public class TimerThread extends Observable implements Runnable  {

    private long startTime = System.currentTimeMillis();
    private long elapsedTime = 0;

    private boolean timeElapsed=false;


    public void run() {
        try {
            Thread.sleep(50000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        setChanged();
        notifyObservers();
    }

    public boolean isTimeElapsed() {
        return timeElapsed;
    }
}
