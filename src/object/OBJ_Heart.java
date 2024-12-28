package object;

import utils.ImageUtils;

import java.io.IOException;

import javax.imageio.ImageIO;

public class OBJ_Heart extends SuperObject{

    public OBJ_Heart() {

        name = "Heart";
        ImageUtils scaler = new ImageUtils();

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_full.png"));
            image2 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_half.png"));
            image3 = ImageIO.read(getClass().getResourceAsStream("/res/objects/heart_blank.png"));

            image = scaler.scaleImage(image, 64, 64);
            image2 = scaler.scaleImage(image2, 64, 64);
            image3 = scaler.scaleImage(image3, 64, 64);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}


