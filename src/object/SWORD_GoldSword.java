package object;

import java.util.Objects;

public class SWORD_GoldSword extends SuperObject {

    private static SWORD_GoldSword instance;

    private SWORD_GoldSword() {
        super("/res/objects/sword/GoldSword.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Gold Sword";
        cost = 7;
        damage = 2;
    }

    public static SWORD_GoldSword getInstance() {
        if (instance == null) {
            instance = new SWORD_GoldSword();
        }
        return instance;
    }
}