package object;

import utils.ImageUtils;

public class OBJ_Heart extends SuperObject {

    public OBJ_Heart() {
        super(
                "/res/objects/heart_full.png",
                "/res/objects/heart_half.png",
                "/res/objects/heart_blank.png"
        ); // Pass all image paths to the SuperObject constructor

        name = "Heart";
        ImageUtils scaler = new ImageUtils();

        // Scale images
        if (image != null) image = scaler.scaleImage(image, 32, 32);
        if (image2 != null) image2 = scaler.scaleImage(image2, 32, 32);
        if (image3 != null) image3 = scaler.scaleImage(image3, 32, 32);
    }
}