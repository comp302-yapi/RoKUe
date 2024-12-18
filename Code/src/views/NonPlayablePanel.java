package views;

import entity.Entity;
import entity.Player;
import managers.CollisionChecker;
import object.SuperObject;
import managers.TileManager;

import javax.swing.*;

public abstract class NonPlayablePanel extends JPanel implements BasePanel {

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
