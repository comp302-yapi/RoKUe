package managers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TimeManager {
    
    public static TimeManager instance;
    public Timer timer;
    public int timeLeft;
    public TimeListener listener;
    public boolean timeStopped;

    // Private constructor for singleton pattern
    public TimeManager() {}

    public static synchronized TimeManager getInstance() {
        if (instance == null) {
            instance = new TimeManager();
        }
        return instance;
    }

    public interface TimeListener {
        void onTimeUpdate(int timeLeft);
        void onTimeUp();
    }

    public void setListener(TimeListener listener) {
        this.listener = listener;
    }

    public void startTimer(int durationInSeconds) {
    	
        stopTimer(); // Stop any running timer
        timeLeft = durationInSeconds;
        timeStopped = false;

        timer = new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (timeLeft > 0) {
                    timeLeft--;
                    if (listener != null) {
                        listener.onTimeUpdate(timeLeft);
                    }
                } else {
                    stopTimer();
                    if (listener != null) {
                        listener.onTimeUp();
                    }
                }
            }
        });

        timer.start();
    }

    public void stopTimer() {
        if (timer != null && timer.isRunning()) {
        	System.out.println("Timer stopped" + timeLeft);
            timer.stop();
            timeStopped = true;
            timer = null;
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
