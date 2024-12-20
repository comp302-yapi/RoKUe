package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

import enums.BuildDirection;
import enums.Hall;
import listeners.keylisteners.*;
import listeners.mouselisteners.BuildPanelMouseListener;
import managers.*;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.SuperObject;
import utils.PanelUtils;

public class BuildPanel extends NonPlayablePanel{
	
	private final BuildPanelKeyListener buildPanelKeyListener;
	private final BuildPanelMouseListener buildPanelMouseListener;
	public SuperObject[] objectsToDraw = new SuperObject[20]; // sadece kenarda objelerin görsellerni oluşturmak için

	private Hall currentHall;// enum oluşturabiliriz bunun için

	public TileManagerForHall hallOfWater;
	public TileManagerForHall hallOfEarth;
	public TileManagerForHall hallOfAir;
	public TileManagerForHall hallOfFire;

	public int mouseClickedX, mouseClickedY;
	public int selectedIdx = -1;
	public boolean selected = false;

	// For button drawings
	// TODO: Find a better way to store these values
	int buttonWidth = 200;
	int buttonHeight = 50;
	int buttonOffset = 100;
	int prevButtonX = buttonOffset;
	int nextButtonX = BasePanel.screenWidth - buttonWidth - buttonOffset;
	int buttonY = BasePanel.screenHeight - buttonHeight - buttonOffset;
	Color buttonColor = new Color(26, 17, 23);


	public BuildPanel(ViewManager viewManager) {
		super(viewManager);
		this.buildPanelKeyListener = new BuildPanelKeyListener(this);
        this.addKeyListener(buildPanelKeyListener);
        
        this.buildPanelMouseListener = new BuildPanelMouseListener(this);
        this.addMouseListener(buildPanelMouseListener);
        
        hallOfWater = new TileManagerForHall(this, Hall.HallOfWater, "/res/maps/hallOfWater.txt", 14, 15);
        hallOfEarth = new TileManagerForHall(this, Hall.HallOfEarth, "/res/maps/hallOfEarth.txt", 14, 15);
        hallOfAir = new TileManagerForHall(this, Hall.HallOfAir, "/res/maps/hallOfAir.txt", 14, 15);
        hallOfFire = new TileManagerForHall(this, Hall.HallOfFire, "/res/maps/hallOfFire.txt", 14, 15);
        currentHall = Hall.HallOfEarth;
        loadObjects();
    
	}
	
	public Hall getCurrentHall() {
		return this.currentHall;
	}

	public TileManagerForHall getCurrentHallManager() {
		return switch (currentHall) {
			case HallOfWater -> hallOfWater;
			case HallOfEarth -> hallOfEarth;
			case HallOfAir -> hallOfAir;
			case HallOfFire -> hallOfFire;
		};
	}
	
	public void setCurrentHall(Hall hall) {
		this.currentHall = hall;
	}
	
