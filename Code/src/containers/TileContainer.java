package containers;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

import tile.Tile;

public class TileContainer {
	
	public static Tile[] tile = new Tile[40];
	
	public static BufferedImage allTiles;
	public static BufferedImage pinkTile;
	public static BufferedImage rockyTile00, rockyTile01, rockyTile02, rockyTile03, rockyTile10, rockyTile11,
	rockyTile12, rockyTile13, rockyTile20, rockyTile21, rockyTile22, rockyTile23;
	public static BufferedImage wallTile;
	public static BufferedImage columnTileTop, columnTileBottom, columnTileMiddle;
	public static BufferedImage boxTileTop, boxTileBottom;
	public static BufferedImage buildModeChest;
	public static BufferedImage entrance;
	public static BufferedImage openDoorTopLeft, openDoorTopRight, openDoorBottomLeft, openDoorBottomRight;
	public static BufferedImage closeDoorTopLeft, closeDoorTopRight, closeDoorBottomLeft, closeDoorBottomRight;

	public static BufferedImage fireFountainTop, fireFountainMiddle, fireFountainBottom;
	public static BufferedImage fireBanner;

	public static BufferedImage earthFountainTop, earthFountainMiddle, earthFountainBottom;
	public static BufferedImage earthBanner;

	public static BufferedImage waterFountainTop, waterFountainMiddle, waterFountainBottom;
	public static BufferedImage waterBanner;

	
	
	public TileContainer() {
		
	}

	public static void initiateTiles() {
		
		try {
			
			allTiles = ImageIO.read(Objects.requireNonNull(TileContainer.class.getResourceAsStream("/res/tiles/0x72_16x16DungeonTileset.v5.png")));
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

			entrance = allTiles.getSubimage(288, 112, 16, 16);
			closeDoorTopLeft = allTiles.getSubimage(368, 16, 16, 16);
			closeDoorTopRight = allTiles.getSubimage(384, 16, 16, 16);
			closeDoorBottomLeft = allTiles.getSubimage(368, 32, 16, 16);
			closeDoorBottomRight = allTiles.getSubimage(384, 32, 16, 16);
			openDoorTopLeft = allTiles.getSubimage(432, 16, 16, 16);
			openDoorTopRight = allTiles.getSubimage(448, 16, 16, 16);
			openDoorBottomLeft = allTiles.getSubimage(432, 32, 16, 16);
			openDoorBottomRight = allTiles.getSubimage(448, 32, 16, 16);

			// FIRE
			fireFountainTop = allTiles.getSubimage(0, 48, 16, 16);
			fireFountainMiddle = allTiles.getSubimage(0, 64, 16, 16);
			fireFountainBottom = allTiles.getSubimage(0, 82, 16, 16);

			fireBanner = allTiles.getSubimage(192, 64, 16, 16);

			// EARTH
			earthFountainTop = allTiles.getSubimage(16, 48, 16, 16);
			earthFountainMiddle = allTiles.getSubimage(16, 64, 16, 16);
			earthFountainBottom = allTiles.getSubimage(16, 82, 16, 16);

			earthBanner = allTiles.getSubimage(208, 64, 16, 16);

			// Water
			waterFountainTop = allTiles.getSubimage(32, 48, 16, 16);
			waterFountainMiddle = allTiles.getSubimage(32, 64, 16, 16);
			waterFountainBottom = allTiles.getSubimage(32, 82, 16, 16);

			waterBanner = allTiles.getSubimage(224, 64, 16, 16);

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
			
			buildModeChest = ImageIO.read(Objects.requireNonNull(TileContainer.class.getResourceAsStream("/res/rokue_like_assets/Buildmodechest.png")));
			tile[19] = new Tile();
			tile[19].image = buildModeChest;

			tile[20] = new Tile();
			tile[20].image = entrance;

			tile[21] = new Tile();
			tile[21].image = openDoorTopLeft;

			tile[22] = new Tile();
			tile[22].image = openDoorTopRight;

			tile[23] = new Tile();
			tile[23].image = openDoorBottomLeft;

			tile[24] = new Tile();
			tile[24].image = openDoorBottomRight;

			tile[25] = new Tile();
			tile[25].image = closeDoorTopLeft;

			tile[26] = new Tile();
			tile[26].image = closeDoorTopRight;

			tile[27] = new Tile();
			tile[27].image = closeDoorBottomLeft;
			tile[27].collision = true;

			tile[28] = new Tile();
			tile[28].image = closeDoorBottomRight;
			tile[28].collision = true;

			// FIRE
			tile[29] = new Tile();
			tile[29].image = fireFountainMiddle;
			tile[29].collision = true;

			tile[30] = new Tile();
			tile[30].image = fireFountainBottom;

			tile[31] = new Tile();
			tile[31].image = fireBanner;
			tile[31].collision = true;

			// Earth
			tile[32] = new Tile();
			tile[32].image = earthFountainMiddle;
			tile[32].collision = true;

			tile[33] = new Tile();
			tile[33].image = earthFountainBottom;

			tile[34] = new Tile();
			tile[34].image = earthBanner;
			tile[34].collision = true;

			// Water
			tile[35] = new Tile();
			tile[35].image = waterFountainMiddle;
			tile[35].collision = true;

			tile[36] = new Tile();
			tile[36].image = waterFountainBottom;

			tile[37] = new Tile();
			tile[37].image = waterBanner;
			tile[37].collision = true;

		} catch(IOException e) {
			e.printStackTrace();
		}
		
	}
	
	
	public static Tile[] getTile() {
		
		return tile;
		
	}

}
