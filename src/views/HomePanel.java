package views;

import entity.Entity;
import listeners.keylisteners.HallPanelKeyListener;
import listeners.keylisteners.HomePanelKeyListener;
import managers.ViewManager;
import java.awt.*;
import java.util.ArrayList;

public class HomePanel extends PlayablePanel {

    private final ArrayList<Entity> entities = new ArrayList<>();
    private boolean isPaused;
    private final HomePanelKeyListener keyListener;

    public HomePanel(ViewManager viewManager) {
        super(viewManager);
        setLayout(null); // No layout, fully customizable space
        getPlayer().panel = this; // Associate the player with this panel

        this.keyListener = new HomePanelKeyListener(this);
        this.addKeyListener(keyListener);
        getPlayer().addKeyListener(keyListener);
    }

    @Override
    public void update() {
        getPlayer().move();
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        update();

        // Clear the panel
        g2.setColor(Color.WHITE); // Background color
        g2.fillRect(0, 0, getWidth(), getHeight());

        // Draw the player
        getPlayer().draw(g2);


        // If paused, show a pause screen
        if (isPaused) {
            drawPauseScreen(g2);
        }

        g2.dispose();
    }

    private void drawPauseScreen(Graphics2D g2) {
        String text = "PAUSED";
        g2.setFont(new Font("Arial", Font.BOLD, 40));
        g2.setColor(Color.BLACK);
        int x = (getWidth() - g2.getFontMetrics().stringWidth(text)) / 2;
        int y = getHeight() / 2;
        g2.drawString(text, x, y);
    }

    public void setPaused(boolean paused) {
        this.isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }
}