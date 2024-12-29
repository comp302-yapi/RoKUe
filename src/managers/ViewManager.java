package managers;

import data.BuildPanelData;
import data.HallPanelData;
import entity.GameState;
import utils.GameLoader;
import utils.GameSaver;
import views.BuildPanel;
import views.HallPanel;
import views.TitlePanel;

import javax.swing.*;
import java.awt.*;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ViewManager implements Runnable, Serializable {

    private static final long serialVersionUID = 1L;

    private final JFrame frame;
    public final Map<String, JPanel> panels;
    private JPanel currentPanel;
    private final transient Thread gameThread;

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

    public GameState collectGameState() {

        HallPanel h = (HallPanel) panels.get("HallPanel");
        HallPanelData hallPanelData = h.exportData();

        BuildPanel b = (BuildPanel) panels.get("BuildPanel");
        BuildPanelData buildPanelData = b.exportData();

        return new GameState(hallPanelData, buildPanelData);
    }

    public void restoreGameState(GameState gameState) {
        HallPanel h = (HallPanel) panels.get("HallPanel");
        h.restoreData(gameState.getHallPanelData());

        BuildPanel b = (BuildPanel) panels.get("BuildPanel");
        b.restoreData(gameState.getBuildPanelData());
    }

    public void saveGame(String filePath, GameState gameState) {
        GameSaver.saveGame(gameState, filePath);
    }

    public GameState loadGame(String filePath) {
//                if (loadedGameState != null) {
//            restoreGameState(loadedGameState);
//        }
        return GameLoader.loadGame(filePath);
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
