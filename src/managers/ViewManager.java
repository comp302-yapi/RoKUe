package managers;

import saveStates.GameState;
import views.BuildPanel;
import views.HallPanel;
import views.TitlePanel;

import javax.swing.*;
import java.awt.*;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.Serial;
import java.io.Serializable;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class ViewManager implements Runnable, Serializable {

    @Serial
    private static final long serialVersionUID = 1L; // Add this for versioning

    private final JFrame frame;
    private final Map<String, JPanel> panels;
    private JPanel currentPanel;
    private final transient Thread gameThread;

    public ViewManager(JFrame frame) {
        this.frame = frame;
        this.panels = new HashMap<>();
        this.gameThread = new Thread(this);

    }

    public Map<String, JPanel> getPanels() {
        return panels;
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

        if (panelToSwitch instanceof TitlePanel) {

            ((TitlePanel) panelToSwitch).soundManager.stop();
            panels.clear();

            JPanel titlePanel = new TitlePanel(this);
            JPanel buildPanel = new BuildPanel(this);
            JPanel hallPanel = new HallPanel(this);

            addPanel("HallPanel", hallPanel);
            addPanel("TitlePanel", titlePanel);
            addPanel("BuildPanel", buildPanel);

        }

        currentPanel = panelToSwitch;
        frame.add(panelToSwitch, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        panelToSwitch.requestFocusInWindow();
    }

    public void loadGame() {

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("game_save.ser"))) {
            GameState savedState = (GameState) ois.readObject();
            printSavedState(savedState);

            panels.clear();

            JPanel titlePanel = new TitlePanel(this);
            JPanel buildPanel = savedState.getBuildPanelState();
            JPanel hallPanel = savedState.getHallPanelState();

            addPanel("HallPanel", hallPanel);
            addPanel("TitlePanel", titlePanel);
            addPanel("BuildPanel", buildPanel);


        } catch (Exception c) {
            c.printStackTrace();
        }
    }

    public void printSavedState(GameState savedState) {
        // Print out the contents of the saved game
        System.out.println("Hero X: " + savedState.getHeroX());
        System.out.println("Hero Y: " + savedState.getHeroY());
        System.out.println("Time Remaining: " + savedState.getTimeRemaining());
        System.out.println("Lives Remaining: " + savedState.getLivesRemaining());

        if (savedState.getHallPanelState() != null) {
            System.out.println("HallPanel State: ");
            System.out.println("  Monsters: " + Arrays.toString(savedState.getHallPanelState().getMonsters()));
            System.out.println("  Objects: " + savedState.getHallPanelState().tileM.objects);
            System.out.println("  Enchantments: " + savedState.getHallPanelState().tileM.enchantments);

        }

        if (savedState.getBuildPanelState() != null) {
            System.out.println("BuildPanel State: ");
            System.out.println("  Objects: " + savedState.getBuildPanelState().getCurrentHallManager().objects);
        }

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
