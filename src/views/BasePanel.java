package views;

import entity.Arrow;
import entity.Entity;
import entity.Fireball;
import entity.Player;
import managers.CollisionChecker;
import managers.TileManager;
import managers.TileManagerForHome;
import managers.ViewManager;
import object.SuperObject;

import java.awt.*;


public interface BasePanel {

    // SCREEN SETTINGS
    int originalTileSize = 16; // 16x16 tile
    int scale = 3;

    int tileSize = originalTileSize * scale;
    int maxScreenCol = 30;
    int maxScreenRow = 18;
    
    // bunları dynamic yapmaya çalışalım
    int screenWidth = tileSize * maxScreenCol;
    int screenHeight = tileSize * maxScreenRow; 

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
    Arrow[] getArrows();
}
