package object;

public class SWORD_DiamondSword extends SuperObject {

    private static SWORD_DiamondSword instance;

    private SWORD_DiamondSword() {
        super("/res/objects/sword/DiamondSword.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Diamond Sword";
        cost = 10;
        damage = 5;
    }

    public static SWORD_DiamondSword getInstance() {
        if (instance == null) {
            instance = new SWORD_DiamondSword();
        }
        return instance;
    }
}