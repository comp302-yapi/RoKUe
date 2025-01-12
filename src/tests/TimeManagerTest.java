package tests;

import managers.TimeManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class TimeManagerTest {

    /**
     * Written by Yasin Mutlu<br>
     * {@link tests.TimeManagerTest}<br>
     * This test class verifies the functionality of the {@link managers.TimeManager} singleton class,
     * including timer start, stop, and countdown operations.<br>
     * <br><br>
     * Requires:<br>
     * {@link managers.TimeManager} to be correctly implemented as a singleton.<br>
     * Timer functionality must support start, stop, and countdown.<br>
     * <br><br>
     * Modifies:<br>
     * {@link managers.TimeManager#timeLeft} when the timer is running.<br>
     * {@link managers.TimeManager#timeStopped} and {@link managers.TimeManager#timer} when the timer is stopped.<br>
     * <br><br>
     * Effects:<br>
     * {@link #testSingletonInstance()} ensures the singleton pattern is correctly implemented.<br>
     * {@link #testStartTimerAndCountdown()} verifies that the timer decrements correctly during a countdown.<br>
     * {@link #testStopTimer()} ensures the timer stops correctly and cleans up associated resources.<br>
     */

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