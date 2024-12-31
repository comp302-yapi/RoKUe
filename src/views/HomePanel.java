package views;

import containers.HallContainer;
import data.HomePanelData;
import entity.Entity;
import entity.Player;
import listeners.keylisteners.HomePanelKeyListener;
import managers.*;
import object.OBJ_Cactus;
import object.OBJ_IronArmor;
import object.OBJ_LeatherArmor;
import object.SuperObject;

import java.awt.*;
import java.util.ArrayList;

public class HomePanel extends PlayablePanel {

    private final ArrayList<Entity> entities = new ArrayList<>();
    private boolean isPaused;
    private final HomePanelKeyListener keyListener;
    private final TileManagerForHome tileM;
    final CollisionCheckerForHome cChecker;

    public HomePanel(ViewManager viewManager) {
        super(viewManager);
        setLayout(null); // No layout, fully customizable space
        getPlayer().panel = this; // Associate the player with this panel

        this.keyListener = new HomePanelKeyListener(this);
        this.addKeyListener(keyListener);

        Player player = Player.getInstance(this);
        player.addKeyListener(keyListener);

        this.tileM = new TileManagerForHome(this,  "/res/maps/Home.txt", 30, 18);
        getPlayer().addKeyListener(keyListener);

        this.cChecker = new CollisionCheckerForHome(this);

        OBJ_IronArmor ironArmor = new OBJ_IronArmor();
        OBJ_LeatherArmor leatherArmor = new OBJ_LeatherArmor();

        tileM.addObject(ironArmor, 200, 400);
        tileM.addObject(leatherArmor, 1200, 400);


    }

    @Override
    public void update() {
        getPlayer().move();
    }

    public TileManagerForHome getTileManagerHome() {
        return tileM;
    }

    @Override
    public void showMessage(String message) {

    }

    public CollisionCheckerForHome getCollisionCheckerForHome(){return this.cChecker;}


    public void buyArmor() {

        SuperObject armorToBuy = chooseArmor();

        if (getPlayer().gold >= armorToBuy.cost) {
            getPlayer().gold -= armorToBuy.cost;
            getPlayer().armor = armorToBuy.armor;
        }
    }

    public SuperObject chooseArmor() {
        SuperObject closestObject = null;
        double closestDistance = Double.MAX_VALUE;

        int playerX = getPlayer().screenX;
        int playerY = getPlayer().screenY;

        for (SuperObject object : tileM.objects) {
            if (object != null) {
                double distance = Math.sqrt(
                        Math.pow(object.worldX - playerX, 2) + Math.pow(object.worldY - playerY, 2)
                );

                if (distance < closestDistance) {
                    closestDistance = distance;
                    closestObject = object;
                }
            }
        }

        return closestObject;
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        update();

        // Clear the panel
        g2.setColor(Color.WHITE); // Background color
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw Tiles
        tileM.draw(g2);

        // Draw Objects
        for (SuperObject superObject : tileM.objects) {
            if (superObject != null) {
                g2.drawImage(superObject.image, superObject.worldX, superObject.worldY, HomePanel.tileSize, HomePanel.tileSize, null);
            }
        }

        // Draw Character Info
        drawPlayerAttributes(g2);

        // Draw the player
        getPlayer().draw(g2);


        // If paused, show a pause screen
        if (isPaused) {
            drawPauseScreen(g2);
        }

        g2.dispose();
    }

    private void drawPauseScreen(Graphics2D g2) {
        String text = "PAUSED";
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.setColor(Color.BLACK);
        int x = (getWidth() - g2.getFontMetrics().stringWidth(text)) / 2;
        int y = getHeight() / 2;
        g2.drawString(text, x, y);
    }

    private void drawPlayerAttributes(Graphics2D g2) {
        Player player = getPlayer(); // Access the player instance

        String[] attributes = {
                "Gold: " + player.gold,
                "Armor: " + player.armor,
                "Speed: " + player.speed,
                "Life: " + player.life + "/" + player.maxLife,
                "ScreenX: " + player.screenX,
                "ScreenY: " + player.screenY
        };

        g2.setFont(new Font("Arial", Font.BOLD, 30));
        g2.setColor(Color.BLACK);

        int x = getWidth() - 250;
        int y = 80;

        for (String attribute : attributes) {
            g2.drawString(attribute, x, y);
            y += 40;
        }
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public HomePanelData exportData() {
        return new HomePanelData(
                new ArrayList<>(tileM.objects), // Clone the list of objects
                getPlayer().gold,
                getPlayer().armor,
                getPlayer().speed,
                getPlayer().life,
                getPlayer().maxLife,
                getPlayer().screenX,
                getPlayer().screenY
        );
    }

    public void restoreData(HomePanelData data) {
        tileM.objects.clear();
        tileM.objects.addAll(data.objectsInHome); // Restore objects in HomePanel

        Player player = getPlayer();
        player.gold = data.playerGold;
        player.armor = data.playerArmor;
        player.speed = data.playerSpeed;
        player.life = data.playerLife;
        player.maxLife = data.playerMaxLife;
        player.screenX = data.playerScreenX;
        player.screenY = data.playerScreenY;
    }



}