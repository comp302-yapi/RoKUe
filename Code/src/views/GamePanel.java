package views;

import entity.Entity;
import entity.Player;
import listeners.keylisteners.GamePanelKeyListener;
import main.AssetSetter;
import main.CollisionChecker;
import object.SuperObject;
import tile.TileManager;
import javax.swing.*;
import java.awt.*;


public class GamePanel extends JPanel implements BasePanel {

    Thread gameThread;
    private final TileManager tileM;
    private final GamePanelKeyListener keyH;
    private final Player player;
    private final SuperObject[] obj;
    private final Entity[] monsters;
    private final CollisionChecker cChecker;
    private final AssetSetter aSetter;

    public GamePanel() {
        tileM = new TileManager(this);
        keyH = new GamePanelKeyListener(this);
        player = new Player(this, keyH);
        obj = new SuperObject[10];
        monsters = new Entity[20];
        cChecker = new CollisionChecker(this, tileM);
        aSetter = new AssetSetter(this);

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

    @Override
    public void update() {

    }

    @Override
    public void showMessage(String message) {
        System.out.println("Message");
    }

    @Override
    public CollisionChecker getCollisionChecker() {
        return cChecker;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public Entity[] getMonsters() {
        return monsters;
    }

    @Override
    public SuperObject[] getSuperObjects() {
        return obj;
    }

    @Override
    public TileManager getTileManager() {
        return tileM;
    }
}
