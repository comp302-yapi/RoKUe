package views;

import entity.Entity;
import entity.Player;
import main.CollisionChecker;
import object.SuperObject;
import tile.TileManager;

public abstract class NonPlayablePanel implements BasePanel {

    @Override
    public CollisionChecker getCollisionChecker() {
        return null;
    }

    @Override
    public Player getPlayer() {
        return null;
    }

    @Override
    public Entity[] getMonsters() {
        return null;
    }

    @Override
    public SuperObject[] getSuperObjects() {
        return null;
    }

    @Override
    public TileManager getTileManager() {
        return null;
    }

}
