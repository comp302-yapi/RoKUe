package object;

import java.util.Objects;

public class ARMOR_IronArmorHead extends SuperObject {

    public ARMOR_IronArmorHead() {
        super("/res/objects/armor/IronHelmet.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Iron Helmet";
        cost = 3;
        armor = 2;
    }
}

