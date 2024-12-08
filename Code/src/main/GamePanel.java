package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;

public class GamePanel extends JPanel implements Runnable{

	// SCREEN SETTINGS
	final int originalTileSize = 16; // 16x16 tile
	final int scale = 3;
	
	public final int tileSize = originalTileSize * scale;
	public final int maxScreenCol = 40;
	public final int maxScreenRow = 20;
	public final int screenWidth = tileSize * maxScreenCol; // 1920p
	public final int screenHeight = tileSize * maxScreenRow; // 1056p
	
	// WORLD SETTINGS
	public final int maxWorldCol = 75;
	public final int maxWorldRow = 75;
	public final int worldWidth = tileSize * maxWorldCol;
	public final int worldHeight = tileSize * maxWorldRow;
	
	
	// FPS
	int FPS = 60;
	
	// SYSTEM
	TileManager tileM = new TileManager(this);
	KeyHandler keyH = new KeyHandler(this);
	Thread gameThread;
	public UI ui = new UI(this);
	public CollisionChecker cChecker = new CollisionChecker(this);
	public AssetSetter aSetter = new AssetSetter(this);
	
	// ENTITY AND OBJECT
	public Player player = new Player(this, keyH);
	public SuperObject obj[] = new SuperObject[10];
	public Entity monster[] = new Entity[20];

	
	// GAME STATE
	public int gameState;
	public int titleState = 0;
	public final int playState = 1;
	public final int pauseState = 2;
	
	
	public GamePanel() {
		
		this.setPreferredSize(new Dimension(screenWidth, screenHeight));
		this.setBackground(Color.black);
		this.setDoubleBuffered(true); // Enhances rendering performance
		this.addKeyListener(keyH);
		this.setFocusable(true);
		
	}
	
	public void setupGame() {
		
		aSetter.setObject();
		aSetter.spawnMonster();
		gameState = titleState;
	}

	public void startSession() {
		
		gameThread = new Thread(this);
		gameThread.start();
	}
	
	@Override
	public void run() {
		
		double drawInterval = 1000000000/FPS;
		double delta = 0;
		long lastTime = System.nanoTime();
		long currentTime;
		
		while(gameThread != null) {

			currentTime = System.nanoTime();
			
			delta += (currentTime - lastTime) / drawInterval;
			
			lastTime = currentTime;
			
			if (delta >= 1) {
				update();
				repaint();
				delta--;
			}
		
		}
		
	}
	
	public void update() {
	
		if (gameState == playState) {
			
			player.move();
			
			for (int i = 0; i < monster.length; i++) {
				
				if (monster[i] != null) {
					monster[i].update();
				}
			}
			
		} else if (gameState == pauseState) {
			// nothing
		}
	}
	
	public void paintComponent(Graphics g) { // repaint()
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D)g;
		
		// TITLE SCREEN
		if (gameState == titleState) {
			ui.draw(g2);

		} else {
			// TILE
			tileM.draw(g2);
			
			// OBJECTS
			for (int i = 0; i < obj.length; i++) {
				
				if (obj[i] != null) {
					obj[i].draw(g2, this);
				}
			}
			
			// PLAYER
			player.draw(g2);
			
			// MONSTER
			for (int i = 0; i < monster.length; i++) {
				
				if (monster[i] != null) {
					monster[i].draw(g2);
				}
			}
			
			// UI
			ui.draw(g2);
			
			g2.dispose();
		
		}
		
		}
	
	
}







