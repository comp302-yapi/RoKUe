package views;

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
	
		
		g2.setColor(new Color(62, 41, 52));
		//g2.fillRect(0, 0, BasePanel.screenWidth, BasePanel.screenHeight);
		
		g2.fillRect(0,0,BasePanel.screenWidth,100);
		g2.fillRect(972,0,BasePanel.screenWidth,BasePanel.screenHeight);
		g2.fillRect(0,0,300,BasePanel.screenHeight);
		g2.fillRect(0,820,BasePanel.screenWidth,BasePanel.screenHeight);
		
		
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
		
		g2.drawImage(this.hallOfWater.tile[19].image, 1200, 100, BasePanel.tileSize*4, BasePanel.tileSize*15, null);
		

		for(int i = 0;i < objectsToDraw.length;i++) {
			SuperObject obj = objectsToDraw[i];
			if(obj != null) {
				g2.drawImage(obj.image, obj.worldX, obj.worldY, BasePanel.tileSize, BasePanel.tileSize, null);
			}
		}
		
		 
		/*
		g2.drawString(">", BasePanel.buildMode.objectToDraw[BasePanel.buildMode.selected].worldX - BasePanel.tileSize, 
				BasePanel.buildMode.objectToDraw[BasePanel.buildMode.selected].worldY+BasePanel.tileSize);
				
		*/
    }
    
	

}
