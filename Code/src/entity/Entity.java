package entity;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;
import javax.imageio.ImageIO;
import utils.ImageUtils;
import views.BasePanel;
import views.HallPanel;

public class Entity {
	
	public BasePanel panel;
	public int worldX, worldY;
	public int speed;
	
	public BufferedImage up1, up2, down1, down2, left1, left2, right1, right2;
	public BufferedImage attackUp1, attackUp2, attackDown1, attackDown2, attackLeft1, attackLeft2, attackRight1, attackRight2;
	public String direction = "down";
	
	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public boolean collisionOn = false;
	public int actionLockCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter;
	boolean attacking = false;
	
	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	
	
	public int type; // 0 = player, 1 = npc, 2 = monster
	
	// CHARACTER STATUS
	public int maxLife;
	public int life;
	
	
	public Entity(BasePanel panel) {
		this.panel = panel;
	}
	
	public void setAction() {
		
	}
	
	public void update() {
		
		setAction();
		
		boolean contactPlayer;
		if (panel instanceof HallPanel p) {
			collisionOn = false;
			p.getCollisionCheckerForHall().checkTile(this);
			p.getCollisionCheckerForHall().checkObject(this, false);
			p.getCollisionCheckerForHall().checkEntity(this, panel.getMonsters());
			contactPlayer = p.getCollisionCheckerForHall().checkPlayer(this);
		}
		
		else {
			collisionOn = false;
			panel.getCollisionChecker().checkTile(this);
			panel.getCollisionChecker().checkObject(this, false);
			panel.getCollisionChecker().checkEntity(this, panel.getMonsters());
			contactPlayer = panel.getCollisionChecker().checkPlayer(this);
		}
		
		if (this.type == 2 && contactPlayer) {
			if(!panel.getPlayer().invincible) {
				panel.getPlayer().life -= 1;
				panel.getPlayer().invincible = true;
			}
		}

		if (life == 0) {
			panel.getViewManager().switchTo("TitlePanel", true);
		}
		
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
	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;
		
		
		//System.out.println("çizilecek gibi");
		if (true) {

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
			
			
			g2.drawImage(image, worldX, worldY, BasePanel.tileSize, BasePanel.tileSize, null);
			
		}
	}
	
	
	public BufferedImage setup(String imagePath, int width, int height) {
		
		ImageUtils uTool = new ImageUtils();
		BufferedImage image = null;
		
		try {
			image = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream(imagePath + ".png")));
			image = uTool.scaleImage(image, width, height);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return image;
		
	}
}
