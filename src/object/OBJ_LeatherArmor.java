package object;

public class OBJ_LeatherArmor extends SuperObject {

    public OBJ_LeatherArmor() {
        super("/res/objects/leather_armor.png", null, null); // Pass the image path for image1, others are null if unused
        name = "LeatherArmor";
        cost = 1;
        armor = 1;
    }
}