package managers;

import containers.HallContainer;
import containers.TileContainer;
import entity.Entity;
import entity.Laser;
import entity.Player;
import monster.MON_LandmineBot;
import object.SuperObject;
import views.BasePanel;
import views.BossPanel;
import views.HallPanel;
import views.HomePanel;

import java.io.Serializable;
import java.util.ArrayList;

public class CollisionCheckerForBoss implements Serializable {

    private static final long serialVersionUID = 1L;

    private final BossPanel bossPanel;
    private final Player player;
    private final TileManagerForBoss tileM;

    public CollisionCheckerForBoss(BossPanel panel) {
        this.tileM = panel.getTileManagerForBoss();
        this.bossPanel = panel;
        this.player = panel.getPlayer();
    }

    public void checkTile(Entity entity) {

        if (entity instanceof Player) {
            Player e = (Player)entity;
            entity.solidArea.x = e.screenX  + entity.solidArea.x;
            entity.solidArea.y = e.screenY  + entity.solidArea.y;
        }
        else {
            entity.solidArea.x = entity.worldX + entity.solidArea.x;
            entity.solidArea.y = entity.worldY  + entity.solidArea.y;
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
                tileNum1 = bossPanel.getTileManagerForBoss().mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = bossPanel.getTileManagerForBoss().mapTileNum[entityRightCol][entityTopRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }

            case "down" -> {
                entityBottomRow = (entityBottomWorldY + entity.speed) / BasePanel.tileSize;
                tileNum1 = bossPanel.getTileManagerForBoss().mapTileNum[entityLeftCol][entityBottomRow];
                tileNum2 = bossPanel.getTileManagerForBoss().mapTileNum[entityRightCol][entityBottomRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }

            case "left" -> {
                entityLeftCol = (entityLeftWorldX - entity.speed) / BasePanel.tileSize;
                tileNum1 = bossPanel.getTileManagerForBoss().mapTileNum[entityLeftCol][entityTopRow];
                tileNum2 = bossPanel.getTileManagerForBoss().mapTileNum[entityLeftCol][entityBottomRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }

            case "right" -> {
                entityRightCol = (entityRightWorldX + entity.speed) / BasePanel.tileSize;
                tileNum1 = bossPanel.getTileManagerForBoss().mapTileNum[entityRightCol][entityTopRow];
                tileNum2 = bossPanel.getTileManagerForBoss().mapTileNum[entityRightCol][entityBottomRow];
                if (TileContainer.getTile()[tileNum1].collision || TileContainer.getTile()[tileNum2].collision) {
                    entity.collisionOn = true;
                }
            }
        }


    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;
        for (SuperObject obj: bossPanel.objects) {
            entity.solidArea.x = entity.solidAreaDefaultX;
            entity.solidArea.y = entity.solidAreaDefaultY;
            obj.solidArea.x = obj.solidAreaDefaultX;
            obj.solidArea.y = obj.solidAreaDefaultY;

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
                    index = bossPanel.objects.indexOf(obj);
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
                entity.solidArea.x = entity.solidAreaDefaultX;
                entity.solidArea.y = entity.solidAreaDefaultY;
                target[i].solidArea.x = target[i].solidAreaDefaultX;
                target[i].solidArea.y = target[i].solidAreaDefaultY;

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

        if (entity instanceof MON_LandmineBot) {
            if (!entity.isAttackingLandmineBot()) {
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
            } else {
                // Get entity's solid area position
                entity.solidArea.x = entity.worldX + entity.solidArea.x - 80;
                entity.solidArea.y = entity.worldY + entity.solidArea.y - 100;

                // Get the object's solid are position
                player.solidArea.x = player.screenX + player.solidArea.x;
                player.solidArea.y = player.screenY + player.solidArea.y;
            }
        } else {
            // Get entity's solid area position
//            if (entity instanceof Laser) {
//                System.out.println("Changing y to: " + (200 + entity.solidArea.y) + " from: " + entity.solidArea.y);
//            }
            entity.solidArea.x = entity.worldX + entity.solidArea.x;
            entity.solidArea.y = 200  + entity.solidArea.y;

            // Get the object's solid are position
            player.solidArea.x = player.screenX + player.solidArea.x;
            player.solidArea.y = player.screenY + player.solidArea.y;

            if (!(entity instanceof Laser)) {
                switch (entity.direction) {
                    case "up" -> entity.solidArea.y -= entity.speed;
                    case "down" -> entity.solidArea.y += entity.speed;
                    case "left" -> entity.solidArea.x -= entity.speed;
                    case "right" -> entity.solidArea.x += entity.speed;
                }
            }
        }

//        if (entity instanceof Laser) {
//            System.out.println(entity.solidArea.x + " X Y " + entity.solidArea.y);
//            System.out.println(entity.solidArea.width + " WIDHT HEIGHT " + entity.solidArea.height);
//        }

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









