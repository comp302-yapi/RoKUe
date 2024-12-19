package managers;

import monster.MON_Archer;
import monster.MON_Wizard;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import views.BasePanel;

public class AssetSetter {

	BasePanel panel;
	
	public AssetSetter(BasePanel panel) {
		this.panel = panel;
	}
	
	public void setObject() {
		
		panel.getSuperObjects()[0] = new OBJ_Key();
		panel.getSuperObjects()[0].worldX = 23 * panel.tileSize;
		panel.getSuperObjects()[0].worldY = 7 * panel.tileSize;
		
		panel.getSuperObjects()[1] = new OBJ_Key();
		panel.getSuperObjects()[1].worldX = 23 * panel.tileSize;
		panel.getSuperObjects()[1].worldY = 40 * panel.tileSize;
		
		panel.getSuperObjects()[2] = new OBJ_Key();
		panel.getSuperObjects()[2].worldX = 38 * panel.tileSize;
		panel.getSuperObjects()[2].worldY = 8 * panel.tileSize;
		
		panel.getSuperObjects()[3] = new OBJ_Door();
		panel.getSuperObjects()[3].worldX = 10 * panel.tileSize;
		panel.getSuperObjects()[3].worldY = 11 * panel.tileSize;
		
		panel.getSuperObjects()[6] = new OBJ_Chest();
		panel.getSuperObjects()[6].worldX = 10 * panel.tileSize;
		panel.getSuperObjects()[6].worldY = 7 * panel.tileSize;
	}
	
	public void spawnMonster() {
		
		panel.getMonsters()[0] = new MON_Archer(panel);
		panel.getMonsters()[0].worldX = panel.tileSize*24;
		panel.getMonsters()[0].worldY = panel.tileSize*21;
		
		panel.getMonsters()[1] = new MON_Archer(panel);
		panel.getMonsters()[1].worldX = panel.tileSize*18;
		panel.getMonsters()[1].worldY = panel.tileSize*23;
		
		panel.getMonsters()[2] = new MON_Wizard(panel);
		panel.getMonsters()[2].worldX = panel.tileSize*20;
		panel.getMonsters()[2].worldY = panel.tileSize*25;


	}
	
}
