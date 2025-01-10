package managers;

import controllers.HallController;
import data.BossPanelData;
import data.BuildPanelData;
import data.HallPanelData;
import data.HomePanelData;
import entity.GameState;
import entity.Player;
import listeners.keylisteners.BossPanelKeyListener;
import enums.Hall;
import listeners.keylisteners.HallPanelKeyListener;
import listeners.keylisteners.HomePanelKeyListener;
import object.SuperObject;
import utils.GameLoader;
import utils.GameSaver;
import views.*;

import javax.swing.*;
import containers.HallContainer;
import java.awt.*;
import java.awt.event.KeyListener;
import java.io.File;
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
    private String[] savePaths = new String[10];
    private soundManager soundManager;


    public ViewManager(JFrame frame) {
        this.frame = frame;
        this.panels = new HashMap<>();
        this.gameThread = new Thread(this);
        this.soundManager = managers.soundManager.getInstance();
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
        //HelpScreen
        if (panelToSwitch instanceof HelpPanel) {
            panelToSwitch.requestFocusInWindow();
        }

        // If switching back to TitlePanel, reset and reload panels
        if (panelToSwitch instanceof TitlePanel) {
            soundManager.getInstance().stop();
            panels.clear();
            
            TimeManager.getInstance().stopTimer();
            TimeManager.getInstance().timer = null;

            JPanel titlePanel = new TitlePanel(this);
            JPanel buildPanel = new BuildPanel(this);
            JPanel homePanel = new HomePanel(this);
            JPanel hallPanel = new HallPanel(this);
            JPanel bossPanel = new BossPanel(this);
            JPanel loadPanel = new LoadPanel(this);
            JPanel HelpPanel = new HelpPanel(this);



            addPanel("TitlePanel", titlePanel);
            addPanel("BuildPanel", buildPanel);
            addPanel("HomePanel", homePanel);
            addPanel("HallPanel", hallPanel);
            addPanel("BossPanel", bossPanel);
            addPanel("LoadPanel", loadPanel);
            addPanel("HelpPanel", HelpPanel);


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

                    HallContainer.getHallOfAir().objects.clear();
                    HallContainer.getHallOfEarth().objects.clear();
                    HallContainer.getHallOfWater().objects.clear();
                    HallContainer.getHallOfFire().objects.clear();

                    HallContainer.getHallOfAir().gridWorld =  new SuperObject[13][14];
                    HallContainer.getHallOfEarth().gridWorld =  new SuperObject[13][14];
                    HallContainer.getHallOfWater().gridWorld =  new SuperObject[13][14];
                    HallContainer.getHallOfFire().gridWorld =  new SuperObject[13][14];

                    hallPanel.getPlayer().screenX = BasePanel.screenWidth/2 - (BasePanel.tileSize/2);
                    hallPanel.getPlayer().screenY = BasePanel.screenHeight/2 - (BasePanel.tileSize/2);

                    HallContainer.getHallOfAir().closeDoor();
                    HallContainer.getHallOfEarth().closeDoor();
                    HallContainer.getHallOfWater().closeDoor();
                    HallContainer.getHallOfFire().closeDoor();

                    hallPanel.currentHall = Hall.HallOfEarth;
                }

                HomePanelKeyListener homeKeyListener = new HomePanelKeyListener(homePanel);
                panelToSwitch.addKeyListener(homeKeyListener);
                player.addKeyListener(homeKeyListener);

                player.keyH = homeKeyListener;
            } else if (panelToSwitch instanceof BossPanel bossPanel) {

                if (currentPanel instanceof HallPanel hallPanel) {
                    if (hallPanel.easter1 && hallPanel.easter2 && hallPanel.easter3 && hallPanel.easter4) {
                        managers.soundManager.getInstance().activeClips.get(1).stop();
                        managers.soundManager.getInstance().setFile(18);
                        managers.soundManager.getInstance().play();
                        managers.soundManager.getInstance().loop();
                        bossPanel.easterEggFinal = true;
                    }
                }

                TimeManager.getInstance().stopTimer();
                TimeManager.getInstance().timer = null;

                BossPanelKeyListener bossPanelKeyListener = new BossPanelKeyListener(bossPanel);
                panelToSwitch.addKeyListener(bossPanelKeyListener);
                player.addKeyListener(bossPanelKeyListener);

                player.keyH = bossPanelKeyListener;
            }
        }
        else if(panelToSwitch instanceof BuildPanel buildPanel) {
        	buildPanel.currentHall = Hall.HallOfEarth;
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

        BossPanel boss = (BossPanel) panels.get("BossPanel");
        BossPanelData bossPanelData = boss.exportData();

        return new GameState(hallPanelData, buildPanelData, homePanelData, bossPanelData);
    }

    public void restoreGameState(GameState gameState) {
        HallPanel h = (HallPanel) panels.get("HallPanel");
        h.restoreData(gameState.getHallPanelData(), gameState.getBuildPanelData(), gameState.getCurrentMode());

        BuildPanel b = (BuildPanel) panels.get("BuildPanel");
        b.restoreData(gameState.getBuildPanelData());

        HomePanel home = (HomePanel) panels.get("HomePanel");
        home.restoreData(gameState.getHomePanelData());

        BossPanel boss = (BossPanel) panels.get("BossPanel");
        boss.restoreData(gameState.getBossPanelData());

    }

    public void saveGame(String filePath, GameState gameState) {
        String[] saveFiles = listSaveFiles();
        int fileCount = (saveFiles != null) ? saveFiles.length : 0;

        if (fileCount >= 6) {
            System.out.println("Cannot save more than 6 games.");
            return;
        }

        int dotIndex = filePath.lastIndexOf('.');
        String newFilePath;
        if (dotIndex != -1) {
            newFilePath = filePath.substring(0, dotIndex) + fileCount + filePath.substring(dotIndex);
        } else {
            newFilePath = filePath + fileCount;
        }

        GameSaver.saveGame(gameState, newFilePath);
        System.out.println("Game saved to: " + newFilePath);
    }

    public GameState loadGame(String filePath) {
        String[] saveFiles = listSaveFiles();
        System.out.println("Available Save Files:");
        for (String fileName : saveFiles) {
            System.out.println(fileName);
        }
        return GameLoader.loadGame(filePath);
    }

    public static String[] listSaveFiles() {
        File saveDir = new File("src/saves");

        if (saveDir.exists() && saveDir.isDirectory()) {
            File[] saveFiles = saveDir.listFiles((dir, name) -> name.endsWith(".ser"));

            if (saveFiles != null && saveFiles.length > 0) {
                String[] fileNames = new String[saveFiles.length];
                for (int i = 0; i < saveFiles.length; i++) {
                    fileNames[i] = saveFiles[i].getName();
                }
                return fileNames;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }



    public void addSavePath(String newPath) {
        for (int i = 0; i < savePaths.length; i++) {
            if (savePaths[i] == null) {
                savePaths[i] = newPath;
                break;
            }
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
