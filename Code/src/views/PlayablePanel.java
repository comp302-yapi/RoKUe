package views;

import entity.Entity;
import entity.Player;
import listeners.keylisteners.GamePanelKeyListener;
import managers.AssetSetter;
import managers.CollisionChecker;
import object.SuperObject;
import managers.TileManager;

import javax.swing.*;

public abstract class PlayablePanel extends JPanel implements BasePanel {

    private final TileManager tileM;
    private final GamePanelKeyListener keyH;
    private final Player player;
    private final SuperObject[] obj;
    private final Entity[] monsters;
    private final CollisionChecker cChecker;
    private final AssetSetter aSetter;

    public PlayablePanel (
            TileManager tileM,
            GamePanelKeyListener keyH,
            Player player, SuperObject[] obj,
            Entity[] monsters,
            CollisionChecker cChecker,
            AssetSetter aSetter
    ) {
        this.tileM = tileM;
        this.keyH = keyH;
        this.player = player;
        this.obj = obj;
        this.monsters = monsters;
        this.cChecker = cChecker;
        this.aSetter = aSetter;
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
