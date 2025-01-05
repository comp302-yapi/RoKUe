package entity;

import views.BasePanel;
import views.HallPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class SorcererProjectile extends Entity {
    int damage = 4;
    private final int speed;
    private final int range;
    private int travelDistance;
    private boolean expired = false;
    public int knockbackValue = 5;

    public int sorcererProjectileCounter;

    // Paths for 'up' direction
    private final String up1Path = "/res/projectiles/FireBall/up1";
    private final String up2Path = "/res/projectiles/FireBall/up2";
    private final String up3Path = "/res/projectiles/FireBall/up3";
    private final String up4Path = "/res/projectiles/FireBall/up4";
    private final String up5Path = "/res/projectiles/FireBall/up5";

    // Paths for 'down' direction
    private final String down1Path = "/res/projectiles/Sorcerer_Projectile/down1";
    private final String down2Path = "/res/projectiles/Sorcerer_Projectile/down2";
    private final String down3Path = "/res/projectiles/Sorcerer_Projectile/down3";
    private final String down4Path = "/res/projectiles/Sorcerer_Projectile/down4";
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

    // Paths for 'closeLeft' direction
    private final String closeLeft1Path = "/res/projectiles/Sorcerer_Projectile/closeLeft1";
    private final String closeLeft2Path = "/res/projectiles/Sorcerer_Projectile/closeLeft2";
    private final String closeLeft3Path = "/res/projectiles/Sorcerer_Projectile/closeLeft3";
    private final String closeLeft4Path = "/res/projectiles/Sorcerer_Projectile/closeLeft4";

    // Paths for 'closeRight' direction
    private final String closeRight1Path = "/res/projectiles/Sorcerer_Projectile/closeRight1";
    private final String closeRight2Path = "/res/projectiles/Sorcerer_Projectile/closeRight2";
    private final String closeRight3Path = "/res/projectiles/Sorcerer_Projectile/closeRight3";
    private final String closeRight4Path = "/res/projectiles/Sorcerer_Projectile/closeRight4";

    // Paths for 'farLeft' direction
    private final String farLeft1Path = "/res/projectiles/Sorcerer_Projectile/farLeft1";
    private final String farLeft2Path = "/res/projectiles/Sorcerer_Projectile/farLeft2";
    private final String farLeft3Path = "/res/projectiles/Sorcerer_Projectile/farLeft3";
    private final String farLeft4Path = "/res/projectiles/Sorcerer_Projectile/farLeft4";

    // Paths for 'farRight' direction
    private final String farRight1Path = "/res/projectiles/Sorcerer_Projectile/farRight1";
    private final String farRight2Path = "/res/projectiles/Sorcerer_Projectile/farRight2";
    private final String farRight3Path = "/res/projectiles/Sorcerer_Projectile/farRight3";
    private final String farRight4Path = "/res/projectiles/Sorcerer_Projectile/farRight4";


    public SorcererProjectile(BasePanel gp, int worldX, int worldY, String direction) {
        super(gp);

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.panel = gp;
        this.knockbackValue = 5;

        // Arrow characteristics
        damage = 4;
        speed = 8;
        range = 16 * gp.tileSize; // 4 squares range
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

        // Load 'down' images
        down1 = setup(down1Path, BasePanel.tileSize, BasePanel.tileSize);
        down2 = setup(down2Path, BasePanel.tileSize, BasePanel.tileSize);
        down3 = setup(down3Path, BasePanel.tileSize, BasePanel.tileSize);
        down4 = setup(down4Path, BasePanel.tileSize, BasePanel.tileSize);

        // Load 'left' images
        left1 = setup(left1Path, BasePanel.tileSize, BasePanel.tileSize);
        left2 = setup(left2Path, BasePanel.tileSize, BasePanel.tileSize);
        left3 = setup(left3Path, BasePanel.tileSize, BasePanel.tileSize);
        left4 = setup(left4Path, BasePanel.tileSize, BasePanel.tileSize);

        // Load 'right' images
        right1 = setup(right1Path, BasePanel.tileSize, BasePanel.tileSize);
        right2 = setup(right2Path, BasePanel.tileSize, BasePanel.tileSize);
        right3 = setup(right3Path, BasePanel.tileSize, BasePanel.tileSize);
        right4 = setup(right4Path, BasePanel.tileSize, BasePanel.tileSize);

        // Load 'farLeft' images
        farLeft1 = setup(farLeft1Path, BasePanel.tileSize, BasePanel.tileSize);
        farLeft2 = setup(farLeft2Path, BasePanel.tileSize, BasePanel.tileSize);
        farLeft3 = setup(farLeft3Path, BasePanel.tileSize, BasePanel.tileSize);
        farLeft4 = setup(farLeft4Path, BasePanel.tileSize, BasePanel.tileSize);

        // Load 'closeLeft' images
        closeLeft1 = setup(closeLeft1Path, BasePanel.tileSize, BasePanel.tileSize);
        closeLeft2 = setup(closeLeft2Path, BasePanel.tileSize, BasePanel.tileSize);
        closeLeft3 = setup(closeLeft3Path, BasePanel.tileSize, BasePanel.tileSize);
        closeLeft4 = setup(closeLeft4Path, BasePanel.tileSize, BasePanel.tileSize);

        // Load 'farRight' images
        farRight1 = setup(farRight1Path, BasePanel.tileSize, BasePanel.tileSize);
        farRight2 = setup(farRight2Path, BasePanel.tileSize, BasePanel.tileSize);
        farRight3 = setup(farRight3Path, BasePanel.tileSize, BasePanel.tileSize);
        farRight4 = setup(farRight4Path, BasePanel.tileSize, BasePanel.tileSize);

        // Load 'closeRight' images
        closeRight1 = setup(closeRight1Path, BasePanel.tileSize, BasePanel.tileSize);
        closeRight2 = setup(closeRight2Path, BasePanel.tileSize, BasePanel.tileSize);
        closeRight3 = setup(closeRight3Path, BasePanel.tileSize, BasePanel.tileSize);
        closeRight4 = setup(closeRight4Path, BasePanel.tileSize, BasePanel.tileSize);
    }

    @Override
    public void update() {
        // Move arrow based on direction

        switch (direction) {
            case "up" -> worldY -= speed;
            case "down" -> worldY += speed;
            case "left" -> worldX -= speed;
            case "right" -> worldX += speed;
            case "farLeft" -> {
                worldX -= speed;
                worldY += speed / 2;
            }
            case "closeLeft" -> {
                worldX -= speed;
                worldY += (int) (speed / 1);
            }
            case "farRight" -> {
                worldX += speed;
                worldY += speed / 2;
            }
            case "closeRight" -> {
                worldX += speed;
                worldY += (int) (speed / 1);
            }
        }

        // Update travel distance
        travelDistance += speed;

        boolean hitPlayer = panel.getCollisionChecker().checkPlayer(this);
        if (hitPlayer && !panel.getPlayer().invincible) {
            // Reduce player life
            if (!panel.getPlayer().invincibleCloak) {
                panel.getPlayer().damagePlayer(damage);
                panel.getPlayer().invincible = true;

                if (panel instanceof HallPanel) {
                    ((HallPanel) panel).playSE(3);
                }
                expired = true;
            }
        }

        if (panel instanceof HallPanel hallPanel) {

            // Check if arrow exceeded range
            if (travelDistance >= range) {
//                System.out.println("Expired2");
                expired = true;
            }

            int monsterIdx = hallPanel.getCollisionCheckerForHall().checkEntity(this, hallPanel.getMonsters());
//            System.out.println(monsterIdx);
//            System.out.println(hallPanel.getMonsters()[0]);
            if (monsterIdx != 999 && !hallPanel.getMonsters()[monsterIdx].invincible) {

                hallPanel.getMonsters()[monsterIdx].isBurning = true;
                hallPanel.getHallMonsters().get(monsterIdx).isBurning = true;
                hallPanel.getMonsters()[monsterIdx].damageReceived = true;
                hallPanel.getHallMonsters().get(monsterIdx).damageReceived = true;

                hallPanel.getPlayer().damageMonster(monsterIdx, this);
//              System.out.println("Expired1");
                expired = true;
            }

            // Check for tile or object collision
            collisionOn = false;
            hallPanel.getCollisionCheckerForHall().checkTile(this);
            if (collisionOn) {
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
                }
            }
            case "down" -> {
                switch (spriteNum) {
                    case 1 -> this.image = down1;
                    case 2 -> this.image = down2;
                    case 3 -> this.image = down3;
                    case 4 -> this.image = down4;
                }
            }
            case "left" -> {
                switch (spriteNum) {
                    case 1 -> this.image = left1;
                    case 2 -> this.image = left2;
                    case 3 -> this.image = left3;
                    case 4 -> this.image = left4;
                }
            }
            case "right" -> {
                switch (spriteNum) {
                    case 1 -> this.image = right1;
                    case 2 -> this.image = right2;
                    case 3 -> this.image = right3;
                    case 4 -> this.image = right4;
                }
            }
            case "closeLeft" -> {
                switch (spriteNum) {
                    case 1 -> this.image = closeLeft1;
                    case 2 -> this.image = closeLeft2;
                    case 3 -> this.image = closeLeft3;
                    case 4 -> this.image = closeLeft4;
                }
            }
            case "closeRight" -> {
                switch (spriteNum) {
                    case 1 -> this.image = closeRight1;
                    case 2 -> this.image = closeRight2;
                    case 3 -> this.image = closeRight3;
                    case 4 -> this.image = closeRight4;
                }
            }
            case "farLeft" -> {
                switch (spriteNum) {
                    case 1 -> this.image = farLeft1;
                    case 2 -> this.image = farLeft2;
                    case 3 -> this.image = farLeft3;
                    case 4 -> this.image = farLeft4;
                }
            }
            case "farRight" -> {
                switch (spriteNum) {
                    case 1 -> this.image = farRight1;
                    case 2 -> this.image = farRight2;
                    case 3 -> this.image = farRight3;
                    case 4 -> this.image = farRight4;
                }
            }
        }
    }

    public void animate() {
        sorcererProjectileCounter++;
        if (sorcererProjectileCounter > 12) {
            spriteNum = (spriteNum % 5) + 1; // Cycle through 1 to 5
            sorcererProjectileCounter = 0;
        }
    }
}