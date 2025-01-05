package entity;

import views.BasePanel;
import views.HallPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Laser extends Entity {
    int damage = 4;
    private final int speed;
    private final int range;
    private int travelDistance;
    private boolean expired = false;
    public int knockbackValue = 5;

    public int sorcererProjectileCounter;

    // Paths for 'right' direction

    private final String right1Path = "/res/projectiles/LaserAnimation/tile003";
    private final String right2Path = "/res/projectiles/LaserAnimation/tile004";
    private final String right3Path = "/res/projectiles/LaserAnimation/tile005";
    private final String right4Path = "/res/projectiles/LaserAnimation/tile006";
    private final String right5Path = "/res/projectiles/LaserAnimation/tile007";
    private final String right6Path = "/res/projectiles/LaserAnimation/tile008";


    public Laser(BasePanel gp, int worldX, int worldY, String direction) {
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
        // Load 'right' images
        right1 = setup(right1Path, BasePanel.tileSize, BasePanel.tileSize);
        right2 = setup(right2Path, BasePanel.tileSize, BasePanel.tileSize);
        right3 = setup(right3Path, BasePanel.tileSize, BasePanel.tileSize);
        right4 = setup(right4Path, BasePanel.tileSize, BasePanel.tileSize);
        right5 = setup(right5Path, BasePanel.tileSize, BasePanel.tileSize);
        right6 = setup(right6Path, BasePanel.tileSize, BasePanel.tileSize);
    }

    @Override
    public void update() {
        // Update travel distance
        travelDistance += speed;

//        System.out.println((this.solidArea.x + this.worldX) + " " + (this.solidArea.y + this.worldY));
        boolean hitPlayer = panel.getCollisionChecker().checkPlayer(this);
        if (hitPlayer && !panel.getPlayer().invincible) {
            // Reduce player life
            if (!panel.getPlayer().invincibleCloak) {
                panel.getPlayer().damagePlayer(damage);
                panel.getPlayer().invincible = true;

                if (panel instanceof HallPanel) {
                    ((HallPanel) panel).playSE(3);
                }
//                expired = true;
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
        g2.drawImage(image, screenX-2400, screenY+200, panel.tileSize*100, panel.tileSize, null);
    }

    public void animateDirection() {
        switch (spriteNum) {
            case 1 -> this.image = right1;
            case 2 -> this.image = right2;
            case 3 -> this.image = right3;
            case 4 -> this.image = right4;
            case 5 -> this.image = right5;
            case 6 -> this.image = right6;
        }
    }

    public void animate() {
        sorcererProjectileCounter++;
        if (sorcererProjectileCounter > 30) {
            spriteNum = (spriteNum % 6) + 1;
            sorcererProjectileCounter = 0;
        }

        if (spriteNum == 1) {
            this.solidArea.y = 0;
            solidArea.width = 0;

        }
        if (spriteNum > 2) {
            this.solidArea.y = this.worldY - 150;
            this.solidArea.x -= 650;

            solidArea.width = 1440;
        }
        if (spriteNum == 6) {
            this.expired = true;
        }
    }
}