package object;

import containers.HallContainer;
import containers.TileContainer;
import enums.Hall;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class OBJ_Entrance extends SuperObject{

    transient BufferedImage allTiles, entrance;
    Hall hall;
    boolean allowEnter = false;

    public OBJ_Entrance(Hall hall) {

        name = "Entrance";
        this.collision = false;

        this.hall = hall;

        try {
            allTiles = ImageIO.read(Objects.requireNonNull(TileContainer.class.getResourceAsStream("/res/tiles/0x72_16x16DungeonTileset.v5.png")));
            entrance = allTiles.getSubimage(288, 112, 16, 16);
            this.image = entrance;

        } catch (IOException e) {
            e.printStackTrace();
        }
        collision = false;
    }

}



