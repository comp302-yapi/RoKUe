package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class ENCH_LuringGem extends SuperObject {

    public ENCH_LuringGem() {
        name = "LuringGem";
        collision = false;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/LuringGem.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


