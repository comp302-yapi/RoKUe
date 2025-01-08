package views;

import containers.HallContainer;
import containers.TileContainer;
import data.BossPanelData;
import data.HomePanelData;
import entity.*;
import listeners.keylisteners.BossPanelKeyListener;
import listeners.keylisteners.HomePanelKeyListener;
import managers.*;
import monster.*;
import object.*;
import superpower.SuperPower;
import utils.ImageUtils;
import utils.PanelUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.*;
import java.util.List;

public class BossPanel extends PlayablePanel {
    private final ArrayList<Entity> entities = new ArrayList<>();
    private boolean isPaused;
    private final BossPanelKeyListener keyListener;
    private final TileManagerForBoss tileM;
    final CollisionCheckerForBoss cChecker;
    private final ArrayList<Entity> monsters = new ArrayList<>();
    public ArrayList<SuperObject> objects = new ArrayList<>();

    public boolean easterEggFinal;

    private int shakeMagnitude = 0;
    private int shakeDuration = 0;
    private Random shakeRandom = new Random();

    SuperObject leatherHelmet = new ARMOR_LeatherArmorHead();
    SuperObject leatherArmorTorso = new ARMOR_LeatherArmorTorso();
    SuperObject ironHelmet = new ARMOR_IronArmorHead();
    SuperObject ironTorso = new ARMOR_IronArmorTorso();
    SuperObject ironLeggings = new ARMOR_IronArmorLeg();
    SuperObject leatherLeggings = new ARMOR_LeatherArmorLeg();

    SWORD_IronSword ironSwordObj = SWORD_IronSword.getInstance();
    public soundManager soundManager;
    public boolean attackSoundPlayed;

    transient BufferedImage heart_full, heart_half, heart_blank;
    SuperObject heart = new OBJ_Heart();
    transient BufferedImage armor_full, armor_half, armor_blank;
    SuperObject armor = new OBJ_Armor();
    public Font maruMonica;

    // SUPERPOWERS
    private JPanel superPowerPanel; // Panel for superpowers
    public ArrayList<SuperPower> superPowers = new ArrayList<>(); // List of superpowers

    public boolean groundSlamActive = false;

    public boolean isShaking = false;
    public int shakeCounter = 0;
    public int shakeOffset = 0;
    public int shakingIndex = -1;

    private int lightningCounter = 0;
    private java.util.List<Entity> lightningTargets = new ArrayList<>();

    // Particles
    transient ArrayList<BufferedImage> bloodAnimation = new ArrayList<>();
    public PRTCL_Blood bloodParticle;
    private final java.util.List<SuperParticle> activeBloodParticles = new ArrayList<>();

    transient ArrayList<BufferedImage> burningAnimation = new ArrayList<>();
    public PRTCL_Burning burningParticle;
    private final java.util.List<SuperParticle> activeBurningParticles = new ArrayList<>();

    transient ArrayList<BufferedImage> lightningAnimation = new ArrayList<>();
    public PRTCL_Lightning lightningParticle;
    private final java.util.List<SuperParticle> activeLightningParticles = new ArrayList<>();

    private final List<SuperParticle> activeParticles = new ArrayList<>();
    private List<SorcererProjectile> sorcererProjectiles = new ArrayList<>();
    private List<Laser> lasers = new ArrayList<>();

    public boolean bossSpawned;

    public int spawnCounter;

    public BOSS_Sorcerer boss;
    public boolean canSpawnBoss = true;

    public BossPanel(ViewManager viewManager) {
        super(viewManager);
        setLayout(null); // No layout, fully customizable space
        getPlayer().panel = this; // Associate the player with this panel

        this.keyListener = new BossPanelKeyListener(this);
        this.addKeyListener(keyListener);

        Player player = Player.getInstance(this);
        player.addKeyListener(keyListener);

        this.soundManager = managers.soundManager.getInstance();

        this.tileM = new TileManagerForBoss(this,  "/res/maps/Boss.txt", 30, 18);
        getPlayer().addKeyListener(keyListener);

        this.cChecker = new CollisionCheckerForBoss(this);

        // PARTICLES
        bloodParticle = new PRTCL_Blood(bloodAnimation, 400, 400);

        burningParticle = new PRTCL_Burning(burningAnimation, null);

        lightningParticle = new PRTCL_Lightning(lightningAnimation, null);

        // SUPERPOWER
        initSuperPowerPanel();

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
        ARMOR_LeatherArmorLeg leatherArmorLeggings = new ARMOR_LeatherArmorLeg();
        ARMOR_IronArmorLeg ironArmorLeg = new ARMOR_IronArmorLeg();

        SWORD_IronSword ironSwordObj = SWORD_IronSword.getInstance();
        SWORD_DiamondSword diamondSwordObj = SWORD_DiamondSword.getInstance();    }

