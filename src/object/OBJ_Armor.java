package object;

import utils.ImageUtils;

public class OBJ_Armor extends SuperObject {

    public OBJ_Armor() {
        super(
                "/res/objects/ArmorFull.png",
                "/res/objects/AmorHalf.png",
                "/res/objects/ArmorEmpty.png"
        );

        name = "Armor";
        ImageUtils scaler = new ImageUtils();

        // Scale images
        if (image != null) image = scaler.scaleImage(image, 32, 32);
        if (image2 != null) image2 = scaler.scaleImage(image2, 32, 32);
        if (image3 != null) image3 = scaler.scaleImage(image3, 32, 32);
    }
}