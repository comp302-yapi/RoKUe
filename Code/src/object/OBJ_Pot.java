package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Pot extends SuperObject {

    public OBJ_Pot() {
        name = "Pot";
        collision = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/pot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}