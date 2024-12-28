package object;

import java.io.IOException;
import javax.imageio.ImageIO;

public class ENCH_Reveal extends SuperObject {

    public ENCH_Reveal() {
        name = "Reveal";
        collision = false;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/reveal.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


