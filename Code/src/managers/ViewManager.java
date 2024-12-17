package managers;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class ViewManager {

    private final JFrame frame;
    private final Map<String, JPanel> panels;
    private JPanel currentPanel;

    public ViewManager(JFrame frame) {
        this.frame = frame;
        this.panels = new HashMap<>();
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
    }

}
