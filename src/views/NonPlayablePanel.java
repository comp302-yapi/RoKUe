package views;

import entity.Arrow;
import entity.Entity;
import entity.Player;
import managers.CollisionChecker;
import managers.TileManager;
import managers.TileManagerForHome;
import managers.ViewManager;
import object.SuperObject;

import javax.swing.*;
import java.awt.*;

public class NonPlayablePanel extends JPanel implements BasePanel {

    private final ViewManager viewManager;

    public NonPlayablePanel(ViewManager viewManager) {
        this.viewManager = viewManager;

        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.setFocusable(true);
    }

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

    @Override
    public ViewManager getViewManager() {
        return viewManager;
    }

    @Override
    public Arrow[] getArrows() {
        return new Arrow[0];
    }

    @Override
    public void update() {
    }

    @Override
    public void showMessage(String message) {
    }
}
