package managers;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Objects;
import javax.imageio.ImageIO;

import containers.TileContainer;
import object.SuperObject;
import tile.Tile;
import views.BasePanel;

public class TileManagerForBoss implements Serializable {

    private static final long serialVersionUID = 1L;

    BasePanel panel;
    public int mapTileNum[][];
    public int maxCol,maxRow,idx;
    public ArrayList<SuperObject> objects = new ArrayList<>();
    public SuperObject[][] gridWorld = new SuperObject[13][14];

    public TileManagerForBoss(BasePanel panel, String path, int maxCol, int maxRow) {

        this.maxCol = maxCol;
        this.maxRow = maxRow;
        this.panel = panel;
        idx = 0;
        mapTileNum = new int[BasePanel.maxWorldCol][BasePanel.maxWorldRow];
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

    public void draw(Graphics2D g2) {

        int worldCol = 0;
        int worldRow = 0;

        while (worldCol < maxCol && worldRow < maxRow) {

            int tileNum = mapTileNum[worldCol][worldRow];

            int worldX = worldCol * BasePanel.tileSize;
            int worldY = worldRow * BasePanel.tileSize;

            g2.drawImage(TileContainer.getTile()[tileNum].image, worldX, worldY, BasePanel.tileSize, BasePanel.tileSize, null);


            worldCol++;

            if (worldCol == maxCol) {
                worldCol = 0;
                worldRow++;
            }
        }

    }

    public void addObject(SuperObject obj, int x, int y) {
        obj.worldX = x;
        obj.worldY = y;
        objects.add(obj);

    }
}









