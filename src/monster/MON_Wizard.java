package monster;

import entity.Entity;
import object.SuperObject;
import strategy.TeleportPlayerStrategy;
import strategy.TeleportRuneStrategy;
import views.BasePanel;
import views.HallPanel;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Random;
import object.OBJ_LuringGem;

import containers.HallContainer;

public class MON_Wizard extends Entity {

	private int teleportCounter = 0;
	private int waitCounter = 0;
	private int teleportPlayerCounter;
	public boolean spawned = false; 
	private final int TELEPORT_INTERVAL = 180; 
	private final int WAIT_INTERVAL = 120; 
	private final int TELEPORT_PLAYER_INTERVAL = 60;
	public boolean playerTeleported = false;
	public int tempScreenX, tempScreenY;

	// Transient images
	private transient BufferedImage up1, up2;
	private transient BufferedImage down1, down2;
	private transient BufferedImage left1, left2;
	private transient BufferedImage right1, right2;

	private final String wizardImagePath = "/res/monster/wizard";


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

	public BufferedImage getAnimateImage() {
		return image;
	}

	private void writeObject(ObjectOutputStream out) throws IOException {
		out.defaultWriteObject();
	}

	private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
		in.defaultReadObject();
		getImage();
	}

	public void getImage() {
		// Load wizard sprite images
		up1 = setup(wizardImagePath, BasePanel.tileSize, BasePanel.tileSize);
		up2 = setup(wizardImagePath, BasePanel.tileSize, BasePanel.tileSize);
		// Use the same sprite for all directions since the wizard doesn't move
		down1 = up1;
		down2 = up2;
		left1 = up1;
		left2 = up2;
		right1 = up1;
		right2 = up2;
	}

	public int getBufferX() {
		return tempScreenX;
	}

	public int getBufferY() {
		return tempScreenY;
	}

	public void animateDirection() {

		tempScreenX = worldX;
		tempScreenY = worldY;

		if (!isAttackingFighter()) {

			switch (direction) {
				case "up" -> {
					if (spriteNum == 1) {
						image = up1;
					}
					if (spriteNum == 2) {
						image = up2;
					}
				}
				case "down" -> {
					if (spriteNum == 1) {
						image = down1;
					}
					if (spriteNum == 2) {
						image = down2;
					}
				}
				case "left" -> {
					if (spriteNum == 1) {
						image = left1;
					}
					if (spriteNum == 2) {
						image = left2;
					}
				}
				case "right" -> {
					if (spriteNum == 1) {
						image = right1;
					}
					if (spriteNum == 2) {
						image = right2;
					}
				}
			}}

		if (isAttackingFighter()) {
			switch (direction) {
				case "up" -> {
					int tempScreenY = worldY - BasePanel.tileSize;
					if (spriteNum == 1) {
						image = up1;
					}
					if (spriteNum == 2) {
						image = up2;
					}
				}
				case "down" -> {
					if (spriteNum == 1) {
						image = down1;
					}
					if (spriteNum == 2) {
						image = down2;
					}
				}
				case "left" -> {
					int tempScreenX = worldX - BasePanel.tileSize;
					if (spriteNum == 1) {
						image = left1;
					}
					if (spriteNum == 2) {
						image = left2;
					}
				}
				case "right" -> {
					if (spriteNum == 1) {
						image = right1;
					}
					if (spriteNum == 2) {
						image = right2;
					}
				}
			}
		}
	}

	@Override
	public void setAction() {

		// Initial spawn logic
		if (!spawned) {
			Random random = new Random();
			worldX = random.nextInt(BasePanel.screenWidth * 2); // Adjust range as needed
			worldY = random.nextInt(BasePanel.screenHeight * 2); // Adjust range as needed
			spawned = true;
		}

		// Handle rune teleportation
		
		if (panel instanceof HallPanel hp) {
			
			if(hp.timeLeft < HallContainer.getCurrentHallManager(hp.currentHall).objects.size() * hp.secondPerObject * 0.3) {
				
				teleportPlayerCounter++;
				if(teleportPlayerCounter >= TELEPORT_PLAYER_INTERVAL && !playerTeleported) {
					
					TeleportPlayerStrategy.teleportPlayer(hp);
					
					playerTeleported = true;
					hp.wizardChecker = false;
					hp.getHallMonsters().remove(this);
					teleportPlayerCounter = 0;
				}
				
				
			}
			else if (hp.timeLeft > HallContainer.getCurrentHallManager(hp.currentHall).objects.size() * hp.secondPerObject *  0.7) {
				
				teleportCounter++;
				if (teleportCounter >= TELEPORT_INTERVAL) {
					
					TeleportRuneStrategy.teleportRune(hp);
					
					teleportCounter = 0;
				}
			}
			
			else {
				
				waitCounter++;
				if(waitCounter >= WAIT_INTERVAL) {
					hp.wizardChecker = false;
					hp.getHallMonsters().remove(this);
					waitCounter = 0;
				}
				
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