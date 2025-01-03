package object;

import utils.ImageUtils;

public class ENCH_ExtraLife extends SuperObject {

    public ENCH_ExtraLife() {
        super("/res/objects/heart_full.png", null, null); // Pass the image path for image1, others are null if unused
        name = "ExtraLife";

        ImageUtils scaler = new ImageUtils();
        if (image != null) {
            image = scaler.scaleImage(image, 64, 64); // Scale the image after loading
        }
    }
}