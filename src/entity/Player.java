package entity;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
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
	public boolean attacking;
	private boolean firstAttackCall = true; // Flag to track the first call of attacking()
	private int tempScreenXLeft;

	private boolean aoeActive = false;   // Tracks if AoE is active
	private int aoeDuration = 20;       // Duration of AoE in frames
	private int aoeCounter = 0;         // Timer for AoE
	private int aoeRadius = 100;        // AoE radius
	private int aoeDamage = 2;          // AoE damage

	public int level;
	public int maxLevel;
	public int xpCurrent;
	public int xpMax;

	public Fireball fireball;

	private Player(BasePanel panel) {
		super(panel);
		System.out.println("PLAYER CREATED");
		screenX = BasePanel.screenWidth/2 - (BasePanel.tileSize/2);
		screenY = BasePanel.screenHeight/2 - (BasePanel.tileSize/2);
		
		solidArea = new Rectangle(8, 16, 32, 32);
		solidAreaDefaultX = 8;
		solidAreaDefaultY = 16;

		this.attackArea.width = 48;
		this.attackArea.height = 48;
		
		setDefaultValues();
		getPlayerImage();
		getIronArmorTorso();
		getLeatherArmorTorso();
		getIronArmorHead();
		getLeatherArmorHead();
		getPlayerImageAttacking();
		getIronArmorTorsoKnifeAttacking();
		getLeatherArmorTorsoKnifeAttacking();
		getIronArmorHeadKnifeAttacking();
		getLeatherArmorHeadKnifeAttacking();
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
		defaultSpeed = 4;
		speed = defaultSpeed;
		armor = 0;
		gold = 10;
		direction = "down";
		knockbackValue = 10;
		maxLife = 12;
		life = maxLife;
		xpCurrent = 0;
		xpMax = 100;
		level = 1;
		maxLevel = 10;
		
	}

	public void getPlayerImage() {
		try {
			up_walking1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_1.png")));
			up_walking2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_2.png")));
			up_walking3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_3.png")));
			up_walking4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_4.png")));
			up_walking5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_5.png")));
			up_walking6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_6.png")));
			up_walking7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_7.png")));
			up_walking8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_8.png")));
			up_walking9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_up_9.png")));

			down_walking1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_1.png")));
			down_walking2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_2.png")));
			down_walking3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_3.png")));
			down_walking4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_4.png")));
			down_walking5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_5.png")));
			down_walking6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_6.png")));
			down_walking7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_7.png")));
			down_walking8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_8.png")));
			down_walking9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_down_9.png")));

			left_walking1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_1.png")));
			left_walking2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_2.png")));
			left_walking3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_3.png")));
			left_walking4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_4.png")));
			left_walking5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_5.png")));
			left_walking6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_6.png")));
			left_walking7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_7.png")));
			left_walking8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_8.png")));
			left_walking9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_left_9.png")));

			right_walking1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_1.png")));
			right_walking2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_2.png")));
			right_walking3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_3.png")));
			right_walking4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_4.png")));
			right_walking5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_5.png")));
			right_walking6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_6.png")));
			right_walking7 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_7.png")));
			right_walking8 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_8.png")));
			right_walking9 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterWalk/main_right_9.png")));

		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	public void getPlayerImageAttacking() {
		try {
			up_attacking1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_up_1.png")));
			up_attacking2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_up_2.png")));
			up_attacking3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_up_3.png")));
			up_attacking4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_up_4.png")));
			up_attacking5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_up_5.png")));

			down_attacking1 = setup("/res/player/MainCharacterAttackKnife/attack_down_1", 80, 64);
			down_attacking2 = setup("/res/player/MainCharacterAttackKnife/attack_down_2", 80, 64);
			down_attacking3 = setup("/res/player/MainCharacterAttackKnife/attack_down_3", 80, 64);
			down_attacking4 = setup("/res/player/MainCharacterAttackKnife/attack_down_4", 80, 64);
			down_attacking5 = setup("/res/player/MainCharacterAttackKnife/attack_down_5", 80, 64);
//			down_attacking1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_down_1.png")));
//			down_attacking2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_down_2.png")));
//			down_attacking3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_down_3.png")));
//			down_attacking4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_down_4.png")));
//			down_attacking5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_down_5.png")));

			left_attacking1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_left_1.png")));
			left_attacking2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_left_2.png")));
			left_attacking3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_left_3.png")));
			left_attacking4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_left_4.png")));
			left_attacking5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_left_5.png")));

			right_attacking1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_right_1.png")));
			right_attacking2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_right_2.png")));
			right_attacking3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_right_3.png")));
			right_attacking4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_right_4.png")));
			right_attacking5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/MainCharacterAttackKnife/attack_right_5.png")));

		} catch (IOException e) {
			e.printStackTrace();
		}
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
		up6 = up_walking6;
		up7 = up_walking7;
		up8 = up_walking8;
		up9 = up_walking9;

		down1 = down_walking1;
		down2 = down_walking2;
		down3 = down_walking3;
		down4 = down_walking4;
		down5 = down_walking5;
		down6 = down_walking6;
		down7 = down_walking7;
		down8 = down_walking8;
		down9 = down_walking9;

		left1 = left_walking1;
		left2 = left_walking2;
		left3 = left_walking3;
		left4 = left_walking4;
		left5 = left_walking5;
		left6 = left_walking6;
		left7 = left_walking7;
		left8 = left_walking8;
		left9 = left_walking9;

		right1 = right_walking1;
		right2 = right_walking2;
		right3 = right_walking3;
		right4 = right_walking4;
		right5 = right_walking5;
		right6 = right_walking6;
		right7 = right_walking7;
		right8 = right_walking8;
		right9 = right_walking9;
	}

	public void chooseImage() {

		if (attacking) {
			chooseImageAttacking();
		}
		else {
			chooseImageWalking();
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

	public void getIronArmorTorsoKnifeAttacking() {
		try {
			// UP direction
			ironarmor_torso_knife_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_up_1.png")));
			ironarmor_torso_knife_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_up_2.png")));
			ironarmor_torso_knife_up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_up_3.png")));
			ironarmor_torso_knife_up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_up_4.png")));
			ironarmor_torso_knife_up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_up_5.png")));
			ironarmor_torso_knife_up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_up_6.png")));


			// DOWN direction
			ironarmor_torso_knife_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_down_1.png")));
			ironarmor_torso_knife_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_down_2.png")));
			ironarmor_torso_knife_down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_down_3.png")));
			ironarmor_torso_knife_down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_down_4.png")));
			ironarmor_torso_knife_down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_down_5.png")));
			ironarmor_torso_knife_down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_down_6.png")));

			// LEFT direction
			ironarmor_torso_knife_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_left_1.png")));
			ironarmor_torso_knife_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_left_2.png")));
			ironarmor_torso_knife_left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_left_3.png")));
			ironarmor_torso_knife_left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_left_4.png")));
			ironarmor_torso_knife_left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_left_5.png")));
			ironarmor_torso_knife_left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_left_6.png")));

			// RIGHT direction
			ironarmor_torso_knife_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_right_1.png")));
			ironarmor_torso_knife_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_right_2.png")));
			ironarmor_torso_knife_right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_right_3.png")));
			ironarmor_torso_knife_right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_right_4.png")));
			ironarmor_torso_knife_right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_right_5.png")));
			ironarmor_torso_knife_right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorTorsoKnife/ironarmor_torso_knife_right_6.png")));

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

	public void getIronArmorHeadKnifeAttacking() {
		try {
			ironarmor_head_knife_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_up_1.png")));
			ironarmor_head_knife_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_up_2.png")));
			ironarmor_head_knife_up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_up_3.png")));
			ironarmor_head_knife_up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_up_4.png")));
			ironarmor_head_knife_up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_up_5.png")));
			ironarmor_head_knife_up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_up_6.png")));

			ironarmor_head_knife_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_down_1.png")));
			ironarmor_head_knife_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_down_2.png")));
			ironarmor_head_knife_down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_down_3.png")));
			ironarmor_head_knife_down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_down_4.png")));
			ironarmor_head_knife_down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_down_5.png")));
			ironarmor_head_knife_down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_down_6.png")));

			ironarmor_head_knife_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_left_1.png")));
			ironarmor_head_knife_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_left_2.png")));
			ironarmor_head_knife_left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_left_3.png")));
			ironarmor_head_knife_left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_left_4.png")));
			ironarmor_head_knife_left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_left_5.png")));
			ironarmor_head_knife_left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_left_6.png")));

			ironarmor_head_knife_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_right_1.png")));
			ironarmor_head_knife_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_right_2.png")));
			ironarmor_head_knife_right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_right_3.png")));
			ironarmor_head_knife_right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_right_4.png")));
			ironarmor_head_knife_right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_right_5.png")));
			ironarmor_head_knife_right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/IronArmorHeadKnife/ironarmor_head_knife_right_6.png")));

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

	public void getLeatherArmorTorsoKnifeAttacking() {
		try {
			// UP direction
			leatherarmor_torso_knife_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_up_1.png")));
			leatherarmor_torso_knife_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_up_2.png")));
			leatherarmor_torso_knife_up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_up_3.png")));
			leatherarmor_torso_knife_up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_up_4.png")));
			leatherarmor_torso_knife_up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_up_5.png")));
			leatherarmor_torso_knife_up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_up_6.png")));

			// DOWN direction
			leatherarmor_torso_knife_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_down_1.png")));
			leatherarmor_torso_knife_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_down_2.png")));
			leatherarmor_torso_knife_down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_down_3.png")));
			leatherarmor_torso_knife_down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_down_4.png")));
			leatherarmor_torso_knife_down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_down_5.png")));
			leatherarmor_torso_knife_down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_down_6.png")));

			// LEFT direction
			leatherarmor_torso_knife_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_left_1.png")));
			leatherarmor_torso_knife_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_left_2.png")));
			leatherarmor_torso_knife_left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_left_3.png")));
			leatherarmor_torso_knife_left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_left_4.png")));
			leatherarmor_torso_knife_left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_left_5.png")));
			leatherarmor_torso_knife_left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_left_6.png")));

			// RIGHT direction
			leatherarmor_torso_knife_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_right_1.png")));
			leatherarmor_torso_knife_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_right_2.png")));
			leatherarmor_torso_knife_right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_right_3.png")));
			leatherarmor_torso_knife_right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_right_4.png")));
			leatherarmor_torso_knife_right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_right_5.png")));
			leatherarmor_torso_knife_right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorTorsoKnife/leatherarmor_torso_knife_right_6.png")));

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

	public void getLeatherArmorHeadKnifeAttacking() {
		try {
			// UP direction
			leatherarmor_head_knife_up1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_up_1.png")));
			leatherarmor_head_knife_up2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_up_2.png")));
			leatherarmor_head_knife_up3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_up_3.png")));
			leatherarmor_head_knife_up4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_up_4.png")));
			leatherarmor_head_knife_up5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_up_5.png")));
			leatherarmor_head_knife_up6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_up_6.png")));

			// DOWN direction
			leatherarmor_head_knife_down1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_down_1.png")));
			leatherarmor_head_knife_down2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_down_2.png")));
			leatherarmor_head_knife_down3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_down_3.png")));
			leatherarmor_head_knife_down4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_down_4.png")));
			leatherarmor_head_knife_down5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_down_5.png")));
			leatherarmor_head_knife_down6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_down_6.png")));

			// LEFT direction
			leatherarmor_head_knife_left1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_left_1.png")));
			leatherarmor_head_knife_left2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_left_2.png")));
			leatherarmor_head_knife_left3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_left_3.png")));
			leatherarmor_head_knife_left4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_left_4.png")));
			leatherarmor_head_knife_left5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_left_5.png")));
			leatherarmor_head_knife_left6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_left_6.png")));

			// RIGHT direction
			leatherarmor_head_knife_right1 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_right_1.png")));
			leatherarmor_head_knife_right2 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_right_2.png")));
			leatherarmor_head_knife_right3 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_right_3.png")));
			leatherarmor_head_knife_right4 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_right_4.png")));
			leatherarmor_head_knife_right5 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_right_5.png")));
			leatherarmor_head_knife_right6 = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/player/LeatherArmorHeadKnife/leatherarmor_head_knife_right_6.png")));

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

			chooseImage();

			AoEDamage(hallPanel);

			if (attacking) {
				attacking();
			}

            else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed) {
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
				}
			} else {
				spriteNum = 1;
			}
		}

		else if (panel instanceof HomePanel homePanel) {

			chooseImage();

			if (attacking) {
				attacking();
			}

			else if (keyH.upPressed || keyH.downPressed || keyH.leftPressed || keyH.rightPressed || keyH.spacePressed) {
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
				}
			} else {
				spriteNum = 1;
				spriteCounter = 0;
			}
		}
	}

	public void removeKeyListener(BaseKeyListener keyListener) {
		if (keyListener == this.keyH) {
			this.keyH = null;
		}
	}

	public void attacking() {

		Random random = new Random();
		int soundToPlay = random.nextBoolean() ? 5 : 6;

		if (panel instanceof HallPanel hallPanel) {
			if (!hallPanel.attackSoundPlayed) {
				hallPanel.playSE(soundToPlay);
				hallPanel.attackSoundPlayed = true; // Set flag to prevent replay
			}
		}

		if (panel instanceof HomePanel homePanel) {
			if (!homePanel.attackSoundPlayed) {
				homePanel.playSE(soundToPlay);
				homePanel.attackSoundPlayed = true; // Set flag to prevent replay
			}
		}

		if (firstAttackCall) {
			spriteNum = 1;
			firstAttackCall = false;
		}

		spriteCounter++;
		if (spriteCounter > 3) {
			spriteNum++;

			if (spriteNum == 2) {

				if (panel instanceof HallPanel hallPanel) {

					// Save the current worldX, worldY, solidArea
					int currentWorldX = screenX;
					int currentWorldY = screenY;
					int solidAreaWidth = solidArea.width;
					int solidAreaHeight = solidArea.height;

					// Adjust player's worldX/Y for the attackArea
					switch (direction) {
						case "up" -> screenY -= attackArea.height;
						case "down" -> screenY += attackArea.height;
						case "left" -> screenX -= attackArea.width;
						case "right" -> screenX += attackArea.width;
					}

					solidArea.width = attackArea.width;
					solidArea.height = attackArea.height;

					// Check monster collision with the updated worldX, worldY, and solidArea
					int monsterIndex = hallPanel.getCollisionCheckerForHall().checkEntity(this, hallPanel.getMonsters());

					// Apply damage to the collided monster
					if (monsterIndex != 999) {
						damageMonster(monsterIndex, this);
					}

					// Restore the original data
					screenX = currentWorldX;
					screenY = currentWorldY;
					solidArea.width = solidAreaWidth;
					solidArea.height = solidAreaHeight;
				}
			}

			if (spriteNum > 5) {
				spriteNum = 1;
				attacking = false;
				firstAttackCall = true;
			}
			spriteCounter = 0;
		}
	}

	public void knockback(Entity entity, Entity hitEntity, int knockbackValue) {
		if (hitEntity instanceof Fireball fireball) {
			entity.tempDirection = entity.direction;
			entity.direction = fireball.direction;
			entity.speed += knockbackValue;
			entity.knockback = true;
		} else {
			entity.tempDirection = entity.direction;
			entity.direction = direction;
			entity.speed += knockbackValue;
			entity.knockback = true;
		}

	}

	public void aoeKnockback(Player player, Entity monster, int knockbackDistance) {
		int deltaX = monster.worldX - player.screenX;
		int deltaY = monster.worldY - player.screenY;

		if (deltaX == 0 && deltaY == 0) return;

		double magnitude = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		double normalizedX = deltaX / magnitude;
		double normalizedY = deltaY / magnitude;

		monster.worldX += (int) (normalizedX * knockbackDistance);
		monster.worldY += (int) (normalizedY * knockbackDistance);

		monster.tempDirection = monster.direction;

		if (Math.abs(normalizedX) > Math.abs(normalizedY)) {
			monster.direction = (normalizedX > 0) ? "right" : "left";
		} else {
			monster.direction = (normalizedY > 0) ? "down" : "up";
		}
		monster.knockback = true;

		monster.speed += 10;
	}

	public void triggerAoE() {
		if (!aoeActive) {
			aoeActive = true;
			aoeCounter = 0;
		}
	}

	public void shootFireball() {
		attacking = true;
		this.collisionOn = true;

		String fireballDirection = this.direction;

		fireball = new Fireball(panel, screenX, screenY, fireballDirection);
	}

	public void AoEDamage(HallPanel hallPanel) {
		if (aoeActive) {
			aoeCounter++;

			for (Entity monster : hallPanel.getMonsters()) {
				if (monster != null && !monster.invincible) {
					int monsterCenterX = monster.worldX + monster.solidArea.width / 2;
					int monsterCenterY = monster.worldY + monster.solidArea.height / 2;

					double distance = Math.sqrt(Math.pow(monsterCenterX - screenX + 16, 2) + Math.pow(monsterCenterY - screenY + 16, 2));

					if (distance <= aoeRadius) {
						monster.life -= aoeDamage;
						monster.invincible = true;
						aoeKnockback(this, monster, 10);
						if (monster.life <= 0) {
							addXp(20);
							monster.alive = false;
							hallPanel.getHallMonsters().remove(monster);
						}
					} else {
//						System.out.println(distance);
					}
				}
			}
			// End AoE after duration
			if (aoeCounter >= aoeDuration) {
				hallPanel.groundSlamActive = false;
				aoeActive = false;
			}
		}
	}

	public void draw(Graphics2D g2) {

		BufferedImage image = null;
		BufferedImage torsoArmorImage = null;
		BufferedImage headArmorImage = null;
		tempScreenX = screenX;
		tempScreenY = screenY;
		tempScreenXLeft = screenX;
		tempScreenYDown = screenY;

		// Select base image and torso armor image
		switch (direction) {
			case "up" -> {
				if (attacking) {
					tempScreenY -= 24;
					tempScreenYDown -= 8;
				}
				switch (spriteNum) {
					case 1 -> {
						image = up1;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_up1;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_up1;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_up1;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_up1;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up1;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up1;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up1;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_up1;
						}
					}
					case 2 -> {
						image = up2;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_up2;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_up2;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_up2;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_up2;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up2;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up2;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up2;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_up2;
						}
					}
					case 3 -> {
						image = up3;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_up3;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_up3;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_up3;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_up3;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up3;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up3;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up3;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_up3;
						}
					}
					case 4 -> {
						image = up4;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_up4;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_up4;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_up4;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_up4;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up4;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up4;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up4;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_up4;
						}
					}
					case 5 -> {
						image = up5;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_up5;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_up5;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_up5;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_up5;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up5;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up5;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up5;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_up5;
						}
					}
					case 6 -> {
						image = up6;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_up6;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_up6;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_up6;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_up6;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up6;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up6;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up6;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_up6;
						}
					}
					case 7 -> {
						image = up7;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_up7;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_up7;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_up7;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_up7;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up7;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up7;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up7;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_up7;
						}
					}
					case 8 -> {
						image = up8;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_up8;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_up8;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_up8;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_up8;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up8;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up8;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up8;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_up8;
						}
					}
					case 9 -> {
						image = up9;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_up9;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_up9;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_up9;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_up9;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_up9;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_up9;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_up9;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_up9;
						}
					}
				}
			}
			case "down" -> {
				switch (spriteNum) {
					case 1 -> {
						image = down1;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_down1;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_down1;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_down1;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_down1;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down1;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down1;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down1;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_down1;
						}
					}
					case 2 -> {
						image = down2;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_down2;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_down2;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_down2;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_down2;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down2;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down2;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down2;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_down2;
						}
					}
					case 3 -> {
						image = down3;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_down3;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_down3;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_down3;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_down3;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down3;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down3;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down3;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_down3;
						}
					}
					case 4 -> {
						image = down4;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_down4;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_down4;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_down4;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_down4;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down4;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down4;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down4;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_down4;
						}
					}
					case 5 -> {
						image = down5;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_down5;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_down5;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_down5;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_down5;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down5;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down5;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down5;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_down5;
						}
					}
					case 6 -> {
						image = down6;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_down6;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_down6;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_down6;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_down6;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down6;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down6;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down6;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_down6;
						}
					}
					case 7 -> {
						image = down7;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_down7;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_down7;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_down7;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_down7;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down7;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down7;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down7;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_down7;
						}
					}
					case 8 -> {
						image = down8;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_down8;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_down8;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_down8;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_down8;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down8;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down8;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down8;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_down8;
						}
					}
					case 9 -> {
						image = down9;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_down9;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_down9;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_down9;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_down9;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_down9;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_down9;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_down9;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_down9;
						}
					}
				}
			}
			case "left" -> {
				if (attacking) {
					tempScreenX -= 48;
					tempScreenXLeft -= 8;
				}
				switch (spriteNum) {
					case 1 -> {
						image = left1;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_left1;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_left1;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_left1;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_left1;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left1;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left1;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left1;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_left1;
						}
					}
					case 2 -> {
						image = left2;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_left2;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_left2;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_left2;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_left2;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left2;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left2;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left2;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_left2;
						}
					}
					case 3 -> {
						image = left3;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_left3;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_left3;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_left3;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_left3;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left3;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left3;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left3;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_left3;
						}
					}
					case 4 -> {
						image = left4;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_left4;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_left4;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_left4;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_left4;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left4;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left4;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left4;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_left4;
						}
					}
					case 5 -> {
						image = left5;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_left5;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_left5;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_left5;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_left5;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left5;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left5;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left5;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_left5;
						}
					}
					case 6 -> {
						image = left6;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_left6;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_left6;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_left6;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_left6;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left6;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left6;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left6;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_left6;
						}
					}
					case 7 -> {
						image = left7;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_left7;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_left7;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_left7;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_left7;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left7;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left7;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left7;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_left7;
						}
					}
					case 8 -> {
						image = left8;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_left8;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_left8;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_left8;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_left8;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left8;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left8;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left8;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_left8;
						}
					}
					case 9 -> {
						image = left9;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_left9;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_left9;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_left9;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_left9;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_left9;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_left9;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_left9;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_left9;
						}
					}
				}
			}
			case "right" -> {
				switch (spriteNum) {
					case 1 -> {
						image = right1;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_right1;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_right1;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_right1;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_right1;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right1;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right1;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right1;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_right1;
						}
					}
					case 2 -> {
						image = right2;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_right2;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_right2;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_right2;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_right2;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right2;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right2;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right2;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_right2;
						}
					}
					case 3 -> {
						image = right3;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_right3;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_right3;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_right3;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_right3;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right3;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right3;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right3;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_right3;
						}
					}
					case 4 -> {
						image = right4;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_right4;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_right4;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_right4;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_right4;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right4;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right4;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right4;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_right4;
						}
					}
					case 5 -> {
						image = right5;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_right5;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_right5;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_right5;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_right5;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right5;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right5;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right5;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_right5;
						}
					}
					case 6 -> {
						image = right6;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_right6;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_right6;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_right6;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_right6;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right6;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right6;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right6;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_right6;
						}
					}
					case 7 -> {
						image = right7;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_right7;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_right7;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_right7;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_right7;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right7;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right7;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right7;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_right7;
						}
					}
					case 8 -> {
						image = right8;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_right8;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_right8;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_right8;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_right8;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right8;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right8;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right8;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_right8;
						}
					}
					case 9 -> {
						image = right9;
						if (attacking) {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_knife_right9;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_knife_right9;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_knife_right9;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_knife_right9;
						} else {
							if (armorOnLeatherTorso) torsoArmorImage = leatherarmor_torso_right9;
							else if (armorOnIronTorso) torsoArmorImage = ironarmor_torso_right9;
							if (armorOnLeatherHead) headArmorImage = leatherarmor_head_right9;
							else if (armorOnIronHead) headArmorImage = ironarmor_head_right9;
						}
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
		g2.drawImage(image, tempScreenX, tempScreenY,
				(int) (image.getWidth() * 1.3),
				(int) (image.getHeight() * 1.3),
				null);

//		 Draw blood effect
//		if (panel instanceof HallPanel hallPanel) {
//			hallPanel.burningParticle.draw(g2, hallPanel);
//			hallPanel.burningParticle.updateAnimation();
//		}

		// Draw torso armor image
		if (torsoArmorImage != null) {
			int scaledWidth = (int) (BasePanel.tileSize * 1.3);
			int scaledHeight = (int) (BasePanel.tileSize * 1.3);
			g2.drawImage(torsoArmorImage, tempScreenXLeft, tempScreenYDown, scaledWidth, scaledHeight, null);
		}

		// Draw head armor image
		if (headArmorImage != null) {
			int scaledWidth = (int) (BasePanel.tileSize * 1.3);
			int scaledHeight = (int) (BasePanel.tileSize * 1.3);
			g2.drawImage(headArmorImage, tempScreenXLeft, tempScreenYDown - 2, scaledWidth, scaledHeight, null);
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

	public void addXp(int xp) {

		if (xpCurrent + xp >= xpMax) {
			xpCurrent = 0;
			if (level < maxLevel) {
				level += 1;
				if (panel instanceof HallPanel hallPanel) {
					hallPanel.playSE(10);
				}
			}
		} else {
			xpCurrent += xp;
		}
	}

	public void damagePlayer(int attackDamage) {

		int finalDamage = Math.max(attackDamage - armor, 0);

		panel.getPlayer().life -= finalDamage;

		if (panel instanceof HallPanel) {
			((HallPanel) panel).playSE(3);
		}

		panel.getPlayer().invincible = true;

	}

	public void damageMonster(int i, Entity entity) {
		if (i != 999) {
			if (panel instanceof HallPanel hallPanel) {
				if (!hallPanel.getMonsters()[i].invincible) {
					hallPanel.playSE(7);
					int randomSE = new Random().nextInt(3) + 13;
					hallPanel.playSE(randomSE);

					hallPanel.getMonsters()[i].life -= 1;
					hallPanel.getMonsters()[i].invincible = true;
					hallPanel.getMonsters()[i].damageReceived = true;

					if (entity instanceof Fireball) {
						knockback(hallPanel.getMonsters()[i], entity, fireball.knockbackValue);
					}  else {
						knockback(hallPanel.getMonsters()[i], this, knockbackValue);
					}
				}
			}
		}
	}
}







