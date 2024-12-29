package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class OBJ_Pot extends SuperObject {

    public OBJ_Pot() {
        super("/res/objects/pot.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Pot";
        collision = true;
    }
}