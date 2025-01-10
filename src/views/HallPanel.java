package views;

import containers.HallContainer;
import containers.TileContainer;
import controllers.HallController;
import data.BuildPanelData;
import data.HallPanelData;
import entity.Arrow;
import entity.Entity;
import entity.Fireball;
import entity.Player;
import enums.Hall;
import listeners.keylisteners.HallPanelKeyListener;
import listeners.mouselisteners.HallPanelMouseListener;
import managers.*;
import monster.BOSS_Sorcerer;
import monster.MON_Archer;
import monster.MON_Fighter;
import monster.MON_Wizard;
import object.*;
import superpower.SuperPower;
import utils.PanelUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;
import java.util.List;

public class HallPanel extends PlayablePanel{

    public Hall currentHall = Hall.HallOfEarth;
    private final HallPanelKeyListener keyListener;
    private final HallPanelMouseListener mouseListener;
    private final ArrayList<Entity> monsters = new ArrayList<>();
    public TileManagerForHall tileM;
    final CollisionCheckerForHall cChecker;
    private boolean isPaused;
    public boolean wizardChecker = false;
    transient BufferedImage heart_full, heart_half, heart_blank;
    SuperObject heart = new OBJ_Heart();
    transient BufferedImage armor_full, armor_half, armor_blank;
    SuperObject armor = new OBJ_Armor();
    public SuperObject[][] gridWorld = new SuperObject[13][14];
    public int[][] gridWorldAll;
    boolean availableSpot = false;
    private ImageIcon backgroundImage;
    private final ImageIcon backgroundEarth = new ImageIcon(getClass().getResource("/res/tiles/forest.png"));
    private final ImageIcon backgroundWater = new ImageIcon(getClass().getResource("/res/tiles/water.png"));
    private final ImageIcon backgroundAir = new ImageIcon(getClass().getResource("/res/tiles/glacial_mountains.png"));
    private final ImageIcon backgroundFire = new ImageIcon(getClass().getResource("/res/tiles/fire.png"));
    public soundManager soundManager;
    public boolean attackSoundPlayed;
    public boolean checkInventoryForReveal = false;
    public boolean checkInventoryForCloak = false;
    public boolean checkInventoryForLuringGem = false;
    public boolean drawReveal = false;
    public int revealX, revealY, revealCounter, change;
    private int shakeMagnitude = 0;
    private int shakeDuration = 0;
    private Random shakeRandom = new Random();
    public Font maruMonica;

    public final int secondPerObject = 100;


    // SUPERPOWERS
    private JPanel superPowerPanel; // Panel for superpowers
    public ArrayList<SuperPower> superPowers = new ArrayList<>(); // List of superpowers

    public boolean groundSlamActive = false;

    private int lightningCounter = 0;
    private List<Entity> lightningTargets = new ArrayList<>();

    // Particles
    transient ArrayList<BufferedImage> bloodAnimation = new ArrayList<>();
    public PRTCL_Blood bloodParticle;
    private final List<SuperParticle> activeBloodParticles = new ArrayList<>();

    transient ArrayList<BufferedImage> burningAnimation = new ArrayList<>();
    public PRTCL_Burning burningParticle;
    private final List<SuperParticle> activeBurningParticles = new ArrayList<>();

    transient ArrayList<BufferedImage> lightningAnimation = new ArrayList<>();
    public PRTCL_Lightning lightningParticle;
    private final List<SuperParticle> activeLightningParticles = new ArrayList<>();

    private final List<SuperParticle> activeParticles = new ArrayList<>();

    public boolean isShaking = false;
    public int shakeCounter = 0;
    public int shakeOffset = 0;
    public int shakingIndex = -1;

    // EASTER EGG
    public boolean easter1, easter2, easter3, easter4;

    // Set the new time

    private TimeManager timeManager;
    private JLabel timerLabel;
    private Timer timer;
    public int timeLeft;
    private int timeLeftSave;
    private boolean createTimer = false;

    public int spawnCounter;

