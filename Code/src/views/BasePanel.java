package views;

import entity.Entity;
import entity.Player;
import managers.CollisionChecker;
import object.SuperObject;
import managers.TileManager;


public interface BasePanel {

    // SCREEN SETTINGS
    int originalTileSize = 16; // 16x16 tile
    int scale = 3;

    int tileSize = originalTileSize * scale;
    int maxScreenCol = 40;
    int maxScreenRow = 20;
    int screenWidth = tileSize * maxScreenCol; // 1920p
    int screenHeight = tileSize * maxScreenRow; // 1056p

    // WORLD SETTINGS
    int maxWorldCol = 75;
    int maxWorldRow = 75;
    int worldWidth = tileSize * maxWorldCol;
    int worldHeight = tileSize * maxWorldRow;

    void update();
    void showMessage(String message);
    CollisionChecker getCollisionChecker();
    Player getPlayer();
    Entity[] getMonsters();
    SuperObject[] getSuperObjects();
    TileManager getTileManager();

}
