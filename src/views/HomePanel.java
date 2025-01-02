package views;

import containers.HallContainer;
import data.HomePanelData;
import entity.Entity;
import entity.Player;
import listeners.keylisteners.HomePanelKeyListener;
import managers.*;
import object.*;
import utils.ImageUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class HomePanel extends PlayablePanel {

    private final ArrayList<Entity> entities = new ArrayList<>();
    private boolean isPaused;
    private final HomePanelKeyListener keyListener;
    private final TileManagerForHome tileM;
    final CollisionCheckerForHome cChecker;
    SuperObject leatherHelmet = new ARMOR_LeatherArmorHead();
    SuperObject leatherArmorTorso = new ARMOR_LeatherArmorTorso();
    SuperObject ironHelmet = new ARMOR_IronArmorHead();
    SuperObject ironTorso = new ARMOR_IronArmorTorso();

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

        ARMOR_IronArmorTorso ironArmorTorso = new ARMOR_IronArmorTorso();
        ARMOR_LeatherArmorTorso leatherArmorTorso = new ARMOR_LeatherArmorTorso();
        ARMOR_IronArmorHead ironArmorHead = new ARMOR_IronArmorHead();
        ARMOR_LeatherArmorHead leatherArmorHead = new ARMOR_LeatherArmorHead();


        tileM.addObject(leatherArmorHead, 800, 188);
        tileM.addObject(leatherArmorTorso, 900, 188);

        tileM.addObject(ironArmorTorso, 500, 188);
        tileM.addObject(ironArmorHead, 600, 188);

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

        if (armorToBuy != null) {
            if (getPlayer().gold >= armorToBuy.cost) {
                getPlayer().gold -= armorToBuy.cost;

                switch (armorToBuy.name) {
                    case "Iron Chestplate" -> getPlayer().wearArmorIronTorso();
                    case "Leather Chestplate" -> getPlayer().wearArmorLeatherTorso();
                    case "Leather Helmet" -> getPlayer().wearArmorLeatherHead();
                    case "Iron Helmet" -> getPlayer().wearArmorIronHead();
                }

                getPlayer().armor = calculateArmor();

            }
        }
    }

    public int calculateArmor() {
        int armor = 0;

        if (getPlayer().armorOnLeatherHead) {
            armor += leatherHelmet.armor;
        }

        if (getPlayer().armorOnLeatherTorso) {
            armor += leatherArmorTorso.armor;
        }

        if (getPlayer().armorOnIronHead) {
            armor += ironHelmet.armor;
        }

        if (getPlayer().armorOnIronTorso) {
            armor += ironTorso.armor;
        }

        return armor;
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

                if (distance <= 4 * 48 && distance < closestDistance) {
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

        displayClosestObjectName(g2);

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

    private void displayClosestObjectName(Graphics2D g2) {
        SuperObject closestObject = null;
        double closestDistance = Double.MAX_VALUE;
        ImageUtils uTool = new ImageUtils();

        // Find the closest object
        for (SuperObject object : tileM.objects) {
            if (object != null) {
                double distance = Math.sqrt(
                        Math.pow(object.worldX - getPlayer().screenX, 2) +
                                Math.pow(object.worldY - getPlayer().screenY, 2)
                );

                if (distance < closestDistance && distance <= 48 * 4) {
                    closestDistance = distance;
                    closestObject = object;
                }
            }
        }

        // If there's a closest object, display its information
        if (closestObject != null) {
            // Load icons
            BufferedImage goldImage, armorImage;
            try {
                goldImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/objects/gold/tile001.png")));
                armorImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/objects/shield_wood.png")));
            } catch (IOException e) {
                e.printStackTrace();
                return; // If any image cannot be loaded, do not display anything
            }

            // Scale icons
            goldImage = uTool.scaleImage(goldImage, 32, 32);
            armorImage = uTool.scaleImage(armorImage, 32, 32);

            // Info panel dimensions
            int panelWidth = 200;
            int panelHeight = 130;
            int panelX = closestObject.worldX + HomePanel.tileSize / 2 - panelWidth / 2;
            int panelY = closestObject.worldY - panelHeight - 20;

            // Draw the panel background
            g2.setColor(new Color(50, 50, 50, 200)); // Grey with transparency
            g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);

            // Draw the panel border
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);

            // Display object image and name
            int objImageX = panelX + 10;
            int objImageY = panelY + 10;
            g2.drawImage(uTool.scaleImage(closestObject.image, 32, 32), objImageX, objImageY, null);
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.setColor(Color.WHITE);
            g2.drawString(closestObject.name, objImageX + 40, objImageY + 20);

            // Display armor info
            int armorX = objImageX;
            int armorY = objImageY + 40;
            g2.drawImage(armorImage, armorX, armorY, null);
            g2.drawString(String.valueOf(closestObject.armor), armorX + 40, armorY + 20);

            // Display cost info
            int goldX = objImageX;
            int goldY = armorY + 40;
            g2.drawImage(goldImage, goldX, goldY, null);
            g2.drawString(String.valueOf(closestObject.cost), goldX + 40, goldY + 20);
        }
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
                getPlayer().screenY,
                getPlayer().armorOnIronHead,
                getPlayer().armorOnIronTorso,
                getPlayer().armorOnLeatherHead,
                getPlayer().armorOnLeatherTorso
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
        player.armorOnLeatherTorso = data.armorOnLeatherTorso;
        player.armorOnLeatherHead = data.armorOnLeatherHead;
        player.armorOnIronTorso = data.armorOnIronTorso;
        player.armorOnIronHead = data.armorOnIronHead;

    }



}