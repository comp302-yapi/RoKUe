package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class ENCH_Cloak extends SuperObject {

    public ENCH_Cloak() {
        name = "Cloak";
        collision = false;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/cloak.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


