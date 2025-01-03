package object;

public class ARMOR_IronArmorTorso extends SuperObject {

    public ARMOR_IronArmorTorso() {
        super("/res/objects/iron_armor.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Iron Chestplate";
        cost = 4;
        armor = 6;
    }
}