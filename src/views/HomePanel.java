package views;

import containers.HallContainer;
import data.HomePanelData;
import entity.Entity;
import entity.Player;
import listeners.keylisteners.HomePanelKeyListener;
import managers.*;
import object.*;
import utils.ImageUtils;
import utils.PanelUtils;

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
    SuperObject ironLeggings = new ARMOR_IronArmorLeg();
    SuperObject leatherLeggings = new ARMOR_LeatherArmorLeg();

    SWORD_IronSword ironSwordObj = SWORD_IronSword.getInstance();
    private soundManager soundManager;
    public boolean attackSoundPlayed;

    transient BufferedImage heart_full, heart_half, heart_blank;
    SuperObject heart = new OBJ_Heart();
    transient BufferedImage armor_full, armor_half, armor_blank;
    SuperObject armor = new OBJ_Armor();
    public Font maruMonica;

    public HomePanel(ViewManager viewManager) {
        super(viewManager);
        setLayout(null); // No layout, fully customizable space
        getPlayer().panel = this; // Associate the player with this panel

        this.keyListener = new HomePanelKeyListener(this);
        this.addKeyListener(keyListener);

        Player player = Player.getInstance(this);
        player.addKeyListener(keyListener);

        this.soundManager = managers.soundManager.getInstance();

        this.tileM = new TileManagerForHome(this,  "/res/maps/Home.txt", 30, 18);
        getPlayer().addKeyListener(keyListener);

        this.cChecker = new CollisionCheckerForHome(this);

        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        armor_full = armor.image;
        armor_half = armor.image2;
        armor_blank = armor.image3;

        PanelUtils panelUtil = new PanelUtils();
        this.maruMonica = panelUtil.getNewFont();

        ARMOR_IronArmorTorso ironArmorTorso = new ARMOR_IronArmorTorso();
        ARMOR_LeatherArmorTorso leatherArmorTorso = new ARMOR_LeatherArmorTorso();
        ARMOR_IronArmorHead ironArmorHead = new ARMOR_IronArmorHead();
        ARMOR_LeatherArmorHead leatherArmorHead = new ARMOR_LeatherArmorHead();
        ARMOR_IronArmorLeg ironArmorLeg = new ARMOR_IronArmorLeg();
        ARMOR_LeatherArmorLeg leatherArmorLeg = new ARMOR_LeatherArmorLeg();

        SWORD_IronSword ironSwordObj = SWORD_IronSword.getInstance();
        SWORD_DiamondSword diamondSwordObj = SWORD_DiamondSword.getInstance();
        SWORD_GoldSword goldSwordObj = SWORD_GoldSword.getInstance();
        tileM.addObject(ironSwordObj, 290, 350);
        tileM.addObject(diamondSwordObj, 290, 450);
        tileM.addObject(goldSwordObj, 290, 550);

        tileM.addObject(leatherArmorHead, 800, 188);
        tileM.addObject(leatherArmorTorso, 900, 188);

        tileM.addObject(ironArmorTorso, 500, 188);
        tileM.addObject(ironArmorHead, 600, 188);

        tileM.addObject(ironArmorLeg, 1110, 350);
        tileM.addObject(leatherArmorLeg, 1110, 450);
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

        SuperObject armorToBuy = chooseBuy();

        if (armorToBuy != null && !armorToBuy.bought) {
            if (getPlayer().gold >= armorToBuy.cost) {
                playSE(17);
                getPlayer().gold -= armorToBuy.cost;

                armorToBuy.bought = true;

                switch (armorToBuy.name) {
                    case "Iron Chestplate" -> getPlayer().wearArmorIronTorso();
                    case "Leather Chestplate" -> getPlayer().wearArmorLeatherTorso();
                    case "Leather Helmet" -> getPlayer().wearArmorLeatherHead();
                    case "Iron Helmet" -> getPlayer().wearArmorIronHead();
                    case "Iron Leggings" -> getPlayer().wearArmorIronLeg();
                    case "Leather Leggings" -> getPlayer().wearArmorLeatherLeg();
                    case "Iron Sword" -> getPlayer().equipIronSword();
                    case "Diamond Sword" -> getPlayer().equipDiamondSword();
                    case "Gold Sword" -> getPlayer().equipGoldSword();
                }

                getPlayer().armor = calculateArmor();
            }
        }
    }

    public void equipBought() {
        SuperObject armorToEquip = chooseBuy();

        if (armorToEquip != null && armorToEquip.bought) {

            switch (armorToEquip.name) {
                case "Iron Chestplate" -> getPlayer().wearArmorIronTorso();
                case "Leather Chestplate" -> getPlayer().wearArmorLeatherTorso();
                case "Leather Helmet" -> getPlayer().wearArmorLeatherHead();
                case "Iron Helmet" -> getPlayer().wearArmorIronHead();
                case "Iron Leggings" -> getPlayer().wearArmorIronLeg();
                case "Leather Leggings" -> getPlayer().wearArmorLeatherLeg();
                case "Iron Sword" -> getPlayer().equipIronSword();
                case "Diamond Sword" -> getPlayer().equipDiamondSword();
                case "Gold Sword" -> getPlayer().equipGoldSword();
            }

            getPlayer().armor = calculateArmor();
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

        if (getPlayer().armorOnLeatherLeg) {
            armor += leatherLeggings.armor;
        }

        if (getPlayer().armorOnIronLeg) {
            armor += ironLeggings.armor;
        }

        return armor;
    }

    public SuperObject chooseBuy() {
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
//        drawPlayerAttributes(g2);

        // Draw the player
        getPlayer().draw(g2);

        // Character Info
        drawCharacterInfo(g2);

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
            BufferedImage goldImage, damageImage, armorImage;
            try {
                goldImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/objects/gold/tile001.png")));
                damageImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/objects/sword_normal.png")));
                armorImage = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/objects/shield_wood.png")));
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            goldImage = uTool.scaleImage(goldImage, 32, 32);
            damageImage = uTool.scaleImage(damageImage, 32, 32);
            armorImage = uTool.scaleImage(armorImage, 32, 32);

            int panelWidth = 200;
            int panelHeight = 150;
            int panelX, panelY;

            if (closestObject instanceof SWORD_IronSword || closestObject instanceof SWORD_DiamondSword || closestObject instanceof SWORD_GoldSword) {
                panelX = closestObject.worldX - panelWidth - 20;
                panelY = closestObject.worldY + HomePanel.tileSize / 2 - panelHeight / 2;
            } else if (closestObject instanceof ARMOR_IronArmorLeg || closestObject instanceof ARMOR_LeatherArmorLeg) {
                panelX = closestObject.worldX + HomePanel.tileSize + 20;
                panelY = closestObject.worldY + HomePanel.tileSize / 2 - panelHeight / 2;
            } else {
                panelX = closestObject.worldX + HomePanel.tileSize / 2 - panelWidth / 2;
                panelY = closestObject.worldY - panelHeight - 20;
            }

            g2.setColor(new Color(50, 50, 50, 200));
            g2.fillRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(3));
            g2.drawRoundRect(panelX, panelY, panelWidth, panelHeight, 10, 10);

            int objImageX = panelX + 10;
            int objImageY = panelY + 10;
            g2.drawImage(uTool.scaleImage(closestObject.image, 32, 32), objImageX, objImageY, null);
            g2.setFont(maruMonica);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
            g2.setColor(Color.WHITE);
            g2.drawString(closestObject.name, objImageX + 40, objImageY + 20);

            if (closestObject instanceof SWORD_IronSword || closestObject instanceof SWORD_DiamondSword || closestObject instanceof SWORD_GoldSword) {
                int damageX = objImageX;
                int damageY = objImageY + 40;
                g2.drawImage(damageImage, damageX, damageY, null);
                g2.drawString(String.valueOf(closestObject.damage), damageX + 40, damageY + 20);
            } else {
                int armorX = objImageX;
                int armorY = objImageY + 40;
                g2.drawImage(armorImage, armorX, armorY, null);
                g2.drawString(String.valueOf(closestObject.armor), armorX + 40, armorY + 20);
            }

            int goldX = objImageX;
            int goldY = objImageY + 80;
            g2.drawImage(goldImage, goldX, goldY, null);
            g2.drawString(String.valueOf(closestObject.cost), goldX + 40, goldY + 20);

            g2.setFont(maruMonica);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 25F));
            g2.setColor(Color.YELLOW);
            if (closestObject.bought) {
                g2.drawString("Press E to equip", panelX + 10, panelY + panelHeight - 10);
            } else {
                g2.drawString("Press B to buy", panelX + 10, panelY + panelHeight - 10);
            }
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

    private void drawPlayerLife(Graphics2D g2) {
        int panelWidth = 150;
        int panelHeight = 166;
        int screenWidth = this.getWidth();
        int screenHeight = this.getHeight();

        int offsetX = 560;
        int offsetY = 260;

        int x = screenWidth - panelWidth - 5 - 40;
        int y = 60;


        int i = 0;

        while (i < getPlayer().maxLife / 2) {
            g2.drawImage(heart_blank, x, y, tileSize / 2, tileSize / 2, null);
            i++;
            x += tileSize / 4 + 2;
        }

        x = screenWidth - panelWidth - 5 - 40;
        i = 0;

        while (i < getPlayer().life) {
            g2.drawImage(heart_half, x, y, tileSize / 2, tileSize / 2, null);
            i++;
            if (i < getPlayer().life) {
                g2.drawImage(heart_full, x, y, tileSize / 2, tileSize / 2, null);
            }
            i++;
            x += tileSize / 4 + 2;
        }
    }

    private void drawPlayerArmor(Graphics2D g2) {
        int panelWidth = 150;
        int panelHeight = 166;
        int screenWidth = this.getWidth();
        int screenHeight = this.getHeight();

        int offsetX = 560;
        int offsetY = 260;

        int x = screenWidth - panelWidth - 5 - 40;
        int y = 80;
        int i = 0;

        while (i < getPlayer().maxArmor / 2) {
            g2.drawImage(armor_blank, x, y, tileSize / 2, tileSize / 2, null);
            i++;
            x += tileSize / 4 + 2;
        }

        x = screenWidth - panelWidth - 5 - 40;
        y = 80;
        i = 0;

        while (i < getPlayer().armor) {
            g2.drawImage(armor_half, x, y, tileSize / 2, tileSize / 2, null);
            i++;
            if (i < getPlayer().armor) {
                g2.drawImage(armor_full, x, y, tileSize / 2, tileSize / 2, null);
            }
            i++;
            x += tileSize / 4 + 2;
        }
    }

    public void drawCharacterInfo(Graphics2D g2) {
        int panelWidth = 150;
        int panelHeight = 166;
        int screenWidth = this.getWidth();
        int screenHeight = this.getHeight();

        int x = screenWidth - panelWidth - 5 - 40;
        int y = 40;

        g2.setColor(new Color(30, 30, 30, 200));
        g2.fillRoundRect(x, y, panelWidth, panelHeight, 10, 10);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(3));
        g2.drawRoundRect(x, y, panelWidth, panelHeight, 10, 10);

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x + 5, y + 5, 130, 10);

        g2.setColor(Color.YELLOW);
        int xpPercentage = (int) (getPlayer().xpCurrent / (float) getPlayer().xpMax * 130);
        g2.fillRect(x + 5, y + 5, xpPercentage, 10);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(1));
        g2.drawRect(x + 5, y + 5, 130, 10);

        drawPlayerLife(g2);
        drawPlayerArmor(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 16F));

        g2.drawString("Level: " + getPlayer().level, x + 5, y + 80);
        g2.drawString("Gold: " + getPlayer().gold, x + 5, y + 100);
        g2.drawString("Armor: " + getPlayer().armor, x + 5, y + 120);
        g2.drawString("Life: " + getPlayer().life + "/" + getPlayer().maxLife, x + 5, y + 140);
        g2.drawString("Xp: " + getPlayer().xpCurrent + "/" + getPlayer().xpMax, x + 5, y + 160);
    }

    public void playSE(int i) {

        soundManager.setFile(i);
        soundManager.play();

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
                getPlayer().armorOnLeatherHead,
                getPlayer().armorOnLeatherTorso,
                getPlayer().armorOnIronHead,
                getPlayer().armorOnIronTorso
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