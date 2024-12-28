package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Chain extends SuperObject {

    public OBJ_Chain() {
        name = "Chain";
        collision = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/chain.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}