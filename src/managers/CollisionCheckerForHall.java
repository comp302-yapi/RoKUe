package managers;

import containers.HallContainer;
import containers.TileContainer;
import entity.Entity;
import entity.Fireball;
import entity.Player;
import object.SuperObject;
import views.BasePanel;
import views.HallPanel;

import java.io.Serializable;
import java.util.ArrayList;

public class CollisionCheckerForHall implements Serializable {

    private static final long serialVersionUID = 1L;

    private final HallPanel hallPanel;
    private final Player player;

    public CollisionCheckerForHall(HallPanel panel) {
        hallPanel = panel;
        this.player = panel.getPlayer();
    }

    public void checkTile(Entity entity) {

        if (entity instanceof Player) {
            Player e = (Player)entity;
            entity.solidArea.x = e.screenX - 336 + entity.solidArea.x;
            entity.solidArea.y = e.screenY - 96 + entity.solidArea.y;
        }
        else {
        	 entity.solidArea.x = entity.worldX - 336 + entity.solidAreaDefaultX;
             entity.solidArea.y = entity.worldY - 96 + entity.solidAreaDefaultY;
        }

       int entityLeftWorldX = entity.solidArea.x;
        int entityRightWorldX = entity.solidArea.x + entity.solidArea.width;
        int entityTopWorldY = entity.solidArea.y;
        int entityBottomWorldY = entity.solidArea.y + entity.solidArea.height;

        int entityLeftCol = entityLeftWorldX/HallPanel.tileSize;
        int entityRightCol = entityRightWorldX/HallPanel.tileSize;
        int entityTopRow = entityTopWorldY/HallPanel.tileSize;
        int entityBottomRow = entityBottomWorldY/HallPanel.tileSize;

        int tileNum1, tileNum2;

        switch (entity.direction) {
            case "up" -> {
                entityTopRow = (entityTopWorldY - entity.speed) / HallPanel.tileSize;
                tileNum1 = HallContainer.getCurrentHallManager(hallPanel.currentHall).mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = HallContainer.getCurrentHallManager(hallPanel.currentHall).mapTileNum[entityRightCol][entityTopRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }

            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / HallPanel.tileSize;
                tileNum1 = HallContainer.getCurrentHallManager(hallPanel.currentHall).mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = HallContainer.getCurrentHallManager(hallPanel.currentHall).mapTileNum[entityRightCol][entityBottomRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }

            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / HallPanel.tileSize;
                tileNum1 = HallContainer.getCurrentHallManager(hallPanel.currentHall).mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = HallContainer.getCurrentHallManager(hallPanel.currentHall).mapTileNum[entityLeftCol][entityBottomRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }

            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / HallPanel.tileSize;
                tileNum1 = HallContainer.getCurrentHallManager(hallPanel.currentHall).mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = HallContainer.getCurrentHallManager(hallPanel.currentHall).mapTileNum[entityRightCol][entityBottomRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }

    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (SuperObject obj: HallContainer.getCurrentHallManager(hallPanel.currentHall).objects) {

            if (player) {
                Player e = (Player)entity;
                entity.solidArea.x = e.screenX + entity.solidArea.x;
                entity.solidArea.y = e.screenY + entity.solidArea.y;
            }

            else {
                entity.solidArea.x = entity.worldX  + entity.solidArea.x;
                entity.solidArea.y = entity.worldY + entity.solidArea.y;
            }


            // Get the object's solid area position
            obj.solidArea.x = obj.worldX + obj.solidArea.x;
            obj.solidArea.y = obj.worldY + obj.solidArea.y;

            switch (entity.direction) {
                case "up" -> entity.solidArea.y -= entity.speed;
                case "down" -> entity.solidArea.y += entity.speed;
                case "left" -> entity.solidArea.x -= entity.speed;
                case "right" -> entity.solidArea.x += entity.speed;
            }

            if (entity.solidArea.intersects(obj.solidArea)) {
                if (obj.collision) {
                    entity.collisionOn = true;
                }

                if (player) {
                    index = HallContainer.getCurrentHallManager(hallPanel.currentHall).objects.indexOf(obj);
                }
            }

            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            obj.solidArea.x = obj.solidAreaDefaultX;
            obj.solidArea.y = obj.solidAreaDefaultY;
        }
        return index;
    }

    // NPC OR MONSTER COLLISION
    public int checkEntity(Entity entity, Entity[] target) {
        int index = 999;

        for (int i = 0; i < target.length; i++) {

            if (target[i] != null) {
                // Get entity's solid area position
                if (entity instanceof Player e) {
                    entity.solidArea.x = e.screenX + entity.solidArea.x;
                    entity.solidArea.y = e.screenY + entity.solidArea.y;
                } else {
                    entity.solidArea.x = entity.worldX  + entity.solidArea.x;
                    entity.solidArea.y = entity.worldY + entity.solidArea.y;
                }

                // Get the object's solid are position
                target[i].solidArea.x = target[i].worldX + target[i].solidArea.x;
                target[i].solidArea.y = target[i].worldY + target[i].solidArea.y;


                if (entity.knockback) {
                    switch (player.direction) {
                        case "up" -> entity.solidArea.y -= entity.speed;
                        case "down" -> entity.solidArea.y += entity.speed;
                        case "left" -> entity.solidArea.x -= entity.speed;
                        case "right" -> entity.solidArea.x += entity.speed;
                    }
                } else {
                    switch (entity.direction) {
                        case "up" -> entity.solidArea.y -= entity.speed;
                        case "down" -> entity.solidArea.y += entity.speed;
                        case "left" -> entity.solidArea.x -= entity.speed;
                        case "right" -> entity.solidArea.x += entity.speed;
                    }
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
        player.solidArea.x = player.screenX + player.solidArea.x;
        player.solidArea.y = player.screenY + player.solidArea.y;

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









