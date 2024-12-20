package entity;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import listeners.BaseKeyListener;
import views.BasePanel;

public class Player extends Entity{

	BaseKeyListener keyH;
	
	public final int screenX;
	public final int screenY;
	public int hasKey = 0;

	public Player(BasePanel panel) {
		super(panel);

		screenX = panel.screenWidth/2 - (panel.tileSize/2);
		screenY = panel.screenHeight/2 - (panel.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = solidArea.x;
		solidAreaDefaultY = solidArea.y;
		
		setDefaultValues();
		getPlayerImage();
	}

	public void addKeyListener(BaseKeyListener keyListener) {
		this.keyH = keyListener;
	}
	
	public void setDefaultValues() {
		
		worldX = panel.tileSize*23;
		worldY = panel.tileSize*21;
		speed = 4;
		direction = "down";
		
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
			int objIndex = panel.getCollisionChecker().checkObject(this, true);
			pickupObject(objIndex);
			
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
	
	public void pickupObject(int i) {
		if (i != 999) {
			String objectName = panel.getSuperObjects()[i].name;
	
			switch (objectName) {
				case "Key": 
					hasKey++; 
					panel.getSuperObjects()[i] = null;
					panel.showMessage("You got a key!");
					break;
	
				case "Door": 
					if (hasKey > 0) {
						panel.getSuperObjects()[i] = null;
						hasKey--;
						panel.showMessage("You opened the door!");
					} else {
						panel.showMessage("You need a key!");
					}
					break;
	
				case "Chest":
					panel.showMessage("a chest!");
					break;
	
					case "Barrel":
					case "Stone":
					case "Pot":
						// Kontrol: Bu objeden bir anahtar alındı mı?
						if (!panel.getSuperObjects()[i].name.endsWith("_Used")) {
							if (Math.random() > 0.5) { // %50 ihtimalle anahtar çıkar
								hasKey++;
								panel.showMessage(objectName + " contains a key!");
								panel.getSuperObjects()[i].name = objectName + "_Used"; // Obje "kullanılmış" olarak işaretlenir
							} else {
								panel.showMessage(objectName + " is empty.");
								panel.getSuperObjects()[i].name = objectName + "_Used"; // Obje "kullanılmış" olarak işaretlenir
							}
						} else {
							panel.showMessage(objectName + " has already been searched.");
						}
						break;
				case "Lantern":
				case "Chain":
				case "Cactus":
					panel.showMessage(objectName);
					break;
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
		
		g2.drawImage(image, screenX, screenY, panel.tileSize, panel.tileSize, null);
		g2.setColor(Color.red);
//		g2.drawRect(screenX + solidArea.x, screenY + solidArea.y, solidArea.width, solidArea.height);
	}
}







