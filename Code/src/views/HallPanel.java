package views;

import containers.HallContainer;
import entity.Arrow;
import enums.Hall;
import listeners.keylisteners.HallPanelKeyListener;
import listeners.mouselisteners.HallPanelMouseListener;
import managers.CollisionCheckerForHall;
import managers.TileManagerForHall;
import managers.ViewManager;
import monster.MON_Archer;
import object.SuperObject;
import utils.PanelUtils;

import java.awt.*;
import java.util.Random;

public class HallPanel extends PlayablePanel{

    public Hall currentHall = Hall.HallOfEarth;
    private final HallPanelKeyListener keyListener;
    private final HallPanelMouseListener mouseListener;
    private final MON_Archer m;
    public TileManagerForHall tileM;
    final CollisionCheckerForHall cChecker;
    private boolean isPaused;

    public HallPanel(ViewManager viewManager) {
        super(viewManager);
        this.tileM = HallContainer.getHallOfEarth();
        this.keyListener = new HallPanelKeyListener(this);
        this.addKeyListener(keyListener);
        getPlayer().addKeyListener(keyListener);

        this.mouseListener = new HallPanelMouseListener(this, getTileM());
        this.addMouseListener(mouseListener);
        this.cChecker = new CollisionCheckerForHall(this, tileM);

        getPlayer().panel = this;

        m = new MON_Archer(this);

        m.worldX = BasePanel.tileSize*10;
        m.worldY = BasePanel.tileSize*10;
        m.spawned = true;
        getMonsters()[0] = m;
    }

    @Override
    public void update() {
        getPlayer().move();
        m.update();

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

    }

    public TileManagerForHall getTileM(){ return this.tileM;}

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

    private void assignRunesToObjects() {
        Random random = new Random();

        // Get all objects in the current hall (for example purposes, using HallOfEarth)
        SuperObject[] objects = HallContainer.getHallOfEarth().objects.toArray(new SuperObject[20]);

        // Number of objects that can have runes (you can adjust this number as needed)
        int runeObjectCount = 1;

        // Randomly assign 'rune' to the specified number of objects
        for (int i = 0; i < runeObjectCount; i++) {
            int randomIndex;

            // Find a random object that does not already have a rune
            do {
                randomIndex = random.nextInt(objects.length);
            } while (objects[randomIndex] == null || objects[randomIndex].hasRune);

            // Assign a rune to the object
            objects[randomIndex].hasRune = true;
        }
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
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
                getPlayer().draw(g2);

                // draw the monster image
                m.draw(g2);

                for (Arrow arrow : getArrows()) {
                    if (arrow != null) {
                        arrow.draw(g2);
                    }
                }
            }
            case HallOfAir -> {
                // Update game
                update();

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
                // Update game
                update();

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
                // Update game
                update();

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
}

