package object;

import java.util.Objects;

public class SWORD_IronSword extends SuperObject {

    public SWORD_IronSword() {
        super("/res/objects/sword/IronSword.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Iron Sword";
        cost = 5;
        damage = 1;
    }
}

