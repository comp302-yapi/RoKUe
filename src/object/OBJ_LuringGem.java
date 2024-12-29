package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_LuringGem extends SuperObject {

    public OBJ_LuringGem() {
        name = "LuringGem";
        collision = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/ThrowedLuringGem.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}