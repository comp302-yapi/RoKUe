package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Stone extends SuperObject {

    public OBJ_Stone() {
        name = "Stone";
        collision = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/stone.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}