    public int spawnEnchantmentCounter;

    String[] monsterTypes = new String[3];

    public HallPanel(ViewManager viewManager) {
        super(viewManager);
        this.tileM = HallContainer.getHallOfEarth();
        this.keyListener = new HallPanelKeyListener(this);
        this.addKeyListener(keyListener);
        getPlayer().addKeyListener(keyListener);
        this.soundManager = managers.soundManager.getInstance();

        Player player = Player.getInstance(this);
        player.addKeyListener(keyListener);

        PanelUtils panelUtil = new PanelUtils();

        this.maruMonica = panelUtil.getNewFont();

        // PARTICLES
        bloodParticle = new PRTCL_Blood(bloodAnimation, 400, 400);

        burningParticle = new PRTCL_Burning(burningAnimation, null);

        lightningParticle = new PRTCL_Lightning(lightningAnimation, null);

        // SUPERPOWER
        initSuperPowerPanel();

        // RESETTING
        HallContainer.getHallOfEarth().objects.clear();
        HallContainer.getHallOfAir().objects.clear();
        HallContainer.getHallOfWater().objects.clear();
        HallContainer.getHallOfFire().objects.clear();

        HallContainer.getHallOfEarth().gridWorld = new SuperObject[13][14];
        HallContainer.getHallOfAir().gridWorld = new SuperObject[13][14];
        HallContainer.getHallOfWater().gridWorld = new SuperObject[13][14];
        HallContainer.getHallOfFire().gridWorld = new SuperObject[13][14];

        HallContainer.getHallOfEarth().closeDoor();
        HallContainer.getHallOfAir().closeDoor();
        HallContainer.getHallOfWater().closeDoor();
        HallContainer.getHallOfFire().closeDoor();

        this.tileM.gridWorld = new SuperObject[13][14];
        this.gridWorldAll = new int[13][14];
        this.tileM.closeDoor();
        this.tileM.enchantments.clear();

        setLayout(new BorderLayout());

        heart_full = heart.image;
        heart_half = heart.image2;
        heart_blank = heart.image3;

        armor_full = armor.image;
        armor_half = armor.image2;
        armor_blank = armor.image3;

        monsterTypes[0] = "Archer";
        monsterTypes[1] = "Wizard";
        monsterTypes[2] = "Fighter";

        this.mouseListener = new HallPanelMouseListener(this, getTileM());
        this.addMouseListener(mouseListener);
        this.cChecker = new CollisionCheckerForHall(this);

        getPlayer().panel = this;
    }

    public HallPanelData exportData() {
        return new HallPanelData(
                currentHall,
                monsters,
                gridWorld,
                gridWorldAll,
                timeLeft,
                isPaused,
                checkInventoryForReveal,
                checkInventoryForCloak,
                checkInventoryForLuringGem,
                HallContainer.getHallOfEarth().objects,
                HallContainer.getHallOfAir().objects,
                HallContainer.getHallOfWater().objects,
                HallContainer.getHallOfFire().objects,
                this.getPlayer().screenX,
                this.getPlayer().screenY,
                this.wizardChecker,
                HallContainer.getHallOfEarth().enchantments,
                HallContainer.getHallOfAir().enchantments,
                HallContainer.getHallOfWater().enchantments,
                HallContainer.getHallOfFire().enchantments,
                this.getPlayer().gold,
                this.getPlayer().level,
                this.getPlayer().xpCurrent,
                this.getPlayer().armorOnIronHead,
                this.getPlayer().armorOnIronTorso,
                this.getPlayer().armorOnLeatherHead,
                this.getPlayer().armorOnLeatherTorso,
                HallContainer.getHallOfEarth().isOpened,
                HallContainer.getHallOfAir().isOpened,
                HallContainer.getHallOfWater().isOpened,
                HallContainer.getHallOfFire().isOpened
                );
    }

