package views;

import entity.Entity;
import entity.Player;
import managers.CollisionChecker;
import managers.ViewManager;
import object.SuperObject;
import managers.TileManager;
import java.awt.*;


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

    Font arial_80B = new Font("Arial", Font.BOLD, 80);
    Font arial_40 = new Font("Arial", Font.PLAIN, 40);
    Font arial_20 = new Font("Arial", Font.PLAIN, 20);

    void update();
    void showMessage(String message);
    CollisionChecker getCollisionChecker();
    Player getPlayer();
    Entity[] getMonsters();
    SuperObject[] getSuperObjects();
    TileManager getTileManager();
    ViewManager getViewManager();

}
