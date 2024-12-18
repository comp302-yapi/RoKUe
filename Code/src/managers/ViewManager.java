package managers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ViewManager implements Runnable {

    private final JFrame frame;
    private final Map<String, JPanel> panels;
    private JPanel currentPanel;
    private final Thread gameThread;

    public ViewManager(JFrame frame) {
        this.frame = frame;
        this.panels = new HashMap<>();
        this.gameThread = new Thread(this);
    }

    public void addPanel(String name, JPanel panel) {
        panels.putIfAbsent(name, panel);
    }

    public void switchTo(String panelName, Boolean closeCurrentPanel) throws IllegalArgumentException {
        if (!panels.containsKey(panelName)) {
            // TODO: Decide on proper logging - if we need
            throw new IllegalArgumentException("Panel with that key does not exist");
        }

        JPanel panelToSwitch = panels.get(panelName);
        if (currentPanel != null && closeCurrentPanel) {
            frame.remove(currentPanel);
        }

        currentPanel = panelToSwitch;
        frame.add(panelToSwitch, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        panelToSwitch.requestFocusInWindow();
    }

    public void startThread() {
        gameThread.start();
    }

    @Override
    public void run() {

        int FPS = 60;
        double drawInterval = (double) 1000000000 / FPS;
        double delta = 0;
        long lastTime = System.nanoTime();
        long currentTime;

        while(gameThread != null) {
            currentTime = System.nanoTime();
            delta += (currentTime - lastTime) / drawInterval;
            lastTime = currentTime;

            if (delta >= 1) {
                if (currentPanel != null) {
                    currentPanel.repaint();
                }
                delta--;
            }
        }
    }
}