    public void restoreData(HallPanelData data, BuildPanelData buildData, String currentMode) {
        this.currentHall = data.currentHall;

        this.monsters.clear();
        this.monsters.addAll(data.monsters);

        this.getPlayer().gold = data.gold;
        this.getPlayer().level = data.level;
        this.getPlayer().xpCurrent = data.xp;

        this.getPlayer().armorOnIronHead = data.armorOnIronHead;
        this.getPlayer().armorOnIronTorso = data.armorOnIronTorso;
        this.getPlayer().armorOnLeatherHead = data.armorOnLeatherHead;
        this.getPlayer().armorOnLeatherTorso = data.armorOnLeatherTorso;


        for (Entity monster : getMonsters()) {
            if (monster != null) {
                monster.panel = this;
            }
        }

        for (Entity arrow : getArrows()) {
            if (arrow != null) {
                arrow.panel = this;
            }
        }

        HallContainer.getHallOfEarth().gridWorld = new SuperObject[13][14];
        HallContainer.getHallOfAir().gridWorld = new SuperObject[13][14];
        HallContainer.getHallOfWater().gridWorld = new SuperObject[13][14];
        HallContainer.getHallOfFire().gridWorld = new SuperObject[13][14];

        this.gridWorld = data.gridWorld;
        this.gridWorldAll = data.gridWorldAll;

        HallContainer.getHallOfEarth().enchantments = data.enchantmentsEarth;
        HallContainer.getHallOfAir().enchantments = data.enchantmentsAir;
        HallContainer.getHallOfWater().enchantments = data.enchantmentsWater;
        HallContainer.getHallOfFire().enchantments = data.enchantmentsFire;

        this.timeLeft = data.timeLeftSave;
        TimeManager.getInstance().startTimer(this.timeLeft);
        this.isPaused = data.isPaused;

        this.checkInventoryForReveal = data.checkInventoryForReveal;
        this.checkInventoryForCloak = data.checkInventoryForCloak;
        this.checkInventoryForLuringGem = data.checkInventoryForLuringGem;
        this.wizardChecker = data.wizardChecker;

        getPlayer().screenX = data.playerX;
        getPlayer().screenY = data.playerY;

        this.tileM.objects.clear();
        HallContainer.getHallOfEarth().objects.clear();
        HallContainer.getHallOfAir().objects.clear();
        HallContainer.getHallOfWater().objects.clear();
        HallContainer.getHallOfFire().objects.clear();

        if (currentMode.equals("Build")) {
            this.tileM = HallContainer.getHallOfEarth();
//            System.out.println("PRINTING EARTH OBJECTS:" + buildData.objectsEarth.getFirst());
            this.tileM.objects = buildData.objectsEarth;

            this.tileM = HallContainer.getHallOfAir();
            this.tileM.objects = buildData.objectsAir;

            this.tileM = HallContainer.getHallOfWater();
            this.tileM.objects = buildData.objectsWater;

            this.tileM = HallContainer.getHallOfFire();
            this.tileM.objects = buildData.objectsFire;

        } else {
            this.tileM = HallContainer.getHallOfEarth();
            this.tileM.objects = data.objectsEarth;
            if (data.earthOpened) {
                this.tileM.openDoor();
            }

            this.tileM = HallContainer.getHallOfAir();
            this.tileM.objects = data.objectsAir;
            if (data.airOpened) {
                this.tileM.openDoor();
            }

            this.tileM = HallContainer.getHallOfWater();
            this.tileM.objects = data.objectsWater;
            if (data.waterOpened) {
                this.tileM.openDoor();
            }

            this.tileM = HallContainer.getHallOfFire();
            this.tileM.objects = data.objectsFire;
            if (data.fireOpened) {
                this.tileM.openDoor();
            }
        }

        switch (currentHall) {
            case HallOfEarth -> {
                this.tileM = HallContainer.getHallOfEarth();
            }
            case HallOfAir -> {
                this.tileM = HallContainer.getHallOfAir();
            }
            case HallOfWater -> {
                this.tileM = HallContainer.getHallOfWater();
            }
            case HallOfFire -> {
                this.tileM = HallContainer.getHallOfFire();
            }
            default -> throw new IllegalArgumentException("Unexpected hall type: " + currentHall);
        }
        }

