package monster;

import containers.HallContainer;
import entity.Arrow;
import entity.Entity;
import enums.Hall;
import object.OBJ_LuringGem;
import object.SuperObject;
import views.BasePanel;
import views.HallPanel;

import java.util.Random;

public class MON_Fighter extends Entity {

	BasePanel gp;
	public boolean spawned = false;
	public int countdown;
	public boolean attacking;

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

		chooseImage();
	}

	public void chooseImage() {

		if (attacking) {
			getImageAttacking();
		}
		else {
			getImage();
		}
	}

	public boolean isAttackingFighter() {
		return attacking;
	}

	public void getImage() {
		// Load archer monster images
		up1 = setup("/res/monster/orc_up_1", BasePanel.tileSize, BasePanel.tileSize);
		up2 = setup("/res/monster/orc_up_2", BasePanel.tileSize, BasePanel.tileSize);
		down1 = setup("/res/monster/orc_down_1", BasePanel.tileSize, BasePanel.tileSize);;
		down2 = setup("/res/monster/orc_down_2", BasePanel.tileSize, BasePanel.tileSize);;
		left1 = setup("/res/monster/orc_left_1", BasePanel.tileSize, BasePanel.tileSize);;
		left2 = setup("/res/monster/orc_left_2", BasePanel.tileSize, BasePanel.tileSize);;
		right1 = setup("/res/monster/orc_right_1", BasePanel.tileSize, BasePanel.tileSize);;
		right2 = setup("/res/monster/orc_right_2", BasePanel.tileSize, BasePanel.tileSize);;
	}

	public void getImageAttacking() {
		up1 = setup("/res/monster/orc_attack_up_1", BasePanel.tileSize, BasePanel.tileSize*2);
		up2 = setup("/res/monster/orc_attack_up_2", BasePanel.tileSize, BasePanel.tileSize*2);
		down1 = setup("/res/monster/orc_attack_down_1", BasePanel.tileSize, BasePanel.tileSize*2);
		down2 = setup("/res/monster/orc_attack_down_2", BasePanel.tileSize, BasePanel.tileSize*2);
		left1 = setup("/res/monster/orc_attack_left_1", BasePanel.tileSize*2, BasePanel.tileSize);
		left2 = setup("/res/monster/orc_attack_left_2", BasePanel.tileSize*2, BasePanel.tileSize);
		right1 = setup("/res/monster/orc_attack_right_1", BasePanel.tileSize*2, BasePanel.tileSize);
		right2 = setup("/res/monster/orc_attack_right_2", BasePanel.tileSize*2, BasePanel.tileSize);
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

	private int calculateDistanceToPlayer() {
		int playerX = gp.getPlayer().screenX;
		int playerY = gp.getPlayer().screenY;

		return (int) Math.sqrt(
				Math.pow(worldX - playerX, 2) +
						Math.pow(worldY - playerY, 2)
		);
	}
}