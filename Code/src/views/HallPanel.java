package views;

import containers.HallContainer;
import controllers.HallController;
import enums.Hall;
import listeners.keylisteners.GamePanelKeyListener;
import listeners.keylisteners.HallPanelKeyListener;
import managers.CollisionChecker;
import managers.CollisionCheckerForHall;
import managers.TileManagerForHall;
import managers.ViewManager;
import object.SuperObject;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.Random;

public class HallPanel extends PlayablePanel{

    public Hall currentHall = Hall.HallOfEarth;
    private final HallPanelKeyListener keyListener;

    public TileManagerForHall tileM;

    final CollisionCheckerForHall cChecker;
    private final HallController hallController;

    public HallPanel(ViewManager viewManager) {
        super(viewManager);
        this.keyListener = new HallPanelKeyListener(this);
        this.addKeyListener(keyListener);
        getPlayer().addKeyListener(keyListener);

        this.tileM = HallContainer.getHallOfEarth();
        this.cChecker = new CollisionCheckerForHall(this, tileM);

        getPlayer().panel = this;

        hallController = new HallController(null); // Pass the required argument (e.g., BuildPanel) if needed
        assignRunesToObjects();
    }

    public void mousePressed(MouseEvent e) {
        int mouseX = e.getX();
        int mouseY = e.getY();

        // Use the hallController instance to call getObjectSelectedInHall
        SuperObject clickedObject = hallController.getObjectSelectedInHall(tileM, mouseX, mouseY);
        if (clickedObject != null) {
            clickedObject.interact(this); // Call interact method to handle rune detection
        }
    }

    @Override
    public void update() {
        getPlayer().move();

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
    public void assignRunesToObjects() {
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