    public Entity[] getMonsters() {
        return this.monsters.toArray(new Entity[0]);
    }

    @Override
    public void addNotify() {
        super.addNotify();
        HallController.movePlayerIfCollision(HallContainer.getCurrentHallManager(currentHall), getPlayer());
    }

    @Override
    public void update() {
        if (!isPaused()) {
        	
            if (TimeManager.getInstance().timer == null && !TimeManager.getInstance().timeStopped) {

            	timeLeft = this.getSuperObjectLength() * secondPerObject;;
                
                TimeManager.getInstance().startTimer(timeLeft);                    
            }
            
            if(TimeManager.getInstance().timeStopped) {
            	TimeManager.getInstance().startTimer(timeLeft);
            }
            
            if (getPlayer().life <= 0 || timeLeft <= 0) {
            	 System.out.println("SWITCH5");
                getViewManager().switchTo("HomePanel", true);
            }
        	
        	timeLeft = TimeManager.getInstance().timeLeft;
        	
            getPlayer().move();

            updateLightningEffect();

            for (SuperPower power : superPowers) {
                power.tickCooldown();
            }

            for (Entity monster : new ArrayList<>(monsters)) {
                if (monster != null) {
                    monster.update();
                }
            }

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

            if (getPlayer().fireball != null) {
                getPlayer().fireball.update();
                if (getPlayer().fireball.isExpired()) {
                    getPlayer().fireball = null;
                }
            }

            HallController.shouldSwitchHallsInGame(getTileM(), getPlayer(), this);

            // Generate Monster
            spawnCounter++;

            if (spawnCounter >= 60 * 5) {
                generateMonster();
                spawnCounter = 0;
            }

            // Generate Enchantment
            spawnEnchantmentCounter++;

            if (spawnEnchantmentCounter >= 60 * 3) {
                tileM.generateEnchantment();
                tileM.generateGold();
                spawnEnchantmentCounter = 0;
            }
        }
        
        else {
            if(!TimeManager.getInstance().timeStopped) {
            	TimeManager.getInstance().stopTimer();
            }
        }
    }

    public TileManagerForHall getTileM(){ return this.tileM;}

    public void setGridWorld() {

        for (int i = 0; i < gridWorld.length; i++) {
            for (int j = 0; j < gridWorld[i].length; j++) {
                gridWorldAll[i][j] = (gridWorld[i][j] != null) ? 1 : 0;
            }
        }

        for (Entity monster : monsters) {

            if (monster != null) {

                int row = monster.worldX / BasePanel.tileSize;
                int column = monster.worldY / BasePanel.tileSize;

                if (row > 13) {
                    row = 13;
                }
                if (column > 13) {
                    column = 13;
                }

                gridWorldAll[row - 7][column - 2] = 1;
            }
        }

    }

