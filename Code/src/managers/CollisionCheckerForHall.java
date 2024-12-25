package managers;

import containers.TileContainer;
import entity.Entity;
import entity.Player;
import object.SuperObject;
import views.BasePanel;

public class CollisionCheckerForHall {

    private final TileManagerForHall tileM;
    private final Player player;
    private final SuperObject[] obj;

    public CollisionCheckerForHall(BasePanel panel, TileManagerForHall tileM) {
        this.tileM = tileM;
        this.player = panel.getPlayer();
        this.obj = tileM.convertToArray();
    }

    public void checkTile(Entity entity) {

        if (entity instanceof Player) {
            Player e = (Player)entity;
            entity.solidArea.x = e.screenX - 336 + entity.solidArea.x;
            entity.solidArea.y = e.screenY - 96 + entity.solidArea.y;
        }
        else {
        	 entity.solidArea.x = entity.worldX - 336 + entity.solidArea.x;
             entity.solidArea.y = entity.worldY - 96 + entity.solidArea.y;
        }

       int entityLeftWorldX = entity.solidArea.x;
        int entityRightWorldX = entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.solidArea.y;
        int entityBottomWorldY = entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/BasePanel.tileSize;
        int entityRightCol = entityRightWorldX/BasePanel.tileSize;
        int entityTopRow = entityTopWorldY/BasePanel.tileSize;
        int entityBottomRow = entityBottomWorldY/BasePanel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / BasePanel.tileSize;
                tileNum1 = tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = tileM.mapTileNum[entityRightCol][entityTopRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }

            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / BasePanel.tileSize;
                tileNum1 = tileM.mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }

            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / BasePanel.tileSize;
                tileNum1 = tileM.mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = tileM.mapTileNum[entityLeftCol][entityBottomRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }

            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / BasePanel.tileSize;
                tileNum1 = tileM.mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = tileM.mapTileNum[entityRightCol][entityBottomRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }

    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (int i = 0; i < obj.length; i++) {

            if (obj[i] != null) {

//                 Get entity's solid area position
                if (player) {
                  //System.out.println(0);
                    Player e = (Player)entity;
                    entity.solidArea.x = e.screenX + entity.solidArea.x;
                    entity.solidArea.y = e.screenY + entity.solidArea.y;
                }
                
                else {
               	 entity.solidArea.x = entity.worldX  + entity.solidArea.x;
                    entity.solidArea.y = entity.worldY + entity.solidArea.y;
               }
                

                // Get the object's solid area position
                obj[i].solidArea.x = obj[i].worldX + obj[i].solidArea.x;
                obj[i].solidArea.y = obj[i].worldY + obj[i].solidArea.y;

                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }

//                if (obj[i].name.equals("Door")) {
//                    System.out.println("PLAYER: " + entity.solidArea.x + " | " + entity.solidArea.y);
//                    System.out.println("OBJECT: " + obj[i].solidArea.x + " | " + obj[i].solidArea.y);
//                }

                if (entity.solidArea.intersects(obj[i].solidArea)) {
                    if (obj[i].collision) {
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









