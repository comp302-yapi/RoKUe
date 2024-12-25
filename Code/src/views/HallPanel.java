package views;

import containers.HallContainer;
import enums.Hall;
import listeners.keylisteners.GamePanelKeyListener;
import listeners.keylisteners.HallPanelKeyListener;
import managers.CollisionChecker;
import managers.CollisionCheckerForHall;
import managers.TileManagerForHall;
import managers.ViewManager;
import object.SuperObject;

import java.awt.*;

public class HallPanel extends PlayablePanel{

    public Hall currentHall = Hall.HallOfEarth;
    private final HallPanelKeyListener keyListener;

    public TileManagerForHall tileM;

    final CollisionCheckerForHall cChecker;


    public HallPanel(ViewManager viewManager) {
        super(viewManager);
        this.keyListener = new HallPanelKeyListener(this);

        this.addKeyListener(keyListener);
        getPlayer().addKeyListener(keyListener);

        this.tileM = HallContainer.getHallOfEarth();
        this.cChecker = new CollisionCheckerForHall(this,tileM);

        getPlayer().panel = this;


    }

    @Override
    public void update() {
        getPlayer().move();

    }

    public TileManagerForHall getTileM(){ return this.tileM;}

    @Override
    public void showMessage(String message) {

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

