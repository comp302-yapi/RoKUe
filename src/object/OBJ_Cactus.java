package object;

public class OBJ_Cactus extends SuperObject {

    public OBJ_Cactus() {
        super("/res/objects/cactus.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Cactus";
        this.collision = true;
    }
}