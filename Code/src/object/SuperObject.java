package object;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

import main.GamePanel;
import views.BasePanel;

public class SuperObject {

	public BufferedImage image;
	public String name;
	public boolean collision = false;
	public int worldX, worldY;
	public Rectangle solidArea = new Rectangle(0,0,48,48);
	public int solidAreaDefaultX = 0;
	public int solidAreaDefaultY = 0;

	public void draw(Graphics2D g2, BasePanel panel) {
		
		int screenX = worldX - panel.getPlayer().worldX + panel.getPlayer().screenX;
		int screenY = worldY - panel.getPlayer().worldY + panel.getPlayer().screenY;
		
		if (worldX > panel.getPlayer().worldX - panel.tileSize - panel.getPlayer().screenX &&
				worldX < panel.getPlayer().worldX + panel.tileSize + panel.getPlayer().screenX &&
				worldY > panel.getPlayer().worldY - panel.tileSize - panel.getPlayer().screenY &&
				worldY < panel.getPlayer().worldY + panel.tileSize + panel.getPlayer().screenY) {
			
			g2.drawImage(image, screenX, screenY, panel.tileSize, panel.tileSize, null);
			
		}
	}
	
}
