package main;

import entity.Entity;
import entity.Player;
import object.SuperObject;
import tile.TileManager;
import views.BasePanel;

public class CollisionChecker {
	
	private final BasePanel panel;
	private final TileManager tileM;
	private final Player player;
	private final SuperObject[] obj;
	
	public CollisionChecker(BasePanel panel, TileManager tileM) {
		this.panel = panel;
		this.tileM = tileM;
		this.player = panel.getPlayer();
		this.obj = panel.getSuperObjects();
	}
	
	public void checkTile(Entity entity) {
		
		int entityLeftWorldX = entity.worldX + entity.solidArea.x;
		int entityRightWorldX = entity.worldX + entity.solidArea.x + entity.solidArea.width;
		int entityTopWorldY = entity.worldY + entity.solidArea.y;
		int entityBottomWorldY = entity.worldY + entity.solidArea.y + entity.solidArea.height;
		
		int entityLeftCol = entityLeftWorldX/panel.tileSize;
		int entityRightCol = entityRightWorldX/panel.tileSize;
		int entityTopRow = entityTopWorldY/panel.tileSize;
		int entityBottomRow = entityBottomWorldY/panel.tileSize;
		
		int tileNum1, tileNum2;

		switch (entity.direction) {
			case "up" -> {
				entityTopRow = (entityTopWorldY - entity.speed) / panel.tileSize;
				tileNum1 = tileM.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = tileM.mapTileNum[entityRightCol][entityTopRow];
				if (tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
			}

			case "down" -> {
				entityBottomRow = (entityBottomWorldY + entity.speed) / panel.tileSize;
				tileNum1 = tileM.mapTileNum[entityLeftCol][entityBottomRow];
				tileNum2 = tileM.mapTileNum[entityRightCol][entityBottomRow];
				if (tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
			}

			case "left" -> {
				entityLeftCol = (entityLeftWorldX - entity.speed) / panel.tileSize;
				tileNum1 = tileM.mapTileNum[entityLeftCol][entityTopRow];
				tileNum2 = tileM.mapTileNum[entityLeftCol][entityBottomRow];
				if (tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
			}

			case "right" -> {
				entityRightCol = (entityRightWorldX + entity.speed) / panel.tileSize;
				tileNum1 = tileM.mapTileNum[entityRightCol][entityTopRow];
				tileNum2 = tileM.mapTileNum[entityRightCol][entityBottomRow];
				if (tileM.tile[tileNum1].collision || tileM.tile[tileNum2].collision) {
					entity.collisionOn = true;
				}
			}
		}
		
	}
	
	public int checkObject(Entity entity, boolean player) {
		int index = 999;

		for (int i = 0; i < obj.length; i++) {
			
			if (obj[i] != null) {
				
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get the object's solid are position
				obj[i].solidArea.x = obj[i].worldX + obj[i].solidArea.x;
				obj[i].solidArea.y = obj[i].worldY + obj[i].solidArea.y;

				switch (entity.direction) {
					case "up" -> entity.solidArea.y -= entity.speed;
					case "down" -> entity.solidArea.y += entity.speed;
					case "left" -> entity.solidArea.x -= entity.speed;
					case "right" -> entity.solidArea.x += entity.speed;
				}
				
				if (entity.solidArea.intersects(obj[i].solidArea)) {
					if (obj[i].collision == true) {
						entity.collisionOn = true;
					}
					if (player) {
						index = i;
					}
				}

				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				obj[i].solidArea.x = obj[i].solidAreaDefaultX;
				obj[i].solidArea.y = obj[i].solidAreaDefaultY;
			}
		}

		return index;
	}
	
	// NPC OR MONSTER COLLISION
	public int checkEntity(Entity entity, Entity[] target) {
		int index = 999;
		
		for (int i = 0; i < target.length; i++) {

			if (target[i] != null) {
				// Get entity's solid area position
				entity.solidArea.x = entity.worldX + entity.solidArea.x;
				entity.solidArea.y = entity.worldY + entity.solidArea.y;

				// Get the object's solid are position
				target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
				target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;

				switch (entity.direction) {
					case "up" -> entity.solidArea.y -= entity.speed;
					case "down" -> entity.solidArea.y += entity.speed;
					case "left" -> entity.solidArea.x -= entity.speed;
					case "right" -> entity.solidArea.x += entity.speed;
				}
				
				if (entity.solidArea.intersects(target[i].solidArea)) {
					if (target[i] != entity) {
						entity.collisionOn = true;
					}
					index = i;
				}
				entity.solidArea.x = entity.solidAreaDefaultX;
				entity.solidArea.y = entity.solidAreaDefaultY;
				target[i].solidArea.x = target[i].solidAreaDefaultX;
				target[i].solidArea.y = target[i].solidAreaDefaultY;
			}
			
		}
		
		return index;
	}
	
	public boolean checkPlayer(Entity entity) {
		boolean contactPlayer = false;
		
		// Get entity's solid area position
		entity.solidArea.x = entity.worldX + entity.solidArea.x;
		entity.solidArea.y = entity.worldY + entity.solidArea.y;

		// Get the object's solid are position
		player.solidArea.x = player.worldX + player.solidArea.x;
		player.solidArea.y = player.worldY + player.solidArea.y;

		switch (entity.direction) {
			case "up" -> entity.solidArea.y -= entity.speed;
			case "down" -> entity.solidArea.y += entity.speed;
			case "left" -> entity.solidArea.x -= entity.speed;
			case "right" -> entity.solidArea.x += entity.speed;
		}
		
		if (entity.solidArea.intersects(player.solidArea)) {
			entity.collisionOn = true;
			contactPlayer = true;
		}
		
		entity.solidArea.x = entity.solidAreaDefaultX;
		entity.solidArea.y = entity.solidAreaDefaultY;
		player.solidArea.x = player.solidAreaDefaultX;
		player.solidArea.y = player.solidAreaDefaultY;

		return contactPlayer;
	}
}







