package object;

import containers.TileContainer;
import enums.Hall;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Objects;

import javax.imageio.ImageIO;

public class OBJ_Entrance extends SuperObject {

    private transient BufferedImage allTiles, entrance;
    private Hall hall;
    private boolean allowEnter = false;

    public OBJ_Entrance(Hall hall) {
        super(null, null, null); // Pass null since we are handling custom subimage loading
        name = "Entrance";
        this.collision = false;
        this.hall = hall;

        loadImages(); // Load images using the centralized method
    }

    private void loadImages() {
        try {
            allTiles = ImageIO.read(Objects.requireNonNull(TileContainer.class.getResourceAsStream("/res/tiles/0x72_16x16DungeonTileset.v5.png")));
            entrance = allTiles.getSubimage(288, 112, 16, 16);
            this.image = entrance; // Assign the specific subimage to the `image` field
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void writeObject(java.io.ObjectOutputStream out) throws IOException {
        out.defaultWriteObject();
    }

    protected void readObject(java.io.ObjectInputStream in) throws IOException, ClassNotFoundException {
        in.defaultReadObject();
        loadImages(); // Reinitialize the images after deserialization
    }
}