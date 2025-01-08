package object;

public class ARMOR_LeatherArmorLeg extends SuperObject {

    public ARMOR_LeatherArmorLeg() {
        super("/res/objects/armor/LeatherLeggings.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Leather Leggings";
        cost = 2;
        armor = 2;
    }
}