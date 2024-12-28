package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import views.BasePanel;
import views.HallPanel;

public class SuperObject {

	public BufferedImage image, image2, image3;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0, 0, 48, 48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;

	// Field to track if the object contains a hidden rune
	public boolean hasRune = false;

	/**
	 * Handles the player's interaction when they click this object.
	 * For example, if the object contains a rune, it prints a message.
	 */


	private int calculateDistanceToPlayer(HallPanel hp) {
		int playerX = hp.getPlayer().screenX;
		int playerY = hp.getPlayer().screenY;

		return (int) Math.sqrt(
				Math.pow(worldX - playerX, 2) +
						Math.pow(worldY - playerY, 2)
		);
	}
	public boolean interact(HallPanel panel) {

		int dist = this.calculateDistanceToPlayer(panel);
		if (dist <= 65) {
			if (hasRune) { 
				panel.playSE(1);
				panel.playSE(2);
				panel.showMessage("Rune found!");
				System.out.println("Rune found!");
				hasRune = false;
				return true;
			}
			else {
				System.out.println("No rune");
				return false;
			}

		} else {
			System.out.println("Too far");
		}
		return false;
	}

	/**
	 * Draws the object on the screen if it is within the player's view.
	 */
	public void draw(Graphics2D g2, BasePanel panel) {

		int screenX = worldX - panel.getPlayer().worldX + panel.getPlayer().screenX;
		int screenY = worldY - panel.getPlayer().worldY + panel.getPlayer().screenY;

		if (worldX > panel.getPlayer().worldX - BasePanel.tileSize - panel.getPlayer().screenX &&
				worldX < panel.getPlayer().worldX + BasePanel.tileSize + panel.getPlayer().screenX &&
				worldY > panel.getPlayer().worldY - BasePanel.tileSize - panel.getPlayer().screenY &&
				worldY < panel.getPlayer().worldY + BasePanel.tileSize + panel.getPlayer().screenY) {

			g2.drawImage(image, screenX, screenY, BasePanel.tileSize, BasePanel.tileSize, null);

		}
	}
}