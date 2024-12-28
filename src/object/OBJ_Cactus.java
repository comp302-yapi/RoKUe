package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Cactus extends SuperObject {

    public OBJ_Cactus() {
        name = "Cactus";
        collision = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/cactus.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}