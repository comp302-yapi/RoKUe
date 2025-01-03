package managers;

import data.BuildPanelData;
import data.HallPanelData;
import data.HomePanelData;
import entity.GameState;
import entity.Player;
import listeners.keylisteners.HallPanelKeyListener;
import listeners.keylisteners.HomePanelKeyListener;
import utils.GameLoader;
import utils.GameSaver;
import views.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.Serializable;
import java.util.Arrays;
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
        System.out.println("Added:" + name);
        panels.putIfAbsent(name, panel);
    }

    public void switchTo(String panelName, Boolean closeCurrentPanel) throws IllegalArgumentException {
        if (!panels.containsKey(panelName)) {
            throw new IllegalArgumentException("Panel with that key does not exist");
        }

        JPanel panelToSwitch = panels.get(panelName);
        if (currentPanel != null && closeCurrentPanel) {
            // Remove key listeners from the current panel
            for (KeyListener keyListener : currentPanel.getKeyListeners()) {
                currentPanel.removeKeyListener(keyListener);
            }

            frame.remove(currentPanel);
        }

        // If switching back to TitlePanel, reset and reload panels
        if (panelToSwitch instanceof TitlePanel) {
            ((TitlePanel) panelToSwitch).soundManager.stop();
            panels.clear();
            
            TimeManager.getInstance().stopTimer();
            TimeManager.getInstance().timer = null;

            JPanel titlePanel = new TitlePanel(this);
            JPanel buildPanel = new BuildPanel(this);
            JPanel homePanel = new HomePanel(this);
            JPanel hallPanel = new HallPanel(this);

            addPanel("TitlePanel", titlePanel);
            addPanel("BuildPanel", buildPanel);
            addPanel("HomePanel", homePanel);
            addPanel("HallPanel", hallPanel);

            panelToSwitch = titlePanel;
        }

        if (panelToSwitch instanceof PlayablePanel playablePanel) {
            Player player = Player.getInstance(playablePanel);

            Arrays.stream(panelToSwitch.getKeyListeners()).forEach(panelToSwitch::removeKeyListener);

            if (player.keyH != null) {
                player.removeKeyListener(player.keyH);
            }

            if (panelToSwitch instanceof HallPanel hallPanel) {
                hallPanel.timeLeft = hallPanel.getSuperObjectLength() * 10;
                hallPanel.getPlayer().life = hallPanel.getPlayer().maxLife;

                HallPanelKeyListener hallKeyListener = new HallPanelKeyListener(hallPanel);
                panelToSwitch.addKeyListener(hallKeyListener);
                player.addKeyListener(hallKeyListener);

                player.keyH = hallKeyListener;
            } else if (panelToSwitch instanceof HomePanel homePanel) {
            	
                TimeManager.getInstance().stopTimer();
                TimeManager.getInstance().timer = null;

            	
                if (currentPanel instanceof HallPanel hallPanel) {
                    if (hallPanel.tileM.objectsEarth != null) hallPanel.tileM.objectsEarth.clear();
                    if (hallPanel.getSuperObjects() != null) Arrays.fill(hallPanel.getSuperObjects(), null);
                    if (hallPanel.tileM.objectsAir != null) hallPanel.tileM.objectsAir.clear();
                    if (hallPanel.tileM.objectsWater != null) hallPanel.tileM.objectsWater.clear();
                    if (hallPanel.tileM.objectsFire != null) hallPanel.tileM.objectsFire.clear();
                    if (hallPanel.tileM.enchantments != null) hallPanel.tileM.enchantments.clear();
                    if (hallPanel.getHallMonsters() != null) hallPanel.getHallMonsters().clear();
                }
                
                HomePanelKeyListener homeKeyListener = new HomePanelKeyListener(homePanel);
                panelToSwitch.addKeyListener(homeKeyListener);
                player.addKeyListener(homeKeyListener);

                player.keyH = homeKeyListener;
            }
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

        HomePanel home = (HomePanel) panels.get("HomePanel");
        HomePanelData homePanelData = home.exportData();

        return new GameState(hallPanelData, buildPanelData, homePanelData);
    }

    public void restoreGameState(GameState gameState) {
        HallPanel h = (HallPanel) panels.get("HallPanel");
        h.restoreData(gameState.getHallPanelData());

        BuildPanel b = (BuildPanel) panels.get("BuildPanel");
        b.restoreData(gameState.getBuildPanelData());

        HomePanel home = (HomePanel) panels.get("HomePanel");
        home.restoreData(gameState.getHomePanelData());
    }

    public void saveGame(String filePath, GameState gameState) {
        GameSaver.saveGame(gameState, filePath);
    }

    public GameState loadGame(String filePath) {
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
