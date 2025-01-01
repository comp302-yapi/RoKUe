package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;

import enums.Hall;
import listeners.BaseKeyListener;
import object.SuperObject;
import views.BasePanel;
import views.GamePanel;
import views.HallPanel;
import views.HomePanel;

public class Player extends Entity{

	public BaseKeyListener keyH;
	public int screenX;
	public int screenY;
	public boolean invincible = false;
	public int invincibilityCounter = 0;
	public ArrayList<SuperObject> inventory = new ArrayList<>();
	public int gold;
	private static Player instance; // Static instance of the singleton
	public boolean armorOnIronTorso, armorOnLeatherTorso;


	private Player(BasePanel panel) {
		super(panel);
		System.out.println("PLAYER CREATED");
		screenX = BasePanel.screenWidth/2 - (BasePanel.tileSize/2);
		screenY = BasePanel.screenHeight/2 - (BasePanel.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = 8;
		solidAreaDefaultY = 16;
		
		setDefaultValues();
		getPlayerImage();
		getIronArmorTorso();
		getLeatherArmorTorso();
	}

	// Static method to get the single instance
	public static Player getInstance(BasePanel panel) {
		if (instance == null) {
			instance = new Player(panel);
		}
		// Ensure the panel is set properly each time
		if (instance.panel != panel) {
			instance.panel = panel;
		}

		return instance;
	}

	public void addKeyListener(BaseKeyListener keyListener) {
		this.keyH = keyListener;
	}
	
	public void setDefaultValues() {
		
		worldX = BasePanel.tileSize*37;
		worldY = BasePanel.tileSize*37;
		speed = 4;
		armor = 0;
		gold = 0;
		direction = "down";
		
		maxLife = 12;
		life = maxLife;
		
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_1.png")));
			up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_2.png")));
			down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_1.png")));
			down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_2.png")));
			left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_1.png")));
			left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_2.png")));
			right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_1.png")));
			right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_2.png")));

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void getIronArmorTorso() {
		try {
			ironarmor_torso_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmor/ironarmor_up_1.png")));
			ironarmor_torso_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmor/ironarmor_up_2.png")));
			ironarmor_torso_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmor/ironarmor_down_1.png")));
			ironarmor_torso_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmor/ironarmor_down_2.png")));
			ironarmor_torso_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmor/ironarmor_left_1.png")));
			ironarmor_torso_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmor/ironarmor_left_2.png")));
			ironarmor_torso_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmor/ironarmor_right_1.png")));
			ironarmor_torso_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmor/ironarmor_right_2.png")));

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void getLeatherArmorTorso() {
		try {
			leatherarmor_torso_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmor/leatherarmor_down_1.png")));
			leatherarmor_torso_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmor/leatherarmor_down_2.png")));
			leatherarmor_torso_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmor/leatherarmor_up_1.png")));
			leatherarmor_torso_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmor/leatherarmor_up_2.png")));
			leatherarmor_torso_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmor/leatherarmor_left_1.png")));
			leatherarmor_torso_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmor/leatherarmor_left_2.png")));
			leatherarmor_torso_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmor/leatherarmor_right_1.png")));
			leatherarmor_torso_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmor/leatherarmor_right_2.png")));
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void wearArmorIronTorso() {
		armorOnLeatherTorso = false;
		armorOnIronTorso = true;
	}

	public void wearArmorLeatherTorso() {
		armorOnIronTorso = false;
		armorOnLeatherTorso = true;
	}
	
	public void move() {
		if(panel instanceof GamePanel) {
			if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
				if (keyH.upPressed) {
					direction = "up";
				}
				if (keyH.downPressed) {
					direction = "down";
				}
				if (keyH.leftPressed) {
					direction = "left";
				}
				if (keyH.rightPressed) {
					direction = "right";
				}

				// CHECK TILE COLLISION
				collisionOn = false;
				panel.getCollisionChecker().checkTile(this);

				// CHECK OBJECT COLLISION
				this.solidArea.x = this.solidAreaDefaultX;
				this.solidArea.y = this.solidAreaDefaultY;
				int objIndex = panel.getCollisionChecker().checkObject(this, true);

				// CHECK MONSTER COLLISION
				int monsterIndex = panel.getCollisionChecker().checkEntity(this, panel.getMonsters());

				// IF COLLISION FALSE, PLAYER CAN MOVE
				if (!collisionOn) {
					switch (direction) {
						case "up" -> worldY -= speed;
						case "down" -> worldY += speed;
						case "left" -> worldX -= speed;
						case "right" -> worldX += speed;
					}
				}

				spriteCounter++;
				if (spriteCounter > 12) {
					if (spriteNum == 1) {
						spriteNum = 2;
					} else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			} else {
				spriteNum = 1;
			}
		}

		else if (panel instanceof HallPanel hallPanel) {

            if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
				if (keyH.upPressed) {
					direction = "up";
				}
				if (keyH.downPressed) {
					direction = "down";
				}
				if (keyH.leftPressed) {
					direction = "left";
				}
				if (keyH.rightPressed) {
					direction = "right";
				}

				// CHECK TILE COLLISION
				collisionOn = false;
				hallPanel.getCollisionCheckerForHall().checkTile(this);

				// CHECK OBJECT COLLISION
				this.solidArea.x = 8;
				this.solidArea.y = 16;
				int objIndex = hallPanel.getCollisionCheckerForHall().checkObject(this, true);

				this.solidArea.x = 8;
				this.solidArea.y = 16;

				// INVINCIBLE
				if(invincible) {
					invincibleCounter++;
				}

				if (invincibleCounter >= 60) {
					invincible = false;
					invincibleCounter = 0;
				}

				if (invincibleCloak) {
					invincibleCounterCloak++;
				}

				if (invincibleCounterCloak >= 2) {
					invincibleCloak = false;
					invincibleCounterCloak = 0;
				}

				// CHECK MONSTER COLLISION
				int monsterIndex = hallPanel.getCollisionCheckerForHall().checkEntity(this, hallPanel.getMonsters());

				// IF COLLISION FALSE, PLAYER CAN MOVE
				if (!collisionOn) {
					switch (direction) {
						case "up" -> {
							screenY -= speed;

						}
						case "down" -> {
							screenY += speed;

						}
						case "left" -> {
							screenX -= speed;

						}
						case "right" -> {
							screenX += speed;

						}
					}
				}

				spriteCounter++;
				if (spriteCounter > 12) {
					if (spriteNum == 1) {
						spriteNum = 2;
					} else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			} else {
				spriteNum = 1;
			}

		}

		else if (panel instanceof HomePanel homePanel) {

			if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed) {
				if (keyH.upPressed) {
					direction = "up";
				}
				if (keyH.downPressed) {
					direction = "down";
				}
				if (keyH.leftPressed) {
					direction = "left";
				}
				if (keyH.rightPressed) {
					direction = "right";
				}

				// CHECK TILE COLLISION
				collisionOn = false;
				homePanel.getCollisionCheckerForHome().checkTile(this);

				// CHECK OBJECT COLLISION
				this.solidArea.x = 8;
				this.solidArea.y = 16;

				this.solidArea.x = 8;
				this.solidArea.y = 16;

				// INVINCIBLE
				if(invincible) {
					invincibleCounter++;
				}

				if (invincibleCounter >= 60) {
					invincible = false;
					invincibleCounter = 0;
				}

				if (invincibleCloak) {
					invincibleCounterCloak++;
				}

				if (invincibleCounterCloak >= 2) {
					invincibleCloak = false;
					invincibleCounterCloak = 0;
				}


				// IF COLLISION FALSE, PLAYER CAN MOVE
				if (!collisionOn) {
					switch (direction) {
						case "up" -> {
							screenY -= speed;

						}
						case "down" -> {
							screenY += speed;

						}
						case "left" -> {
							screenX -= speed;

						}
						case "right" -> {
							screenX += speed;

						}
					}
				}

				spriteCounter++;
				if (spriteCounter > 12) {
					if (spriteNum == 1) {
						spriteNum = 2;
					} else if (spriteNum == 2) {
						spriteNum = 1;
					}
					spriteCounter = 0;
				}
			} else {
				spriteNum = 1;
			}

		}
	}

	public void removeKeyListener(BaseKeyListener keyListener) {
		if (keyListener == this.keyH) {
			this.keyH = null;
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		BufferedImage torsoArmorImage = null;

		// Select base image and torso armor image
		switch (direction) {
			case "up" -> {
				image = (spriteNum == 1) ? up1 : up2;
				if (armorOnIronTorso) {
					torsoArmorImage = (spriteNum == 1) ? ironarmor_torso_up1 : ironarmor_torso_up2;
				} else if (armorOnLeatherTorso) {
					torsoArmorImage = (spriteNum == 1) ? leatherarmor_torso_up1 : leatherarmor_torso_up2;
				}
			}
			case "down" -> {
				image = (spriteNum == 1) ? down1 : down2;
				if (armorOnIronTorso) {
					torsoArmorImage = (spriteNum == 1) ? ironarmor_torso_down1 : ironarmor_torso_down2;
				} else if (armorOnLeatherTorso) {
					torsoArmorImage = (spriteNum == 1) ? leatherarmor_torso_down1 : leatherarmor_torso_down2;
				}
			}
			case "left" -> {
				image = (spriteNum == 1) ? left1 : left2;
				if (armorOnIronTorso) {
					torsoArmorImage = (spriteNum == 1) ? ironarmor_torso_left1 : ironarmor_torso_left2;
				} else if (armorOnLeatherTorso) {
					torsoArmorImage = (spriteNum == 1) ? leatherarmor_torso_left1 : leatherarmor_torso_left2;
				}
			}
			case "right" -> {
				image = (spriteNum == 1) ? right1 : right2;
				if (armorOnIronTorso) {
					torsoArmorImage = (spriteNum == 1) ? ironarmor_torso_right1 : ironarmor_torso_right2;
				} else if (armorOnLeatherTorso) {
					torsoArmorImage = (spriteNum == 1) ? leatherarmor_torso_right1 : leatherarmor_torso_right2;
				}
			}
		}

		if (invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		if (invincibleCloak) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f));
		}

		// Draw the base image
		g2.drawImage(image, screenX, screenY, BasePanel.tileSize, BasePanel.tileSize, null);

		// Draw torso armor image
		if (torsoArmorImage != null) {
			g2.drawImage(torsoArmorImage, screenX, screenY, BasePanel.tileSize, BasePanel.tileSize, null);
		}

		g2.setColor(Color.red);

		// RESET INVINCIBLE EFFECT SO IT DOES NOT AFFECT MONSTERS
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

//	g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
	}

	public int inventoryLength() {
		int count = 0;
		for (SuperObject object : inventory) {
			if (object != null) {
				count++;
			}
		}
		return count;
	}

	public void damagePlayer(int attackDamage) {

		int finalDamage = Math.max(attackDamage - armor, 0);

		panel.getPlayer().life -= finalDamage;

		if (panel instanceof HallPanel) {
			((HallPanel) panel).playSE(3);
		}

		panel.getPlayer().invincible = true;

	}

}







