package monster;

import entity.Arrow;
import entity.Entity;
import views.BasePanel;

import java.util.Random;

public class MON_Fighter extends Entity {

	BasePanel gp;
	public boolean spawned = false;
	public int countdown;

	public MON_Fighter(BasePanel gp) {
		super(gp);

		this.gp = gp;

		type = 2; // monster type
		name = "Fighter Monster";
		speed = 2; // Added minimal movement
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
		up1 = setup("/res/monster/greenslime_down_1", BasePanel.tileSize, BasePanel.tileSize);
		up2 = setup("/res/monster/greenslime_down_2", BasePanel.tileSize, BasePanel.tileSize);
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
			worldX = random.nextInt(BasePanel.worldWidth);
			worldY = random.nextInt(BasePanel.worldHeight);
			spawned = true;
		}

		boolean hitPlayer = panel.getCollisionChecker().checkPlayer(this);
		if (hitPlayer) {
			// Reduce player life
			panel.getPlayer().life -= 1;

		}

		// Add random movement
		actionLockCounter++;
		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;

			if (i <= 25) {
				direction = "up";
			} else if (i <= 50) {
				direction = "down";
			} else if (i <= 75) {
				direction = "left";
			} else if (i <= 100) {
				direction = "right";
			}

			actionLockCounter = 0;
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
}