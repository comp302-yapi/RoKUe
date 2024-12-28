package object;

import utils.ImageUtils;

import java.io.IOException;

import javax.imageio.ImageIO;

public class ENCH_ExtraLife extends SuperObject{

    public ENCH_ExtraLife() {

        name = "ExtraLife";
        ImageUtils scaler = new ImageUtils();

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_full.png"));
            image = scaler.scaleImage(image, 64, 64);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


