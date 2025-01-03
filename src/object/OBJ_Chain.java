package object;

public class OBJ_Chain extends SuperObject {

    public OBJ_Chain() {
        super("/res/objects/chain.png", null, null); // Pass the image path for image1, others are null if unused
        name = "Chain";
        this.collision = true;
    }
}