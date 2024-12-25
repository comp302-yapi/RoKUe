package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Barrel extends SuperObject {

    public OBJ_Barrel() {
        name = "Barrel";
        collision = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/barrel.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}