package managers;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.Serial;
import java.io.Serializable;

public class TimeManager implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Add this for versioning

    private static TimeManager instance;
    private Timer timer;
    private int timeLeft;
    private TimeListener listener;

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
            timer.stop();
        }
    }

    public void resetTimer() {
        stopTimer();
        timeLeft = 0;
        if (listener != null) {
            listener.onTimeUpdate(timeLeft);
        }
    }

    public int getTimeLeft() {
        return timeLeft;
    }
}
