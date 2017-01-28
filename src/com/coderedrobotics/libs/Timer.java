package com.coderedrobotics.libs;

/**
 *
 * @author Michael
 * 3/9/16 - revised by D. Van Voorst
 * This class can be used to simplify timing autonomous steps
 * Use getStage() to manage your state machine
 * Use setTimerAndAdvanceStage() to start a timer for a particular stage
 * Use advanceWhenReady() to automatically advance to the next auto stage when the timer is reached
 * Use stopTimeAndAdvanceStage() when your drive command has reached its target to prevent the advanceWhenReady from firing also
 * 
 */
public class Timer {

    private int stage;
    private long endTime;

    public Timer() {

    }

    public void resetTimer(long time) {
    	setTimer(time);
    }

    public void setTimer(long time) {
    	endTime = System.currentTimeMillis() + time;
    }
    
    public void setTimerAndAdvanceStage(long time) {
    	resetTimer(time);
    	nextStage();
    }
    
    public void stopTimerAndAdvanceStage() {
    	resetTimer(1000000); // push the target time way into the future so it never reaches the target time
    	nextStage();
    }
    
    public void advanceWhenReady() {
        if (ready()) {
            stage++;
        }
    }
    
    public boolean ready() {
        return endTime < System.currentTimeMillis();
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }
    
    public void nextStage() {
        this.stage++;
    }
}
