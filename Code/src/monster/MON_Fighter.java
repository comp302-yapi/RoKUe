package monster;

import containers.HallContainer;
import entity.Arrow;
import entity.Entity;
import enums.Hall;
import object.OBJ_LuringGem;
import object.SuperObject;
import views.BasePanel;
import views.HallPanel;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class MON_Fighter extends Entity {

	BasePanel gp;
	public boolean spawned = false;
	public int countdown;
	public boolean attacking;
	private int fighterCounter;
	public int tempScreenY, tempScreenX;
	public int loadCountWalk, loadCountAttacking;s

	public MON_Fighter(BasePanel gp) {
		super(gp);

		this.gp = gp;

		type = 2; // monster type
		name = "Fighter Monster";
		speed = 1; // Added minimal movement
		maxLife = 4;
		life = maxLife;

		// Solid area for collision detection
		solidArea.x = 3;
		solidArea.y = 18;
		solidArea.width = 42;
		solidArea.height = 30;

		solidAreaDefaultWidth = solidArea.width;
		solidAreaDefaultHeight = solidArea.height;

		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;

		// Attack Area
		attackArea.width = 36;
		attackArea.height = 36;

		getImage();
		getImageAttacking();

		chooseImage();
	}

	public void getImage() {
		// Load archer monster images
//		loadCountWalk++;
//		System.out.println(loadCountWalk + "Walking");
		up_walking1 = setup("/res/monster/orc_up_1", BasePanel.tileSize, BasePanel.tileSize);
		up_walking2 = setup("/res/monster/orc_up_2", BasePanel.tileSize, BasePanel.tileSize);
		down_walking1 = setup("/res/monster/orc_down_1", BasePanel.tileSize, BasePanel.tileSize);;
		down_walking2 = setup("/res/monster/orc_down_2", BasePanel.tileSize, BasePanel.tileSize);;
		left_walking1 = setup("/res/monster/orc_left_1", BasePanel.tileSize, BasePanel.tileSize);;
		left_walking2 = setup("/res/monster/orc_left_2", BasePanel.tileSize, BasePanel.tileSize);;
		right_walking1 = setup("/res/monster/orc_right_1", BasePanel.tileSize, BasePanel.tileSize);;
		right_walking2 = setup("/res/monster/orc_right_2", BasePanel.tileSize, BasePanel.tileSize);;
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
//		loadCountAttacking++;
//		System.out.println(loadCountAttacking + "Attacking");
		up_attacking1 = setup("/res/monster/orc_attack_up_1", BasePanel.tileSize, BasePanel.tileSize*2);
		up_attacking2 = setup("/res/monster/orc_attack_up_2", BasePanel.tileSize, BasePanel.tileSize*2);
		down_attacking1 = setup("/res/monster/orc_attack_down_1", BasePanel.tileSize, BasePanel.tileSize*2);
		down_attacking2 = setup("/res/monster/orc_attack_down_2", BasePanel.tileSize, BasePanel.tileSize*2);
		left_attacking1 = setup("/res/monster/orc_attack_left_1", BasePanel.tileSize*2, BasePanel.tileSize);
		left_attacking2 = setup("/res/monster/orc_attack_left_2", BasePanel.tileSize*2, BasePanel.tileSize);
		right_attacking1 = setup("/res/monster/orc_attack_right_1", BasePanel.tileSize*2, BasePanel.tileSize);
		right_attacking2 = setup("/res/monster/orc_attack_right_2", BasePanel.tileSize*2, BasePanel.tileSize);
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

	public boolean isAttackingFighter() {
		return attacking;
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

		if (hitPlayer && !panel.getPlayer().invincible) {
			panel.getPlayer().life -= 1;

			if (panel instanceof HallPanel) {
				((HallPanel) panel).playSE(3);
			}

			panel.getPlayer().invincible = true;
		}

		if (calculateDistanceToPlayer() <= 96) {
			attacking = true;
			direction = determineDirection();
			if (checkLuringGem()) {
				direction = determineDirectionLuringGem();
			}
		} else {

		attacking = false;
		// Add random movement
		actionLockCounter++;
		if (actionLockCounter == 120) {
			Random random = new Random();
			int i = random.nextInt(100) + 1;

			if (i <= 25) {
				direction = "up";

				if (checkLuringGem()) {
					direction = determineDirectionLuringGem();
				}
			} else if (i <= 50) {
				direction = "down";

				if (checkLuringGem()) {
					direction = determineDirectionLuringGem();
				}
			} else if (i <= 75) {
				direction = "left";

				if (checkLuringGem()) {
					direction = determineDirectionLuringGem();
				}
			} else if (i <= 100) {
				direction = "right";

				if (checkLuringGem()) {
					direction = determineDirectionLuringGem();
				}
			}
			actionLockCounter = 0;
		}}
	}

	public BufferedImage getAnimateImage() {
		return image;
	}

	public int getBufferX() {
		return tempScreenX;
	}

	public int getBufferY() {
		return tempScreenY;
	}

	private String determineDirection() {
		int playerX = gp.getPlayer().screenX;
		int playerY = gp.getPlayer().screenY;

		if (Math.abs(playerX - worldX) > Math.abs(playerY - worldY)) {
			return playerX > worldX ? "right" : "left";
		} else {
			return playerY > worldY ? "down" : "up";
		}
	}

	public boolean checkLuringGem() {
		boolean checker = false;

		if (panel instanceof HallPanel) {
			switch (((HallPanel) panel).currentHall) {
				case HallOfEarth -> {
					for (SuperObject obj : HallContainer.getHallOfEarth().objects) {
						if (obj instanceof OBJ_LuringGem) {
							checker = true;
							break;
						}
					}
				}
				case HallOfFire -> {
					for (SuperObject obj : HallContainer.getHallOfFire().objects) {
						if (obj instanceof OBJ_LuringGem) {
							checker = true;
							break;
						}
					}
				}
				case HallOfWater -> {
					for (SuperObject obj : HallContainer.getHallOfWater().objects) {
						if (obj instanceof OBJ_LuringGem) {
							checker = true;
							break;
						}
					}
				}
				case HallOfAir -> {
					for (SuperObject obj : HallContainer.getHallOfAir().objects) {
						if (obj instanceof OBJ_LuringGem) {
							checker = true;
							break;
						}
					}
				}
				default -> checker = false;
			}
		}
		return checker;
	}

	public String determineDirectionLuringGem() {
		if (panel instanceof HallPanel) {
			for (SuperObject obj : HallContainer.getHallOfEarth().objects) {
				if (obj instanceof OBJ_LuringGem luringGem) {
                    if (Math.abs(luringGem.worldX - worldX) > Math.abs(luringGem.worldY - worldY)) {
						return luringGem.worldX > worldX ? "right" : "left";
					} else {
						return luringGem.worldY > worldY ? "down" : "up";
					}
                }
			}
		}
		return direction;
	}

	public void animate() {
		fighterCounter++;
		if (fighterCounter > 12) {
			if (spriteNum == 1) {
				if (isAttackingFighter()) {

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
			fighterCounter = 0;
		}
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
					tempScreenY = worldY - BasePanel.tileSize;
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
					tempScreenX = worldX - BasePanel.tileSize;
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

	private int calculateDistanceToPlayer() {
		int playerX = gp.getPlayer().screenX;
		int playerY = gp.getPlayer().screenY;

		return (int) Math.sqrt(
				Math.pow(worldX - playerX, 2) +
						Math.pow(worldY - playerY, 2)
		);
	}
}