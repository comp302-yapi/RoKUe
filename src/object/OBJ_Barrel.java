package object;

public class OBJ_Barrel extends SuperObject {

    public OBJ_Barrel() {
        super("/res/objects/barrel.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Barrel";
        this.collision = true;
    }
}