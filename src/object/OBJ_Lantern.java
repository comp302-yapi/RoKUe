package object;

public class OBJ_Lantern extends SuperObject {

    public OBJ_Lantern() {
        super("/res/objects/lantern.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Lantern";
        collision = true;
    }
}