    public void generateMonster(){

        Random random = new Random();
        String pickMonster = monsterTypes[random.nextInt(monsterTypes.length)]; // Get a random index
//        pickMonster = "Sorcerer";

        int locationX = random.nextInt(1,13) + 7;
        int locationY = random.nextInt(1,14) + 2;

        int gX = (BasePanel.tileSize*locationX - 336)/48;
        int gY = (BasePanel.tileSize*locationY - 96) / 48;

        setGridWorld();

        while (gridWorldAll[locationX - 8][locationY - 3] != 0 || getTileM().gridWorld[gX][gY] != null)  {
            locationX = random.nextInt(1,13) + 7;
            locationY = random.nextInt(1,14) + 2;
            gX = (BasePanel.tileSize*locationX - 336)/48;
            gY = (BasePanel.tileSize*locationY - 96) / 48;
        }


        switch (pickMonster) {
            case "Sorcerer":
                BOSS_Sorcerer boss = new BOSS_Sorcerer(this);
                boss.worldX = BasePanel.tileSize*locationX;
                boss.worldY = BasePanel.tileSize*locationY;

                boss.spawned = true;

                for (int i = 0; i < getMonsters().length; i++) {

                    if (getMonsters()[i] == null) {
                        getMonsters()[i] = boss;
                        break;
                    }
                }

                monsters.add(boss);

                break;

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

    public CollisionCheckerForHall getCollisionCheckerForHall(){return this.cChecker;}

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void playMusic(int i) {

        soundManager.setFile(i);
        soundManager.play();
        soundManager.loop();

    }

    public void paintComponent(Graphics g) {

        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

        g2.setColor(Color.WHITE);

        long drawStart = 0;
        drawStart = System.nanoTime();
   
        update();

        switch (currentHall) {
            case HallOfEarth -> {
                // Repaint game
                g2.setFont(arial_40);

                // Set background image
                backgroundImage = backgroundEarth;
                Image scaledImage = backgroundImage.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                g2.drawImage(scaledIcon.getImage(), 0, 0, null);

                // Draw tiles
                HallContainer.getHallOfEarth().draw(g2);

                // Draw superpowers
                drawSuperPowers(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfEarth().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }

                // Draw Enchantments
                for (SuperObject superObject : HallContainer.getHallOfEarth().enchantments) {
                    if (superObject != null) {
                        g2.drawImage(superObject.image, superObject.worldX, superObject.worldY, tileSize, tileSize, null);
                    }
                }


                // Draw the Timer
                g2.setFont(new Font("Arial", Font.BOLD, 30)); // Set font size and style
                g2.setColor(Color.BLACK);                     // Set text color
                String timerText = "Time Left: " + timeLeft + "s";
                g2.drawString(timerText, this.getWidth()-250, 40);

            }
            case HallOfAir -> {

  

                // Repaint game
                g2.setFont(arial_40);

                // Set background image
                backgroundImage = backgroundAir;
                Image scaledImage = backgroundImage.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                g2.drawImage(scaledIcon.getImage(), 0, 0, null);

                // Draw tiles
                HallContainer.getHallOfAir().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfAir().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }

                // Draw Enchantments
                for (SuperObject superObject : HallContainer.getHallOfAir().enchantments) {
                    if (superObject != null) {
                        g2.drawImage(superObject.image, superObject.worldX, superObject.worldY, tileSize, tileSize, null);
                    }
                }

                // Draw the Timer
                g2.setFont(new Font("Arial", Font.BOLD, 30)); // Set font size and style
                g2.setColor(Color.BLACK);                     // Set text color
                String timerText = "Time Left: " + timeLeft + "s";
                g2.drawString(timerText, this.getWidth()-250, 40);

            }
            case HallOfWater -> {


                // Repaint game
                g2.setFont(arial_40);

                // Set background image
                backgroundImage = backgroundWater;
                Image scaledImage = backgroundImage.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                g2.drawImage(scaledIcon.getImage(), 0, 0, null);

                // Draw tiles
                HallContainer.getHallOfWater().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfWater().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }

                // Draw Enchantments
                for (SuperObject superObject : HallContainer.getHallOfWater().enchantments) {
                    if (superObject != null) {
                        g2.drawImage(superObject.image, superObject.worldX, superObject.worldY, tileSize, tileSize, null);
                    }
                }

                // Draw the Timer
                g2.setFont(new Font("Arial", Font.BOLD, 30)); // Set font size and style
                g2.setColor(Color.BLACK);                     // Set text color
                String timerText = "Time Left: " + timeLeft + "s";
                g2.drawString(timerText, this.getWidth()-250, 40);
            }
            case HallOfFire -> {

                // Repaint game
                g2.setFont(arial_40);

                // Set background image
                backgroundImage = backgroundFire;
                Image scaledImage = backgroundImage.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                g2.drawImage(scaledIcon.getImage(), 0, 0, null);

                // Draw tiles
                HallContainer.getHallOfFire().draw(g2);

                // Draw super objects
                for (SuperObject superObject : HallContainer.getHallOfFire().objects) {
                    if (superObject != null) {
                        superObject.draw(g2, this);
                    }
                }

                // Draw Enchantments
                for (SuperObject superObject : HallContainer.getHallOfFire().enchantments) {
                    if (superObject != null) {
                        g2.drawImage(superObject.image, superObject.worldX, superObject.worldY, tileSize, tileSize, null);
                    }
                }

                // Draw the Timer
                g2.setFont(new Font("Arial", Font.BOLD, 30)); // Set font size and style
                g2.setColor(Color.BLACK);                     // Set text color
                String timerText = "Time Left: " + timeLeft + "s";
                g2.drawString(timerText, this.getWidth()-250, 40);
            }
        }

        // Using Reveal Enchantment
        if (checkInventoryForReveal) {
            System.out.println("Reveal");
            reveal(g2);
            checkInventoryForReveal = false;
        }

        if (!drawReveal) {
            Random random = new Random();
            change = random.nextInt(4);
        }

        // TODO: FIX: DOESN'T DRAW RECTANGLE WHEN ANOTHER REVEAL IS USED
        if (drawReveal && revealCounter < 60 * 4) {
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f)); // 50% transparency
            g2.setColor(Color.YELLOW);
            g2.fillRect(revealX - (tileSize * change), revealY - (tileSize * change), 48 * 4, 48 * 4);
            g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1.0f));
            revealCounter++;

        } else if (revealCounter > 60 * 4) {
            revealCounter = 0;
            drawReveal = false;
        } else {
            revealCounter = 0;
            drawReveal = false;
        }

        if (shakeDuration > 0) {
            int offsetX = shakeRandom.nextInt(shakeMagnitude * 2 + 1) - shakeMagnitude;
            int offsetY = shakeRandom.nextInt(shakeMagnitude * 2 + 1) - shakeMagnitude;
            g2.translate(offsetX, offsetY); // Apply offset to graphics
            shakeDuration--; // Decrease shake duration
        }

        if (groundSlamActive) {
            performGroundSlam(g2);
        }

        g2.translate(0, 0);

        getPlayer().draw(g2);

        Iterator<Entity> iteratorMonster = monsters.iterator();
        while (iteratorMonster.hasNext()) {
            Entity monster = iteratorMonster.next();
            if (monster != null) {
                monster.draw(g2);

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

        if (getPlayer().fireball != null) {
            getPlayer().fireball.draw(g2);
        }

        drawInventory(g2);

        drawCharacterInfo(g2);

        if (isPaused()) {
            drawPauseScreen(g2);
        }

        // DEBUGGING
//      long drawEnd = System.nanoTime();
//      long passed = drawEnd - drawStart;
//      g2.setColor(Color.white);
//      g2.drawString("Draw Time: " + passed, 10, 400);
//      System.out.println("Draw Time: " + passed);

        g2.dispose();
    }

    // GETTERS
    public ArrayList<Entity> getHallMonsters() {
        return monsters;
    }

    public int getSuperObjectLength() {
        int num = 0;

        switch (currentHall) {
            case HallOfEarth -> {
                for (int i = 0; i <= HallContainer.getHallOfEarth().objects.size() - 1; i++) {
                    if (HallContainer.getHallOfEarth().objects.get(i) != null) {
                        num++;
                    }
                }
            }
            case HallOfFire -> {
                for (int i = 0; i <= HallContainer.getHallOfFire().objects.size() - 1; i++) {
                    if (HallContainer.getHallOfFire().objects.get(i) != null) {
                        num++;
                    }
                }
            }
            case HallOfAir -> {
                for (int i = 0; i <= HallContainer.getHallOfAir().objects.size() - 1; i++) {
                    if (HallContainer.getHallOfAir().objects.get(i) != null) {
                        num++;
                    }
                }
            }
            case HallOfWater -> {
                for (int i = 0; i <= HallContainer.getHallOfWater().objects.size() - 1; i++) {
                    if (HallContainer.getHallOfWater().objects.get(i) != null) {
                        num++;
                    }
                }
            }
            default -> throw new IllegalArgumentException("Invalid hall type: " + currentHall);
        }
        return num;
    }

    // DRAW METHODS

    public void drawSuperPowers(Graphics2D g2) {
        int currentLevel = getPlayer().level;
        int x = 20;
        int y = 357;
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

    private void drawPauseScreen(Graphics2D g2) {
        int x, y;
        String text = "PAUSED";
        x = PanelUtils.getXForCenteredText(text, this, g2);
        y = BasePanel.screenHeight / 2;
        g2.setColor(Color.WHITE);
        g2.drawString(text, x - 100, y);
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

    private void drawPlayerLife(Graphics2D g2) {

        int x = tileSize/4;
        int y = tileSize;
        int i = 0;

        while(i < getPlayer().maxLife/2) {
            g2.drawImage(heart_blank, x, y, null);
            i++;
            x += tileSize/2 + 5;
        }

        x = tileSize/4;
        y = tileSize;
        i = 0;

        while(i < getPlayer().life) {
            g2.drawImage(heart_half, x, y, null);
            i++;
            if(i < getPlayer().life) {
                g2.drawImage(heart_full, x, y, null);
            }
            i++;
            x += tileSize/2 + 5;
        }

    }

    private void drawPlayerArmor(Graphics2D g2) {

        int x = tileSize/4;
        int y = 87;
        int i = 0;

        while(i < getPlayer().maxArmor/2) {
            g2.drawImage(armor_blank, x, y, null);
            i++;
            x += tileSize/2 + 5;
        }

        x = tileSize/4;
        y = 87;
        i = 0;

        while(i < getPlayer().armor) {
            g2.drawImage(armor_half, x, y, null);
            i++;
            if(i < getPlayer().armor) {
                g2.drawImage(armor_full, x, y, null);
            }
            i++;
            x += tileSize/2 + 5;
        }

    }

    public void drawCharacterInfo(Graphics2D g2) {
        g2.setColor(new Color(30, 30, 30, 200));
        g2.fillRoundRect(10, 10, 300, 332, 15, 15);

        g2.setColor(Color.BLACK);
        g2.setStroke(new BasicStroke(5));
        g2.drawRoundRect(10, 10, 300, 332, 15, 15);

        g2.setColor(Color.DARK_GRAY);
        g2.fillRect(20, 20, 260, 20);

        g2.setColor(Color.YELLOW);
        int xpPercentage = (int) (getPlayer().xpCurrent / (float) getPlayer().xpMax * 260);
        g2.fillRect(20, 20, xpPercentage, 20);

        g2.setColor(Color.WHITE);
        g2.setStroke(new BasicStroke(2));
        g2.drawRect(20, 20, 260, 20);

        drawPlayerLife(g2);
        drawPlayerArmor(g2);

        g2.setColor(Color.WHITE);
        g2.setFont(maruMonica);
        g2.setFont(g2.getFont().deriveFont(Font.PLAIN, 32F));

        g2.drawString("Level: " + getPlayer().level, 20, 152);
        g2.drawString("Gold: " + getPlayer().gold, 20, 192);
        g2.drawString("Armor: " + getPlayer().armor, 20, 232);
        g2.drawString("Life: " + getPlayer().life + "/" + getPlayer().maxLife, 20, 272);
        g2.drawString("Xp: " + getPlayer().xpCurrent + "/" + getPlayer().xpMax, 20, 312);
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

    // ENCHANTMENT METHODS

    public void reveal(Graphics2D g2) {
        switch (currentHall) {
            case HallOfEarth -> {
                for (SuperObject obj : HallContainer.getHallOfEarth().objects) {
                    if (obj != null && obj.hasRune) {
                        drawReveal = true;
                        revealX = obj.worldX;
                        revealY = obj.worldY;
                        break;
                    }
                }
            }
            case HallOfAir -> {
                for (SuperObject obj : HallContainer.getHallOfAir().objects) {
                    if (obj != null && obj.hasRune) {
                        drawReveal = true;
                        revealX = obj.worldX;
                        revealY = obj.worldY;
                        break;
                    }
                }
            }
            case HallOfWater -> {
                for (SuperObject obj : HallContainer.getHallOfWater().objects) {
                    if (obj != null && obj.hasRune) {
                        drawReveal = true;
                        revealX = obj.worldX;
                        revealY = obj.worldY;
                        break;
                    }
                }
            }
            case HallOfFire -> {
                for (SuperObject obj : HallContainer.getHallOfFire().objects) {
                    if (obj != null && obj.hasRune) {
                        drawReveal = true;
                        revealX = obj.worldX;
                        revealY = obj.worldY;
                        break;
                    }
                }
            }
            default -> throw new IllegalArgumentException("Unexpected hall type: " + currentHall);
        }
    }

    public void checkInventoryForReveal() {

        boolean hasEnchantment = false;

        for (SuperObject enhancement : getPlayer().inventory) {
            if (enhancement instanceof ENCH_Reveal) {
                hasEnchantment = true;
                getPlayer().inventory.remove(enhancement);
                break;
            }
        }
        System.out.println("Checked " + hasEnchantment);
        this.checkInventoryForReveal = hasEnchantment;
    }

    public void checkInventoryForCloak() {

        boolean hasEnchantment = false;

        for (SuperObject enhancement : getPlayer().inventory) {
            if (enhancement instanceof ENCH_Cloak) {
                hasEnchantment = true;
                getPlayer().inventory.remove(enhancement);
                getPlayer().invincibleCloak = true;
                break;
            }
        }
        this.checkInventoryForCloak = hasEnchantment;
    }

    public boolean checkInventoryForLuringGem() {

        boolean hasEnchantment = false;

        for (SuperObject enhancement : getPlayer().inventory) {
            if (enhancement instanceof ENCH_LuringGem) {
                hasEnchantment = true;

                getPlayer().inventory.remove(enhancement);

                break;
            }
        }
        this.checkInventoryForLuringGem = hasEnchantment;
        return hasEnchantment;
    }

    public void throwGem(String direction) {
        OBJ_LuringGem lg = new OBJ_LuringGem();
        lg.collision = true;

        lg.worldX = getPlayer().screenX;
        lg.worldY = getPlayer().screenY;

        switch (direction.toLowerCase()) {
            case "up" -> lg.worldY -= 96;
            case "down" -> lg.worldY += 96;
            case "left" -> lg.worldX -= 96;
            case "right" -> lg.worldX += 96;
            default -> {
                System.out.println("Invalid direction! Use 'up', 'down', 'left', or 'right'.");
                return;
            }
        }

        switch (currentHall) {
            case HallOfEarth -> HallContainer.getHallOfEarth().objects.add(lg);
            case HallOfAir -> HallContainer.getHallOfAir().objects.add(lg);
            case HallOfWater -> HallContainer.getHallOfWater().objects.add(lg);
            case HallOfFire -> HallContainer.getHallOfFire().objects.add(lg);
            default -> throw new IllegalArgumentException("Invalid hall: " + currentHall);
        }
    }

    // SUPER POWER METHODS

    public void triggerScreenShake(int magnitude, int duration) {
        this.shakeMagnitude = magnitude;
        this.shakeDuration = duration;
    }

    private void initSuperPowerPanel() {
        superPowers.add(new SuperPower("Ground Slam", "/res/objects/chain.png", 100));
        superPowers.add(new SuperPower("Fireball", "/res/projectiles/FireBall/right1.png", 60));
        superPowers.add(new SuperPower("Lightning", "/res/objects/LightningIcon.png", 180));
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

    // SOUND EFFECT

    public void playSE(int i) {
        soundManager.setFile(i);
        soundManager.play();
    }

    public void stopSE(int i) {
        soundManager.setFile(i);
        soundManager.stop();
    }
}

