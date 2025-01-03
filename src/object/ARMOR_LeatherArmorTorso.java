package object;

public class ARMOR_LeatherArmorTorso extends SuperObject {

    public ARMOR_LeatherArmorTorso() {
        super("/res/objects/leather_armor.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Leather Chestplate";
        cost = 2;
        armor = 3;
    }
}