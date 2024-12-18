package views;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;

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


	public BuildPanel(ViewManager viewManager) {
		super(viewManager);
		this.buildPanelKeyListener = new BuildPanelKeyListener(this);
        this.addKeyListener(buildPanelKeyListener);
        
        this.buildPanelMouseListener = new BuildPanelMouseListener(this);
        this.addMouseListener(buildPanelMouseListener);
        
        hallOfWater = new TileManagerForHall(this,"/res/maps/hallOfWater.txt", 14, 15);
        hallOfEarth = new TileManagerForHall(this,"/res/maps/hallOfEarth.txt", 14, 15);
        hallOfAir = new TileManagerForHall(this,"/res/maps/hallOfAir.txt", 14, 15);
        hallOfFire = new TileManagerForHall(this,"/res/maps/hallOfFire.txt", 14, 15);
        currentHall = Hall.HallOfWater;
        loadObjects();
    
	}
	
	public Hall getCurrentHall() {
		return this.currentHall;
	}
	
	public void setCurrentHall(Hall hall) {
		this.currentHall = hall;
	}
	
	protected void loadObjects() {
		
		OBJ_Chest chest = new OBJ_Chest();
		chest.worldX = BasePanel.tileSize * 25 + 32;
		chest.worldY = BasePanel.tileSize * 7;
		
		OBJ_Key key = new OBJ_Key();
		key.worldX = BasePanel.tileSize * 25 + 32;
		key.worldY = BasePanel.tileSize * 9;

		
		OBJ_Door door = new OBJ_Door();
		door.worldX = BasePanel.tileSize * 25 + 32;
		door.worldY = BasePanel.tileSize * 11;
		
		this.objectsToDraw[0] = chest;
		this.objectsToDraw[1] = key;
		this.objectsToDraw[2] = door;
		
		OBJ_Chest chest1 = new OBJ_Chest();
		chest1.worldX = BasePanel.tileSize * 27 + 32;
		chest1.worldY = BasePanel.tileSize * 7;
		
		OBJ_Key key1 = new OBJ_Key();
		key1.worldX = BasePanel.tileSize * 27 + 32;
		key1.worldY = BasePanel.tileSize * 9;

		
		OBJ_Door door1 = new OBJ_Door();
		door1.worldX = BasePanel.tileSize * 27 + 32;
		door1.worldY = BasePanel.tileSize * 11;
		
		this.objectsToDraw[3] = chest1;
		this.objectsToDraw[4] = key1;
		this.objectsToDraw[5] = door1;
	}
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;

        drawBuildScreen(g2, this);
    }
    
    public boolean checkBorder(int x, int y) {
    	
    	
    	if(x - (int)(BasePanel.tileSize / 2) > 384 && x + (int)(BasePanel.tileSize / 2) < 960
    			&& y - (int)(BasePanel.tileSize / 2) > 144 && y + (int)(BasePanel.tileSize / 2) < 772) {
    		return true;
    	}
    	
    	return false;
    	
    }
    
    public void drawBuildScreen(Graphics2D g2,BasePanel panel) {
    	
    	
    	
    	switch(currentHall){
    	
	    	case HallOfWater:
	    		this.hallOfWater.draw(g2);
	    		break;
	    	case HallOfEarth:
	    		this.hallOfEarth.draw(g2);
	    		break;
	    	case HallOfAir:
	    		this.hallOfAir.draw(g2);
	    		break;
	    	case HallOfFire:
	    		this.hallOfFire.draw(g2);
	    		break;
    		
    	
    	}
    	
    	if (checkBorder(mouseClickedX,mouseClickedY)){
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
		x = PanelUtils.getXForCenteredText(text, panel, g2) - BasePanel.tileSize*2;
		y = BasePanel.tileSize * 3;
		
		
		// SHADOW
		g2.setColor(new Color(40, 35, 38));
		g2.drawString(text, x+5, y-70);
		
		// MAIN COLOR
		g2.setColor(new Color(26, 17, 23));
		g2.drawString(text, x, y-75);
		
		switch(currentHall){
    	
    	case HallOfWater:
    		
    		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
    		String text1 = "Hall Of Water";
    		int x1, y1;
    		x1 = PanelUtils.getXForCenteredText(text1, panel, g2) - BasePanel.tileSize*2;
    		y1 = BasePanel.tileSize * 3;
    		g2.setColor(new Color(20, 218, 34));
    		g2.drawString(text1, x1 + 500, y1-75);
    		
    		break;
    		
    	case HallOfEarth:
    		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
    		String text2 = "Hall Of Earth";
    		int x2, y2;
    		x2 = PanelUtils.getXForCenteredText(text2, panel, g2) - BasePanel.tileSize*2;
    		y2 = BasePanel.tileSize * 3;
    		g2.setColor(new Color(20, 218, 34));
    		g2.drawString(text2, x2 + 500, y2-75);
    		break;
    	case HallOfAir:
    		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
    		String text3 = "Hall of Air";
    		int x3, y3;
    		x3 = PanelUtils.getXForCenteredText(text3, panel, g2) - BasePanel.tileSize*2;
    		y3 = BasePanel.tileSize * 3;
    		g2.setColor(new Color(20, 218, 34));
    		g2.drawString(text3, x3 + 500, y3-75);
    		break;
    	case HallOfFire:
    		g2.setFont(g2.getFont().deriveFont(Font.BOLD, 36F));
    		String text4 = "Hall Of Fire";
    		int x4, y4;
    		x4 = PanelUtils.getXForCenteredText(text4, panel, g2) - BasePanel.tileSize*2;
    		y4 = BasePanel.tileSize * 3;
    		g2.setColor(new Color(20, 218, 34));
    		g2.drawString(text4, x4 + 500, y4-75);
    		break;
    		
	
	}
		
		g2.drawImage(this.hallOfWater.tile[19].image, 1200, 100, BasePanel.tileSize*4, BasePanel.tileSize*15, null);
		

		for(int i = 0;i < objectsToDraw.length;i++) {
			SuperObject obj = objectsToDraw[i];
			if(obj != null) {
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
    
	public void addObject(SuperObject obj, int x, int y) {
    	switch(currentHall){
    	
    	case HallOfWater:
    		this.hallOfWater.addObject(obj,x,y);
    		break;
    	case HallOfEarth:
    		this.hallOfEarth.addObject(obj,x,y);
    		break;
    	case HallOfAir:
    		this.hallOfAir.addObject(obj,x,y);
    		break;
    	case HallOfFire:
    		this.hallOfFire.addObject(obj,x,y);
    		break;
    		
    	
    	}
    	
	}

}
