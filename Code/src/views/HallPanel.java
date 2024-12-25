package views;

import containers.HallContainer;
import controllers.HallController;
import entity.Arrow;
import entity.Entity;
import enums.Hall;
import listeners.keylisteners.HallPanelKeyListener;
import listeners.mouselisteners.HallPanelMouseListener;
import managers.CollisionCheckerForHall;
import managers.TileManagerForHall;
import managers.ViewManager;
import monster.MON_Archer;
import monster.MON_Fighter;
import monster.MON_Wizard;
import object.OBJ_Heart;
import object.SuperObject;
import utils.PanelUtils;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Random;

public class HallPanel extends PlayablePanel{

    public Hall currentHall = Hall.HallOfEarth;
    private final HallPanelKeyListener keyListener;
    private final HallPanelMouseListener mouseListener;
    private final ArrayList<Entity> monsters = new ArrayList<>();
    public TileManagerForHall tileM;
    final CollisionCheckerForHall cChecker;
    private boolean isPaused;
    boolean wizardChecker = false;
    BufferedImage heart_full, heart_half, heart_blank;
    SuperObject heart = new OBJ_Heart();



    public int spawnCounter;

    String[] monsterTypes = new String[3];

    public HallPanel(ViewManager viewManager) {
        super(viewManager);
        this.tileM = HallContainer.getHallOfEarth();
        this.keyListener = new HallPanelKeyListener(this);
        this.addKeyListener(keyListener);
        getPlayer().addKeyListener(keyListener);

        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;



        monsterTypes[0] = "Archer";
        monsterTypes[1] = "Wizard";
        monsterTypes[2] = "Fighter";

        this.mouseListener = new HallPanelMouseListener(this, getTileM());
        this.addMouseListener(mouseListener);
        this.cChecker = new CollisionCheckerForHall(this);

        getPlayer().panel = this;
    }

    @Override
    public void addNotify() {
        super.addNotify();
        HallController.movePlayerIfCollision(HallContainer.getCurrentHallManager(currentHall), getPlayer());
    }

    @Override
    public void update() {
        if (!isPaused()) {
            getPlayer().move();

            for (Entity monster : monsters) {
                if (monster != null) {
                    monster.update();

                }
            }

            spawnCounter++;

            //Update Arrows
            for (int i = 0; i < getArrows().length; i++) {
                if (getArrows()[i] != null) {
                    getArrows()[i].update();

                    // Remove expired arrows
                    if (getArrows()[i].isExpired()) {
                        getArrows()[i] = null;
                    }
                }
            }

            HallController.shouldSwitchHallsInGame(getTileM(), getPlayer(), this);

            if (spawnCounter >= 60 * 8) {
                generateMonster();
                spawnCounter = 0;
            }
        }
    }

    public TileManagerForHall getTileM(){ return this.tileM;}

    public void generateMonster(){

        Random random = new Random();
        String pickMonster = monsterTypes[random.nextInt(monsterTypes.length)]; // Get a random index

        int locationX = random.nextInt(1,13) + 7;
        int locationY = random.nextInt(1,14) + 2;

        switch (pickMonster) {
            case "Archer":
                MON_Archer archer = new MON_Archer(this);
                archer.worldX = BasePanel.tileSize*locationX;
                archer.worldY = BasePanel.tileSize*locationY;
                archer.spawned = true;

                for (int i = 0; i < getMonsters().length; i++) {

                    if (getMonsters()[i] == null) {
                        getMonsters()[i] = archer;
                        break;
                    }
                }

                monsters.add(archer);

                break;

            case "Wizard":

                if (!wizardChecker) {
                MON_Wizard wizard = new MON_Wizard(this);
                wizard.worldX = BasePanel.tileSize * locationX;
                wizard.worldY = BasePanel.tileSize * locationY;
                wizard.spawned = true;


                for (int i = 0; i < getMonsters().length; i++) {

                    if (getMonsters()[i] == null) {
                        getMonsters()[i] = wizard;
                        break;
                    }
                }


                    monsters.add(wizard);
                    wizardChecker = true;
                }

                break;

            case "Fighter":

                MON_Fighter fighter = new MON_Fighter(this);
                fighter.worldX = BasePanel.tileSize*locationX;
                fighter.worldY = BasePanel.tileSize*locationY;
                fighter.spawned = true;

                for (int i = 0; i < getMonsters().length; i++) {

                    if (getMonsters()[i] == null) {
                        getMonsters()[i] = fighter;
                        break;
                    }
                }

                monsters.add(fighter);
                break;
            default:
                System.out.println("Unknown character type.");
        }
    }

    @Override
    public void showMessage(String message) {
        Graphics2D g2 = (Graphics2D) getGraphics();
        if (g2 != null) {
            g2.setFont(new Font("Arial", Font.BOLD, 18));
            g2.setColor(Color.YELLOW);
            g2.drawString(message, 10, 10); // Draw message at top left
        }
    }

    public CollisionCheckerForHall getCollisionCheckerForHall(){ return this.cChecker;}

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    private void drawPlayerLife(Graphics2D g2) {

        int x = tileSize/2;
        int y = tileSize/2;
        int i = 0;

        while(i < getPlayer().maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += tileSize*1.5;
        }

        x = tileSize/2;
        y = tileSize/2;
        i = 0;

        while(i < getPlayer().life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < getPlayer().life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += tileSize*1.5;
        }

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        if (!isPaused()) {
            update();
        }

        switch (currentHall) {
            case HallOfEarth -> {
                // Repaint game
                g2.setFont(arial_40);

                // Draw tiles
                HallContainer.getHallOfEarth().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfEarth().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }
            }
            case HallOfAir -> {
                // Repaint game
                g2.setFont(arial_40);

                // Draw tiles
                HallContainer.getHallOfAir().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfAir().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }
            }
            case HallOfWater -> {
                // Repaint game
                g2.setFont(arial_40);

                // Draw tiles
                HallContainer.getHallOfWater().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfWater().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }
            }
            case HallOfFire -> {
                // Repaint game
                g2.setFont(arial_40);

                // Draw tiles
                HallContainer.getHallOfFire().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfFire().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }
            }
        }

        getPlayer().draw(g2);

        drawPlayerLife(g2);

        for (Entity monster : monsters) {
            if (monster != null) {
                monster.draw(g2);
            }
        }

        for (Arrow arrow : getArrows()) {
            if (arrow != null) {
                arrow.draw(g2);
            }
        }

        if (isPaused()) {
            drawPauseScreen(g2);
        }

        g2.dispose();
    }

    private void drawPauseScreen(Graphics2D g2) {
        int x, y;
        String text = "PAUSED";
        x = PanelUtils.getXForCenteredText(text, this, g2);
        y = BasePanel.screenHeight / 2;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 100, y);
    }

    public ArrayList<Entity> getHallMonsters() {
        return monsters;
    }
}

