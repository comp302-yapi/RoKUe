package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.util.Objects;
import javax.imageio.ImageIO;

import monster.MON_Archer;
import monster.MON_Fighter;
import utils.ImageUtils;
import views.BasePanel;
import views.HallPanel;
import views.HomePanel;


public class Entity implements Serializable {

	private static final long serialVersionUID = 1L;
	
	public BasePanel panel;
	public int worldX, worldY;
	public int speed;
	
	public transient BufferedImage up1, up2, up3, up4, up5;
	public transient BufferedImage down1, down2, down3, down4, down5;
	public transient BufferedImage left1, left2, left3, left4, left5;
	public transient BufferedImage right1, right2 , right3, right4, right5;

	public transient BufferedImage up_attacking1, up_attacking2, up_attacking3, up_attacking4, up_attacking5;
	public transient BufferedImage down_attacking1, down_attacking2, down_attacking3, down_attacking4, down_attacking5;
	public transient BufferedImage left_attacking1, left_attacking2, left_attacking3, left_attacking4, left_attacking5;
	public transient BufferedImage right_attacking1, right_attacking2, right_attacking3, right_attacking4, right_attacking5;

	public transient BufferedImage up_walking1, up_walking2, up_walking3, up_walking4, up_walking5;
	public transient BufferedImage down_walking1, down_walking2, down_walking3, down_walking4, down_walking5;
	public transient BufferedImage left_walking1, left_walking2, left_walking3, left_walking4, left_walking5;
	public transient BufferedImage right_walking1, right_walking2, right_walking3, right_walking4, right_walking5;

	public transient BufferedImage ironarmor_torso_up1, ironarmor_torso_up2;
	public transient BufferedImage ironarmor_torso_down1, ironarmor_torso_down2;
	public transient BufferedImage ironarmor_torso_left1, ironarmor_torso_left2;
	public transient BufferedImage ironarmor_torso_right1, ironarmor_torso_right2;

	public transient BufferedImage leatherarmor_torso_up1, leatherarmor_torso_up2;
	public transient BufferedImage leatherarmor_torso_down1, leatherarmor_torso_down2;
	public transient BufferedImage leatherarmor_torso_left1, leatherarmor_torso_left2;
	public transient BufferedImage leatherarmor_torso_right1, leatherarmor_torso_right2;

	public String direction = "down";

	public int spriteCounter = 0;
	public int spriteNum = 1;
	
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX, solidAreaDefaultY;
	public int solidAreaDefaultWidth, solidAreaDefaultHeight;

	public Rectangle attackArea = new Rectangle(0, 0, 48, 48);

	public boolean collisionOn = false;
	public int actionLockCounter = 0;
	public boolean invincible = false;
	public int invincibleCounter;

	public boolean invincibleCloak = false;
	public int invincibleCounterCloak;
	
	public transient BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	
	
	public int type; // 0 = player, 1 = npc, 2 = monster
	
	// CHARACTER STATUS
	public int maxLife;
	public int life;
	public int damage;
	public int armor;
	
	
	public Entity(BasePanel panel) {
		this.panel = panel;
	}
	
	public void setAction() {
		
	}

	public void chooseImage() {

	}

	public boolean isAttackingFighter() {
        return false;
    }

	public boolean isAttackingArcher(){
		return false;
	}

	public void update() {
		
		setAction();
		chooseImage();

		// THE GAME WORKS WITHOUT THE BELOW CODE
		// TODO FIND OUT WHY, CAN WE DELETE THESE?

		boolean contactPlayer;
		if (panel instanceof HallPanel p) {
			collisionOn = false;
			p.getCollisionCheckerForHall().checkTile(this);
			p.getCollisionCheckerForHall().checkObject(this, false);
			p.getCollisionCheckerForHall().checkEntity(this, panel.getMonsters());
			contactPlayer = p.getCollisionCheckerForHall().checkPlayer(this);
		}

		else if (panel instanceof HomePanel p) {
			collisionOn = false;
			System.out.println("Checking for home...");
			p.getCollisionCheckerForHome().checkTile(this);
			contactPlayer = p.getCollisionCheckerForHome().checkPlayer(this);
			System.out.println(contactPlayer);

		}

		else {
			collisionOn = false;
			panel.getCollisionChecker().checkTile(this);
			panel.getCollisionChecker().checkObject(this, false);
			panel.getCollisionChecker().checkEntity(this, panel.getMonsters());
			contactPlayer = panel.getCollisionChecker().checkPlayer(this);
		}

		// This doesn't do anything (?)
		if (this.type == 2 && contactPlayer) {
			if(!panel.getPlayer().invincible) {
				if(!panel.getPlayer().invincibleCloak) {
					panel.getPlayer().life -= 1;
					if (panel instanceof HallPanel) {
						((HallPanel) panel).playSE(3);
					}
					panel.getPlayer().invincible = true;
				}
			}
		}

		if (life == 0) {
			if (panel instanceof HallPanel) {
				System.out.println("Life = 0");
			}
			System.out.println("SWITCH1");
			panel.getViewManager().switchTo("HomePanel", true);
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


	}
	
	public void draw(Graphics2D g2) {
		
		BufferedImage image = null;

		int tempScreenX = worldX;
		int tempScreenY = worldY;

		animateDirection();
		animate();

		image = getAnimateImage();

		tempScreenX = getBufferX();
		tempScreenY = getBufferY();

		if (image == null) {
			System.out.println(this.name);
		}
        g2.drawImage(image, tempScreenX, tempScreenY, image.getWidth(), image.getHeight(), null);

    }

	public void animate() {

	}

	public int getBufferX() {
        return 0;
    }

	public int getBufferY() {
		return 0;
	}

	public BufferedImage getAnimateImage() {
        return null;
    }

	public void animateDirection() {

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
