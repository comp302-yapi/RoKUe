package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Lantern extends SuperObject {

    public OBJ_Lantern() {
        name = "Lantern";
        collision = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/lantern.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}