package managers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.imageio.ImageIO;

import containers.TileContainer;
import tile.Tile;
import views.BasePanel;

public class TileManager {

	BasePanel panel;

	public int mapTileNum[][];
	
	
	
	 
	
	public TileManager(BasePanel panel) {
		 
		this.panel = panel;
		mapTileNum = new int[BasePanel.maxWorldCol][BasePanel.maxWorldRow];
		//getTileImage();
		loadMap("/res/maps/world01.txt");
	}
	
	
	public void loadMap(String filePath) {

		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < BasePanel.maxWorldCol && row < BasePanel.maxWorldRow) {
				
				String line = br.readLine();
				
				while (col < BasePanel.maxWorldCol) {
					
					String[] numbers = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				
				if (col == BasePanel.maxWorldCol) {
					col = 0;
					row++;
				}
			}

			br.close();

		} catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	
	public void draw(Graphics2D g2) {

		int worldCol = 0;
		int worldRow = 0;
		
		while (worldCol < BasePanel.maxWorldCol && worldRow < BasePanel.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * BasePanel.tileSize;
			int worldY = worldRow * BasePanel.tileSize;
			int screenX = worldX - panel.getPlayer().worldX + panel.getPlayer().screenX;
			int screenY = worldY - panel.getPlayer().worldY + panel.getPlayer().screenY;
			
			if (worldX > panel.getPlayer().worldX - BasePanel.tileSize - panel.getPlayer().screenX &&
					worldX < panel.getPlayer().worldX + BasePanel.tileSize + panel.getPlayer().screenX &&
					worldY > panel.getPlayer().worldY - BasePanel.tileSize - panel.getPlayer().screenY &&
					worldY < panel.getPlayer().worldY + BasePanel.tileSize + panel.getPlayer().screenY) {
				
				g2.drawImage(TileContainer.getTile()[tileNum].image, screenX, screenY, BasePanel.tileSize, BasePanel.tileSize, null);
				
			}
			
			worldCol++;

			if (worldCol == BasePanel.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
			
		}
		
	}
	
	
}