    @Override
    public void update() {
        getPlayer().move();

        if (getPlayer().life <= 0) {
            getPlayer().life = getPlayer().maxLife;
            System.out.println("SWITCH5");
            getViewManager().switchTo("HomePanel", true);
        }

        updateLightningEffect();

        for (SuperPower power : superPowers) {
            power.tickCooldown();
        }

        if (getPlayer().fireball != null) {
            getPlayer().fireball.update();
            if (getPlayer().fireball.isExpired()) {
                getPlayer().fireball = null;
            }
        }

        // Generate Monster
        spawnCounter++;

        if (spawnCounter >= 60 * 1.5 ) {
            if (canSpawnBoss) {
                spawnBoss();
                canSpawnBoss = false;
            }
            if (boss.life <= boss.maxLife / 2) {
                generateMonster();
            }
            spawnCounter = 0;
        }


        // Update monster
        for (Entity monster : monsters) {
            if (monster != null) {
                monster.update();
                if (monster instanceof BOSS_Sorcerer bossSorcerer) {
                    if (bossSorcerer.sorcererProjectile != null) {
                        bossSorcerer.sorcererProjectile.update();
                    }
                    if (bossSorcerer.laser != null) {
                        bossSorcerer.laser.update();
                    }
                }
            }
        }

        // Update projectile
        for (Iterator<SorcererProjectile> iterator = sorcererProjectiles.iterator(); iterator.hasNext(); ) {
            SorcererProjectile sorcererProjectile = iterator.next();
            sorcererProjectile.update();
            if (sorcererProjectile.isExpired()) {
                iterator.remove();
            }
        }

        // Update projectile
        for (Iterator<Laser> iterator = lasers.iterator(); iterator.hasNext(); ) {
            Laser laser = iterator.next();
            laser.update();
            if (laser.isExpired()) {
                iterator.remove();
            }
        }

    }

    public TileManagerForBoss getTileManagerForBoss() {
        return tileM;
    }

    @Override
    public void showMessage(String message) {

    }

    public CollisionCheckerForBoss getCollisionCheckerForBoss(){return this.cChecker;}

