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

		getImage();
		getImageAttacking();

		chooseImage();
	}

	public void getImage() {
		// Load archer monster images
		up_walking1 = setup("/res/monster/ArcherUpWalk/tile000", BasePanel.tileSize, BasePanel.tileSize);
		up_walking2 = setup("/res/monster/ArcherUpWalk/tile001", BasePanel.tileSize, BasePanel.tileSize);
		up_walking3 = setup("/res/monster/ArcherUpWalk/tile002", BasePanel.tileSize, BasePanel.tileSize);
		up_walking4 = setup("/res/monster/ArcherUpWalk/tile003", BasePanel.tileSize, BasePanel.tileSize);;
		up_walking5 = setup("/res/monster/ArcherUpWalk/tile004", BasePanel.tileSize, BasePanel.tileSize);

		down_walking1 = setup("/res/monster/ArcherDownWalk/tile000", BasePanel.tileSize, BasePanel.tileSize);
		down_walking2 = setup("/res/monster/ArcherDownWalk/tile001", BasePanel.tileSize, BasePanel.tileSize);
		down_walking3 = setup("/res/monster/ArcherDownWalk/tile002", BasePanel.tileSize, BasePanel.tileSize);
		down_walking4 = setup("/res/monster/ArcherDownWalk/tile003", BasePanel.tileSize, BasePanel.tileSize);
		down_walking5 = setup("/res/monster/ArcherDownWalk/tile004", BasePanel.tileSize, BasePanel.tileSize);

		left_walking1 = setup("/res/monster/ArcherLeftWalk/tile000", BasePanel.tileSize, BasePanel.tileSize);
		left_walking2 = setup("/res/monster/ArcherLeftWalk/tile001", BasePanel.tileSize, BasePanel.tileSize);
		left_walking3 = setup("/res/monster/ArcherLeftWalk/tile002", BasePanel.tileSize, BasePanel.tileSize);
		left_walking4 = setup("/res/monster/ArcherLeftWalk/tile003", BasePanel.tileSize, BasePanel.tileSize);
		left_walking5 = setup("/res/monster/ArcherLeftWalk/tile004", BasePanel.tileSize, BasePanel.tileSize);

		right_walking1 = setup("/res/monster/ArcherRightWalk/tile000", BasePanel.tileSize, BasePanel.tileSize);
		right_walking2 = setup("/res/monster/ArcherRightWalk/tile001", BasePanel.tileSize, BasePanel.tileSize);
		right_walking3 = setup("/res/monster/ArcherRightWalk/tile002", BasePanel.tileSize, BasePanel.tileSize);
		right_walking4 = setup("/res/monster/ArcherRightWalk/tile003", BasePanel.tileSize, BasePanel.tileSize);
		right_walking5 = setup("/res/monster/ArcherRightWalk/tile004", BasePanel.tileSize, BasePanel.tileSize);
	}

	public void chooseImage() {

		if (attacking) {
			chooseImageAttacking();
		}
		else {
			chooseImageWalking();
		}
	}

	public void getImageAttacking() {
		// Load archer monster images
		up_attacking1 = setup("/res/monster/ArcherUpShoot/tile000", BasePanel.tileSize, BasePanel.tileSize*2);
		up_attacking2 = setup("/res/monster/ArcherUpShoot/tile001", BasePanel.tileSize, BasePanel.tileSize*2);
		up_attacking3 = setup("/res/monster/ArcherUpShoot/tile002", BasePanel.tileSize, BasePanel.tileSize*2);
		up_attacking4 = setup("/res/monster/ArcherUpShoot/tile003", BasePanel.tileSize, BasePanel.tileSize*2);;
		up_attacking5 = setup("/res/monster/ArcherUpShoot/tile004", BasePanel.tileSize, BasePanel.tileSize*2);

		down_attacking1 = setup("/res/monster/ArcherDownShoot/tile000", BasePanel.tileSize, BasePanel.tileSize*2);
		down_attacking2 = setup("/res/monster/ArcherDownShoot/tile001", BasePanel.tileSize, BasePanel.tileSize*2);
		down_attacking3 = setup("/res/monster/ArcherDownShoot/tile002", BasePanel.tileSize, BasePanel.tileSize*2);
		down_attacking4 = setup("/res/monster/ArcherDownShoot/tile003", BasePanel.tileSize, BasePanel.tileSize*2);
		down_attacking5 = setup("/res/monster/ArcherDownShoot/tile004", BasePanel.tileSize, BasePanel.tileSize*2);

		left_attacking1 = setup("/res/monster/ArcherLeftShoot/tile000", BasePanel.tileSize*2, BasePanel.tileSize);
		left_attacking2 = setup("/res/monster/ArcherLeftShoot/tile001", BasePanel.tileSize*2, BasePanel.tileSize);
		left_attacking3 = setup("/res/monster/ArcherLeftShoot/tile002", BasePanel.tileSize*2, BasePanel.tileSize);
		left_attacking4 = setup("/res/monster/ArcherLeftShoot/tile003", BasePanel.tileSize*2, BasePanel.tileSize);
		left_attacking5 = setup("/res/monster/ArcherLeftShoot/tile004", BasePanel.tileSize*2, BasePanel.tileSize);

		right_attacking1 = setup("/res/monster/ArcherRightShoot/tile000", BasePanel.tileSize*2, BasePanel.tileSize);
		right_attacking2 = setup("/res/monster/ArcherRightShoot/tile001", BasePanel.tileSize*2, BasePanel.tileSize);
		right_attacking3 = setup("/res/monster/ArcherRightShoot/tile002", BasePanel.tileSize*2, BasePanel.tileSize);
		right_attacking4 = setup("/res/monster/ArcherRightShoot/tile003", BasePanel.tileSize*2, BasePanel.tileSize);
		right_attacking5 = setup("/res/monster/ArcherRightShoot/tile004", BasePanel.tileSize*2, BasePanel.tileSize);
	}

	public void chooseImageAttacking() {

		up1 = up_attacking1;
		up2 = up_attacking2;
		up3 = up_attacking3;
		up4 = up_attacking4;
		up5 = up_attacking5;

		down1 = down_attacking1;
		down2 = down_attacking2;
		down3 = down_attacking3;
		down4 = down_attacking4;
		down5 = down_attacking5;

		left1 = left_attacking1;
		left2 = left_attacking2;
		left3 = left_attacking3;
		left4 = left_attacking4;
		left5 = left_attacking5;

		right1 = right_attacking1;
		right2 = right_attacking2;
		right3 = right_attacking3;
		right4 = right_attacking4;
		right5 = right_attacking5;

	}

	public void chooseImageWalking() {

		up1 = up_walking1;
		up2 = up_walking2;
		up3 = up_walking3;
		up4 = up_walking4;
		up5 = up_walking5;

		down1 = down_walking1;
		down2 = down_walking2;
		down3 = down_walking3;
		down4 = down_walking4;
		down5 = down_walking5;

		left1 = left_walking1;
		left2 = left_walking2;
		left3 = left_walking3;
		left4 = left_walking4;
		left5 = left_walking5;

		right1 = right_walking1;
		right2 = right_walking2;
		right3 = right_walking3;
		right4 = right_walking4;
		right5 = right_walking5;

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

					
					} else {
			

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