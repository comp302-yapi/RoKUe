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
	public boolean armorOnIronHead, armorOnLeatherHead;

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
		getIronArmorHead();
		getLeatherArmorHead();
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
		gold = 100000;
		direction = "down";
		
		maxLife = 12;
		life = maxLife;
		
	}

	public void getPlayerImage() {
		try {
			up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_1.png")));
			up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_2.png")));
			up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_3.png")));
			up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_4.png")));
			up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_5.png")));
			up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_6.png")));
			up7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_7.png")));
			up8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_8.png")));
			up9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_9.png")));

			down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_1.png")));
			down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_2.png")));
			down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_3.png")));
			down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_4.png")));
			down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_5.png")));
			down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_6.png")));
			down7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_7.png")));
			down8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_8.png")));
			down9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_9.png")));

			left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_1.png")));
			left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_2.png")));
			left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_3.png")));
			left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_4.png")));
			left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_5.png")));
			left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_6.png")));
			left7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_7.png")));
			left8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_8.png")));
			left9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_9.png")));

			right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_1.png")));
			right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_2.png")));
			right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_3.png")));
			right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_4.png")));
			right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_5.png")));
			right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_6.png")));
			right7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_7.png")));
			right8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_8.png")));
			right9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_9.png")));

		} catch(IOException e) {
			e.printStackTrace();
		}
	}


	// IRON ARMOR
	public void getIronArmorTorso() {
		try {
			ironarmor_torso_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_up_1.png")));
			ironarmor_torso_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_up_2.png")));
			ironarmor_torso_up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_up_3.png")));
			ironarmor_torso_up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_up_4.png")));
			ironarmor_torso_up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_up_5.png")));
			ironarmor_torso_up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_up_6.png")));
			ironarmor_torso_up7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_up_7.png")));
			ironarmor_torso_up8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_up_8.png")));
			ironarmor_torso_up9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_up_9.png")));

			ironarmor_torso_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_down_1.png")));
			ironarmor_torso_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_down_2.png")));
			ironarmor_torso_down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_down_3.png")));
			ironarmor_torso_down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_down_4.png")));
			ironarmor_torso_down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_down_5.png")));
			ironarmor_torso_down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_down_6.png")));
			ironarmor_torso_down7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_down_7.png")));
			ironarmor_torso_down8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_down_8.png")));
			ironarmor_torso_down9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_down_9.png")));

			ironarmor_torso_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_left_1.png")));
			ironarmor_torso_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_left_2.png")));
			ironarmor_torso_left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_left_3.png")));
			ironarmor_torso_left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_left_4.png")));
			ironarmor_torso_left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_left_5.png")));
			ironarmor_torso_left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_left_6.png")));
			ironarmor_torso_left7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_left_7.png")));
			ironarmor_torso_left8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_left_8.png")));
			ironarmor_torso_left9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_left_9.png")));

			ironarmor_torso_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_right_1.png")));
			ironarmor_torso_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_right_2.png")));
			ironarmor_torso_right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_right_3.png")));
			ironarmor_torso_right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_right_4.png")));
			ironarmor_torso_right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_right_5.png")));
			ironarmor_torso_right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_right_6.png")));
			ironarmor_torso_right7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_right_7.png")));
			ironarmor_torso_right8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_right_8.png")));
			ironarmor_torso_right9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorTorso/ironarmor_torso_right_9.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getIronArmorHead() {
		try {
			ironarmor_head_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_up_1.png")));
			ironarmor_head_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_up_2.png")));
			ironarmor_head_up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_up_3.png")));
			ironarmor_head_up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_up_4.png")));
			ironarmor_head_up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_up_5.png")));
			ironarmor_head_up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_up_6.png")));
			ironarmor_head_up7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_up_7.png")));
			ironarmor_head_up8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_up_8.png")));
			ironarmor_head_up9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_up_9.png")));

			ironarmor_head_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_down_1.png")));
			ironarmor_head_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_down_2.png")));
			ironarmor_head_down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_down_3.png")));
			ironarmor_head_down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_down_4.png")));
			ironarmor_head_down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_down_5.png")));
			ironarmor_head_down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_down_6.png")));
			ironarmor_head_down7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_down_7.png")));
			ironarmor_head_down8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_down_8.png")));
			ironarmor_head_down9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_down_9.png")));

			ironarmor_head_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_left_1.png")));
			ironarmor_head_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_left_2.png")));
			ironarmor_head_left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_left_3.png")));
			ironarmor_head_left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_left_4.png")));
			ironarmor_head_left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_left_5.png")));
			ironarmor_head_left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_left_6.png")));
			ironarmor_head_left7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_left_7.png")));
			ironarmor_head_left8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_left_8.png")));
			ironarmor_head_left9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_left_9.png")));

			ironarmor_head_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_right_1.png")));
			ironarmor_head_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_right_2.png")));
			ironarmor_head_right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_right_3.png")));
			ironarmor_head_right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_right_4.png")));
			ironarmor_head_right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_right_5.png")));
			ironarmor_head_right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_right_6.png")));
			ironarmor_head_right7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_right_7.png")));
			ironarmor_head_right8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_right_8.png")));
			ironarmor_head_right9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainIronArmorHead/ironarmor_head_right_9.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	// LEATHER ARMOR
	public void getLeatherArmorTorso() {
		try {
			leatherarmor_torso_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_up_1.png")));
			leatherarmor_torso_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_up_2.png")));
			leatherarmor_torso_up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_up_3.png")));
			leatherarmor_torso_up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_up_4.png")));
			leatherarmor_torso_up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_up_5.png")));
			leatherarmor_torso_up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_up_6.png")));
			leatherarmor_torso_up7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_up_7.png")));
			leatherarmor_torso_up8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_up_8.png")));
			leatherarmor_torso_up9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_up_9.png")));

			leatherarmor_torso_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_down_1.png")));
			leatherarmor_torso_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_down_2.png")));
			leatherarmor_torso_down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_down_3.png")));
			leatherarmor_torso_down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_down_4.png")));
			leatherarmor_torso_down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_down_5.png")));
			leatherarmor_torso_down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_down_6.png")));
			leatherarmor_torso_down7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_down_7.png")));
			leatherarmor_torso_down8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_down_8.png")));
			leatherarmor_torso_down9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_down_9.png")));

			leatherarmor_torso_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_left_1.png")));
			leatherarmor_torso_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_left_2.png")));
			leatherarmor_torso_left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_left_3.png")));
			leatherarmor_torso_left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_left_4.png")));
			leatherarmor_torso_left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_left_5.png")));
			leatherarmor_torso_left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_left_6.png")));
			leatherarmor_torso_left7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_left_7.png")));
			leatherarmor_torso_left8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_left_8.png")));
			leatherarmor_torso_left9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_left_9.png")));

			leatherarmor_torso_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_right_1.png")));
			leatherarmor_torso_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_right_2.png")));
			leatherarmor_torso_right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_right_3.png")));
			leatherarmor_torso_right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_right_4.png")));
			leatherarmor_torso_right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_right_5.png")));
			leatherarmor_torso_right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_right_6.png")));
			leatherarmor_torso_right7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_right_7.png")));
			leatherarmor_torso_right8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_right_8.png")));
			leatherarmor_torso_right9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorTorso/leatherarmor_right_9.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void getLeatherArmorHead() {
		try {
			leatherarmor_head_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_up_1.png")));
			leatherarmor_head_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_up_2.png")));
			leatherarmor_head_up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_up_3.png")));
			leatherarmor_head_up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_up_4.png")));
			leatherarmor_head_up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_up_5.png")));
			leatherarmor_head_up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_up_6.png")));
			leatherarmor_head_up7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_up_7.png")));
			leatherarmor_head_up8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_up_8.png")));
			leatherarmor_head_up9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_up_9.png")));

			leatherarmor_head_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_down_1.png")));
			leatherarmor_head_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_down_2.png")));
			leatherarmor_head_down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_down_3.png")));
			leatherarmor_head_down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_down_4.png")));
			leatherarmor_head_down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_down_5.png")));
			leatherarmor_head_down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_down_6.png")));
			leatherarmor_head_down7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_down_7.png")));
			leatherarmor_head_down8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_down_8.png")));
			leatherarmor_head_down9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_down_9.png")));

			leatherarmor_head_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_left_1.png")));
			leatherarmor_head_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_left_2.png")));
			leatherarmor_head_left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_left_3.png")));
			leatherarmor_head_left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_left_4.png")));
			leatherarmor_head_left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_left_5.png")));
			leatherarmor_head_left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_left_6.png")));
			leatherarmor_head_left7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_left_7.png")));
			leatherarmor_head_left8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_left_8.png")));
			leatherarmor_head_left9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_left_9.png")));

			leatherarmor_head_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_right_1.png")));
			leatherarmor_head_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_right_2.png")));
			leatherarmor_head_right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_right_3.png")));
			leatherarmor_head_right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_right_4.png")));
			leatherarmor_head_right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_right_5.png")));
			leatherarmor_head_right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_right_6.png")));
			leatherarmor_head_right7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_right_7.png")));
			leatherarmor_head_right8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_right_8.png")));
			leatherarmor_head_right9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainLeatherArmorHead/leatherarmor_head_right_9.png")));
		} catch (IOException e) {
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

	public void wearArmorIronHead() {
		armorOnLeatherHead = false;
		armorOnIronHead = true;
	}

	public void wearArmorLeatherHead() {
		armorOnIronHead = false;
		armorOnLeatherHead = true;
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
				if (spriteCounter > 3) {
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
				if (spriteCounter > 3) {
					spriteNum++;
					if (spriteNum > 9) { // Reset after 9
						spriteNum = 1;
					}
					spriteCounter = 0; // Reset counter after switching sprite
				}}

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
				if (spriteCounter > 3) {
					spriteNum++;
					if (spriteNum > 9) { // Reset after 9
						spriteNum = 1;
					}
					spriteCounter = 0; // Reset counter after switching sprite
				}}

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
		BufferedImage headArmorImage = null;

		// Select base image and torso armor image
		switch (direction) {
			case "up" -> {
				switch (spriteNum) {
					case 1 -> {
						image = up1;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up1;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up1;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up1;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_up1;
					}
					case 2 -> {
						image = up2;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up2;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up2;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up2;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_up2;
					}
					case 3 -> {
						image = up3;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up3;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up3;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up3;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_up3;
					}
					case 4 -> {
						image = up4;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up4;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up4;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up4;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_up4;
					}
					case 5 -> {
						image = up5;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up5;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up5;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up5;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_up5;
					}
					case 6 -> {
						image = up6;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up6;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up6;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up6;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_up6;
					}
					case 7 -> {
						image = up7;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up7;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up7;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up7;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_up7;
					}
					case 8 -> {
						image = up8;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up8;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up8;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up8;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_up8;
					}
					case 9 -> {
						image = up9;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up9;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up9;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up9;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_up9;
					}
				}
			}
			case "down" -> {
				switch (spriteNum) {
					case 1 -> {
						image = down1;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down1;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down1;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down1;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_down1;
					}
					case 2 -> {
						image = down2;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down2;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down2;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down2;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_down2;
					}
					case 3 -> {
						image = down3;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down3;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down3;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down3;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_down3;
					}
					case 4 -> {
						image = down4;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down4;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down4;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down4;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_down4;
					}
					case 5 -> {
						image = down5;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down5;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down5;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down5;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_down5;
					}
					case 6 -> {
						image = down6;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down6;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down6;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down6;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_down6;
					}
					case 7 -> {
						image = down7;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down7;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down7;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down7;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_down7;
					}
					case 8 -> {
						image = down8;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down8;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down8;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down8;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_down8;
					}
					case 9 -> {
						image = down9;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down9;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down9;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down9;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_down9;
					}
				}
			}
			case "left" -> {
				switch (spriteNum) {
					case 1 -> {
						image = left1;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left1;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left1;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left1;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_left1;
					}
					case 2 -> {
						image = left2;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left2;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left2;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left2;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_left2;
					}
					case 3 -> {
						image = left3;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left3;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left3;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left3;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_left3;
					}
					case 4 -> {
						image = left4;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left4;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left4;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left4;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_left4;
					}
					case 5 -> {
						image = left5;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left5;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left5;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left5;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_left5;
					}
					case 6 -> {
						image = left6;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left6;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left6;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left6;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_left6;
					}
					case 7 -> {
						image = left7;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left7;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left7;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left7;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_left7;
					}
					case 8 -> {
						image = left8;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left8;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left8;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left8;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_left8;
					}
					case 9 -> {
						image = left9;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left9;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left9;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left9;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_left9;
					}
				}
			}
			case "right" -> {
				switch (spriteNum) {
					case 1 -> {
						image = right1;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right1;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right1;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right1;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_right1;
					}
					case 2 -> {
						image = right2;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right2;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right2;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right2;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_right2;
					}
					case 3 -> {
						image = right3;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right3;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right3;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right3;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_right3;
					}
					case 4 -> {
						image = right4;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right4;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right4;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right4;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_right4;
					}
					case 5 -> {
						image = right5;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right5;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right5;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right5;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_right5;
					}
					case 6 -> {
						image = right6;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right6;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right6;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right6;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_right6;
					}
					case 7 -> {
						image = right7;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right7;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right7;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right7;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_right7;
					}
					case 8 -> {
						image = right8;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right8;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right8;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right8;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_right8;
					}
					case 9 -> {
						image = right9;
						if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right9;
						else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right9;
						if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right9;
						else if (armorOnIronHead) headArmorImage = ironarmor_head_right9;
					}
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

		if (headArmorImage != null) {
			g2.drawImage(headArmorImage, screenX, screenY-2, BasePanel.tileSize, BasePanel.tileSize, null);
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







