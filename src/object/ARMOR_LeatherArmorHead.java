package object;

public class ARMOR_LeatherArmorHead extends SuperObject {

    public ARMOR_LeatherArmorHead() {
        super("/res/objects/armor/LeatherHelmet.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Leather Helmet";
        cost = 1;
        armor = 1;
    }
}