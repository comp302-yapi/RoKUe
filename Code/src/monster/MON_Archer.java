package monster;

import entity.Arrow;
import entity.Entity;
import views.BasePanel;
import java.util.Random;

public class MON_Archer extends Entity {

	BasePanel gp;
	private int shootCounter = 0;
	private boolean spawned = false;

	public MON_Archer(BasePanel gp) {
		super(gp);

		this.gp = gp;

		type = 2; // monster type
		name = "Archer Monster";
		speed = 1; // Added minimal movement
		maxLife = 4;
		life = maxLife;

		// Solid area for collision detection
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		getImage();
	}

	public void getImage() {
		// Load archer monster images
		up1 = setup("/res/monster/archer", gp.tileSize, gp.tileSize);
		up2 = setup("/res/monster/archer", gp.tileSize, gp.tileSize);
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}

	public void setAction() {
		// Spawn in a random location in the hall if not already spawned
		if (!spawned) {
			Random random = new Random();
			worldX = random.nextInt(gp.worldWidth);
			worldY = random.nextInt(gp.worldHeight);
			spawned = true;
		}

		// Add random movement
		actionLockCounter++;
		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;

			if (i <= 25) {
				direction = "up";
			}
			else if (i <= 50) {
				direction = "down";
			}
			else if (i <= 75) {
				direction = "left";
			}
			else if (i <= 100) {
				direction = "right";
			}

			actionLockCounter = 0;
		}

		// Shoot arrow every second
		shootCounter++;
		if (shootCounter >= 60) { // Assuming 60 FPS
			shootArrow();
			shootCounter = 0;
		}
	}

	private void shootArrow() {
		// Calculate distance to player
		int distanceToPlayer = calculateDistanceToPlayer();

		// Check if player is within 4 squares
		if (distanceToPlayer <= 4 * gp.tileSize) {
			// Determine arrow direction
			String arrowDirection = determineArrowDirection();

			// Create and launch arrow
			Arrow arrow = new Arrow(gp, worldX, worldY, arrowDirection);

			// Add arrow to game's arrow array
			Arrow[] arrows = gp.getArrows();
			for (int i = 0; i < arrows.length; i++) {
				if (arrows[i] == null) {
					arrows[i] = arrow;
					break;
				}
			}
		}
	}

	private int calculateDistanceToPlayer() {
		int playerX = gp.getPlayer().worldX;
		int playerY = gp.getPlayer().worldY;

		return (int) Math.sqrt(
				Math.pow(worldX - playerX, 2) +
						Math.pow(worldY - playerY, 2)
		);
	}

	private String determineArrowDirection() {
		int playerX = gp.getPlayer().worldX;
		int playerY = gp.getPlayer().worldY;

		if (Math.abs(playerX - worldX) > Math.abs(playerY - worldY)) {
			return playerX > worldX ? "right" : "left";
		} else {
			return playerY > worldY ? "down" : "up";
		}
	}
}