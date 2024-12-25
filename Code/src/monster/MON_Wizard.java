package monster;

import entity.Entity;
import views.BasePanel;

import java.util.Random;

public class MON_Wizard extends Entity {

	private int teleportCounter = 0;
	public boolean spawned = false;
	private final int TELEPORT_INTERVAL = 300; // 5 seconds at 60 FPS

	public MON_Wizard(BasePanel panel) {
		super(panel);

		type = 2; // monster type
		name = "Wizard";
		speed = 0; // Wizard doesn't move
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
		// Load wizard sprite images - you'll need to create these sprites
		up1 = setup("/res/monster/wizard", panel.tileSize, panel.tileSize);
		up2 = setup("/res/monster/wizard", panel.tileSize, panel.tileSize);
		// Use same sprite for all directions since wizard doesn't move
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}

	@Override
	public void setAction() {

		// Initial spawn logic
		if (!spawned) {
			Random random = new Random();
			worldX = random.nextInt(panel.screenWidth * 2); // Adjust range as needed
			worldY = random.nextInt(panel.screenHeight * 2); // Adjust range as needed
			spawned = true;
		}

		// Handle rune teleportation
		teleportCounter++;
		if (teleportCounter >= TELEPORT_INTERVAL) {
			teleportRune();
			teleportCounter = 0;
		}
	}

	private void teleportRune() {
		Random random = new Random();

		// Find and teleport the key (rune)
		for (int i = 0; i < panel.getSuperObjects().length; i++) {
			if (panel.getSuperObjects()[i] != null && panel.getSuperObjects()[i].name.equals("Key")) {
				// Generate random position
				int newX = random.nextInt(panel.screenWidth * 2); // Adjust range as needed
				int newY = random.nextInt(panel.screenHeight * 2); // Adjust range as needed

				// Update rune position
				panel.getSuperObjects()[i].worldX = newX;
				panel.getSuperObjects()[i].worldY = newY;
				System.out.println(newX + " | " + newY);
				break;
			}
		}
	}

	@Override
	public void update() {
		// Override the parent update to remove movement logic
		setAction();

		// Check collisions but don't move
		collisionOn = false;
		panel.getCollisionChecker().checkTile(this);
		panel.getCollisionChecker().checkObject(this, false);
		panel.getCollisionChecker().checkEntity(this, panel.getMonsters());
		boolean contactPlayer = panel.getCollisionChecker().checkPlayer(this);

		// Handle player contact
		if (contactPlayer) {
			if (!panel.getPlayer().invincible) {
				panel.getPlayer().life -= 1;
				panel.getPlayer().invincible = true;
			}
		}

		// Update sprite animation
		spriteCounter++;
		if (spriteCounter > 12) {
			spriteNum = (spriteNum == 1) ? 2 : 1;
			spriteCounter = 0;
		}
	}
}