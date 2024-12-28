package entity;

import views.BasePanel;
import views.HallPanel;

import java.awt.*;
import java.awt.image.BufferedImage;

public class Arrow extends Entity {
    private int damage;
    private final int speed;
    private final int range;
    private int travelDistance;
    private boolean expired = false;

    public Arrow(BasePanel gp, int worldX, int worldY, String direction) {
        super(gp);

        this.worldX = worldX;
        this.worldY = worldY;
        this.direction = direction;
        this.panel = gp;

        // Arrow characteristics
        damage = 1;
        speed = 5;
        range = 4 * gp.tileSize; // 4 squares range
        travelDistance = 0;

        // Set up arrow images
        getArrowImage();
    }

    public boolean isExpired() {
        return expired;
    }

    private void getArrowImage() {
        // Load arrow images for different directions
        up1 = setup("/res/projectiles/arrow_up", 16, 16);
        down1 = setup("/res/projectiles/arrow_down", 16, 16);
        left1 = setup("/res/projectiles/arrow_left", 16, 16);
        right1 = setup("/res/projectiles/arrow_right", 16, 16);
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

        // Check for player collision
        boolean hitPlayer = panel.getCollisionChecker().checkPlayer(this);
        if (hitPlayer && !panel.getPlayer().invincible) {
            // Reduce player life
            if (!panel.getPlayer().invincibleCloak) {
                panel.getPlayer().life -= damage;
                panel.getPlayer().invincible = true;

                if (panel instanceof HallPanel) {
                    ((HallPanel) panel).playSE(3);
                }
                expired = true;
            }
        }

        // Check if arrow exceeded range
        if (travelDistance >= range) {
            expired = true;
        }

        // Check for tile or object collision
        collisionOn = false;
        panel.getCollisionChecker().checkTile(this);
        if (collisionOn) {
            expired = true;
        }
    }

    @Override
    public void draw(Graphics2D g2) {
        BufferedImage image = null;

        // Select appropriate image based on direction
        switch (direction) {
            case "up" -> image = up1;
            case "down" -> image = down1;
            case "left" -> image = left1;
            case "right" -> image = right1;
        }

        // Calculate screen position
        int screenX = worldX;
        int screenY = worldY;

        // Draw arrow if within screen bounds
        g2.drawImage(image, screenX, screenY, panel.tileSize, panel.tileSize, null);
    }
}