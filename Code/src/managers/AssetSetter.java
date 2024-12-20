package managers;

import monster.MON_GreenSlime;
import monster.MON_Witch;
import object.OBJ_Barrel;
import object.OBJ_Cactus;
import object.OBJ_Chain;
import object.OBJ_Chest;
import object.OBJ_Door;
import object.OBJ_Key;
import object.OBJ_Lantern;
import object.OBJ_Pot;
import object.OBJ_Stone;
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

		panel.getSuperObjects()[4] = new OBJ_Barrel();
        panel.getSuperObjects()[4].worldX = 15 * panel.tileSize;
        panel.getSuperObjects()[4].worldY = 10 * panel.tileSize;

        panel.getSuperObjects()[5] = new OBJ_Stone();
        panel.getSuperObjects()[5].worldX = 30 * panel.tileSize;
        panel.getSuperObjects()[5].worldY = 30 * panel.tileSize;

        panel.getSuperObjects()[6] = new OBJ_Chest();
        panel.getSuperObjects()[6].worldX = 10 * panel.tileSize;
        panel.getSuperObjects()[6].worldY = 7 * panel.tileSize;

        panel.getSuperObjects()[7] = new OBJ_Pot();
        panel.getSuperObjects()[7].worldX = 10 * panel.tileSize;
        panel.getSuperObjects()[7].worldY = 30 * panel.tileSize;

        panel.getSuperObjects()[8] = new OBJ_Lantern();
        panel.getSuperObjects()[8].worldX = 20 * panel.tileSize;
        panel.getSuperObjects()[8].worldY = 8 * panel.tileSize;

        panel.getSuperObjects()[9] = new OBJ_Chain();
        panel.getSuperObjects()[9].worldX = 55 * panel.tileSize;
        panel.getSuperObjects()[9].worldY = 10 * panel.tileSize;

        panel.getSuperObjects()[10] = new OBJ_Cactus();
        panel.getSuperObjects()[10].worldX = 30 * panel.tileSize;
        panel.getSuperObjects()[10].worldY = 18 * panel.tileSize;
		
		
	}
	
	public void spawnMonster() {
		
		panel.getMonsters()[0] = new MON_GreenSlime(panel);
		panel.getMonsters()[0].worldX = panel.tileSize*24;
		panel.getMonsters()[0].worldY = panel.tileSize*21;
		
		panel.getMonsters()[1] = new MON_GreenSlime(panel);
		panel.getMonsters()[1].worldX = panel.tileSize*18;
		panel.getMonsters()[1].worldY = panel.tileSize*23;
		
		panel.getMonsters()[2] = new MON_Witch(panel);
		panel.getMonsters()[2].worldX = panel.tileSize*20;
		panel.getMonsters()[2].worldY = panel.tileSize*25;

	}
	
}
