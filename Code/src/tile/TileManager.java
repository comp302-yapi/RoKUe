package tile;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.imageio.ImageIO;
import views.BasePanel;

public class TileManager {

	BasePanel panel;
	public Tile[] tile;
	public int mapTileNum[][];
	
	public BufferedImage allTiles;
	public BufferedImage pinkTile;
	public BufferedImage rockyTile00, rockyTile01, rockyTile02, rockyTile03, rockyTile10, rockyTile11,
	rockyTile12, rockyTile13, rockyTile20, rockyTile21, rockyTile22, rockyTile23;
	public BufferedImage wallTile;
	public BufferedImage columnTileTop, columnTileBottom, columnTileMiddle;
	public BufferedImage boxTileTop, boxTileBottom;
	
	
	
	
	public TileManager(BasePanel panel) {
		
		this.panel = panel;
		
		tile = new Tile[20];
		mapTileNum = new int[panel.maxWorldCol][panel.maxWorldRow];
		getTileImage();
		loadMap("/res/maps/world01.txt");
	}
	
	public void getTileImage() {
		try {
			allTiles = ImageIO.read(Objects.requireNonNull(getClass().getResourceAsStream("/res/tiles/0x72_16x16DungeonTileset.v5.png")));
			pinkTile = allTiles.getSubimage(32, 48, 16, 16);
			
			rockyTile00 = allTiles.getSubimage(0, 96, 16, 16);
			rockyTile01 = allTiles.getSubimage(16, 96, 16, 16);
			rockyTile02 = allTiles.getSubimage(32, 96, 16, 16);
			rockyTile03 = allTiles.getSubimage(48, 96, 16, 16);
			rockyTile10 = allTiles.getSubimage(0, 112, 16, 16);
			rockyTile11 = allTiles.getSubimage(16, 112, 16, 16);
			rockyTile12 = allTiles.getSubimage(32, 112, 16, 16);
			rockyTile13 = allTiles.getSubimage(48, 112, 16, 16);
			rockyTile20 = allTiles.getSubimage(0, 128, 16, 16);
			rockyTile21 = allTiles.getSubimage(16, 128, 16, 16);
			rockyTile22 = allTiles.getSubimage(32, 128, 16, 16);
			rockyTile23 = allTiles.getSubimage(48, 128, 16, 16);
			
			columnTileTop = allTiles.getSubimage(96, 48, 16, 16);
			columnTileMiddle = allTiles.getSubimage(96, 64, 16, 16);
			columnTileBottom = allTiles.getSubimage(96, 80, 16, 16);
			
			boxTileTop = allTiles.getSubimage(96, 96, 16, 16);
			boxTileBottom = allTiles.getSubimage(96, 112, 16, 16);

			wallTile = allTiles.getSubimage(0, 12, 16, 16);
			
			// ROCKY TILE
			
			tile[0] = new Tile();
			tile[0].image = rockyTile00;

			tile[1] = new Tile();
			tile[1].image = rockyTile01;

			tile[2] = new Tile();
			tile[2].image = rockyTile02;

			tile[3] = new Tile();
			tile[3].image = rockyTile03;

			tile[4] = new Tile();
			tile[4].image = rockyTile10;

			tile[5] = new Tile();
			tile[5].image = rockyTile11;

			tile[6] = new Tile();
			tile[6].image = rockyTile12;

			tile[7] = new Tile();
			tile[7].image = rockyTile13;

			tile[8] = new Tile();
			tile[8].image = rockyTile20;

			tile[9] = new Tile();
			tile[9].image = rockyTile21;

			tile[10] = new Tile();
			tile[10].image = rockyTile22;

			tile[11] = new Tile();
			tile[11].image = rockyTile23;
			
			// WALL

			tile[12] = new Tile();
			tile[12].image = wallTile;
			tile[12].collision = true;
			
			// EMPTY ROAD
			
			tile[13] = new Tile();
			tile[13].image = pinkTile;
			
			// COLUMN TILE
			
			tile[14] = new Tile();
			tile[14].image = columnTileTop;
			tile[14].collision = true;

			tile[15] = new Tile();
			tile[15].image = columnTileMiddle;
			tile[15].collision = true;

			tile[16] = new Tile();
			tile[16].image = columnTileBottom;
			
			// BOX TILE
			
			tile[17] = new Tile();
			tile[17].image = boxTileTop;
			tile[17].collision = true;
			
			tile[18] = new Tile();
			tile[18].image = boxTileBottom; 
			tile[18].collision = true;
			
		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	public void loadMap(String filePath) {

		try {
			InputStream is = getClass().getResourceAsStream(filePath);
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			
			int col = 0;
			int row = 0;
			
			while (col < panel.maxWorldCol && row < panel.maxWorldRow) {
				
				String line = br.readLine();
				
				while (col < panel.maxWorldCol) {
					
					String[] numbers = line.split(" ");
					
					int num = Integer.parseInt(numbers[col]);
					
					mapTileNum[col][row] = num;
					col++;
				}
				
				if (col == panel.maxWorldCol) {
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
		
		while (worldCol < panel.maxWorldCol && worldRow < panel.maxWorldRow) {
			
			int tileNum = mapTileNum[worldCol][worldRow];
			
			int worldX = worldCol * panel.tileSize;
			int worldY = worldRow * panel.tileSize;
			int screenX = worldX - panel.getPlayer().worldX + panel.getPlayer().screenX;
			int screenY = worldY - panel.getPlayer().worldY + panel.getPlayer().screenY;
			
			if (worldX > panel.getPlayer().worldX - panel.tileSize - panel.getPlayer().screenX &&
					worldX < panel.getPlayer().worldX + panel.tileSize + panel.getPlayer().screenX &&
					worldY > panel.getPlayer().worldY - panel.tileSize - panel.getPlayer().screenY &&
					worldY < panel.getPlayer().worldY + panel.tileSize + panel.getPlayer().screenY) {
				
				g2.drawImage(tile[tileNum].image, screenX, screenY, panel.tileSize, panel.tileSize, null);
				
			}
			
			worldCol++;

			if (worldCol == panel.maxWorldCol) {
				worldCol = 0;
				worldRow++;
			}
			
		}
		
	}
	
	
}









