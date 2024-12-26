package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;

import enums.Hall;
import listeners.BaseKeyListener;
import object.SuperObject;
import views.BasePanel;
import views.GamePanel;
import views.HallPanel;

public class Player extends Entity{

	BaseKeyListener keyH;
	public int screenX;
	public int screenY;
	public boolean invincible = false;
	public int invincibilityCounter = 0;

	public Player(BasePanel panel) {
		super(panel);

		screenX = BasePanel.screenWidth/2 - (BasePanel.tileSize/2);
		screenY = BasePanel.screenHeight/2 - (BasePanel.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = 8;
		solidAreaDefaultY = 16;
		
		setDefaultValues();
		getPlayerImage();
	}

	public void addKeyListener(BaseKeyListener keyListener) {
		this.keyH = keyListener;
	}
	
	public void setDefaultValues() {
		
		worldX = BasePanel.tileSize*37;
		worldY = BasePanel.tileSize*37;
		speed = 4;
		direction = "down";
		
		maxLife = 6;
		life = maxLife;
		
	}
	
	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_up_1.png")));
			up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_up_2.png")));
			down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_down_1.png")));
			down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_down_2.png")));
			left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_left_1.png")));
			left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_left_2.png")));
			right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_right_1.png")));
			right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/boy_right_2.png")));
			
		} catch(IOException e) {
			e.printStackTrace();
		}
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
	}

	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;

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
		}

		if (invincible) {
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.3f));
		}

		g2.drawImage(image, screenX, screenY, BasePanel.tileSize, BasePanel.tileSize, null);
		g2.setColor(Color.red);

		// RESET INVINCIBLE EFFECT SO IT DOES NOT AFFECT MONSTERS
		g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));

//		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
	}
}







