package object;

public class SWORD_IronSword extends SuperObject {

    private static SWORD_IronSword instance;

    private SWORD_IronSword() {
        super("/res/objects/sword/IronSword.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Iron Sword";
        cost = 5;
        damage = 1;
    }

    public static SWORD_IronSword getInstance() {
        if (instance == null) {
            instance = new SWORD_IronSword();
        }
        return instance;
    }
}