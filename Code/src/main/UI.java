package main;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.text.DecimalFormat;

import object.OBJ_Key;

public class UI {
	
	GamePanel gp;
	Graphics2D g2;
	Font arial_80B, arial_40, arial_20;
//	BufferedImage keyImage;
	public boolean messageOn = false;
	public String message = "";
	int messageCounter = 0; 
	public boolean gameFinished = false;
	public int commandNum = 0;

	
	public double playTime;
	DecimalFormat dFormat = new DecimalFormat("#0.00");
	
	public UI(GamePanel gp) {
		
		this.gp = gp;
		arial_80B = new Font("Arial", Font.BOLD, 80);
		arial_40 = new Font("Arial", Font.PLAIN, 40);
		arial_20 = new Font("Arial", Font.PLAIN, 20);
//		OBJ_Key key = new OBJ_Key();
//		keyImage = key.image;
	}
	
	public void showMessage(String text) {
		
		message = text;
		messageOn = true;
		
	}
	
	public void draw(Graphics2D g2) {
		
		this.g2 = g2;
		
		g2.setFont(arial_40);
		g2.setColor(Color.white);
		
		if (gp.gameState == gp.titleState) {
			drawTitleScreen();
			
		}
		
		if (gp.gameState == gp.playState) {
			// Do playState stuff later
		}
		
		if (gp.gameState == gp.pauseState) {
			drawPuaseScreen();
			
		}
	}
	
public void drawTitleScreen() {
		
		g2.setColor(new Color(62, 41, 52));
		g2.fillRect(0, 0, gp.screenWidth, gp.screenHeight);
		
		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
		String text = "RoKue Like";
		int x, y;
		x = getXforCenteredText(text) - gp.tileSize*2;
		y = gp.tileSize * 3;
		
		// SHADOW
		g2.setColor(new Color(40, 35, 38));
		g2.drawString(text, x+5, y+5);
		
		// MAIN COLOR
		g2.setColor(new Color(26, 17, 23));
		g2.drawString(text, x, y);
		
		// BLUE BOY IMAGE
		x = gp.screenWidth/2 - (gp.tileSize*2)/2 -(gp.tileSize*2);
		y += gp.tileSize*3;
		g2.drawImage(gp.monster[2].up1, x, y, gp.tileSize*2, gp.tileSize*2, null);
		
		// MENU
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 48F));
		
		text = "NEW GAME";
		x = getXforCenteredText(text) - gp.tileSize*2;
		y += gp.tileSize*5;
		g2.drawString(text, x, y);
		if (commandNum == 0) {
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		text = "LOAD GAME";
		x = getXforCenteredText(text)- gp.tileSize*2;
		y += gp.tileSize*3;
		g2.drawString(text, x, y);
		if (commandNum == 1) {
			g2.drawString(">", x - gp.tileSize, y);
		}
		
		text = "QUIT GAME";
		x = getXforCenteredText(text)- gp.tileSize*2;
		y += gp.tileSize*3;
		g2.drawString(text, x, y);
		if (commandNum == 2) {
			g2.drawString(">", x - gp.tileSize, y);
		}
	}
	
	public void drawPuaseScreen() {
		
		String text = "PAUSED";
		int x, y;
		
		x = getXforCenteredText(text);
		
		y = gp.screenHeight / 2;
		
		g2.drawString(text, x, y);
		
	}
	
	public int getXforCenteredText(String text) {
		int length = (int)g2.getFontMetrics().getStringBounds(text, g2).getWidth();
		int x = gp.screenWidth/2 - length/2;
		
		return x;
	}
	
}