	protected void loadObjects() {
		
		OBJ_Chest chest = new OBJ_Chest();
		chest.worldX = BasePanel.tileSize * 23 + 16;
		chest.worldY = BasePanel.tileSize * 7;
		
		OBJ_Key key = new OBJ_Key();
		key.worldX = BasePanel.tileSize * 23 + 16;
		key.worldY = BasePanel.tileSize * 9;

		
		OBJ_Door door = new OBJ_Door();
		door.worldX = BasePanel.tileSize * 25 + 16;
		door.worldY = BasePanel.tileSize * 7;
		
		this.objectsToDraw[0] = chest;
		this.objectsToDraw[1] = key;
		this.objectsToDraw[2] = door;
		
		OBJ_Chest chest1 = new OBJ_Chest();
		chest1.worldX = BasePanel.tileSize * 25 + 16;
		chest1.worldY = BasePanel.tileSize * 9;
		
		OBJ_Key key1 = new OBJ_Key();
		key1.worldX = BasePanel.tileSize * 27 + 16;
		key1.worldY = BasePanel.tileSize * 7;

		
		OBJ_Door door1 = new OBJ_Door();
		door1.worldX = BasePanel.tileSize * 27 + 16;
		door1.worldY = BasePanel.tileSize * 9;
		
		this.objectsToDraw[3] = chest1;
		this.objectsToDraw[4] = key1;
		this.objectsToDraw[5] = door1;
	}
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        drawBuildScreen(g2, this);
    }
    
    public boolean checkObjectBorder(int x, int y) {
		return x - (int) (BasePanel.tileSize / 2) > 384 && x + (int) (BasePanel.tileSize / 2) < 960
				&& y - (int) (BasePanel.tileSize / 2) > 144 && y + (int) (BasePanel.tileSize / 2) < 772;

	}
    
    public void drawBuildScreen(Graphics2D g2,BasePanel panel) {

		getCurrentHallManager().draw(g2);

    	if (checkObjectBorder(mouseClickedX,mouseClickedY)){
            g2.setColor(Color.BLACK);
            g2.setStroke(new BasicStroke(2)); // Thicker border
            g2.drawRect(this.mouseClickedX - (int)(BasePanel.tileSize / 2), this.mouseClickedY - (int)(BasePanel.tileSize / 2), BasePanel.tileSize, BasePanel.tileSize);
    	}

		g2.setColor(new Color(62, 41, 52));
		//g2.fillRect(0, 0, BasePanel.screenWidth, BasePanel.screenHeight);

		g2.fillRect(0,0,BasePanel.screenWidth,96);
		g2.fillRect(1008,0,BasePanel.screenWidth,BasePanel.screenHeight);
		g2.fillRect(0,0,336,BasePanel.screenHeight);
		g2.fillRect(0,816,BasePanel.screenWidth,BasePanel.screenHeight);

		// TITLE NAME
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 72F));
		String text = "Build Mode";
		int x, y;
		x = PanelUtils.getXForCenteredText(text, panel, g2) - BasePanel.tileSize*2 + 50;
		y = BasePanel.tileSize * 3;

		// SHADOW
		g2.setColor(new Color(40, 35, 38));
		g2.drawString(text, x+5, y-70);

		// MAIN COLOR
		g2.setColor(new Color(26, 17, 23));
		g2.drawString(text, x, y-75);

		int hallTextXOffset = 450;
		int hallTextYOffset = 75;
		switch (currentHall) {
			case HallOfEarth -> {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
				String text2 = "Hall Of Earth";
				int x2, y2;
				x2 = PanelUtils.getXForCenteredText(text2, panel, g2) - BasePanel.tileSize * 2;
				y2 = BasePanel.tileSize * 3;
				g2.setColor(new Color(26, 17, 23));
				g2.drawString(text2, x2 - hallTextXOffset, y2 - hallTextYOffset);
				drawHallControlButtons(g2, null , BuildDirection.Forward.label);
			}
			case HallOfAir -> {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
				String text3 = "Hall of Air";
				int x3, y3;
				x3 = PanelUtils.getXForCenteredText(text3, panel, g2) - BasePanel.tileSize * 2;
				y3 = BasePanel.tileSize * 3;
				g2.setColor(new Color(26, 17, 23));
				g2.drawString(text3, x3 - hallTextXOffset, y3 - hallTextYOffset);
				drawHallControlButtons(g2, BuildDirection.Backward.label, BuildDirection.Forward.label);
			}
			case HallOfWater -> {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
				String text1 = "Hall Of Water";
				int x1, y1;
				x1 = PanelUtils.getXForCenteredText(text1, panel, g2) - BasePanel.tileSize * 2;
				y1 = BasePanel.tileSize * 3;
				g2.setColor(new Color(26, 17, 23));
				g2.drawString(text1, x1 - hallTextXOffset, y1 - hallTextYOffset);
				drawHallControlButtons(g2, BuildDirection.Backward.label, BuildDirection.Forward.label);
			}
			case HallOfFire -> {
				g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
				String text4 = "Hall Of Fire";
				int x4, y4;
				x4 = PanelUtils.getXForCenteredText(text4, panel, g2) - BasePanel.tileSize * 2;
				y4 = BasePanel.tileSize * 3;
				g2.setColor(new Color(26, 17, 23));
				g2.drawString(text4, x4 - hallTextXOffset, y4 - hallTextYOffset);
				drawHallControlButtons(g2, BuildDirection.Backward.label, "Start Game");
			}
		}

		g2.drawImage(this.hallOfWater.tile[19].image, 1075, 100, BasePanel.tileSize*7, BasePanel.tileSize*12, null);

		for (SuperObject obj : objectsToDraw) {
			if (obj != null) {
				g2.drawImage(obj.image, obj.worldX, obj.worldY, BasePanel.tileSize, BasePanel.tileSize, null);
			}
		}

		if(selected) {
			 g2.setColor(Color.BLACK);
	            g2.setStroke(new BasicStroke(2)); // Thicker border
	            g2.drawRect(this.objectsToDraw[selectedIdx].worldX, this.objectsToDraw[selectedIdx].worldY, BasePanel.tileSize, BasePanel.tileSize);
		}

		/*
		g2.drawString(">", BasePanel.buildMode.objectToDraw[BasePanel.buildMode.selected].worldX - BasePanel.tileSize,
				BasePanel.buildMode.objectToDraw[BasePanel.buildMode.selected].worldY+BasePanel.tileSize);

		*/
    }
	private void drawHallControlButtons(Graphics2D g2, String prevText, String nextText) {
		if(prevText != null) {
			g2.setColor(buttonColor);
			g2.fillRect(prevButtonX, buttonY, buttonWidth, buttonHeight);
			g2.setColor(Color.WHITE);
			g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
			int textXPrev = prevButtonX + (buttonWidth - g2.getFontMetrics().stringWidth(prevText)) / 2;
			int textYPrev = buttonY + (buttonHeight + g2.getFontMetrics().getAscent()) / 2 - 5;
			g2.drawString(prevText, textXPrev, textYPrev);
		}

		g2.setColor(buttonColor);
		g2.fillRect(nextButtonX, buttonY, buttonWidth, buttonHeight);
		g2.setColor(Color.WHITE);
		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 18F));
		int textXNext = nextButtonX + (buttonWidth - g2.getFontMetrics().stringWidth(nextText)) / 2;
		int textYNext = buttonY + (buttonHeight + g2.getFontMetrics().getAscent()) / 2 - 5;
		g2.drawString(nextText, textXNext, textYNext);
	}

	public boolean isInPreviousButton(int mouseX, int mouseY) {
		return mouseX > prevButtonX && mouseX < prevButtonX + buttonWidth
				&& mouseY > buttonY && mouseY < buttonY + buttonHeight;
	}

	public boolean isInNextButton(int mouseX, int mouseY) {
		return mouseX > nextButtonX && mouseX < nextButtonX + buttonWidth
				&& mouseY > buttonY && mouseY < buttonY + buttonHeight;
	}
}
