package object;

import java.util.Objects;

public class SWORD_DiamondSword extends SuperObject {

    public SWORD_DiamondSword() {
        super("/res/objects/sword/DiamondSword.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Diamond Sword";
        cost = 10;
        damage = 5;
    }
}

