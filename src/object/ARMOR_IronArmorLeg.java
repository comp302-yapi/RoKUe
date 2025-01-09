package object;

import java.util.Objects;

public class ARMOR_IronArmorLeg extends SuperObject {

    public ARMOR_IronArmorLeg() {
        super("/res/objects/armor/IronLeggings.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Iron Leggings";
        cost = 4;
        armor = 10;
    }
}

