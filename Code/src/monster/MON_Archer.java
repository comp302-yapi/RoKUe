package monster;

import entity.Arrow;
import entity.Entity;
import views.BasePanel;
import views.HallPanel;

import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_Archer extends Entity {

	BasePanel gp;
	private int shootCounter = 0;
	public boolean spawned = false;
	public int archerCounter;
	public int tempScreenX, tempScreenY;
	public boolean attacking;


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

		chooseImage();
	}

	public void getImage() {
		// Load archer monster images
		up1 = setup("/res/monster/ArcherUpWalk/tile000", BasePanel.tileSize, BasePanel.tileSize);
		up2 = setup("/res/monster/ArcherUpWalk/tile001", BasePanel.tileSize, BasePanel.tileSize);
		up3 = setup("/res/monster/ArcherUpWalk/tile002", BasePanel.tileSize, BasePanel.tileSize);
		up4 = setup("/res/monster/ArcherUpWalk/tile003", BasePanel.tileSize, BasePanel.tileSize);;
		up5 = setup("/res/monster/ArcherUpWalk/tile004", BasePanel.tileSize, BasePanel.tileSize);

		down1 = setup("/res/monster/ArcherDownWalk/tile000", BasePanel.tileSize, BasePanel.tileSize);
		down2 = setup("/res/monster/ArcherDownWalk/tile001", BasePanel.tileSize, BasePanel.tileSize);
		down3 = setup("/res/monster/ArcherDownWalk/tile002", BasePanel.tileSize, BasePanel.tileSize);
		down4 = setup("/res/monster/ArcherDownWalk/tile003", BasePanel.tileSize, BasePanel.tileSize);
		down5 = setup("/res/monster/ArcherDownWalk/tile004", BasePanel.tileSize, BasePanel.tileSize);

		left1 = setup("/res/monster/ArcherLeftWalk/tile000", BasePanel.tileSize, BasePanel.tileSize);
		left2 = setup("/res/monster/ArcherLeftWalk/tile001", BasePanel.tileSize, BasePanel.tileSize);
		left3 = setup("/res/monster/ArcherLeftWalk/tile002", BasePanel.tileSize, BasePanel.tileSize);
		left4 = setup("/res/monster/ArcherLeftWalk/tile003", BasePanel.tileSize, BasePanel.tileSize);
		left5 = setup("/res/monster/ArcherLeftWalk/tile004", BasePanel.tileSize, BasePanel.tileSize);

		right1 = setup("/res/monster/ArcherRightWalk/tile000", BasePanel.tileSize, BasePanel.tileSize);
		right2 = setup("/res/monster/ArcherRightWalk/tile001", BasePanel.tileSize, BasePanel.tileSize);
		right3 = setup("/res/monster/ArcherRightWalk/tile002", BasePanel.tileSize, BasePanel.tileSize);
		right4 = setup("/res/monster/ArcherRightWalk/tile003", BasePanel.tileSize, BasePanel.tileSize);
		right5 = setup("/res/monster/ArcherRightWalk/tile004", BasePanel.tileSize, BasePanel.tileSize);
	}

	public void chooseImage() {

		if (attacking) {
			getImageAttacking();
		}
		else {
			getImage();
		}
	}

	public void getImageAttacking() {
		// Load archer monster images
		up1 = setup("/res/monster/ArcherUpShoot/tile000", BasePanel.tileSize, BasePanel.tileSize);
		up2 = setup("/res/monster/ArcherUpShoot/tile001", BasePanel.tileSize, BasePanel.tileSize);
		up3 = setup("/res/monster/ArcherUpShoot/tile002", BasePanel.tileSize, BasePanel.tileSize);
		up4 = setup("/res/monster/ArcherUpShoot/tile003", BasePanel.tileSize, BasePanel.tileSize);;
		up5 = setup("/res/monster/ArcherUpShoot/tile004", BasePanel.tileSize, BasePanel.tileSize);

		down1 = setup("/res/monster/ArcherDownShoot/tile000", BasePanel.tileSize, BasePanel.tileSize);
		down2 = setup("/res/monster/ArcherDownShoot/tile001", BasePanel.tileSize, BasePanel.tileSize);
		down3 = setup("/res/monster/ArcherDownShoot/tile002", BasePanel.tileSize, BasePanel.tileSize);
		down4 = setup("/res/monster/ArcherDownShoot/tile003", BasePanel.tileSize, BasePanel.tileSize);
		down5 = setup("/res/monster/ArcherDownShoot/tile004", BasePanel.tileSize, BasePanel.tileSize);

		left1 = setup("/res/monster/ArcherLeftShoot/tile000", BasePanel.tileSize, BasePanel.tileSize);
		left2 = setup("/res/monster/ArcherLeftShoot/tile001", BasePanel.tileSize, BasePanel.tileSize);
		left3 = setup("/res/monster/ArcherLeftShoot/tile002", BasePanel.tileSize, BasePanel.tileSize);
		left4 = setup("/res/monster/ArcherLeftShoot/tile003", BasePanel.tileSize, BasePanel.tileSize);
		left5 = setup("/res/monster/ArcherLeftShoot/tile004", BasePanel.tileSize, BasePanel.tileSize);

		right1 = setup("/res/monster/ArcherRightShoot/tile000", BasePanel.tileSize, BasePanel.tileSize);
		right2 = setup("/res/monster/ArcherRightShoot/tile001", BasePanel.tileSize, BasePanel.tileSize);
		right3 = setup("/res/monster/ArcherRightShoot/tile002", BasePanel.tileSize, BasePanel.tileSize);
		right4 = setup("/res/monster/ArcherRightShoot/tile003", BasePanel.tileSize, BasePanel.tileSize);
		right5 = setup("/res/monster/ArcherRightShoot/tile004", BasePanel.tileSize, BasePanel.tileSize);
	}


	// Add a new field to remember if we're locked to the opposite direction:
	private boolean inOppositeMode = false;

	public void setAction() {
		// Initial spawn
		if (!spawned) {
			Random random = new Random();
			worldX = random.nextInt(BasePanel.worldWidth);
			worldY = random.nextInt(BasePanel.worldHeight);
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

	public int getBufferX() {
		return tempScreenX;
	}

	public int getBufferY() {
		return tempScreenY;
	}

	private void shootArrow() {
		// Calculate distance to player
		int distanceToPlayer = calculateDistanceToPlayer();

		// Check if player is within 4 squares
		if (distanceToPlayer <= 4 * gp.tileSize) {
			attacking = true;
			// Determine arrow direction
			this.collisionOn = true;
			System.out.println("Collision changed to true");

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
		} else {
			attacking = false;
			shootCounter = 60;

		}
	}

	public void animate() {
		archerCounter++;
		if (archerCounter > 12) {
			if (spriteNum == 1) {
				if (isAttackingArcher()) {

					// Save current values
					int currentWorldX = worldX;
					int currentWorldY = worldY;
					int solidAreaWidth = solidArea.width;
					int solidAreaHeight = solidArea.height;

					// Adjust attacking
					// Adjust player's worldX/Y for the attackArea
					switch (direction) {
						case "up":
							worldY -= attackArea.height;
							break;
						case "down":
							worldY += attackArea.height;
							break;
						case "left":
							worldX -= attackArea.width;
							break;
						case "right":
							worldX += attackArea.width;
							break;
					}

					solidArea.width = attackArea.width;
					solidArea.height = attackArea.height;

					boolean playerHitAttackCheck = panel.getCollisionChecker().checkPlayer(this);

					if (playerHitAttackCheck && !panel.getPlayer().invincible) {
						panel.getPlayer().life -= 1;
						panel.getPlayer().invincible = true;

						if (panel instanceof HallPanel) {
							((HallPanel) panel).playSE(3);
						}

						System.out.println("HIT PLAYER");
					} else {
						System.out.println("Miss");

					}

					worldX = currentWorldX;
					worldY = currentWorldY;
					solidArea.width = solidAreaWidth;
					solidArea.height = solidAreaHeight;
				}

				spriteNum = 2;
			} else if (spriteNum == 2) {
				spriteNum = 1;
			}
			archerCounter = 0;
		}
	}

	public void animateDirection() {

		tempScreenX = worldX;
		tempScreenY = worldY;

		if (!isAttackingArcher()) {

			switch (direction) {
				case "up" -> {
					if (spriteNum == 1) {
						image = up1;
					}
					if (spriteNum == 2) {
						image = up2;
					}
					if (spriteNum == 3) {
						image = up3;
					}
					if (spriteNum == 4) {
						image = up4;
					}
					if (spriteNum == 5) {
						image = up5;
					}
				}
				case "down" -> {
					if (spriteNum == 1) {
						image = down1;
					}
					if (spriteNum == 2) {
						image = down2;
					}
					if (spriteNum == 3) {
						image = down3;
					}
					if (spriteNum == 4) {
						image = down4;
					}
					if (spriteNum == 5) {
						image = down5;
					}
				}
				case "left" -> {
					if (spriteNum == 1) {
						image = left1;
					}
					if (spriteNum == 2) {
						image = left2;
					}
					if (spriteNum == 3) {
						image = left3;
					}
					if (spriteNum == 4) {
						image = left4;
					}
					if (spriteNum == 5) {
						image = left5;
					}
				}
				case "right" -> {
					if (spriteNum == 1) {
						image = right1;
					}
					if (spriteNum == 2) {
						image = right2;
					}
					if (spriteNum == 3) {
						image = right3;
					}
					if (spriteNum == 4) {
						image = right4;
					}
					if (spriteNum == 5) {
						image = right5;
					}
				}
			}}

		if (isAttackingArcher()) {
			switch (direction) {
				case "up" -> {
					tempScreenY = worldY - BasePanel.tileSize;
					if (spriteNum == 1) {
						image = up1;
					}
					if (spriteNum == 2) {
						image = up2;
					}
					if (spriteNum == 3) {
						image = up3;
					}
					if (spriteNum == 4) {
						image = up4;
					}
					if (spriteNum == 5) {
						image = up5;
					}
				}
				case "down" -> {
					if (spriteNum == 1) {
						image = down1;
					}
					if (spriteNum == 2) {
						image = down2;
					}
					if (spriteNum == 3) {
						image = down3;
					}
					if (spriteNum == 4) {
						image = down4;
					}
					if (spriteNum == 5) {
						image = down5;
					}
				}
				case "left" -> {
					tempScreenX = worldX - BasePanel.tileSize;
					if (spriteNum == 1) {
						image = left1;
					}
					if (spriteNum == 2) {
						image = left2;
					}
					if (spriteNum == 3) {
						image = left3;
					}
					if (spriteNum == 4) {
						image = left4;
					}
					if (spriteNum == 5) {
						image = left5;
					}
				}
				case "right" -> {
					if (spriteNum == 1) {
						image = right1;
					}
					if (spriteNum == 2) {
						image = right2;
					}
					if (spriteNum == 3) {
						image = right3;
					}
					if (spriteNum == 4) {
						image = right4;
					}
					if (spriteNum == 5) {
						image = right5;
					}
				}
			}
		}
	}

	public boolean isAttackingArcher() {
		return attacking;
	}

	public BufferedImage getAnimateImage() {
		return image;
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