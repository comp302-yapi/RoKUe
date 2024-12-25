package managers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;

import containers.TileContainer;
import enums.Hall;
import object.SuperObject;
import tile.Tile;
import views.BasePanel;
import views.HallPanel;

public class TileManagerForHall{

	public Hall hall;

	public int[][] mapTileNum;
	public ArrayList<SuperObject> objects = new ArrayList<>();
	public int maxCol,maxRow,idx;

	public TileManagerForHall(Hall hall, String path, int maxCol, int maxRow) {
        this.maxCol = maxCol;
		this.maxRow = maxRow;
		this.hall = hall;
		mapTileNum = new int[BasePanel.maxWorldCol][BasePanel.maxWorldRow];
		idx = 0;
		loadMap(path);
	}
	

	
	public void loadMap(String filePath) {

		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < maxCol && row < maxRow) {
				
				String line = br.readLine();
				
				while (col < maxCol) {
					
					String[] numbers = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				
				if (col == maxCol) {
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
		
		while (worldCol < maxCol && worldRow < maxRow) {

			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * BasePanel.tileSize;
			int worldY = worldRow * BasePanel.tileSize;

			if(checkObject(worldX + 300, worldY + 100)) {
				g2.drawImage(TileContainer.getTile()[tileNum].image, worldX + 336, worldY + 96, BasePanel.tileSize, BasePanel.tileSize, null);
			}
			
			worldCol++;

			if (worldCol == maxCol) {
				worldCol = 0;
				worldRow++;
			}
		}



		for (SuperObject object : objects) {
			if (object != null) {
				g2.drawImage(object.image, object.worldX, object.worldY, BasePanel.tileSize, BasePanel.tileSize, null);
			}
		}
	}
	
	public boolean checkObject(int x,int y) {

		for (SuperObject object : objects) {
			if (object != null) {
				if (object.worldX == x && object.worldY == y) {
					return false;
				}
			}
		}
		
		return true;
	}
	
	public void addObject(SuperObject obj, int x, int y) {
		obj.worldX = x;
		obj.worldY = y;
		objects.add(obj);

	}

	public SuperObject[] convertToArray() {


		return objects.toArray(new SuperObject[30]);

	}

	public void removeObject(SuperObject obj) {
		objects.remove(obj);
	}
}
