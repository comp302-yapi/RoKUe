package views;

import containers.HallContainer;
import controllers.HallController;
import entity.Arrow;
import enums.Hall;
import listeners.keylisteners.HallPanelKeyListener;
import listeners.mouselisteners.HallPanelMouseListener;
import managers.CollisionCheckerForHall;
import managers.TileManagerForHall;
import managers.ViewManager;
import monster.MON_Archer;
import object.SuperObject;
import java.awt.*;

public class HallPanel extends PlayablePanel{

    public Hall currentHall = Hall.HallOfEarth;
    private final HallPanelKeyListener keyListener;
    private final HallPanelMouseListener mouseListener;
    private final MON_Archer m;
    public TileManagerForHall tileM;
    final CollisionCheckerForHall cChecker;
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

        HallController.shouldSwitchHallsInGame(getTileM(), getPlayer(), this);
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

    public void paintComponent(Graphics g) {

        switch (currentHall) {
            case HallOfEarth -> {
                // Update game
                update();

                // Repaint game
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
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

                g2.dispose();
            }
            case HallOfAir -> {
                // Update game
                update();

                // Repaint game
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                g2.setFont(arial_40);

                // Draw tiles
                HallContainer.getHallOfAir().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfAir().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }


                g2.dispose();

            }
            case HallOfWater -> {
                // Update game
                update();

                // Repaint game
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                g2.setFont(arial_40);

                // Draw tiles
                HallContainer.getHallOfWater().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfWater().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }


                g2.dispose();

            }
            case HallOfFire -> {
                // Update game
                update();

                // Repaint game
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D)g;
                g2.setFont(arial_40);

                // Draw tiles
                HallContainer.getHallOfFire().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfFire().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }
                g2.dispose();
            }
        }
    }
}

