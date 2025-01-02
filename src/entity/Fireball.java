package entity;

import views.BasePanel;
import views.HallPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Fireball extends Entity {
    private int damage;
    private final int speed;
    private final int range;
    private int travelDistance;
    private boolean expired = false;
    public int knockbackValue = 5;

    public int fireballCounter;

    // Paths for 'up' direction
    private final String up1Path = "/res/projectiles/FireBall/up1";
    private final String up2Path = "/res/projectiles/FireBall/up2";
    private final String up3Path = "/res/projectiles/FireBall/up3";
    private final String up4Path = "/res/projectiles/FireBall/up4";
    private final String up5Path = "/res/projectiles/FireBall/up5";

    // Paths for 'down' direction
    private final String down1Path = "/res/projectiles/FireBall/down1";
    private final String down2Path = "/res/projectiles/FireBall/down2";
    private final String down3Path = "/res/projectiles/FireBall/down3";
    private final String down4Path = "/res/projectiles/FireBall/down4";
    private final String down5Path = "/res/projectiles/FireBall/down5";

    // Paths for 'left' direction
    private final String left1Path = "/res/projectiles/FireBall/left1";
    private final String left2Path = "/res/projectiles/FireBall/left2";
    private final String left3Path = "/res/projectiles/FireBall/left3";
    private final String left4Path = "/res/projectiles/FireBall/left4";
    private final String left5Path = "/res/projectiles/FireBall/left5";

    // Paths for 'right' direction
    private final String right1Path = "/res/projectiles/FireBall/right1";
    private final String right2Path = "/res/projectiles/FireBall/right2";
    private final String right3Path = "/res/projectiles/FireBall/right3";
    private final String right4Path = "/res/projectiles/FireBall/right4";
    private final String right5Path = "/res/projectiles/FireBall/right5";


    public Fireball(BasePanel gp, int worldX, int worldY, String direction) {
        super(gp);

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.panel = gp;
        this.knockbackValue = 5;

        // Arrow characteristics
        damage = 1;
        speed = 8;
        range = 4 * gp.tileSize; // 4 squares range
        travelDistance = 0;
        knockbackValue = 5;

        // Set up arrow images
        getArrowImage();
    }

    public boolean isExpired() {
        return expired;
    }

    private void getArrowImage() {
        // Load 'up' images
        up1 = setup(up1Path, BasePanel.tileSize, BasePanel.tileSize);
        up2 = setup(up2Path, BasePanel.tileSize, BasePanel.tileSize);
        up3 = setup(up3Path, BasePanel.tileSize, BasePanel.tileSize);
        up4 = setup(up4Path, BasePanel.tileSize, BasePanel.tileSize);
        up5 = setup(up5Path, BasePanel.tileSize, BasePanel.tileSize);

        // Load 'down' images
        down1 = setup(down1Path, BasePanel.tileSize, BasePanel.tileSize);
        down2 = setup(down2Path, BasePanel.tileSize, BasePanel.tileSize);
        down3 = setup(down3Path, BasePanel.tileSize, BasePanel.tileSize);
        down4 = setup(down4Path, BasePanel.tileSize, BasePanel.tileSize);
        down5 = setup(down5Path, BasePanel.tileSize, BasePanel.tileSize);

        // Load 'left' images
        left1 = setup(left1Path, BasePanel.tileSize, BasePanel.tileSize);
        left2 = setup(left2Path, BasePanel.tileSize, BasePanel.tileSize);
        left3 = setup(left3Path, BasePanel.tileSize, BasePanel.tileSize);
        left4 = setup(left4Path, BasePanel.tileSize, BasePanel.tileSize);
        left5 = setup(left5Path, BasePanel.tileSize, BasePanel.tileSize);

        // Load 'right' images
        right1 = setup(right1Path, BasePanel.tileSize, BasePanel.tileSize);
        right2 = setup(right2Path, BasePanel.tileSize, BasePanel.tileSize);
        right3 = setup(right3Path, BasePanel.tileSize, BasePanel.tileSize);
        right4 = setup(right4Path, BasePanel.tileSize, BasePanel.tileSize);
        right5 = setup(right5Path, BasePanel.tileSize, BasePanel.tileSize);
    }

    @Override
    public void update() {
        // Move arrow based on direction

        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
        }

        // Update travel distance
        travelDistance += speed;

        if (panel instanceof HallPanel hallPanel) {

            // Check if arrow exceeded range
            if (travelDistance >= range) {
//                System.out.println("Expired2");
                expired = true;
            }

            int monsterIdx = panel.getCollisionChecker().checkEntity(this, hallPanel.getMonsters());
            if (monsterIdx != 999 && !hallPanel.getMonsters()[monsterIdx].invincible) {

                hallPanel.getPlayer().damageMonster(monsterIdx, this);
//                System.out.println("Expired1");
                expired = true;
            }

            // Check for tile or object collision
            collisionOn = false;
            hallPanel.getCollisionCheckerForHall().checkTile(this);
            if (collisionOn) {
//                System.out.println(this.worldX + "Fireball" + this.worldY);
//                System.out.println(hallPanel.getPlayer().screenX + "Player" + hallPanel.getPlayer().screenY);
//                System.out.println("Expired3");
                expired = true;
            }
        }



    }

    @Override
    public void draw(Graphics2D g2) {
        animateDirection();
        animate();

        // Calculate screen position
        int screenX = worldX;
        int screenY = worldY;

        // Draw arrow if within screen bounds
        g2.drawImage(image, screenX, screenY, panel.tileSize, panel.tileSize, null);
    }

    public void animateDirection() {
        switch (direction) {
            case "up" -> {
                switch (spriteNum) {
                    case 1 -> this.image = up1;
                    case 2 -> this.image = up2;
                    case 3 -> this.image = up3;
                    case 4 -> this.image = up4;
                    case 5 -> this.image = up5;
                }
            }
            case "down" -> {
                switch (spriteNum) {
                    case 1 -> this.image = down1;
                    case 2 -> this.image = down2;
                    case 3 -> this.image = down3;
                    case 4 -> this.image = down4;
                    case 5 -> this.image = down5;
                }
            }
            case "left" -> {
                switch (spriteNum) {
                    case 1 -> this.image = left1;
                    case 2 -> this.image = left2;
                    case 3 -> this.image = left3;
                    case 4 -> this.image = left4;
                    case 5 -> this.image = left5;
                }
            }
            case "right" -> {
                switch (spriteNum) {
                    case 1 -> this.image = right1;
                    case 2 -> this.image = right2;
                    case 3 -> this.image = right3;
                    case 4 -> this.image = right4;
                    case 5 -> this.image = right5;
                }
            }
        }
    }

    public void animate() {
        fireballCounter++;
        if (fireballCounter > 12) {
            spriteNum = (spriteNum % 5) + 1; // Cycle through 1 to 5
            fireballCounter = 0;
        }
    }
}