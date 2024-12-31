package object;

public class OBJ_IronArmor extends SuperObject {

    public OBJ_IronArmor() {
        super("/res/objects/iron_armor.png", null, null); // Pass the image path for image1, others are null if unused
        name = "IronArmor";
        cost = 3;
        armor = 2;
    }
}