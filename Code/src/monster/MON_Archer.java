package monster;

import entity.Arrow;
import entity.Entity;
import views.BasePanel;
import java.util.Random;

public class MON_Archer extends Entity {

	BasePanel gp;
	private int shootCounter = 0;
	public boolean spawned = false;

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
		up1 = setup("/res/monster/skeletonlord_up_1", gp.tileSize, gp.tileSize);
		up2 = setup("/res/monster/skeletonlord_up_2", gp.tileSize, gp.tileSize);;
		down1 = setup("/res/monster/skeletonlord_down_1", gp.tileSize, gp.tileSize);;
		down2 = setup("/res/monster/skeletonlord_down_2", gp.tileSize, gp.tileSize);;
		left1 = setup("/res/monster/skeletonlord_left_1", gp.tileSize, gp.tileSize);;
		left2 = setup("/res/monster/skeletonlord_left_2", gp.tileSize, gp.tileSize);;
		right1 = setup("/res/monster/skeletonlord_right_1", gp.tileSize, gp.tileSize);;
		right2 = setup("/res/monster/skeletonlord_right_2", gp.tileSize, gp.tileSize);;
	}

	// Add a new field to remember if we're locked to the opposite direction:
	private boolean inOppositeMode = false;

	public void setAction() {
		// Initial spawn
		if (!spawned) {
			Random random = new Random();
			worldX = random.nextInt(gp.worldWidth);
			worldY = random.nextInt(gp.worldHeight);
			spawned = true;
		}

		int distance = calculateDistanceToPlayer();

		if (distance < 48 * 4) {
			if (!inOppositeMode) {
				switch (gp.getPlayer().direction) {
					case "up":    direction = "down";  break;
					case "down":  direction = "up";    break;
					case "left":  direction = "right"; break;
					case "right": direction = "left";  break;
					default:      direction = "unknown";
				}
				inOppositeMode = true;
			}
		} else {
			if (inOppositeMode) {
				inOppositeMode = false;
			}
			// Random movement
			actionLockCounter++;
			if (actionLockCounter == 120) {
				Random random = new Random();
				int i = random.nextInt(100) + 1;
				if      (i <= 25) direction = "up";
				else if (i <= 50) direction = "down";
				else if (i <= 75) direction = "left";
				else              direction = "right";
				actionLockCounter = 0;
			}
		}

		// Shoot arrow
		shootCounter++;
		if (shootCounter >= 60) {
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
		int playerX = gp.getPlayer().screenX;
		int playerY = gp.getPlayer().screenY;

		return (int) Math.sqrt(
				Math.pow(worldX - playerX, 2) +
						Math.pow(worldY - playerY, 2)
		);
	}

	private String determineArrowDirection() {
		int playerX = gp.getPlayer().screenX;
		int playerY = gp.getPlayer().screenY;

		if (Math.abs(playerX - worldX) > Math.abs(playerY - worldY)) {
			return playerX > worldX ? "right" : "left";
		} else {
			return playerY > worldY ? "down" : "up";
		}
	}
}