    public void buyArmor() {

        SuperObject armorToBuy = chooseBuy();

        if (armorToBuy != null) {
            if (getPlayer().gold >= armorToBuy.cost) {
                playSE(17);
                getPlayer().gold -= armorToBuy.cost;

                switch (armorToBuy.name) {
                    case "Iron Chestplate" -> getPlayer().wearArmorIronTorso();
                    case "Leather Chestplate" -> getPlayer().wearArmorLeatherTorso();
                    case "Leather Helmet" -> getPlayer().wearArmorLeatherHead();
                    case "Iron Helmet" -> getPlayer().wearArmorIronHead();

                    case "Iron Sword" -> getPlayer().equipIronSword();
                    case "Diamond Sword" -> getPlayer().equipDiamondSword();
                    case "Gold Sword" -> getPlayer().equipGoldSword();

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

    public Entity[] getMonsters() {
        return this.monsters.toArray(new Entity[0]);
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

        // Superpowers
        if (shakeDuration > 0) {
            int offsetX = shakeRandom.nextInt(shakeMagnitude * 2 + 1) - shakeMagnitude;
            int offsetY = shakeRandom.nextInt(shakeMagnitude * 2 + 1) - shakeMagnitude;
            g2.translate(offsetX, offsetY); // Apply offset to graphics
            shakeDuration--; // Decrease shake duration
        }

        if (groundSlamActive) {
            performGroundSlam(g2);
        }

        // MONSTER ETC.
        Iterator<Entity> iteratorMonster = monsters.iterator();
        while (iteratorMonster.hasNext()) {
            Entity monster = iteratorMonster.next();
            if (monster != null) {
                monster.draw(g2);
//                if (monster.isAttackingFighter() && monster.spriteNum == 2) {
//                    g2.setColor(Color.CYAN);
//                    g2.drawRect(
//                            monster.worldX - 80,
//                            monster.worldY - 100,
//                            monster.attackArea.width,
//                            monster.attackArea.height
//                    );
//                }

                if (monster instanceof BOSS_Sorcerer boss) {
                    boss.drawBossHealthBar(g2, boss);
                }

                for (SorcererProjectile sorcererProjectile : sorcererProjectiles) {
                    sorcererProjectile.draw(g2);
                }

                for (Laser laser : lasers) {
                    laser.draw(g2);
                }

                if (monster.damageReceived) {
                    SuperParticle newBloodParticle = new PRTCL_Blood(bloodAnimation, 400, 400);
                    newBloodParticle.worldX = monster.worldX;
                    newBloodParticle.worldY = monster.worldY;
                    activeBloodParticles.add(newBloodParticle);

                    if (monster.isBurning) {
                        SuperParticle newBurningParticle = new PRTCL_Burning(burningAnimation, monster);
                        newBurningParticle.worldX = monster.worldX;
                        newBurningParticle.worldY = monster.worldY;
                        activeBurningParticles.add(newBurningParticle);
                        activeParticles.add(newBurningParticle);
                    }

                    activeParticles.add(newBloodParticle);

                    monster.damageReceived = false;

                    if (monster.life <= 0) {
                        getPlayer().addXp(20);
                        monster.alive = false;
                        iteratorMonster.remove();
                    }


                } else {
                    if (!monster.alive) {
                        iteratorMonster.remove();
                    }
                }


            }
        }

        Iterator<SuperParticle> iteratorParticles = activeParticles.iterator();
        while (iteratorParticles.hasNext()) {
            SuperParticle particle = iteratorParticles.next();
            particle.updateAnimation();

            particle.draw(g2, this);
            if (particle.isAnimationComplete()) {
                iteratorParticles.remove();
            }
        }

        for (Arrow arrow : getArrows()) {
            if (arrow != null) {
                arrow.draw(g2);
            }
        }

        // Character Info
        drawCharacterInfo(g2);

        // Fireball
        if (getPlayer().fireball != null) {
            getPlayer().fireball.draw(g2);
        }

        // If paused, show a pause screen
        if (isPaused) {
            drawPauseScreen(g2);
        }

        // Draw Superpowers
        drawSuperPowers(g2);

        g2.dispose();
    }

    public void generateMonster(){

        Random random = new Random();

        int locationX = random.nextInt(1,13) + 7;
        int locationY = random.nextInt(1,14) + 2;

        int gX = (BasePanel.tileSize*locationX - 336)/48;
        int gY = (BasePanel.tileSize*locationY - 96) / 48;

        locationX = random.nextInt(1,13) + 7;
        locationY = random.nextInt(1,14) + 2;
        gX = (BasePanel.tileSize*locationX - 336)/48;
        gY = (BasePanel.tileSize*locationY - 96) / 48;

        MON_LandmineBot landmineBot = new MON_LandmineBot(this);
        landmineBot.worldX = BasePanel.tileSize*locationX;
        landmineBot.worldY = BasePanel.tileSize*locationY;

        landmineBot.spawned = true;

        for (int i = 0; i < getMonsters().length; i++) {

            if (getMonsters()[i] == null) {
                getMonsters()[i] = landmineBot;
                break;
            }
        }
        monsters.add(landmineBot);
    }

    public void spawnBoss(){
        int locationX = 15;
        int locationY = 5;

        boss = new BOSS_Sorcerer(this);
        boss.worldX = BasePanel.tileSize*locationX;
        boss.worldY = BasePanel.tileSize*locationY;

        boss.spawned = true;
        bossSpawned = true;

        for (int i = 0; i < getMonsters().length; i++) {

            if (getMonsters()[i] == null) {
                getMonsters()[i] = boss;
                break;
            }
        }
        monsters.add(boss);
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
            int panelHeight = 130;
            int panelX, panelY;

            if (closestObject instanceof SWORD_IronSword || closestObject instanceof SWORD_DiamondSword) {
                panelX = closestObject.worldX - panelWidth - 20;
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
            g2.setFont(new Font("Arial", Font.BOLD, 16));
            g2.setColor(Color.WHITE);
            g2.drawString(closestObject.name, objImageX + 40, objImageY + 20);

            if (closestObject instanceof SWORD_IronSword || closestObject instanceof SWORD_DiamondSword) {
                int damageX = objImageX;
                int damageY = objImageY + 40;
                g2.drawImage(damageImage, damageX, damageY, null);
                g2.drawString(String.valueOf(closestObject.damage), damageX + 40, damageY + 20);
            } else {
                int armorX = objImageX;
                int armorY = objImageY + 40;
                g2.drawImage(armorImage, armorX, armorY, null); // Placeholder for armor icon
                g2.drawString(String.valueOf(closestObject.armor), armorX + 40, armorY + 20);
            }

            int goldX = objImageX;
            int goldY = objImageY + 80;
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

    private void drawPlayerLife(Graphics2D g2) {
        int offsetX = 1070;
        int offsetY = 50;

        int x = offsetX + tileSize / 4;
        int y = offsetY + tileSize;
        int i = 0;

        while (i < getPlayer().maxLife / 2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += tileSize / 2 + 5;
        }

        x = offsetX + tileSize / 4;
        y = offsetY + tileSize;
        i = 0;

        while (i < getPlayer().life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if (i < getPlayer().life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += tileSize / 2 + 5;
        }
    }

    private void drawPlayerArmor(Graphics2D g2) {
        int offsetX = 1070;
        int offsetY = 50;

        int x = offsetX + tileSize / 4;
        int y = offsetY + 87;
        int i = 0;

        while (i < getPlayer().maxArmor / 2) {
            g2.drawImage(armor_blank, x, y, null);
            i++;
            x += tileSize / 2 + 5;
        }

        x = offsetX + tileSize / 4;
        y = offsetY + 87;
        i = 0;

        while (i < getPlayer().armor) {
            g2.drawImage(armor_half, x, y, null);
            i++;
            if (i < getPlayer().armor) {
                g2.drawImage(armor_full, x, y, null);
            }
            i++;
            x += tileSize / 2 + 5;
        }
    }

    public void drawCharacterInfo(Graphics2D g2) {
        int panelWidth = 300;
        int panelHeight = 332;
        int screenWidth = this.getWidth();
        int screenHeight = this.getHeight();

        int x = screenWidth - panelWidth - 60;
        int y = 50;

        g2.setColor(new Color(30, 30, 30, 200));
        g2.fillRoundRect(x, y, panelWidth, panelHeight, 15, 15);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(x, y, panelWidth, panelHeight, 15, 15);

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(x + 10, y + 10, 260, 20);

        g2.setColor(Color.YELLOW);
        int xpPercentage = (int) (getPlayer().xpCurrent / (float) getPlayer().xpMax * 260);
        g2.fillRect(x + 10, y + 10, xpPercentage, 20);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(x + 10, y + 10, 260, 20);

        drawPlayerLife(g2);
        drawPlayerArmor(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

        g2.drawString("Level: " + getPlayer().level, x + 10, y + 160);
        g2.drawString("Gold: " + getPlayer().gold, x + 10, y + 200);
        g2.drawString("Armor: " + getPlayer().armor, x + 10, y + 240);
        g2.drawString("Life: " + getPlayer().life + "/" + getPlayer().maxLife, x + 10, y + 280);
        g2.drawString("Xp: " + getPlayer().xpCurrent + "/" + getPlayer().xpMax, x + 10, y + 320);
    }

    public void playSE(int i) {

        soundManager.setFile(i);
        soundManager.play();

    }

    public void addSorcererProjectile(SorcererProjectile sorcererProjectile) {
        sorcererProjectiles.add(sorcererProjectile);
    }

    public void addLaser(Laser laser) {
        lasers.add(laser);
    }

    // DRAW METHODS
    public void drawSuperPowers(Graphics2D g2) {
        int currentLevel = getPlayer().level;
        int x = 70;
        int y = 70;
        int iconSize = 48;
        int padding = 10;
        int panelWidth = 150;
        int panelHeight = iconSize + 10;

        int[] levelRequirements = {2, 5, 10};

        for (int i = 0; i < superPowers.size(); i++) {
            SuperPower power = superPowers.get(i);

            int xOffset = (isShaking && i == shakingIndex) ? shakeOffset : 0;

            g2.setColor(new Color(30, 30, 30, 200));
            g2.fillRoundRect(x - 10 + xOffset, y - 5, panelWidth, panelHeight, 15, 15);

            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(5));
            g2.drawRoundRect(x - 10 + xOffset, y - 5, panelWidth, panelHeight, 15, 15);

            g2.setFont(maruMonica);
            g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

            if (currentLevel < levelRequirements[i]) {
                g2.setColor((isShaking && i == shakingIndex) ? Color.RED : Color.WHITE);
                g2.drawString("LOCKED", x + 20 + xOffset, y + iconSize / 2 + 12);
            } else {
                try {
                    Image icon = new ImageIcon(getClass().getResource(power.getIconPath())).getImage();
                    g2.drawImage(icon, x + xOffset, y, iconSize, iconSize, null);

                    g2.setColor(Color.WHITE);
                    String cooldownText = power.getRemainingCooldown() > 0 ? power.getRemainingCooldown() + "s" : "Ready";
                    g2.drawString(cooldownText, x + iconSize + 15 + xOffset, y + iconSize / 2 + 12);
                } catch (Exception e) {
                    System.err.println("Failed to load icon: " + power.getIconPath());
                }
            }

            y += panelHeight + padding;
        }

        if (isShaking) {
            shakeCounter++;
            shakeOffset = (shakeCounter % 2 == 0) ? -2 : 2;

            if (shakeCounter >= 10) {
                isShaking = false;
                shakeCounter = 0;
                shakeOffset = 0;
                shakingIndex = -1;
            }
        }
    }

    public void triggerShake(int index) {
        isShaking = true;
        shakeCounter = 0;
        shakeOffset = 0;
        shakingIndex = index;
        playSE(12);
    }

    public void drawInventory(Graphics2D g2) {
        g2.drawImage(TileContainer.getTile()[19].image, 1075, 100, BasePanel.tileSize*7, BasePanel.tileSize*12, null);

        for (SuperObject obj : getPlayer().inventory) {
            if (obj != null) {
                g2.drawImage(obj.image, obj.worldX, obj.worldY, BasePanel.tileSize, BasePanel.tileSize, null);
            }
        }
    }

    public void drawGroundCracks(Graphics2D g2, int centerX, int centerY, int radius) {
        Random random = new Random();

        int crackCount = 10;
        int maxLength = 100;
        int minLength = 30;

        ArrayList<Rectangle> crackAreas = new ArrayList<>();

        g2.setColor(new Color(100, 50, 0));

        for (int i = 0; i < crackCount; i++) {
            int startX = centerX + random.nextInt(2 * radius) - radius;
            int startY = centerY + random.nextInt(2 * radius) - radius;

            Rectangle potentialArea = new Rectangle(startX - 10, startY - 10, 20, 20);
            boolean overlaps = crackAreas.stream().anyMatch(area -> area.intersects(potentialArea));
            if (overlaps) {
                i--;
                continue;
            }
            crackAreas.add(potentialArea);

            int length = random.nextInt(maxLength - minLength) + minLength;
            double angle = random.nextDouble() * 2 * Math.PI; // Random angle

            int endX = startX + (int) (length * Math.cos(angle));
            int endY = startY + (int) (length * Math.sin(angle));

            g2.setStroke(new BasicStroke(2 + random.nextInt(2))); // Random thickness
            g2.drawLine(startX, startY, endX, endY);

            if (random.nextBoolean()) {
                int subLength = random.nextInt(maxLength / 3) + 10;
                double subAngle = angle + (random.nextDouble() * Math.PI / 4 - Math.PI / 8);

                int subEndX = endX + (int) (subLength * Math.cos(subAngle));
                int subEndY = endY + (int) (subLength * Math.sin(subAngle));

                g2.drawLine(endX, endY, subEndX, subEndY);
            }
        }
    }

    public void drawAoE(Graphics2D g2) {

        int aoeRadius = 100;
        int aoeDiameter = aoeRadius * 2; // Diameter of the AoE circle

        // Set transparency and color for the AoE circle
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // 30% transparency
        g2.setColor(new Color(255, 0, 0)); // Red color for the AoE

        // Draw the circle centered on the player
        int centerX = getPlayer().screenX - aoeRadius + 16; // Adjust for player's position
        int centerY = getPlayer().screenY - aoeRadius + 16;
        g2.fillOval(centerX, centerY, aoeDiameter, aoeDiameter);

        // Reset transparency to default
        g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));

    }

    // SUPER POWER METHODS
    public void triggerScreenShake(int magnitude, int duration) {
        this.shakeMagnitude = magnitude;
        this.shakeDuration = duration;
    }

    private void initSuperPowerPanel() {
        superPowers.add(new SuperPower("Ground Slam", "/res/objects/chain.png", 100));
        superPowers.add(new SuperPower("Fireball", "/res/projectiles/FireBall/right1.png", 300));
        superPowers.add(new SuperPower("Lightning", "/res/objects/LightningIcon.png", 300));
    }

    public void activateGroundSlam() {
        for (SuperPower power : superPowers) {
            if (power.getName().equals("Ground Slam") && power.getRemainingCooldown() <= 0) {
                power.startCooldown();
                playSE(4);
                getPlayer().triggerAoE();
                getPlayer().AoEDamage(this);
                triggerScreenShake(5, 10);
                groundSlamActive = true;
                break;
            }
        }
    }

    public void performGroundSlam(Graphics2D g2) {
        int centerX = getPlayer().screenX + 16;
        int centerY = getPlayer().screenY + 16;

        triggerScreenShake(5, 10);
        drawGroundCracks(g2, centerX, centerY, 100);

    }

    public void activateFireBall() {
        for (SuperPower power : superPowers) {
            if (power.getName().equals("Fireball") && power.getRemainingCooldown() <= 0) {
                power.startCooldown();

                int randomSound = new java.util.Random().nextInt(2) + 8;
                System.out.println(randomSound);
                playSE(randomSound);

                getPlayer().shootFireball();
                break;
            }
        }
    }

    public void activateLightning() {
        for (SuperPower power : superPowers) {
            if (power.getName().equals("Lightning") && power.getRemainingCooldown() <= 0) {
                dropLightning();
                power.startCooldown();
                break;
            }
        }
    }

    public void dropLightning() {
        if (monsters.isEmpty()) return;

        Random random = new Random();

        // Clear any previous targets
        lightningTargets.clear();

        // Pick 3 unique random monsters
        while (lightningTargets.size() < 3 && lightningTargets.size() < monsters.size()) {
            int randomIndex = random.nextInt(monsters.size());
            Entity targetMonster = monsters.get(randomIndex);

            if (targetMonster != null && targetMonster.alive && !lightningTargets.contains(targetMonster)) {
                lightningTargets.add(targetMonster);
            }
        }

        // Reset the counter to control the delay
        lightningCounter = 0;
    }

    public void updateLightningEffect() {
        if (!lightningTargets.isEmpty() && lightningCounter % 60 == 0) { // 60 frames = 1 second
            playSE(11);
            Entity targetMonster = lightningTargets.remove(0);

            PRTCL_Lightning newLightningParticle = new PRTCL_Lightning(lightningAnimation, targetMonster);
            newLightningParticle.worldX = targetMonster.worldX;
            newLightningParticle.worldY = targetMonster.worldY;

            activeParticles.add(newLightningParticle);

            triggerScreenShake(10, 5);
            targetMonster.life -= 2;
            targetMonster.hpBarOn = true;
            targetMonster.damageReceived = true;
        }

        if (!lightningTargets.isEmpty()) {
            lightningCounter++;
        }
    }

    // GETTERS
    public ArrayList<Entity> getBossMonsters() {
        return monsters;
    }

    public BossPanelData exportData() {
        return new BossPanelData(
                new ArrayList<>(monsters),
                isPaused,
                getPlayer().screenX,
                getPlayer().screenY,
                getPlayer().gold,
                getPlayer().level,
                getPlayer().xpCurrent,
                getPlayer().armorOnIronHead,
                getPlayer().armorOnIronTorso,
                getPlayer().armorOnIronLeg,
                getPlayer().armorOnLeatherHead,
                getPlayer().armorOnLeatherTorso,
                getPlayer().armorOnLeatherLeg,
                canSpawnBoss,
                sorcererProjectiles,
                lasers
        );
    }

    public void restoreData(BossPanelData data) {
        monsters.clear();
        monsters.addAll(data.monsters);
        isPaused = data.isPaused;
        Player player = getPlayer();
        player.screenX = data.playerX;
        player.screenY = data.playerY;
        player.gold = data.gold;
        player.level = data.level;
        player.xpCurrent = data.xp;
        player.armorOnIronHead = data.armorOnIronHead;
        player.armorOnIronTorso = data.armorOnIronTorso;
        player.armorOnIronLeg = data.armorOnIronLeg;
        player.armorOnLeatherHead = data.armorOnLeatherHead;
        player.armorOnLeatherTorso = data.armorOnLeatherTorso;
        player.armorOnLeatherLeg = data.armorOnLeatherLeg;
        canSpawnBoss = data.spawned;
        sorcererProjectiles = data.sorcererProjectiles;
        lasers = data.lasers;
        if (!monsters.isEmpty() && monsters.get(0) != null) {
            this.boss = (BOSS_Sorcerer) monsters.get(0);
        }
    }
}