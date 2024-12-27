package views;

import containers.HallContainer;
import containers.TileContainer;
import controllers.HallController;
import entity.Arrow;
import entity.Entity;
import enums.Hall;
import listeners.keylisteners.HallPanelKeyListener;
import listeners.mouselisteners.HallPanelMouseListener;
import managers.*;
import monster.MON_Archer;
import monster.MON_Fighter;
import monster.MON_Wizard;
import object.*;
import tile.Tile;
import utils.PanelUtils;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
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
    public SuperObject[][] gridWorld = new SuperObject[13][13];
    public int[][] gridWorldAll = new int[13][13];
    boolean availableSpot = false;
    private ImageIcon backgroundImage;
    soundManager soundManager = new soundManager();
    public boolean checkInventoryForReveal = false;
    public boolean checkInventoryForCloak = false;
    public boolean checkInventoryForLuringGem = false;
    public boolean drawReveal = false;
    public int revealX, revealY, revealCounter, change;

    // Set the new time

    private TimeManager timeManager;
    private JLabel timerLabel;
    private Timer timer;
    private int timeLeft;
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


        setLayout(new BorderLayout());

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

        // PLAY MUSIC
        playMusic(0);
    }

    public void startTimer() {
        // Stop any existing timer
        if (timer != null && timer.isRunning()) {
            timer.stop();
        }

        // Create and start the timer
        timer = new Timer(1000, e -> {
            if (timeLeft > 0) {
                timeLeft--;
//                System.out.println("Time Left: " + timeLeft + " seconds");
            } else {
                timer.stop();
                getViewManager().switchTo("TitlePanel", true);
//                System.out.println("Time's up!");
            }
        });
        timer.start();
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

            // Generate Monster
            spawnCounter++;

            if (spawnCounter >= 60 * 2) {
                generateMonster();
                spawnCounter = 0;
            }

            // Generate Enchantment
            spawnEnchantmentCounter++;

            if (spawnEnchantmentCounter >= 60 * 2) {
                OBJ_Cactus cactus = new OBJ_Cactus();
                cactus.collision = true;
                tileM.generateEnchantment();
                spawnEnchantmentCounter = 0;
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

            int row = monster.worldX / BasePanel.tileSize;
            int column = monster.worldY / BasePanel.tileSize;

            if (row > 13) {row = 13;}
            if (column > 13) {column = 13;}

            gridWorldAll[row - 7][column - 2] = 1;

        }

    }

    public void generateMonster(){

        Random random = new Random();
        String pickMonster = monsterTypes[random.nextInt(monsterTypes.length)]; // Get a random index

        int locationX = random.nextInt(1,13) + 7;
        int locationY = random.nextInt(1,14) + 2;

        setGridWorld();

        while (gridWorldAll[locationX - 8][locationY - 3] != 0)  {
            locationX = random.nextInt(1,13) + 7;
            locationY = random.nextInt(1,14) + 2;
        }

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

    public void nullTimer() {
        timer.stop();
        timer = null;
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

                if (timer == null) {
                    timeLeft = this.getSuperObjectLength() * 5;
                    System.out.println(timeLeft);
                    startTimer();
                }

                // Repaint game
                g2.setFont(arial_40);

                // Set background image
                backgroundImage = new ImageIcon(getClass().getResource("/res/tiles/forest.png"));
                Image scaledImage = backgroundImage.getImage().getScaledInstance(this.getWidth(), this.getHeight(), Image.SCALE_SMOOTH);
                ImageIcon scaledIcon = new ImageIcon(scaledImage);

                g2.drawImage(scaledIcon.getImage(), 0, 0, null);

                // Draw tiles
                HallContainer.getHallOfEarth().draw(g2);

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

                if (timer == null) {
                    timeLeft = this.getSuperObjectLength() * 5;
                    System.out.println(timeLeft);
                    startTimer();
                }

                // Repaint game
                g2.setFont(arial_40);

                // Set background image
                backgroundImage = new ImageIcon(getClass().getResource("/res/tiles/glacial_mountains.png"));
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

                if (timer == null) {
                    timeLeft = this.getSuperObjectLength() * 5;
                    System.out.println(timeLeft);
                    startTimer();
                }

                // Repaint game
                g2.setFont(arial_40);

                // Set background image
                backgroundImage = new ImageIcon(getClass().getResource("/res/tiles/water.png"));
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

                if (timer == null) {
                    timeLeft = this.getSuperObjectLength() * 5;
                    System.out.println(timeLeft);
                    startTimer();
                }

                // Repaint game
                g2.setFont(arial_40);

                // Set background image
                backgroundImage = new ImageIcon(getClass().getResource("/res/tiles/fire.png"));
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

        drawInventory(g2);


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

    public void drawInventory(Graphics2D g2) {
        g2.drawImage(TileContainer.getTile()[19].image, 1075, 100, BasePanel.tileSize*7, BasePanel.tileSize*12, null);

        for (SuperObject obj : getPlayer().inventory) {
            if (obj != null) {
                g2.drawImage(obj.image, obj.worldX, obj.worldY, BasePanel.tileSize, BasePanel.tileSize, null);
            }
        }
    }

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


    public ArrayList<Entity> getHallMonsters() {
        return monsters;
    }

    public void playMusic(int i) {

        soundManager.setFile(i);
        soundManager.play();
        soundManager.loop();

    }

    public void stopMusic() {
        soundManager.stop();
    }

    public void playSE(int i) {

        soundManager.setFile(i);
        soundManager.play();

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


}

