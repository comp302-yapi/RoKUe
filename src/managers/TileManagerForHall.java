package managers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;
import javax.imageio.ImageIO;

import containers.TileContainer;
import entity.Entity;
import enums.Hall;
import object.*;
import tile.Tile;
import views.BasePanel;
import views.HallPanel;

public class TileManagerForHall implements Serializable {

	private static final long serialVersionUID = 1L;

	public Hall hall;

	public int[][] mapTileNum;
	public ArrayList<SuperObject> objects = new ArrayList<>();
	public ArrayList<SuperObject> objectsEarth;
	public ArrayList<SuperObject> objectsAir;
	public ArrayList<SuperObject> objectsWater;
	public ArrayList<SuperObject> objectsFire;
	public ArrayList<SuperObject> enchantments = new ArrayList<>();
	public SuperObject[][] gridWorld = new SuperObject[13][14];
	//public ArrayList<Entity> monsters = new ArrayList<>();
	public int maxCol,maxRow,idx;
	public boolean isOpened = false;
	private final int bottomWorldBorder;

	public TileManagerForHall(Hall hall, String path, int maxCol, int maxRow) {
        this.maxCol = maxCol;
		this.maxRow = maxRow;
		this.hall = hall;
		mapTileNum = new int[BasePanel.maxWorldCol][BasePanel.maxWorldRow];
		idx = 0;
		isOpened = false;
		loadMap(path);
		bottomWorldBorder = 96 + BasePanel.tileSize * 15;
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

	public void openDoor() {
		mapTileNum[5][13] = 21;
		mapTileNum[6][13] = 22;
		mapTileNum[5][14] = 23;
		mapTileNum[6][14] = 24;

		isOpened = true;
	}

	public void closeDoor() {
		mapTileNum[5][13] = 25;
		mapTileNum[6][13] = 26;
		mapTileNum[5][14] = 27;
		mapTileNum[6][14] = 28;

		isOpened = false;
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

		convertToGrid(objects);


		for (int i = 0; i < gridWorld.length; i++) { // Loop through rows
			for (int j = 0; j < gridWorld[i].length; j++) { // Loop through columns
				if (gridWorld[i][j] != null) {
					g2.drawImage(gridWorld[i][j].image, gridWorld[i][j].worldX, gridWorld[i][j].worldY, BasePanel.tileSize, BasePanel.tileSize, null);			}
			}
		}
	}

	public void convertToGrid(ArrayList<SuperObject> superObjects) {

		int row, column;

		for (SuperObject object : objects) {
			if (object != null) {

				column = (object.worldY - 96) / 48;
				row =  (object.worldX - 336) / 48;
				gridWorld[row][column] = object;

			}
		}

	}

	public int getBottomWorldBorder() {
		return bottomWorldBorder;
	}

	public boolean checkObject(int x, int y) {

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

		int rx = x % 48;
		int ry = y % 48;

		if(rx <= 24) x = x - rx;
		if (rx > 24) x = x + (48 - rx);

		if(ry <= 24) y = y - ry;
		if (ry > 24) y = y + (48 - ry);

		int px = BasePanel.screenWidth/2 - (BasePanel.tileSize/2);
		int py = BasePanel.screenHeight/2 - (BasePanel.tileSize/2);

		System.out.println(y - py);
		System.out.println(y + " y " + py);
		System.out.println(x - px);
		System.out.println(x + " x " + px);
		if (Math.abs(x - px) > 48 || Math.abs(y - py) > 48){

			System.out.println(y - py);
			System.out.println(y + " y " + py);


			obj.worldX = x;
			obj.worldY = y;
			objects.add(obj);

		}



	}

	public void generateEnchantment() {
		Random random = new Random();

		// Randomly pick a type of object
		SuperObject obj;
		int randomType = random.nextInt(5);
//		randomType = 0;

		switch (randomType) {
			case 0 -> obj = new ENCH_Reveal();
			case 1 -> obj = new ENCH_Cloak();
			case 2 -> obj = new ENCH_LuringGem();
			case 3 -> obj = new ENCH_ExtraLife();
			case 4 -> obj = new ENCH_AddTime();
			default -> throw new IllegalStateException("Unexpected value: " + randomType);
		}

		// Set random position
		obj.worldX = (random.nextInt(1, 13) + 7) * 48;
		obj.worldY = (random.nextInt(1, 14) + 2) * 48;

		// Add the object to enchantments list
		enchantments.add(obj);
	}

	public void generateGold() {
		Random random = new Random();

		SuperObject gold = new MISC_Gold();

		// Set random position
		gold.worldX = (random.nextInt(1, 13) + 7) * 48;
		gold.worldY = (random.nextInt(1, 14) + 2) * 48;

		// Add the object to enchantments list
		enchantments.add(gold);
	}

	public SuperObject[] convertToArray() {
		return objects.toArray(new SuperObject[30]);
	}

	public void removeObject(SuperObject obj) {
		int column = (obj.worldY - 96) / 48;
		int row =  (obj.worldX - 336) / 48;
		gridWorld[row][column]  = null; 
		
		objects.remove(obj);
	}
}
