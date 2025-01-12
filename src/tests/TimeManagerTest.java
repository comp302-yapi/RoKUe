package tests;

import managers.TimeManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeManagerTest {

    private TimeManager timeManager;

    @BeforeEach
    void setUp() {
        timeManager = TimeManager.getInstance();
        timeManager.stopTimer();
    }

    @Test
    void testSingletonInstance() {
        TimeManager anotherInstance = TimeManager.getInstance();
        Assertions.assertSame(timeManager, anotherInstance, "getInstance should return the same instance.");
    }

    @Test
    void testStartTimerAndCountdown() throws InterruptedException {
        timeManager.startTimer(5);
        Thread.sleep(3000); // Wait for 3 seconds

        int timeLeft = timeManager.getTimeLeft();
        Assertions.assertEquals(3, timeLeft, "Timer should decrement timeLeft correctly.");
    }

    @Test
    void testStopTimer() {
        timeManager.startTimer(5);
        timeManager.stopTimer();

        Assertions.assertTrue(timeManager.timeStopped, "Timer should be marked as stopped.");
        Assertions.assertNull(timeManager.timer, "Timer object should be null after stopping.");
    }
}